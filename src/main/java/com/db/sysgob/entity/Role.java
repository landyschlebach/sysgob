package com.db.sysgob.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@PersistenceUnit(name = "persistenceUnit")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="roles_seq")
    @SequenceGenerator(name="roles_seq", sequenceName="roles_seq", allocationSize=1)
	@Column(name = "role_id", columnDefinition="serial", nullable = false)
	private Long roleId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "dependency_id", nullable = false)
	private Long dependencyId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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

	public Long getDependencyId() {
		return dependencyId;
	}

	public void setDependencyId(Long dependencyId) {
		this.dependencyId = dependencyId;
	}
	
    @Override
    public String toString() {
    	return "Role [roleId=" + roleId + 
			", name=" + name + 
			", description=" + description + 
			", dependencyId=" + dependencyId + "]";
    }
	
}
