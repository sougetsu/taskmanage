package com.sdmx.taskmanage.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.taskmanage.dao.ITaskPackageDao;
import com.sdmx.taskmanage.entity.TaskPackage;
@Repository("taskPackageDao")
public class TaskPackageDaoImpl extends BaseDaoImpl<TaskPackage> implements ITaskPackageDao {

}
