package com.sdmx.taskmanage.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sdmx.framework.entity.Member;

@Entity
@Table(name = "RadiationTaskOrder", schema="SDMX")
public class RadiationTaskOrder implements Serializable {

	private static final long serialVersionUID = -6294276787096470384L;
	private Long orderId;
	private String circuitName;//电路名称
	private String circuitType;//电路型号
	private Integer singleionsFlag;//类别单粒子 
	private Integer totalDoseFlag;//类别总剂量
	private String singleionsBatch;//单粒子生产批次
	private String totalDoseBatch;//总剂量生产批次
	private String singleionsSmpNum;//单粒子样品数量
	private String totalDoseSmpNum;//总剂量样品数量
	private Integer singleionsTest;//单粒子委托开发测试系统
	private Integer totalDoseTest;//总剂量委托开发测试系统
	private String microchipsVersion;//芯片版本
	private String reductionNo;//芯片版本
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
	private int status;//任务单状态
	private String lsh;//任务单号;
	private Integer singleProVal;//单例子产值
	private Integer totalProVal;//总剂量产值
	private Integer type;//任务单类型
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
	private Integer urgency;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_RadiationOrder_ID")
	@SequenceGenerator(name = "S_RadiationOrder_ID", sequenceName = "S_RadiationOrder_ID",allocationSize=1)
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
	public Date getTestSampleSplDate() {
		return testSampleSplDate;
	}
	public void setTestSampleSplDate(Date testSampleSplDate) {
		this.testSampleSplDate = testSampleSplDate;
	}
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name="memberId")
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	@Column(columnDefinition="Date default sysdate",nullable=false)
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
	public String getLsh() {
		return lsh;
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
	}
	public Integer getSingleProVal() {
		return singleProVal;
	}
	public void setSingleProVal(Integer singleProVal) {
		this.singleProVal = singleProVal;
	}
	public Integer getTotalProVal() {
		return totalProVal;
	}
	public void setTotalProVal(Integer totalProVal) {
		this.totalProVal = totalProVal;
	}
	public String getTopicNoName() {
		return topicNoName;
	}
	public void setTopicNoName(String topicNoName) {
		this.topicNoName = topicNoName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getProductManager() {
		return productManager;
	}
	public void setProductManager(String productManager) {
		this.productManager = productManager;
	}
	public String getTestManager() {
		return testManager;
	}
	public void setTestManager(String testManager) {
		this.testManager = testManager;
	}
	public String getRadiationLeader() {
		return radiationLeader;
	}
	public void setRadiationLeader(String radiationLeader) {
		this.radiationLeader = radiationLeader;
	}
	
	public Date getProjectManagerTime() {
		return projectManagerTime;
	}
	public void setProjectManagerTime(Date projectManagerTime) {
		this.projectManagerTime = projectManagerTime;
	}
	public Date getProductManagerTime() {
		return productManagerTime;
	}
	public void setProductManagerTime(Date productManagerTime) {
		this.productManagerTime = productManagerTime;
	}
	public Date getTestManagerTime() {
		return testManagerTime;
	}
	public void setTestManagerTime(Date testManagerTime) {
		this.testManagerTime = testManagerTime;
	}
	public Date getRadiationLeaderTime() {
		return radiationLeaderTime;
	}
	public void setRadiationLeaderTime(Date radiationLeaderTime) {
		this.radiationLeaderTime = radiationLeaderTime;
	}
	
	public Date getCompletetime() {
		return completetime;
	}
	public void setCompletetime(Date completetime) {
		this.completetime = completetime;
	}
	public String toLogInfo(){
    	String info = ",工单号："+this.getLsh() + ",电路名称："+this.circuitName+",电路型号："+this.circuitType+",单粒子："+this.singleionsFlag+
    			",总剂量："+this.totalDoseFlag+",单粒子生产批次："+this.singleionsBatch+",单粒子样品数量："+this.singleionsSmpNum+",单粒子委托开发测试系统："+this.singleionsTest+
    			",总剂量生产批次："+this.getTotalDoseBatch()+",总剂量样品数量："+this.getTotalDoseSmpNum()+",总剂量委托开发测试系统：："+this.getTotalDoseTest()+
    			",芯片版本："+this.getMicrochipsVersion()+",圆片批次："+this.getReductionNo()+",单粒子指标："+this.getSingleionsIndex()+",总剂量指标："+this.getTotalDoseIndex()+
    			",委托监督单位：" +this.getEntrustedUnits()+",详细规范编号：" +this.getDetailSpecification()+",详细规范状态：" +this.getDetailSpecificationStatus()+",用户单位："+this.getUserUnits()+
    			",工程型号：" +this.getWorkModel()+",单例子样品编号："+this.getSingleionsNo()+",单粒子备用样品编号："+this.getSingleionsSpareNo()+",总剂量样品编号："+this.getTotalDoseNo()+
    			",总剂量对比样品编号："+this.getTotalDoseCompareNo() +",试验样品提供人:"+this.getTestSampleSplMember()+",日期:"+this.getTestSampleSplDate()+",单例子产值:"+this.getSingleProVal()+",总剂量产值:"+this.getTotalProVal();
    	return info;
    }
	public Integer getUrgency() {
		return urgency;
	}
	public void setUrgency(Integer urgency) {
		this.urgency = urgency;
	}
	
}
