package com.sdmx.taskmanage.dao;

import com.sdmx.framework.dao.IBaseDao;
import com.sdmx.taskmanage.entity.OrderLsh;

public interface ILshDao extends IBaseDao<OrderLsh> {
	public String getLsh();
}
