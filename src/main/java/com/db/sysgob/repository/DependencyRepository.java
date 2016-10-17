package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.sysgob.entity.Dependency;
import com.db.sysgob.entity.Project;

@Repository
public class DependencyRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(value = "transactionManager", readOnly = true)
	public Dependency getById(Long id) {
		return em.find(Dependency.class, id);
	}
	
	@Transactional(value = "transactionManager", readOnly = true)
	public List<Dependency> getDependencies() {
	      return em.createQuery(
	          "from Dependency as d",
	          Dependency.class).getResultList();
	}
}
