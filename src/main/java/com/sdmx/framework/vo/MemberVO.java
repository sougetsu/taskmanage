package com.sdmx.framework.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.sdmx.framework.util.JsonDateSerializer;

public class MemberVO {
	
	private int page;
	private int rows;
	private String sort;
	private String order;
	private String memberIds;
	private String q;
	private String roleNames;
	private String resourceIds;
	private String resourceNames;
	private List<String> resourceUrls = new ArrayList<String>();
	private String createdatetimeStart;
	private String createdatetimeEnd;
	private Long memberId;
	@NotEmpty(message="用户名不能为空") 
	@Length(min=3,max=20,message="用户名长度必须在3-20位")
	private String loginName;
	private String oldPassword;
	@NotEmpty(message="密码不能为空")
	@Length(min=6,max=16,message="密码长度必须在6-16位")
	private String savedPassword;
	@NotEmpty(message = "姓名不能为空")
	@Length(min=2,max=20,message="姓名长度必须在2-20位")
	private String realName;
	@Email(message = "邮件格式不正确")
	private String mailAddress;
	@NotEmpty(message="人员所属角色不能为空")
	private String roleIds;
	@NotEmpty(message="人员所属部门不能为空") 
	private String departmentId;
	private String departmentName;
	private Date registerDate;
	private Date activeDate;
	private Date lastLoginDate;
	private Date lastLogoutDate;
	private Date lastChangePasswordDate;
	private Date lastInvalidPasswordDate;
	private Integer invalidPasswordAttempt;
	private String onlineToken;
	private String activeCode;
	private int status;
	private String statusName;

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

	public String getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(String memberIds) {
		this.memberIds = memberIds;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getResourceNames() {
		return resourceNames;
	}

	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
	}

	public List<String> getResourceUrls() {
		return resourceUrls;
	}

	public void setResourceUrls(List<String> resourceUrls) {
		this.resourceUrls = resourceUrls;
	}

	public String getCreatedatetimeStart() {
		return createdatetimeStart;
	}

	public void setCreatedatetimeStart(String createdatetimeStart) {
		this.createdatetimeStart = createdatetimeStart;
	}

	public String getCreatedatetimeEnd() {
		return createdatetimeEnd;
	}

	public void setCreatedatetimeEnd(String createdatetimeEnd) {
		this.createdatetimeEnd = createdatetimeEnd;
	}
	
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getSavedPassword() {
		return savedPassword;
	}

	public void setSavedPassword(String savedPassword) {
		this.savedPassword = savedPassword;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getLastLogoutDate() {
		return lastLogoutDate;
	}

	public void setLastLogoutDate(Date lastLogoutDate) {
		this.lastLogoutDate = lastLogoutDate;
	}

	public Date getLastChangePasswordDate() {
		return lastChangePasswordDate;
	}

	public void setLastChangePasswordDate(Date lastChangePasswordDate) {
		this.lastChangePasswordDate = lastChangePasswordDate;
	}

	public Date getLastInvalidPasswordDate() {
		return lastInvalidPasswordDate;
	}

	public void setLastInvalidPasswordDate(Date lastInvalidPasswordDate) {
		this.lastInvalidPasswordDate = lastInvalidPasswordDate;
	}

	public Integer getInvalidPasswordAttempt() {
		return invalidPasswordAttempt;
	}

	public void setInvalidPasswordAttempt(Integer invalidPasswordAttempt) {
		this.invalidPasswordAttempt = invalidPasswordAttempt;
	}

	public String getOnlineToken() {
		return onlineToken;
	}

	public void setOnlineToken(String onlineToken) {
		this.onlineToken = onlineToken;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	
}
