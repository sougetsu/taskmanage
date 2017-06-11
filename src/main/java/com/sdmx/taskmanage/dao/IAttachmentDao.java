package com.sdmx.taskmanage.dao;

import java.util.List;

import com.sdmx.framework.dao.IBaseDao;
import com.sdmx.taskmanage.entity.Attachment;

public interface IAttachmentDao  extends IBaseDao<Attachment> {
	public List<Attachment> listByTaskOrderId(long orderId);
}
