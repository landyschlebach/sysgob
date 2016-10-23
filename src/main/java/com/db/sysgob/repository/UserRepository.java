package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.sysgob.entity.User;
import com.db.sysgob.entity.UserBasicInfo;

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

	public void deleteUser(User user) {
		em.getTransaction().begin();
		em.remove(user);
		em.getTransaction().commit();		
	}
	
	@Transactional(value = "transactionManager", readOnly = true)
	public UserBasicInfo getUserByName(String username) {
		
		  Query qry = em.createNativeQuery("SELECT"
		      		+ " u.user_id, u.name, u.role_id, r.name, r.dependency_id, d.name"
		      		+ " FROM users u"
		      		+ " INNER JOIN roles r"
		      		+ " ON u.role_id = r.role_id"
		      		+ " INNER JOIN dependencies d"
		      		+ " ON r.dependency_id = d.dependency_id"
		      		+ " WHERE u.name = :username");
		  qry.setParameter("username", username);
		  UserBasicInfo result = (UserBasicInfo) qry.getResultList().get(0);
		
		  return result;
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