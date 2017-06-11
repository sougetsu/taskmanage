package com.sdmx.ersaitask.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdmx.ersaitask.dao.IErsaiTaskPriceDao;
import com.sdmx.ersaitask.service.IErsaiTaskPriceService;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.taskmanage.vo.TaskPriceVO;

@Service("ersaiTaskPriceService")
public class ErsaiTaskPriceServiceImpl implements IErsaiTaskPriceService{
	
	
	@Autowired
	private IErsaiTaskPriceDao ersaiTaskPriceDao;
	
	@Override
	public DataGrid getTaskPriceVO(String id) {
		DataGrid dg = new DataGrid();
		List<TaskPriceVO> taskPriceList = ersaiTaskPriceDao.getTaskPriceVO(id);
		dg.setRows(taskPriceList);
		return dg;
	}

}
