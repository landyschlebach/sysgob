package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.db.sysgob.entity.Role;

@Repository
public class RoleRepository {
	
	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;
	
	public Role getById(Long id) {
		return em.find(Role.class, id);
	}
	
	public List<Role> getRoles() {
	      TypedQuery<Role> query = em.createQuery(
	          "from Role as r",
	          Role.class);
	      
	      return query.getResultList();
	}
}
