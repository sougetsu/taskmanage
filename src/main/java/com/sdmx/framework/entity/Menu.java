package com.sdmx.framework.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sdmx.framework.util.annotation.FieldCN;
import com.sdmx.framework.util.validation.constant.Length;

@Entity
@Table(name = "Menu", schema="SDMX")
public class Menu implements Serializable{
	private static final long serialVersionUID = -2866939461602284843L;
	private Long menuId;
	private String menuName;
	private String description;
	private String imageUri;
	private String menuUri;
	private String rawParmeters;
	private Integer seq;
	private Menu pmenu;
	private List<Function> functions;//子功能

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_Menu_ID")
	@SequenceGenerator(name = "S_Menu_ID", sequenceName = "S_Menu_ID",allocationSize=1)
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	@Length(min = 0, max = 20, message = "菜单名称长度不能超过20位")
	@Column(length = 20)
	@FieldCN(fieldCn = "菜单名称")
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Length(min = 0, max = 200, message = "菜单描述长度不能超过200位")
	@Column(length = 20)
	@FieldCN(fieldCn = "菜单描述")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Length(min = 0, max = 200, message = "菜单图片长度不能超过200位")
	@Column(length = 200)
	@FieldCN(fieldCn = "菜单图片链接地址")
	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	@Length(min = 0, max = 200, message = "菜单链接地址长度不能超过200位")
	@Column(length = 200)
	@FieldCN(fieldCn = "菜单链接地址")
	public String getMenuUri() {
		return menuUri;
	}

	public void setMenuUri(String menuUri) {
		this.menuUri = menuUri;
	}

	@Column(length = 200)
	public String getRawParmeters() {
		return rawParmeters;
	}

	public void setRawParmeters(String rawParmeters) {
		this.rawParmeters = rawParmeters;
	}
	
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="menu")
	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menuGroup_MenuId")
	public Menu getPmenu() {
		return pmenu;
	}

	public void setPmenu(Menu pmenu) {
		this.pmenu = pmenu;
	}
	
	
}