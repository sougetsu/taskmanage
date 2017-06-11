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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sdmx.framework.util.annotation.FieldCN;
import com.sdmx.framework.util.validation.constant.Length;
/**
 * 字典表
 * @author JustDance
 * @version 0.0.1
 */
@Entity
@Table(name = "Dictionary", schema="SDMX")
public class Dictionary implements Serializable{

	private static final long serialVersionUID = 3478634563712919709L;
	
	/**
	 * 唯一主键
	 */
	private Long dictionaryId;
	
	/**
	 * 部门
	 */
	private Dictionary departDicId;
	
	/**
	 * 父级
	 */
	private Dictionary dictionary;
	
	/**
	 * 类型编号 四位
	 */
	private String categoryNO;
	
	/**
	 * 编码编号 四位
	 */
	private String codeNO;
	
	/**
	 * 显示字符
	 */
	private String value;
	
	/**
	 * 注释
	 */
	private String annotation;
	
	/**
	 * 显示状态 
	 */
	private String display;
	
	/**
	 * 排序
	 */
	private Integer seq;

	/**
	 * 当前信息状态
	 */
	private String state;
	
	private List<Dictionary> dictionarys;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_Dictionary_ID")
	@SequenceGenerator(name = "S_Dictionary_ID", sequenceName = "S_Dictionary_ID",allocationSize=1)
	@Column(name="dictionaryId")
	public Long getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(Long dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid")
	public Dictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "departId")
	public Dictionary getDepartDicId() {
		return departDicId;
	}

	public void setDepartDicId(Dictionary departDicId) {
		this.departDicId = departDicId;
	}

	@Length(min = 4, max = 4, message = "类别编号长度不能超过4位")
	@Column(length = 4,nullable=false)
	@FieldCN(fieldCn = "类别编号")
	public String getCategoryNO() {
		return categoryNO;
	}

	public void setCategoryNO(String categoryNO) {
		this.categoryNO = categoryNO;
	}
	
	@Length(min = 4, max = 4, message = "编码长度不能超过4位")
	@Column(length = 4)
	@FieldCN(fieldCn = "编码")
	public String getCodeNO() {
		return codeNO;
	}

	public void setCodeNO(String codeNO) {
		this.codeNO = codeNO;
	}

	@Length(min = 1, max = 10, message = "值长度不能超过10位")
	@Column(length = 10)
	@FieldCN(fieldCn = "值")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Length(min = 0, max = 20, message = "注释长度不能超过20位")
	@Column(length = 20,nullable=false)
	@FieldCN(fieldCn = "注释")
	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	@Length(min = 1, max = 1, message = "显示状态")
	@Column(length = 1,nullable=false)
	@FieldCN(fieldCn = "显示状态")
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	@Column(nullable=false)
	@FieldCN(fieldCn = "排序")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@Length(min = 1, max = 1, message = "状态")
	@Column(length = 1,nullable=false)
	@FieldCN(fieldCn = "状态")
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}

	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY,mappedBy = "dictionary")
	public List<Dictionary> getDictionarys() {
		return dictionarys;
	}

	public void setDictionarys(List<Dictionary> dictionarys) {
		this.dictionarys = dictionarys;
	}
}
