package com.sdmx.taskmanage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.JsonResult;
import com.sdmx.taskmanage.service.IPriceDetailService;
import com.sdmx.taskmanage.vo.PriceDetailVO;
import com.sdmx.taskmanage.vo.TaskOrderVO;

@Controller
@RequestMapping(value = "/priceDetail")
public class PriceDetailController {
	
	@Autowired
	private IPriceDetailService priceDetailService;
	
	
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
	 * 	价格列表查询
	 * 
	 * @param priceDetailVO
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public DataGrid listPriceDetail(PriceDetailVO priceDetailVO) {
		return priceDetailService.listPriceDetail(priceDetailVO);
	}
	
	/**
	 *	新增价格项
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/create")
	@ResponseBody
	public JsonResult create(PriceDetailVO priceDetailVO,
			BindingResult errors) {
		// 验证任务单信息输入
		if (errors.hasErrors()) {
			String code = errors.getFieldError().getCode();
			String msg = errors.getFieldError().getDefaultMessage();// 出错的信息
			return JsonResult.failed(msg, code);
		}
		priceDetailService.create(priceDetailVO);
		return JsonResult.success("新增任务单成功！", priceDetailVO);
	}
	
	
	/**
	 * 修改后提交
	 * 
	 * @param priceDetailVO
	 * @return
	 */
	@RequestMapping(value = "/editSubmit")
	@ResponseBody
	public JsonResult editSubmit(PriceDetailVO priceDetailVO,
			BindingResult errors) {
		// 验证任务单信息输入
		if (errors.hasErrors()) {
			String code = errors.getFieldError().getCode();
			String msg = errors.getFieldError().getDefaultMessage();// 出错的信息
			return JsonResult.failed(msg, code);
		}
		priceDetailService.edit(priceDetailVO);
		return JsonResult.success("操作成功！", priceDetailVO);
	}
	
	/**
	 * 查询-修改页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/editPage")
	public ModelAndView editPage(String id, HttpServletRequest request) {
		PriceDetailVO tInfo = priceDetailService.getPriceDetailById(id);
		request.setAttribute("priceDetail", tInfo);
		return new ModelAndView("/taskmanage/priceDetailEdit");
	}
	
	/**
	 * 查询-详细页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/detailPage")
	public ModelAndView detailPage(String id, HttpServletRequest request) {
		PriceDetailVO tInfo = priceDetailService.getPriceDetailById(id);
		request.setAttribute("priceDetail", tInfo);
		return new ModelAndView("/taskmanage/priceDetail");
	}
	
	/**
	 * 任务单删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/removePriceDetail")
	@ResponseBody
	public JsonResult removePriceDetail(String id) {
		priceDetailService.remove(id);
		return JsonResult.success("删除成功！", id);
	}
}
