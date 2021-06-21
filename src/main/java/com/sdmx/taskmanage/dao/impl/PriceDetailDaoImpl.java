package com.sdmx.taskmanage.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.taskmanage.dao.IPriceDetailDao;
import com.sdmx.taskmanage.entity.PriceDetail;

@Repository("priceDetailDao")
public class PriceDetailDaoImpl extends BaseDaoImpl<PriceDetail> implements IPriceDetailDao {

}
