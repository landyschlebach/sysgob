package com.db.sysgob.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.repository.BudgetRepository;

@Controller
@RequestMapping("publico")
public class DashboardController {

	@Autowired
	private BudgetRepository budgetRepository;
	
	@Autowired
	private List<Budget> budgets;
	
	@RequestMapping(value = "presupuestos", method = RequestMethod.GET)
	public void loadBudgetsView() {
		budgets = budgetRepository.getBudgets();
	}
}
