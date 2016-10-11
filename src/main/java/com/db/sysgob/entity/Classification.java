package com.db.sysgob.entity;

public class Classification {

	private Long riskClassification;
	private Long othersClassification;
	private Long otherImplications;
	private Long financeClassification;
	private Long strategicClassification;
	
	public Long getRiskClassification() {
		return riskClassification;
	}
	
	public void setRiskClassification(Long riskClassification) {
		this.riskClassification = riskClassification;
	}
	
	public Long getOthersClassification() {
		return othersClassification;
	}
	
	public void setOthersClassification(Long othersClassification) {
		this.othersClassification = othersClassification;
	}
	
	public Long getOtherImplications() {
		return otherImplications;
	}
	
	public void setOtherImplications(Long otherImplications) {
		this.otherImplications = otherImplications;
	}
	
	public Long getFinanceClassification() {
		return financeClassification;
	}
	
	public void setFinanceClassification(Long financeClassification) {
		this.financeClassification = financeClassification;
	}
	
	public Long getStrategicClassification() {
		return strategicClassification;
	}
	
	public void setStrategicClassification(Long strategicClassification) {
		this.strategicClassification = strategicClassification;
	}
}
