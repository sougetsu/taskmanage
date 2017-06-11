package com.sdmx.yansTask.entity;

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

import com.sdmx.taskmanage.entity.PriceItem;

@Entity
@Table(name = "YansTaskPrice")
public class YansTaskPrice implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5981163449952512128L;
	private Long taskPriceId;
	private YansTaskOrder yansTaskOrder;
	private PriceItem priceItem;
	private Integer itemNum;//数量
	private Double itemSum;//总计
	private String remarks;//备注
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ErsaiTaskPrice_ID")
	@SequenceGenerator(name = "S_ErsaiTaskPrice_ID", sequenceName = "S_ErsaiTaskPrice_ID",allocationSize=1)
	public Long getTaskPriceId() {
		return taskPriceId;
	}
	public void setTaskPriceId(Long taskPriceId) {
		this.taskPriceId = taskPriceId;
	}
	
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
	@JoinColumn(name = "taskOrderId")
	public YansTaskOrder getYansTaskOrder() {
		return yansTaskOrder;
	}
	public void setYansTaskOrder(YansTaskOrder yansTaskOrder) {
		this.yansTaskOrder = yansTaskOrder;
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
