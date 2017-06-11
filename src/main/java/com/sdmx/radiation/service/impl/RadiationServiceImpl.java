package com.sdmx.radiation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sdmx.framework.dao.IMemberDao;
import com.sdmx.framework.entity.Member;
import com.sdmx.framework.util.BeanUtilsEx;
import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.SessionInfo;
import com.sdmx.radiation.dao.IRadiationDao;
import com.sdmx.radiation.entity.RadiationTask;
import com.sdmx.radiation.service.IRadiationService;
import com.sdmx.radiation.vo.RadiationTaskVO;

@Service("radiationService")
public class RadiationServiceImpl implements IRadiationService{
	
	@Autowired
	private IMemberDao memberDao;
	
	@Autowired
	private IRadiationDao radiationDao;
	
	@Transactional
	public RadiationTask create(RadiationTaskVO radiationTaskVO){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        Member currentMember = memberDao.get(Member.class, Long.parseLong(sessionInfo.getUserId()));
        RadiationTask radiationTask = new RadiationTask();
		BeanUtilsEx.copyProperties(radiationTask, radiationTaskVO);
		radiationTask.setMember(currentMember);
		radiationTask.setTaskNo(radiationTaskVO.getDllb()+"0001");
		
		//试验进度
		radiationTask.setSingleParticleSchedule(null);
		radiationTask.setIntegralDoseSchedule(null);
		
		//试验文档
		//试验日志
		radiationTask.setSingleParticleLog(null);
		radiationTask.setIntegralDoseLog(null);
		radiationTask.setCreatetime(new Date());
		radiationDao.save(radiationTask);
		
		return radiationTask;
	}

	@Override
	public DataGrid listRadiation(RadiationTaskVO radiationTaskVO) {
		DataGrid dg = new DataGrid();
		String hql = "from RadiationTask t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(radiationTaskVO, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(radiationTaskVO, hql);
		List<RadiationTask> l = radiationDao.find(hql, params, radiationTaskVO.getPage(), radiationTaskVO.getRows());
		List<RadiationTaskVO> nl = new ArrayList<RadiationTaskVO>();
		changeModelList(l, nl);
		dg.setTotal(radiationDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	private String addWhere(RadiationTaskVO radiationTaskVO, String hql, Map<String, Object> params) {
		hql += " where 1=1 ";
		//课题类别
		if (UtilValidate.isNotEmpty(radiationTaskVO.getKtlb())) {
			hql += " and t.ktlb = :ktlb";
			params.put("ktlb", radiationTaskVO.getKtlb());
		}
		//电路类别
		if (UtilValidate.isNotEmpty(radiationTaskVO.getDllb())) {
			hql += " and t.dllb = :dllb";
			params.put("dllb", radiationTaskVO.getDllb());
		}
		//研发部门
		if (UtilValidate.isNotEmpty(radiationTaskVO.getYfbm())) {
			hql += " and t.yfbm = :yfbm";
			params.put("yfbm", radiationTaskVO.getYfbm());
		}
		//卫星型号
		if (UtilValidate.isNotEmpty(radiationTaskVO.getWxxh())){
			hql += " and t.wxxh = :wxxh";
			params.put("wxxh", radiationTaskVO.getWxxh());
		}
		//工艺尺寸
		if (UtilValidate.isNotEmpty(radiationTaskVO.getGycc())) {
			hql += " and t.gycc = :gycc";
			params.put("gycc", radiationTaskVO.getGycc());
		}
		//生产厂家
		if (UtilValidate.isNotEmpty(radiationTaskVO.getSccj())) {
			hql += " and t.sccj = :sccj";
			params.put("sccj", radiationTaskVO.getSccj());
		}
		//制造工艺
		if (UtilValidate.isNotEmpty(radiationTaskVO.getZcgy())) {
			hql += " and t.zcgy = :zcgy";
			params.put("zcgy", radiationTaskVO.getZcgy());
		}		
		//双极工艺
		if (UtilValidate.isNotEmpty(radiationTaskVO.getSjgy())) {
			hql += " and t.sjgy = :sjgy";
			params.put("sjgy", radiationTaskVO.getSjgy());
		}
		
		return hql;
	}
	private String addOrder(RadiationTaskVO radiationTaskVO, String hql){
		if (radiationTaskVO.getSort() != null) {
			if("statusName".equals(radiationTaskVO.getSort())){
				hql += " order by t.status "  + radiationTaskVO.getOrder();
			}else if("projectName".equals(radiationTaskVO.getSort())){
				hql += " order by t.project.value "  + radiationTaskVO.getOrder();
			}else if("topicNo".equals(radiationTaskVO.getSort())){
				hql += " order by t.topic.value "  + radiationTaskVO.getOrder();
			}else{
				hql += " order by " + radiationTaskVO.getSort() + " " + radiationTaskVO.getOrder();
			}
		}else{
			hql += " order by t.createtime desc";
		}
		return hql;
	}
	private void changeModelList(List<RadiationTask> l, List<RadiationTaskVO> nl) {
		if (l != null && l.size() > 0) {
			for (Iterator<RadiationTask> iterator = l.iterator(); iterator.hasNext();) {
				RadiationTask radiationTask = (RadiationTask) iterator.next();
				RadiationTaskVO radiationTaskVO = getDtoData(radiationTask);
				nl.add(radiationTaskVO);
			}
		}
	}
	private RadiationTaskVO getDtoData(RadiationTask radiationTask){
		RadiationTaskVO radiationTaskVO = new RadiationTaskVO();
		BeanUtilsEx.copyProperties(radiationTaskVO,radiationTask);
		return radiationTaskVO;
	}

}
