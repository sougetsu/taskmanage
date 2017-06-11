package com.sdmx.framework.entity;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sdmx.framework.util.annotation.FieldCN;
import com.sdmx.framework.util.validation.constant.Length;

@Entity
@Table(name = "Function", schema="SDMX")
public class Function implements Serializable {

	private static final long serialVersionUID = -2561932260780050092L;

	private Long functionId;// 功能Id
	private String functionName;// 功能名称
	private String description;// 描述
	private String url;// 功能地址
	private String className;// 类名
	private String method;// 方法
	private Integer status;// 状态
	private Menu menu;// 所属菜单
	private List<Role> roles;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_Function_ID")
	@SequenceGenerator(name = "S_Function_ID", sequenceName = "S_Function_ID",allocationSize=1)
	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	@NotNull(message = "功能点名称不能为空")
	@Length(min = 1, max = 20, message = "功能点名称长度不能大于20")
	@Column(length = 20)
	@FieldCN(fieldCn = "功能点名称")
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Length(min = 3, max = 64, message = "功能点描述长度不能小于3位大于64位")
	@Column(length = 64)
	@FieldCN(fieldCn = "功能点描述")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Length(min = 0, max = 64, message = "URL 长度不能超过64")
	@Column(length = 64)
	@FieldCN(fieldCn = "特征资源路径")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Length(min = 0, max = 128, message = "全路径类名长度不能超过128位")
	@Column(length = 128)
	@FieldCN(fieldCn = "全路径类名")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Length(min = 0, max = 40, message = "业务接口方法长度不能超过40")
	@Column(length = 40)
	@FieldCN(fieldCn = "业务接口方法")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@FieldCN(fieldCn = "可用性")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name = "MENU_ID")
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="Role_function",joinColumns={@JoinColumn(name="Function_id")},inverseJoinColumns={@JoinColumn(name="Role_id")})
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}