package com.sdmx.framework.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdmx.framework.service.IFunctionService;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.FunctionVO;
import com.sdmx.framework.vo.JsonResult;

@Controller
@RequestMapping(value = "/function")
public class FunctionController {

	private IFunctionService functionService;

	public IFunctionService getFunctionService() {
		return functionService;
	}

	@Autowired
	public void setFunctionService(IFunctionService functionService) {
		this.functionService = functionService;
	}

	/**
	 * 新增功能
	 * 
	 * @param functionvo
	 * @return
	 */
	@RequestMapping(value = "/create")
	@ResponseBody
	public JsonResult create(@Valid FunctionVO functionvo,BindingResult errors) {
		if(errors.hasErrors()){
			String code = errors.getFieldError().getCode();
	        String msg = errors.getFieldError().getDefaultMessage();
			return JsonResult.failed(msg,code);
		}
		if(functionService.checkExist(functionvo)){
			return JsonResult.failed("该功能名称已存在",null);
		}
		functionService.addNewFunction(functionvo);
		return JsonResult.success("新增功能成功！", null);
	}

	/**
	 * 删除功能
	 * 
	 * @param functionvo
	 * @return
	 */
	@RequestMapping(value = "/remove")
	@ResponseBody
	public JsonResult deleteFunction(FunctionVO functionvo) {
		functionService.deleteFunction(functionvo.getId());
		return JsonResult.success("删除成功！", null);
	}

	/**
	 * 修改功能
	 * 
	 * @param functionvo
	 * @return
	 */
	@RequestMapping(value = "/modify")
	@ResponseBody
	public JsonResult updateFunction(@Valid FunctionVO functionvo,BindingResult errors) {
		if(errors.hasErrors()){
			String code = errors.getFieldError().getCode();
	        String msg = errors.getFieldError().getDefaultMessage();
			return JsonResult.failed(msg,code);
		}
		if(functionService.checkExist(functionvo)){
			return JsonResult.failed("该功能名称已存在",null);
		}
		functionService.updateFunction(functionvo);
		return JsonResult.success("修改成功！", null);
	}

	/**
	 * 查询功能（列表）
	 * 
	 * @param functionvo
	 * @return
	 */
	@RequestMapping(value = "/listfunc")
	@ResponseBody
	public DataGrid functionList(FunctionVO functionvo) {
		DataGrid dg = functionService.getFunctions(functionvo);
		return dg;
	}

	/**
	 * 查询功能（树形）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public List<FunctionVO> functionList() {
		List<FunctionVO> dg = functionService.list();
		return dg;
	}

}
