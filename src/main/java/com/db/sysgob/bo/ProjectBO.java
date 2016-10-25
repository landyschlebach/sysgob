package com.db.sysgob.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.Category;
import com.db.sysgob.entity.Project;
import com.db.sysgob.entity.ProjectDetail;
import com.db.sysgob.entity.ProjectSummary;
import com.db.sysgob.repository.CategoryRepository;
import com.db.sysgob.service.BudgetService;
import com.db.sysgob.service.ProjectService;

@Component
public class ProjectBO {	
	private static final Logger log = LoggerFactory.getLogger(ProjectBO.class);
	
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
		
		log.debug("Calculating System SYSGOB Category");
		Long total = riskClassification + 
					othersClassification + 
					financeClassification + 
					otherImplications + 
					strategicClassification;
		
		log.debug("Total category points: " + total);
		return total;
	}
	
	public Project defineCategory(Project project, Long points) {
		log.debug("Defining " + project + " category");
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
		systemCategoryId = categoryRepository.getCategoryByPoints(averageCategory).getCategoryId();
		project.setCategoryId(systemCategoryId);
		log.debug(project + " final category: " + project.getCategoryId());
		
		return project;
	}
	
	public Budget verifyBudget(Long dependencyId) {
		log.debug("Verifying budget for [DependencyId=" + dependencyId + "]");
		return budgetWS.findByDependencyId(dependencyId);
	}
	
	public Budget initiateDefaultBudget(Long dependencyId) throws ParseException {
		log.debug("Making default budget");
		
		Budget budget = new Budget();
		
		budget.setAmount(0L);
		budget.setDependencyId(dependencyId);
		budget.setExpirationDate(new java.sql.Timestamp(format.parse(date).getTime()));
		
		return budget;
	}
	
	public Budget calculateBudget(Project project, Long dependencyId) throws ParseException {
		log.debug("[Initial] Calculating budget");
		
		Budget budget = null;
		Long amount = project.getAmount();
		
		/*
		 * Based on the category, define the percentage
		 *  	of budget the project will get.
		 */

		log.debug("Project Category=[" + project.getCategoryId() + "]");
		if (project.getCategoryId() == 2) {
			log.debug("Project [" + project.getProjectId() + "] is assign 75% of $" + project.getAmount());
			amount = (long) (amount * 0.75);
			
			log.debug("Project [" + project.getProjectId() + "] total budget amount: $" + amount);
		} else if (project.getCategoryId() == 3) {
			log.debug("Project [" + project.getProjectId() + "] is assign 50% of $" + project.getAmount());
			amount = (long) (amount * 0.50);
			
			log.debug("Project [" + project.getProjectId() + "] total budget amount: $" + amount);
		} else {
			log.debug("Project [" + project.getProjectId() + "] is assign 25% of $" + project.getAmount());
			amount = (long) (amount * 0.25);
			
			log.debug("Project [" + project.getProjectId() + "] total budget amount: $" + amount);
		}
		/*
		 * Update entity of budget table
		 */
		if(budgetWS.findByDependencyId(dependencyId) != null) {
			
			budget = budgetWS.findByDependencyId(dependencyId);
			budget.setAmount(budget.getAmount() + amount);
			log.debug("Budget for dependencyId[" + dependencyId + "] - $" + budget.getAmount());
		}
		
		return budget;
	}
	
	public Budget recalculateBudget(Project project, Long dependencyId) throws ParseException {
		log.debug("Project amount or category is being modified: [Recalculating Budget]");
		
		Budget budget = null;
		Project prevProject = projectWS.findById(project.getProjectId());
		Long amount = project.getAmount();
		
		/*
		 * Based on the category, define the percentage
		 *  	of budget the project will get.
		 */
		log.debug("Project Category=[" + project.getCategoryId() + "]");
		if (project.getCategoryId() == 2) {
			log.debug("Project [" + project.getProjectId() + "] is assign 75% of $" + project.getAmount());
			amount = (long) (amount * 0.75);
			
			log.debug("Project [" + project.getProjectId() + "] total budget amount: $" + amount);
		} else if (project.getCategoryId() == 3) {
			log.debug("Project [" + project.getProjectId() + "] is assign 50% of $" + project.getAmount());
			amount = (long) (amount * 0.50);
			
			log.debug("Project [" + project.getProjectId() + "] total budget amount: $" + amount);
		} else {
			log.debug("Project [" + project.getProjectId() + "] is assign 25% of $" + project.getAmount());
			amount = (long) (amount * 0.25);
			
			log.debug("Project [" + project.getProjectId() + "] total budget amount: $" + amount);
		}
		/*
		 * Update entity of budget table
		 */
		if(budgetWS.findByDependencyId(dependencyId) != null) {
			
			budget = budgetWS.findByDependencyId(dependencyId);
			budget.setAmount((budget.getAmount() - prevProject.getAmount()) + amount);
			log.debug("Budget for dependencyId[" + dependencyId + "] - $" + budget.getAmount());
		}
		
		return budget;
	}
	
	public Budget removeProjectAmountFromBudget(Project project, Long dependencyId) throws ParseException {
		log.debug("Project amount will be removed from Budget");
		Budget budget = null;
		/*
		 * Update entity of budget table
		 */
		if(budgetWS.findByDependencyId(dependencyId) != null) {
			budget = budgetWS.findByDependencyId(dependencyId);
			budget.setAmount(budget.getAmount() - project.getAmount());
			log.debug("Budget for dependencyId[" + dependencyId + "] - $" + budget.getAmount());
		}
		
		return budget;
	}
	
	public boolean verifyAmountChanged(Project project) {
		log.debug("Verifying project amount was modified [ProjecId=" + project.getProjectId() + "]");
		
		boolean result = false;
		Project prevProjectInfo = projectWS.findById(project.getProjectId());
		
		if(!prevProjectInfo.getAmount().equals(prevProjectInfo.getAmount())) {
			log.debug("[CHANGE ] " + project);
			return true;
		} else{
			log.debug("[NO CHANGE] " + project);
		}
		
		return result;
	}
	
	public ProjectDetail getCurrentProject(Long projectId, Long dependencyId) {
		log.debug("Checking ProjectId[" + projectId + "], DependencyId [" + dependencyId + "]");
		List<ProjectDetail> projects = projectWS.findProjectDetailsByDependencyId(dependencyId);
	    int position = 0;
	    
		if(projectId == 1) {
			log.debug("Return first project in list.");
			return projects.get(0);
		} else {
			log.debug("ProjectId[" + projectId + "]");
			
			for(int i=0; i <= projects.size(); i++) {
				
				if(projectId == projects.get(i).getProjectId()) {
					position = i;
				}
			}
			return projects.get(position);
		}
	}
	
	public Long getPreviousProject(Long projectId, Long dependencyId) {
		log.debug("Checking ProjectId[" + projectId + "], DependencyId [" + dependencyId + "]");
		List<ProjectSummary> projects = projectWS.findByDependencyId(dependencyId);
		log.debug(projects.toString());
	    int position = 0;
	    
		if(projectId != 1) {

			log.debug("ProjectId[" + projectId + "] not the first in list.");
			for(int i=0; i <= projects.size(); i++) {
			
				if(projectId == projects.get(i).getProjectId()) {
					position = i--;
				}
			}
			return projects.get(position).getProjectId();
		} else {
			log.debug("ProjectId[" + projectId + "] first in list.");
			return null;
		}
	}
	
	public Long getNextProject(Long projectId, Long dependencyId) {
		log.debug("Checking ProjectId[" + projectId + "], DependencyId [" + dependencyId + "]");
		List<ProjectSummary> projects = projectWS.findByDependencyId(dependencyId);
		log.debug(projects.toString());
		ProjectSummary lastProject = projects.get(projects.size() - 1);
		log.debug(lastProject.toString());
		
		if(projectId == 1 && projects.size() > 1) {
			return projects.get(1).getProjectId();
			
		} else if(projectId != lastProject.getProjectId() && projects.size() > 1) {
				log.debug("ProjectId[" + projectId + "] not last in list.");
				
			    int position = 0;
				for(int i=0; i <= projects.size(); i++) {
					
					if(projectId == projects.get(i).getProjectId()) {
						position = i++;
					}
				}
				return projects.get(position).getProjectId();
		} else {
			log.debug("ProjectId[" + projectId + "] last in list.");
			return null;
		}
	}
	
	public Project getProjectAfterRemoval(Long projectId, Long dependencyId) {
		log.debug("Checking ProjectId[" + projectId + "], DependencyId [" + dependencyId + "]");
		List<ProjectSummary> projects = projectWS.findByDependencyId(dependencyId);
		log.debug(projects.toString());
		
		ProjectSummary lastProject = projects.get(projects.size() - 1);
		if(projectId == lastProject.getProjectId()) {
			
			ProjectSummary project = projects.get(projects.size() - 2);
			return projectWS.findById(project.getProjectId());
			
		} else if (projectId == projects.get(0).getProjectId()) {
			ProjectSummary project = projects.get(1);
			return projectWS.findById(project.getProjectId());
		} else {
			
		    int position = 0;
			for(int i = 0; i <= projects.size(); i++) {
			
				if(projectId == projects.get(i).getProjectId()) {
					position = i++;
				}
			}
			ProjectSummary project = projects.get(position);
			return projectWS.findById(project.getProjectId());		
		}

	}
}
