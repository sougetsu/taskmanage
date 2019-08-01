package com.sdmx.certification.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.sdmx.certification.dao.ICertificationLshDao;
import com.sdmx.certification.entity.CertificationLsh;
import com.sdmx.framework.dao.impl.BaseDaoImpl;
@Repository("certificationLshDao")
public class CertificationLshDaoImpl extends BaseDaoImpl<CertificationLsh>
		implements ICertificationLshDao {
	public String getCertificationId() {
		SessionFactory sessionFactory = getSessionFactory();
		String sql = "select 'SDMX'||to_char(sysdate,'yyyyMMdd')||lpad(t.lsh,4,'0') as xh, t.yearid"
				+ " from CertificationLsh t"
				+ " where t.yearId= to_char(sysdate,'yyyy')";
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
		CertificationLsh certificationLsh = get(CertificationLsh.class, year);
		certificationLsh.setLsh(certificationLsh.getLsh() + 1);
		update(certificationLsh);
		return lsh;

	}
}
