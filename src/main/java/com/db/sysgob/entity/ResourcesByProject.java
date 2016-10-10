package com.db.sysgob.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ResourcesByProject {

	@Id
	@Column(name = "project_id", nullable = false)
	private Long id;
	
	@Column(name = "resource_id", nullable = false)
	private Long resourceId;
	
	@Column(name = "amount", nullable = false)
	private int amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
