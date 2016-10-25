package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.db.sysgob.entity.Dependency;

@Repository
public class DependencyRepository {
	
	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;
	
	public Dependency getById(Long id) {
		return em.find(Dependency.class, id);
	}
	
	public List<Dependency> getDependencies() {
	      TypedQuery<Dependency> query = em.createQuery(
	          "from Dependency as d",
	          Dependency.class);
	      
	      return query.getResultList();
	}
}
