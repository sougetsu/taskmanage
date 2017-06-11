package com.sdmx.framework.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.IMenuDao;
import com.sdmx.framework.entity.Menu;

@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements IMenuDao {
	
}
