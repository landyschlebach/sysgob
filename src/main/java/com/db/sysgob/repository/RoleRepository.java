package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.sysgob.entity.Role;

@Repository
public class RoleRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(value = "transactionManager", readOnly = true)
	public Role getById(Long id) {
		return em.find(Role.class, id);
	}
	
	@Transactional(value = "transactionManager", readOnly = true)
	public List<Role> getRoles() {
	      return em.createQuery(
	          "from Role as r",
	          Role.class).getResultList();
	}
}
