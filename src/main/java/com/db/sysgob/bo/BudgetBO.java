package com.db.sysgob.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.Project;
import com.db.sysgob.repository.BudgetRepository;

@Component
public class BudgetBO {

	@Autowired
	private BudgetRepository budgetRepository;
	
	public void defineBudget(Project project, Long dependencyId) {
		Budget budget;
		Long amount = 0L;
		amount = project.getAmount();
		
		if (project.getCategoryId() == 2 ) {
			amount = amount * 0.75;
		} else if (project.getCategoryId() == 3 ) {
			amount = amount * 0.50;
		} else {
			amount = amount * 0.25;
		}
		
		if(budgetRepository.getBudgetByDependencyId(dependencyId)) {
			budget = budgetRepository.getBudgetByDependencyId(dependencyId);
			budget.setAmount(budget.getAmount() + amount);
			budgetRepository.updateBudget(budget);
		} else {
			budget = new Budget();
			
			budget.setAmount(amount);
			budget.setDependencyId(dependencyId);
			budget.setExpirationDate("31 12 2017");
		
			budgetRepository.createBudget(budget);
		}
	}
}
