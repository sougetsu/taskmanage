package com.sdmx.framework.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdmx.framework.service.IDictionaryService;
import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.framework.vo.DictionaryInfo;
import com.sdmx.framework.vo.JsonResult;
import com.sdmx.framework.vo.RoleType;
import com.sdmx.framework.vo.SessionInfo;

@Controller
@RequestMapping(value = "/dictionary")
public class DictionaryController {
	
	@Autowired
	private IDictionaryService dictionaryService;
	
	@RequestMapping(value = "/list")
    @ResponseBody
    public List<DictionaryInfo> list() {
    	return dictionaryService.list();
    }
	
	@RequestMapping(value = "/create")
    @ResponseBody
    public JsonResult create(DictionaryInfo dic) {
		DictionaryInfo dicInfo = dictionaryService.create(dic);
    	return JsonResult.success("新增字典成功！", dicInfo);
    }
	
	@RequestMapping("/allTreeNode")
	@ResponseBody
	public List<DictionaryInfo> allTreeNode() {
		return dictionaryService.allTreeNode();
	}
	
	@RequestMapping(value = "/modify")
    @ResponseBody
    public JsonResult modify(DictionaryInfo dicInfo) {
		DictionaryInfo dic = dictionaryService.modify(dicInfo);
        return JsonResult.success("修改成功！", dic);
    }
	
	@RequestMapping(value = "/remove")
    @ResponseBody
    public JsonResult remove(String dicId) {
		dictionaryService.remove(dicId);
        return JsonResult.success("删除成功！", dicId);
    }
	
	/**
	 * 获得分转单位对象
	 * @param request
	 * @return List<DictionaryInfo> 对象列表
	 */
	@RequestMapping("/organizationList")
	@ResponseBody
	public List<DictionaryInfo> getOrganizationObjectList(HttpServletRequest request) {
    	SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
    	RoleType roleType = RoleType.getType(sessionInfo.getRoleNames());
    	String memberId = sessionInfo.getUserId();
		return dictionaryService.getOrganizationObjectByRoleAndMember(roleType, memberId,2);
	}
	/**
	 * 获得项目名称
	 * @param request
	 * @return List<DictionaryInfo> 对象列表
	 */
	@RequestMapping("/projectList")
	@ResponseBody
	public List<DictionaryInfo> getProjectList(HttpServletRequest request) {
		return dictionaryService.getProjectList();
	}
	
	/**
	 * 获得库存名称
	 * @param request
	 * @return List<DictionaryInfo> 对象列表
	 */
	@RequestMapping("/storeList")
	@ResponseBody
	public List<DictionaryInfo> getStoreList(HttpServletRequest request) {
		return dictionaryService.getStoreList();
	}
	
	/**
	 * 获得成本归集课题号
	 * @param request
	 * @return List<DictionaryInfo> 对象列表
	 */
	@RequestMapping("/topicList")
	@ResponseBody
	public List<DictionaryInfo> getTopicList(HttpServletRequest request) {
		return dictionaryService.getTopicList();
	}
	
	/**
	 * 获得二筛课题号
	 * @param request
	 * @return List<DictionaryInfo> 对象列表
	 */
	@RequestMapping("/ersaitopicList")
	@ResponseBody
	public List<DictionaryInfo> getErsaiTopicList(HttpServletRequest request) {
		return dictionaryService.getErsaiTopicList();
	}
	/**
	 * 获得验收课题号
	 * @param request
	 * @return List<DictionaryInfo> 对象列表
	 */
	@RequestMapping("/yanstopicList")
	@ResponseBody
	public List<DictionaryInfo> getYansTopicList(HttpServletRequest request) {
		return dictionaryService.getYansTopicList();
	}
	
	/**
	 * 获得任务单类型列表
	 * @param request
	 * @return List<DictionaryInfo> 对象列表
	 */
	@RequestMapping("/orderTypeList")
	@ResponseBody
	public List<DictionaryInfo> getOrderTypeList(HttpServletRequest request) {
		return dictionaryService.getOrderTypeList();
	}
}
