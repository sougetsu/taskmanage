package com.sdmx.taskmanage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdmx.framework.util.BeanUtilsEx;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.taskmanage.dao.IPriceDetailDao;
import com.sdmx.taskmanage.entity.PriceDetail;
import com.sdmx.taskmanage.entity.TaskOrder;
import com.sdmx.taskmanage.service.IPriceDetailService;
import com.sdmx.taskmanage.vo.PriceDetailVO;

@Service("priceDetailService")
public class PriceDetailServiceImpl  implements IPriceDetailService{
	@Autowired
	private IPriceDetailDao priceDetailDao;
	
	@Override
	public DataGrid listPriceDetail(PriceDetailVO priceDetailVO) {
		DataGrid dg = new DataGrid();
		String hql = "from PriceDetail t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(priceDetailVO, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(priceDetailVO, hql);
		List<PriceDetail> l = priceDetailDao.find(hql, params, priceDetailVO.getPage(), priceDetailVO.getRows());
		List<PriceDetailVO> nl = new ArrayList<PriceDetailVO>();
		changeModelList(l, nl);
		dg.setTotal(priceDetailDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	
	private String addWhere(PriceDetailVO priceDetailVO, String hql, Map<String, Object> params) {
		
		hql += " where 1=1  ";
		//产品型号
		if (UtilValidate.isNotEmpty(priceDetailVO.getCpxh())) {
			hql += " and t.cpxh like :cpxh";
			params.put("cpxh", "%%" +priceDetailVO.getCpxh().trim() + "%%");
		}
		//电路名称
		if (UtilValidate.isNotEmpty(priceDetailVO.getDlmc())) {
			hql += " and t.dlmc like :dlmc";
			params.put("dlmc", "%%" +priceDetailVO.getDlmc().trim() + "%%");
		}
		//封装类型
		if (UtilValidate.isNotEmpty(priceDetailVO.getFzlx())) {
			hql += " and t.fzlx like :fzlx";
			params.put("fzlx", "%%" +priceDetailVO.getFzlx().trim() + "%%");
		}
		//测试机台
		if (UtilValidate.isNotEmpty(priceDetailVO.getCsjt())) {
			hql += " and t.csjt like :csjt";
			params.put("csjt", "%%" +priceDetailVO.getCsjt().trim() + "%%");
		}
		return hql;
	}
	private String addOrder(PriceDetailVO priceDetailVO, String hql){
		if (priceDetailVO.getSort() != null) {
			hql += " order by " + priceDetailVO.getSort() + " " + priceDetailVO.getOrder();
		}else{
			hql += " order by t.priceDetailId desc";
		}
		return hql;
	}
	
	private void changeModelList(List<PriceDetail> l, List<PriceDetailVO> nl) {
		if (l != null && l.size() > 0) {
			for (Iterator<PriceDetail> iterator = l.iterator(); iterator.hasNext();) {
				PriceDetail priceDetail = (PriceDetail) iterator.next();
				PriceDetailVO priceDetailVO = getDtoData(priceDetail);
				nl.add(priceDetailVO);
			}
		}
	}
	
	private PriceDetailVO getDtoData(PriceDetail priceDetail){
		PriceDetailVO priceDetailVO = new PriceDetailVO();
		BeanUtilsEx.copyProperties(priceDetailVO,priceDetail);
		return priceDetailVO;
	}

	@Override
	@Transactional
	public PriceDetail create(PriceDetailVO priceDetailVO) {
		PriceDetail priceDetail = new PriceDetail();
		BeanUtilsEx.copyProperties(priceDetail,priceDetailVO);
		priceDetailDao.save(priceDetail);
		return priceDetail;
	}

	@Override
	public PriceDetailVO getPriceDetailById(String priceDetailId) {
		PriceDetail priceDetail = priceDetailDao.get(PriceDetail.class, Long.parseLong(priceDetailId));
		PriceDetailVO priceDetailVO = getDtoData(priceDetail);
		return priceDetailVO;
	}

	@Override
	@Transactional
	public void remove(String priceDetailId) {
		PriceDetail priceDetail = priceDetailDao.get(PriceDetail.class, Long.parseLong(priceDetailId));
		priceDetailDao.delete(priceDetail);
	}

	@Override
	@Transactional
	public PriceDetail edit(PriceDetailVO priceDetailVO) {
		PriceDetail priceDetail = priceDetailDao.get(PriceDetail.class, Long.parseLong(priceDetailVO.getPriceDetailId()));
		BeanUtilsEx.copyProperties(priceDetail,priceDetailVO);
		priceDetailDao.saveOrUpdate(priceDetail);
		return priceDetail;
	}
	
	
	
}
