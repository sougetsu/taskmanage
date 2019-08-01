package com.sdmx.certification.service.impl;

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

import com.sdmx.certification.dao.ICertificationDao;
import com.sdmx.certification.dao.ICertificationLshDao;
import com.sdmx.certification.entity.Certification;
import com.sdmx.certification.service.ICertificationService;
import com.sdmx.certification.vo.CertificationVO;
import com.sdmx.framework.dao.IMemberDao;
import com.sdmx.framework.entity.Member;
import com.sdmx.framework.util.BeanUtilsEx;
import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.SessionInfo;
import com.sdmx.taskmanage.dao.IOperateLogDao;
import com.sdmx.taskmanage.entity.OperateLog;

@Service("certificationService")
public class CertificationServiceImpl implements ICertificationService {

	@Autowired
	private IMemberDao memberDao;
	@Autowired
	private ICertificationDao certificationDao;
	@Autowired
	private ICertificationLshDao certificationLshDao;
	@Autowired
	private IOperateLogDao operatelogDao;
	
	@Override
	@Transactional
	public Certification create(CertificationVO certificationvo) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ResourceUtil.getSessionInfoName());
		Member currentMember = memberDao.get(Member.class,
				Long.parseLong(sessionInfo.getUserId()));
		Certification certification = new Certification();
		BeanUtilsEx.copyProperties(certification, certificationvo);
		certification.setCreateMember(currentMember);
		certification.setCreateTime(new Date());
		certification.setCertificationDate(certificationvo.getCertificationDate());
		certification.setStatus(0);
		// 生成流水号
		certification.setCertificationId(certificationLshDao
				.getCertificationId());
		certificationDao.save(certification);
		return certification;
	}
	
	@Override
	@Transactional
	public void editSubmit(CertificationVO certificationvo)
	{
		Certification certification = certificationDao.get(Certification.class, certificationvo.getId());
		certification.setProductName(certificationvo.getProductName());
		certification.setProductType(certificationvo.getProductType());
		certification.setProductBatch(certificationvo.getProductBatch());
		certification.setProductNum(certificationvo.getProductNum());
		certification.setTestStandard(certificationvo.getTestStandard());
		certification.setTestReportId(certificationvo.getTestReportId());
		certification.setQualityStatus(certificationvo.getQualityStatus());
		certification.setUserUnits(certificationvo.getUserUnits());
		certification.setInspector(certificationvo.getInspector());
		certification.setCertificationDate(certificationvo.getCertificationDate());
		certification.setRemark(certificationvo.getRemark());
		certificationDao.update(certification);
	}
	public DataGrid listCertification(CertificationVO certificationvo)
	{
		
		DataGrid dg = new DataGrid();
		String hql = "from Certification t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(certificationvo, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(certificationvo, hql);
		List<Certification> l = certificationDao.find(hql, params, certificationvo.getPage(), certificationvo.getRows());
		List<CertificationVO> nl = new ArrayList<CertificationVO>();
		changeModelList(l, nl);
		dg.setTotal(certificationDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	private String addWhere(CertificationVO certificationvo, String hql, Map<String, Object> params) {
		hql += " where 1=1 and t.status=0 ";
		//合格证编号
		if (UtilValidate.isNotEmpty(certificationvo.getCertificationId())) {
			hql += " and t.certificationId like :certificationId";
			params.put("certificationId", "%%" +certificationvo.getCertificationId().trim() + "%%");
		}
		//产 品 名 称
		if (UtilValidate.isNotEmpty(certificationvo.getProductName())) {
			hql += " and t.productName like :productName";
			params.put("productName", "%%" +certificationvo.getProductName().trim() + "%%");
		}
		//产 品 型 号
		if (UtilValidate.isNotEmpty(certificationvo.getProductType())) {
			hql += " and t.productType like :productType";
			params.put("productType", "%%" +certificationvo.getProductType().trim() + "%%");
		}
		//检  验  员
		if (UtilValidate.isNotEmpty(certificationvo.getInspector())) {
			hql += " and t.inspector like :inspector";
			params.put("inspector", "%%" +certificationvo.getInspector().trim() + "%%");
		}
		//开始登记时间
		if (certificationvo.getCertificationStart() != null) {
			hql += " and t.certificationDate >= :startDate";
			params.put("startDate", certificationvo.getCertificationStart());
		}
		//结束登记时间
		if (certificationvo.getCertificationEnd() != null) {
			hql += " and t.certificationDate <= :endDate";
			params.put("endDate", certificationvo.getCertificationEnd());
		}
		return hql;
	}
	private String addOrder(CertificationVO certificationvo, String hql){
		if (certificationvo.getSort() != null) {
			if("productName".equals(certificationvo.getSort())){
				hql += " order by t.productName "  + certificationvo.getOrder();
			}else{
				hql += " order by " + certificationvo.getSort() + " " + certificationvo.getOrder();
			}
		}else{
			hql += " order by t.createTime desc";
		}
		return hql;
	}
	/**
	 * 根据实体转化页面展示项(集合)
	 * @param l
	 * @param nl
	 */
	private void changeModelList(List<Certification> l, List<CertificationVO> nl) {
		if (l != null && l.size() > 0) {
			for (Iterator<Certification> iterator = l.iterator(); iterator.hasNext();) {
				Certification certification = (Certification) iterator.next();
				CertificationVO certificationVO = getDtoData(certification);
				nl.add(certificationVO);
			}
		}
	}
	private CertificationVO getDtoData(Certification certification){
		CertificationVO certificationVO = new CertificationVO();
		BeanUtilsEx.copyProperties(certificationVO,certification);
		return certificationVO;
	}
	
	@Override
	public CertificationVO getCertificationById(String id){
		Certification certification = certificationDao.get(Certification.class, Long.parseLong(id));
		CertificationVO certificationVO = getDtoData(certification);
		return certificationVO;
	}
	
	@Override
	@Transactional
	public void remove(String id)
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        Certification certification = certificationDao.get(Certification.class, Long.valueOf(id));
        certification.setStatus(1);
        certificationDao.update(certification);
		//操作日志
  		OperateLog opLog = new OperateLog();
  		opLog.setLshId(certification.getCertificationId());
  		opLog.setContent(sessionInfo.getLoginName()+"删除了合格证"+id);
  		opLog.setMemberId(Long.valueOf(sessionInfo.getUserId()));
  		opLog.setCreatetime(new Date());
  		operatelogDao.save(opLog);
	}
}
