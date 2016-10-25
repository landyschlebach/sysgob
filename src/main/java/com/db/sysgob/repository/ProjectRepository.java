package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.sysgob.entity.DashboardChart;
import com.db.sysgob.entity.Project;
import com.db.sysgob.entity.ProjectDetail;
import com.db.sysgob.entity.ProjectSummary;

@Repository
public class ProjectRepository {
	
	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Transactional(value = "transactionManager")
	public void createProject(Project project) {
		em.persist(project);
		em.flush();
	}

	@Transactional(value = "transactionManager")
	public void updateProject(Project project) {
		em.merge(project);
		em.flush();
	}

	@Transactional(value = "transactionManager")
	public void deleteProject(Project project) {
		 Query qry = em.createQuery("DELETE from Project AS p"
			 		+ " WHERE p.projectId=:projectId").setParameter(
			 				"projectId", project.getProjectId());
			 qry.executeUpdate();
			 em.flush();
	}

	public Project getById(Long id) {
		return em.find(Project.class, id);
	}

	public List<ProjectSummary> getProjectSummariesByDependency(Long dependencyId) {
	  TypedQuery<ProjectSummary> qry =
	      em.createQuery("SELECT NEW com.db.sysgob.entity.ProjectSummary"
	      		+ " (p.projectId, p.name, p.description, p.updateDate, c.name AS categoryName, u.name AS username)"
	      		+ " from Project p, Category c, User u, Role r, Dependency d"
	      		+ " WHERE p.categoryId = c.categoryId"
	      		+ " AND p.userId = u.userId"
	      		+ " AND u.roleId = r.roleId"
	      		+ " AND r.dependencyId = d.dependencyId"
	      		+ " AND d.dependencyId=:dependencyId", ProjectSummary.class);
	  qry.setParameter("dependencyId", dependencyId);
	  List<ProjectSummary> result = qry.getResultList();
	
	  return result;
	}
	
	public List<ProjectDetail> getProjectsDetailByDependency(Long dependencyId) {
		  TypedQuery<ProjectDetail> qry =
		      em.createQuery("SELECT NEW com.db.sysgob.entity.ProjectDetail"
		      		+ " (p.projectId, p.name, p.description, p.createDate, p.updateDate, c.name AS categoryName, p.amount, u.name AS username)"
		      		+ " from Project p, Category c, User u, Role r, Dependency d"
		      		+ " WHERE p.userId = u.userId"
		      		+ " AND p.categoryId = c.categoryId"
		      		+ " AND u.roleId = r.roleId"
		      		+ " AND r.dependencyId = d.dependencyId"
		      		+ " AND d.dependencyId=:dependencyId", ProjectDetail.class);
		  qry.setParameter("dependencyId", dependencyId);
		  List<ProjectDetail> result = qry.getResultList();
		
		  return result;
	}
	
	public List<Project> getProjects() {
		TypedQuery<Project> query = em.createQuery(
	          "from Project as p",
	          Project.class);
		
		return query.getResultList();
	}

	public String healthcheck() {
	  String queryResult;
	    
	  Query query = em.createNativeQuery("SELECT now()");
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
