package com.sdmx.taskmanage.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "attachment", schema="SDMX")
public class Attachment {
	private int id;
	private long attachSize;
	private String newName;
	private String oldName;
	private String contentType;
	private Date createDate;
	private TaskOrder taskOrder;

	public Attachment(String newName, String oldName, String contentType,
			Date createDate) {
		super();
		this.newName = newName;
		this.oldName = oldName;
		this.contentType = contentType;
		this.createDate = createDate;
	}

	public Attachment() {
	}

	/**
	 * 附件标识
	 * 
	 * @return
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_attachs_ID")
	@SequenceGenerator(name = "S_attachs_ID", sequenceName = "S_attachs_ID",allocationSize=1)
	public int getId() {
		return id;
	}

	/**
	 * 设置附件标识
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 获取附件的原始大小
	 * 
	 * @return
	 */
	public long getAttachSize() {
		return attachSize;
	}

	/**
	 * 设置附件的大小
	 * 
	 * @param size
	 */
	public void setAttachSize(long attachSize) {
		this.attachSize = attachSize;
	}

	/**
	 * 获取附件的新名称，当附件上传之后，会根据当前的时间来获取一个名称（名称是唯一的）
	 * 
	 * @return
	 */
	public String getNewName() {
		return newName;
	}

	/**
	 * 设置附件的新名称
	 * 
	 * @param newName
	 */
	public void setNewName(String newName) {
		this.newName = newName;
	}

	/**
	 * 获取附件的原始名称
	 * 
	 * @return
	 */
	public String getOldName() {
		return oldName;
	}

	/**
	 * 设置附件的原始名称
	 * 
	 * @param oldName
	 */
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	/**
	 * 获取附件的文件类型
	 * 
	 * @return
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * 设置附件的文件类型
	 * 
	 * @param contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * 获取附件的创建时间
	 * 
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 设置附件 的创建 时间
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@ManyToOne
	@JoinColumn(name = "taskorderId")
	public TaskOrder getTaskOrder() {
		return taskOrder;
	}

	public void setTaskOrder(TaskOrder taskOrder) {
		this.taskOrder = taskOrder;
	}
	
	
}
