package com.sdmx.taskmanage.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.sdmx.framework.service.IService;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.taskmanage.entity.RadiationTaskOrder;
import com.sdmx.taskmanage.vo.LogVO;
import com.sdmx.taskmanage.vo.PieRate;
import com.sdmx.taskmanage.vo.RadiationTaskOrderVO;

public interface IRadiationmdTaskOrderService extends IService {
	
	public DataGrid listTaskOrder(RadiationTaskOrderVO radiationTaskOrderVO);
	public DataGrid listSusTaskOrder(RadiationTaskOrderVO radiationTaskOrderVO);
	public RadiationTaskOrder create(RadiationTaskOrderVO radiationTaskOrderVO);
	public List<PieRate> getPieRate();
	public RadiationTaskOrderVO getTaskOrderById(String taskId);
	public RadiationTaskOrder confirmOK(RadiationTaskOrderVO radiationTaskOrderVO);
	public RadiationTaskOrder confirmNG(RadiationTaskOrderVO radiationTaskOrderVO);
	public RadiationTaskOrder fixComplete(RadiationTaskOrderVO radiationTaskOrderVO);
	public HttpServletResponse downloadExcel(RadiationTaskOrderVO radiationTaskOrderVO,HttpServletResponse response) throws Exception;
	public int getTaskNum();
	public void remove(String id);
	public DataGrid logList(LogVO logVO);
}
