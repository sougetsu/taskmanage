package com.sdmx.ersaitask.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sdmx.framework.entity.Dictionary;
import com.sdmx.framework.entity.Member;

@Entity
@Table(name = "ErSaiTaskOrder", schema = "SDMX")
public class ErsaiTaskOrder implements Serializable {

	private static final long serialVersionUID = 4985393382774049796L;

	private Long orderId;
	private String taskType;// 任务类型
	private String reportNo;// 通知单号
	private String internalModel;// 所内型号
	private String applyDept;// 申请部门
	private String applyMember;// 申请人
	private String applyMemberPhone;// 申请人电话
	private Dictionary topic;// 课题号
	private String projectManager;// 项目负责人
	private String projectManagerPhone;// 项目负责人电话
	private Dictionary helpDept;// 请求协作部门
	private Date wantedEndDate;// 希望完成时间
	private String applyReason;// 申请原因及说明
	private String detailRequire;// 具体要求二筛条件
	private String remarks;// 备注
	private String productManagesuggest;// 生产部门负责人意见
	private Date createtime;// 任务单创建时间
	private Member member;// 任务单创建人
	private int status;// 任务单状态
	private Set<ErsaiTaskPrice> taskPriceList = new HashSet<ErsaiTaskPrice>();// 工单核价内容
	private ErsaiTaskSchedule ersaiTaskSchedule;// 工单日程
	private Double sumPrice;
	private String lsh;
	private Integer attachmentFlag;// 是否有附件
	private Integer outputFlag;// 电路是否已出库
	private Integer checkFlag;// 是否验收
	private Integer goldcutFlag;// 是否切金
	private String  goldcutNo;// 切金编号
	private Integer urgency;
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ErsaiOrder_ID")
	@SequenceGenerator(name = "S_ErsaiOrder_ID", sequenceName = "S_ErsaiOrder_ID", allocationSize = 1)
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
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

	@JsonIgnore
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "topicId", nullable = false)
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

	@JsonIgnore
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "helpDept")
	public Dictionary getHelpDept() {
		return helpDept;
	}

	public void setHelpDept(Dictionary helpDept) {
		this.helpDept = helpDept;
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

	@Column(columnDefinition = "Date default sysdate", nullable = false)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "ersaiTaskOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<ErsaiTaskPrice> getTaskPriceList() {
		return taskPriceList;
	}

	public void setTaskPriceList(Set<ErsaiTaskPrice> taskPriceList) {
		this.taskPriceList = taskPriceList;
	}

	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "scheduleId")
	public ErsaiTaskSchedule getErsaiTaskSchedule() {
		return ersaiTaskSchedule;
	}

	public void setErsaiTaskSchedule(ErsaiTaskSchedule ersaiTaskSchedule) {
		this.ersaiTaskSchedule = ersaiTaskSchedule;
	}

	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public Integer getAttachmentFlag() {
		return attachmentFlag;
	}

	public void setAttachmentFlag(Integer attachmentFlag) {
		this.attachmentFlag = attachmentFlag;
	}

	public String toLogInfo() {
		String info = "工单号：" + this.getLsh() + "申请部门：" + this.getApplyDept()
				+ "申请员工号：" + this.applyMember + "申请人号码："
				+ this.applyMemberPhone + "申请原因：" + this.applyReason + "具体要求："
				+ this.getDetailRequire() + "所内型号：" + this.getInternalModel()
				+ "生产调度意见：" + this.getProductManagesuggest() + "项目负责人："
				+ this.getProjectManager() + "负责人电话："
				+ this.getProjectManagerPhone() + "备注：" + this.getRemarks()
				+ "状态：" + this.getStatus() + "是否附件：" + this.getAttachmentFlag()
				+ "总价：" + this.getSumPrice() + "协助部门："
				+ this.getHelpDept().getAnnotation() + "希望完成时间："
				+ this.getWantedEndDate();
		return info;
	}
	 /* 
     * 添加jiage
     */  
    public void addPriceItem(ErsaiTaskPrice taskPrice) {  
        if (!this.taskPriceList.contains(taskPrice)) {  
            this.taskPriceList.add(taskPrice);  
            taskPrice.setErsaiTaskOrder(this);  
        }  
    }
    /* 
     * 删除订单 
     */  
    public void removePriceItem(ErsaiTaskPrice taskPrice) {  
    	taskPrice.setErsaiTaskOrder(null);  
        this.taskPriceList.remove(taskPrice);
    }

	public Integer getUrgency() {
		return urgency;
	}

	public void setUrgency(Integer urgency) {
		this.urgency = urgency;
	}
	
	@Column(columnDefinition = "INT default 0")
	public Integer getOutputFlag() {
		return outputFlag;
	}

	public void setOutputFlag(Integer outputFlag) {
		this.outputFlag = outputFlag;
	}
	@Column(columnDefinition = "INT default 0")
	public Integer getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(Integer checkFlag) {
		this.checkFlag = checkFlag;
	}
	@Column(columnDefinition = "INT default 0")
	public Integer getGoldcutFlag() {
		return goldcutFlag;
	}

	public void setGoldcutFlag(Integer goldcutFlag) {
		this.goldcutFlag = goldcutFlag;
	}

	public String getGoldcutNo() {
		return goldcutNo;
	}

	public void setGoldcutNo(String goldcutNo) {
		this.goldcutNo = goldcutNo;
	}
    
}
