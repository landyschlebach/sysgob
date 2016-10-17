package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.sysgob.entity.Project;

@Repository
public class ProjectRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public void createProject(Project project) {
		em.getTransaction().begin();
		em.persist(project);
		em.getTransaction().commit();
	}
	
	public void updateProject(Project project) {
		em.getTransaction().begin();
		em.merge(project);
		em.getTransaction().commit();
	}
	
	public void deleteProject(Project project) {
		em.getTransaction().begin();
		em.remove(project);
		em.getTransaction().commit();
	}

	@Transactional(value = "transactionManager", readOnly = true)
	public Project getById(Long id) {
		return em.find(Project.class, id);
	}

	@Transactional(value = "transactionManager", readOnly = true)
	public List<Project> getProjects(Long dependencyId) {
	
	  TypedQuery<Project> qry =
	      em.createQuery(
	          "from Project as c where c.dependencyId=:dependencyId",
	          Project.class);
	  qry.setParameter("dependencyId", dependencyId);
	  List<Project> result = qry.getResultList();
	
	  return result;
	}
	
	@Transactional(value = "transactionManager", readOnly = true)
	public List<Project> getProjects() {
	      return em.createQuery(
	          "from Project as p",
	          Project.class).getResultList();
	}

	@Transactional(value = "transactionManager", readOnly = true)
	public String healthcheck() {
	  String queryResult;
	    
	  Query query = em.createNativeQuery("SELECT CURDATE()");
	  queryResult = query.getSingleResult().toString();
	    
	    return queryResult;
	}
	
	/* For JUnit Testing */
	public EntityManager getEntityManager() {
	  return em;
	}
	
	public void setEntityManager(EntityManager em) {
	  this.em = em;
	}
}
