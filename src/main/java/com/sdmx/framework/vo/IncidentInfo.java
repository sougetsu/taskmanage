package com.sdmx.framework.vo;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class IncidentInfo implements java.io.Serializable {
	private static final long serialVersionUID = 182676933949200953L;
	public interface CreateTypeCheck{}
	public interface RedirectTypeCheck{}
	public interface AssignTypeCheck{}
	public interface CallBackCheck{}
	public interface UrgentTypeCheck{}
	private int page;
	private int rows;
	private String sort;
	private String order;
	private String orderId;//工单号
	@NotEmpty(message="主叫号码不能为空",groups = CreateTypeCheck.class) 
	private String inCall;//呼入号码
	@NotEmpty(message="号码归属地不能为空",groups = CreateTypeCheck.class) 
	private String callerLocal;//号码归属地
	private String customName;//客户姓名
	private Date incomeDate;//来电时间
	private int sex;//性别
	private String sexName;//性别名称
	private String contactPhone;//联系电话
	private Date regDate;//登记日期
	private Date registTimeStart;//登记开始日期
	private Date registTimeEnd;//登记结束日期
	private String regName;//登记人
	private Integer status;//工单状态
	private String statusName;//工单状态名称
	@NotEmpty(message="事件类型不能为空",groups = {CreateTypeCheck.class,RedirectTypeCheck.class,UrgentTypeCheck.class}) 
	private String incidentId;//事件类型
	private String incidentName;//事件类型内容
	@NotEmpty(message="接报内容不能为空",groups = CreateTypeCheck.class) 
	@Length(max=500,message="接报内容长度必须在500位以内",groups = {CreateTypeCheck.class,UrgentTypeCheck.class})
	private String incidentContent;
	private String memberId;//登记人ID
	private String lastModifyMemberId;//最后操作人ID
	private Date lastModifyDate;//最后操作时间
	private String clzt;//处理状态（1：全部，2：待处理,3：已完成）
	private String clType;//处理类型（1：待处理查询，2：全部查询）
	@NotEmpty(message="回访情况不能为空",groups = CallBackCheck.class) 
	private String answerReal;//回访属实
	private String answerRealName;//回访属实
	@NotEmpty(message="解答评价不能为空",groups = CallBackCheck.class) 
	private String mark;//回访评价
	private String markName;//回访评价
	@Length(max=100,message="回访备注长度必须在100位以内",groups = CallBackCheck.class)
	private String evaluate;//回访备注
	private int callbackState;//回访状态
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getInCall() {
		return inCall;
	}
	public void setInCall(String inCall) {
		this.inCall = inCall;
	}
	public String getCallerLocal() {
		return callerLocal;
	}
	public void setCallerLocal(String callerLocal) {
		this.callerLocal = callerLocal;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public Date getIncomeDate() {
		return incomeDate;
	}
	public void setIncomeDate(Date incomeDate) {
		this.incomeDate = incomeDate;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getSexName() {
		return sexName;
	}
	public void setSexName(String sexName) {
		this.sexName = sexName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Date getRegistTimeStart() {
		return registTimeStart;
	}
	public void setRegistTimeStart(Date registTimeStart) {
		this.registTimeStart = registTimeStart;
	}
	public Date getRegistTimeEnd() {
		return registTimeEnd;
	}
	public void setRegistTimeEnd(Date registTimeEnd) {
		this.registTimeEnd = registTimeEnd;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}
	public String getIncidentName() {
		return incidentName;
	}
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}
	public String getIncidentContent() {
		return incidentContent;
	}
	public void setIncidentContent(String incidentContent) {
		this.incidentContent = incidentContent;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getLastModifyMemberId() {
		return lastModifyMemberId;
	}
	public void setLastModifyMemberId(String lastModifyMemberId) {
		this.lastModifyMemberId = lastModifyMemberId;
	}
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public String getClzt() {
		return clzt;
	}
	public void setClzt(String clzt) {
		this.clzt = clzt;
	}
	public String getClType() {
		return clType;
	}
	public void setClType(String clType) {
		this.clType = clType;
	}
	public String getAnswerReal() {
		return answerReal;
	}
	public void setAnswerReal(String answerReal) {
		this.answerReal = answerReal;
	}
	public String getAnswerRealName() {
		return answerRealName;
	}
	public void setAnswerRealName(String answerRealName) {
		this.answerRealName = answerRealName;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getMarkName() {
		return markName;
	}
	public void setMarkName(String markName) {
		this.markName = markName;
	}
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	public int getCallbackState() {
		return callbackState;
	}
	public void setCallbackState(int callbackState) {
		this.callbackState = callbackState;
	}
}
