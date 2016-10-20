package com.db.sysgob.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.Expense;
import com.db.sysgob.repository.BudgetRepository;

@Service
public class BudgetService {
	private final String TAG = BudgetService.class.getSimpleName();
	private static final Logger log = LoggerFactory.getLogger("sysgob_log");
	
	@Autowired
	private BudgetRepository budgetRepository;
	
	public boolean create(Budget budget){
		log.debug(TAG, "WebService[CREATE]");
		boolean result = false;
		
		try {
			budgetRepository.createBudget(budget);
			result = true;

			log.debug(TAG, "Successfully created Budget [" + budget +"]");
		} catch (Exception e){
			log.debug(TAG, e.getMessage());
		}
		
		return result;
	}
	
	public boolean modify(Budget budget){
		log.debug(TAG, "WebService[UPDATE]");
		boolean result = false;
		
		try {
			budgetRepository.updateBudget(budget);
			result = true;
			
			log.debug(TAG, "Successfully updated Budget [" + budget +"]");
		} catch (Exception e){
			log.debug(TAG, e.getMessage());
		}
		
		return result;
	}
	
	public boolean remove(Budget budget){
		log.debug(TAG, "WebService [DELETE]");
		boolean result = false;
		
		try {
			budgetRepository.deleteBudget(budget);
			result = true;
			
			log.debug(TAG, "Successfully remove Budget [" + budget +"]");
		} catch (Exception e) {
			log.debug(TAG, e.getMessage());
		}
		
		return result;
	}
	
	public Budget findById(Long id){
		Budget result = null;
		
		try {
			result = budgetRepository.getBudgetByDependencyId(id);
			log.debug(TAG, "Retrieving Budget [" + result + "]");
		} catch (Exception e){
			log.debug(TAG, e.getMessage());
		}
		
		return result;
	}
	
	public List<Budget> search(){
		List<Budget> result = null;
		
		try {
			result = budgetRepository.getBudgets();
		} catch (Exception e){
			log.debug(TAG, e.getMessage());
		}
		
		return result;
	}
}
