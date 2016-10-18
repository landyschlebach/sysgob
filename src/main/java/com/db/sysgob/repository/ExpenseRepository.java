package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.sysgob.entity.Expense;

@Repository
public class ExpenseRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public void createExpense(Expense expense) {
		em.getTransaction().begin();
		em.persist(expense);
		em.getTransaction().commit();
	}
	
	public void updateExpense(Expense expense) {
		em.getTransaction().begin();
		em.merge(expense);
		em.getTransaction().commit();
	}
	
	public void deleteExpense(Expense expense) {
		em.getTransaction().begin();
		em.remove(expense);
		em.getTransaction().commit();
	}

	@Transactional(value = "transactionManager", readOnly = true)
	public Expense getById(Long id) {
		return em.find(Expense.class, id);
	}

	@Transactional(value = "transactionManager", readOnly = true)
	public Expense getExpenseByDependencyId(Long dependencyId) {
	
	  TypedQuery<Expense> qry =
	      em.createQuery(
	          "from Expense as c where c.dependencyId=:dependencyId",
	          Expense.class);
	  qry.setParameter("dependencyId", dependencyId);
	  Expense result = qry.getResultList().get(0);
	
	  return result;
	}
	
	@Transactional(value = "transactionManager", readOnly = true)
	public List<Expense> getExpenses() {
	      return em.createQuery(
	          "from Expense as e",
	          Expense.class).getResultList();
	}
	
	/* For JUnit Testing */
	public EntityManager getEntityManager() {
	  return em;
	}
	
	public void setEntityManager(EntityManager em) {
	  this.em = em;
	}
}
