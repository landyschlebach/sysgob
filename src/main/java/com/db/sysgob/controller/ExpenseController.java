package com.db.sysgob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.db.sysgob.bo.ProjectBO;
import com.db.sysgob.entity.Expense;
import com.db.sysgob.entity.ResourceSpentByDependency;
import com.db.sysgob.repository.ExpenseRepository;
import com.db.sysgob.repository.ResourcesSpentRepository;

@Controller
@RequestMapping("gastos")
public class ExpenseController {

	@Autowired
	private Expense expense;
	
	@Autowired
	private ResourceSpentByDependency resourcesSpent;
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private ResourcesSpentRepository rsRepository;
	
	@Autowired
	private ProjectBO projectBO;
	
	@RequestMapping("formulario")
	public String form(ModelMap model) {
		return "formulario_gastos";
	}

	@RequestMapping(value = "nuevo", method = RequestMethod.POST)
	public String newProject(ModelMap model, @ModelAttribute("gastos") Expense expense) {
		return "formulario_gastos";
	}	
}
