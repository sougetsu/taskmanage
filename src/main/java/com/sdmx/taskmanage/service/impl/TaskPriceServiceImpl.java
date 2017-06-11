package com.sdmx.taskmanage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdmx.framework.vo.DataGrid;
import com.sdmx.taskmanage.dao.ITaskPriceDao;
import com.sdmx.taskmanage.service.ITaskPriceService;
import com.sdmx.taskmanage.vo.TaskPriceVO;

@Service("taskPriceService")
public class TaskPriceServiceImpl implements ITaskPriceService{
	
	
	@Autowired
	private ITaskPriceDao taskPriceDao;
	
	@Override
	public DataGrid getTaskPriceVO(String id) {
		DataGrid dg = new DataGrid();
		List<TaskPriceVO> taskPriceList = taskPriceDao.getTaskPriceVO(id);
		dg.setRows(taskPriceList);
		return dg;
	}

}
