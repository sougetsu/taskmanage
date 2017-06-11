package com.sdmx.exportexcel.service;

import javax.servlet.http.HttpServletResponse;

import com.sdmx.exportexcel.dto.ExcelDateInfo;
import com.sdmx.framework.service.IService;

public interface IExportService extends IService {

	/**
	 * 导出
	 * @param excelDateInfo
	 * @return
	 * @throws Exception
	 */
	public HttpServletResponse export(ExcelDateInfo excelDateInfo,HttpServletResponse response) throws Exception;
}
