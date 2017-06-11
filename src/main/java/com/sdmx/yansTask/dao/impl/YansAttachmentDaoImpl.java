package com.sdmx.yansTask.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.yansTask.dao.IYansAttachmentDao;
import com.sdmx.yansTask.entity.YansAttachment;

@Repository("yansAttachmentDao")
public class YansAttachmentDaoImpl extends BaseDaoImpl<YansAttachment> implements IYansAttachmentDao{

	@Override
	public List<YansAttachment> listByTaskOrderId(long orderId) {
		Object[] parm = {orderId};
		return this.find("select a from YansAttachment a left join fetch a.yansTaskOrder m where m.orderId=?",parm);
	}
	
}
