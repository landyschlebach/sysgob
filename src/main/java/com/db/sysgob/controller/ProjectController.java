package com.db.sysgob.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.db.sysgob.bo.ProjectBO;
import com.db.sysgob.bo.UserBO;
import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.Category;
import com.db.sysgob.entity.Classification;
import com.db.sysgob.entity.Expense;
import com.db.sysgob.entity.Project;
import com.db.sysgob.repository.CategoryRepository;
import com.db.sysgob.repository.ProjectRepository;
import com.db.sysgob.service.BudgetService;
import com.db.sysgob.service.ProjectService;
import com.db.sysgob.service.UserService;

@Controller
@RequestMapping("proyectos")
public class ProjectController {

	@Autowired
	private ProjectBO projectBO;
	
	@Autowired
	private UserService userWS;
	
	@Autowired
	private ProjectService projectWS;
	
	@Autowired
	private BudgetService budgetWS;

	@RequestMapping("consulta")
	public String viewProjects(ModelMap model, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId) {
		
		List<Project> projects = projectWS.findById(dependencyId);
		
		/* Basic User Info */	    
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);
	    
	    model.addAttribute("projects", projects);
	    
		return "proyectos";
	}
	
	@RequestMapping("nuevo")
	public String showForm(ModelMap model, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId) {
		
		/* Basic User Info */	    
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);
	    
		return "formulario_proyectos";
	}

	@RequestMapping(value = "nuevo", method = RequestMethod.POST, params={"project"})
	public String newProject(ModelMap model, 
			@ModelAttribute("project") Project project, 
			@ModelAttribute("classification") Classification classification, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId) throws ParseException {
		
		String view = "formulario_proyectos";
		boolean projectRS = false;
		boolean budgetRS = false;
		
		project.setCreateDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		project.setUpdateDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		project.setUserId(userWS.findByUsername(user).getUserId());
		
		if(projectBO.verifyBudget(dependencyId) == null) {
			Budget budget = projectBO.initiateDefaultBudget(dependencyId);
			budgetRS = budgetWS.create(budget);
		} else {
			budgetRS = true;
		}
		
		projectRS = projectWS.create(project);
		
		if(projectRS && budgetRS) {
			view = "redirect:/proyectos/clasificar";
		} else if (!projectRS || !budgetRS) {
			model.addAttribute("failure", true);
		}
		
		/* Basic User Info */	    
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);

		model.addAttribute("project", project);
		model.addAttribute("projectName", project.getName());
		model.addAttribute("projectDescription", project.getDescription());

		return view;
	}	
	
	@RequestMapping(value = "clasificar", method = RequestMethod.GET)
	public String classifyProject(ModelMap model, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId, 
			@ModelAttribute("project") Project project, 
			@ModelAttribute("projectName") String projectName,
			@ModelAttribute("projectDescription") String projectDescription) {
		
		CategoryRepository categoryRepository = new CategoryRepository();
		List<Category> categories = categoryRepository.getCategories();
		
		model.addAttribute("categoryHigh", categories.get(0).getMinScore());
		model.addAttribute("categoryMediumHigh", categories.get(1).getMinScore());
		model.addAttribute("categoryMediumLow", categories.get(2).getMinScore());
		model.addAttribute("categoryLow", categories.get(3).getMinScore());
		
		/* Basic User Info */	    
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);
	    
		model.addAttribute("project", project);
		model.addAttribute("projectName", project.getName());
		model.addAttribute("projectDescription", project.getDescription());
		
		return "categorias";
	}
	
	@RequestMapping(value = "clasificar", method = RequestMethod.POST, params={"classification"})
	public String classifyProject(ModelMap model, 
			@ModelAttribute("classification") Classification classification, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId, 
			@ModelAttribute("project") Project project, 
			@ModelAttribute("projectName") String projectName,
			@ModelAttribute("projectDescription") String projectDescription) throws ParseException {
		
		boolean projectRS = false;
		boolean budgetRS = false;
		
		Long points = projectBO.calculateCategory(classification);
		project = projectBO.defineCategory(project, points);
		Budget budget = projectBO.calculateBudget(project, dependencyId);
		
		projectRS = projectWS.modify(project);
		budgetRS = budgetWS.modify(budget);
		
		if(projectRS && budgetRS) {
			model.addAttribute("success", true);
		} else if (!projectRS || !budgetRS) {
			model.addAttribute("failure", true);
		}
		
		/* Basic User Info */	    
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);
		
		return "categorias";
	}
	
	@RequestMapping("modificar/{projectId}")
	public String edit(ModelMap model, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId,
			@PathVariable @ModelAttribute("projectId") Long projectId) {
		
		Project project = projectWS.search(projectId);
		
		/* Basic User Info */	    
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);
	    model.addAttribute("project", project);
	    
	    if(project.getProjectId() != 1) {
	    	model.addAttribute("prevId", project.getProjectId() - 1);
	    }
	    
	    if(project.getProjectId() < (projectWS.search().size() + 1)) {
	    	model.addAttribute("nextId", project.getProjectId() + 1);
	    }
	    
		return "editar_proyectos";
	}

	@RequestMapping(value = "modificar/{projectId}", method = RequestMethod.POST, params={"expense"})
	public String editProject(ModelMap model, @ModelAttribute("project") Project project, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId,
			@PathVariable @ModelAttribute("projectId") Long projectId) throws ParseException {
		
		Budget budget = null;
		boolean projectRS = false;
		boolean budgetRS = false; 
		
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
			model.addAttribute("success", true);
		} else if (!projectRS || !budgetRS) {
			model.addAttribute("failure", true);
		}
		
		/* Basic User Info */	    
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);
	    model.addAttribute("projectId", project.getProjectId());
	    model.addAttribute("project", project);
	    
		return "proyectos";
	}	
	
	
	/* Test: Health check of nemonicoDB */
	@RequestMapping("probar_conexion")
	public String checkConnection() {
		ProjectRepository projectRepository = new ProjectRepository();
		return projectRepository.healthcheck();
	}
}
