package com.db.sysgob.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@PersistenceUnit(name = "persistenceUnit")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="categories_seq")
    @SequenceGenerator(name="categories_seq", sequenceName="categories_seq", allocationSize=1)
	@Column(name = "category_id", columnDefinition="serial", nullable = false)
	private Long categoryId;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "min_score", nullable = false)
	private Long minScore;

	@Column(name = "max_score", nullable = false)
	private Long maxScore;
	
	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getMinScore() {
		return minScore;
	}

	public void setMinScore(Long minScore) {
		this.minScore = minScore;
	}

	public Long getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Long maxScore) {
		this.maxScore = maxScore;
	}
	 @Override
	    public String toString() {
	    	return "Category [categoryId=" + categoryId + 
				", name=" + name + 
				", description=" + description + 
				", minScore=" + minScore + 
				", maxScore=" + maxScore + "]";
	    }
}