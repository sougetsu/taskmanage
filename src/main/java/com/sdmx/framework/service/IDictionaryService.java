package com.sdmx.framework.service;

import java.io.Serializable;
import java.util.List;

import com.sdmx.framework.entity.Dictionary;
import com.sdmx.framework.vo.DictionaryInfo;
import com.sdmx.framework.vo.RoleType;

public interface IDictionaryService extends IService {
	
	public DictionaryInfo create(DictionaryInfo dic);

	public void remove(String dicId);
	
	public Dictionary find(Class<Dictionary> c, Serializable id);

	public DictionaryInfo modify(DictionaryInfo dic);
	
	public List<DictionaryInfo> list();

	public List<DictionaryInfo> allTreeNode();
	
	public List<DictionaryInfo> queslist();
	
	public List<DictionaryInfo> typeList();
	
	/**
	 * 根据 seleT 返回组织机构对象
	 * @param roleType
	 * @param memberId
	 * @param seleT 控制参数
	 * @return
	 */
	public List<DictionaryInfo> getOrganizationObjectByRoleAndMember(RoleType roleType , String memberId,int seleT);
	public List<DictionaryInfo> getOrganizationObjectTextByRoleAndMember(RoleType roleType , String memberId,int seleT);
	public Dictionary getStatus(String status);
	
	public Dictionary getLimitedDays(int limitedType);
	public List<DictionaryInfo> getProjectList();
	public List<DictionaryInfo> getElectricList();
	public List<DictionaryInfo> getElectricListText();
	public List<DictionaryInfo> getTopicList();
	public List<DictionaryInfo> getErsaiTopicList();
	public List<DictionaryInfo> getYansTopicList();
	public List<DictionaryInfo> getOrderTypeList();
	public List<DictionaryInfo> getStoreList();
}
