package com.db.sysgob.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.DashboardChart;
import com.db.sysgob.repository.BudgetRepository;

@Service
public class BudgetService {
	private static final Logger log = LoggerFactory.getLogger(BudgetService.class);
	
	@Autowired
	private BudgetRepository budgetRepository;
	
	public boolean create(Budget budget){
		log.debug("WebService[CREATE]");
		boolean result = false;
		
		try {
			budgetRepository.createBudget(budget);
			result = true;

			log.debug("Successfully created Budget [" + budget +"]");
		} catch (Exception e){
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public boolean modify(Budget budget){
		log.debug("WebService[UPDATE]");
		boolean result = false;
		
		try {
			budgetRepository.updateBudget(budget);
			result = true;
			
			log.debug("Successfully updated Budget [" + budget +"]");
		} catch (Exception e){
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public boolean remove(Budget budget){
		log.debug("WebService [DELETE]");
		boolean result = false;
		
		try {
			budgetRepository.deleteBudget(budget);
			result = true;
			
			log.debug("Successfully remove Budget [" + budget +"]");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public Budget findByDependencyId(Long dependencyId){
		Budget result = null;
		
		try {
			result = budgetRepository.getBudgetByDependencyId(dependencyId);
			log.debug("Retrieving Budget [" + result + "]");
		} catch (Exception e){
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public List<Budget> findAll(){
		List<Budget> result = null;
		
		try {
			result = budgetRepository.getBudgets();
		} catch (Exception e){
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public List<DashboardChart> getDashboardData() {
		return budgetRepository.getDashboardChartData();
	}
}
