package com.sdmx.yansTask.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "YansOrderLsh", schema="SDMX")
public class YansOrderLsh {

	private int yearId;
	private int lsh;
	
	@Id
	public int getYearId() {
		return yearId;
	}
	public void setYearId(int yearId) {
		this.yearId = yearId;
	}
	public int getLsh() {
		return lsh;
	}
	public void setLsh(int lsh) {
		this.lsh = lsh;
	}

}
