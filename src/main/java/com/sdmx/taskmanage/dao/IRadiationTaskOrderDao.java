package com.sdmx.taskmanage.dao;

import com.sdmx.framework.dao.IBaseDao;
import com.sdmx.taskmanage.entity.RadiationTaskOrder;

public interface IRadiationTaskOrderDao extends IBaseDao<RadiationTaskOrder> {
	
	public int[] getSumByMonth();
	
	public int[] getSumByMonthMD();
}
