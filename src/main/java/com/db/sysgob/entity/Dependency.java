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
@Table(name = "dependencies")
@PersistenceUnit(name = "persistenceUnit")
public class Dependency {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="dependencies_seq")
    @SequenceGenerator(name="dependencies_seq", sequenceName="dependencies_seq", allocationSize=1)
	@Column(name = "dependency_id", columnDefinition="serial", nullable = false)
	private Long dependencyId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "acronym")
	private String acronym;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "responsible", nullable = false)
	private String responsible;

	public Long getDependencyId() {
		return dependencyId;
	}

	public void setDependencyId(Long dependencyId) {
		this.dependencyId = dependencyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	
	 @Override
	    public String toString() {
	    	return "Dependency [dependencyId=" + dependencyId + 
				", name=" + name + 
				", acronym=" + acronym + 
				", description=" + description + 
				", responsible=" + responsible + "]";
	    }
}
