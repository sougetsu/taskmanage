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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sdmx.framework.util.annotation.FieldCN;
import com.sdmx.framework.util.validation.constant.Length;

@Entity
@Table(name = "Role", schema="SDMX")
public class Role implements Serializable{
	private static final long serialVersionUID = -5473041902961314800L;
	private Long roleId;
	private String roleName;
	private String roleDescription;
	private String enabled;
	private List<Member> members;
	private List<Function> functions;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_Role_ID")
	@SequenceGenerator(name = "S_Role_ID", sequenceName = "S_Role_ID",allocationSize=1)
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@NotNull()
	@Length(min = 3, max = 20, message = "角色名不能小于3位大于20位")
	@Column(length = 20)
	@FieldCN(fieldCn = "角色名")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Length(max = 200, message = "角色描述不能超过200位")
	@Column(length = 200)
	@FieldCN(fieldCn = "角色描述")
	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	@Column(length = 1)
	@FieldCN(fieldCn = "是否是系统角色")
	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@JsonIgnore
	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch=FetchType.LAZY ,mappedBy="roles")
	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="Role_function", schema="SDMX",joinColumns={@JoinColumn(name="Role_id")},inverseJoinColumns={@JoinColumn(name="Function_id")})
	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	
	public void removeFunction(Function function){
		if(this.functions.contains(function)){
			this.functions.remove(function);
		}
	}
}