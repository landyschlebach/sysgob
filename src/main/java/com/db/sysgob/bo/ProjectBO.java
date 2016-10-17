package com.db.sysgob.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.Category;
import com.db.sysgob.entity.Classification;
import com.db.sysgob.entity.Expense;
import com.db.sysgob.entity.Project;
import com.db.sysgob.repository.CategoryRepository;
import com.db.sysgob.service.BudgetService;
import com.db.sysgob.service.ProjectService;

@Component
public class ProjectBO {	
	private final String date = "31/12/2017";              
    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");   
    
	@Autowired
	private BudgetService budgetWS;
	
	@Autowired
	private ProjectService projectWS;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public Long calculateCategory(Classification classification) {
		Long total = classification.getRiskClassification() + 
				classification.getOthersClassification() + 
				classification.getFinanceClassification() + 
				classification.getOtherImplications() + 
				classification.getStrategicClassification();
		
		return total;
	}
	
	public Project defineCategory(Project project, Long points) {
		Long systemCategoryId = 0L;
		Category category = categoryRepository.getById(project.getCategoryId());
		/* 
		 * Get score of category & add the points of SYSGOB system classification
		 * calculate the average, to know the final points of the project.
		 */
		Long averageCategory = (category.getMinScore() + points) / 2;
		/* 
		 * Consult the DB to know
		 * the final category assigned
		 */
		systemCategoryId = categoryRepository.getCategoryByPoints(averageCategory);
		project.setCategoryId(systemCategoryId);
		
		return project;
	}
	
	public Budget verifyBudget(Long dependencyId) {
		return budgetWS.findById(dependencyId);
	}
	
	public Budget initiateDefaultBudget(Long dependencyId) throws ParseException {
		Budget budget = new Budget();
		
		budget.setAmount(0L);
		budget.setDependencyId(dependencyId);
		budget.setExpirationDate(new java.sql.Date(format.parse(date).getTime()));
		
		return budget;
	}
	
	public Budget calculateBudget(Project project, Long dependencyId) throws ParseException {
		Budget budget = null;
		Long amount = project.getAmount();
		
		/*
		 * Based on the category, define the percentage
		 *  	of budget the project will get.
		 */
		if (project.getCategoryId() == 2) {
			amount = (long) (amount * 0.75);
		} else if (project.getCategoryId() == 3) {
			amount = (long) (amount * 0.50);
		} else {
			amount = (long) (amount * 0.25);
		}
		/*
		 * Update entity of budget table
		 */
		if(budgetWS.findById(dependencyId) != null) {
			
			budget = budgetWS.findById(dependencyId);
			budget.setAmount(budget.getAmount() + amount);
		}
		
		return budget;
	}
	
	public Budget recalculateBudget(Project project, Long dependencyId) throws ParseException {
		Budget budget = null;
		Project prevProject = projectWS.search(project.getProjectId());
		Long amount = project.getAmount();
		
		/*
		 * Based on the category, define the percentage
		 *  	of budget the project will get.
		 */
		if (project.getCategoryId() == 2) {
			amount = (long) (amount * 0.75);
		} else if (project.getCategoryId() == 3) {
			amount = (long) (amount * 0.50);
		} else {
			amount = (long) (amount * 0.25);
		}
		/*
		 * Update entity of budget table
		 */
		if(budgetWS.findById(dependencyId) != null) {
			
			budget = budgetWS.findById(dependencyId);
			budget.setAmount((budget.getAmount() - prevProject.getAmount()) + amount);
		}
		
		return budget;
	}
	
	public boolean verifyAmountChanged(Project project) {
		boolean result = false;
		Project prevProjectInfo = projectWS.search(project.getProjectId());
		
		if(!prevProjectInfo.getAmount().equals(prevProjectInfo.getAmount())) {
			return true;
		}
		
		return result;
	}
}
