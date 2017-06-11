package com.sdmx.taskmanage.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "TaskPrice")
public class TaskPrice implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5981163449952512128L;
	private Long taskPriceId;
	private TaskOrder taskOrder;
	private PriceItem priceItem;
	private Integer itemNum;//数量
	private Double itemSum;//总计
	private String remarks;//备注
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_TaskPrice_ID")
	@SequenceGenerator(name = "S_TaskPrice_ID", sequenceName = "S_TaskPrice_ID",allocationSize=1)
	public Long getTaskPriceId() {
		return taskPriceId;
	}
	public void setTaskPriceId(Long taskPriceId) {
		this.taskPriceId = taskPriceId;
	}
	
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
	@JoinColumn(name = "taskOrderId")
	public TaskOrder getTaskOrder() {
		return taskOrder;
	}
	public void setTaskOrder(TaskOrder taskOrder) {
		this.taskOrder = taskOrder;
	}
	@JsonIgnore
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
	@JoinColumn(name="priceItemId")
	public PriceItem getPriceItem() {
		return priceItem;
	}
	public void setPriceItem(PriceItem priceItem) {
		this.priceItem = priceItem;
	}
	public Integer getItemNum() {
		return itemNum;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public Double getItemSum() {
		return itemSum;
	}
	public void setItemSum(Double itemSum) {
		this.itemSum = itemSum;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
