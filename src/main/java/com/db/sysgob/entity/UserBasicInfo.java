package com.db.sysgob.entity;

public class UserBasicInfo {

	private String name;	
	private Long roleId;	
	private String roleName;	
	private Long dependencyId;	
	private String dependencyName;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public Long getDependencyId() {
		return dependencyId;
	}
	
	public void setDependencyId(Long dependencyId) {
		this.dependencyId = dependencyId;
	}
	
	public String getDependencyName() {
		return dependencyName;
	}
	
	public void setDependencyName(String dependencyName) {
		this.dependencyName = dependencyName;
	}
}
