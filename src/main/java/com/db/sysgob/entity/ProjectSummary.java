package com.db.sysgob.entity;

import java.util.Date;

public class ProjectSummary {

	private Long projectId;
	private String name;
	private String description;
	private Date updateDate;
	private String categoryName;
	private String username;
	
	public ProjectSummary(Long projectId, String name, String description, 
			Date updateDate, String categoryName, String username) {
		super();
		
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		this.updateDate = updateDate;
		this.categoryName = categoryName;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	} 
	
    @Override
    public String toString() {
    	return "ProjectSummary [projectId=" + projectId + 
			", name=" + name + 
			", description=" + description + 
			", updateDate=" + updateDate +
			", categoryName=" + categoryName + 
			", username=" + username + "]";
    }
}
