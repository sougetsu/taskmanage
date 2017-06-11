package com.sdmx.stockmanage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdmx.framework.vo.DataGrid;
import com.sdmx.stockmanage.vo.LCCStockVO;
import com.sdmx.taskmanage.entity.PriceItem;
import com.sdmx.taskmanage.vo.PriceItemVO;

@Controller
@RequestMapping(value = "/stockManage")
public class StockOrderController {
	
	/**
	 * 获取LCC库存详细信息
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/listLccDetail")
	@ResponseBody
	public DataGrid listLccDetail() {
		DataGrid dg = new DataGrid();
		List<LCCStockVO> nl = new ArrayList<LCCStockVO>();
		for (int i = 0; i < 8; i++) {
			LCCStockVO lcc = new LCCStockVO();
			lcc.setBatchNo("160104-"+i);
			lcc.setMaterielName("DIP24"+i+"管壳");
			lcc.setMaterielNo("0101000"+i);
			lcc.setMeasureUnit("只");
			lcc.setSpecification("KD-82831-D-0"+i);
			lcc.setNum(1264/(i+1));
			nl.add(lcc);
		}
		dg.setRows(nl);
		return dg;
	}
}
