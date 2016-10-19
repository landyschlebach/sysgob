package com.db.sysgob.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.service.BudgetService;

@Controller
public class DashboardController {
	private final String TAG = DashboardController.class.getSimpleName();
	private static final Logger log = LoggerFactory.getLogger("sysgob_log");

	@Autowired
	private BudgetService budgetWS;
	
	@RequestMapping(value = "/publico", method = RequestMethod.GET)
	public String loadBudgetsView(ModelMap model) {
		
		log.debug(TAG, "[Public user] Loading all dependencies' budgets");
		List<Budget> budgets = budgetWS.search();
		
		model.addAttribute("budgets", budgets);
		
		return "dashboard";
	}
}
