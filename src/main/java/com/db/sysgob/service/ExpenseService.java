package com.db.sysgob.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.sysgob.entity.Expense;
import com.db.sysgob.repository.ExpenseRepository;

@Service
public class ExpenseService {
	private static final Logger log = LoggerFactory.getLogger(ExpenseService.class);

	@Autowired
	private ExpenseRepository expenseRepository;
	
	public boolean create(Expense expense){
		log.debug("WebService [CREATE]");
		boolean result = false;
		
		try {
			expenseRepository.createExpense(expense);
			result = true;
			
			log.debug("Successfully created Expense [" + expense +"]");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public boolean modify(Expense expense){
		log.debug("WebService [UPDATE]");
		boolean result = false;
		
		try {
			expenseRepository.updateExpense(expense);
			result = true;
			
			log.debug("Successfully updated Expense [" + expense +"]");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public boolean remove(Expense expense){
		log.debug("WebService [DELETE]");
		boolean result = false;
		
		try {
			expenseRepository.deleteExpense(expense);
			result = true;
			
			log.debug("Successfully remove Expense [" + expense +"]");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public Expense findById(Long id){
		Expense result = null;
		
		try {
			result = expenseRepository.getExpenseByDependencyId(id);
			log.debug("Retrieving Expense [" + result + "]");
		} catch (Exception e){
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public List<Expense> search(){
		List<Expense> result = null;
		
		try {
			result = expenseRepository.getExpenses();
		} catch (Exception e){
			log.debug(e.getMessage());
		}
		
		return result;
	}
}
