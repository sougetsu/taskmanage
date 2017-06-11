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
import com.sdmx.framework.dao.IRoleDao;
import com.sdmx.framework.entity.Function;
import com.sdmx.framework.entity.Role;
import com.sdmx.framework.service.IRoleService;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.util.annotation.BussAnnotation;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.RoleVO;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {
	private IRoleDao roleDao;
	private IFunctionDao functionDao;

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public IFunctionDao getFunctionDao() {
		return functionDao;
	}

	@Autowired
	public void setFunctionDao(IFunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	/**
	 * 添加角色
	 */
	@Override
	@BussAnnotation(moduleName = "角色管理", option = "添加角色")
	@Transactional
	public RoleVO create(RoleVO role) {
		Role trole = new Role();
		BeanUtils.copyProperties(role, trole);
		// 设置功能权限
		if (role.getFunctionIds() != null) {
			String functionNames = "";
			String functionIds = "";
			List<Function> rl = new ArrayList<Function>();
			for (String id : role.getFunctionIds().split(",")) {
				if (id.indexOf("f_") != -1) {
					id = id.substring(2);
					Function func = functionDao.get(Function.class,
							Long.valueOf(id));
					if (func != null) {
						rl.add(func);
					}
					if (functionNames.length() > 0) {
						functionNames += ",";
						functionIds += ",";
					}
					functionIds += func.getFunctionId();
					functionNames += func.getFunctionName();
				}
			}
			trole.setFunctions(rl);
			role.setFunctionIds(functionIds);
			role.setFunctionNames(functionNames);
		}
		roleDao.save(trole);
		role.setRoleId(trole.getRoleId());
		return role;
	}

	/**
	 * 删除角色
	 */
	@BussAnnotation(moduleName = "角色管理", option = "删除角色")
	@Override
	@Transactional
	public void remove(String ids) {
		for (String id : ids.split(",")) {
			Role trole = roleDao.get(Role.class, Long.valueOf(id));
			if (trole != null) {
				roleDao.delete(trole);
			}
		}
	}

	/**
	 * 修改角色
	 */
	@Override
	@Transactional
	public RoleVO modify(RoleVO role) {
		Role t = roleDao.get(Role.class, role.getRoleId());
		t.setRoleName(role.getRoleName());// 角色姓名
		if (role.getFunctionIds() != null) {
			String functionNames = "";
			String functionIds = "";
			List<Function> rl = new ArrayList<Function>();
			for (String id : role.getFunctionIds().split(",")) {
				if (id.indexOf("f_") != -1) {
					id = id.substring(2);
					Function func = functionDao.get(Function.class,
							Long.valueOf(id));
					if (func != null) {
						rl.add(func);
					}
					if (functionNames.length() > 0) {
						functionNames += ",";
						functionIds += ",";
					}
					functionIds += func.getFunctionId();
					functionNames += func.getFunctionName();
				}
			}
			t.setFunctions(rl);// 角色对应的功能权限
			role.setFunctionIds(functionIds);
			role.setFunctionNames(functionNames);
		}
		roleDao.update(t);
		return role;

	}

	/**
	 * 查询角色列表
	 */
	@Override
	public DataGrid list(RoleVO role) {
		DataGrid dg = new DataGrid();
		String hql = "from Role t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String totalHql = "select count(*) " + hql;
		List<Role> l = roleDao
				.find(hql, params, role.getPage(), role.getRows());
		List<RoleVO> nl = new ArrayList<RoleVO>();
		changeModel(l, nl);
		dg.setTotal(roleDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}

	/**
	 * 实体对象转化为数据传输对象， 用于页面展示
	 * 
	 * @param l
	 * @param nl
	 */
	private void changeModel(List<Role> l, List<RoleVO> nl) {
		if (l != null && l.size() > 0) {
			for (Role t : l) {
				RoleVO u = new RoleVO();
				BeanUtils.copyProperties(t, u);
				List<Function> r = t.getFunctions();
				String functionIds = "";
				String functionNames = "";
				for (Function function : r) {
					if (functionIds.length() > 0) {
						functionIds += ",";
						functionNames += ",";
					}
					functionIds += String.valueOf(function.getFunctionId());
					functionNames += function.getFunctionName();
				}
				u.setFunctionIds(functionIds);
				u.setFunctionNames(functionNames);
				nl.add(u);
			}
		}
	}

	/**
	 * 判断角色是否已存在
	 */
	public boolean checkExist(RoleVO rolevo) {
		String hql = "from Role t where t.roleName = ?";
		Object[] params = {rolevo.getRoleName()};
		List<Role> roles = roleDao.find(hql,params);
		//新增角色时
		if (UtilValidate.isEmpty(rolevo.getRoleId())) {
			if(roles.size()>0){
				return true;
			}
		}else{
			//修改角色时
			for (Role role : roles) {
				if(role.getRoleId().longValue() != rolevo.getRoleId()){
					return true;
				}
			}
		}
		return false;
	}

}
