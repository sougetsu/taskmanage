package com.sdmx.taskmanage.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.taskmanage.dao.ITaskMixPackageDao;
import com.sdmx.taskmanage.entity.TaskMixPackage;
@Repository("taskMixPackageDao")
public class TaskMixPackageDaoImpl extends BaseDaoImpl<TaskMixPackage> implements ITaskMixPackageDao {

}
