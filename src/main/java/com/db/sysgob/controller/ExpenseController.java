package com.db.sysgob.controller;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.db.sysgob.bo.ExpenseBO;
import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.Expense;
import com.db.sysgob.service.BudgetService;
import com.db.sysgob.service.ExpenseService;

@Controller
@RequestMapping("/gastos")
public class ExpenseController {
	private final String TAG = ExpenseController.class.getSimpleName();
	private static final Logger log = LoggerFactory.getLogger("sysgob_log");
	
	private final String NEW = "new";
	private final String EDIT = "edit";
	
	@Autowired
	private ExpenseBO expenseBO;
	
	@Autowired
	private ExpenseService expenseWS;
	
	@Autowired
	private BudgetService budgetWS;
	
	@RequestMapping("/consulta")
	public String viewExpenses(ModelMap model, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId) {
		
		Expense expense = expenseWS.findById(dependencyId);
		log.debug(TAG, "Loading: Expense [" + expense + "]");
		
		/* Basic User Info */	    
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);
	    
	    model.addAttribute("expense", expense);
	    
		return "gastos";
	}
	
	@RequestMapping("/nuevo")
	public String form(ModelMap model, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId) {
		
		/* Basic User Info */	    
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);
	    
		return "formulario_gastos";
	}

	@RequestMapping(value = "/nuevo", method = RequestMethod.POST, params={"expense"})
	public String newExpense(ModelMap model, @ModelAttribute("expense") Expense expense, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId) {
		
		log.debug(TAG, "Creating new expense [" + expense + "]");
		Budget budget = null;
		boolean expenseRS = false;
		boolean budgetRS = false; 
		
		if(expenseBO.verifyBudget(dependencyId) != null) {
			/* Only if budget already exists do the following: 
			 */
			if(expenseWS.findById(dependencyId) != null) { 
				log.debug(TAG, "Expense for dependency [" + dependencyId + "] already existed. Will modify it.");
				
				expense = expenseBO.calculateTotal(expense, dependencyId);
				expense.setUpdateDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
				
				expenseRS = expenseWS.modify(expense);
				
			} else {
				log.debug(TAG, "Expense for dependency [" + dependencyId + "] didn't exist. Creating it.");
				expense = expenseBO.calculateTotal(expense, dependencyId);
				expense.setCreateDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
				expense.setUpdateDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
				expense.setDependencyId(dependencyId);
				
				expenseRS = expenseWS.create(expense);
			}
			
			log.debug(TAG, "Reflecting total expense amount in budget amount");
			budget = expenseBO.getBudgetReduced(dependencyId, expense.getTotalAmount(), NEW);
			budgetRS = budgetWS.modify(budget);
			
			if(expense.getName() != null && expense.getTotalAmount() != null){
				expenseRS = expenseWS.create(expense);
			}
			else {
				expenseRS = false;
		}
		
		if(expenseRS && budgetRS) {
			log.debug(TAG, "Showing success alert");
			model.addAttribute("success", true);
		} else if (!expenseRS || !budgetRS) {
			log.debug(TAG, "Showing error alert");
			model.addAttribute("failure", true);
		}
		
		/* Basic User Info */	    
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);
	    
		return "formulario_gastos";
	}	

	@RequestMapping("/modificar")
	public String edit(ModelMap model, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId) {
		
		Expense expense = expenseWS.findById(dependencyId);
		
		/* Basic User Info */	    
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);
	    model.addAttribute("expense", expense);
	    
		return "editar_gastos";
	}

	@RequestMapping(value = "/modificar", method = RequestMethod.POST, params={"expense"})
	public String editExpense(ModelMap model, @ModelAttribute("expense") Expense expense, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId) {
		
		Budget budget = null;
		boolean expenseRS = false;
		boolean budgetRS = false; 

		log.debug(TAG, "Expense for dependency [" + dependencyId + "] has been modified");
		expense.setUpdateDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		expenseRS = expenseWS.modify(expense);
		
		if(expenseBO.verifyAmountChanged(expense)) {
			budget = expenseBO.getBudgetReduced(dependencyId, expense.getTotalAmount(), EDIT);
			budgetRS = budgetWS.modify(budget);
		} else {
			budgetRS = true;
		}

		if(expenseRS && budgetRS) {
			log.debug(TAG, "Showing success alert");
			model.addAttribute("success", true);
		} else if (!expenseRS || !budgetRS) {
			log.debug(TAG, "Showing error alert");
			model.addAttribute("failure", true);
		}
		
		/* Basic User Info */	    
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);
	    
		return "editar_gastos";
	}	
}
