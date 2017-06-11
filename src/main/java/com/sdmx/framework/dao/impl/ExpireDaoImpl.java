package com.sdmx.framework.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.IExpireDao;
import com.sdmx.framework.entity.Expire;

@Repository("expireDao")
public class ExpireDaoImpl extends BaseDaoImpl<Expire> implements IExpireDao {

}
