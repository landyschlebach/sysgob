package com.db.sysgob.controller;

import java.util.Calendar;

import javax.persistence.Id;
import javax.servlet.http.HttpSession;

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
import com.db.sysgob.entity.Dependency;
import com.db.sysgob.entity.Expense;
import com.db.sysgob.service.BudgetService;
import com.db.sysgob.service.ExpenseService;

@Controller
@RequestMapping("/gastos")
public class ExpenseController {
	private static final Logger log = LoggerFactory.getLogger(ExpenseController.class);
	
	private final String NEW = "new";
	private final String EDIT = "edit";
	
	@Autowired
	private ExpenseBO expenseBO;
	
	@Autowired
	private ExpenseService expenseWS;
	
	@Autowired
	private BudgetService budgetWS;
	
	@RequestMapping("/consulta")
	public String viewExpenses(ModelMap model, HttpSession session) {
		Dependency dependency = (Dependency) session.getAttribute("dependency");
		
		Expense expense = expenseWS.findById(dependency.getDependencyId());
		log.debug("Loading: Expense [" + expense + "]");
	    
	    model.addAttribute("expense", expense);	    
		return "gastos";
	}
	
	@RequestMapping("/nuevo")
	public String form(ModelMap model, HttpSession session) {
		return "formulario_gastos";
	}

	@RequestMapping(value = "/nuevo", method = RequestMethod.POST)
	public String newExpense(ModelMap model, String name, Long totalAmount, HttpSession session) {
		Dependency dependency = (Dependency) session.getAttribute("dependency");
		
		Expense expense = null;
		Budget budget = null;
		boolean expenseRS = false;
		boolean budgetRS = false; 
		
		
		if(expenseBO.verifyBudget(dependency.getDependencyId()) != null) {
			/* Only if budget already exists do the following: 
			 */
			if(expenseWS.findById(dependency.getDependencyId()) != null) { 
				log.debug("Expense for " + dependency + " already existed. Will modify it.");
				
				expense = expenseWS.findById(dependency.getDependencyId());
				expense.setName(name);
				expense.setTotalAmount(totalAmount);
				expense.setUpdateDate(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
				
				expense = expenseBO.calculateTotal(expense, dependency.getDependencyId());
				expenseRS = expenseWS.modify(expense);
				
			} else {
				
				if(name != null && totalAmount != null){
					log.debug("Expense for " + dependency + " didn't exist. Creating it.");
					
					expense = new Expense();
					expense.setName(name);
					expense.setTotalAmount(totalAmount);
					expense.setCreateDate(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
					expense.setUpdateDate(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
					expense.setDependencyId(dependency.getDependencyId());
					
					expenseRS = expenseWS.create(expense);
				} else {
					expenseRS = false;
				}
			}
			
			log.debug("Reflecting total expense amount in budget amount");
			budget = expenseBO.getBudgetReduced(dependency.getDependencyId(), expense.getTotalAmount(), NEW);
			budgetRS = budgetWS.modify(budget);
		}
		
		if(expenseRS && budgetRS) {
			log.debug("Showing success alert");
			model.addAttribute("success", true);
		} else if (!expenseRS || !budgetRS) {
			log.debug("Showing error alert");
			model.addAttribute("failure", true);
		} else if (budgetRS && !expenseRS) {
			log.debug("Showing warning alert");
			model.addAttribute("dataMissing", true);
		}
	    
		return "formulario_gastos";
	}	

	@RequestMapping("/modificar")
	public String edit(ModelMap model, HttpSession session) {
		Dependency dependency = (Dependency) session.getAttribute("dependency");
		Expense expense = expenseWS.findById(dependency.getDependencyId());
		log.debug("Loading: " + expense + " to edit.");
	    
	    model.addAttribute("expense", expense);	     
		return "editar_gastos";
	}

	@RequestMapping(value = "/modificar", method = RequestMethod.POST)
	public String editExpense(ModelMap model, Long totalAmount, HttpSession session) {
		Dependency dependency = (Dependency) session.getAttribute("dependency");

		boolean expenseRS = false;
		boolean budgetRS = false; 

		log.debug("Expense for " + dependency + " has been modified");
		
		if(expenseBO.verifyAmountChanged(totalAmount, dependency.getDependencyId())) {
			Budget budget = expenseBO.getBudgetReduced(dependency.getDependencyId(), totalAmount, EDIT);
			budgetRS = budgetWS.modify(budget);
			
			Expense expense = expenseBO.setupModifiedExpense(totalAmount, dependency.getDependencyId());
			expenseRS = expenseWS.modify(expense);
			model.addAttribute("expense", expense);	  
		} else {
			budgetRS = true;
		}

		if(expenseRS && budgetRS) {
			log.debug("Showing success alert");
			model.addAttribute("success", true);
		} else if (!expenseRS || !budgetRS) {
			log.debug("Showing error alert");
			model.addAttribute("failure", true);
		}
	    
		return "editar_gastos";
	}	
	
	@RequestMapping("/eliminar")
	public String remove(ModelMap model, HttpSession session) {
		Dependency dependency = (Dependency) session.getAttribute("dependency");	
		Expense expense = expenseWS.findById(dependency.getDependencyId());

	    model.addAttribute("expense", expense);	    
		return "eliminar_gastos";
	}

	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public String removeExpense(ModelMap model, HttpSession session) {
		Dependency dependency = (Dependency) session.getAttribute("dependency");
		
		boolean expenseRS = false;
		boolean budgetRS = false; 

		log.debug("Expense for " + dependency + " will be removed");
		Expense expense = expenseWS.findById(dependency.getDependencyId());
		Budget budget = expenseBO.calculateBudgetWithoutExpenses(dependency.getDependencyId());
		
		expenseRS = expenseWS.remove(expense);
		budgetRS = budgetWS.modify(budget);

		if(expenseRS && budgetRS) {
			log.debug("Showing success alert");
			model.addAttribute("success", true);
		} else if (!expenseRS || !budgetRS) {
			log.debug("Showing error alert");
			model.addAttribute("failure", true);
		}
	    
		return "eliminar_gastos";
	}	
}
