package com.sdmx.taskmanage.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.taskmanage.dao.IRadiationTaskOrderDao;
import com.sdmx.taskmanage.entity.RadiationTaskOrder;

@Repository("radiationTaskOrderDao")
public class RadiationTaskOrderDaoImpl extends BaseDaoImpl<RadiationTaskOrder> implements IRadiationTaskOrderDao {
	public int[] getSumByMonth(){
		SessionFactory sessionFactory = getSessionFactory();
		String sql = "select  to_char(createtime,'mm') as mm,count(*) as sum" +
				" from RadiationTaskOrder t" +
				" where 1=1 and t.status !=0  and t.type=0 and t.createtime> trunc(sysdate,'yyyy') " +
				" group by  to_char(createtime,'mm')";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.addScalar("mm", StandardBasicTypes.STRING)
				.addScalar("sum", StandardBasicTypes.INTEGER);
		List list =  query.list();
		int[] yearmm = new int[12]; 
		for (int i = 0; i < 12; i++) {
			int num=0;
			String mm = "";
			for (int j = 0; j < list.size(); j++) {
				Object[] o = (Object[]) list.get(j);
				mm = (String) o[0];
				int mmsum = (Integer) o[1];
				if(Integer.valueOf(mm) == i+1)
				{
					num = mmsum;
				}
			}
			yearmm[i] = num;
		}
		return  yearmm;
	}
	
	public int[] getSumByMonthMD(){
		SessionFactory sessionFactory = getSessionFactory();
		String sql = "select  to_char(createtime,'mm') as mm,count(*) as sum" +
				" from RadiationTaskOrder t" +
				" where 1=1 and t.status !=0  and t.type=1 and t.createtime> trunc(sysdate,'yyyy') " +
				" group by  to_char(createtime,'mm')";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.addScalar("mm", StandardBasicTypes.STRING)
				.addScalar("sum", StandardBasicTypes.INTEGER);
		List list =  query.list();
		int[] yearmm = new int[12]; 
		for (int i = 0; i < 12; i++) {
			int num=0;
			String mm = "";
			for (int j = 0; j < list.size(); j++) {
				Object[] o = (Object[]) list.get(j);
				mm = (String) o[0];
				int mmsum = (Integer) o[1];
				if(Integer.valueOf(mm) == i+1)
				{
					num = mmsum;
				}
			}
			yearmm[i] = num;
		}
		return  yearmm;
	}
}
