package com.sdmx.yansTask.vo;

import java.util.Date;
import java.util.List;

import com.sdmx.taskmanage.vo.AttachmentVO;

public class YansTaskOrderVO {
	
	private int page;
	private int rows;
	private String sort;
	private String order;
	
	private String orderId;
	private String taskType;//任务类型
	private String reportNo;//通知单号
	private String internalModel;//所内型号
	private String applyDept;//申请部门
	private String applyMember;//申请人
	private String applyMemberPhone;//申请人电话
	private String topicId;//课题号ID
	private String topicName;//课题号
	private String projectManager;//项目负责人
	private String projectManagerPhone;//项目负责人电话
	private String helpDeptId;//请求协作部门
	private String helpDeptName;//请求协作部门
	private Date wantedEndDate;//希望完成时间
	private String applyReason;//申请原因及说明
	private String detailRequire;//具体要求二筛条件
	private String remarks;//备注
	private String productManagesuggest;//生产部门负责人意见
	private Date createtime;//任务单创建时间
	private int status;//任务单状态
	private String statusName;//状态名称
	private int attachmentFlag;// 是否有附件
	private Integer[] attachids;
	private List<AttachmentVO> attachment;
	private Date registTimeStart;// 登记开始日期
	private Date registTimeEnd;// 登记结束日期
	private String lsh;
	private String clType;//处理类型（1：待处理查询，2：全部查询）
	private int confirmState;//审核状态
	private int priceState;//核价状态
	private int priceEditState;//核价修改状态
	private int fixState;//确认状态
	private int editState;//修改状态
	private int deleteState;//删除权限
	private Double sumPrice;
	private int yansFlag;
	private int yansNum;
	private int ersaiFlag;
	private String ersaiLsh;
	private int urgency;
	private int urgencyState;//修改状态
	private int borrow;
	private String borrowState;
	private int borrowEditState; //借库修改状态
	private int goldcutFlag;// 是否切金
	private String  goldcutNo;// 切金编号
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getReportNo() {
		return reportNo;
	}
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	public String getInternalModel() {
		return internalModel;
	}
	public void setInternalModel(String internalModel) {
		this.internalModel = internalModel;
	}
	public String getApplyDept() {
		return applyDept;
	}
	public void setApplyDept(String applyDept) {
		this.applyDept = applyDept;
	}
	public String getApplyMember() {
		return applyMember;
	}
	public void setApplyMember(String applyMember) {
		this.applyMember = applyMember;
	}
	public String getApplyMemberPhone() {
		return applyMemberPhone;
	}
	public void setApplyMemberPhone(String applyMemberPhone) {
		this.applyMemberPhone = applyMemberPhone;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getProjectManagerPhone() {
		return projectManagerPhone;
	}
	public void setProjectManagerPhone(String projectManagerPhone) {
		this.projectManagerPhone = projectManagerPhone;
	}
	public Date getWantedEndDate() {
		return wantedEndDate;
	}
	public void setWantedEndDate(Date wantedEndDate) {
		this.wantedEndDate = wantedEndDate;
	}
	public String getApplyReason() {
		return applyReason;
	}
	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}
	public String getDetailRequire() {
		return detailRequire;
	}
	public void setDetailRequire(String detailRequire) {
		this.detailRequire = detailRequire;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getProductManagesuggest() {
		return productManagesuggest;
	}
	public void setProductManagesuggest(String productManagesuggest) {
		this.productManagesuggest = productManagesuggest;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getHelpDeptId() {
		return helpDeptId;
	}
	public void setHelpDeptId(String helpDeptId) {
		this.helpDeptId = helpDeptId;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getHelpDeptName() {
		return helpDeptName;
	}
	public void setHelpDeptName(String helpDeptName) {
		this.helpDeptName = helpDeptName;
	}
	public int getAttachmentFlag() {
		return attachmentFlag;
	}
	public void setAttachmentFlag(int attachmentFlag) {
		this.attachmentFlag = attachmentFlag;
	}
	public Integer[] getAttachids() {
		return attachids;
	}
	public void setAttachids(Integer[] attachids) {
		this.attachids = attachids;
	}
	public List<AttachmentVO> getAttachment() {
		return attachment;
	}
	public void setAttachment(List<AttachmentVO> attachment) {
		this.attachment = attachment;
	}
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
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getLsh() {
		return lsh;
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
	}
	public String getClType() {
		return clType;
	}
	public void setClType(String clType) {
		this.clType = clType;
	}
	public int getConfirmState() {
		return confirmState;
	}
	public void setConfirmState(int confirmState) {
		this.confirmState = confirmState;
	}
	public int getPriceState() {
		return priceState;
	}
	public void setPriceState(int priceState) {
		this.priceState = priceState;
	}
	public int getPriceEditState() {
		return priceEditState;
	}
	public void setPriceEditState(int priceEditState) {
		this.priceEditState = priceEditState;
	}
	public int getFixState() {
		return fixState;
	}
	public void setFixState(int fixState) {
		this.fixState = fixState;
	}
	public int getEditState() {
		return editState;
	}
	public void setEditState(int editState) {
		this.editState = editState;
	}
	public int getDeleteState() {
		return deleteState;
	}
	public void setDeleteState(int deleteState) {
		this.deleteState = deleteState;
	}
	public Double getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}
	public int getYansFlag() {
		return yansFlag;
	}
	public void setYansFlag(int yansFlag) {
		this.yansFlag = yansFlag;
	}
	public int getYansNum() {
		return yansNum;
	}
	public void setYansNum(int yansNum) {
		this.yansNum = yansNum;
	}
	public int getErsaiFlag() {
		return ersaiFlag;
	}
	public void setErsaiFlag(int ersaiFlag) {
		this.ersaiFlag = ersaiFlag;
	}
	public String getErsaiLsh() {
		return ersaiLsh;
	}
	public void setErsaiLsh(String ersaiLsh) {
		this.ersaiLsh = ersaiLsh;
	}
	public int getUrgency() {
		return urgency;
	}
	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}
	public int getUrgencyState() {
		return urgencyState;
	}
	public void setUrgencyState(int urgencyState) {
		this.urgencyState = urgencyState;
	}
	public int getBorrow() {
		return borrow;
	}
	public void setBorrow(int borrow) {
		this.borrow = borrow;
	}
	public String getBorrowState() {
		return borrowState;
	}
	public void setBorrowState(String borrowState) {
		this.borrowState = borrowState;
	}
	public int getGoldcutFlag() {
		return goldcutFlag;
	}
	public void setGoldcutFlag(int goldcutFlag) {
		this.goldcutFlag = goldcutFlag;
	}
	public String getGoldcutNo() {
		return goldcutNo;
	}
	public void setGoldcutNo(String goldcutNo) {
		this.goldcutNo = goldcutNo;
	}
	public int getBorrowEditState() {
		return borrowEditState;
	}
	public void setBorrowEditState(int borrowEditState) {
		this.borrowEditState = borrowEditState;
	}
	
}
