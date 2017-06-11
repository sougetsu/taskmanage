package com.sdmx.ersaitask.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdmx.ersaitask.dao.IErsaiAttachmentDao;
import com.sdmx.ersaitask.entity.ErsaiAttachment;
import com.sdmx.ersaitask.service.IErsaiAttachmentService;
import com.sdmx.framework.util.ResourceUtil;

@Service("ersaiAttachmentService")
public class ErsaiAttachmentServiceImpl implements IErsaiAttachmentService {
	@Autowired
	private IErsaiAttachmentDao ersaiAttachmentDao;

	/**
	 * 添加附件
	 */
	@Override
	@Transactional
	public ErsaiAttachment create(ErsaiAttachment attach) {
		ersaiAttachmentDao.save(attach);
		return attach;
	}

	/**
	 * 根据ID删除附件
	 */
	@Override
	@Transactional
	public void remove(int id) {
		ErsaiAttachment attachment = ersaiAttachmentDao.get(ErsaiAttachment.class, id);
		ersaiAttachmentDao.delete(attachment);
		deleteFile(attachment.getNewName());
	}

	/**
	 * 根据信息Id查询信息的所有附件列表
	 */
	@Override
	public List<ErsaiAttachment> listByCode(long code) {
		Long[] getcode = { code };
		return ersaiAttachmentDao
				.find("select a from ErsaiAttachment a join fetch a.ersaiTaskOrder m where m.orderId=?",
						getcode);
	}

	/**
	 * 根据信息Id删除该信息的所有附件
	 */
	@Override
	public void removeByCode(long code) {
		List<ErsaiAttachment> attach = listByCode(code);
		for (ErsaiAttachment attachment : attach) {
			ersaiAttachmentDao.delete(attachment);
			deleteFile(attachment.getNewName());
		}
	}

	/**
	 * 删除附件在物理硬盘上的文件
	 */
	private void deleteFile(String filename) {
		String savePath = ResourceUtil.getUploadPath()
				+ ResourceUtil.getErsaiUploadDirectory() + "/";
		File f = new File(savePath + filename);
		f.delete();
	}
	/**
	 * 删除未指定所属信息的附件（定时任务，深夜执行）
	 */
	@Override
	public void updateClearAttach() {
		String hql = "from ErsaiAttachment t where t.ersaiTaskOrder is null";
		List<ErsaiAttachment> attach = ersaiAttachmentDao.find(hql);
		for (ErsaiAttachment attachment : attach) {
			remove(attachment.getId());
		}
	}
	
}
