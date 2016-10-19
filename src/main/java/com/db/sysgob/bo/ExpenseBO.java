package com.db.sysgob.bo;

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
	private final String TAG = ExpenseBO.class.getSimpleName();
	private static final Logger log = LoggerFactory.getLogger("sysgob_log");
	
	private final String NEW = "new";
	private final String EDIT = "edit";
	
	@Autowired
	private ExpenseService expenseWS;
	
	@Autowired
	private BudgetService budgetWS;
	
	public Expense calculateTotal(Expense expense, Long dependencyId) {
		log.debug(TAG, "Calculating final expense amount for secretaryId " + dependencyId);
		Expense prevExpense = expenseWS.findById(dependencyId);
		
		log.debug(TAG, "Previous expense: $" + prevExpense.getTotalAmount());
		log.debug(TAG, "New expense: $" + expense.getTotalAmount());
		Long totalExpenseAmount = prevExpense.getTotalAmount() + expense.getTotalAmount();
		expense.setTotalAmount(totalExpenseAmount);
		
		log.debug(TAG, "Total expense: $" + expense.getTotalAmount());
		return expense;
	}
	
	public boolean verifyAmountChanged(Expense expense) {
		boolean result = false;
		
		log.debug(TAG, "Verifying expense amount was modified [ExpenseId=" + expense.getExpenseId() + "]");
		Expense prevExpense = expenseWS.findById(expense.getDependencyId());
		
		if(!prevExpense.getTotalAmount().equals(expense.getTotalAmount())) {
			log.debug(TAG, "Expense " + expense.getExpenseId() + " changed");
			return true;
		} else {
			log.debug(TAG, "Expense " + expense.getExpenseId() + " NOT changed");
		}
		
		return result;
	}
	
	public Budget verifyBudget(Long dependencyId) {
		log.debug(TAG, "Verifying budget [" + dependencyId + "]");
		return budgetWS.findById(dependencyId);
	}
	
	public Budget getBudgetReduced(Long dependencyId, Long expenseAmount, String value) {
		log.debug(TAG, "Calculate budget reduction");
		
		Budget budget = verifyBudget(dependencyId);
		Long budgetReduction = 0L;
		
		if(value.equals(NEW)) {

			log.debug(TAG, "New expense added to system");
			budgetReduction = budget.getAmount() - expenseAmount;

		} else if (value.equals(EDIT)) {
			log.debug(TAG, "Previous expense amount is being modified");
			Expense expense = expenseWS.findById(dependencyId);
			Long lastExpense = expense.getTotalAmount();

			log.debug(TAG, "Expense [" + expense.getExpenseId() + "] "
					+ "Previous amount: $" + lastExpense + " "
					+ "Modified expense amount: $" + expense.getTotalAmount());
			
			budgetReduction = (budget.getAmount() + lastExpense) - expenseAmount;
		}

		log.debug(TAG, "After calculations: Total budget $" + budgetReduction);
		budget.setAmount(budgetReduction);
		return budget;
	}
}
