package com.sdmx.taskmanage.vo;


public class TaskPriceVO {
	private String taskPriceId;
	private String itemId;
	private String itemName;//项目名
	private Double price;//单价
	private String chargeUnit;//计价单位
	private int basePrice;//开机费，起步价
	private Integer amount;//数量
	private Double unitcost;//总计
	private String remarks;//备注
	public String getTaskPriceId() {
		return taskPriceId;
	}
	public void setTaskPriceId(String taskPriceId) {
		this.taskPriceId = taskPriceId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
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
	public int getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(int basePrice) {
		this.basePrice = basePrice;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Double getUnitcost() {
		return unitcost;
	}
	public void setUnitcost(Double unitcost) {
		this.unitcost = unitcost;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
