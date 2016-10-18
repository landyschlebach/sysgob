package com.db.sysgob.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.Expense;
import com.db.sysgob.service.BudgetService;
import com.db.sysgob.service.ExpenseService;

@Component
public class ExpenseBO {
	private final String NEW = "new";
	private final String EDIT = "edit";
	
	@Autowired
	private ExpenseService expenseWS;
	
	@Autowired
	private BudgetService budgetWS;
	
	public Expense calculateTotal(Expense expense, Long dependencyId) {
		Expense prevExpense = expenseWS.findById(dependencyId);
		
		Long totalExpenseAmount = prevExpense.getTotalAmount() + expense.getTotalAmount();
		expense.setTotalAmount(totalExpenseAmount);
		
		return expense;
	}
	
	public boolean verifyAmountChanged(Expense expense) {
		boolean result = false;
		Expense prevExpense = expenseWS.findById(expense.getDependencyId());
		
		if(!prevExpense.getTotalAmount().equals(expense.getTotalAmount())) {
			return true;
		}
		
		return result;
	}
	
	public Budget verifyBudget(Long dependencyId) {
		return budgetWS.findById(dependencyId);
	}
	
	public Budget getBudgetReduced(Long dependencyId, Long expenseAmount, String value) {
		Budget budget = verifyBudget(dependencyId);
		Long budgetReduction = 0L;
		
		if(value.equals(NEW)) {

			budgetReduction = budget.getAmount() - expenseAmount;

		} else if (value.equals(EDIT)) {
			Expense expense = expenseWS.findById(dependencyId);
			Long lastExpense = expense.getTotalAmount();
			
			budgetReduction = (budget.getAmount() + lastExpense) - expenseAmount;
		}
		
		budget.setAmount(budgetReduction);
		return budget;
	}
}
