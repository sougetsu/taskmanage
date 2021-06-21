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

import com.sdmx.framework.entity.Dictionary;

@Entity
@Table(name = "TaskPriceDetail")
public class TaskPriceDetail implements Serializable{
	
	private static final long serialVersionUID = 2046211639725078346L;
	private Long taskPriceDetailId;
	private TaskOrder taskOrder;
	private Dictionary orderType; 
	private PriceDetail priceDetail;
	private Integer itemNum;//数量
	private Double totalPrice;//总计
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_TaskPriceqDetail_ID")
	@SequenceGenerator(name = "S_TaskPriceqDetail_ID", sequenceName = "S_TaskPriceqDetail_ID",allocationSize=1)
	public Long getTaskPriceDetailId() {
		return taskPriceDetailId;
	}
	public void setTaskPriceDetailId(Long taskPriceDetailId) {
		this.taskPriceDetailId = taskPriceDetailId;
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
	@JoinColumn(name="orderTypeId")
	public Dictionary getOrderType() {
		return orderType;
	}
	public void setOrderType(Dictionary orderType) {
		this.orderType = orderType;
	}
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
	@JoinColumn(name="priceDetailId")
	public PriceDetail getPriceDetail() {
		return priceDetail;
	}
	public void setPriceDetail(PriceDetail priceDetail) {
		this.priceDetail = priceDetail;
	}
	public Integer getItemNum() {
		return itemNum;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
