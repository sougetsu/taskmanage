package com.sdmx.yansTask.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.yansTask.dao.IYansTaskOrderDao;
import com.sdmx.yansTask.entity.YansTaskOrder;
@Repository("yansTaskOrderDao")
public class YansTaskOrderDaoImpl extends BaseDaoImpl<YansTaskOrder> implements IYansTaskOrderDao{

}
