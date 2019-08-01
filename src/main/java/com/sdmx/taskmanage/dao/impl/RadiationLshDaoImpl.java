package com.sdmx.taskmanage.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.taskmanage.dao.IRadiationLshDao;
import com.sdmx.taskmanage.entity.RadiationOrderLsh;
@Repository("radiationLshDao")
public class RadiationLshDaoImpl extends BaseDaoImpl<RadiationOrderLsh> implements IRadiationLshDao{
	@Override
	public String getRadiationLsh() {
		SessionFactory sessionFactory = getSessionFactory();
		String sql = "select 'RTTN'||to_char(sysdate,'yyyy')||lpad(t.lsh,3,'0') as xh, t.yearid" +
				" from RadiationOrderLsh t" +
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
		RadiationOrderLsh orderLsh = get(RadiationOrderLsh.class,year);
		orderLsh.setLsh(orderLsh.getLsh()+1);
		update(orderLsh);
		return lsh;
	}
}
