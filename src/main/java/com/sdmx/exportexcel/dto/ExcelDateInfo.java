package com.sdmx.exportexcel.dto;

import java.util.List;

/**
 * 导出Excel专用Dto
 * @author JustDance
 * @version 1.0.1.0
 * @Date 2013-07-18
 */
public class ExcelDateInfo {

	private String fileName;
	
	private String sheetName;
	
	private String title;
	
	private List<String> columnTitle;
	
	private List<List<String>> dateInfo;

	public String getFileName() {
		return fileName;
	}

	//文件名称 如 “电话按键统计分析数据文档”
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getTitle() {
		return title;
	}

	//文件标题 “电话按键统计分析数据”
	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getColumnTitle() {
		return columnTitle;
	}

	//行标题 
	public void setColumnTitle(List<String> columnTitle) {
		this.columnTitle = columnTitle;
	}

	public List<List<String>> getDateInfo() {
		return dateInfo;
	}

	/**
	 * 数据 二维数组形式
	 * 1A 1B 1C 1D 1F
	 * 2A 2B 2C 2D 3F
	 * 3A 3B 3C 3D 3F
	 * 4A 4B 4C 4D 4F
	 * @param dateInfo
	 */
	public void setDateInfo(List<List<String>> dateInfo) {
		this.dateInfo = dateInfo;
	}
	
}
