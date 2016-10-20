package com.db.sysgob.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.Category;
import com.db.sysgob.entity.Project;
import com.db.sysgob.repository.CategoryRepository;
import com.db.sysgob.service.BudgetService;
import com.db.sysgob.service.ProjectService;

@Component
public class ProjectBO {	
	private final String TAG = ProjectBO.class.getSimpleName();
	private static final Logger log = LoggerFactory.getLogger("sysgob_log");
	
	private final String date = "31/12/2017";              
    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");   
    
	@Autowired
	private BudgetService budgetWS;
	
	@Autowired
	private ProjectService projectWS;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public Long calculateCategory(Long riskClassification, 
			Long othersClassification, Long otherImplications, 
			Long financeClassification, Long strategicClassification) {
		
		log.debug(TAG, "Calculating System SYSGOB Category");
		Long total = riskClassification + 
					othersClassification + 
					financeClassification + 
					otherImplications + 
					strategicClassification;
		
		log.debug(TAG, "Total category points: " + total);
		return total;
	}
	
	public Project defineCategory(Project project, Long points) {
		log.debug(TAG, "Defining Project[" + project.getProjectId() + "] category");
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
		log.debug(TAG, "Project[" + project.getProjectId() + "] final category: " + project.getCategoryId());
		
		return project;
	}
	
	public Budget verifyBudget(Long dependencyId) {
		log.debug(TAG, "Verifying budget [" + dependencyId + "]");
		return budgetWS.findById(dependencyId);
	}
	
	public Budget initiateDefaultBudget(Long dependencyId) throws ParseException {
		log.debug(TAG, "Making default budget");
		
		Budget budget = new Budget();
		
		budget.setAmount(0L);
		budget.setDependencyId(dependencyId);
		budget.setExpirationDate(new java.sql.Date(format.parse(date).getTime()));
		
		return budget;
	}
	
	public Budget calculateBudget(Project project, Long dependencyId) throws ParseException {
		log.debug(TAG, "[Initial] Calculating budget");
		
		Budget budget = null;
		Long amount = project.getAmount();
		
		/*
		 * Based on the category, define the percentage
		 *  	of budget the project will get.
		 */

		log.debug(TAG, "Project Category=[" + project.getCategoryId() + "]");
		if (project.getCategoryId() == 2) {
			log.debug(TAG, "Project [" + project.getProjectId() + "] is assign 75% of $" + project.getAmount());
			amount = (long) (amount * 0.75);
			
			log.debug(TAG, "Project [" + project.getProjectId() + "] total budget amount: $" + amount);
		} else if (project.getCategoryId() == 3) {
			log.debug(TAG, "Project [" + project.getProjectId() + "] is assign 50% of $" + project.getAmount());
			amount = (long) (amount * 0.50);
			
			log.debug(TAG, "Project [" + project.getProjectId() + "] total budget amount: $" + amount);
		} else {
			log.debug(TAG, "Project [" + project.getProjectId() + "] is assign 25% of $" + project.getAmount());
			amount = (long) (amount * 0.25);
			
			log.debug(TAG, "Project [" + project.getProjectId() + "] total budget amount: $" + amount);
		}
		/*
		 * Update entity of budget table
		 */
		if(budgetWS.findById(dependencyId) != null) {
			
			budget = budgetWS.findById(dependencyId);
			budget.setAmount(budget.getAmount() + amount);
			log.debug(TAG, "Budget for dependencyId[" + dependencyId + "] - $" + budget.getAmount());
		}
		
		return budget;
	}
	
	public Budget recalculateBudget(Project project, Long dependencyId) throws ParseException {
		log.debug(TAG, "Project amount or category is being modified: [Recalculating Budget]");
		
		Budget budget = null;
		Project prevProject = projectWS.search(project.getProjectId());
		Long amount = project.getAmount();
		
		/*
		 * Based on the category, define the percentage
		 *  	of budget the project will get.
		 */
		log.debug(TAG, "Project Category=[" + project.getCategoryId() + "]");
		if (project.getCategoryId() == 2) {
			log.debug(TAG, "Project [" + project.getProjectId() + "] is assign 75% of $" + project.getAmount());
			amount = (long) (amount * 0.75);
			
			log.debug(TAG, "Project [" + project.getProjectId() + "] total budget amount: $" + amount);
		} else if (project.getCategoryId() == 3) {
			log.debug(TAG, "Project [" + project.getProjectId() + "] is assign 50% of $" + project.getAmount());
			amount = (long) (amount * 0.50);
			
			log.debug(TAG, "Project [" + project.getProjectId() + "] total budget amount: $" + amount);
		} else {
			log.debug(TAG, "Project [" + project.getProjectId() + "] is assign 25% of $" + project.getAmount());
			amount = (long) (amount * 0.25);
			
			log.debug(TAG, "Project [" + project.getProjectId() + "] total budget amount: $" + amount);
		}
		/*
		 * Update entity of budget table
		 */
		if(budgetWS.findById(dependencyId) != null) {
			
			budget = budgetWS.findById(dependencyId);
			budget.setAmount((budget.getAmount() - prevProject.getAmount()) + amount);
			log.debug(TAG, "Budget for dependencyId[" + dependencyId + "] - $" + budget.getAmount());
		}
		
		return budget;
	}
	
	public Budget removeProjectAmountFromBudget(Project project, Long dependencyId) throws ParseException {
		log.debug(TAG, "Project amount will be removed from Budget");
		Budget budget = null;
		/*
		 * Update entity of budget table
		 */
		if(budgetWS.findById(dependencyId) != null) {
			budget = budgetWS.findById(dependencyId);
			budget.setAmount(budget.getAmount() - project.getAmount());
			log.debug(TAG, "Budget for dependencyId[" + dependencyId + "] - $" + budget.getAmount());
		}
		
		return budget;
	}
	
	public boolean verifyAmountChanged(Project project) {
		log.debug(TAG, "Verifying project amount was modified [ProjecId=" + project.getProjectId() + "]");
		
		boolean result = false;
		Project prevProjectInfo = projectWS.search(project.getProjectId());
		
		if(!prevProjectInfo.getAmount().equals(prevProjectInfo.getAmount())) {
			log.debug(TAG, "Project " + project.getProjectId() + " changed");
			return true;
		} else{
			log.debug(TAG, "Project " + project.getProjectId() + " NOT changed");
		}
		
		return result;
	}
}
