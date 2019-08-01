package com.sdmx.framework.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sdmx.ersaitask.service.IErsaiTaskOrderService;
import com.sdmx.framework.entity.Function;
import com.sdmx.framework.entity.Member;
import com.sdmx.framework.entity.Role;
import com.sdmx.framework.service.IMemberService;
import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.JsonResult;
import com.sdmx.framework.vo.MemberVO;
import com.sdmx.framework.vo.SessionInfo;
import com.sdmx.taskmanage.service.IRadiationTaskOrderService;
import com.sdmx.taskmanage.service.ITaskOrderService;
import com.sdmx.taskmanage.service.ITaskOrderTestService;
import com.sdmx.yansTask.service.IYansTaskOrderService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	private static final Logger logger = Logger
			.getLogger(MemberController.class);
	@Autowired
	private ITaskOrderService taskOrderService;
	@Autowired
	private ITaskOrderTestService taskOrderTestService;
	@Autowired
	private IErsaiTaskOrderService ersaiTaskOrderService;
	@Autowired
	private IYansTaskOrderService yansTaskOrderService;
	@Autowired
	private IRadiationTaskOrderService radiationTaskOrderService;
	
	private IMemberService memberService;
	public IMemberService getMemberService() {
		return memberService;
	}

	@Autowired
	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * 新增用户
	 * 
	 * @param membervo
	 * @return
	 */
	@RequestMapping(value = "/create")
	@ResponseBody
	public JsonResult create(@Valid MemberVO membervo, BindingResult errors) {
		// 验证用户信息输入
		if (errors.hasErrors()) {
			String code = errors.getFieldError().getCode();
			String msg = errors.getFieldError().getDefaultMessage();// 出错的信息
			return JsonResult.failed(msg, code);
		}
		// 验证用户是否存在
		if (memberService.checkExist(membervo)) {
			return JsonResult.failed("用户名已存在", null);
		}
		membervo = memberService.create(membervo);
		return JsonResult.success("新增用户成功！", membervo);
	}

	/**
	 * 删除用户
	 * 
	 * @param membervo
	 * @return
	 */
	@RequestMapping(value = "/remove")
	@ResponseBody
	public JsonResult remove(MemberVO membervo) {
		memberService.remove(membervo.getMemberIds());
		return JsonResult.success("删除成功！", membervo);
	}

	/**
	 * 恢复用户
	 * 
	 * @param membervo
	 * @return
	 */
	@RequestMapping(value = "/resume")
	@ResponseBody
	public JsonResult resume(MemberVO membervo) {
		memberService.resume(membervo.getMemberIds());
		return JsonResult.success("恢复成功！", membervo);
	}

	/**
	 * 修改用户
	 * 
	 * @param membervo
	 * @return
	 */
	@RequestMapping(value = "/modify")
	@ResponseBody
	public JsonResult modify(@Valid MemberVO membervo, BindingResult errors) {
		// 验证用户信息输入
		if (errors.hasErrors()) {
			String code = errors.getFieldError().getCode();
			String msg = errors.getFieldError().getDefaultMessage();// 出错的信息
			return JsonResult.failed(msg, code);
		}
		// 验证用户是否存在
		if (memberService.checkExist(membervo)) {
			return JsonResult.failed("用户名已存在", null);
		}
		membervo = memberService.modify(membervo);
		return JsonResult.success("修改用户成功！", membervo);
	}

	/**
	 * 修改用户密码
	 * 
	 * @param membervo
	 * @return
	 */
	@RequestMapping(value = "/modifyPwd")
	@ResponseBody
	public JsonResult modifyPwd(MemberVO membervo) {
		memberService.modifyPwd(membervo);
		return JsonResult.success("密码修改成功！", membervo);
	}

	/**
	 * 查询用户列表
	 * 
	 * @param membervo
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public DataGrid memberList(MemberVO membervo) {
		return memberService.getMembers(membervo);
	}

	/**
	 * 用户登录
	 * 
	 * @param member
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public JsonResult login(Member member, HttpServletRequest request,
			HttpSession session) {
		if(!memberService.checkLicense(member)){
			return JsonResult.failed("授权已过期！", null);
		}
		Member u = memberService.login(member);
		if (u != null) {
			// 保存用户信息到session中
			SessionInfo sessionInfo = new SessionInfo();
			sessionInfo.setUserId(String.valueOf(u.getMemberId()));// 用户ID
			sessionInfo.setLoginName(u.getLoginName());// 登录名
//			sessionInfo.setIp(IpUtil.getIpAddr(request));// IP地址
			// 用户角色设置
			if (UtilValidate.isNotEmpty(u.getRoles())) {
				String roleIds = "";
				String roleNames = "";
				List<String> functionList = new ArrayList<String>();
				for (Role role : u.getRoles()) {
					if (UtilValidate.isNotEmpty(roleNames)
							&& UtilValidate.isNotEmpty(roleIds)) {
						roleIds += ",";
						roleNames += ",";
					}
					roleIds += role.getRoleId();
					roleNames += role.getRoleName();
					// 角色拥有的功能列表
					List<Function> function = role.getFunctions();
					if (UtilValidate.isNotEmpty(function)) {
						for (Function func : function) {
							if (UtilValidate.isNotEmpty(func)) {
								functionList.add(func.getUrl());
							}
						}
					}
				}
				sessionInfo.setRoleIds(roleIds);// 角色ID
				sessionInfo.setRoleNames(roleNames);// 角色名称
//				sessionInfo.setIp(IpUtil.getIpAddr(request));
				sessionInfo.setFunctionUrls(functionList);// 功能地址
			}
			// 用户单位设置
			if (UtilValidate.isNotEmpty(u.getOrganization())) {
				sessionInfo.setOrgnizationId(String.valueOf(u.getOrganization()
						.getDictionaryId()));// 所属单位ID
				sessionInfo.setOrgnizationName(u.getOrganization().getValue());// 所属单位名称
			}
			session.setAttribute(ResourceUtil.getSessionInfoName(), sessionInfo);// 设置session
			return JsonResult.success("登录成功！", null);
		} else {
			return JsonResult.failed("用户名密码不匹配！", null);
		}
	}

	/**
	 * 登录后页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/forward")
	public ModelAndView forward(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws Exception {
		logger.info("forward");
		return new ModelAndView("/user");
	}
	
	/**
	 * 咨询菜单跳转（带默认选择）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/forwardMain")
	public ModelAndView forwardMain(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,String operaterType) throws Exception {
		request.setAttribute("typeValue", operaterType);
		return new ModelAndView("/user");
	}
	
	/**
	 * 受理页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/acceptPage")
	public ModelAndView acceptPage(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws Exception {
		return new ModelAndView("/seatAccept");
	}
	/**
	 * 用户退出
	 * 
	 * @param member
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public JsonResult logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return JsonResult.success("注销成功！", null);
	}
	/**
	 * 修改密码(当前用户)
	 * 
	 * @param membervo
	 * @return
	 */
	@RequestMapping(value = "/modifyCurrentPwd")
	@ResponseBody
	public JsonResult modifyCurrentPwd(HttpServletRequest request,MemberVO membervo) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		membervo.setMemberId(Long.valueOf(sessionInfo.getUserId()));
		MemberVO memberInfo = memberService.getMemberById(sessionInfo.getUserId());
		if(membervo.getOldPassword().equals(memberInfo.getSavedPassword())){
			memberService.modifyPwd(membervo);
			return JsonResult.success("密码修改成功！", membervo);
		}else{
			return JsonResult.failed("原密码填写错误！", membervo);
		}
	}
	/**
	 * 用户信息页面
	 * @param orderId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/userInfoPage")
	public ModelAndView userInfoPage(HttpServletRequest request) throws Exception {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		MemberVO member = memberService.getMemberById(sessionInfo.getUserId());
		request.setAttribute("memberInfo", member);
		int taskNum = taskOrderService.getTaskNum();
		int erSaiTaskNum = ersaiTaskOrderService.getTaskNum();
		int yansTaskNum = yansTaskOrderService.getTaskNum();
		int fcwxTaskNum = taskOrderTestService.getTaskNum();
		int fsrwTaskNum = radiationTaskOrderService.getTaskNum();
		request.setAttribute("taskInfo", taskNum);
		request.setAttribute("ersaiTaskInfo", erSaiTaskNum);
		request.setAttribute("yansTaskInfo", yansTaskNum);
		request.setAttribute("fcwxTaskInfo", fcwxTaskNum);
		request.setAttribute("fsTaskInfo", fsrwTaskNum);
		return new ModelAndView("/admin/currentUserInfo");
	}
}
