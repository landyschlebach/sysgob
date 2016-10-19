package com.db.sysgob.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "budgets")
public class Budget {

	@Id
	@Column(name = "budget_id", columnDefinition="serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long budgetId;
	
	@Column(name = "amount", nullable = false)
	private Long amount;
	
	@Column(name = "expiration_date")
	private Date expirationDate;
	
	@Column(name = "dependency_id")
	private Long dependencyId;

	public Long getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(Long budgetId) {
		this.budgetId = budgetId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Long getDependencyId() {
		return dependencyId;
	}

	public void setDependencyId(Long dependencyId) {
		this.dependencyId = dependencyId;
	}

}

