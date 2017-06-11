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
import com.sdmx.framework.dao.IMemberDao;
import com.sdmx.framework.dao.IMenuDao;
import com.sdmx.framework.entity.Function;
import com.sdmx.framework.entity.Member;
import com.sdmx.framework.entity.Menu;
import com.sdmx.framework.entity.Role;
import com.sdmx.framework.service.IMenuService;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.vo.MenuVO;
import com.sdmx.framework.vo.SessionInfo;

@Service("menuService")
public class MenuServiceImpl implements IMenuService {
	private IMenuDao menuDao;
	private IMemberDao memberDao;
	private IFunctionDao functionDao;

	public IMenuDao getMenuDao() {
		return menuDao;
	}

	@Autowired
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}
	
	public IMemberDao getMemberDao() {
		return memberDao;
	}
	@Autowired
	public void setMemberDao(IMemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public IFunctionDao getFunctionDao() {
		return functionDao;
	}
	@Autowired
	public void setFunctionDao(IFunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	/**
	 * 增加菜单
	 */
	@Transactional
	public MenuVO create(MenuVO menu) {
		Menu tmenu = new Menu();
		tmenu.setMenuName(menu.getText());
		if(UtilValidate.isNotEmpty(menu.getPid())){
			tmenu.setPmenu(menuDao.get(Menu.class, Long.parseLong(menu.getPid())));
		}
		tmenu.setImageUri(menu.getIconCls());
		tmenu.setMenuUri(menu.getUrl());
		tmenu.setSeq(menu.getSeq());
		menuDao.save(tmenu);
		menu.setId(String.valueOf(tmenu.getMenuId()));
		return menu;
	}

	/**
	 * 删除菜单
	 */
	@Transactional
	public void remove(String menuId) {
		Menu menu = menuDao.get(Menu.class, Long.valueOf(menuId));
		menuDao.delete(menu);
	}

	/**
	 * 修改菜单
	 */
	@Transactional
	public MenuVO modify(MenuVO menu) {
		Menu tmenu  = menuDao.get(Menu.class, Long.parseLong(menu.getId()));
		tmenu.setMenuName(menu.getText());
		if(UtilValidate.isNotEmpty(menu.getPid())){
			tmenu.setPmenu(menuDao.get(Menu.class, Long.parseLong(menu.getPid())));
		}
		tmenu.setImageUri(menu.getIconCls());
		tmenu.setMenuUri(menu.getUrl());
		tmenu.setSeq(menu.getSeq());
		menuDao.update(tmenu);
		return menu;
	}

	/**
	 * 查询菜单
	 */
	@Override
	public List<MenuVO> list() {
		String hql = "from Menu t order by t.seq";
		List<Menu> l = menuDao.find(hql);
		List<MenuVO> nl = new ArrayList<MenuVO>();

		if (l != null && l.size() > 0) {
			for (Menu t : l) {
				MenuVO r = new MenuVO();
				BeanUtils.copyProperties(t, r);
				if (t.getPmenu() != null) {
					r.setPid(String.valueOf(t.getPmenu().getMenuId()));
				}
				r.setUrl(t.getMenuUri());
				r.setText(t.getMenuName());
				r.setId(String.valueOf(t.getMenuId()));
				r.setIconCls(t.getImageUri());
				nl.add(r);
			}
		}
		return nl;
	}

	/**
	 * 生成菜单
	 */
	public List<MenuVO> allTreeNode() {
		List<MenuVO> nl = new ArrayList<MenuVO>();
		String hql = "from Menu t order by t.seq";
		List<Menu> l = menuDao.find(hql);
		if (l != null && l.size() > 0) {
			for (Menu t : l) {
				MenuVO m = new MenuVO();
				BeanUtils.copyProperties(t, m);
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("url", t.getMenuUri());
				m.setAttributes(attributes);
				m.setText(t.getMenuName());
				m.setId(String.valueOf(t.getMenuId()));
				m.setIconCls(t.getImageUri());
				if (t.getPmenu() != null) {
					m.setPid(String.valueOf(t.getPmenu().getMenuId()));
				}
				nl.add(m);
			}
		}
		return nl;
	}
	
	/**
	 * 根据用户权限生成菜单栏
	 */
	@Override
	public List<MenuVO> treeNode(SessionInfo session) {
		List<MenuVO> mlist = new ArrayList<MenuVO>();
		List<Long> menuIds = new ArrayList<Long>();
		String memberId = session.getUserId();
		Member member = memberDao.get(Member.class, Long.parseLong(memberId));
		List<Long> funcIds = new ArrayList<Long>();
		//取得所有功能权限
		if(UtilValidate.isNotEmpty(member.getRoles())){
			for(Role role: member.getRoles()){
				//角色拥有的功能列表
				List<Function> function = role.getFunctions();
				if(UtilValidate.isNotEmpty(function)){
					for (Function func : function) {
						funcIds.add(func.getFunctionId());
					}
				}
			}
		}
		//根据权限生成菜单
		String hql1 = "select distinct t from Menu t,Function f where t.menuId=f.menu.menuId and f.functionId in(:ids) order by t.seq";
		List<Menu> menus = menuDao.find(hql1, "ids", funcIds);
		for (Menu menu : menus) {
			setMenu(menu,menuIds);
		}
		//根据所有菜单ID重新进行取值，解决菜单排序问题
		String hql2 = "from Menu t where t.menuId in(:menuIds) order by t.seq";
		List<Menu> menusList = menuDao.find(hql2, "menuIds", menuIds);
		if (menusList != null && menusList.size() > 0) {
			for (Menu t : menusList) {
				MenuVO m = new MenuVO();
				BeanUtils.copyProperties(t, m);
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("url", t.getMenuUri());
				m.setAttributes(attributes);
				m.setText(t.getMenuName());
				m.setId(String.valueOf(t.getMenuId()));
				m.setIconCls(t.getImageUri());
				if (t.getPmenu() != null) {
					m.setPid(String.valueOf(t.getPmenu().getMenuId()));
				}
				mlist.add(m);
			}
		}
		return mlist;
	}
	/**
	 * 设置功能所属菜单父节点信息，直到根节点。
	 * 
	 * @param uid
	 *            菜单ID
	 * @param nl
	 *            菜单集合
	 */
	private void setMenu(Menu u,List<Long> menuIds) {
		if(UtilValidate.isNotEmpty(u)){
			if (u.getPmenu() != null) {
				setMenu(u.getPmenu(),menuIds);// 递归查找父节点，知道根节点。
			}
			if (checkSave(u.getMenuId(), menuIds)) {
				menuIds.add(u.getMenuId());
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
	private boolean checkSave(Long id, List<Long> nl) {
		boolean isSave = true;
		if (UtilValidate.isNotEmpty(nl)) {
			for (Long ids : nl) {
				if (id.longValue()== ids.longValue()) {
					isSave = false;
					return isSave;
				}
			}
		}
		return isSave;
	}

	/**
	 * 判断菜单是否已存在
	 */
	public boolean checkExist(MenuVO menuvo) {
		String hql = "from Menu t where t.menuName = ?";
		Object[] params = {menuvo.getText()};
		List<Menu> menus = menuDao.find(hql,params);
		//新增菜单时
		if (UtilValidate.isEmpty(menuvo.getId())) {
			if(menus.size()>0){
				return true;
			}
		}else{
			//修改菜单时
			for (Menu menu : menus) {
				if(!String.valueOf(menu.getMenuId()).equals(menuvo.getId())){
					return true;
				}
			}
		}
		return false;
	}
}
