package com.sdmx.framework.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdmx.framework.service.IRoleService;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.JsonResult;
import com.sdmx.framework.vo.RoleVO;

@Controller
@RequestMapping(value = "/role")
public class RoleController {
	private IRoleService roleService;

	public IRoleService getRoleService() {
		return roleService;
	}

	@Autowired
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * 角色添加
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/create")
	@ResponseBody
	public JsonResult create(@Valid RoleVO rolevo,BindingResult errors) {
		//验证输入
		if(errors.hasErrors()){
			String code = errors.getFieldError().getCode();
	        String msg = errors.getFieldError().getDefaultMessage();//出错的信息  
			return JsonResult.failed(msg,code);
		}
		if(roleService.checkExist(rolevo)){
			return JsonResult.failed("该角色已存在",null);
		}
		RoleVO r = roleService.create(rolevo);
		return JsonResult.success("添加成功", r);
	}

	/**
	 * 角色删除
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public JsonResult remove(RoleVO role) {
		roleService.remove((role.getIds()));
		return JsonResult.success("删除成功", null);
	}

	/**
	 * 角色修改
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public JsonResult modify(@Valid RoleVO rolevo,BindingResult errors) {
		//验证输入
		if(errors.hasErrors()){
			String code = errors.getFieldError().getCode();
	        String msg = errors.getFieldError().getDefaultMessage();//出错的信息  
			return JsonResult.failed(msg,code);
		}
		if(roleService.checkExist(rolevo)){
			return JsonResult.failed("该角色已存在",null);
		}
		RoleVO u = roleService.modify(rolevo);
		return JsonResult.success("修改成功", u);
	}

	/**
	 * 角色查询列表
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public DataGrid list(RoleVO role) {
		return roleService.list(role);
	}

}
