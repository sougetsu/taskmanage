package com.sdmx.taskmanage.entity;

import java.io.Serializable;
import java.util.List;

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

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sdmx.framework.entity.Dictionary;

@Entity
@Table(name = "TaskMixPackage", schema="SDMX")
public class TaskMixPackage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3898943870937211324L;
	private Long mpackageId;
	private List<Dictionary> mpackageStatus;//封装状态
	private String mqualityLevel;// 质量等级;
	private String mdiscBatch;// 圆片批次;
	private Integer mpackageNum;// 数 量
	private String mchipLabel; // 芯片标识
	private String mshellType;// 管壳型号
	private String mbondNum;// 压焊图号
	private String mpackageShape;// 封装形式
	private String mmarkDemand;// 打标要求
	private String mdiscNum; //使用圆片号
	private int mwaferFlag; //是否中测
	private int mchipNum; //需求芯片数量
	private int mstockFlag; //库存是否满足
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_Mix_Package_ID")
	@SequenceGenerator(name = "S_Mix_Package_ID", sequenceName = "S_Mix_Package_ID",allocationSize=1)
	public Long getMpackageId() {
		return mpackageId;
	}
	public void setMpackageId(Long mpackageId) {
		this.mpackageId = mpackageId;
	}
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="MixPackageStatus", schema="SDMX",joinColumns={@JoinColumn(name="packageId")},inverseJoinColumns={@JoinColumn(name="dictionaryId")})
	public List<Dictionary> getMpackageStatus() {
		return mpackageStatus;
	}
	public void setMpackageStatus(List<Dictionary> mpackageStatus) {
		this.mpackageStatus = mpackageStatus;
	}
	public String getMqualityLevel() {
		return mqualityLevel;
	}
	public void setMqualityLevel(String mqualityLevel) {
		this.mqualityLevel = mqualityLevel;
	}
	public String getMdiscBatch() {
		return mdiscBatch;
	}
	public void setMdiscBatch(String mdiscBatch) {
		this.mdiscBatch = mdiscBatch;
	}
	public Integer getMpackageNum() {
		return mpackageNum;
	}
	public void setMpackageNum(Integer mpackageNum) {
		this.mpackageNum = mpackageNum;
	}
	public String getMchipLabel() {
		return mchipLabel;
	}
	public void setMchipLabel(String mchipLabel) {
		this.mchipLabel = mchipLabel;
	}
	public String getMshellType() {
		return mshellType;
	}
	public void setMshellType(String mshellType) {
		this.mshellType = mshellType;
	}
	public String getMbondNum() {
		return mbondNum;
	}
	public void setMbondNum(String mbondNum) {
		this.mbondNum = mbondNum;
	}
	public String getMpackageShape() {
		return mpackageShape;
	}
	public void setMpackageShape(String mpackageShape) {
		this.mpackageShape = mpackageShape;
	}
	public String getMmarkDemand() {
		return mmarkDemand;
	}
	public void setMmarkDemand(String mmarkDemand) {
		this.mmarkDemand = mmarkDemand;
	}
	public String getMdiscNum() {
		return mdiscNum;
	}
	public void setMdiscNum(String mdiscNum) {
		this.mdiscNum = mdiscNum;
	}
	@Column(columnDefinition="int default 0",nullable=false)
	public int getMwaferFlag() {
		return mwaferFlag;
	}
	public void setMwaferFlag(int mwaferFlag) {
		this.mwaferFlag = mwaferFlag;
	}
	public int getMchipNum() {
		return mchipNum;
	}
	public void setMchipNum(int mchipNum) {
		this.mchipNum = mchipNum;
	}
	@Column(columnDefinition="int default 1",nullable=false)
	public int getMstockFlag() {
		return mstockFlag;
	}
	public void setMstockFlag(int mstockFlag) {
		this.mstockFlag = mstockFlag;
	}
	
	
	
}
