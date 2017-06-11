package com.sdmx.yansTask.dao;

import java.util.List;

import com.sdmx.framework.dao.IBaseDao;
import com.sdmx.yansTask.entity.YansAttachment;

public interface IYansAttachmentDao  extends IBaseDao<YansAttachment> {
	public List<YansAttachment> listByTaskOrderId(long orderId);
}
