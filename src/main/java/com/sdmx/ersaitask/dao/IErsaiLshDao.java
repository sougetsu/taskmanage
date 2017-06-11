package com.sdmx.ersaitask.dao;

import com.sdmx.ersaitask.entity.ErsaiOrderLsh;
import com.sdmx.framework.dao.IBaseDao;

public interface IErsaiLshDao extends IBaseDao<ErsaiOrderLsh> {
	public String getLsh();
}
