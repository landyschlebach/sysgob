package com.db.sysgob.entity;

import java.util.Date;

public class ProjectDetail {

	private Long projectId;
	private String name;
	private String description;
	private Date createDate;
	private Date updateDate;
	private String categoryName;
	private Long amount;
	private String username;
	
	public ProjectDetail(Long projectId, String name, String description, Date createDate,
			Date updateDate, String categoryName, Long amount, String username) {
		super();
		
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.categoryName = categoryName;
		this.amount = amount;
		this.username = username;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
