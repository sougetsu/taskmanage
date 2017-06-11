package com.sdmx.taskmanage.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.taskmanage.dao.ITaskOrderDao;
import com.sdmx.taskmanage.entity.TaskOrder;

@Repository("taskOrderDao")
public class TaskOrderDaoImpl  extends BaseDaoImpl<TaskOrder> implements ITaskOrderDao {
	
}
