package com.sdmx.certification.vo;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sdmx.framework.entity.Member;
import com.sdmx.framework.util.JsonDateSerializer;
import com.sdmx.framework.util.JsonDateTypeSerializer;

public class CertificationVO {
	
	private int page;
	private int rows;
	private String sort;
	private String order;
	
	public long id;
	public String certificationId;
	public String productName;
	public String productType;
	public String productBatch;
	public String productNum;
	public String testStandard;
	public String testReportId;
	public String qualityStatus;
	public String userUnits;
	public String inspector;
	public Date certificationDate;
	public String remark;
	public Date createTime;
	private Member createMember;//创建人
	private Date certificationStart;// 登记开始日期
	private Date certificationEnd;// 登记结束日期
	
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
	@JsonSerialize(using = JsonDateTypeSerializer.class)
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Member getCreateMember() {
		return createMember;
	}
	public void setCreateMember(Member createMember) {
		this.createMember = createMember;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCertificationStart() {
		return certificationStart;
	}
	public void setCertificationStart(Date certificationStart) {
		this.certificationStart = certificationStart;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCertificationEnd() {
		return certificationEnd;
	}
	public void setCertificationEnd(Date certificationEnd) {
		this.certificationEnd = certificationEnd;
	}
	
	
}
