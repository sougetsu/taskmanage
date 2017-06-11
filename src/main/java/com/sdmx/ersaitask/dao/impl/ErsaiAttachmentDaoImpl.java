package com.sdmx.ersaitask.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sdmx.ersaitask.dao.IErsaiAttachmentDao;
import com.sdmx.ersaitask.entity.ErsaiAttachment;
import com.sdmx.framework.dao.impl.BaseDaoImpl;

@Repository("ersaiAttachmentDao")
public class ErsaiAttachmentDaoImpl extends BaseDaoImpl<ErsaiAttachment> implements IErsaiAttachmentDao{

	@Override
	public List<ErsaiAttachment> listByTaskOrderId(long orderId) {
		Object[] parm = {orderId};
		return this.find("select a from ErsaiAttachment a left join fetch a.ersaiTaskOrder m where m.orderId=?",parm);
	}
	
}
