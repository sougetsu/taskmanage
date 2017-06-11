package com.sdmx.framework.vo;

import java.util.ArrayList;
import java.util.List;

public class SessionInfo implements java.io.Serializable {

	private static final long serialVersionUID = -8563583121738307986L;
	private String userId;// 用户ID
	private String loginName;// 登录名
	private String ip;// 用户IP地址
	private String roleNames;// 角色名称
	private String roleIds;// 角色ID
	private String functionIds;// 功能ID
	private String functionNames;// 功能名称
	private String orgnizationId;// 组织部门ID
	private String orgnizationName;// 组织部门名称
	private List<String> functionUrls = new ArrayList<String>();// 功能地址

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Override
	public String toString() {
		return loginName;
	}

	public String getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	public String getFunctionNames() {
		return functionNames;
	}

	public void setFunctionNames(String functionNames) {
		this.functionNames = functionNames;
	}

	public List<String> getFunctionUrls() {
		return functionUrls;
	}

	public void setFunctionUrls(List<String> functionUrls) {
		this.functionUrls = functionUrls;
	}

	public String getOrgnizationId() {
		return orgnizationId;
	}

	public void setOrgnizationId(String orgnizationId) {
		this.orgnizationId = orgnizationId;
	}

	public String getOrgnizationName() {
		return orgnizationName;
	}

	public void setOrgnizationName(String orgnizationName) {
		this.orgnizationName = orgnizationName;
	}

}
