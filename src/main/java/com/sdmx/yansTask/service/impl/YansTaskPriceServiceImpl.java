package com.sdmx.yansTask.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdmx.framework.vo.DataGrid;
import com.sdmx.taskmanage.vo.TaskPriceVO;
import com.sdmx.yansTask.dao.IYansTaskPriceDao;
import com.sdmx.yansTask.service.IYansTaskPriceService;

@Service("yansTaskPriceService")
public class YansTaskPriceServiceImpl implements IYansTaskPriceService{
	
	
	@Autowired
	private IYansTaskPriceDao yansTaskPriceDao;
	
	@Override
	public DataGrid getTaskPriceVO(String id) {
		DataGrid dg = new DataGrid();
		List<TaskPriceVO> taskPriceList = yansTaskPriceDao.getTaskPriceVO(id);
		dg.setRows(taskPriceList);
		return dg;
	}

}
