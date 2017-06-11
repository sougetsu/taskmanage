package com.sdmx.framework.util;

import java.util.ResourceBundle;

public class ResourceUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	private ResourceUtil() {
	}

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
	}

	/**
	 * 获得上传表单域的名称
	 * 
	 * @return
	 */
	public static final String getUploadFieldName() {
		return bundle.getString("uploadFieldName");
	}

	/**
	 * 获得上传文件的最大大小限制
	 * 
	 * @return
	 */
	public static final long getUploadFileMaxSize() {
		return Long.valueOf(bundle.getString("uploadFileMaxSize"));
	}

	/**
	 * 获得允许上传文件的扩展名
	 * 
	 * @return
	 */
	public static final String getUploadFileExts() {
		return bundle.getString("uploadFileExts");
	}

	/**
	 * 获得上传文件要放到那个盘符路径
	 * 
	 * @return
	 */
	public static final String getUploadPath() {
		return bundle.getString("uploadPath");
	}
	
	/**
	 * 获得普通任务上传文件要放到那个目录
	 * 
	 * @return
	 */
	public static final String getUploadDirectory() {
		return bundle.getString("uploadDirectory");
	}
	
	/**
	 * 获得二筛任务上传文件要放到那个目录
	 * 
	 * @return
	 */
	public static final String getErsaiUploadDirectory() {
		return bundle.getString("ersaiuploadDirectory");
	}
	/**
	 * 获得验收任务上传文件要放到那个目录
	 * 
	 * @return
	 */
	public static final String getYansUploadDirectory() {
		return bundle.getString("yansuploadDirectory");
	}
	/**
	 * 获得索引要放到那个路径
	 * 
	 * @return
	 */
	public static final String getIndexPath() {
		return bundle.getString("indexPath");
	}
}
