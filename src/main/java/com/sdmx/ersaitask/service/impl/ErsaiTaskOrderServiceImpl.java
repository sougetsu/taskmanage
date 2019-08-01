package com.sdmx.ersaitask.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.sdmx.ersaitask.dao.IErsaiAttachmentDao;
import com.sdmx.ersaitask.dao.IErsaiLshDao;
import com.sdmx.ersaitask.dao.IErsaiTaskOrderDao;
import com.sdmx.ersaitask.dao.IErsaiTaskPriceDao;
import com.sdmx.ersaitask.entity.ErsaiAttachment;
import com.sdmx.ersaitask.entity.ErsaiTaskOrder;
import com.sdmx.ersaitask.entity.ErsaiTaskPrice;
import com.sdmx.ersaitask.entity.ErsaiTaskSchedule;
import com.sdmx.ersaitask.service.IErsaiAttachmentService;
import com.sdmx.ersaitask.service.IErsaiTaskOrderService;
import com.sdmx.ersaitask.vo.ErsaiTaskOrderStatus;
import com.sdmx.ersaitask.vo.ErsaiTaskOrderVO;
import com.sdmx.exportexcel.dto.ExcelDateInfo;
import com.sdmx.exportexcel.service.IExportService;
import com.sdmx.framework.dao.IBaseDao;
import com.sdmx.framework.dao.IDictionaryDao;
import com.sdmx.framework.dao.IMemberDao;
import com.sdmx.framework.entity.Dictionary;
import com.sdmx.framework.entity.Member;
import com.sdmx.framework.util.Arith;
import com.sdmx.framework.util.BeanUtilsEx;
import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.RoleType;
import com.sdmx.framework.vo.SessionInfo;
import com.sdmx.taskmanage.dao.IOperateLogDao;
import com.sdmx.taskmanage.dao.IPriceItemDao;
import com.sdmx.taskmanage.entity.OperateLog;
import com.sdmx.taskmanage.entity.PriceItem;
import com.sdmx.taskmanage.vo.AttachmentVO;
import com.sdmx.taskmanage.vo.DateUtil;
import com.sdmx.taskmanage.vo.LogVO;
import com.sdmx.taskmanage.vo.OperatorQueryType;
import com.sdmx.taskmanage.vo.PriceCheckVO;
import com.sdmx.taskmanage.vo.TaskOrderStatus;
import com.sdmx.taskmanage.vo.TaskPriceVO;
import com.sdmx.taskmanage.vo.TaskScheduleVO;
import com.sdmx.yansTask.service.IYansTaskOrderService;
import com.sdmx.yansTask.vo.YansTaskOrderStatus;
import com.sdmx.yansTask.vo.YansTaskOrderVO;

@Service("ersaiTaskOrderService")
public class ErsaiTaskOrderServiceImpl implements IErsaiTaskOrderService{
	@Autowired
	private IDictionaryDao dictionaryDao;
	@Autowired
	private IMemberDao memberDao;
	@Autowired
	private IErsaiLshDao ersaiLshDao;
	@Autowired
	private IErsaiTaskOrderDao ersaiTaskOrderDao;
	@Autowired
	private IOperateLogDao operatelogDao;
	@Autowired
	private IPriceItemDao priceItemDao;
	@Autowired
	private IErsaiTaskPriceDao ersaiTaskPriceDao;
	@Autowired
	private IBaseDao<ErsaiTaskSchedule> baseDao;
	@Autowired
	private IYansTaskOrderService yansTaskOrderService;
	@Autowired
	private IErsaiAttachmentService ersaiAttachmentService;
	@Autowired
	private IErsaiAttachmentDao ersaiAttachmentDao;
	@Autowired
	private IExportService exportExcelService;
	@Override
	@Transactional
	public ErsaiTaskOrder create(ErsaiTaskOrderVO ersaiTaskOrdervo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        Member currentMember = memberDao.get(Member.class, Long.parseLong(sessionInfo.getUserId()));
        ErsaiTaskOrder taskOrder = new ErsaiTaskOrder();
		BeanUtilsEx.copyProperties(taskOrder, ersaiTaskOrdervo);
		//课题号
		Dictionary project = dictionaryDao.get(Dictionary.class, Long.parseLong(ersaiTaskOrdervo.getTopicId()));
		taskOrder.setTopic(project);
		//协助部门
		Dictionary helpDept = dictionaryDao.get(Dictionary.class, Long.parseLong(ersaiTaskOrdervo.getHelpDeptId()));
		taskOrder.setHelpDept(helpDept);
		
		//希望完成时间
		taskOrder.setWantedEndDate(ersaiTaskOrdervo.getWantedEndDate());
		taskOrder.setCreatetime(new Date());
		switch(memType){
      	//只有市场调度，生产调度有创建功能
			case DepartManage:
			case DepartMember :{
				taskOrder.setStatus(TaskOrderStatus.WAITTOCONFIRM_PRODUCTMANAGE.getValue());
				break;
			}
			case ProductManage:{
				taskOrder.setStatus(TaskOrderStatus.WAITTOCONFIRM_TESTCENTERMANAGE.getValue());
				break;
			}
			default:{
				break;
			}
      	}
		taskOrder.setMember(currentMember);
		//生成流水号
		taskOrder.setLsh(ersaiLshDao.getLsh());
		
		ersaiTaskOrderDao.save(taskOrder);
		//有附件
		if(ersaiTaskOrdervo.getAttachmentFlag() == 1){
			// 给附件指定所属信息项
			if (ersaiTaskOrdervo.getAttachids() != null) {
				for (Integer aid : ersaiTaskOrdervo.getAttachids()) {
					ErsaiAttachment att = ersaiAttachmentDao.get(ErsaiAttachment.class, aid);
					att.setErsaiTaskOrder(taskOrder);
					ersaiAttachmentDao.update(att);
				}
			}
		}
		OperateLog opLog = new OperateLog();
		opLog.setLshId(taskOrder.getLsh());
		opLog.setContent(sessionInfo.getLoginName()+"创建了任务单"+taskOrder.toLogInfo());
		opLog.setMemberId(currentMember.getMemberId());
		opLog.setCreatetime(new Date());
		operatelogDao.save(opLog);
		return taskOrder;
	}
	@Override
	public DataGrid listTaskOrder(ErsaiTaskOrderVO ersaiTaskOrderVO) {
		DataGrid dg = new DataGrid();
		String hql = "from ErsaiTaskOrder t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(ersaiTaskOrderVO, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(ersaiTaskOrderVO, hql);
		List<ErsaiTaskOrder> ersaiTaskOrderList = ersaiTaskOrderDao.find(hql, params, ersaiTaskOrderVO.getPage(), ersaiTaskOrderVO.getRows());
		List<ErsaiTaskOrderVO> ersaiTaskOrderVOList = new ArrayList<ErsaiTaskOrderVO>();
		changeModelList(ersaiTaskOrderList, ersaiTaskOrderVOList);
		dg.setTotal(ersaiTaskOrderDao.count(totalHql, params));
		dg.setRows(ersaiTaskOrderVOList);
		return dg;
	}
	private String addWhere(ErsaiTaskOrderVO ersaiTaskOrderVO, String hql, Map<String, Object> params) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		hql += " where 1=1 and t.status !=0 ";
		
		//任务类型
		if (UtilValidate.isNotEmpty(ersaiTaskOrderVO.getTaskType())) {
			hql += " and t.taskType like :taskType";
			params.put("taskType", "%%" +ersaiTaskOrderVO.getTaskType().trim() + "%%");
		}
		//申请人
		if (UtilValidate.isNotEmpty(ersaiTaskOrderVO.getApplyMember())) {
			hql += " and t.applyMember like :applyMember";
			params.put("applyMember", "%%" +ersaiTaskOrderVO.getApplyMember().trim() + "%%");
		}
		//通知单号
		if (UtilValidate.isNotEmpty(ersaiTaskOrderVO.getReportNo())) {
			hql += " and t.reportNo like :reportNo";
			params.put("reportNo", "%%" +ersaiTaskOrderVO.getReportNo().trim() + "%%");
		}
		//任务单号
		if (UtilValidate.isNotEmpty(ersaiTaskOrderVO.getLsh())) {
			hql += " and t.lsh= :lshNo";
			params.put("lshNo", ersaiTaskOrderVO.getLsh().trim());
		}
		//开始登记时间
		if (ersaiTaskOrderVO.getRegistTimeStart() != null) {
			hql += " and t.createtime >= :regStartDate";
			params.put("regStartDate", ersaiTaskOrderVO.getRegistTimeStart());
		}
		//结束登记时间
		if (ersaiTaskOrderVO.getRegistTimeEnd() != null) {
			hql += " and t.createtime <= :regEndDate";
			params.put("regEndDate", ersaiTaskOrderVO.getRegistTimeEnd());
		}
		//根据角色确定内容
		RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
		switch(memType){
			case DepartManage:
			case DepartMember :{
				//待处理查询
				if(OperatorQueryType.SuspendingQuery.getValue().equals(ersaiTaskOrderVO.getClType())){
					hql += " and t.status in (21,22,23,24)";
					hql += " and t.member.memberId = :getMemberId ";
					params.put("getMemberId", Long.parseLong(sessionInfo.getUserId()));
				}else{
					hql += " and t.member.memberId = :getMemberId ";
					params.put("getMemberId", Long.parseLong(sessionInfo.getUserId()));
				}
				break;
			}
			case ProductManage:{
				//待处理查询
				if(OperatorQueryType.SuspendingQuery.getValue().equals(ersaiTaskOrderVO.getClType())){
					hql += " and (t.status ='31' " +
							" or (t.status ='23' and t.member.memberId = :getMemberId))";
					params.put("getMemberId", Long.parseLong(sessionInfo.getUserId()));
				}
				break;
			}
			case TestCenterManage:{
				//待处理查询
				if(OperatorQueryType.SuspendingQuery.getValue().equals(ersaiTaskOrderVO.getClType())){
					hql += " and t.status in (41,42,43)";
				}
				break;
			}
			default:{
				break;
			}
		}
		
		return hql;
	}
	private String addOrder(ErsaiTaskOrderVO ersaiTaskOrderVO, String hql){
		if (ersaiTaskOrderVO.getSort() != null) {
			if("statusName".equals(ersaiTaskOrderVO.getSort())){
				hql += " order by t.status "  + ersaiTaskOrderVO.getOrder();
			}else if("projectName".equals(ersaiTaskOrderVO.getSort())){
				hql += " order by t.project.value "  + ersaiTaskOrderVO.getOrder();
			}else if("topicNo".equals(ersaiTaskOrderVO.getSort())){
				hql += " order by t.topic.value "  + ersaiTaskOrderVO.getOrder();
			}else{
				hql += " order by " + ersaiTaskOrderVO.getSort() + " " + ersaiTaskOrderVO.getOrder();
			}
		}else{
			hql += " order by t.createtime desc";
		}
		return hql;
	}
	/**
	 * 根据实体转化页面展示项(集合)
	 * @param l
	 * @param nl
	 */
	private void changeModelList(List<ErsaiTaskOrder> l, List<ErsaiTaskOrderVO> nl) {
		if (l != null && l.size() > 0) {
			for (Iterator<ErsaiTaskOrder> iterator = l.iterator(); iterator.hasNext();) {
				ErsaiTaskOrder taskOrder = (ErsaiTaskOrder) iterator.next();
				ErsaiTaskOrderVO taskOrderVO = getDtoData(taskOrder);
				nl.add(taskOrderVO);
			}
		}
	}
	private ErsaiTaskOrderVO getDtoData(ErsaiTaskOrder ersaiTaskOrder){
		ErsaiTaskOrderVO ersaiTaskOrderVO = new ErsaiTaskOrderVO();
		BeanUtilsEx.copyProperties(ersaiTaskOrderVO,ersaiTaskOrder);
		ersaiTaskOrderVO.setTopicId(String.valueOf(ersaiTaskOrder.getTopic().getDictionaryId()));
		ersaiTaskOrderVO.setTopicName(ersaiTaskOrder.getTopic().getAnnotation());
		
		
		ersaiTaskOrderVO.setOrderId(String.valueOf(ersaiTaskOrder.getOrderId()));
		ersaiTaskOrderVO.setHelpDeptId(String.valueOf(ersaiTaskOrder.getHelpDept().getDictionaryId()));
		ersaiTaskOrderVO.setHelpDeptName(ersaiTaskOrder.getHelpDept().getValue());
		//状态名称
		for (ErsaiTaskOrderStatus type : ErsaiTaskOrderStatus.values()) {
			if(ersaiTaskOrder.getStatus()== type.getValue()){
				ersaiTaskOrderVO.setStatusName(type.getLabel());
			}
		}
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        //如果是部门待确认状态，任务单创建者具有确认权限
        if(ersaiTaskOrder.getStatus() == ErsaiTaskOrderStatus.WAITTOFIX_DEPARTMANAGE.getValue()){
        	if(String.valueOf(ersaiTaskOrder.getMember().getMemberId()).equals(sessionInfo.getUserId())){
        		ersaiTaskOrderVO.setFixState(1);
        	}else{
        		ersaiTaskOrderVO.setFixState(0);
        	}
        }
        //设置审核权限和核价，修改权限
        switch(memType){
        	case DepartManage:
	        case DepartMember :{
	    		if(ersaiTaskOrder.getStatus() == YansTaskOrderStatus.NOTPASS_PRODUCTMANAGE.getValue()||
	    				ersaiTaskOrder.getStatus() == YansTaskOrderStatus.NOTPASS_TESTCENTERMANAGE.getValue()){
	    			ersaiTaskOrderVO.setEditState(1);
	        	}
	        	break;
	    	}
      	//生产调度，封测有审核功能
			case ProductManage:{
				if(ersaiTaskOrder.getStatus() == ErsaiTaskOrderStatus.WAITTOCONFIRM_PRODUCTMANAGE.getValue()){
					ersaiTaskOrderVO.setConfirmState(1);
				}
				ersaiTaskOrderVO.setEditState(1);
				ersaiTaskOrderVO.setDeleteState(1);
				ersaiTaskOrderVO.setUrgencyState(1);
				break;
			}
			case TestCenterManage:{
				if(ersaiTaskOrder.getStatus() == TaskOrderStatus.WAITTOCONFIRM_TESTCENTERMANAGE.getValue()){
					ersaiTaskOrderVO.setConfirmState(1);
				}
				if(ersaiTaskOrder.getStatus() == TaskOrderStatus.WAITTOCHARGE_TESTCENTERMANAGE.getValue()){
					ersaiTaskOrderVO.setPriceState(1);
				}
				if(ersaiTaskOrder.getStatus() == TaskOrderStatus.WAITTOFIX_TESTCENTERMANAGE.getValue()){
					ersaiTaskOrderVO.setFixState(1);
					ersaiTaskOrderVO.setPriceEditState(1);
				}
				break;
			}
			default:{
				ersaiTaskOrderVO.setConfirmState(0);
				ersaiTaskOrderVO.setPriceState(0);
				break;
			}
		
        }
        //附件
        if(ersaiTaskOrder.getAttachmentFlag() == 1){
        	List<ErsaiAttachment> attach = ersaiAttachmentService.listByCode(ersaiTaskOrder.getOrderId());
    		if (UtilValidate.isNotEmpty(attach)) {
    			List<AttachmentVO> attachvo = new ArrayList<AttachmentVO>();
    			for (ErsaiAttachment attachment : attach) {
    				AttachmentVO avo = new AttachmentVO();
    				BeanUtilsEx.copyProperties(avo, attachment);
    				attachvo.add(avo);
    			}
    			ersaiTaskOrderVO.setAttachment(attachvo);// 设置附件信息
    		}
        }
		return ersaiTaskOrderVO;
	}
	@Override
	public DataGrid listSusTaskOrder(ErsaiTaskOrderVO ersaiTaskOrderVO) {
		DataGrid dg = new DataGrid();
		String hql = "from ErsaiTaskOrder t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(ersaiTaskOrderVO, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(ersaiTaskOrderVO, hql);
		List<ErsaiTaskOrder> l = ersaiTaskOrderDao.find(hql, params, ersaiTaskOrderVO.getPage(), ersaiTaskOrderVO.getRows());
		List<ErsaiTaskOrderVO> nl = new ArrayList<ErsaiTaskOrderVO>();
		changeModelList(l, nl);
		dg.setTotal(ersaiTaskOrderDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	@Override
	public ErsaiTaskOrderVO getTaskOrderById(String taskId) {
		ErsaiTaskOrder taskOrder = ersaiTaskOrderDao.get(ErsaiTaskOrder.class, Long.parseLong(taskId));
		ErsaiTaskOrderVO taskOrderVO = getDtoData(taskOrder);
		return taskOrderVO;
	}
	@Override
	public List<TaskPriceVO> getTaskPriceListById(String taskId) {
		ErsaiTaskOrder taskOrder = ersaiTaskOrderDao.get(ErsaiTaskOrder.class, Long.parseLong(taskId));
		Set<ErsaiTaskPrice> tpList = taskOrder.getTaskPriceList();
		List<TaskPriceVO> taskPriceList = new ArrayList<TaskPriceVO>();
		for (ErsaiTaskPrice tp : tpList) {
			TaskPriceVO tpvo = new TaskPriceVO();
			tpvo.setAmount(tp.getItemNum());
			tpvo.setBasePrice(tp.getPriceItem().getBasePrice());
			tpvo.setChargeUnit(tp.getPriceItem().getChargeUnit());
			tpvo.setItemId(String.valueOf(tp.getPriceItem().getItemId()));
			tpvo.setItemName(tp.getPriceItem().getItemName());
			tpvo.setPrice(tp.getPriceItem().getPrice());
			tpvo.setRemarks(tp.getRemarks());
			tpvo.setTaskPriceId(String.valueOf(tp.getTaskPriceId()));
			tpvo.setUnitcost(tp.getItemSum());
			taskPriceList.add(tpvo);
		}
		return taskPriceList;
	}
	@Override
	public TaskScheduleVO getTaskScheduleById(String taskId) {
		ErsaiTaskOrder taskOrder = ersaiTaskOrderDao.get(ErsaiTaskOrder.class, Long.parseLong(taskId));
		TaskScheduleVO taskScheduleVO = new TaskScheduleVO();
		ErsaiTaskSchedule taskSchedule = taskOrder.getErsaiTaskSchedule();
		BeanUtils.copyProperties(taskSchedule, taskScheduleVO);
		return taskScheduleVO;
	}
	@Override
	@Transactional
	public ErsaiTaskOrder confirmOK(ErsaiTaskOrderVO ersaiTaskOrderVO) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        ErsaiTaskOrder taskOrder = ersaiTaskOrderDao.get(ErsaiTaskOrder.class, Long.parseLong(ersaiTaskOrderVO.getOrderId()));
		//任务类型
        taskOrder.setTaskType(ersaiTaskOrderVO.getTaskType());
		//通知单号
		taskOrder.setReportNo(ersaiTaskOrderVO.getReportNo());
		//所内型号
		taskOrder.setInternalModel(ersaiTaskOrderVO.getInternalModel());
		//申请部门
		taskOrder.setApplyDept(ersaiTaskOrderVO.getApplyDept());
		//申 请 人
		taskOrder.setApplyMember(ersaiTaskOrderVO.getApplyMember());
		//申请人电话
		taskOrder.setApplyMemberPhone(ersaiTaskOrderVO.getApplyMemberPhone());
		//课 题 号
		Dictionary topic = dictionaryDao.get(Dictionary.class, Long.parseLong(ersaiTaskOrderVO.getTopicId()));
		taskOrder.setTopic(topic);
		//项目负责人
		taskOrder.setProjectManager(ersaiTaskOrderVO.getProjectManager());
		//项目负责人电话
		taskOrder.setProjectManagerPhone(ersaiTaskOrderVO.getProjectManagerPhone());
		//请求协助部门
		Dictionary helpDept = dictionaryDao.get(Dictionary.class, Long.parseLong(ersaiTaskOrderVO.getHelpDeptId()));
		taskOrder.setHelpDept(helpDept);
		//希望完成时间
		taskOrder.setWantedEndDate(ersaiTaskOrderVO.getWantedEndDate());
		//是否有附件
		taskOrder.setAttachmentFlag(ersaiTaskOrderVO.getAttachmentFlag());
		//紧急程度
		taskOrder.setUrgency(ersaiTaskOrderVO.getUrgency());
		//申请原因及说明
		taskOrder.setApplyReason(ersaiTaskOrderVO.getApplyReason());
		//具体要求（二筛条件）
		taskOrder.setDetailRequire(ersaiTaskOrderVO.getDetailRequire());
		taskOrder.setRemarks(ersaiTaskOrderVO.getRemarks());
		taskOrder.setProductManagesuggest(ersaiTaskOrderVO.getProductManagesuggest());
		switch(memType){
			case DepartManage:
			case DepartMember:{
				taskOrder.setStatus(ErsaiTaskOrderStatus.WAITTOCONFIRM_PRODUCTMANAGE.getValue());
				break;
			}
      		//只有生产调度，封测有审核功能
			case ProductManage:{
				if(taskOrder.getStatus() != ErsaiTaskOrderStatus.COMPLETED.getValue()){
					taskOrder.setStatus(ErsaiTaskOrderStatus.WAITTOCONFIRM_TESTCENTERMANAGE.getValue());
				}
				break;
			}
			case TestCenterManage:{
				taskOrder.setStatus(ErsaiTaskOrderStatus.WAITTOCHARGE_TESTCENTERMANAGE.getValue());
				break;
			}
			default:{
				break;
			}
      	}
		//有附件
		if(ersaiTaskOrderVO.getAttachmentFlag() == 1){
			// 给附件指定所属信息项
			if (ersaiTaskOrderVO.getAttachids() != null) {
				for (Integer aid : ersaiTaskOrderVO.getAttachids()) {
					ErsaiAttachment att = ersaiAttachmentDao.get(ErsaiAttachment.class, aid);
					att.setErsaiTaskOrder(taskOrder);
					ersaiAttachmentDao.update(att);
				}
			}
		}
		ersaiTaskOrderDao.saveOrUpdate(taskOrder);
		//操作日志
		OperateLog opLog = new OperateLog();
		opLog.setLshId(taskOrder.getLsh());
		opLog.setContent(sessionInfo.getLoginName()+"审核通过了任务单"+taskOrder.toLogInfo());
		opLog.setMemberId(Long.valueOf(sessionInfo.getUserId()));
		opLog.setCreatetime(new Date());
		operatelogDao.save(opLog);
		return taskOrder;
	}
	@Override
	@Transactional
	public ErsaiTaskOrder confirmNG(ErsaiTaskOrderVO ersaiTaskOrderVO) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        ErsaiTaskOrder taskOrder = ersaiTaskOrderDao.get(ErsaiTaskOrder.class, Long.parseLong(ersaiTaskOrderVO.getOrderId()));
		//不通过原因
		taskOrder.setProductManagesuggest(ersaiTaskOrderVO.getProductManagesuggest());
		switch(memType){
			//只有生产调度，封测有审核功能
			case ProductManage:{
				taskOrder.setStatus(ErsaiTaskOrderStatus.NOTPASS_PRODUCTMANAGE.getValue());
				break;
			}
			case TestCenterManage:{
				taskOrder.setStatus(ErsaiTaskOrderStatus.NOTPASS_TESTCENTERMANAGE.getValue());
				break;
			}
			default:{
				break;
			}
      	}
		ersaiTaskOrderDao.saveOrUpdate(taskOrder);
		//操作日志
		OperateLog opLog = new OperateLog();
		opLog.setLshId(taskOrder.getLsh());
		opLog.setContent(sessionInfo.getLoginName()+"审核未通过任务单"+taskOrder.toLogInfo());
		opLog.setMemberId(Long.valueOf(sessionInfo.getUserId()));
		opLog.setCreatetime(new Date());
		operatelogDao.save(opLog);
		return taskOrder;
	}
	@Override
	@Transactional
	public ErsaiTaskOrder fixComplete(ErsaiTaskOrderVO ersaiTaskOrderVO) {
		ErsaiTaskOrder taskOrder = ersaiTaskOrderDao.get(ErsaiTaskOrder.class, Long.parseLong(ersaiTaskOrderVO.getOrderId()));
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        switch(memType){
			case TestCenterManage:{
				taskOrder.setStatus(ErsaiTaskOrderStatus.WAITTOFIX_DEPARTMANAGE.getValue());
				break;
			}
			default:{
				//取得是否要验收
				if(ersaiTaskOrderVO.getYansFlag()==1){
					ErsaiTaskOrderVO evo = getDtoData(taskOrder);
					evo.setYansFlag(ersaiTaskOrderVO.getYansFlag());
					evo.setYansNum(ersaiTaskOrderVO.getYansNum());
					YansTaskOrderVO yansTaskOrderVO = new YansTaskOrderVO();
					BeanUtilsEx.copyProperties(yansTaskOrderVO, evo);
					yansTaskOrderVO.setErsaiFlag(1);
					yansTaskOrderVO.setErsaiLsh(taskOrder.getLsh());
					yansTaskOrderVO.setAttachmentFlag(0);
					yansTaskOrderVO.setTaskType("验收任务");
					yansTaskOrderVO.setSumPrice(0.0);
					yansTaskOrderVO.setWantedEndDate(taskOrder.getWantedEndDate());
					yansTaskOrderService.create(yansTaskOrderVO);
					
					taskOrder.setStatus(ErsaiTaskOrderStatus.COMPLETEDTOYANS.getValue());
				}else{
					taskOrder.setStatus(ErsaiTaskOrderStatus.COMPLETED.getValue());
				}
				break;
			}
      	}
        ersaiTaskOrderDao.update(taskOrder);
        //操作日志
  		OperateLog opLog = new OperateLog();
  		opLog.setLshId(taskOrder.getLsh());
  		opLog.setContent(sessionInfo.getLoginName()+"对任务单进行了确认");
  		opLog.setMemberId(Long.valueOf(sessionInfo.getUserId()));
  		opLog.setCreatetime(new Date());
  		operatelogDao.save(opLog);
        return taskOrder;
	
	}
	@Override
	public HttpServletResponse downloadExcel(ErsaiTaskOrderVO ersaiTaskOrderVO,
			HttpServletResponse response) throws Exception {
		ExcelDateInfo excelDateInfo = new ExcelDateInfo();
		String hql = "from ErsaiTaskOrder t";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(ersaiTaskOrderVO, hql, params);
		hql = addOrder(ersaiTaskOrderVO, hql);
		List<ErsaiTaskOrder> l = ersaiTaskOrderDao.find(hql, params, ersaiTaskOrderVO.getPage(), ersaiTaskOrderVO.getRows());
		
		editExcelInfo(excelDateInfo);
		excelDateInfo.setDateInfo(exportChangeModel(l));
		return exportExcelService.export(excelDateInfo,response);
	}
	/**
	 * 设置导出文件的标题
	 * @param excelDateInfo
	 */
	private void editExcelInfo(ExcelDateInfo excelDateInfo){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		excelDateInfo.setFileName("ersaitaskList_"+date);
		excelDateInfo.setSheetName("二筛任务单数据");
		excelDateInfo.setTitle("二筛任务单数据导出数据如下");
		excelDateInfo.setColumnTitle(columnTitle());
	}
	/**
	 * 明细列标题
	 * @return
	 */
	private List<String> columnTitle(){
		List<String> columnTitle = new ArrayList<String>();
		columnTitle.add("任务单号");
		columnTitle.add("任务类型");
		columnTitle.add("通知单号");
		columnTitle.add("所内型号");
		columnTitle.add("申请部门 ");
		columnTitle.add("申请人");
		columnTitle.add("成本课题号");
		columnTitle.add("项目负责人");
		columnTitle.add("申请日期");
		columnTitle.add("希望完成时间");
		columnTitle.add("申请原因");
		columnTitle.add("二筛条件");
		columnTitle.add("价格");
		columnTitle.add("任务单状态");

		return columnTitle;
	}
	
	/**
	 * 导出数据
	 * @param consults
	 * @return
	 */
	private List<List<String>> exportChangeModel(List<ErsaiTaskOrder> taskOrder){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<List<String>> rows = new ArrayList<List<String>>();
		List<String> columns = null;
		for (Iterator<ErsaiTaskOrder> iterator = taskOrder.iterator(); iterator.hasNext();) {
			ErsaiTaskOrder task = (ErsaiTaskOrder) iterator.next();
			columns = new ArrayList<String>();
			columns.add(task.getLsh());//任务单号
			columns.add(task.getTaskType());//任务类型
			columns.add(task.getReportNo());//通知单号
			columns.add(task.getInternalModel());//所内型号
			columns.add(task.getApplyDept());//申请部门
			columns.add(task.getApplyMember());//申请人
			columns.add(task.getTopic().getValue());//成本课题号
			columns.add(task.getProjectManager());//项目负责人
			columns.add(task.getCreatetime()==null?"":sdf.format(task.getCreatetime()));//申请日期
			columns.add(task.getWantedEndDate()==null?"":sdf.format(task.getWantedEndDate()));//希望完成时间
			columns.add(task.getApplyReason());//申请原因
			columns.add(task.getDetailRequire());//二筛条件
			columns.add(String.valueOf(task.getSumPrice()));//价格
			//状态名称
			String statusName="";
			for (ErsaiTaskOrderStatus type : ErsaiTaskOrderStatus.values()) {
				if(task.getStatus()== type.getValue()){
					statusName = type.getLabel();
				}
			}
			columns.add(statusName);//任务单状态
			rows.add(columns);
		}
		
		return rows;
	}
	@Override
	@Transactional
	public ErsaiTaskOrder checkPrice(PriceCheckVO priceCheckVO) {
		List<TaskPriceVO> listPrice = new ArrayList<TaskPriceVO>();
		ErsaiTaskOrder taskOrder = ersaiTaskOrderDao.get(ErsaiTaskOrder.class, Long.parseLong(priceCheckVO.getOrderId()));
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		if(UtilValidate.isNotEmpty(priceCheckVO.getPriceItems())){
			listPrice = JSON.parseArray(priceCheckVO.getPriceItems(), TaskPriceVO.class);
		}
		List<Long> deleteList = new ArrayList<Long>();
		//初始核价
		if(UtilValidate.isEmpty(taskOrder.getTaskPriceList())){
			for (TaskPriceVO taskPriceVO : listPrice) {
				if(UtilValidate.isNotEmpty(taskPriceVO.getUnitcost()) && taskPriceVO.getUnitcost()!=0.0){
					ErsaiTaskPrice tp  =  new ErsaiTaskPrice();
					PriceItem pi = priceItemDao.get(PriceItem.class, Long.parseLong(taskPriceVO.getItemId()));
					tp.setItemNum(taskPriceVO.getAmount());
					tp.setItemSum(taskPriceVO.getUnitcost());
					tp.setPriceItem(pi);
					tp.setRemarks(taskPriceVO.getRemarks());
					tp.setErsaiTaskOrder(taskOrder);
					taskOrder.addPriceItem(tp);
				}
			}
		}else{
			//修改核价
			Set<ErsaiTaskPrice> taskPriceListOld = taskOrder.getTaskPriceList();
			for (TaskPriceVO taskPriceVO : listPrice) {
				String itemID = taskPriceVO.getItemId();
				boolean isExist = false;
				for (ErsaiTaskPrice erSaitaskPrice : taskPriceListOld) {
					if(erSaitaskPrice.getPriceItem().getItemId().toString().equals(itemID)){
						isExist = true;
						erSaitaskPrice.setItemNum(taskPriceVO.getAmount());
						erSaitaskPrice.setItemSum(taskPriceVO.getUnitcost());
						erSaitaskPrice.setRemarks(taskPriceVO.getRemarks());
						if(taskPriceVO.getUnitcost()==0.0){
							deleteList.add(erSaitaskPrice.getTaskPriceId());
						}
					}
				}
				if(!isExist){
					if(UtilValidate.isNotEmpty(taskPriceVO.getUnitcost()) && taskPriceVO.getUnitcost()!=0.0 ){
						ErsaiTaskPrice tp  =  new ErsaiTaskPrice();
						PriceItem pi = priceItemDao.get(PriceItem.class, Long.parseLong(taskPriceVO.getItemId()));
						tp.setItemNum(taskPriceVO.getAmount());
						tp.setItemSum(taskPriceVO.getUnitcost());
						tp.setPriceItem(pi);
						tp.setRemarks(taskPriceVO.getRemarks());
						tp.setErsaiTaskOrder(taskOrder);
						taskOrder.addPriceItem(tp);
					}
				}
			}
			//
			for (Long deleteId : deleteList) {
				ErsaiTaskPrice  taskPrice =  ersaiTaskPriceDao.get(ErsaiTaskPrice.class, deleteId);
				taskOrder.removePriceItem(taskPrice);
			}
		}
		Set<ErsaiTaskPrice> taskPriceListAfter = taskOrder.getTaskPriceList();
		Double sumPrice = 0.0;
		for (ErsaiTaskPrice taskPrice : taskPriceListAfter) {
			sumPrice = Arith.add(sumPrice, taskPrice.getItemSum());
		}
		taskOrder.setSumPrice(sumPrice);
		//初始核价
		if(UtilValidate.isEmpty(taskOrder.getErsaiTaskSchedule())){
			ErsaiTaskSchedule ts = new ErsaiTaskSchedule();
			BeanUtils.copyProperties(priceCheckVO, ts);
			ts.setPakTime(DateUtil.getDate(ts.getPakstartDate(),ts.getPakendDate()));
//			ts.setSvTime(DateUtil.getDate(ts.getSvstartDate(),ts.getSvendDate()));
//			ts.setTestTime(DateUtil.getDate(ts.getTeststartDate(),ts.getTestendDate()));
//			ts.setSxTime(DateUtil.getDate(ts.getSxstartDate(),ts.getSxendDate()));
//			ts.setJdTime(DateUtil.getDate(ts.getJdstartDate(),ts.getJdendDate()));
			baseDao.save(ts);
			taskOrder.setErsaiTaskSchedule(ts);
		}else{
			//修改核价
			ErsaiTaskSchedule ts = taskOrder.getErsaiTaskSchedule();
			BeanUtils.copyProperties(priceCheckVO, ts);
			ts.setPakTime(DateUtil.getDate(ts.getPakstartDate(),ts.getPakendDate()));
//			ts.setSvTime(DateUtil.getDate(ts.getSvstartDate(),ts.getSvendDate()));
//			ts.setTestTime(DateUtil.getDate(ts.getTeststartDate(),ts.getTestendDate()));
//			ts.setSxTime(DateUtil.getDate(ts.getSxstartDate(),ts.getSxendDate()));
//			ts.setJdTime(DateUtil.getDate(ts.getJdstartDate(),ts.getJdendDate()));
			taskOrder.setErsaiTaskSchedule(ts);
		}
		taskOrder.setStatus(ErsaiTaskOrderStatus.WAITTOFIX_TESTCENTERMANAGE.getValue());
		ersaiTaskOrderDao.update(taskOrder);
		for (Long deleteId : deleteList) {
			ErsaiTaskPrice  taskPrice =  ersaiTaskPriceDao.get(ErsaiTaskPrice.class, deleteId);
			ersaiTaskPriceDao.delete(taskPrice);
		}
		//操作日志
		OperateLog opLog = new OperateLog();
		opLog.setLshId(taskOrder.getLsh());
		opLog.setContent(sessionInfo.getLoginName()+"对任务单进行了核价");
		opLog.setMemberId(Long.valueOf(sessionInfo.getUserId()));
		opLog.setCreatetime(new Date());
		operatelogDao.save(opLog);
		return taskOrder;
	}
	@Override
	public int getTaskNum() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        Map<String, Object> params = new HashMap<String, Object>();
        String hql = "from ErsaiTaskOrder t where 1=1 and t.status !=0  ";
        switch(memType){
        	case DepartManage:
	        case DepartMember :{
	        	hql += " and t.status in (22,23,24)";
	        	hql += " and t.member.memberId = :getMemberId ";
				params.put("getMemberId", Long.parseLong(sessionInfo.getUserId()));
				break;
			}
			case ProductManage:{
				hql += " and (t.status ='31' " +
						" or (t.status ='23' and t.member.memberId = :getMemberId))";
				params.put("getMemberId", Long.parseLong(sessionInfo.getUserId()));
				break;
			}
			case TestCenterManage:{
				hql += " and t.status in (41,42,43)";
				break;
			}
			default:{
				hql += " and 1=2";
				break;
			}
        }
        String totalHql = "select count(*) " + hql;
        return ersaiTaskOrderDao.count(totalHql,params).intValue();
	
	}
	@Override
	@Transactional
	public void remove(String id) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        ErsaiTaskOrder taskOrder = ersaiTaskOrderDao.get(ErsaiTaskOrder.class, Long.valueOf(id));
		taskOrder.setStatus(0);
		ersaiTaskOrderDao.update(taskOrder);
		//操作日志
  		OperateLog opLog = new OperateLog();
  		opLog.setLshId(taskOrder.getLsh());
  		opLog.setContent(sessionInfo.getLoginName()+"删除了任务单"+id);
  		opLog.setMemberId(Long.valueOf(sessionInfo.getUserId()));
  		opLog.setCreatetime(new Date());
  		operatelogDao.save(opLog);
	}
	@Override
	public DataGrid logList(LogVO logVO) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
