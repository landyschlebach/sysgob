package com.db.sysgob.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "budgets")
@PersistenceUnit(name = "persistenceUnit")
public class Budget {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="budgets_seq")
    @SequenceGenerator(name="budgets_seq", sequenceName="budgets_seq", allocationSize=1)
	@Column(name = "budget_id", columnDefinition="serial", nullable = false)
	private Long budgetId;
	
	@Column(name = "amount", nullable = false)
	private Long amount;
	
	@Column(name = "expiration_date")
	private Timestamp expirationDate;
	
	@Column(name = "dependency_id", nullable = false)
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

	public Timestamp getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Timestamp expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Long getDependencyId() {
		return dependencyId;
	}

	public void setDependencyId(Long dependencyId) {
		this.dependencyId = dependencyId;
	}

	 @Override
	    public String toString() {
	    	return "Budget [budgetId=" + budgetId + 
				", amount=" + amount + 
				", expirationDate=" + expirationDate + 
				", dependencyId=" + dependencyId + "]";
	    }
}

