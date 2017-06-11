package com.sdmx.yansTask.dao;

import java.util.List;

import com.sdmx.framework.dao.IBaseDao;
import com.sdmx.taskmanage.vo.TaskPriceVO;
import com.sdmx.yansTask.entity.YansTaskPrice;

public interface IYansTaskPriceDao extends IBaseDao<YansTaskPrice> {
	public List<TaskPriceVO> getTaskPriceVO(String id);
}
