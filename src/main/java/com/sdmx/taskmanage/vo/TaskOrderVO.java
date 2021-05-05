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
	private String electricId;//电路Id
	private String electricName;// 电路名称
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
	private int urgency;
	private int urgencyState;//修改状态
	private String urgencyName;//修改状态
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
	private String stockName; //使用库存
	
	private int mixPackageFlag;// 1 有混合封装。0无混合封装
	private String mpackageStatusIds; // 封装状态Id
	private String mpackageStatusNames; // 封装状态名称
	private String mqualityLevel;// 质量等级;
	private String mdiscBatch;// 圆片批次;
	private Integer mpackageNum;// 数 量
	private String mchipLabel; // 芯片标识
	private String mshellType;// 管壳型号
	private String mbondNum;// 压焊图号
	private String mpackageShape;// 封装形式
	private String mmarkDemand;// 打标要求
	private String mdiscNum; //使用圆片号
	private int mwaferFlag; //是否中测
	private Integer mchipNum;
	private int mstockFlag;
	private String mstockName; //使用库存
	
	private int mcpackageFlag;// 1 有多芯片封装。0无多芯片封装
	private String mcpackageStatusIds; // 封装状态Id
	private String mcpackageStatusNames; // 封装状态名称
	private String mcqualityLevel;// 质量等级;
	private String mcdiscBatch;// 圆片批次;
	private Integer mcpackageNum;// 数 量
	private String mcchipLabel; // 芯片标识
	private String mcshellType;// 管壳型号
	private String mcbondNum;// 压焊图号
	private String mcpackageShape;// 封装形式
	private String mcmarkDemand;// 打标要求
	private String mcdiscNum; //使用圆片号
	private int mcwaferFlag; //是否中测
	private Integer mcchipNum;
	private int mcstockFlag;
	private String mcstockName; //使用库存
	private String orderTypeId;
	private String orderTypeName;
	private Integer productStatus;
	private Integer entrustNum;
	
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
	
	public String getElectricId() {
		return electricId;
	}

	public void setElectricId(String electricId) {
		this.electricId = electricId;
	}

	public String getElectricName() {
		return electricName;
	}

	public void setElectricName(String electricName) {
		this.electricName = electricName;
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
		return (applyReason==null?applyReason:applyReason.replace("	"," "));
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getRemarks() {
		return  (remarks==null?remarks:remarks.replace("	"," "));
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProductManagesuggest() {
		return  (productManagesuggest==null?productManagesuggest:productManagesuggest.replace("	"," "));
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
		return (detailRequire==null?detailRequire:detailRequire.replace("	"," "));
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

	public String getUrgencyName() {
		return urgencyName;
	}

	public void setUrgencyName(String urgencyName) {
		this.urgencyName = urgencyName;
	}

	public String getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(String orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public int getMixPackageFlag() {
		return mixPackageFlag;
	}

	public void setMixPackageFlag(int mixPackageFlag) {
		this.mixPackageFlag = mixPackageFlag;
	}

	public String getMpackageStatusIds() {
		return mpackageStatusIds;
	}

	public void setMpackageStatusIds(String mpackageStatusIds) {
		this.mpackageStatusIds = mpackageStatusIds;
	}

	public String getMpackageStatusNames() {
		return mpackageStatusNames;
	}

	public void setMpackageStatusNames(String mpackageStatusNames) {
		this.mpackageStatusNames = mpackageStatusNames;
	}

	public String getMqualityLevel() {
		return mqualityLevel;
	}

	public void setMqualityLevel(String mqualityLevel) {
		this.mqualityLevel = mqualityLevel;
	}

	public String getMdiscBatch() {
		return mdiscBatch;
	}

	public void setMdiscBatch(String mdiscBatch) {
		this.mdiscBatch = mdiscBatch;
	}

	public Integer getMpackageNum() {
		return mpackageNum;
	}

	public void setMpackageNum(Integer mpackageNum) {
		this.mpackageNum = mpackageNum;
	}

	public String getMchipLabel() {
		return mchipLabel;
	}

	public void setMchipLabel(String mchipLabel) {
		this.mchipLabel = mchipLabel;
	}

	public String getMshellType() {
		return mshellType;
	}

	public void setMshellType(String mshellType) {
		this.mshellType = mshellType;
	}

	public String getMbondNum() {
		return mbondNum;
	}

	public void setMbondNum(String mbondNum) {
		this.mbondNum = mbondNum;
	}

	public String getMpackageShape() {
		return mpackageShape;
	}

	public void setMpackageShape(String mpackageShape) {
		this.mpackageShape = mpackageShape;
	}

	public String getMmarkDemand() {
		return mmarkDemand;
	}

	public void setMmarkDemand(String mmarkDemand) {
		this.mmarkDemand = mmarkDemand;
	}

	public String getMdiscNum() {
		return mdiscNum;
	}

	public void setMdiscNum(String mdiscNum) {
		this.mdiscNum = mdiscNum;
	}

	public int getMwaferFlag() {
		return mwaferFlag;
	}

	public void setMwaferFlag(int mwaferFlag) {
		this.mwaferFlag = mwaferFlag;
	}

	public Integer getMchipNum() {
		return mchipNum;
	}

	public void setMchipNum(Integer mchipNum) {
		this.mchipNum = mchipNum;
	}

	public int getMstockFlag() {
		return mstockFlag;
	}

	public void setMstockFlag(int mstockFlag) {
		this.mstockFlag = mstockFlag;
	}

	public int getMcpackageFlag() {
		return mcpackageFlag;
	}

	public void setMcpackageFlag(int mcpackageFlag) {
		this.mcpackageFlag = mcpackageFlag;
	}

	public String getMcpackageStatusIds() {
		return mcpackageStatusIds;
	}

	public void setMcpackageStatusIds(String mcpackageStatusIds) {
		this.mcpackageStatusIds = mcpackageStatusIds;
	}

	public String getMcpackageStatusNames() {
		return mcpackageStatusNames;
	}

	public void setMcpackageStatusNames(String mcpackageStatusNames) {
		this.mcpackageStatusNames = mcpackageStatusNames;
	}

	public String getMcqualityLevel() {
		return mcqualityLevel;
	}

	public void setMcqualityLevel(String mcqualityLevel) {
		this.mcqualityLevel = mcqualityLevel;
	}

	public String getMcdiscBatch() {
		return mcdiscBatch;
	}

	public void setMcdiscBatch(String mcdiscBatch) {
		this.mcdiscBatch = mcdiscBatch;
	}

	public Integer getMcpackageNum() {
		return mcpackageNum;
	}

	public void setMcpackageNum(Integer mcpackageNum) {
		this.mcpackageNum = mcpackageNum;
	}

	public String getMcchipLabel() {
		return mcchipLabel;
	}

	public void setMcchipLabel(String mcchipLabel) {
		this.mcchipLabel = mcchipLabel;
	}

	public String getMcshellType() {
		return mcshellType;
	}

	public void setMcshellType(String mcshellType) {
		this.mcshellType = mcshellType;
	}

	public String getMcbondNum() {
		return mcbondNum;
	}

	public void setMcbondNum(String mcbondNum) {
		this.mcbondNum = mcbondNum;
	}

	public String getMcpackageShape() {
		return mcpackageShape;
	}

	public void setMcpackageShape(String mcpackageShape) {
		this.mcpackageShape = mcpackageShape;
	}

	public String getMcmarkDemand() {
		return mcmarkDemand;
	}

	public void setMcmarkDemand(String mcmarkDemand) {
		this.mcmarkDemand = mcmarkDemand;
	}

	public String getMcdiscNum() {
		return mcdiscNum;
	}

	public void setMcdiscNum(String mcdiscNum) {
		this.mcdiscNum = mcdiscNum;
	}

	public int getMcwaferFlag() {
		return mcwaferFlag;
	}

	public void setMcwaferFlag(int mcwaferFlag) {
		this.mcwaferFlag = mcwaferFlag;
	}

	public Integer getMcchipNum() {
		return mcchipNum;
	}

	public void setMcchipNum(Integer mcchipNum) {
		this.mcchipNum = mcchipNum;
	}

	public int getMcstockFlag() {
		return mcstockFlag;
	}

	public void setMcstockFlag(int mcstockFlag) {
		this.mcstockFlag = mcstockFlag;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getMstockName() {
		return mstockName;
	}

	public void setMstockName(String mstockName) {
		this.mstockName = mstockName;
	}

	public String getMcstockName() {
		return mcstockName;
	}

	public void setMcstockName(String mcstockName) {
		this.mcstockName = mcstockName;
	}

	public Integer getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
	}

	public Integer getEntrustNum() {
		return entrustNum;
	}

	public void setEntrustNum(Integer entrustNum) {
		this.entrustNum = entrustNum;
	}
	
}
