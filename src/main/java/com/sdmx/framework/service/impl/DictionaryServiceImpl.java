package com.sdmx.framework.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sdmx.framework.dao.IBaseDao;
import com.sdmx.framework.dao.IDictionaryDao;
import com.sdmx.framework.entity.Dictionary;
import com.sdmx.framework.service.IDictionaryService;
import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.vo.DataGrid;
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
	@Autowired
	private IDictionaryDao dictionaryDao;

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
	public DataGrid getDictionary(DictionaryInfo dictionaryInfo) {
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		List<DictionaryInfo> nl = new ArrayList<DictionaryInfo>();

		String hql = "from Dictionary t  where t.state = '1'";
		hql = addWhere(dictionaryInfo, hql, params);
		hql = addOrder(dictionaryInfo, hql);
		String totalHql = "select count(*) " + hql;
		List<Dictionary> l = dictionaryDao.find(hql, params, dictionaryInfo.getPage(),
				dictionaryInfo.getRows());
		dg.setTotal(dictionaryDao.count(totalHql, params));
		changeModel(l, nl);
		dg.setRows(nl);
		return dg;
	}
	private String addWhere(DictionaryInfo dictionaryInfo, String hql,
			Map<String, Object> params) {
		// 名称
		if (dictionaryInfo.getText() != null && !dictionaryInfo.getText().trim().equals("")) {
			hql += " and t.annotation like :textval ";
			params.put("textval", "%%" + dictionaryInfo.getText().trim() + "%%");
		}
		// 课题号
		if (dictionaryInfo.getValue() != null && !dictionaryInfo.getValue().trim().equals("")) {
			hql += " and t.value like :valuestr ";
			params.put("valuestr", "%%" + dictionaryInfo.getValue().trim() + "%%");
		}
		// 电路名称
		if (dictionaryInfo.getExpvalue() != null && !dictionaryInfo.getExpvalue().trim().equals("")) {
			hql += " and t.expvalue like :expvalue ";
			params.put("expvalue", "%%" + dictionaryInfo.getExpvalue().trim() + "%%");
		}
		// 字典类型
		if (dictionaryInfo.getCategoryNO() != null && !dictionaryInfo.getCategoryNO().trim().equals("")) {
			hql += " and t.categoryNO = :categoryNO ";
			params.put("categoryNO", dictionaryInfo.getCategoryNO());
		}
		return hql;
	}
	private String addOrder(DictionaryInfo dictionaryInfo, String hql) {
		hql += " order by t.categoryNO";
		return hql;
	}
	private void changeModel(List<Dictionary> l, List<DictionaryInfo> nl) {
		if (l != null && l.size() > 0) {
			for (Dictionary dictionary : l) {
				nl.add(getDtoByEntity(dictionary));
			}
		}
	}
	
	private DictionaryInfo getDtoByEntity(Dictionary t){
		if(t == null){
			return null;
		}
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
		return r;
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
		return getDictionarylistByHql(hql);
	}
	public List<DictionaryInfo> getOrganizationObjectTextByRoleAndMember(RoleType roleType , String memberId,int seleT){
		String hql = "from Dictionary t where 1=1 and t.state = '1' ";
		if(seleT==0){
			seleT=2;
		}
		if(seleT==2){
			hql += " and (t.categoryNO = '0005' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0005' )) ";
		}else if(seleT==1){
			hql += " and (t.categoryNO = '0005' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0005' )) ";
		}
		return getDictionaryTextlistByHql(hql);
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
	private List<DictionaryInfo> getDictionaryTextlistByHql(String hql) {
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
				m.setId(t.getAnnotation());
				nl.add(m);
			}
		}
		return nl;
	}
	
	private List<DictionaryInfo> getElectricListByHql(String hql) {
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
				m.setText(t.getExpvalue());
				m.setId(String.valueOf(t.getDictionaryId()));
				nl.add(m);
			}
		}
		return nl;
	}
	
	private List<DictionaryInfo> getElectricTextListByHql(String hql) {
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
				m.setText(t.getExpvalue());
				m.setId(t.getExpvalue());
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
	public List<DictionaryInfo> getElectricList() {
		String hql = "from Dictionary t where 1=1 and t.state = '1' and t.expvalue is not null";
		hql += " and (t.categoryNO = '0004' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0004' )) ";
		return getElectricListByHql(hql);
	}
	
	@Override
	public List<DictionaryInfo> getElectricListText() {
		String hql = "from Dictionary t where 1=1 and t.state = '1' and t.expvalue is not null";
		hql += " and (t.categoryNO = '0004' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0004' )) ";
		return getElectricTextListByHql(hql);
	}
	
	

	@Override
	public List<DictionaryInfo> getTopicList() {
		String hql = "from Dictionary t where 1=1 and t.state = '1' ";
		hql += " and (t.categoryNO = '0006' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0006' )) ";
		
		return getDictionarylistByHql(hql);
	}
	
	@Override
	public List<DictionaryInfo> getErsaiTopicList() {
		String hql = "from Dictionary t where 1=1 and t.state = '1' ";
		hql += " and (t.categoryNO = '0016' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0016' )) ";
		
		return getDictionarylistByHql(hql);
	}
	
	@Override
	public List<DictionaryInfo> getYansTopicList() {
		String hql = "from Dictionary t where 1=1 and t.state = '1' ";
		hql += " and (t.categoryNO = '0026' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0026' )) ";
		
		return getDictionarylistByHql(hql);
	}
	
	@Override
	public List<DictionaryInfo> getOrderTypeList() {
		String hql = "from Dictionary t where 1=1 and t.state = '1' ";
		hql += " and (t.categoryNO = '0009' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0009' )) ";
		
		return getDictionarylistByHql(hql);
	}
	@Override
	public List<DictionaryInfo> getStoreList() {
		String hql = "from Dictionary t where 1=1 and t.state = '1' ";
		hql += " and (t.categoryNO = '0012' or t.categoryNO in (select a.codeNO from Dictionary a where a.categoryNO = '0012' )) ";
		
		return getDictionarylistByHql(hql);
	}
}
