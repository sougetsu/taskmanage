package com.sdmx.taskmanage.vo;

import java.util.Date;

public class TaskScheduleVO {
	private String scheduleId;
	private Date pakstartDate;
	private Date pakendDate;
	private Date svstartDate;
	private Date svendDate;
	private Date teststartDate;
	private Date testendDate;
	private Date sxstartDate;
	private Date sxendDate;
	private Date jdstartDate;
	private Date jdendDate;
	private int pakTime;
	private int svTime;
	private int testTime;
	private int sxTime;
	private int jdTime;
	
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
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
	
}
