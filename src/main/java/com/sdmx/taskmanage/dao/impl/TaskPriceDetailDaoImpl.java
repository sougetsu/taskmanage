package com.sdmx.taskmanage.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.taskmanage.dao.ITaskPriceDetailDao;
import com.sdmx.taskmanage.entity.TaskPriceDetail;

@Repository("taskPriceDetailDao")
public class TaskPriceDetailDaoImpl extends BaseDaoImpl<TaskPriceDetail> implements ITaskPriceDetailDao {
	
}
