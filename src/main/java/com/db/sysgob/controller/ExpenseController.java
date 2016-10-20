package com.db.sysgob.controller;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	public String viewExpenses(ModelMap model) {
		Long dependencyId = (Long) model.get("dependencyId");
		
		Expense expense = expenseWS.findById(dependencyId);
		log.debug(TAG, "Loading: Expense [" + expense + "]");
	    
	    model.addAttribute("expense", expense);	    
		return "gastos";
	}
	
	@RequestMapping("/nuevo")
	public String form(ModelMap model) {
		return "formulario_gastos";
	}

	@RequestMapping(value = "/nuevo", method = RequestMethod.POST)
	public String newExpense(ModelMap model, String name, Long totalAmount) {
		Long dependencyId = (Long) model.get("dependencyId");
		
		Expense expense = null;
		Budget budget = null;
		boolean expenseRS = false;
		boolean budgetRS = false; 
		
		
		if(expenseBO.verifyBudget(dependencyId) != null) {
			/* Only if budget already exists do the following: 
			 */
			if(expenseWS.findById(dependencyId) != null) { 
				log.debug(TAG, "Expense for dependency [" + dependencyId + "] already existed. Will modify it.");
				
				expense = expenseWS.findById(dependencyId);
				expense.setName(name);
				expense.setTotalAmount(totalAmount);
				expense.setUpdateDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
				
				expense = expenseBO.calculateTotal(expense, dependencyId);
				expenseRS = expenseWS.modify(expense);
				
			} else {
				log.debug(TAG, "Expense for dependency [" + dependencyId + "] didn't exist. Creating it.");

				expense = new Expense();
				expense.setName(name);
				expense.setTotalAmount(totalAmount);
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
	    
		return "formulario_gastos";
	}	

	@RequestMapping("/modificar")
	public String edit(ModelMap model) {
		Long dependencyId = (Long) model.get("dependencyId");		
		Expense expense = expenseWS.findById(dependencyId);

	    model.addAttribute("expense", expense);	    
		return "editar_gastos";
	}

	@RequestMapping(value = "/modificar", method = RequestMethod.POST)
	public String editExpense(ModelMap model, String name, Long totalAmount) {
		Long dependencyId = (Long) model.get("dependencyId");
		
		Budget budget = null;
		boolean expenseRS = false;
		boolean budgetRS = false; 

		log.debug(TAG, "Expense for dependency [" + dependencyId + "] has been modified");
		Expense expense = expenseWS.findById(dependencyId);
		
		expense.setName(name);
		expense.setTotalAmount(totalAmount);
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
	    
		return "editar_gastos";
	}	
	
	@RequestMapping("/eliminar")
	public String remove(ModelMap model) {
		Long dependencyId = (Long) model.get("dependencyId");		
		Expense expense = expenseWS.findById(dependencyId);

	    model.addAttribute("expense", expense);	    
		return "eliminar_gastos";
	}

	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public String removeExpense(ModelMap model, Long expenseId) {
		Long dependencyId = (Long) model.get("dependencyId");
		
		Budget budget = null;
		boolean expenseRS = false;
		boolean budgetRS = false; 

		log.debug(TAG, "Expense for dependency [" + dependencyId + "] will be removed");
		Expense expense = expenseWS.findById(dependencyId);
		budget = expenseBO.getBudgetReduced(dependencyId, expense.getTotalAmount(), EDIT);

		expenseRS = expenseWS.remove(expense);
		budgetRS = budgetWS.modify(budget);

		if(expenseRS && budgetRS) {
			log.debug(TAG, "Showing success alert");
			model.addAttribute("success", true);
		} else if (!expenseRS || !budgetRS) {
			log.debug(TAG, "Showing error alert");
			model.addAttribute("failure", true);
		}
	    
		return "eliminar_gastos";
	}	
}
