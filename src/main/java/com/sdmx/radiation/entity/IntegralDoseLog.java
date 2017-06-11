package com.sdmx.radiation.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "RT_INTEGRALDOSE_LOG", schema="SDMX")
public class IntegralDoseLog {
	private long testLogId;
	private Date testDate;//测试日期
	private String testPlace;//测试地点
	private String productBatch;//生产批次
	private String waferBatch;//晶圆批次
	private int amount;//数量
	private String sampleNumber;//样品编号
	private String nature;//性质
	private String producer;//监制
	private String cost;//费用
	private String resultInfo;//结果
	private RadiationTask radiationTask; //所属任务单
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_INTEGRALLOG_ID")
	@SequenceGenerator(name = "S_INTEGRALLOG_ID", sequenceName = "S_INTEGRALLOG_ID",allocationSize=1)
	public long getTestLogId() {
		return testLogId;
	}
	public void setTestLogId(long testLogId) {
		this.testLogId = testLogId;
	}
	public Date getTestDate() {
		return testDate;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	public String getTestPlace() {
		return testPlace;
	}
	public void setTestPlace(String testPlace) {
		this.testPlace = testPlace;
	}
	public String getProductBatch() {
		return productBatch;
	}
	public void setProductBatch(String productBatch) {
		this.productBatch = productBatch;
	}
	public String getWaferBatch() {
		return waferBatch;
	}
	public void setWaferBatch(String waferBatch) {
		this.waferBatch = waferBatch;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getSampleNumber() {
		return sampleNumber;
	}
	public void setSampleNumber(String sampleNumber) {
		this.sampleNumber = sampleNumber;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
	@JoinColumn(name = "radiationTaskId")
	public RadiationTask getRadiationTask() {
		return radiationTask;
	}
	public void setRadiationTask(RadiationTask radiationTask) {
		this.radiationTask = radiationTask;
	}
	
}
