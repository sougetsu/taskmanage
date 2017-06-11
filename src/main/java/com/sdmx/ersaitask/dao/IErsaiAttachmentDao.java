package com.sdmx.ersaitask.dao;

import java.util.List;

import com.sdmx.ersaitask.entity.ErsaiAttachment;
import com.sdmx.framework.dao.IBaseDao;

public interface IErsaiAttachmentDao  extends IBaseDao<ErsaiAttachment> {
	public List<ErsaiAttachment> listByTaskOrderId(long orderId);
}
