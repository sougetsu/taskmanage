package com.sdmx.taskmanage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "OperateLog", schema="SDMX")
public class OperateLog {
	
	private Long logId;
	private String lshId;
	private String content;
	private Date createtime;
	private Long memberId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_OpLog_ID")
	@SequenceGenerator(name = "S_OpLog_ID", sequenceName = "S_OpLog_ID",allocationSize=1)
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public String getLshId() {
		return lshId;
	}
	public void setLshId(String lshId) {
		this.lshId = lshId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(columnDefinition="Date default sysdate",nullable=false)
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
}
