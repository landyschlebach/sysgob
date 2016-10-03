package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ResourcesByProjectRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public void createResourceByProject(ResourcesByProject resourcesByProject) {
		em.getTransaction().begin();
		em.persist(resourcesByProject);
		em.getTransaction().commit();
	}
	
	public void updateResourceByProject(ResourcesByProject resourcesByProject) {
		em.getTransaction().begin();
		em.merge(resourcesByProject);
		em.getTransaction().commit();
	}
	
	public void deleteResourceByProject(ResourcesByProject resourcesByProject) {
		em.getTransaction().begin();
		em.remove(resourcesByProject);
		em.getTransaction().commit();
	}

	@Transactional(value = "transactionManager", readOnly = true)
	public ResourcesByProject getById(Long id) {
		return em.find(ResourcesByProject.class, id);
	}

	@Transactional(value = "transactionManager", readOnly = true)
	public List<ResourcesByProject> getResourcesByProject(Long projectId) {
	
	  TypedQuery<ResourcesByProject> qry =
	      em.createQuery(
	          "from ResourcesByProject as rp where rp.projectId=:projectId",
	          ResourcesByProject.class);
	  qry.setParameter("projectId", projectId);
	  List<ResourcesByProject> result = qry.getResultList();
	
	  return result;
	}
	
	@Transactional(value = "transactionManager", readOnly = true)
	public List<ResourcesByProject> getResourcesByProjects() {
	      return em.createQuery(
	          "from ResourcesByProject as rp",
	          ResourcesByProject.class).getResultList();
	}
}
