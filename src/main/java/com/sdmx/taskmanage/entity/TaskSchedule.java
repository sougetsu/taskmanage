package com.sdmx.taskmanage.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "TaskSchedule")
public class TaskSchedule implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8269818796848598959L;
	private Long taskScheduleId;
	private Date pakstartDate;
	private Date pakendDate;
	private int pakTime;
	private Date svstartDate;
	private Date svendDate;
	private int svTime;
	private Date teststartDate;
	private Date testendDate;
	private int testTime;
	private Date sxstartDate;
	private Date sxendDate;
	private int sxTime;
	private Date jdstartDate;
	private Date jdendDate;
	private int jdTime;
	private TaskOrder taskOrder;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_TaskSchedule_ID")
	@SequenceGenerator(name = "S_TaskSchedule_ID", sequenceName = "S_TaskSchedule_ID",allocationSize=1)
	public Long getTaskScheduleId() {
		return taskScheduleId;
	}
	public void setTaskScheduleId(Long taskScheduleId) {
		this.taskScheduleId = taskScheduleId;
	}
	public Date getPakstartDate() {
		return pakstartDate;
	}
	public void setPakstartDate(Date pakstartDate) {
		this.pakstartDate = pakstartDate;
	}
	public Date getPakendDate() {
		return pakendDate;
	}
	public void setPakendDate(Date pakendDate) {
		this.pakendDate = pakendDate;
	}
	public Date getSvstartDate() {
		return svstartDate;
	}
	public void setSvstartDate(Date svstartDate) {
		this.svstartDate = svstartDate;
	}
	public Date getSvendDate() {
		return svendDate;
	}
	public void setSvendDate(Date svendDate) {
		this.svendDate = svendDate;
	}
	public Date getTeststartDate() {
		return teststartDate;
	}
	public void setTeststartDate(Date teststartDate) {
		this.teststartDate = teststartDate;
	}
	public Date getTestendDate() {
		return testendDate;
	}
	public void setTestendDate(Date testendDate) {
		this.testendDate = testendDate;
	}
	public Date getSxstartDate() {
		return sxstartDate;
	}
	public void setSxstartDate(Date sxstartDate) {
		this.sxstartDate = sxstartDate;
	}
	public Date getSxendDate() {
		return sxendDate;
	}
	public void setSxendDate(Date sxendDate) {
		this.sxendDate = sxendDate;
	}
	public Date getJdstartDate() {
		return jdstartDate;
	}
	public void setJdstartDate(Date jdstartDate) {
		this.jdstartDate = jdstartDate;
	}
	public Date getJdendDate() {
		return jdendDate;
	}
	public void setJdendDate(Date jdendDate) {
		this.jdendDate = jdendDate;
	}
	public int getPakTime() {
		return pakTime;
	}
	public void setPakTime(int pakTime) {
		this.pakTime = pakTime;
	}
	public int getSvTime() {
		return svTime;
	}
	public void setSvTime(int svTime) {
		this.svTime = svTime;
	}
	public int getTestTime() {
		return testTime;
	}
	public void setTestTime(int testTime) {
		this.testTime = testTime;
	}
	public int getSxTime() {
		return sxTime;
	}
	public void setSxTime(int sxTime) {
		this.sxTime = sxTime;
	}
	public int getJdTime() {
		return jdTime;
	}
	public void setJdTime(int jdTime) {
		this.jdTime = jdTime;
	}
	@OneToOne(mappedBy="taskSchedule")
	public TaskOrder getTaskOrder() {
		return taskOrder;
	}
	public void setTaskOrder(TaskOrder taskOrder) {
		this.taskOrder = taskOrder;
	}
	
}
