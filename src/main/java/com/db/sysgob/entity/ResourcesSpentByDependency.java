package com.db.sysgob.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class ResourcesSpentByDependency {

	@Id
	@Column(name = "dependency_id", nullable = false)
	private Long dependencyId;
	
	@Column(name = "resource_id", nullable = false)
	private Long resourceId;
	
	@Column(name = "amount", nullable = false)
	private int amount;

	public Long getDependencyId() {
		return dependencyId;
	}

	public void setDependencyId(Long dependencyId) {
		this.dependencyId = dependencyId;
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
