package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.sysgob.entity.Budget;

@Repository
public class BudgetRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public void createBudget(Budget budget) {
		em.getTransaction().begin();
		em.persist(budget);
		em.getTransaction().commit();
	}
	
	public void updateBudget(Budget budget) {
		em.getTransaction().begin();
		em.merge(budget);
		em.getTransaction().commit();
	}
	
	@Transactional(value = "transactionManager", readOnly = true)
	public Budget getById(Long id) {
		return em.find(Budget.class, id);
	}
	
	@Transactional(value = "transactionManager", readOnly = true)
	public List<Budget> getBudgets() {
	      return em.createQuery(
	          "from Budget as b",
	          Budget.class).getResultList();
	}
	
	@Transactional(value = "transactionManager", readOnly = true)
	public Budget getBudgetByDependencyId(Long dependencyId) {

		  TypedQuery<Budget> qry =
		      em.createQuery(
		          "from Budget as b where b.dependencyId=:dependencyId",
		          Budget.class);
		  qry.setParameter("dependencyId", dependencyId);
		  Budget result = qry.getResultList().get(0);
		
		  return result;
	}
}
