package com.sdmx.radiation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdmx.framework.vo.DictionaryInfo;
import com.sdmx.radiation.service.IRadiationService;

@Controller
@RequestMapping(value = "/raSerach")
public class RadiationSearchController {
	
	@Autowired
	private IRadiationService radiationService;
	
	/**
	 * 获得课题类别
	 * @param request
	 * @return List<DictionaryInfo> 对象列表
	 */
	@RequestMapping("/ktlbList")
	@ResponseBody
	public List<String> getktlbList(HttpServletRequest request) {
//		return radiationService.getktlbList();
		return null;
	}
}
