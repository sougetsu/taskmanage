package com.sdmx.radiation.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * 测试进度
 * @author wangj
 *
 */
@Entity
@Table(name = "RT_TESTSCHEDULE", schema="SDMX")
public class TestSchedule {
	private long scheduleId;
	private int category;//类别(单粒子/总剂量)
	private String type;//性质
	private Date registerDate;//登记
	private Date startDate;//启动
	private Date planDate;//方案
	private Date systemDate;//系统
	private Date testDate;//摸底
	private Date checkDate;//鉴定
	private Date reportDate;//报告
	private String remark;//备注
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_radiationAttachs_ID")
	@SequenceGenerator(name = "S_radiationAttachs_ID", sequenceName = "S_radiationAttachs_ID",allocationSize=1)
	public long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public Date getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}
	public Date getTestDate() {
		return testDate;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
