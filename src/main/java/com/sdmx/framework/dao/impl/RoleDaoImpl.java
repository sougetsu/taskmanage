package com.sdmx.framework.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.IRoleDao;
import com.sdmx.framework.entity.Role;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao {

}
