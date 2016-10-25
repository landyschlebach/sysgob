package com.db.sysgob.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.db.sysgob.bo.ProjectBO;
import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.Category;
import com.db.sysgob.entity.Dependency;
import com.db.sysgob.entity.Project;
import com.db.sysgob.entity.ProjectSummary;
import com.db.sysgob.entity.User;
import com.db.sysgob.service.BudgetService;
import com.db.sysgob.service.ProjectService;

@Controller
@RequestMapping("/proyectos")
public class ProjectController {
	private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ProjectBO projectBO;
	
	@Autowired
	private ProjectService projectWS;
	
	@Autowired
	private BudgetService budgetWS;

	@RequestMapping("/consulta")
	public String viewProjects(ModelMap model, HttpSession session) {
		Dependency dependency = (Dependency) session.getAttribute("dependency");
		
		List<ProjectSummary> projects = projectWS.findByDependencyId(dependency.getDependencyId());
		log.debug("Loading: Projects [" + projects +"]");
	    
	    model.addAttribute("projects", projects);	    
		return "proyectos";
	}
	
	@RequestMapping("/nuevo")
	public String showForm(ModelMap model, HttpSession session) {	
		return "formulario_proyectos";
	}

	@RequestMapping(value = "/nuevo", method = RequestMethod.POST)
	public String newProject(ModelMap model, 
			String name, String description, 
			Long amount, Long categoryId, HttpSession session) throws ParseException {		
		
		boolean projectRS = false;
		boolean budgetRS = false;
		
		String view = "formulario_proyectos";
		User user = (User) session.getAttribute("user");
		Dependency dependency = (Dependency) session.getAttribute("dependency");
		
		if(name != null && amount != null && categoryId != null){
			Project project = new Project();
			project.setName(name);
			project.setDescription(description);
			project.setAmount(amount);
			project.setCategoryId(categoryId);
			project.setCreateDate(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
			project.setUpdateDate(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
			project.setUserId(user.getUserId());
			
			log.debug("Creating new Project [" + project + "]");
			projectRS = projectWS.create(project);
			session.setAttribute("newProject", project);
		}
		else {
			projectRS = false;
		}
		
		if(projectBO.verifyBudget(dependency.getDependencyId()) == null) {
			log.debug("First project created for dependency[" + dependency.getName() + "]. Will create budget.");
			
			Budget budget = projectBO.initiateDefaultBudget(dependency.getDependencyId());
			budgetRS = budgetWS.create(budget);
		} else {
			budgetRS = true;
		}
		
		if(projectRS && budgetRS) {
			log.debug("Showing success alert");
			view = "redirect:/proyectos/clasificar";
		} else if (!projectRS || !budgetRS) {
			log.debug("Showing error alert");
			model.addAttribute("failure", true);
		} else if (budgetRS && !projectRS) {
			log.debug("Showing warning alert");
			model.addAttribute("dataMissing", true);
		}

		return view;
	}	
	
	@RequestMapping(value = "/clasificar", method = RequestMethod.GET)
	public String classifyProject(ModelMap model, HttpSession session) {
		List<Category> categories = projectWS.getProjectCategories();
		
		model.addAttribute("categoryHigh", categories.get(0).getMinScore());
		model.addAttribute("categoryMediumHigh", categories.get(1).getMinScore());
		model.addAttribute("categoryMediumLow", categories.get(2).getMinScore());
		model.addAttribute("categoryLow", categories.get(3).getMinScore());
		
		return "categorias";
	}
	
	@RequestMapping(value = "/clasificar", method = RequestMethod.POST)
	public String classifyProject(ModelMap model,
			Long riskClassification, Long othersClassification,
			Long otherImplications, Long financeClassification,
			Long strategicClassification, HttpSession session) throws ParseException {
		
		boolean projectRS = false;
		boolean budgetRS = false;

		Dependency dependency = (Dependency) session.getAttribute("dependency");
		
		Long points = projectBO.calculateCategory(riskClassification, othersClassification,
				otherImplications, financeClassification, strategicClassification);
		
		Project project = (Project) session.getAttribute("newProject");
		project = projectBO.defineCategory(project, points);
		Budget budget = projectBO.calculateBudget(project, dependency.getDependencyId());
		
		projectRS = projectWS.modify(project);
		budgetRS = budgetWS.modify(budget);
		
		if(projectRS && budgetRS) {
			log.debug("Showing success alert");
			model.addAttribute("success", true);
		} else if (!projectRS || !budgetRS) {
			log.debug("Showing error alert");
			model.addAttribute("failure", true);
		}
		
		return "categorias";
	}
	
	@RequestMapping("/modificar/{projectId}")
	public String edit(ModelMap model, 
			@PathVariable Long projectId, HttpSession session) {
		Dependency dependency = (Dependency) session.getAttribute("dependency");
		
		model.addAttribute("prevId", projectBO.getPreviousProject(projectId, dependency.getDependencyId()));
		model.addAttribute("nextId", projectBO.getNextProject(projectId, dependency.getDependencyId()));
	    model.addAttribute("project", projectBO.getCurrentProject(projectId, dependency.getDependencyId()));
		return "editar_proyectos";
	}

	@RequestMapping(value = "/modificar/{projectId}", method = RequestMethod.POST)
	public String editProject(ModelMap model,
			@PathVariable Long projectId, 
			String name, String description, 
			Long amount, Long categoryId, HttpSession session) throws ParseException {
		
		Budget budget = null;
		boolean projectRS = false;
		boolean budgetRS = false; 

		User user = (User) session.getAttribute("user");
		Dependency dependency = (Dependency) session.getAttribute("dependency");

		Project project = (Project) model.get("project");
		log.debug("Project [" + project + "] has been modified");
		
		project.setName(name);
		project.setDescription(description);
		project.setAmount(amount);
		project.setCategoryId(categoryId);
		project.setUpdateDate(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
		project.setUserId(user.getUserId());
		
		projectRS = projectWS.modify(project);
		
		if(projectBO.verifyAmountChanged(project)) {
			budget = projectBO.recalculateBudget(project, dependency.getDependencyId());
			budgetRS = budgetWS.modify(budget);
		} else {
			budgetRS = true;
		}
		
		if(projectRS && budgetRS) {
			log.debug("Showing success alert");
			model.addAttribute("success", true);
		} else if (!projectRS || !budgetRS) {
			log.debug("Showing error alert");
			model.addAttribute("failure", true);
		}
		
	    model.addAttribute("project", project);
		return "proyectos";
	}	
	
	@RequestMapping("/eliminar")
	public String remove(ModelMap model, HttpSession session) {
		Dependency dependency = (Dependency) session.getAttribute("dependency");
		
	    model.addAttribute("projects", projectWS.findProjectDetailsByDependencyId(dependency.getDependencyId()));
		return "eliminar_proyectos";
	}

	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public String removeProject(ModelMap model, @RequestParam("projectId") Long projectId, HttpSession session) throws ParseException {
		
		Budget budget = null;
		boolean projectRS = false;
		boolean budgetRS = false; 

		Dependency dependency = (Dependency) session.getAttribute("dependency");

		Project project = projectWS.findById(projectId);
		log.debug("Project [" + project + "] will be removed");

		budget = projectBO.removeProjectAmountFromBudget(project, dependency.getDependencyId());		
		projectRS = projectWS.remove(project);

		if(budget.getAmount() > 0){
			budgetRS = budgetWS.modify(budget);
		} else {
			budgetRS = budgetWS.remove(budget);
		}
		
		if(projectRS && budgetRS) {
			log.debug("Showing success alert");
			model.addAttribute("success", true);
		} else if (!projectRS || !budgetRS) {
			log.debug("Showing error alert");
			model.addAttribute("failure", true);
		}

	    model.addAttribute("project", projectBO.getProjectAfterRemoval(projectId, dependency.getDependencyId()));
		return "eliminar_proyectos";
	}	
}
