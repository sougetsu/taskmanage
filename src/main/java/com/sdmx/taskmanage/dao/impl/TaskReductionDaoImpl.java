package com.sdmx.taskmanage.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.taskmanage.dao.ITaskReductionDao;
import com.sdmx.taskmanage.entity.TaskReduction;

@Repository("taskReductionDao")
public class TaskReductionDaoImpl extends BaseDaoImpl<TaskReduction> implements ITaskReductionDao {

}
