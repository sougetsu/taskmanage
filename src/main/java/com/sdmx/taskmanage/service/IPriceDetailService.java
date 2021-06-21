package com.sdmx.taskmanage.service;

import com.sdmx.framework.service.IService;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.taskmanage.entity.PriceDetail;
import com.sdmx.taskmanage.vo.PriceDetailVO;

public interface IPriceDetailService extends IService {
	
	
	public DataGrid listPriceDetail(PriceDetailVO priceDetailVO);
	
	public PriceDetail create(PriceDetailVO priceDetailVO);
	
	public PriceDetailVO getPriceDetailById(String priceDetailId);
	
	public void remove(String priceDetailId);
	
	public PriceDetail edit(PriceDetailVO priceDetailVO);
}
