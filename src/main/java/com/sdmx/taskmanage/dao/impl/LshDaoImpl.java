package com.sdmx.taskmanage.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.taskmanage.dao.ILshDao;
import com.sdmx.taskmanage.entity.OrderLsh;
import com.sdmx.taskmanage.entity.RadiationOrderLsh;
@Repository("lshDao")
public class LshDaoImpl extends BaseDaoImpl<OrderLsh> implements ILshDao{
	
	@Override
	public String getLsh() {
		SessionFactory sessionFactory = getSessionFactory();
		String sql = "select 'DZRW'||to_char(sysdate,'yyyyMMdd')||lpad(t.lsh,4,'0') as xh, t.yearid" +
				" from OrderLsh t" +
				" where t.yearId= to_char(sysdate,'yyyy')";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.addScalar("xh", StandardBasicTypes.STRING)
				.addScalar("yearid", StandardBasicTypes.STRING);
		List list = query.list();
		String lsh = "";
		String yearDate = "";
		for (int i = 0; i < list.size(); i++) {
			Object[] o = (Object[]) list.get(i);
			lsh = (String) o[0];
			yearDate = (String) o[1];
		}
		int year = Integer.valueOf(yearDate);
		OrderLsh orderLsh = get(OrderLsh.class,year);
		orderLsh.setLsh(orderLsh.getLsh()+1);
		update(orderLsh);
		return lsh;
	}
	
}
