package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ResourcesSpentRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	
	public void createResourceByProject(ResourcesSpent resourcesSpent) {
		em.getTransaction().begin();
		em.persist(resourcesSpent);
		em.getTransaction().commit();
	}
	
	public void updateResourceByProject(ResourcesSpent resourcesSpent) {
		em.getTransaction().begin();
		em.merge(resourcesSpent);
		em.getTransaction().commit();
	}
	
	public void deleteResourceByProject(ResourcesSpent resourcesSpent) {
		em.getTransaction().begin();
		em.remove(resourcesSpent);
		em.getTransaction().commit();
	}

	@Transactional(value = "transactionManager", readOnly = true)
	public ResourcesSpent getById(Long id) {
		return em.find(ResourcesSpent.class, id);
	}

	@Transactional(value = "transactionManager", readOnly = true)
	public List<ResourcesSpent> getResourcesByProject(Long dependencyId) {
	
	  TypedQuery<ResourcesSpent> qry =
	      em.createQuery(
	          "from ResourcesSpent as rs where rs.dependencyId=:dependencyId",
	          ResourcesSpent.class);
	  qry.setParameter("dependencyId", dependencyId);
	  List<ResourcesSpent> result = qry.getResultList();
	
	  return result;
	}
	
	@Transactional(value = "transactionManager", readOnly = true)
	public List<ResourcesSpent> getResourcesByProjects() {
	      return em.createQuery(
	          "from ResourcesSpent as rs",
	          ResourcesSpent.class).getResultList();
	}
}
