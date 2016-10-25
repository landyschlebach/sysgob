package com.db.sysgob.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.DashboardChart;

@Repository
public class BudgetRepository {
	
	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;
	
	@Transactional(value = "transactionManager")
	public void createBudget(Budget budget) {
		em.persist(budget);
		em.flush();
	}

	@Transactional(value = "transactionManager")
	public void updateBudget(Budget budget) {
		em.merge(budget);
		em.flush();
	}

	@Transactional(value = "transactionManager")
	public void deleteBudget(Budget budget) {
		 Query qry = em.createQuery("DELETE from Budget AS b"
			 		+ " WHERE b.budgetId=:budgetId").setParameter(
			 				"budgetId", budget.getBudgetId());
			 qry.executeUpdate();
			 em.flush();
	}
	
	public Budget getById(Long id) {
		return em.find(Budget.class, id);
	}

	public List<Budget> getBudgets() {
	      TypedQuery<Budget> query = em.createQuery(
	          "from Budget as b",
	          Budget.class);
	      
	      return query.getResultList();
	}
	
	public Budget getBudgetByDependencyId(Long dependencyId) {
		  TypedQuery<Budget> qry =
		      em.createQuery(
		          "from Budget as b where b.dependencyId=:dependencyId",
		          Budget.class);
		  qry.setParameter("dependencyId", dependencyId);
		  Budget result = qry.getResultList().get(0);
		
		  return result;
	}
	
	public List<DashboardChart> getDashboardChartData() {
		List<DashboardChart> result = new ArrayList<DashboardChart>();
		
		TypedQuery<DashboardChart> query = em.createQuery("SELECT NEW com.db.sysgob.entity.DashboardChart"
				+ " (d.name AS label, b.amount AS value)"
				+ " from Budget b, Dependency d"
				+ " WHERE b.dependencyId = d.dependencyId", DashboardChart.class);
		List<DashboardChart> list = query.getResultList();
		
		for(int i=0; i < list.size(); i++) {
			result.add(list.get(i));
		}
		
		return result;
	}
}
