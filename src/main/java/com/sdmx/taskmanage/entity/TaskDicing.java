package com.sdmx.taskmanage.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TaskDicing")
public class TaskDicing implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6502023585711026163L;
	private Long dicingId;
	private String dicingNo;
	private String dicingTabletsNo;
	private String dicingTubeNum;
	private String dicingPlan;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_dicingId_ID")
	@SequenceGenerator(name = "S_dicingId_ID", sequenceName = "S_dicingId_ID",allocationSize=1)
	public Long getDicingId() {
		return dicingId;
	}
	public void setDicingId(Long dicingId) {
		this.dicingId = dicingId;
	}
	public String getDicingNo() {
		return dicingNo;
	}
	public void setDicingNo(String dicingNo) {
		this.dicingNo = dicingNo;
	}
	public String getDicingTabletsNo() {
		return dicingTabletsNo;
	}
	public void setDicingTabletsNo(String dicingTabletsNo) {
		this.dicingTabletsNo = dicingTabletsNo;
	}
	public String getDicingTubeNum() {
		return dicingTubeNum;
	}
	public void setDicingTubeNum(String dicingTubeNum) {
		this.dicingTubeNum = dicingTubeNum;
	}
	public String getDicingPlan() {
		return dicingPlan;
	}
	public void setDicingPlan(String dicingPlan) {
		this.dicingPlan = dicingPlan;
	}
}
