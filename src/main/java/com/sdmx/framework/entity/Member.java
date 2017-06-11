package com.sdmx.framework.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sdmx.framework.util.annotation.FieldCN;
import com.sdmx.framework.util.validation.constant.Length;

@Entity
@Table(name = "Member", schema="SDMX")
public class Member  implements Serializable {
	private static final long serialVersionUID = 6874206413321214779L;
	/**
	 * 用户ID
	 */
	private Long memberId;
	
	/**
	 * 登录名
	 */
	private String loginName;
	
	/**
	 * 登录密码
	 */
	private String savedPassword;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 电子邮件
	 */
	private String mailAddress;
	
	/**
	 * 登记日期
	 */
	private Date registerDate;
	
	/**
	 * 激活时间
	 */
	private Date activeDate;
	
	/**
	 * 最后登录日期
	 */
	private Date lastLoginDate;
	
	/**
	 * 最后退出日期
	 */
	private Date lastLogoutDate;
	
	/**
	 * 最后一次修改密码日期
	 */
	private Date lastChangePasswordDate;
	
	/**
	 * 最后一次无效密码日期
	 */
	private Date lastInvalidPasswordDate;
	
	/**
	 * 无效密码尝试次数
	 */
	private Integer invalidPasswordAttempt;
	
	/**
	 * 登录成功后的令牌
	 */
	private String onlineToken;
	
	/**
	 * 激活码
	 */
	private String activeCode;
	
	/**
	 * 用户角色
	 */
	private List<Role> roles;
	
	/**
	 * 状态
	 */
	private int status;
	
	/**
	 * 组织机构
	 */
	private Dictionary organization;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_Member_ID")
	@SequenceGenerator(name = "S_Member_ID", sequenceName = "S_Member_ID",allocationSize=1)
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@NotNull(message = "登录名不能为空")
	@Length(min = 2, max = 20, message = "请输入有效的登录名")
	@Column(length = 20,unique=true,nullable=false)
	@FieldCN(fieldCn = "登录名")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@NotNull(message = "密码不能为空")
	@Length(min = 6, max = 16, message = "请输入有效的密码")
	@Column(length = 64)
	@FieldCN(fieldCn = "保存的密码")
	public String getSavedPassword() {
		return savedPassword;
	}

	public void setSavedPassword(String savedPassword) {
		this.savedPassword = savedPassword;
	}

	@NotNull(message = "姓名不能为空")
	@Length(message = "姓名长度不能小于两位不能超过20位")
	@Column(length = 20)
	@FieldCN(fieldCn = "姓名")
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

	@FieldCN(fieldCn = "注册时间")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@FieldCN(fieldCn = "激活时间")
	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	@FieldCN(fieldCn = "最近一次登录时间")
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	@FieldCN(fieldCn = "最近一次注销时间")
	public Date getLastLogoutDate() {
		return lastLogoutDate;
	}

	public void setLastLogoutDate(Date lastLogoutDate) {
		this.lastLogoutDate = lastLogoutDate;
	}

	@FieldCN(fieldCn = "最近一次更换密码时间")
	public Date getLastChangePasswordDate() {
		return lastChangePasswordDate;
	}

	public void setLastChangePasswordDate(Date lastChangePasswordDate) {
		this.lastChangePasswordDate = lastChangePasswordDate;
	}

	@FieldCN(fieldCn = "上次登录失败时间")
	public Date getLastInvalidPasswordDate() {
		return lastInvalidPasswordDate;
	}

	public void setLastInvalidPasswordDate(Date lastInvalidPasswordDate) {
		this.lastInvalidPasswordDate = lastInvalidPasswordDate;
	}

	@FieldCN(fieldCn = "错误密码次数")
	public Integer getInvalidPasswordAttempt() {
		return invalidPasswordAttempt;
	}

	public void setInvalidPasswordAttempt(Integer invalidPasswordAttempt) {
		this.invalidPasswordAttempt = invalidPasswordAttempt;
	}

	@Column(length = 64)
	@FieldCN(fieldCn = "登录成功后的令牌")
	public String getOnlineToken() {
		return onlineToken;
	}

	public void setOnlineToken(String onlineToken) {
		this.onlineToken = onlineToken;
	}

	@Column(length = 255)
	@FieldCN(fieldCn = "激活码")
	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="Member_Role", schema="SDMX",joinColumns={@JoinColumn(name="Member_id")},inverseJoinColumns={@JoinColumn(name="role_id")})
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
	@JoinColumn(name="organization")
	public Dictionary getOrganization() {
		return organization;
	}

	public void setOrganization(Dictionary organization) {
		this.organization = organization;
	}

	@Column(columnDefinition="int default 1",nullable=false)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}