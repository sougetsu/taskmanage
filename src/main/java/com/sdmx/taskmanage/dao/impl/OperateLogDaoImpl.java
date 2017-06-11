package com.sdmx.taskmanage.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.taskmanage.dao.IOperateLogDao;
import com.sdmx.taskmanage.entity.OperateLog;

@Repository("operatelogDao")
public class OperateLogDaoImpl extends BaseDaoImpl<OperateLog> implements IOperateLogDao{

}
