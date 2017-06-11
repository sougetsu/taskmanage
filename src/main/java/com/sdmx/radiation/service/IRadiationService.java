package com.sdmx.radiation.service;

import com.sdmx.framework.service.IService;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.radiation.entity.RadiationTask;
import com.sdmx.radiation.vo.RadiationTaskVO;

public interface IRadiationService extends IService{
	
	public RadiationTask create(RadiationTaskVO radiationTaskVO);
	
	public DataGrid listRadiation(RadiationTaskVO radiationTaskVO);
}
