package com.sdmx.certification.entity;

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
@Table(name = "Certification", schema = "SDMX")
public class Certification {
	private long id;
	private String certificationId;
	private String productName;
	private String productType;
	private String productBatch;
	private String productNum;
	private String testStandard;
	private String testReportId;
	private String qualityStatus;
	private String userUnits;
	private String inspector;
	private Date certificationDate;
	private String remark;
	private Date createTime;
	private Member createMember;
	private Integer status;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_certification_ID")
	@SequenceGenerator(name = "S_certification_ID", sequenceName = "S_certification_ID",allocationSize=1)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCertificationId() {
		return certificationId;
	}
	public void setCertificationId(String certificationId) {
		this.certificationId = certificationId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductBatch() {
		return productBatch;
	}
	public void setProductBatch(String productBatch) {
		this.productBatch = productBatch;
	}
	public String getProductNum() {
		return productNum;
	}
	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
	public String getTestStandard() {
		return testStandard;
	}
	public void setTestStandard(String testStandard) {
		this.testStandard = testStandard;
	}
	public String getTestReportId() {
		return testReportId;
	}
	public void setTestReportId(String testReportId) {
		this.testReportId = testReportId;
	}
	public String getQualityStatus() {
		return qualityStatus;
	}
	public void setQualityStatus(String qualityStatus) {
		this.qualityStatus = qualityStatus;
	}
	public String getUserUnits() {
		return userUnits;
	}
	public void setUserUnits(String userUnits) {
		this.userUnits = userUnits;
	}
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public Date getCertificationDate() {
		return certificationDate;
	}
	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(columnDefinition="Date default sysdate",nullable=false)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name="memberId")
	public Member getCreateMember() {
		return createMember;
	}
	public void setCreateMember(Member createMember) {
		this.createMember = createMember;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
