package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.sysgob.entity.User;

@Repository
public class UserRepository {
	
	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Transactional(value = "transactionManager")
	public void newUser(User user) {
		em.persist(user);
		em.flush();
	}

	@Transactional(value = "transactionManager")
	public void updateUser(User user) {
		em.merge(user);
		em.flush();
	}

	@Transactional(value = "transactionManager")
	public void deleteUser(User user) {
		 Query qry = em.createQuery("DELETE from User AS u"
			 		+ " WHERE u.userId=:userId").setParameter(
			 				"userId", user.getUserId());
			 qry.executeUpdate();
			 em.flush();
	}

	public User getUserByName(String username) {
		
		TypedQuery<User> query = em.createQuery("from User u"
				+ " WHERE u.name=:username", User.class);
		query.setParameter("username", username);

	    return query.getResultList().get(0);
	}

	public User getUserById(Long id) {
		return em.find(User.class, id);
	}

	public List<User> getUsers() {
	      TypedQuery<User> query = em.createQuery(
	          "from User as u",
	          User.class);
	      
	      return query.getResultList();
	}
}