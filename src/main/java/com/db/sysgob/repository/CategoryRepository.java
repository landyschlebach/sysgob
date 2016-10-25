package com.db.sysgob.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.db.sysgob.entity.Category;

@Repository
public class CategoryRepository {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	public Category getById(Long id) {
		return em.find(Category.class, id);
	}
	
	public List<Category> getCategories() {
		TypedQuery<Category> query = em.createQuery("from Category as c", Category.class);
		return query.getResultList();
	}

	public Category getCategoryByPoints(Long points) {
	  TypedQuery<Category> qry = em.createQuery("from Category as c "
	          + "WHERE c.minScore <=:points "
	          + "AND c.maxScore <=:points",
	          Category.class);
	  qry.setParameter("points", points);
	
	  return qry.getResultList().get(0);
	}
}
