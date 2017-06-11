package com.sdmx.framework.service;

import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.RoleVO;

public interface IRoleService extends IService {
	/**
	 * 增加角色
	 * 
	 * @param role
	 * @return
	 */
	public RoleVO create(RoleVO role);

	/**
	 * 删除角色
	 * 
	 * @param ids
	 */
	public void remove(String ids);

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @return
	 */
	public RoleVO modify(RoleVO role);

	/**
	 * 查询角色列表
	 * 
	 * @param role
	 * @return
	 */
	public DataGrid list(RoleVO role);
	
	/**
	 * 判断角色是否已存在
	 * @param rolevo
	 * @return
	 */
	public boolean checkExist(RoleVO rolevo);
}
