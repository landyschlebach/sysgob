package com.db.sysgob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.db.sysgob.bo.BudgetBO;
import com.db.sysgob.bo.ProjectBO;
import com.db.sysgob.bo.UserBO;
import com.db.sysgob.entity.Classification;
import com.db.sysgob.entity.Project;

@Controller
@RequestMapping("proyectos")
public class ProjectController {

	@Autowired
	private UserBO userBO;

	@Autowired
	private ProjectBO projectBO;
	
	@Autowired
	private BudgetBO budgetBO;
	
	@RequestMapping("formulario")
	public String form(ModelMap model) {
		return "formulario_proyectos";
	}

	@RequestMapping(value = "nuevo", method = RequestMethod.POST)
	public String newProject(ModelMap model, @ModelAttribute("project") Project project, @ModelAttribute("classification") Classification classification) {
		Long points = projectBO.calculateCategory(classification);
		project = projectBO.defineCategory(project, points);
		
		budgetBO.defineBudget(project, userBO.getDependencyOfUser(project.getUserId()));
		
		return "formulario_proyectos";
	}	
	
	@RequestMapping("probar_conexion")
	public String checkConnection() {
		return projectRepository.healthcheck();
	}
}
