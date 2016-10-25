package com.db.sysgob.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.Dependency;
import com.db.sysgob.entity.Expense;
import com.db.sysgob.entity.Project;
import com.db.sysgob.entity.ProjectSummary;
import com.db.sysgob.entity.Role;
import com.db.sysgob.entity.User;
import com.db.sysgob.service.BudgetService;
import com.db.sysgob.service.ExpenseService;
import com.db.sysgob.service.ProjectService;

@Controller
public class HomeController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private BudgetService budgetWS;
	
	@Autowired
	private ProjectService projectWS;
	
	@Autowired
	private ExpenseService expenseWS;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String showDashboard(ModelMap model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Role role = (Role) session.getAttribute("role");
		Dependency dependency = (Dependency) session.getAttribute("dependency");
		
		log.debug("Loading dashboard for User[" + user + 
				"] DependencyId: [" + dependency + "] Role: [" + role + "]");
		
		Budget budget = budgetWS.findByDependencyId(dependency.getDependencyId());
		Expense expense = expenseWS.findById(dependency.getDependencyId());
		List<ProjectSummary> projects = projectWS.findByDependencyId(dependency.getDependencyId());
		
		model.addAttribute("budgetAmount", budget != null ? budget.getAmount() : "$0.00");
		model.addAttribute("expenseAmount", expense != null ? expense.getTotalAmount() : "$0.00");
		model.addAttribute("projects", projects != null ? projects : null);
			
		return "index";
	}
}
