package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.sysgob.entity.Category;
import com.db.sysgob.entity.Project;

@Repository
public class CategoryRepository {

	@PersistenceContext
	private EntityManager em;

	@Transactional(value = "transactionManager", readOnly = true)
	public Category getById(Long id) {
		return em.find(Category.class, id);
	}
	
	@Transactional(value = "transactionManager", readOnly = true)
	public List<Category> getCategories() {
	      return em.createQuery(
	          "from Category as c",
	          Category.class).getResultList();
	}

	@Transactional(value = "transactionManager", readOnly = true)
	public Long getCategoryByPoints(Long points) {
	
	  TypedQuery<Category> qry =
	      em.createQuery(
	          "SELECT c.category_id "
	          + "FROM Category as c "
	          + "WHERE c.min_score <=:points "
	          + "AND c.max_score >=:points",
	          Category.class);
	  qry.setParameter("points", points);
	  Long result = qry.getSingleResult().getCategoryId();
	
	  return result;
	}
}
