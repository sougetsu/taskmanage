package com.sdmx.framework.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.ILogDao;
import com.sdmx.framework.entity.LogInfo;

@Repository("logDao")
public class LogDaoImpl extends BaseDaoImpl<LogInfo> implements ILogDao{

}
