package com.sdmx.yansTask.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.yansTask.dao.IYansLshDao;
import com.sdmx.yansTask.entity.YansOrderLsh;
@Repository("yansLshDao")
public class YansLshDaoImpl extends BaseDaoImpl<YansOrderLsh> implements IYansLshDao{

	@Override
	public String getLsh() {
		SessionFactory sessionFactory = getSessionFactory();
		String sql = "select 'DZRWY'||to_char(sysdate,'yyyyMMdd')||lpad(t.lsh,4,'0') as xh, t.yearid" +
				" from YansOrderLsh t" +
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
		YansOrderLsh orderLsh = get(YansOrderLsh.class,year);
		orderLsh.setLsh(orderLsh.getLsh()+1);
		update(orderLsh);
		return lsh;
	}
}
