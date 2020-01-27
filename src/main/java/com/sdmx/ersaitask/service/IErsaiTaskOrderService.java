package com.sdmx.ersaitask.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.sdmx.ersaitask.entity.ErsaiTaskOrder;
import com.sdmx.ersaitask.vo.ErsaiTaskOrderVO;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.taskmanage.vo.LogVO;
import com.sdmx.taskmanage.vo.PriceCheckVO;
import com.sdmx.taskmanage.vo.TaskPriceVO;
import com.sdmx.taskmanage.vo.TaskScheduleVO;

public interface IErsaiTaskOrderService {
	public ErsaiTaskOrder create(ErsaiTaskOrderVO ersaiTaskOrderVO);
	public DataGrid listTaskOrder(ErsaiTaskOrderVO ersaiTaskOrderVO);
	public DataGrid listSusTaskOrder(ErsaiTaskOrderVO ersaiTaskOrderVO);
	public ErsaiTaskOrderVO getTaskOrderById(String taskId);
	public List<TaskPriceVO> getTaskPriceListById(String taskId);
	public TaskScheduleVO getTaskScheduleById(String taskId);
	public ErsaiTaskOrder confirmOK(ErsaiTaskOrderVO ersaiTaskOrderVO);
	public ErsaiTaskOrder confirmNG(ErsaiTaskOrderVO ersaiTaskOrderVO);
	public ErsaiTaskOrder fixComplete(ErsaiTaskOrderVO ersaiTaskOrderVO);
	public HttpServletResponse downloadExcel(ErsaiTaskOrderVO ersaiTaskOrderVO,HttpServletResponse response) throws Exception;
	public ErsaiTaskOrder checkPrice(PriceCheckVO priceCheckVO);
	public int getTaskNum();
	public void remove(String id);
	public void cancel(String id);
	public DataGrid logList(LogVO logVO);
}
