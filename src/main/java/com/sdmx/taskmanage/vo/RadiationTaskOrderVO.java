package com.sdmx.taskmanage.vo;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sdmx.framework.entity.Member;
import com.sdmx.framework.util.JsonDateSerializer;
import com.sdmx.framework.util.JsonDateTypeSerializer;

public class RadiationTaskOrderVO {

	private int page;
	private int rows;
	private String sort;
	private String order;
	
	private Long orderId;
	private String circuitName;//电路名称
	private String circuitType;//电路型号
	private Integer singleionsFlag;//类别单粒子 
	private Integer totalDoseFlag;//类别总剂量
	private String FlagName; //类别 单粒子/总剂量
	private String singleionsBatch;//单粒子生产批次
	private String totalDoseBatch;//总剂量生产批次
	private String batch;//生产批次 单粒子/总剂量
	private String singleionsSmpNum;//单粒子样品数量
	private String totalDoseSmpNum;//总剂量样品数量
	private Integer singleionsTest;//单粒子委托开发测试系统
	private Integer totalDoseTest;//总剂量委托开发测试系统
	private String microchipsVersion;//芯片版本
	private String reductionNo;//圆片批次
	private String singleionsIndex;//单粒子指标
	private String totalDoseIndex;//总剂量指标
	private String entrustedUnits;//委托监督单位
	private String detailSpecification;//详细规范
	private String detailSpecificationStatus;//详细规范状态
	private String userUnits;//用户单位
	private String workModel;//工程型号
	private String singleionsNo;//单粒子样品编号
	private String singleionsSpareNo;//单粒子备用样品编号
	private String totalDoseNo;//总剂量样品编号
	private String totalDoseCompareNo;//总剂量对比样品编号
	private String testSampleSplMember;//试验样品提供人
	private Date testSampleSplDate;//试验样品提供日期
	private Member member;//创建人
	private Date createtime;//任务单创建时间
	private Date registTimeStart;// 登记开始日期
	private Date registTimeEnd;// 登记结束日期
	private int status;//任务单状态
	private String statusName;//任务单状态
	private String lsh;//任务单号;
	private String clType;//处理类型（1：待处理查询，2：全部查询）
	private int confirmState;//审核状态
	private int fixState;//确认状态
	private int editState;//修改状态
	private int deleteState;//删除权限
	private int OrderStatus;
	private int singleProVal;//单例子产值
	private int totalProVal;//总剂量产值
	private String proVal;//单例子/总剂量
	private int type;//任务单类型
	private String typeName;
	private String topicNoName; //课题号
	private String projectManager;//项目负责人
	private Date projectManagerTime;
	private String productManager;//生产负责人
	private Date productManagerTime;
	private String testManager;//试验负责人
	private Date testManagerTime;
	private String radiationLeader;//试验组长
	private Date radiationLeaderTime;
	private Date completetime;
	private int urgency;
	private int urgencyState;//修改状态
	
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
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getCircuitName() {
		return circuitName;
	}
	public void setCircuitName(String circuitName) {
		this.circuitName = circuitName;
	}
	public String getCircuitType() {
		return circuitType;
	}
	public void setCircuitType(String circuitType) {
		this.circuitType = circuitType;
	}
	public Integer getSingleionsFlag() {
		return singleionsFlag;
	}
	public void setSingleionsFlag(Integer singleionsFlag) {
		this.singleionsFlag = singleionsFlag;
	}
	public Integer getTotalDoseFlag() {
		return totalDoseFlag;
	}
	public void setTotalDoseFlag(Integer totalDoseFlag) {
		this.totalDoseFlag = totalDoseFlag;
	}
	public String getSingleionsBatch() {
		return singleionsBatch;
	}
	public void setSingleionsBatch(String singleionsBatch) {
		this.singleionsBatch = singleionsBatch;
	}
	public String getTotalDoseBatch() {
		return totalDoseBatch;
	}
	public void setTotalDoseBatch(String totalDoseBatch) {
		this.totalDoseBatch = totalDoseBatch;
	}
	public String getSingleionsSmpNum() {
		return singleionsSmpNum;
	}
	public void setSingleionsSmpNum(String singleionsSmpNum) {
		this.singleionsSmpNum = singleionsSmpNum;
	}
	public String getTotalDoseSmpNum() {
		return totalDoseSmpNum;
	}
	public void setTotalDoseSmpNum(String totalDoseSmpNum) {
		this.totalDoseSmpNum = totalDoseSmpNum;
	}
	public Integer getSingleionsTest() {
		return singleionsTest;
	}
	public void setSingleionsTest(Integer singleionsTest) {
		this.singleionsTest = singleionsTest;
	}
	public Integer getTotalDoseTest() {
		return totalDoseTest;
	}
	public void setTotalDoseTest(Integer totalDoseTest) {
		this.totalDoseTest = totalDoseTest;
	}
	public String getMicrochipsVersion() {
		return microchipsVersion;
	}
	public void setMicrochipsVersion(String microchipsVersion) {
		this.microchipsVersion = microchipsVersion;
	}
	public String getReductionNo() {
		return reductionNo;
	}
	public void setReductionNo(String reductionNo) {
		this.reductionNo = reductionNo;
	}
	public String getSingleionsIndex() {
		return singleionsIndex;
	}
	public void setSingleionsIndex(String singleionsIndex) {
		this.singleionsIndex = singleionsIndex;
	}
	public String getTotalDoseIndex() {
		return totalDoseIndex;
	}
	public void setTotalDoseIndex(String totalDoseIndex) {
		this.totalDoseIndex = totalDoseIndex;
	}
	public String getEntrustedUnits() {
		return entrustedUnits;
	}
	public void setEntrustedUnits(String entrustedUnits) {
		this.entrustedUnits = entrustedUnits;
	}
	public String getDetailSpecification() {
		return detailSpecification;
	}
	public void setDetailSpecification(String detailSpecification) {
		this.detailSpecification = detailSpecification;
	}
	public String getDetailSpecificationStatus() {
		return detailSpecificationStatus;
	}
	public void setDetailSpecificationStatus(String detailSpecificationStatus) {
		this.detailSpecificationStatus = detailSpecificationStatus;
	}
	public String getUserUnits() {
		return userUnits;
	}
	public void setUserUnits(String userUnits) {
		this.userUnits = userUnits;
	}
	public String getWorkModel() {
		return workModel;
	}
	public void setWorkModel(String workModel) {
		this.workModel = workModel;
	}
	public String getSingleionsNo() {
		return singleionsNo;
	}
	public void setSingleionsNo(String singleionsNo) {
		this.singleionsNo = singleionsNo;
	}
	public String getSingleionsSpareNo() {
		return singleionsSpareNo;
	}
	public void setSingleionsSpareNo(String singleionsSpareNo) {
		this.singleionsSpareNo = singleionsSpareNo;
	}
	public String getTotalDoseNo() {
		return totalDoseNo;
	}
	public void setTotalDoseNo(String totalDoseNo) {
		this.totalDoseNo = totalDoseNo;
	}
	public String getTotalDoseCompareNo() {
		return totalDoseCompareNo;
	}
	public void setTotalDoseCompareNo(String totalDoseCompareNo) {
		this.totalDoseCompareNo = totalDoseCompareNo;
	}
	public String getTestSampleSplMember() {
		return testSampleSplMember;
	}
	public void setTestSampleSplMember(String testSampleSplMember) {
		this.testSampleSplMember = testSampleSplMember;
	}
	
	public Member getMember() {
		return member;
	}
	
	@JsonSerialize(using = JsonDateTypeSerializer.class)
	public Date getTestSampleSplDate() {
		return testSampleSplDate;
	}
	public void setTestSampleSplDate(Date testSampleSplDate) {
		this.testSampleSplDate = testSampleSplDate;
	}
	public void setMember(Member member) {
		this.member = member;
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
	public int getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		OrderStatus = orderStatus;
	}
	public int getSingleProVal() {
		return singleProVal;
	}
	public void setSingleProVal(int singleProVal) {
		this.singleProVal = singleProVal;
	}
	public int getTotalProVal() {
		return totalProVal;
	}
	public void setTotalProVal(int totalProVal) {
		this.totalProVal = totalProVal;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTopicNoName() {
		return topicNoName;
	}
	public void setTopicNoName(String topicNoName) {
		this.topicNoName = topicNoName;
	}
	public String getFlagName() {
		return FlagName;
	}
	public void setFlagName(String flagName) {
		FlagName = flagName;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getProVal() {
		return proVal;
	}
	public void setProVal(String proVal) {
		this.proVal = proVal;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public Date getProjectManagerTime() {
		return projectManagerTime;
	}
	@JsonSerialize(using = JsonDateTypeSerializer.class)
	public void setProjectManagerTime(Date projectManagerTime) {
		this.projectManagerTime = projectManagerTime;
	}
	public String getProductManager() {
		return productManager;
	}
	public void setProductManager(String productManager) {
		this.productManager = productManager;
	}
	@JsonSerialize(using = JsonDateTypeSerializer.class)
	public Date getProductManagerTime() {
		return productManagerTime;
	}
	public void setProductManagerTime(Date productManagerTime) {
		this.productManagerTime = productManagerTime;
	}
	public String getTestManager() {
		return testManager;
	}
	public void setTestManager(String testManager) {
		this.testManager = testManager;
	}
	@JsonSerialize(using = JsonDateTypeSerializer.class)
	public Date getTestManagerTime() {
		return testManagerTime;
	}
	public void setTestManagerTime(Date testManagerTime) {
		this.testManagerTime = testManagerTime;
	}
	public String getRadiationLeader() {
		return radiationLeader;
	}
	public void setRadiationLeader(String radiationLeader) {
		this.radiationLeader = radiationLeader;
	}
	@JsonSerialize(using = JsonDateTypeSerializer.class)
	public Date getRadiationLeaderTime() {
		return radiationLeaderTime;
	}
	public void setRadiationLeaderTime(Date radiationLeaderTime) {
		this.radiationLeaderTime = radiationLeaderTime;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@JsonSerialize(using = JsonDateTypeSerializer.class)
	public Date getCompletetime() {
		return completetime;
	}
	public void setCompletetime(Date completetime) {
		this.completetime = completetime;
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
	
}
