package com.sdmx.yansTask.service;

import com.sdmx.framework.service.IService;
import com.sdmx.framework.vo.DataGrid;

public interface IYansTaskPriceService extends IService {
	public DataGrid getTaskPriceVO(String id);
}
