package com.db.sysgob.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.service.BudgetService;

@Controller
public class DashboardController {

	@Autowired
	private BudgetService budgetWS;
	
	@Autowired
	private List<Budget> budgets;
	
	@RequestMapping(value = "publico", method = RequestMethod.GET)
	public String loadBudgetsView(ModelMap model) {
		budgets = budgetWS.search();
		
		model.addAttribute("budgets", budgets);
		
		return "dashboard";
	}
}
