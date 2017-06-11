package com.sdmx.taskmanage.vo;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sdmx.framework.util.JsonDateSerializer;

public class LogVO {
	private String logId;
	private String lshId;
	private String content;
	private Date createtime;
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
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
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	
}
