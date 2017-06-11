package com.sdmx.framework.service;

import java.util.List;

import com.sdmx.framework.vo.MenuVO;
import com.sdmx.framework.vo.SessionInfo;

public interface IMenuService extends IService {
	/**
	 * 增加菜单
	 * 
	 * @param menu
	 * @return
	 */
	public MenuVO create(MenuVO menu);

	/**
	 * 删除菜单
	 * 
	 * @param menuId
	 */
	public void remove(String menuId);

	/**
	 * 修改菜单
	 * 
	 * @param menu
	 * @return
	 */
	public MenuVO modify(MenuVO menu);

	/**
	 * 查询菜单
	 * 
	 * @return
	 */
	public List<MenuVO> list();

	/**
	 * 所有菜单栏
	 * 
	 * @return
	 */
	public List<MenuVO> allTreeNode();
	
	/**
	 * 根据权限生成菜单栏
	 * 
	 * @return
	 */
	public List<MenuVO> treeNode(SessionInfo sessionInfo);
	
	/**
	 * 判断菜单是否已存在
	 * @param menuvo
	 * @return
	 */
	public boolean checkExist(MenuVO menuvo);
}
