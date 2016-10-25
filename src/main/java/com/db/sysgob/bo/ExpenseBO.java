package com.db.sysgob.bo;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.Expense;
import com.db.sysgob.service.BudgetService;
import com.db.sysgob.service.ExpenseService;

@Component
public class ExpenseBO {
	private static final Logger log = LoggerFactory.getLogger(ExpenseBO.class);
	
	private final String NEW = "new";
	private final String EDIT = "edit";
	
	@Autowired
	private ExpenseService expenseWS;
	
	@Autowired
	private BudgetService budgetWS;
	
	public Expense setupModifiedExpense(Long totalAmount, Long dependencyId) {
		Expense expense = expenseWS.findById(dependencyId);
		
		if(totalAmount != null) {
			expense.setTotalAmount(totalAmount);
			expense.setUpdateDate(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
		}
		
		return expense;
	}
	
	public Expense calculateTotal(Expense expense, Long dependencyId) {
		log.debug("Calculating final expense amount for secretaryId " + dependencyId);
		Expense prevExpense = expenseWS.findById(dependencyId);
		
		log.debug("Previous expense: $" + prevExpense.getTotalAmount());
		log.debug("New expense: $" + expense.getTotalAmount());
		Long totalExpenseAmount = prevExpense.getTotalAmount() + expense.getTotalAmount();
		expense.setTotalAmount(totalExpenseAmount);
		
		log.debug("Total expense: $" + expense.getTotalAmount());
		return expense;
	}
	
	public boolean verifyAmountChanged(Long totalAmount, Long dependencyId) {
		boolean result = false;
		
		log.debug("Verifying expense amount was modified. ");
		Expense prevExpense = expenseWS.findById(dependencyId);
		
		if(!prevExpense.getTotalAmount().equals(dependencyId)) {
			log.debug("[CHANGE] in: " + prevExpense + ", Modified Total Amount $" + totalAmount);
			return true;
		} else {
			log.debug( "[NO CHANGE]" + prevExpense);
		}
		
		return result;
	}
	
	public Budget verifyBudget(Long dependencyId) {
		log.debug("Verifying budget for [DependencyId=" + dependencyId + "]");
		return budgetWS.findByDependencyId(dependencyId);
	}
	
	public Budget getBudgetReduced(Long dependencyId, Long expenseAmount, String value) {
		log.debug("Calculate budget reduction");
		
		Budget budget = verifyBudget(dependencyId);
		Long budgetReduction = 0L;
		
		if(value.equals(NEW)) {

			log.debug("New expense added to system");
			budgetReduction = budget.getAmount() - expenseAmount;
			log.debug(budget + " to be modified");

		} else if (value.equals(EDIT)) {
			log.debug("Previous expense amount is being modified");
			Expense expense = expenseWS.findById(dependencyId);
			Long lastExpense = expense.getTotalAmount();

			log.debug("Expense [" + expense.getExpenseId() + "] "
					+ "Previous amount: $" + lastExpense + " "
					+ "Modified expense amount: $" + expenseAmount);
			
			budgetReduction = (budget.getAmount() + lastExpense) - expenseAmount;
		}

		log.debug("After calculations: Total budget $" + budgetReduction);
		budget.setAmount(budgetReduction);
		return budget;
	}
	
	public Budget calculateBudgetWithoutExpenses(Long dependencyId) {
		log.debug("Removing expenses. Calculating budget.");
		
		Budget budget = verifyBudget(dependencyId);
		Expense expense = expenseWS.findById(dependencyId);
		Long budgetAmount = 0L;

		log.debug(expense.toString());
		log.debug("Calculating " + budget);
		
		budgetAmount = budget.getAmount() + expense.getTotalAmount();

		log.debug("After calculations: Total budget $" + budgetAmount);
		budget.setAmount(budgetAmount);
		return budget;
	}
}
