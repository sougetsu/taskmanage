package com.sdmx.framework.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class RoleVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8342600291451294618L;
	private int page;
	private int rows;
	private String order;
	private String ids;
	private String q;
	private String functionIds;
	private String functionNames;
	private long roleId;
	@NotEmpty(message="角色名不能为空")
	@Length(min=1,max=20,message="角色名长度必须小于20位")
	private String roleName;

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

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
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

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
