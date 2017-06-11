package com.sdmx.taskmanage.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "PriceItem")
public class PriceItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6218682402869737168L;
	
	private Long itemId;
	private String itemName;//项目名
	private Double price;//单价
	private String chargeUnit;//计价单位
	private PriceItem priceItem;//上级
	private String cycle;//正常周期
	private String remark;//备注
	private String isleaf;// 是否是目录
	private int basePrice;//开机费，起步价
	private int status;//状态
	private int typeclass;//分类
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_PriceItem_ID")
	@SequenceGenerator(name = "S_PriceItem_ID", sequenceName = "S_PriceItem_ID",allocationSize=1)
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	@Column(length = 50, nullable = false)
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getChargeUnit() {
		return chargeUnit;
	}
	public void setChargeUnit(String chargeUnit) {
		this.chargeUnit = chargeUnit;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid")
	public PriceItem getPriceItem() {
		return priceItem;
	}
	public void setPriceItem(PriceItem priceItem) {
		this.priceItem = priceItem;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	@Column(length = 500)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsleaf() {
		return isleaf;
	}
	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}
	public int getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(int basePrice) {
		this.basePrice = basePrice;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTypeclass() {
		return typeclass;
	}
	public void setTypeclass(int typeclass) {
		this.typeclass = typeclass;
	}
	
}
