package com.db.sysgob.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Budget {

	@Id
	@Column(name = "budget_id", columnDefinition="serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long budgetId;
	
	@Column(name = "amount", nullable = false)
	private int amount;
	
	@Column(name = "expiration_date")
	private String expirationDate;
	
	@Column(name = "dependency_id")
	private Long dependencyId;

	public Long getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(Long budgetId) {
		this.budgetId = budgetId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Long getDependencyId() {
		return dependencyId;
	}

	public void setDependencyId(Long dependencyId) {
		this.dependencyId = dependencyId;
	}

}

