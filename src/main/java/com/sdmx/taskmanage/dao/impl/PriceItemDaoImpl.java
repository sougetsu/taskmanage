package com.sdmx.taskmanage.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.taskmanage.dao.IPriceItemDao;
import com.sdmx.taskmanage.entity.PriceItem;

@Repository("priceItemDao")
public class PriceItemDaoImpl extends BaseDaoImpl<PriceItem> implements IPriceItemDao {

}
