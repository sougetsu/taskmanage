package com.sdmx.taskmanage.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.taskmanage.dao.IAttachmentDao;
import com.sdmx.taskmanage.entity.Attachment;
import com.sdmx.taskmanage.service.IAttachmentService;

@Service("attachmentService")
public class AttachmentServiceImpl implements IAttachmentService {

	private IAttachmentDao attachmentDao;

	public IAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	@Autowired
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	/**
	 * 添加附件
	 */
	@Override
	@Transactional
	public Attachment create(Attachment attach) {
		attachmentDao.save(attach);
		return attach;
	}

	/**
	 * 根据ID删除附件
	 */
	@Override
	@Transactional
	public void remove(int id) {
		Attachment attachment = attachmentDao.get(Attachment.class, id);
		attachmentDao.delete(attachment);
		deleteFile(attachment.getNewName());
	}

	/**
	 * 根据信息Id查询信息的所有附件列表
	 */
	@Override
	public List<Attachment> listByCode(long code) {
		Long[] getcode = { code };
		return attachmentDao
				.find("select a from Attachment a join fetch a.taskOrder m where m.orderId=?",
						getcode);
	}

	/**
	 * 根据信息Id删除该信息的所有附件
	 */
	@Override
	public void removeByCode(long code) {
		List<Attachment> attach = listByCode(code);
		for (Attachment attachment : attach) {
			attachmentDao.delete(attachment);
			deleteFile(attachment.getNewName());
		}
	}

	/**
	 * 删除附件在物理硬盘上的文件
	 */
	private void deleteFile(String filename) {
		String savePath = ResourceUtil.getUploadPath()
				+ ResourceUtil.getUploadDirectory() + "/";
		File f = new File(savePath + filename);
		f.delete();
	}
	/**
	 * 删除未指定所属信息的附件（定时任务，深夜执行）
	 */
	@Override
	public void updateClearAttach() {
		String hql = "from Attachment t where t.TaskOrder is null";
		List<Attachment> attach = attachmentDao.find(hql);
		for (Attachment attachment : attach) {
			remove(attachment.getId());
		}
	}
	
}
