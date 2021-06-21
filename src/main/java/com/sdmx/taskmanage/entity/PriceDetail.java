package com.sdmx.taskmanage.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PriceDetail")
public class PriceDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 299113464991173165L;

	private Long priceDetailId;
	private String cpxh;//产品型号
	private String dlmc ;//电路名称
	private String fzlx ;//封装类型
	private String cplb;//产品类别
	private String csjt;//测试机台
	private Double fzPrice;//封装单价(元/只)
	private Double jdghpcsPrice;//鉴定供货批测试费(元/只)
	private Double sxPrice;//筛选费用(元/只)
	private Double jdyzxjcPrice;//鉴定/一致性检测费(元/批)
	private Double ysPrice;//验收/只
	private Double bcsxPrice;//补充筛选
	private Double qtscfPrice;//其他生产费（切筋成型植球植柱）(元/只）
	private Double swhgpcsPrice;//三温合格品测试费(元/只)
	private Double cwcpPrice;//常温产品(元/只)
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_PriceDetail_ID")
	@SequenceGenerator(name = "S_PriceDetail_ID", sequenceName = "S_PriceDetail_ID",allocationSize=1)
	public Long getPriceDetailId() {
		return priceDetailId;
	}
	public void setPriceDetailId(Long priceDetailId) {
		this.priceDetailId = priceDetailId;
	}
	
	@Column(unique=true)
	public String getCpxh() {
		return cpxh;
	}
	public void setCpxh(String cpxh) {
		this.cpxh = cpxh;
	}
	public String getDlmc() {
		return dlmc;
	}
	public void setDlmc(String dlmc) {
		this.dlmc = dlmc;
	}
	public String getFzlx() {
		return fzlx;
	}
	public void setFzlx(String fzlx) {
		this.fzlx = fzlx;
	}
	public String getCplb() {
		return cplb;
	}
	public void setCplb(String cplb) {
		this.cplb = cplb;
	}
	public String getCsjt() {
		return csjt;
	}
	public void setCsjt(String csjt) {
		this.csjt = csjt;
	}
	public Double getFzPrice() {
		return fzPrice;
	}
	public void setFzPrice(Double fzPrice) {
		this.fzPrice = fzPrice;
	}
	public Double getJdghpcsPrice() {
		return jdghpcsPrice;
	}
	public void setJdghpcsPrice(Double jdghpcsPrice) {
		this.jdghpcsPrice = jdghpcsPrice;
	}
	public Double getSxPrice() {
		return sxPrice;
	}
	public void setSxPrice(Double sxPrice) {
		this.sxPrice = sxPrice;
	}
	public Double getJdyzxjcPrice() {
		return jdyzxjcPrice;
	}
	public void setJdyzxjcPrice(Double jdyzxjcPrice) {
		this.jdyzxjcPrice = jdyzxjcPrice;
	}
	public Double getYsPrice() {
		return ysPrice;
	}
	public void setYsPrice(Double ysPrice) {
		this.ysPrice = ysPrice;
	}
	public Double getBcsxPrice() {
		return bcsxPrice;
	}
	public void setBcsxPrice(Double bcsxPrice) {
		this.bcsxPrice = bcsxPrice;
	}
	public Double getQtscfPrice() {
		return qtscfPrice;
	}
	public void setQtscfPrice(Double qtscfPrice) {
		this.qtscfPrice = qtscfPrice;
	}
	public Double getSwhgpcsPrice() {
		return swhgpcsPrice;
	}
	public void setSwhgpcsPrice(Double swhgpcsPrice) {
		this.swhgpcsPrice = swhgpcsPrice;
	}
	public Double getCwcpPrice() {
		return cwcpPrice;
	}
	public void setCwcpPrice(Double cwcpPrice) {
		this.cwcpPrice = cwcpPrice;
	}
}
