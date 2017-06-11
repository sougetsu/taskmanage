package com.sdmx.taskmanage.service;

import com.sdmx.framework.service.IService;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.taskmanage.vo.PriceItemVO;

public interface IPriceItemService extends IService {
	public DataGrid listPriceItem(PriceItemVO priceItemvo); 
	public DataGrid listErsaiPriceItem(PriceItemVO priceItemvo); 
}
