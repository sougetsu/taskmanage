package com.sdmx.taskmanage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdmx.framework.util.BeanUtilsEx;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.taskmanage.dao.IPriceItemDao;
import com.sdmx.taskmanage.entity.PriceItem;
import com.sdmx.taskmanage.service.IPriceItemService;
import com.sdmx.taskmanage.vo.PriceItemVO;

@Service("priceItemService")
public class PriceItemServiceImpl implements IPriceItemService{
	@Autowired
	private IPriceItemDao priceItemDao;
	@Override
	public DataGrid listPriceItem(PriceItemVO priceItemvo) {
		DataGrid dg = new DataGrid();
		String hql = "from PriceItem t where t.isleaf=1 and t.status=1 order by t.itemId";
		Map<String, Object> params = new HashMap<String, Object>();
		List<PriceItem> kl = priceItemDao.find(hql, params);
		List<PriceItemVO> nl = new ArrayList<PriceItemVO>();
		changeModel(kl, nl);
		dg.setRows(nl);
		return dg;
	}
	private void changeModel(List<PriceItem> l,List<PriceItemVO> lv){

		if (l != null && l.size() > 0) {
			for (PriceItem k : l) {
				PriceItemVO kv = new PriceItemVO();
				BeanUtilsEx.copyProperties(kv, k);
				kv.setItemId(String.valueOf(k.getItemId()));
				// 父级内容
				if (k.getPriceItem() != null) {
					kv.setPid(String.valueOf(k.getPriceItem().getItemId()));
					kv.setpItemName(k.getPriceItem().getItemName());
				}
				lv.add(kv);
			}
		}
	}
	public DataGrid listErsaiPriceItem(PriceItemVO priceItemvo) {
		DataGrid dg = new DataGrid();
		String hql = "from PriceItem t where t.isleaf=1 and t.status=1 and t.typeclass=2 order by t.itemId";
		Map<String, Object> params = new HashMap<String, Object>();
		List<PriceItem> kl = priceItemDao.find(hql, params);
		List<PriceItemVO> nl = new ArrayList<PriceItemVO>();
		changeModel(kl, nl);
		dg.setRows(nl);
		return dg;
	}
}
