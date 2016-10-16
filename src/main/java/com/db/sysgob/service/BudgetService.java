package com.db.sysgob.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.repository.BudgetRepository;

@Service
public class BudgetService {
	
	@Autowired
	private BudgetRepository budgetRepository;
	
	public boolean create(Budget budget){
		boolean result = false;
		
		try {
			budgetRepository.createBudget(budget);
			result = true;
		} catch (Exception e){
			e.getMessage();
		}
		
		return result;
	}
	
	public boolean modify(Budget budget){
		boolean result = false;
		
		try {
			budgetRepository.updateBudget(budget);
			result = true;
		} catch (Exception e){
			e.getMessage();
		}
		
		return result;
	}
	
	public Budget findById(Long id){
		Budget result = null;
		
		try {
			result = budgetRepository.getBudgetByDependencyId(id);
		} catch (Exception e){
			e.getMessage();
		}
		
		return result;
	}
	
	public List<Budget> search(){
		List<Budget> result = null;
		
		try {
			result = budgetRepository.getBudgets();
		} catch (Exception e){
			e.getMessage();
		}
		
		return result;
	}
}
