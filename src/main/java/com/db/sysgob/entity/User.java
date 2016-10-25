package com.db.sysgob.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "users")
@PersistenceUnit(name = "persistenceUnit")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="users_seq")
    @SequenceGenerator(name="users_seq", sequenceName="users_seq", allocationSize=1)
	@Column(name = "user_id", columnDefinition="serial", nullable = false)
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

	public String getName() {
		return name;
	}

	public void setName(String user) {
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
