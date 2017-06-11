package com.sdmx.taskmanage.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.impl.BaseDaoImpl;
import com.sdmx.taskmanage.dao.IAttachmentDao;
import com.sdmx.taskmanage.entity.Attachment;

@Repository("attachmentDao")
public class AttachmentDaoImpl extends BaseDaoImpl<Attachment> implements IAttachmentDao{

	@Override
	public List<Attachment> listByTaskOrderId(long orderId) {
		Object[] parm = {orderId};
		return this.find("select a from Attachment a left join fetch a.taskOrder m where m.orderId=?",parm);
	}
	
}
