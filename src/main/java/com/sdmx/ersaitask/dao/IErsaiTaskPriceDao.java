package com.sdmx.ersaitask.dao;

import java.util.List;

import com.sdmx.ersaitask.entity.ErsaiTaskPrice;
import com.sdmx.framework.dao.IBaseDao;
import com.sdmx.taskmanage.vo.TaskPriceVO;

public interface IErsaiTaskPriceDao extends IBaseDao<ErsaiTaskPrice> {
	public List<TaskPriceVO> getTaskPriceVO(String id);
}
