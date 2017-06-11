package com.sdmx.framework.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdmx.framework.dao.IFunctionDao;
import com.sdmx.framework.dao.IMenuDao;
import com.sdmx.framework.entity.Function;
import com.sdmx.framework.entity.Menu;
import com.sdmx.framework.service.IFunctionService;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.FunctionVO;

@Service("functionService")
public class FunctionServiceImpl implements IFunctionService {

	private IFunctionDao functionDao;
	private IMenuDao menuDao;

	public IFunctionDao getFunctionDao() {
		return functionDao;
	}

	@Autowired
	public void setFunctionDao(IFunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	public IMenuDao getMenuDao() {
		return menuDao;
	}

	@Autowired
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	/**
	 * 新增功能
	 */
	@Override
	@Transactional
	public FunctionVO addNewFunction(FunctionVO functionVO) {
		Function function = new Function();
		BeanUtils.copyProperties(functionVO, function);
		// 设置功能所属菜单
		if (UtilValidate.isNotEmpty(functionVO.getMenuIds())) {
			Menu m = menuDao.get(Menu.class,
					Long.parseLong(functionVO.getMenuIds()));
			function.setMenu(m);
		}
		functionDao.save(function);
		return functionVO;
	}

	/**
	 * 根据功能Id删除功能
	 */
	@Override
	@Transactional
	public void deleteFunction(String functionIds) {
		functionIds = getFnctionId(functionIds);
		if (UtilValidate.isNotEmpty(functionIds)) {
			Function function = functionDao.get(Function.class,
					Long.parseLong(functionIds));
			if (function != null) {
				//先删除角色功能关系表中的数据
				functionDao.delete(function);
			}
		}
	}

	/**
	 * 修改功能 修改项：功能名称、功能地址、功能描述、功能状态、所属菜单
	 */
	@Override
	@Transactional
	public FunctionVO updateFunction(FunctionVO functionvo) {
		String functionIds = getFnctionId(functionvo.getFunctionId());
		Function function = functionDao.get(Function.class,
				Long.parseLong(functionIds));
		function.setFunctionName(functionvo.getFunctionName());// 功能名称
		function.setUrl(functionvo.getUrl());// 功能地址
		function.setDescription(functionvo.getDescription());// 功能描述
		function.setStatus(functionvo.getStatus());// 功能状态
		// 功能所属菜单
		if (UtilValidate.isNotEmpty(functionvo.getMenuIds())) {
			Menu m = menuDao.get(Menu.class,
					Long.parseLong(functionvo.getMenuIds()));
			function.setMenu(m);
		}
		functionDao.update(function);
		return functionvo;
	}

	/**
	 * 查询功能列表
	 */
	@Override
	public DataGrid getFunctions(FunctionVO functionVO) {

		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		List<FunctionVO> functionVos = new ArrayList<FunctionVO>();

		String hql = "from Function t ";
		String totalHql = "select count(*) " + hql;
		hql = addOrder(functionVO, hql);

		List<Function> functions = functionDao.find(hql, params,
				functionVO.getPage(), functionVO.getRows());
		changeModel(functions, functionVos);

		dg.setTotal(functionDao.count(totalHql, params));
		dg.setRows(functionVos);
		return dg;
	}

	/**
	 * 根据前台选择排序条件对查询语句增加排序
	 * 
	 * @param functionVO
	 * @param hql
	 * @return
	 */
	private String addOrder(FunctionVO functionVO, String hql) {
		if (functionVO.getSort() != null) {
			hql += " order by " + functionVO.getSort() + " "
					+ functionVO.getOrder();
		}
		return hql;
	}

	/**
	 * 将实体对象转化为数据传输对象，用于前台展示
	 * 
	 * @param functions
	 *            实体对象
	 * @param functionvos
	 *            数据传输对象
	 */
	private void changeModel(List<Function> functions,
			List<FunctionVO> functionvos) {
		if (functions != null && functions.size() > 0) {
			FunctionVO functionVO = null;
			for (Function function : functions) {
				functionVO = new FunctionVO();
				BeanUtils.copyProperties(function, functionVO);
				// 功能所属菜单ID
				String menuIds = String.valueOf(function.getMenu().getMenuId());
				// 功能所属菜单名称
				String menuNames = function.getMenu().getMenuName();
				functionVO.setMenuIds(menuIds);
				functionVO.setMenuNames(menuNames);
				functionvos.add(functionVO);
			}
		}
	}

	/**
	 * 查询所有功能(树形)根据功能-->菜单 包括功能所属菜单数据，以树形形式展现。
	 */
	public List<FunctionVO> list() {
		String hql = "from Function t";
		List<Function> l = functionDao.find(hql);
		List<FunctionVO> nl = new ArrayList<FunctionVO>();
		if (l != null && l.size() > 0) {
			for (Function t : l) {
				FunctionVO r = new FunctionVO();
				r.setId("f_" + t.getFunctionId());// 功能ID特殊标识，用于区别菜单ID
				r.setUrl(t.getUrl());// 功能路径
				r.setText(t.getFunctionName());// 功能名称
				// 如果有所属菜单，找出所有父节点。
				if (t.getMenu() != null) {
					Menu u = t.getMenu();
					r.setPid(String.valueOf(u.getMenuId()));// 功能父节点ID
					r.setMenuIds(String.valueOf(u.getMenuId()));// 功能所属菜单ID
					setFunc(String.valueOf(u.getMenuId()),nl);
				}
				r.setItemStatus(1);// 节点类型：功能
				r.setDescription(t.getDescription());// 功能描述
				r.setStatus(t.getStatus());// 功能状态
				nl.add(r);// 保存功能节点
			}
		}
		return nl;
	}

	/**
	 * 设置功能所属菜单父节点信息，直到根节点。
	 * 
	 * @param uid
	 *            菜单ID
	 * @param nl
	 *            菜单集合
	 */
	private void setFunc(String uid, List<FunctionVO> nl) {
		if (uid != null) {
			Menu u = menuDao.get(Menu.class,
					Long.parseLong(uid));
			FunctionVO fvo = new FunctionVO();// 菜单
			fvo.setId(String.valueOf(u.getMenuId()));// 菜单ID
			fvo.setText(u.getMenuName());// 菜单名称
			if (u.getPmenu() != null) {
				fvo.setPid(String.valueOf(u.getPmenu().getMenuId()));// 设置父菜单ID
				setFunc(String.valueOf(u.getPmenu().getMenuId()), nl);// 递归查找父节点，直到根节点。
			}
			// 判断是否需要保存菜单父节点。
			if (checkSave(String.valueOf(u.getMenuId()), nl)) {
				nl.add(fvo);
			}
		}
	}

	/**
	 * 根据ID去已有的列表中验证是否需要保存。避免重复。
	 * 
	 * @param id
	 *            菜单ID
	 * @param nl
	 *            已有的列表
	 * @return true：保存，false：不保存
	 */
	private boolean checkSave(String id, List<FunctionVO> nl) {
		boolean isSave = true;
		if (UtilValidate.isNotEmpty(nl)) {
			for (FunctionVO functionVO : nl) {
				if (functionVO.getId().equals(id)) {
					isSave = false;
					return isSave;
				}
			}
		}
		return isSave;
	}

	/**
	 * 由于功能ID和菜单ID可能重复，功能ID进行了特殊处理。 该方法用于取得真实的功能ID
	 * 
	 * @param id
	 * @return
	 */
	private String getFnctionId(String id) {
		if (id.indexOf("f_") != -1) {
			if (UtilValidate.isNotEmpty(id)) {
				return id.substring(2);
			} else {
				return id;
			}
		} else {
			return null;
		}
	}

	/**
	 * 判断功能是否已存在
	 * @param functionVO
	 * @return
	 */
	public boolean checkExist(FunctionVO functionvo) {
		String hql = "from Function t where t.functionName = ?";
		Object[] params = {functionvo.getFunctionName()};
		List<Function> functions = functionDao.find(hql,params);
		//新增用户时
		if (UtilValidate.isEmpty(functionvo.getFunctionId())) {
			if(functions.size()>0){
				return true;
			}
		}else{
			//修改用户时
			for (Function function : functions) {
				String functionId = getFnctionId(functionvo.getFunctionId());
				if(!String.valueOf(function.getFunctionId()).equals(functionId)){
					return true;
				}
			}
		}
		return false;
	}
}
