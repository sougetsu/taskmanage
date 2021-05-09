package com.sdmx.framework.vo;

public class DictionaryInfo {
	private String id;
	private String pid;
	private String text;
	private String categoryNO;
	private String codeNO;
	private String value;
	private String annotation;
	private String expvalue;
	private String display;
	private Integer seq;
	private String state;
	private String departmentId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getText() {
		return (text==null?text:text.replace("	"," "));
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCategoryNO() {
		return categoryNO;
	}
	public void setCategoryNO(String categoryNO) {
		this.categoryNO = categoryNO;
	}
	public String getCodeNO() {
		return codeNO;
	}
	public void setCodeNO(String codeNO) {
		this.codeNO = codeNO;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAnnotation() {
		return (annotation==null?annotation:annotation.replace("	"," "));
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	
	public String getExpvalue() {
		return (expvalue==null?expvalue:expvalue.replace("	"," "));
	}
	public void setExpvalue(String expvalue) {
		this.expvalue = expvalue;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	
}
