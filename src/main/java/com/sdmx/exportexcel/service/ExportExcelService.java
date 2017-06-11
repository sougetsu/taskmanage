package com.sdmx.exportexcel.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Service;

import com.sdmx.exportexcel.dto.ExcelDateInfo;
import com.sdmx.framework.util.UtilValidate;

@Service("exportExcelService")
public class ExportExcelService implements IExportService{

	// 准备设置excel工作表的标题
	public HttpServletResponse export(ExcelDateInfo excelDateInfo,HttpServletResponse response)throws Exception {
		WritableWorkbook writableWorkbook = null;
		WritableSheet writableSheet = null;
		
		response.setHeader("Content-Disposition", "inline; filename="  + excelDateInfo.getFileName() + ".xls");  
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");  
		writableWorkbook = createWorkbook(response,writableWorkbook);
		writableSheet = createSheet(writableWorkbook,excelDateInfo.getSheetName());
		insertTitle(excelDateInfo.getTitle(),writableSheet);
		insertColumnTitle(excelDateInfo.getColumnTitle(),writableSheet);
		insertDateInfo(excelDateInfo.getDateInfo(),writableSheet);
		writableWorkbook.write();
		writableWorkbook.close();
		
		return response;
	}
	
	
	/**
	 * 创建文档
	 * @param filePath
	 * @param writableWorkbook
	 */
	private WritableWorkbook createWorkbook(HttpServletResponse response,
			WritableWorkbook writableWorkbook) throws Exception{
		
		writableWorkbook = Workbook.createWorkbook(response.getOutputStream());
		
		return writableWorkbook;
	}
	
	/**
	 * 创建工作薄
	 * @param writableWorkbook
	 * @param sheetName
	 */
	private WritableSheet createSheet(WritableWorkbook writableWorkbook,String sheetName) throws Exception{
		WritableSheet writableSheet = null;
		
		writableSheet = writableWorkbook.createSheet(sheetName, 0);
		
		return writableSheet;
	}
	
	/**
	 * 添加标题
	 * @param title
	 * @param writableSheet
	 * @throws Exception
	 */
	private void insertTitle(String title,WritableSheet writableSheet)throws Exception{
		writableSheet.mergeCells(0, 0, 6, 0);
		Label label = new Label(0, 0,title);
		writableSheet.addCell(label);
	}
	
	/**
	 * 添加列头
	 * @param columnTitle 
	 * @param writableSheet
	 * @throws Exception
	 */
	private void insertColumnTitle(List<String> columnTitle,WritableSheet writableSheet) throws Exception {
		Label label = null;
		for (int columntIndex = 0; columntIndex < columnTitle.size(); columntIndex++) {
			label = new Label(columntIndex, 1, columnTitle.get(columntIndex));
			writableSheet.addCell(label);
		}
	}
	
	/**
	 * 填充Excel 数据
	 * @param dateInfo 数据
	 * @param writableSheet 工作薄
	 * @throws Exception
	 */
	private void insertDateInfo(List<List<String>> dateInfo,WritableSheet writableSheet) throws Exception{
		Label label = null;
		
		for (int rollIndex = 0; rollIndex < dateInfo.size(); rollIndex++) {
			for (int columntIndex = 0; columntIndex < dateInfo.get(rollIndex).size(); columntIndex++) {
				String date = checkObject(dateInfo.get(rollIndex).get(columntIndex));
				label = new Label(columntIndex, rollIndex+2, date);
				writableSheet.addCell(label);
			}
		}
	}
	
	/**
	 * 检查 数据 时候为空
	 * @param object 数据
	 * @return
	 */
	private String checkObject(Object object){
		String result = null;
		if(UtilValidate.isNotEmpty(object)){
			result = object.toString();
		}else{
			result = "";
		}
		return result;
	}

}
