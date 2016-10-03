package com.db.sysgob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.db.sysgob.entity.Expense;
import com.db.sysgob.repository.ExpenseRepository;
import com.db.sysgob.repository.ResourcesSpentRepository;

@Controller
@RequestMapping("gastos")
public class ExpenseController {

	@Autowired
	private Expense expense;
	
	@Autowired
	private ResourcesSpent resourcesSpent;
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private ResourcesSpentRepository rsRepository;
	
	@RequestMapping("nuevo")
	public String form() {
		return "";
	}

	@RequestMapping("nuevo?nombre=&monto=&recurso=")
	public String newProject() {
		return "";
	}	
}
