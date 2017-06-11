package com.sdmx.ersaitask.service;

import com.sdmx.framework.service.IService;
import com.sdmx.framework.vo.DataGrid;

public interface IErsaiTaskPriceService extends IService {
	public DataGrid getTaskPriceVO(String id);
}
