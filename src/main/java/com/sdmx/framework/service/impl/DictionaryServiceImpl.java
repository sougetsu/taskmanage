package com.sdmx.framework.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sdmx.framework.dao.IBaseDao;
import com.sdmx.framework.entity.Dictionary;
import com.sdmx.framework.service.IDictionaryService;
import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.vo.DictionaryInfo;
import com.sdmx.framework.vo.RoleType;
import com.sdmx.framework.vo.SessionInfo;

@Service("dictionaryService")
public class DictionaryServiceImpl implements IDictionaryService{
	
	
	private IBaseDao<Dictionary> baseDao;

	public IBaseDao<Dictionary> getBaseDao() {
		return baseDao;
	}
	
	@Autowired
	public void setBaseDao(IBaseDao<Dictionary> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	@Transactional
	public DictionaryInfo create(DictionaryInfo dicInfo) {
		Dictionary dic = new Dictionary();
		BeanUtils.copyProperties(dicInfo, dic);
		dic.setDictionary(find(Dictionary.class,Long.valueOf(dicInfo.getPid())));
		dic.setAnnotation(dicInfo.getText());
		dic.setDisplay("1");
		dic.setState("1");
		if(UtilValidate.isNotEmpty(dicInfo.getDepartmentId())){
			dic.setDepartDicId(find(Dictionary.class,Long.valueOf(dicInfo.getDepartmentId())));
		}
		baseDao.save(dic);
		dicInfo.setId(String.valueOf(dic.getDictionaryId()));
		return dicInfo;
	}
	
	@Override
	public Dictionary find(Class<Dictionary> c, Serializable id) {
		return baseDao.get(c, id);
	}

	@Override
	@Transactional
	public void remove(String dicId) {
		Dictionary dic = baseDao.get(Dictionary.class, Long.valueOf(dicId));
		dic.setState("0");
		baseDao.update(dic);	
	}

	@Override
	@Transactional
	public DictionaryInfo modify(DictionaryInfo dicInfo) {
		Dictionary dic = new Dictionary();
		BeanUtils.copyProperties(dicInfo, dic);
		dic.setDictionaryId(Long.valueOf(dicInfo.getId()));
		dic.setDictionary(find(Dictionary.class,Long.valueOf(dicInfo.getPid())));
		dic.setAnnotation(dicInfo.getText());
		dic.setDisplay("1");
		dic.setState("1");
		if(UtilValidate.isNotEmpty(dicInfo.getDepartmentId())){
			dic.setDepartDicId(find(Dictionary.class,Long.valueOf(dicInfo.getDepartmentId())));
		}
		baseDao.update(dic);
		return dicInfo;
	}

	@Override
	public List<DictionaryInfo> list() {
		String hql = "from Dictionary t where t.state = '1' order by t.seq";
		List<Dictionary> l = baseDao.find(hql);
		List<DictionaryInfo> nl = new ArrayList<DictionaryInfo>();
		
		if (l != null && l.size() > 0) {
			for (Dictionary t : l) {
				DictionaryInfo r = new DictionaryInfo();
				BeanUtils.copyProperties(t, r);
				if (t.getDictionary() != null) {
					r.setPid(String.valueOf(t.getDictionary().getDictionaryId()));
				}
				if (t.getDepartDicId() != null) {
					r.setDepartmentId(String.valueOf(t.getDepartDicId().getDictionaryId()));
				}
				r.setText(t.getAnnotation());
				r.setId(String.valueOf(t.getDictionaryId()));
				nl.add(r);
			}
		}
		return nl;
	}

	@Override
	public List<DictionaryInfo> allTreeNode() {
		List<DictionaryInfo> nl = new ArrayList<DictionaryInfo>();
		String hql = "from Dictionary t where t.state = '1' order by t.seq";
		List<Dictionary> l = baseDao.find(hql);
		if (l != null && l.size() > 0) {
			for (Dictionary t : l) {
				DictionaryInfo m = new DictionaryInfo();
				BeanUtils.copyProperties(t, m);
				if (t.getDictionary() != null) {
					m.setPid(String.valueOf(t.getDictionary().getDictionaryId()));
				}
				if (t.getDepartDicId() != null) {
					m.setDepartmentId(String.valueOf(t.getDepartDicId().getDictionaryId()));
				}
				m.setText(t.getAnnotation());
				m.setId(String.valueOf(t.getDictionaryId()));
				nl.add(m);
			}
		}
		return nl;
	}

	@Override
	public List<DictionaryInfo> queslist() {
		List<DictionaryInfo> nl = new ArrayList<DictionaryInfo>();
		String hql = "from Dictionary t where  t.state = '1' and t.categoryNO = '0004'order by t.seq";
		List<Dictionary> l = baseDao.find(hql);
		if (l != null && l.size() > 0) {
			for (Dictionary t : l) {
				DictionaryInfo m = new DictionaryInfo();
				BeanUtils.copyProperties(t, m);
				if (t.getDictionary() != null) {
					m.setPid(String.valueOf(t.getDictionary().getDictionaryId()));
				}
				if (t.getDepartDicId() != null) {
					m.setDepartmentId(String.valueOf(t.getDepartDicId().getDictionaryId()));
				}
				m.setText(t.getAnnotation());
				m.setId(String.valueOf(t.getDictionaryId()));
				nl.add(m);
			}
		}
		return nl;
	}
	
	public Dictionary getStatus(String status){
		List<Dictionary> Dictionarys = new ArrayList<Dictionary>();
		String hql = "from Dictionary t where t.categoryNO = '0003' and t.codeNO ='"+status+"'";
		Dictionarys =baseDao.find(hql);
		return Dictionarys.get(0);
	}
	
	public List<DictionaryInfo> getOrganizationObjectByRoleAndMember(RoleType roleType , String memberId,int seleT){
		String hql = "from Dictionary t where 1=1 and t.state = '1' ";
		if(seleT==0){
			seleT=2;
		}
		if(seleT==2){
			hql += " and (t.categoryNO = '0005' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0005' )) ";
		}else if(seleT==1){
			hql += " and (t.categoryNO = '0005' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0005' )) ";
		}
//		if("超管角色".equals(memberType)){
//			hql += " and t.categoryNO = '0005' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0005' ";
//		}else if("系统管理员".equals(memberType)){
//			hql += " and t.categoryNO = '0005' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0005' )";
//		}else if("班长席".equals(memberType)){
//			hql += " and t.categoryNO = '0005' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0005' )";
//		}else if("坐席".equals(memberType)){
//			hql += " and (t.categoryNO = '0005' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0005' ))";
//		}else if("直属单位".equals(memberType)){
//			hql += " and t.categoryNO = (select m.organization.codeNO from Member m where m.memberId = "+memberId+") ";
//		}else if("直属单位下属部门".equals(memberType)){
//			
//		}
		return getDictionarylistByHql(hql);
	}
	

	private List<DictionaryInfo> getDictionarylistByHql(String hql) {
		List<DictionaryInfo> nl = new ArrayList<DictionaryInfo>();
		List<Dictionary> l = baseDao.find(hql);
		if (l != null && l.size() > 0) {
			for (Dictionary t : l) {
				DictionaryInfo m = new DictionaryInfo();
				BeanUtils.copyProperties(t, m);
				if (t.getDictionary() != null) {
					m.setPid(String.valueOf(t.getDictionary().getDictionaryId()));
				}
				if (t.getDepartDicId() != null) {
					m.setDepartmentId(String.valueOf(t.getDepartDicId().getDictionaryId()));
				}
				m.setText(t.getAnnotation());
				m.setId(String.valueOf(t.getDictionaryId()));
				nl.add(m);
			}
		}
		return nl;
	}

	@Override
	public List<DictionaryInfo> typeList() {
		List<DictionaryInfo> nl = new ArrayList<DictionaryInfo>();
		String hql = "from Dictionary t where t.state = '1' and t.categoryNO = '0009'order by t.seq";
		List<Dictionary> l = baseDao.find(hql);
		if (l != null && l.size() > 0) {
			for (Dictionary t : l) {
				DictionaryInfo m = new DictionaryInfo();
				BeanUtils.copyProperties(t, m);
				if (t.getDictionary() != null) {
					m.setPid(String.valueOf(t.getDictionary().getDictionaryId()));
				}
				if (t.getDepartDicId() != null) {
					m.setDepartmentId(String.valueOf(t.getDepartDicId().getDictionaryId()));
				}
				m.setText(t.getAnnotation());
				m.setId(String.valueOf(t.getDictionaryId()));
				nl.add(m);
			}
		}
		return nl;
	}
	
//	public List<DictionaryInfo> getOrganizationObjectByRoleAndMember(String memberType , String memberId){
//		List<Object> params = null;
//		String hql = "from Dictionary t "; //+
////				"left join fetch t.dictionarys a " +
////				"left outer join Dictionary.dictionarys.dictionarys b on a.codeNO = b.categoryNO " +
////				"where t.codeNO = '0005' ";
//		hql += addWhere(memberType,memberId);
//		hql += " order by t.seq";
//		return getDictionarylistByHql(hql,params);
//	}
//	
//	private String addWhere(String memberType , String memberId){
//		String hql = "where 1=1 ";
//		List<Object> params = new ArrayList<Object>();
//		
//		if("超管角色".equals(memberType)){
//		}else if("系统管理员".equals(memberType)){
//		}else if("班长席".equals(memberType)){
//		}else if("坐席".equals(memberType)){
//			hql += " and (t.codeNO = '0005' or (t.dictionarys.categoryNO = t.codeNO ) or (t.dictionarys.dictionarys.categoryNO = t.dictionarys.codeNO )) ";
//			params.add(Long.parseLong(memberId));
//		}else if("直属单位".equals(memberType)){
//			hql += " and( t.complaintObject.categoryNO in " +
//					"(select y.organization.codeNO from Member y  " +
//					"where y.memberId = :getMemberId ) " +
//					" or t.complaintObject.codeNO = (select z.organization.codeNO from Member z " +
//					"where z.memberId = :getMemberId ))";
//			params.add(Long.parseLong(memberId));
//		}else if("直属单位下属部门".equals(memberType)){
//			hql += " and t.complaintObject.codeNO in (select y.organization.codeNO from Member y " +
//					"where y.memberId = :getMemberId )";
//			params.add(Long.parseLong(memberId));
//		}
//		return hql;
//	}
//	private List<DictionaryInfo> getDictionarylistByHql(String hql,List<Object> params) {
//		List<DictionaryInfo> nl = new ArrayList<DictionaryInfo>();
//		List<Dictionary> l = baseDao.find(hql,params);
//		if (l != null && l.size() > 0) {
//			for (Dictionary t : l) {
//				DictionaryInfo m = new DictionaryInfo();
//				BeanUtils.copyProperties(t, m);
//				if (t.getpId() != null) {
//					m.setPid(String.valueOf(t.getpId()));
//				}
//				m.setText(t.getValue());
//				m.setId(String.valueOf(t.getDictionaryId()));
//				nl.add(m);
//			}
//		}
//		return nl;
//	}
	public Dictionary getLimitedDays(int limitedType){
		Dictionary limitedDay = null;
		String hql = "from Dictionary t where t.state = '1' and t.categoryNO = '0010' and t.codeNO="+limitedType;
		Object[] obj = null;
		limitedDay = baseDao.get(hql,obj);
		return limitedDay;
	}

	@Override
	public List<DictionaryInfo> getProjectList() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        Long departId = Long.valueOf(sessionInfo.getOrgnizationId());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
		String hql = "from Dictionary t where 1=1 and t.state = '1' ";
		hql += " and (t.categoryNO = '0004' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0004' )) ";
		switch(memType){
			case MarketManger:
			case DepartMember :	
			case DepartManage :{
				hql += " and (t.departDicId.dictionaryId ="+departId+ ") ";
				break;
			}
			default:{
				break;
			}
		}
		
		return getDictionarylistByHql(hql);
	}

	@Override
	public List<DictionaryInfo> getTopicList() {
		String hql = "from Dictionary t where 1=1 and t.state = '1' ";
		hql += " and (t.categoryNO = '0006' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0006' )) ";
		
		return getDictionarylistByHql(hql);
	}
}
