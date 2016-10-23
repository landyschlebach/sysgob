package com.db.sysgob.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "user_id", columnDefinition="serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "role_id", nullable = false)
	private Long roleId;
	
	@Column(name = "create_date", nullable = false)
	private Timestamp createDate;
	
	@Column(name = "update_date")
	private Timestamp updateDate;
	
	@Column(name = "blocked")
	private short blocked;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUser() {
		return name;
	}

	public void setUser(String user) {
		this.name = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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
	
	public short getBlocked() {
		return blocked;
	}

	public void blocked(short blocked) {
		this.blocked = blocked;
	}
	
    @Override
    public String toString() {
    	return "User [userId=" + userId + 
			", user=" + name + 
			", password=" + password + 
			", roleId=" + roleId + 
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", blocked=" + blocked + "]";
    }
}
