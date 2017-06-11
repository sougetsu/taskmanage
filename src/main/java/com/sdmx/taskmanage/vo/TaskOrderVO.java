package com.sdmx.taskmanage.vo;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.sdmx.framework.util.JsonDateSerializer;
import com.sdmx.framework.util.JsonDateTypeSerializer;

public class TaskOrderVO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7957353465724758546L;

	public interface CreateTypeCheck {
	}

	private int page;
	private int rows;
	private String sort;
	private String order;

	private String orderId;// 任务单ID
	private String projectId;//项目Id
	private String projectName;// 项目名称
	private String costTopicNoId;//成本归集课题号ID
	private String costTopicNoName;//成本归集课题号
	private String internalModel;// 所内型号
	private String helpDeptId;// 请求协作部门
	private String helpDeptName;// 请求协作部门
	private String applyDept;// 申请部门
	private String applyMember;// 申请人
	private String applyMemberPhone;// 申请人电话
	private String topicNoId;//课题号Id
	@NotEmpty(message = "课题号不能为空", groups = CreateTypeCheck.class)
	@Length(max = 500, message = "课题号长度必须在30位以内", groups = CreateTypeCheck.class)
	private String topicNo;// 课题号
	private String projectManager;// 项目负责人
	private String projectManagerPhone;// 项目负责人电话
	private String deliverable;// 交付物
	private Date wantedEndDate;// 希望完成时间
	private int attachmentFlag;// 是否有附件
	private Integer[] attachids;
	private List<AttachmentVO> attachment;
	private int superviseFlag;// 是否监制
	private String superviseUnit;// 监制单位
	private int controlledPlanFlag;// 是否受控详规
	private int countersignFlag;// 是否会签稿
	private String detailPlanNo;// 详规号
	private String applyContentIds;// 业务申请内容
	private String applyContentNames;// 业务申请内容
	private String checkTypeId;// 鉴定方式
	private String checkTypeName;// 鉴定方式名称
	private String applyReason;// 申请原因及说明
	private String remarks;// 备注
	private String productManagesuggest;// 生产部门负责人意见
	private Date createtime;// 任务单创建时间
	private Date registTimeStart;// 登记开始日期
	private Date registTimeEnd;// 登记结束日期
	private int status;// 任务单状态
	private String statusName;// 任务单状态名
	private String detailRequire;// 具体要求 无封装时
	private int confirmState;//审核状态
	private int priceState;//核价状态
	private int priceEditState;//核价修改状态
	private int fixState;//确认状态
	private int editState;//修改状态
	private int deleteState;//删除权限
	private Double sumPrice;
	private String lsh;
	private String clType;//处理类型（1：待处理查询，2：全部查询）
	
	private int reductionFlag;// 1 有减薄。0无减薄
	private String reductionNo;
	private String reductionTabletsNo;
	private String reductionThickness;
	
	private int dicingFlag;// 1 有划片。0无划片
	private String dicingNo;
	private String dicingTabletsNo;
	private String dicingTubeNum;
	private String dicingPlan;
	
	private int packageFlag;// 1 有封装。0无封装
	private String packageStatusIds; // 封装状态Id
	private String packageStatusNames; // 封装状态名称
	private String qualityLevel;// 质量等级;
	private String discBatch;// 圆片批次;
	private Integer packageNum;// 数 量
	private String chipLabel; // 芯片标识
	private String shellType;// 管壳型号
	private String bondNum;// 压焊图号
	private String packageShape;// 封装形式
	private String markDemand;// 打标要求
	private String discNum; //使用圆片号
	private int waferFlag; //是否中测
	
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getInternalModel() {
		return internalModel;
	}

	public void setInternalModel(String internalModel) {
		this.internalModel = internalModel;
	}

	public String getHelpDeptId() {
		return helpDeptId;
	}

	public void setHelpDeptId(String helpDeptId) {
		this.helpDeptId = helpDeptId;
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

	public String getTopicNo() {
		return topicNo;
	}

	public void setTopicNo(String topicNo) {
		this.topicNo = topicNo;
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

	public String getDeliverable() {
		return deliverable;
	}

	public void setDeliverable(String deliverable) {
		this.deliverable = deliverable;
	}

	@JsonSerialize(using = JsonDateTypeSerializer.class)
	public Date getWantedEndDate() {
		return wantedEndDate;
	}

	public void setWantedEndDate(Date wantedEndDate) {
		this.wantedEndDate = wantedEndDate;
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

	public int getSuperviseFlag() {
		return superviseFlag;
	}

	public void setSuperviseFlag(int superviseFlag) {
		this.superviseFlag = superviseFlag;
	}

	public String getSuperviseUnit() {
		return superviseUnit;
	}

	public void setSuperviseUnit(String superviseUnit) {
		this.superviseUnit = superviseUnit;
	}

	public int getControlledPlanFlag() {
		return controlledPlanFlag;
	}

	public void setControlledPlanFlag(int controlledPlanFlag) {
		this.controlledPlanFlag = controlledPlanFlag;
	}

	public int getCountersignFlag() {
		return countersignFlag;
	}

	public void setCountersignFlag(int countersignFlag) {
		this.countersignFlag = countersignFlag;
	}

	public String getDetailPlanNo() {
		return detailPlanNo;
	}

	public void setDetailPlanNo(String detailPlanNo) {
		this.detailPlanNo = detailPlanNo;
	}

	public String getApplyContentIds() {
		return applyContentIds;
	}

	public void setApplyContentIds(String applyContentIds) {
		this.applyContentIds = applyContentIds;
	}

	public String getCheckTypeId() {
		return checkTypeId;
	}

	public void setCheckTypeId(String checkTypeId) {
		this.checkTypeId = checkTypeId;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
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

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getRegistTimeStart() {
		return registTimeStart;
	}

	public void setRegistTimeStart(Date registTimeStart) {
		this.registTimeStart = registTimeStart;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getRegistTimeEnd() {
		return registTimeEnd;
	}

	public void setRegistTimeEnd(Date registTimeEnd) {
		this.registTimeEnd = registTimeEnd;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getApplyContentNames() {
		return applyContentNames;
	}

	public void setApplyContentNames(String applyContentNames) {
		this.applyContentNames = applyContentNames;
	}

	public String getCheckTypeName() {
		return checkTypeName;
	}

	public void setCheckTypeName(String checkTypeName) {
		this.checkTypeName = checkTypeName;
	}

	public String getHelpDeptName() {
		return helpDeptName;
	}

	public void setHelpDeptName(String helpDeptName) {
		this.helpDeptName = helpDeptName;
	}

	public int getPackageFlag() {
		return packageFlag;
	}

	public void setPackageFlag(int packageFlag) {
		this.packageFlag = packageFlag;
	}

	public String getDetailRequire() {
		return detailRequire;
	}

	public void setDetailRequire(String detailRequire) {
		this.detailRequire = detailRequire;
	}

	public String getPackageStatusIds() {
		return packageStatusIds;
	}

	public void setPackageStatusIds(String packageStatusIds) {
		this.packageStatusIds = packageStatusIds;
	}

	public String getPackageStatusNames() {
		return packageStatusNames;
	}

	public void setPackageStatusNames(String packageStatusNames) {
		this.packageStatusNames = packageStatusNames;
	}

	public String getQualityLevel() {
		return qualityLevel;
	}

	public void setQualityLevel(String qualityLevel) {
		this.qualityLevel = qualityLevel;
	}

	public String getDiscBatch() {
		return discBatch;
	}

	public void setDiscBatch(String discBatch) {
		this.discBatch = discBatch;
	}

	public Integer getPackageNum() {
		return packageNum;
	}

	public void setPackageNum(Integer packageNum) {
		this.packageNum = packageNum;
	}

	public String getChipLabel() {
		return chipLabel;
	}

	public void setChipLabel(String chipLabel) {
		this.chipLabel = chipLabel;
	}

	public String getShellType() {
		return shellType;
	}

	public void setShellType(String shellType) {
		this.shellType = shellType;
	}

	public String getBondNum() {
		return bondNum;
	}

	public void setBondNum(String bondNum) {
		this.bondNum = bondNum;
	}

	public String getPackageShape() {
		return packageShape;
	}

	public void setPackageShape(String packageShape) {
		this.packageShape = packageShape;
	}

	public String getMarkDemand() {
		return markDemand;
	}

	public void setMarkDemand(String markDemand) {
		this.markDemand = markDemand;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getCostTopicNoId() {
		return costTopicNoId;
	}

	public void setCostTopicNoId(String costTopicNoId) {
		this.costTopicNoId = costTopicNoId;
	}

	public String getCostTopicNoName() {
		return costTopicNoName;
	}

	public void setCostTopicNoName(String costTopicNoName) {
		this.costTopicNoName = costTopicNoName;
	}

	public String getTopicNoId() {
		return topicNoId;
	}

	public void setTopicNoId(String topicNoId) {
		this.topicNoId = topicNoId;
	}

	public String getReductionNo() {
		return reductionNo;
	}

	public void setReductionNo(String reductionNo) {
		this.reductionNo = reductionNo;
	}

	public String getReductionTabletsNo() {
		return reductionTabletsNo;
	}

	public void setReductionTabletsNo(String reductionTabletsNo) {
		this.reductionTabletsNo = reductionTabletsNo;
	}

	public String getReductionThickness() {
		return reductionThickness;
	}

	public void setReductionThickness(String reductionThickness) {
		this.reductionThickness = reductionThickness;
	}

	public String getDicingNo() {
		return dicingNo;
	}

	public void setDicingNo(String dicingNo) {
		this.dicingNo = dicingNo;
	}

	public String getDicingTabletsNo() {
		return dicingTabletsNo;
	}

	public void setDicingTabletsNo(String dicingTabletsNo) {
		this.dicingTabletsNo = dicingTabletsNo;
	}

	public String getDicingTubeNum() {
		return dicingTubeNum;
	}

	public void setDicingTubeNum(String dicingTubeNum) {
		this.dicingTubeNum = dicingTubeNum;
	}

	public String getDicingPlan() {
		return dicingPlan;
	}

	public void setDicingPlan(String dicingPlan) {
		this.dicingPlan = dicingPlan;
	}

	public int getReductionFlag() {
		return reductionFlag;
	}

	public void setReductionFlag(int reductionFlag) {
		this.reductionFlag = reductionFlag;
	}

	public int getDicingFlag() {
		return dicingFlag;
	}

	public void setDicingFlag(int dicingFlag) {
		this.dicingFlag = dicingFlag;
	}

	public int getFixState() {
		return fixState;
	}

	public void setFixState(int fixState) {
		this.fixState = fixState;
	}

	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}

	public int getEditState() {
		return editState;
	}

	public void setEditState(int editState) {
		this.editState = editState;
	}

	public int getPriceEditState() {
		return priceEditState;
	}

	public void setPriceEditState(int priceEditState) {
		this.priceEditState = priceEditState;
	}

	public int getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(int deleteState) {
		this.deleteState = deleteState;
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

	public String getDiscNum() {
		return discNum;
	}

	public void setDiscNum(String discNum) {
		this.discNum = discNum;
	}

	public int getWaferFlag() {
		return waferFlag;
	}

	public void setWaferFlag(int waferFlag) {
		this.waferFlag = waferFlag;
	}
	
}
