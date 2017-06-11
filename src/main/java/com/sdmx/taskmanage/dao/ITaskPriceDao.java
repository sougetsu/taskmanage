package com.sdmx.taskmanage.dao;

import java.util.List;

import com.sdmx.framework.dao.IBaseDao;
import com.sdmx.taskmanage.entity.TaskPrice;
import com.sdmx.taskmanage.vo.TaskPriceVO;

public interface ITaskPriceDao extends IBaseDao<TaskPrice> {
	public List<TaskPriceVO> getTaskPriceVO(String id);
}
