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
@Table(name = "TaskMultiChipPackage", schema="SDMX")
public class TaskMultiChipPackage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3898943870937211324L;
	private Long mcpackageId;
	private List<Dictionary> mcpackageStatus;//封装状态
	private String mcqualityLevel;// 质量等级;
	private String mcdiscBatch;// 圆片批次;
	private Integer mcpackageNum;// 数 量
	private String mcchipLabel; // 芯片标识
	private String mcshellType;// 管壳型号
	private String mcbondNum;// 压焊图号
	private String mcpackageShape;// 封装形式
	private String mcmarkDemand;// 打标要求
	private String mcdiscNum; //使用圆片号
	private int mcwaferFlag; //是否中测
	private int mcchipNum; //需求芯片数量
	private int mcstockFlag; //库存是否满足
	private String mcstockName;//使用库存
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_MultiChip_Package_ID")
	@SequenceGenerator(name = "S_MultiChip_Package_ID", sequenceName = "S_MultiChip_Package_ID",allocationSize=1)
	public Long getMcpackageId() {
		return mcpackageId;
	}
	public void setMcpackageId(Long mcpackageId) {
		this.mcpackageId = mcpackageId;
	}
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="MultiChipPackageStatus", schema="SDMX",joinColumns={@JoinColumn(name="packageId")},inverseJoinColumns={@JoinColumn(name="dictionaryId")})
	public List<Dictionary> getMcpackageStatus() {
		return mcpackageStatus;
	}
	public void setMcpackageStatus(List<Dictionary> mcpackageStatus) {
		this.mcpackageStatus = mcpackageStatus;
	}
	public String getMcqualityLevel() {
		return mcqualityLevel;
	}
	public void setMcqualityLevel(String mcqualityLevel) {
		this.mcqualityLevel = mcqualityLevel;
	}
	public String getMcdiscBatch() {
		return mcdiscBatch;
	}
	public void setMcdiscBatch(String mcdiscBatch) {
		this.mcdiscBatch = mcdiscBatch;
	}
	public Integer getMcpackageNum() {
		return mcpackageNum;
	}
	public void setMcpackageNum(Integer mcpackageNum) {
		this.mcpackageNum = mcpackageNum;
	}
	public String getMcchipLabel() {
		return mcchipLabel;
	}
	public void setMcchipLabel(String mcchipLabel) {
		this.mcchipLabel = mcchipLabel;
	}
	public String getMcshellType() {
		return mcshellType;
	}
	public void setMcshellType(String mcshellType) {
		this.mcshellType = mcshellType;
	}
	public String getMcbondNum() {
		return mcbondNum;
	}
	public void setMcbondNum(String mcbondNum) {
		this.mcbondNum = mcbondNum;
	}
	public String getMcpackageShape() {
		return mcpackageShape;
	}
	public void setMcpackageShape(String mcpackageShape) {
		this.mcpackageShape = mcpackageShape;
	}
	public String getMcmarkDemand() {
		return mcmarkDemand;
	}
	public void setMcmarkDemand(String mcmarkDemand) {
		this.mcmarkDemand = mcmarkDemand;
	}
	public String getMcdiscNum() {
		return mcdiscNum;
	}
	public void setMcdiscNum(String mcdiscNum) {
		this.mcdiscNum = mcdiscNum;
	}
	@Column(columnDefinition="int default 0",nullable=false)
	public int getMcwaferFlag() {
		return mcwaferFlag;
	}
	public void setMcwaferFlag(int mcwaferFlag) {
		this.mcwaferFlag = mcwaferFlag;
	}
	public int getMcchipNum() {
		return mcchipNum;
	}
	public void setMcchipNum(int mcchipNum) {
		this.mcchipNum = mcchipNum;
	}
	@Column(columnDefinition="int default 1",nullable=false)
	public int getMcstockFlag() {
		return mcstockFlag;
	}
	public void setMcstockFlag(int mcstockFlag) {
		this.mcstockFlag = mcstockFlag;
	}
	@Column(nullable=true)
	public String getMcstockName() {
		return mcstockName;
	}
	public void setMcstockName(String mcstockName) {
		this.mcstockName = mcstockName;
	}
	
	
}
