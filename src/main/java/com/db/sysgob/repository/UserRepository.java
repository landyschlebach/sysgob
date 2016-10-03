package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.sysgob.entity.Project;

@Repository
public class UserRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public void newUser(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}
	
	public void updateUser(User user) {
		em.getTransaction().begin();
		em.merge(user);
		em.getTransaction().commit();		
	}

	@Transactional(value = "transactionManager", readOnly = true)
	public User getUserById(Long id) {
		return em.find(User.class, id);
	}
	
	@Transactional(value = "transactionManager", readOnly = true)
	public List<User> getUsers() {
	      return em.createQuery(
	          "from User as u",
	          User.class).getResultList();
	}
}