package com.sdmx.taskmanage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.taskmanage.dao.ITaskPriceDao;
import com.sdmx.taskmanage.entity.TaskPrice;
import com.sdmx.taskmanage.vo.TaskPriceVO;

@Repository("taskPriceDao")
public class TaskPriceDaoImpl extends BaseDaoImpl<TaskPrice> implements
		ITaskPriceDao {
	@Autowired
	private SessionFactory sessionFactory;

	public List<TaskPriceVO> getTaskPriceVO(String id) {
		List<TaskPriceVO> tpvoList = new ArrayList<TaskPriceVO>();
		String sql = "select s.taskpriceid,t.itemid,t.itemname,t.price,t.chargeunit,t.baseprice,s.itemnum,s.itemsum,s.remarks from priceitem t left join (select * from taskprice where taskprice.taskorderid='"
				+ id
				+ "') s on s.priceitemid = t.itemid where t.isleaf=1 and t.status=1  order by t.itemId";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.addScalar("taskpriceid", StandardBasicTypes.STRING)
				.addScalar("itemid", StandardBasicTypes.STRING)
				.addScalar("itemName", StandardBasicTypes.STRING)
				.addScalar("price", StandardBasicTypes.DOUBLE)
				.addScalar("chargeUnit", StandardBasicTypes.STRING)
				.addScalar("baseprice", StandardBasicTypes.INTEGER)
				.addScalar("itemnum", StandardBasicTypes.INTEGER)
				.addScalar("itemsum", StandardBasicTypes.DOUBLE)
				.addScalar("remarks", StandardBasicTypes.STRING);
		List list = query.list();
		for (int i = 0; i < list.size(); i++) {
			TaskPriceVO tpvo = new TaskPriceVO();
			Object[] o = (Object[]) list.get(i);
			tpvo.setTaskPriceId((String) o[0]);
			tpvo.setItemId((String) o[1]);
			tpvo.setItemName((String) o[2]);
			tpvo.setPrice((Double) o[3]);
			tpvo.setChargeUnit((String) o[4]);
			tpvo.setBasePrice((Integer) o[5]);
			tpvo.setAmount((Integer) o[6]);
			tpvo.setUnitcost((Double) o[7]);
			tpvo.setRemarks((String) o[8]);
			tpvoList.add(tpvo);
		}
		return tpvoList;
	}
}
