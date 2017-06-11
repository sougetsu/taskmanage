package com.sdmx.yansTask.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.sdmx.framework.vo.DataGrid;
import com.sdmx.taskmanage.vo.LogVO;
import com.sdmx.taskmanage.vo.PriceCheckVO;
import com.sdmx.taskmanage.vo.TaskPriceVO;
import com.sdmx.taskmanage.vo.TaskScheduleVO;
import com.sdmx.yansTask.entity.YansTaskOrder;
import com.sdmx.yansTask.vo.YansTaskOrderVO;

public interface IYansTaskOrderService {
	public YansTaskOrder create(YansTaskOrderVO yansTaskOrderVO);
	public DataGrid listTaskOrder(YansTaskOrderVO yansTaskOrderVO);
	public DataGrid listSusTaskOrder(YansTaskOrderVO yansTaskOrderVO);
	public YansTaskOrderVO getTaskOrderById(String taskId);
	public List<TaskPriceVO> getTaskPriceListById(String taskId);
	public TaskScheduleVO getTaskScheduleById(String taskId);
	public YansTaskOrder confirmOK(YansTaskOrderVO yansTaskOrderVO);
	public YansTaskOrder confirmNG(YansTaskOrderVO yansTaskOrderVO);
	public YansTaskOrder fixComplete(YansTaskOrderVO yansTaskOrderVO);
	public HttpServletResponse downloadExcel(YansTaskOrderVO yansTaskOrderVO,HttpServletResponse response) throws Exception;
	public YansTaskOrder checkPrice(PriceCheckVO priceCheckVO);
	public int getTaskNum();
	public void remove(String id);
	public DataGrid logList(LogVO logVO);
}
