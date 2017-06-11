package com.sdmx.taskmanage.service;

import java.util.List;

import com.sdmx.taskmanage.entity.Attachment;

public interface IAttachmentService {
	/**
	 * 添加附件
	 * 
	 * @param attach
	 * @return
	 */
	public Attachment create(Attachment attach);

	/**
	 * 根据ID删除附件
	 * 
	 * @param id
	 */
	public void remove(int id);

	/**
	 * 根据信息Id查询信息的所有附件列表
	 * 
	 * @param code
	 * @return
	 */
	public List<Attachment> listByCode(long code);

	/**
	 * 根据信息Id删除该信息的所有附件
	 * 
	 * @param code
	 */
	public void removeByCode(long code);
	/**
	 * 删除未指定所属信息的附件（定时任务，深夜执行）
	 * 
	 * @param code
	 */
	public void updateClearAttach();
}
