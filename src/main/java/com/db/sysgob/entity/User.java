package com.db.sysgob.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class User {

	@Id
	@Column(name = "user_id", columnDefinition="serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(name = "user", nullable = false)
	private String user;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "seed", nullable = false)
	private String seed;
	
	@Column(name = "encryption")
	private String encryption;
	
	@Column(name = "role_id", nullable = false)
	private Long roleId;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "create_date", nullable = false)
	private Date createDate;
	
	@Column(name = "update_date")
	private Date updateDate;
	
	@Column(name = "blocked")
	private boolean blocked;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}
	
	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption= encryption;
	}
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	public boolean getBlocked() {
		return blocked;
	}

	public void blocked(boolean blocked) {
		this.blocked = blocked;
	}
	
}
