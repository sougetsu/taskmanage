package com.sdmx.taskmanage.service;

import com.sdmx.framework.service.IService;
import com.sdmx.framework.vo.DataGrid;

public interface ITaskPriceService extends IService {
	public DataGrid getTaskPriceVO(String id);
}
