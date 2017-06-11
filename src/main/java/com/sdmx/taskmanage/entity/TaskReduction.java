package com.sdmx.taskmanage.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TaskReduction")
public class TaskReduction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1464257280656644806L;
	private Long reductionId;
	private String reductionNo;
	private String reductionTabletsNo;
	private String reductionThickness;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_reductionId_ID")
	@SequenceGenerator(name = "S_reductionId_ID", sequenceName = "S_reductionId_ID",allocationSize=1)
	public Long getReductionId() {
		return reductionId;
	}
	public void setReductionId(Long reductionId) {
		this.reductionId = reductionId;
	}
	public String getReductionNo() {
		return reductionNo;
	}
	public void setReductionNo(String reductionNo) {
		this.reductionNo = reductionNo;
	}
	public String getReductionTabletsNo() {
		return reductionTabletsNo;
	}
	public void setReductionTabletsNo(String reductionTabletsNo) {
		this.reductionTabletsNo = reductionTabletsNo;
	}
	public String getReductionThickness() {
		return reductionThickness;
	}
	public void setReductionThickness(String reductionThickness) {
		this.reductionThickness = reductionThickness;
	}
	
}
