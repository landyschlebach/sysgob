package com.db.sysgob.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.sysgob.entity.Expense;
import com.db.sysgob.repository.ExpenseRepository;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;
	
	public boolean create(Expense expense){
		boolean result = false;
		
		try {
			expenseRepository.createExpense(expense);
			result = true;
		} catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}
	
	public boolean modify(Expense expense){
		boolean result = false;
		
		try {
			expenseRepository.updateExpense(expense);
			result = true;
		} catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}
	
	public boolean remove(Expense expense){
		boolean result = false;
		
		try {
			expenseRepository.deleteExpense(expense);
			result = true;
		} catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}
	
	public Expense findById(Long id){
		Expense result = null;
		
		try {
			result = expenseRepository.getExpenseByDependencyId(id);
		} catch (Exception e){
			e.getMessage();
		}
		
		return result;
	}
	
	public List<Expense> search(){
		List<Expense> result = null;
		
		try {
			result = expenseRepository.getExpenses();
		} catch (Exception e){
			e.getMessage();
		}
		
		return result;
	}
}
