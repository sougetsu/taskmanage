package com.sdmx.framework.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdmx.framework.service.IMenuService;
import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.framework.vo.JsonResult;
import com.sdmx.framework.vo.MenuVO;
import com.sdmx.framework.vo.SessionInfo;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {
	private IMenuService menuService;

	public IMenuService getMenuService() {
		return menuService;
	}

	@Autowired
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	/**
	 * 增加菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/create")
	@ResponseBody
	public JsonResult create(@Valid MenuVO menuvo,BindingResult errors) {
		if(errors.hasErrors()){
			String code = errors.getFieldError().getCode();
	        String msg = errors.getFieldError().getDefaultMessage();
			return JsonResult.failed(msg,code);
		}
		if(menuService.checkExist(menuvo)){
			return JsonResult.failed("该菜单名已存在",null);
		}
		menuService.create(menuvo);
		return JsonResult.success("增加菜单成功！", null);
	}

	/**
	 * 删除菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/remove")
	@ResponseBody
	public JsonResult remove(MenuVO menu) {
		menuService.remove(menu.getId());
		return JsonResult.success("删除菜单成功！", null);
	}

	/**
	 * 修改菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/modify")
	@ResponseBody
	public JsonResult modify(@Valid MenuVO menuvo,BindingResult errors) {
		if(errors.hasErrors()){
			String code = errors.getFieldError().getCode();
	        String msg = errors.getFieldError().getDefaultMessage();
			return JsonResult.failed(msg,code);
		}
		if(menuService.checkExist(menuvo)){
			return JsonResult.failed("该菜单名已存在",null);
		}
		MenuVO m = menuService.modify(menuvo);
		return JsonResult.success("修改菜单成功！", m);
	}

	/**
	 * 查询菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public List<MenuVO> list() {
		return menuService.list();
	}

	/**
	 * 生成菜单栏（所有菜单）
	 * 
	 * @return
	 */
	@RequestMapping("/allTreeNode")
	@ResponseBody
	public List<MenuVO> allTreeNode() {
		return menuService.allTreeNode();
	}
	
	/**
	 * 根据权限生成菜单栏
	 * 
	 * @return
	 */
	@RequestMapping("/treeNode")
	@ResponseBody
	public List<MenuVO> treeNode(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ResourceUtil.getSessionInfoName());
		return menuService.treeNode(sessionInfo);
	}
}
