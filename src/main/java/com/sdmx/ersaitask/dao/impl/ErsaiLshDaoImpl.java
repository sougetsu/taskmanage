package com.sdmx.ersaitask.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.sdmx.ersaitask.dao.IErsaiLshDao;
import com.sdmx.ersaitask.entity.ErsaiOrderLsh;
import com.sdmx.framework.dao.impl.BaseDaoImpl;
@Repository("ersaiLshDao")
public class ErsaiLshDaoImpl extends BaseDaoImpl<ErsaiOrderLsh> implements IErsaiLshDao{

	@Override
	public String getLsh() {
		SessionFactory sessionFactory = getSessionFactory();
		String sql = "select 'DZRWS'||to_char(sysdate,'yyyyMMdd')||lpad(t.lsh,4,'0') as xh, t.yearid" +
				" from ErsaiOrderLsh t" +
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
		ErsaiOrderLsh orderLsh = get(ErsaiOrderLsh.class,year);
		orderLsh.setLsh(orderLsh.getLsh()+1);
		update(orderLsh);
		return lsh;
	}
}
