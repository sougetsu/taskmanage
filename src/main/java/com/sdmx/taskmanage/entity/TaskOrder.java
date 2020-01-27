package com.sdmx.taskmanage.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sdmx.framework.entity.Dictionary;
import com.sdmx.framework.entity.Member;

@Entity
@Table(name = "TaskOrder", schema="SDMX")
public class TaskOrder implements Serializable {
	private static final long serialVersionUID = -470176884419364014L;
	
	private Long orderId;
	private Dictionary project;//项目id
	//private Dictionary costTopicNo;//成本归集课题号
	private String internalModel;//所内型号
	private Dictionary helpDept;//请求协作部门
	private String applyDept;//申请部门
	private String applyMember;//申请人
	private String applyMemberPhone;//申请人电话
	private Dictionary topic;//课题号
	private String projectManager;//项目负责人
	private String projectManagerPhone;//项目负责人电话
	private String deliverable;//交付物
	private Date wantedEndDate;//希望完成时间
	private Integer attachmentFlag;//是否有附件
	private Integer superviseFlag;//是否监制
	private String superviseUnit;//监制单位
	private Integer controlledPlanFlag;//是否受控详规
	private Integer countersignFlag;//是否会签稿
	private String detailPlanNo;//详规号
	private List<Dictionary> applyContent;//业务申请内容
	private List<Dictionary> checkType;//鉴定方式
	private String applyReason;//申请原因及说明
	private String remarks;//备注
	private String productManagesuggest;//生产部门负责人意见
	private Date createtime;//任务单创建时间
	private int status;//任务单状态
	private String detailRequire;//具体要求 无封装时
	private Member member;//创建人
	private TaskReduction taskReduction;//减薄
	private TaskDicing taskDicing;//划片
	private TaskPackage taskPackage;//封装
	private Set<TaskPrice> taskPriceList = new  HashSet<TaskPrice>();//工单核价内容
	private TaskSchedule taskSchedule;//工单日程
	private Double sumPrice;
	private String lsh;
	private Integer taskOrderType;
	private Integer urgency;
	private Dictionary orderType;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_Order_ID")
	@SequenceGenerator(name = "S_Order_ID", sequenceName = "S_Order_ID",allocationSize=1)
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
	@JoinColumn(name="projectId")
	public Dictionary getProject() {
		return project;
	}
	public void setProject(Dictionary project) {
		this.project = project;
	}
//	@JsonIgnore
//	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
//	@JoinColumn(name="costTopicNoId")
//	public Dictionary getCostTopicNo() {
//		return costTopicNo;
//	}
//	public void setCostTopicNo(Dictionary costTopicNo) {
//		this.costTopicNo = costTopicNo;
//	}
	@Column(length = 100)
	public String getInternalModel() {
		return internalModel;
	}
	public void setInternalModel(String internalModel) {
		this.internalModel = internalModel;
	}
	@JsonIgnore
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
	@JoinColumn(name="helpDept")
	public Dictionary getHelpDept() {
		return helpDept;
	}
	public void setHelpDept(Dictionary helpDept) {
		this.helpDept = helpDept;
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
	@JsonIgnore
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
	@JoinColumn(name="topicId",nullable=false)
	public Dictionary getTopic() {
		return topic;
	}
	public void setTopic(Dictionary topic) {
		this.topic = topic;
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
	public Date getWantedEndDate() {
		return wantedEndDate;
	}
	public void setWantedEndDate(Date wantedEndDate) {
		this.wantedEndDate = wantedEndDate;
	}
	public Integer getAttachmentFlag() {
		return attachmentFlag;
	}
	public void setAttachmentFlag(Integer attachmentFlag) {
		this.attachmentFlag = attachmentFlag;
	}
	public Integer getSuperviseFlag() {
		return superviseFlag;
	}
	public void setSuperviseFlag(Integer superviseFlag) {
		this.superviseFlag = superviseFlag;
	}
	public String getSuperviseUnit() {
		return superviseUnit;
	}
	public void setSuperviseUnit(String superviseUnit) {
		this.superviseUnit = superviseUnit;
	}
	public Integer getControlledPlanFlag() {
		return controlledPlanFlag;
	}
	public void setControlledPlanFlag(Integer controlledPlanFlag) {
		this.controlledPlanFlag = controlledPlanFlag;
	}
	public Integer getCountersignFlag() {
		return countersignFlag;
	}
	public void setCountersignFlag(Integer countersignFlag) {
		this.countersignFlag = countersignFlag;
	}
	public String getDetailPlanNo() {
		return detailPlanNo;
	}
	public void setDetailPlanNo(String detailPlanNo) {
		this.detailPlanNo = detailPlanNo;
	}
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="Order_applyContent", schema="SDMX",joinColumns={@JoinColumn(name="Order_id")},inverseJoinColumns={@JoinColumn(name="dictionary_id")})
	public List<Dictionary> getApplyContent() {
		return applyContent;
	}
	public void setApplyContent(List<Dictionary> applyContent) {
		this.applyContent = applyContent;
	}
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="Order_CheckType", schema="SDMX",joinColumns={@JoinColumn(name="Order_id")},inverseJoinColumns={@JoinColumn(name="dictionary_id")})
	public List<Dictionary> getCheckType() {
		return checkType;
	}
	public void setCheckType(List<Dictionary> checkType) {
		this.checkType = checkType;
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
	@Column(columnDefinition="Date default sysdate",nullable=false)
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Column(length = 1)
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDetailRequire() {
		return detailRequire;
	}
	public void setDetailRequire(String detailRequire) {
		this.detailRequire = detailRequire;
	}
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name="memberId")
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name="taskReductionId")
	public TaskReduction getTaskReduction() {
		return taskReduction;
	}
	public void setTaskReduction(TaskReduction taskReduction) {
		this.taskReduction = taskReduction;
	}
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name="taskDicingId")
	public TaskDicing getTaskDicing() {
		return taskDicing;
	}
	public void setTaskDicing(TaskDicing taskDicing) {
		this.taskDicing = taskDicing;
	}
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name="taskPackageId")
	public TaskPackage getTaskPackage() {
		return taskPackage;
	}
	public void setTaskPackage(TaskPackage taskPackage) {
		this.taskPackage = taskPackage;
	}
	@OneToMany(mappedBy="taskOrder",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	public Set<TaskPrice> getTaskPriceList() {
		return taskPriceList;
	}
	public void setTaskPriceList(Set<TaskPrice> taskPriceList) {
		this.taskPriceList = taskPriceList;
	}
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name="scheduleId")
	public TaskSchedule getTaskSchedule() {
		return taskSchedule;
	}
	public void setTaskSchedule(TaskSchedule taskSchedule) {
		this.taskSchedule = taskSchedule;
	}
	public Double getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}
	 /* 
     * 添加jiage
     */  
    public void addPriceItem(TaskPrice taskPrice) {  
        if (!this.taskPriceList.contains(taskPrice)) {  
            this.taskPriceList.add(taskPrice);  
            taskPrice.setTaskOrder(this);  
        }  
    }  
  
    /* 
     * 删除订单 
     */  
    public void removePriceItem(TaskPrice taskPrice) {  
    	taskPrice.setTaskOrder(null);  
        this.taskPriceList.remove(taskPrice);
    }
    
    public String toLogInfo(){
    	String applycontent = "";
    	List<Dictionary> list = this.getApplyContent();
    	for (Dictionary dictionary : list) {
    		applycontent = applycontent+ " " +dictionary.getValue();
		}
    	String taskReduction ="";
    	if(this.getTaskReduction()!=null)
    	{
    		taskReduction = "圆片批次："+this.getTaskReduction().getReductionNo()+ " 片号:"+this.getTaskReduction().getReductionTabletsNo()+ " 减薄厚度:"+this.getTaskReduction().getReductionThickness() ;
    	}
    	String taskDicing ="";
    	if(this.getTaskDicing()!=null)
    	{
    		taskDicing = "圆片批次："+ this.getTaskDicing().getDicingNo()+ " 片号:"+this.getTaskDicing().getDicingTabletsNo()+"需管芯数量:"+this.getTaskDicing().getDicingTubeNum()+ " 划片方案:"+this.getTaskDicing().getDicingPlan() ;
    	}
    	String taskPackage ="";
    	if(this.getTaskPackage()!=null)
    	{
    		taskPackage = "封装状态:"+this.getTaskPackage().getPackageStatus().get(0).getValue()+
    				" 质量等级:"+this.getTaskPackage().getQualityLevel()+
    				" 圆片批次:"+this.getTaskPackage().getDiscBatch()+
    				" 数 量:"+this.getTaskPackage().getPackageNum()+
    				" 芯片标识:"+this.getTaskPackage().getChipLabel()+ 
    				" 管壳型号:"+this.getTaskPackage().getShellType()+
    				" 压焊图号:" +this.getTaskPackage().getBondNum()+ 
    				" 封装形式:"+this.getTaskPackage().getPackageShape()+
    				" 打标要求:"+this.getTaskPackage().getMarkDemand()+
    				" 使用圆片号:"+this.getTaskPackage().getDiscNum()+
    				" 是否中测/修调:"+this.getTaskPackage().getWaferFlag();
    				
    	}
    	String info = ",工单号："+this.getLsh() + ",申请部门："+this.getApplyDept()+",申请员工号："+this.applyMember+",申请人号码："+this.applyMemberPhone+
    			",申请原因："+this.applyReason+",加急状态："+this.urgency+",交付物："+this.deliverable+",详规号："+this.getDetailPlanNo()+",具体要求："+this.getDetailRequire()+
    			",所内型号："+this.getInternalModel()+",生产调度意见："+this.getProductManagesuggest()+",项目负责人："+this.getProjectManager()+
    			",负责人电话："+this.getProjectManagerPhone()+",备注："+this.getRemarks()+",状态："+this.getStatus()+",监制单位："+this.getSuperviseUnit()+
    			",是否附件：" +this.getAttachmentFlag()+",是否祥规：" +this.getControlledPlanFlag()+",是否会签：" +this.getCountersignFlag()+",总价："+this.getSumPrice()+
    			",是否监制：" +this.getSuperviseFlag()+",协助部门："+this.getHelpDept().getAnnotation()+",项目："+this.getProject().getAnnotation()+",希望完成时间："+this.getWantedEndDate()+
    			",业务申请内容："+applycontent +",减薄信息:"+taskReduction+",划片信息:"+taskDicing+",封装信息:"+taskPackage;
    	return info;
    }
	public String getLsh() {
		return lsh;
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
	}
	public Integer getTaskOrderType() {
		return taskOrderType;
	}
	public void setTaskOrderType(Integer taskOrderType) {
		this.taskOrderType = taskOrderType;
	}
	public Integer getUrgency() {
		return urgency;
	}
	public void setUrgency(Integer urgency) {
		this.urgency = urgency;
	}
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
	@JoinColumn(name="orderTypeId")
	public Dictionary getOrderType() {
		return orderType;
	}
	public void setOrderType(Dictionary orderType) {
		this.orderType = orderType;
	}

	
	
}
