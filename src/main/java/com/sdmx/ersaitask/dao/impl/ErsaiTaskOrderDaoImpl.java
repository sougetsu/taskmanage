package com.sdmx.ersaitask.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.ersaitask.dao.IErsaiTaskOrderDao;
import com.sdmx.ersaitask.entity.ErsaiTaskOrder;
import com.sdmx.framework.dao.impl.BaseDaoImpl;
@Repository("ersaiTaskOrderDao")
public class ErsaiTaskOrderDaoImpl extends BaseDaoImpl<ErsaiTaskOrder> implements IErsaiTaskOrderDao{

}
