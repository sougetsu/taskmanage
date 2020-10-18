package com.sdmx.taskmanage.entity;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sdmx.framework.entity.Dictionary;

@Entity
@Table(name = "TaskPackage")
public class TaskPackage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3898943870937211324L;
	private Long packageId;
	private List<Dictionary> packageStatus;//封装状态
	private String qualityLevel;// 质量等级;
	private String discBatch;// 圆片批次;
	private Integer packageNum;// 数 量
	private String chipLabel; // 芯片标识
	private String shellType;// 管壳型号
	private String bondNum;// 压焊图号
	private String packageShape;// 封装形式
	private String markDemand;// 打标要求
	private String discNum; //使用圆片号
	private int waferFlag; //是否中测
	private String stockName;//使用库存
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_Package_ID")
	@SequenceGenerator(name = "S_Package_ID", sequenceName = "S_Package_ID",allocationSize=1)
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="PackageStatus", schema="SDMX",joinColumns={@JoinColumn(name="packageId")},inverseJoinColumns={@JoinColumn(name="dictionaryId")})
	public List<Dictionary> getPackageStatus() {
		return packageStatus;
	}
	public void setPackageStatus(List<Dictionary> packageStatus) {
		this.packageStatus = packageStatus;
	}
	public String getQualityLevel() {
		return qualityLevel;
	}
	public void setQualityLevel(String qualityLevel) {
		this.qualityLevel = qualityLevel;
	}
	public String getDiscBatch() {
		return discBatch;
	}
	public void setDiscBatch(String discBatch) {
		this.discBatch = discBatch;
	}
	public Integer getPackageNum() {
		return packageNum;
	}
	public void setPackageNum(Integer packageNum) {
		this.packageNum = packageNum;
	}
	public String getChipLabel() {
		return chipLabel;
	}
	public void setChipLabel(String chipLabel) {
		this.chipLabel = chipLabel;
	}
	public String getShellType() {
		return shellType;
	}
	public void setShellType(String shellType) {
		this.shellType = shellType;
	}
	public String getBondNum() {
		return bondNum;
	}
	public void setBondNum(String bondNum) {
		this.bondNum = bondNum;
	}
	public String getPackageShape() {
		return packageShape;
	}
	public void setPackageShape(String packageShape) {
		this.packageShape = packageShape;
	}
	public String getMarkDemand() {
		return markDemand;
	}
	public void setMarkDemand(String markDemand) {
		this.markDemand = markDemand;
	}
	public String getDiscNum() {
		return discNum;
	}
	public void setDiscNum(String discNum) {
		this.discNum = discNum;
	}
	@Column(columnDefinition="int default 0",nullable=false)
	public int getWaferFlag() {
		return waferFlag;
	}
	public void setWaferFlag(int waferFlag) {
		this.waferFlag = waferFlag;
	}
	@Column(nullable=true)
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
	
}
