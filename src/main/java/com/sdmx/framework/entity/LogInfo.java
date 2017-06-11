package com.sdmx.framework.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sdmx.framework.util.JsonDateSerializer;
import com.sdmx.framework.util.annotation.FieldCN;

@Entity
@Table(name = "Log", schema="SDMX")
public class LogInfo  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1962721254544129127L;
	private String logId;
	private String memberId;
	private String memberName;
	private String className;
	private String methodName;
	private String argument;
	private String ipAdress;
	private String errMsg;
	private Date operationTime;
	private String flag;
	
	@Id
	@Column(name = "logId", nullable = false, length = 36)
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	
	@Column(length = 20)
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@Column(length = 20)
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	@Column(length = 100,nullable = false)
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@Column(length = 20,nullable = false)
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	@Column(length = 200)
	@FieldCN(fieldCn = "参数")
	public String getArgument() {
		return argument;
	}
	public void setArgument(String argument) {
		this.argument = argument;
	}
	@Column(length = 50)
	@FieldCN(fieldCn = "地址")
	public String getIpAdress() {
		return ipAdress;
	}
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}
	@Column(length = 255)
	@FieldCN(fieldCn = "错误信息")
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	@Column(nullable = false)
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	@Column(length = 1)
	@FieldCN(fieldCn = "日志分类")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
}