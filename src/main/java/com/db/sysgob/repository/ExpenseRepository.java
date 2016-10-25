package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.sysgob.entity.Expense;

@Repository
public class ExpenseRepository {
	
	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Transactional(value = "transactionManager")
	public void createExpense(Expense expense) {
		em.persist(expense);
		em.flush();
	}

	@Transactional(value = "transactionManager")
	public void updateExpense(Expense expense) {
		em.merge(expense);
		em.flush();
	}

	@Transactional(value = "transactionManager")
	public void deleteExpense(Expense expense) {
		 Query qry = em.createQuery("DELETE from Expense AS e"
		 		+ " WHERE e.expenseId=:expenseId").setParameter(
		 				"expenseId", expense.getExpenseId());
		 qry.executeUpdate();
		 em.flush();
	}

	public Expense getById(Long id) {
		return em.find(Expense.class, id);
	}

	public Expense getExpenseByDependencyId(Long dependencyId) {
	  TypedQuery<Expense> qry =
	      em.createQuery(
	          "from Expense as c where c.dependencyId=:dependencyId",
	          Expense.class);
	  qry.setParameter("dependencyId", dependencyId);
	  Expense result = qry.getResultList().get(0);
	
	  return result;
	}
	
	public List<Expense> getExpenses() {
		TypedQuery<Expense> query =em.createQuery(
	          "from Expense as e",
	          Expense.class);
		
		return query.getResultList();
	}
	
	/* For JUnit Testing */
	public EntityManager getEntityManager() {
	  return em;
	}
	
	public void setEntityManager(EntityManager em) {
	  this.em = em;
	}
}
