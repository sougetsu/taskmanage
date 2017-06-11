package com.sdmx.radiation.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.JsonResult;
import com.sdmx.radiation.service.IRadiationService;
import com.sdmx.radiation.vo.RadiationTaskVO;

@Controller
@RequestMapping(value = "/radiation")
public class RadiationTaskController {
	
	@Autowired
	private IRadiationService radiationService;
	/**
	 * 日期处理
	 * @param binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**
	 * 新增任务单
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/create")
	@ResponseBody
	public JsonResult create(@Valid RadiationTaskVO radiationTaskVO,
			BindingResult errors) {
		// 验证任务单信息输入
		if (errors.hasErrors()) {
			String code = errors.getFieldError().getCode();
			String msg = errors.getFieldError().getDefaultMessage();// 出错的信息
			return JsonResult.failed(msg, code);
		}
		radiationService.create(radiationTaskVO);
		return JsonResult.success("新增任务单成功！", radiationTaskVO);
	}
	
	/**
	 * 任务单列表查询
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public DataGrid listRadiation(RadiationTaskVO radiationTaskVO) {
		return radiationService.listRadiation(radiationTaskVO);
	}
}
