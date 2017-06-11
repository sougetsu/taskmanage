package com.sdmx.yansTask.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.yansTask.dao.IYansAttachmentDao;
import com.sdmx.yansTask.entity.YansAttachment;
import com.sdmx.yansTask.service.IYansAttachmentService;

@Service("yansAttachmentService")
public class YansAttachmentServiceImpl implements IYansAttachmentService {
	@Autowired
	private IYansAttachmentDao yansAttachmentDao;

	/**
	 * 添加附件
	 */
	@Override
	@Transactional
	public YansAttachment create(YansAttachment attach) {
		yansAttachmentDao.save(attach);
		return attach;
	}

	/**
	 * 根据ID删除附件
	 */
	@Override
	@Transactional
	public void remove(int id) {
		YansAttachment attachment = yansAttachmentDao.get(YansAttachment.class, id);
		yansAttachmentDao.delete(attachment);
		deleteFile(attachment.getNewName());
	}

	/**
	 * 根据信息Id查询信息的所有附件列表
	 */
	@Override
	public List<YansAttachment> listByCode(long code) {
		Long[] getcode = { code };
		return yansAttachmentDao
				.find("select a from YansAttachment a join fetch a.yansTaskOrder m where m.orderId=?",
						getcode);
	}

	/**
	 * 根据信息Id删除该信息的所有附件
	 */
	@Override
	public void removeByCode(long code) {
		List<YansAttachment> attach = listByCode(code);
		for (YansAttachment attachment : attach) {
			yansAttachmentDao.delete(attachment);
			deleteFile(attachment.getNewName());
		}
	}

	/**
	 * 删除附件在物理硬盘上的文件
	 */
	private void deleteFile(String filename) {
		String savePath = ResourceUtil.getUploadPath()
				+ ResourceUtil.getYansUploadDirectory() + "/";
		File f = new File(savePath + filename);
		f.delete();
	}
	/**
	 * 删除未指定所属信息的附件（定时任务，深夜执行）
	 */
	@Override
	public void updateClearAttach() {
		String hql = "from YansAttachment t where t.yansTaskOrder is null";
		List<YansAttachment> attach = yansAttachmentDao.find(hql);
		for (YansAttachment attachment : attach) {
			remove(attachment.getId());
		}
	}
	
}
