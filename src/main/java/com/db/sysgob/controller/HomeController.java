package com.db.sysgob.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.Expense;
import com.db.sysgob.entity.Project;
import com.db.sysgob.service.BudgetService;
import com.db.sysgob.service.ExpenseService;
import com.db.sysgob.service.ProjectService;

@Controller
public class HomeController {
	private final String TAG = HomeController.class.getSimpleName();
	private static final Logger log = LoggerFactory.getLogger("sysgob_log");

	@Autowired
	private BudgetService budgetWS;
	
	@Autowired
	private ProjectService projectWS;
	
	@Autowired
	private ExpenseService expenseWS;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String showDashboard(ModelMap model, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId) {
		
		log.debug(TAG, "Loading dashboard for User[" + user + 
				"] DependencyId: [" + dependencyId + "] RoleId: [" + roleId + "]");
		
		Budget budget = budgetWS.findById(dependencyId);
		Expense expense = expenseWS.findById(dependencyId);
		List<Project> projects = projectWS.findById(dependencyId);
		
		/* Basic User Info */
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);
		
		model.addAttribute("budgetAmount", budget.getAmount());
		model.addAttribute("expenseAmount", expense.getTotalAmount());
		model.addAttribute("projects", projects);
			
		return "index";
	}
}
