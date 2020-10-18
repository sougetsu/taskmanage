package com.sdmx.taskmanage.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.sdmx.framework.service.IService;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.taskmanage.entity.TaskOrder;
import com.sdmx.taskmanage.vo.LogVO;
import com.sdmx.taskmanage.vo.PriceCheckVO;
import com.sdmx.taskmanage.vo.TaskOrderVO;
import com.sdmx.taskmanage.vo.TaskPriceVO;
import com.sdmx.taskmanage.vo.TaskScheduleVO;

public interface ITaskOrderService extends IService {
	public DataGrid listTaskOrder(TaskOrderVO taskOrdervo);
	public DataGrid listSusTaskOrder(TaskOrderVO taskOrdervo);
	public TaskOrder create(TaskOrderVO taskOrdervo);
	public TaskOrderVO getTaskOrderById(String taskId);
	public List<TaskPriceVO> getTaskPriceListById(String taskId);
	public TaskScheduleVO getTaskScheduleById(String taskId);
	public TaskOrder confirmOK(TaskOrderVO taskOrdervo);
	public TaskOrder confirmNG(TaskOrderVO taskOrdervo);
	public TaskOrder fixComplete(TaskOrderVO taskOrdervo);
	public HttpServletResponse downloadExcel(TaskOrderVO taskOrdervo,HttpServletResponse response) throws Exception;
	public TaskOrder checkPrice(PriceCheckVO priceCheckVO);
	public int getTaskNum();
	public void remove(String id);
	public void cancel(String id);
	public void copy(String id);
	public DataGrid logList(LogVO logVO);
}
