package com.db.sysgob.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense {

	@Id
	@Column(name = "expense_id", columnDefinition="serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long expenseId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "total_amount", nullable = false)
	private Long totalAmount;
	
	@Column(name = "create_date", nullable = false)
	private Timestamp createDate;
	
	@Column(name = "update_date")
	private Timestamp updateDate;
	
	@Column(name = "dependency_id", nullable = false)
	private Long dependencyId;

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long id) {
		this.expenseId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Long getDependencyId() {
		return dependencyId;
	}

	public void setDependencyId(Long dependencyId) {
		this.dependencyId = dependencyId;
	}

	 @Override
	    public String toString() {
	    	return "Expense [expenseId=" + expenseId + 
				", name=" + name + 
				", totalAmount=" + totalAmount + 
				", createDate=" + createDate + 
				", updateDate=" + updateDate +
				", dependencyId=" + dependencyId + "]";
	    }
}
