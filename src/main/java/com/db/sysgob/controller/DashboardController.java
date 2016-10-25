package com.db.sysgob.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.db.sysgob.service.BudgetService;
import com.google.gson.Gson;

@Controller
public class DashboardController {
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	private BudgetService budgetWS;
	
	@RequestMapping(value = "/publico")
	public String loadBudgetsView(ModelMap model, HttpSession session) {
		log.debug("[Public user] Loading all budgets");	
		
		model.addAttribute("budgets", budgetWS.getDashboardData());
		return "dashboard";
	}
	
    @RequestMapping(value = "/presupuestos/datos", produces="application/json")
    @ResponseBody
    public String getBudgetsData() {
    	log.debug("Loading data for Morris donut chart: " + budgetWS.getDashboardData());
    	
    	String json = new Gson().toJson(budgetWS.getDashboardData());
    	return json;
    }
}
