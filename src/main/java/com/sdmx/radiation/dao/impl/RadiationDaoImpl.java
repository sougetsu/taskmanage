package com.sdmx.radiation.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.radiation.dao.IRadiationDao;
import com.sdmx.radiation.entity.RadiationTask;

@Repository("radiationDao")
public class RadiationDaoImpl extends BaseDaoImpl<RadiationTask> implements IRadiationDao{

}
