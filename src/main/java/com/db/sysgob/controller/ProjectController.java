package com.db.sysgob.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

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
import com.db.sysgob.entity.Project;
import com.db.sysgob.repository.CategoryRepository;
import com.db.sysgob.service.BudgetService;
import com.db.sysgob.service.ProjectService;
import com.db.sysgob.service.UserService;

@Controller
@RequestMapping("/proyectos")
public class ProjectController {
	private final String TAG = ProjectController.class.getSimpleName();
	private static final Logger log = LoggerFactory.getLogger("sysgob_log");

	@Autowired
	private ProjectBO projectBO;
	
	@Autowired
	private UserService userWS;
	
	@Autowired
	private ProjectService projectWS;
	
	@Autowired
	private BudgetService budgetWS;

	@RequestMapping("/consulta")
	public String viewProjects(ModelMap model) {
		Long dependencyId = (Long) model.get("dependencyId");
		
		List<Project> projects = projectWS.findById(dependencyId);
		log.debug(TAG, "Loading: Projects [" + projects +"]");
	    
	    model.addAttribute("projects", projects);	    
		return "proyectos";
	}
	
	@RequestMapping("/nuevo")
	public String showForm(ModelMap model) {	    
		return "formulario_proyectos";
	}

	@RequestMapping(value = "/nuevo", method = RequestMethod.POST)
	public String newProject(ModelMap model, 
			String name, String description, 
			Long amount, Long categoryId) throws ParseException {		
		
		boolean projectRS = false;
		boolean budgetRS = false;
		
		String view = "formulario_proyectos";
		Long dependencyId = (Long) model.get("dependencyId");
		Long userId = (Long) model.get("userId");
		
		Project project = new Project();
		project.setName(name);
		project.setDescription(description);
		project.setAmount(amount);
		project.setCategoryId(categoryId);
		project.setCreateDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		project.setUpdateDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		project.setUserId(userId);

		log.debug(TAG, "Creating new Project [" + project + "]");
		
		if(projectBO.verifyBudget(dependencyId) == null) {
			log.debug(TAG, "First project created for dependency[" + dependencyId + "]. Will create budget.");
			
			Budget budget = projectBO.initiateDefaultBudget(dependencyId);
			budgetRS = budgetWS.create(budget);
		} else {
			budgetRS = true;
		}
<<<<<<< HEAD
		
		if(project.getName() != null && project.getAmount() != null && project.getCategoryId()!= null){
			projectRS = projectWS.create(project);
		}
		else {
			projectRS = false;
		}
		
=======

		projectRS = projectWS.create(project);
>>>>>>> master
		
		if(projectRS && budgetRS) {
			log.debug(TAG, "Showing success alert");
			view = "redirect:/proyectos/clasificar";
		} else if (!projectRS || !budgetRS) {
			log.debug(TAG, "Showing error alert");
			model.addAttribute("failure", true);
		}

		model.addAttribute("project", project);
		model.addAttribute("projectName", project.getName());
		model.addAttribute("projectDescription", project.getDescription());

		return view;
	}	
	
	@RequestMapping(value = "/clasificar", method = RequestMethod.GET)
	public String classifyProject(ModelMap model) {
		
		CategoryRepository categoryRepository = new CategoryRepository();
		List<Category> categories = categoryRepository.getCategories();
		
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
			Long strategicClassification) throws ParseException {
		
		boolean projectRS = false;
		boolean budgetRS = false;
		
		Long dependencyId = (Long) model.get("dependencyId");
		
		Long points = projectBO.calculateCategory(riskClassification, othersClassification,
				otherImplications, financeClassification, strategicClassification);
		
		Project project = (Project) model.get("project");
		project = projectBO.defineCategory(project, points);
		Budget budget = projectBO.calculateBudget(project, dependencyId);
		
		projectRS = projectWS.modify(project);
		budgetRS = budgetWS.modify(budget);
		
		if(projectRS && budgetRS) {
			log.debug(TAG, "Showing success alert");
			model.addAttribute("success", true);
		} else if (!projectRS || !budgetRS) {
			log.debug(TAG, "Showing error alert");
			model.addAttribute("failure", true);
		}
		
		return "categorias";
	}
	
	@RequestMapping("/modificar/{projectId}")
	public String edit(ModelMap model, 
			@PathVariable @RequestParam("projectId") Long projectId) {
		
		Project project = projectWS.search(projectId);
	    
	    if(project.getProjectId() != 1) {
	    	model.addAttribute("prevId", project.getProjectId() - 1);
	    }
	    
	    if(project.getProjectId() < (projectWS.search().size() + 1)) {
	    	model.addAttribute("nextId", project.getProjectId() + 1);
	    }
	    
	    model.addAttribute("project", project);
		return "editar_proyectos";
	}

	@RequestMapping(value = "/modificar/{projectId}", method = RequestMethod.POST)
	public String editProject(ModelMap model,
			@PathVariable @RequestParam("projectId") Long projectId, 
			String name, String description, 
			Long amount, Long categoryId) throws ParseException {
		
		Budget budget = null;
		boolean projectRS = false;
		boolean budgetRS = false; 
		
		Long dependencyId = (Long) model.get("dependencyId");
		String user = (String) model.get("user");

		Project project = (Project) model.get("project");
		log.debug(TAG, "Project [" + project + "] has been modified");
		
		project.setName(name);
		project.setDescription(description);
		project.setAmount(amount);
		project.setCategoryId(categoryId);
		project.setUpdateDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		project.setUserId(userWS.findByUsername(user).getUserId());
		
		projectRS = projectWS.modify(project);
		
		if(projectBO.verifyAmountChanged(project)) {
			budget = projectBO.recalculateBudget(project, dependencyId);
			budgetRS = budgetWS.modify(budget);
		} else {
			budgetRS = true;
		}
		
		if(projectRS && budgetRS) {
			log.debug(TAG, "Showing success alert");
			model.addAttribute("success", true);
		} else if (!projectRS || !budgetRS) {
			log.debug(TAG, "Showing error alert");
			model.addAttribute("failure", true);
		}
		
	    model.addAttribute("projectId", project.getProjectId());
	    model.addAttribute("project", project);
	    
		return "proyectos";
	}	
	
	@RequestMapping("/eliminar/{projectId}")
	public String remove(ModelMap model, 
			@PathVariable @RequestParam("projectId") Long projectId) {
		
		Project project = projectWS.search(projectId);
	    
	    if(project.getProjectId() != 1) {
	    	model.addAttribute("prevId", project.getProjectId() - 1);
	    }
	    
	    if(project.getProjectId() < (projectWS.search().size() + 1)) {
	    	model.addAttribute("nextId", project.getProjectId() + 1);
	    }
	    
	    model.addAttribute("project", project);
		return "eliminar_proyectos";
	}

	@RequestMapping(value = "/eliminar/{projectId}", method = RequestMethod.POST)
	public String removeProject(ModelMap model,
			@PathVariable @RequestParam("projectId") Long projectId) throws ParseException {
		
		Budget budget = null;
		boolean projectRS = false;
		boolean budgetRS = false; 
		
		Long dependencyId = (Long) model.get("dependencyId");

		Project project = projectWS.search(projectId);
		log.debug(TAG, "Project [" + project + "] will be removed");

		budget = projectBO.removeProjectAmountFromBudget(project, dependencyId);		
		projectRS = projectWS.remove(project);

		if(budget.getAmount() > 0){
			budgetRS = budgetWS.modify(budget);
		} else {
			budgetRS = budgetWS.remove(budget);
		}
		
		if(projectRS && budgetRS) {
			log.debug(TAG, "Showing success alert");
			model.addAttribute("success", true);
		} else if (!projectRS || !budgetRS) {
			log.debug(TAG, "Showing error alert");
			model.addAttribute("failure", true);
		}
		
	    model.addAttribute("projectId", project.getProjectId() + 1);
	    
		return "eliminar_proyectos";
	}	
}
