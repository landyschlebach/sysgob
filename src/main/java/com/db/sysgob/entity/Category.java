package com.db.sysgob.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Category {

	@Id
	@Column(name = "category_id", columnDefinition="serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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


}