package com.sdmx.yansTask.dao;

import com.sdmx.framework.dao.IBaseDao;
import com.sdmx.yansTask.entity.YansOrderLsh;

public interface IYansLshDao extends IBaseDao<YansOrderLsh> {
	public String getLsh();
}
