package com.sdmx.framework.service;

import java.util.List;

import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.FunctionVO;

public interface IFunctionService extends IService {
	/**
	 * 增加功能
	 * 
	 * @param functionvo
	 * @return
	 */
	public FunctionVO addNewFunction(FunctionVO functionvo);

	/**
	 * 删除功能
	 * 
	 * @param functionIds
	 */
	public void deleteFunction(String functionIds);

	/**
	 * 修改功能
	 * 
	 * @param functionvo
	 * @return
	 */
	public FunctionVO updateFunction(FunctionVO functionvo);

	/**
	 * 查询所有功能（列表）
	 * 
	 * @param functionvo
	 * @return
	 */
	public DataGrid getFunctions(FunctionVO functionvo);

	/**
	 * 查询所有功能（树形）
	 * 
	 * @return
	 */
	public List<FunctionVO> list();
	
	/**
	 * 判断功能是否已存在
	 * @param functionvo
	 * @return
	 */
	public boolean checkExist(FunctionVO functionvo);
}
