package com.db.sysgob.entity;

public class DashboardChart {
	
	private String label;
	private Long value;
	  
	  public DashboardChart (String label, Long value) {
	    super();
	    
	    this.label = label;
	    this.value = value;
	  }
	  
	  public String getLabel() {
	    return label;
	  }
	  
	  public void setLabel(String label) {
	    this.label = label;
	  }
	  
	  public Long getValue() {
	    return value;
	  }
	  
	  public void setValue(Long value) {
	    this.value = value;
	  }
	
	  @Override
	  public String toString() {
	    return "DashboardChart [label=" + label + ", value=" + value + "]";
	  }
}
