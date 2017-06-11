package com.sdmx.yansTask.service.impl;

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
import com.sdmx.ersaitask.vo.ErsaiTaskOrderStatus;
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
import com.sdmx.yansTask.dao.IYansAttachmentDao;
import com.sdmx.yansTask.dao.IYansLshDao;
import com.sdmx.yansTask.dao.IYansTaskOrderDao;
import com.sdmx.yansTask.dao.IYansTaskPriceDao;
import com.sdmx.yansTask.entity.YansAttachment;
import com.sdmx.yansTask.entity.YansTaskOrder;
import com.sdmx.yansTask.entity.YansTaskPrice;
import com.sdmx.yansTask.entity.YansTaskSchedule;
import com.sdmx.yansTask.service.IYansAttachmentService;
import com.sdmx.yansTask.service.IYansTaskOrderService;
import com.sdmx.yansTask.vo.YansTaskOrderStatus;
import com.sdmx.yansTask.vo.YansTaskOrderVO;

@Service("yansTaskOrderService")
public class YansTaskOrderServiceImpl implements IYansTaskOrderService{
	@Autowired
	private IDictionaryDao dictionaryDao;
	@Autowired
	private IMemberDao memberDao;
	@Autowired
	private IYansLshDao yansLshDao;
	@Autowired
	private IYansTaskOrderDao yansTaskOrderDao;
	@Autowired
	private IOperateLogDao operatelogDao;
	@Autowired
	private IPriceItemDao priceItemDao;
	@Autowired
	private IYansTaskPriceDao yansTaskPriceDao;
	@Autowired
	private IBaseDao<YansTaskSchedule> baseDao;
	@Autowired
	private IYansAttachmentService yansAttachmentService;
	@Autowired
	private IYansAttachmentDao yansAttachmentDao;
	@Autowired
	private IExportService exportExcelService;
	@Override
	@Transactional
	public YansTaskOrder create(YansTaskOrderVO yansTaskOrdervo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        Member currentMember = memberDao.get(Member.class, Long.parseLong(sessionInfo.getUserId()));
        YansTaskOrder taskOrder = new YansTaskOrder();
		BeanUtilsEx.copyProperties(taskOrder, yansTaskOrdervo);
		//课题号
		Dictionary project = dictionaryDao.get(Dictionary.class, Long.parseLong(yansTaskOrdervo.getTopicId()));
		taskOrder.setTopic(project);
		//协助部门
		Dictionary helpDept = dictionaryDao.get(Dictionary.class, Long.parseLong(yansTaskOrdervo.getHelpDeptId()));
		taskOrder.setHelpDept(helpDept);
		
		//希望完成时间
		taskOrder.setWantedEndDate(yansTaskOrdervo.getWantedEndDate());
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
		taskOrder.setLsh(yansLshDao.getLsh());
		
		yansTaskOrderDao.save(taskOrder);
		//有附件
		if(yansTaskOrdervo.getAttachmentFlag() == 1){
			// 给附件指定所属信息项
			if (yansTaskOrdervo.getAttachids() != null) {
				for (Integer aid : yansTaskOrdervo.getAttachids()) {
					YansAttachment att = yansAttachmentDao.get(YansAttachment.class, aid);
					att.setYansTaskOrder(taskOrder);
					yansAttachmentDao.update(att);
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
	public DataGrid listTaskOrder(YansTaskOrderVO yansTaskOrderVO) {
		DataGrid dg = new DataGrid();
		String hql = "from YansTaskOrder t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(yansTaskOrderVO, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(yansTaskOrderVO, hql);
		List<YansTaskOrder> yansTaskOrderList = yansTaskOrderDao.find(hql, params, yansTaskOrderVO.getPage(), yansTaskOrderVO.getRows());
		List<YansTaskOrderVO> yansTaskOrderVOList = new ArrayList<YansTaskOrderVO>();
		changeModelList(yansTaskOrderList, yansTaskOrderVOList);
		dg.setTotal(yansTaskOrderDao.count(totalHql, params));
		dg.setRows(yansTaskOrderVOList);
		return dg;
	}
	private String addWhere(YansTaskOrderVO yansTaskOrderVO, String hql, Map<String, Object> params) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		hql += " where 1=1 and t.status !=0 ";
		
		//任务类型
		if (UtilValidate.isNotEmpty(yansTaskOrderVO.getTaskType())) {
			hql += " and t.taskType like :taskType";
			params.put("taskType", "%%" +yansTaskOrderVO.getTaskType().trim() + "%%");
		}
		//申请人
		if (UtilValidate.isNotEmpty(yansTaskOrderVO.getApplyMember())) {
			hql += " and t.applyMember like :applyMember";
			params.put("applyMember", "%%" +yansTaskOrderVO.getApplyMember().trim() + "%%");
		}
		//通知单号
		if (UtilValidate.isNotEmpty(yansTaskOrderVO.getReportNo())) {
			hql += " and t.reportNo.value like :reportNo";
			params.put("reportNo", "%%" +yansTaskOrderVO.getReportNo().trim() + "%%");
		}
		//课题号
		if (UtilValidate.isNotEmpty(yansTaskOrderVO.getTopicId())) {
			hql += " and t.topic.value like :topicNo";
			params.put("topicNo", "%%" +yansTaskOrderVO.getTopicId().trim() + "%%");
		}
		//任务单号
		if (UtilValidate.isNotEmpty(yansTaskOrderVO.getLsh())) {
			hql += " and t.lsh= :lshNo";
			params.put("lshNo", yansTaskOrderVO.getLsh().trim());
		}
		//开始登记时间
		if (yansTaskOrderVO.getRegistTimeStart() != null) {
			hql += " and t.createtime >= :regStartDate";
			params.put("regStartDate", yansTaskOrderVO.getRegistTimeStart());
		}
		//结束登记时间
		if (yansTaskOrderVO.getRegistTimeEnd() != null) {
			hql += " and t.createtime <= :regEndDate";
			params.put("regEndDate", yansTaskOrderVO.getRegistTimeEnd());
		}
		//根据角色确定内容
		RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
		switch(memType){
			case DepartManage:
			case DepartMember :{
				//待处理查询
				if(OperatorQueryType.SuspendingQuery.getValue().equals(yansTaskOrderVO.getClType())){
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
				if(OperatorQueryType.SuspendingQuery.getValue().equals(yansTaskOrderVO.getClType())){
					hql += " and (t.status ='31' " +
							" or (t.status ='23' and t.member.memberId = :getMemberId))";
					params.put("getMemberId", Long.parseLong(sessionInfo.getUserId()));
				}
				break;
			}
			case TestCenterManage:{
				//待处理查询
				if(OperatorQueryType.SuspendingQuery.getValue().equals(yansTaskOrderVO.getClType())){
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
	private String addOrder(YansTaskOrderVO yansTaskOrderVO, String hql){
		if (yansTaskOrderVO.getSort() != null) {
			if("statusName".equals(yansTaskOrderVO.getSort())){
				hql += " order by t.status "  + yansTaskOrderVO.getOrder();
			}else if("projectName".equals(yansTaskOrderVO.getSort())){
				hql += " order by t.project.value "  + yansTaskOrderVO.getOrder();
			}else if("topicNo".equals(yansTaskOrderVO.getSort())){
				hql += " order by t.topic.value "  + yansTaskOrderVO.getOrder();
			}else{
				hql += " order by " + yansTaskOrderVO.getSort() + " " + yansTaskOrderVO.getOrder();
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
	private void changeModelList(List<YansTaskOrder> l, List<YansTaskOrderVO> nl) {
		if (l != null && l.size() > 0) {
			for (Iterator<YansTaskOrder> iterator = l.iterator(); iterator.hasNext();) {
				YansTaskOrder taskOrder = (YansTaskOrder) iterator.next();
				YansTaskOrderVO taskOrderVO = getDtoData(taskOrder);
				nl.add(taskOrderVO);
			}
		}
	}
	private YansTaskOrderVO getDtoData(YansTaskOrder yansTaskOrder){
		YansTaskOrderVO yansTaskOrderVO = new YansTaskOrderVO();
		BeanUtilsEx.copyProperties(yansTaskOrderVO,yansTaskOrder);
		yansTaskOrderVO.setTopicId(String.valueOf(yansTaskOrder.getTopic().getDictionaryId()));
		yansTaskOrderVO.setTopicName(yansTaskOrder.getTopic().getAnnotation());
		
		
		yansTaskOrderVO.setOrderId(String.valueOf(yansTaskOrder.getOrderId()));
		yansTaskOrderVO.setHelpDeptId(String.valueOf(yansTaskOrder.getHelpDept().getDictionaryId()));
		yansTaskOrderVO.setHelpDeptName(yansTaskOrder.getHelpDept().getValue());
		//状态名称
		for (YansTaskOrderStatus type : YansTaskOrderStatus.values()) {
			if(yansTaskOrder.getStatus()== type.getValue()){
				yansTaskOrderVO.setStatusName(type.getLabel());
			}
		}
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        //如果是部门待确认状态，任务单创建者具有确认权限
        if(yansTaskOrder.getStatus() == YansTaskOrderStatus.WAITTOFIX_DEPARTMANAGE.getValue()){
        	if(String.valueOf(yansTaskOrder.getMember().getMemberId()).equals(sessionInfo.getUserId())){
        		yansTaskOrderVO.setFixState(1);
        	}else{
        		yansTaskOrderVO.setFixState(0);
        	}
        }
        //设置审核权限和核价，修改权限
        switch(memType){
        	case DepartManage:
        	case DepartMember :{
        		if(yansTaskOrder.getStatus() == YansTaskOrderStatus.NOTPASS_PRODUCTMANAGE.getValue()||
        				yansTaskOrder.getStatus() == YansTaskOrderStatus.NOTPASS_TESTCENTERMANAGE.getValue()){
        			yansTaskOrderVO.setEditState(1);
	        	}
	        	break;
        	}
      		//生产调度，封测有审核功能
			case ProductManage:{
				if(yansTaskOrder.getStatus() == YansTaskOrderStatus.WAITTOCONFIRM_PRODUCTMANAGE.getValue()){
					yansTaskOrderVO.setConfirmState(1);
				}
				yansTaskOrderVO.setEditState(1);
				yansTaskOrderVO.setDeleteState(1);
				break;
			}
			case TestCenterManage:{
				if(yansTaskOrder.getStatus() == TaskOrderStatus.WAITTOCONFIRM_TESTCENTERMANAGE.getValue()){
					yansTaskOrderVO.setConfirmState(1);
				}
				if(yansTaskOrder.getStatus() == TaskOrderStatus.WAITTOCHARGE_TESTCENTERMANAGE.getValue()){
					yansTaskOrderVO.setPriceState(1);
				}
				if(yansTaskOrder.getStatus() == TaskOrderStatus.WAITTOFIX_TESTCENTERMANAGE.getValue()){
					yansTaskOrderVO.setFixState(1);
					yansTaskOrderVO.setPriceEditState(1);
				}
				break;
			}
			default:{
				yansTaskOrderVO.setConfirmState(0);
				yansTaskOrderVO.setPriceState(0);
				break;
			}
		
        }
        //附件
        if(yansTaskOrder.getAttachmentFlag() == 1){
        	List<YansAttachment> attach = yansAttachmentService.listByCode(yansTaskOrder.getOrderId());
    		if (UtilValidate.isNotEmpty(attach)) {
    			List<AttachmentVO> attachvo = new ArrayList<AttachmentVO>();
    			for (YansAttachment attachment : attach) {
    				AttachmentVO avo = new AttachmentVO();
    				BeanUtilsEx.copyProperties(avo, attachment);
    				attachvo.add(avo);
    			}
    			yansTaskOrderVO.setAttachment(attachvo);// 设置附件信息
    		}
        }
		return yansTaskOrderVO;
	}
	@Override
	public DataGrid listSusTaskOrder(YansTaskOrderVO yansTaskOrderVO) {
		DataGrid dg = new DataGrid();
		String hql = "from YansTaskOrder t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(yansTaskOrderVO, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(yansTaskOrderVO, hql);
		List<YansTaskOrder> l = yansTaskOrderDao.find(hql, params, yansTaskOrderVO.getPage(), yansTaskOrderVO.getRows());
		List<YansTaskOrderVO> nl = new ArrayList<YansTaskOrderVO>();
		changeModelList(l, nl);
		dg.setTotal(yansTaskOrderDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	@Override
	public YansTaskOrderVO getTaskOrderById(String taskId) {
		YansTaskOrder taskOrder = yansTaskOrderDao.get(YansTaskOrder.class, Long.parseLong(taskId));
		YansTaskOrderVO taskOrderVO = getDtoData(taskOrder);
		return taskOrderVO;
	}
	@Override
	public List<TaskPriceVO> getTaskPriceListById(String taskId) {
		YansTaskOrder taskOrder = yansTaskOrderDao.get(YansTaskOrder.class, Long.parseLong(taskId));
		Set<YansTaskPrice> tpList = taskOrder.getTaskPriceList();
		List<TaskPriceVO> taskPriceList = new ArrayList<TaskPriceVO>();
		for (YansTaskPrice tp : tpList) {
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
		YansTaskOrder taskOrder = yansTaskOrderDao.get(YansTaskOrder.class, Long.parseLong(taskId));
		TaskScheduleVO taskScheduleVO = new TaskScheduleVO();
		YansTaskSchedule taskSchedule = taskOrder.getYansTaskSchedule();
		BeanUtils.copyProperties(taskSchedule, taskScheduleVO);
		return taskScheduleVO;
	}
	@Override
	@Transactional
	public YansTaskOrder confirmOK(YansTaskOrderVO yansTaskOrderVO) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        YansTaskOrder taskOrder = yansTaskOrderDao.get(YansTaskOrder.class, Long.parseLong(yansTaskOrderVO.getOrderId()));
		//任务类型
        taskOrder.setTaskType(yansTaskOrderVO.getTaskType());
		//通知单号
		taskOrder.setReportNo(yansTaskOrderVO.getReportNo());
		//所内型号
		taskOrder.setInternalModel(yansTaskOrderVO.getInternalModel());
		//申请部门
		taskOrder.setApplyDept(yansTaskOrderVO.getApplyDept());
		//申 请 人
		taskOrder.setApplyMember(yansTaskOrderVO.getApplyMember());
		//申请人电话
		taskOrder.setApplyMemberPhone(yansTaskOrderVO.getApplyMemberPhone());
		//课 题 号
		Dictionary topic = dictionaryDao.get(Dictionary.class, Long.parseLong(yansTaskOrderVO.getTopicId()));
		taskOrder.setTopic(topic);
		//项目负责人
		taskOrder.setProjectManager(yansTaskOrderVO.getProjectManager());
		//项目负责人电话
		taskOrder.setProjectManagerPhone(yansTaskOrderVO.getProjectManagerPhone());
		//请求协助部门
		Dictionary helpDept = dictionaryDao.get(Dictionary.class, Long.parseLong(yansTaskOrderVO.getHelpDeptId()));
		taskOrder.setHelpDept(helpDept);
		//希望完成时间
		taskOrder.setWantedEndDate(yansTaskOrderVO.getWantedEndDate());
		//是否有附件
		taskOrder.setAttachmentFlag(yansTaskOrderVO.getAttachmentFlag());
		//申请原因及说明
		taskOrder.setApplyReason(yansTaskOrderVO.getApplyReason());
		//具体要求（验收条件）
		taskOrder.setDetailRequire(yansTaskOrderVO.getDetailRequire());
		taskOrder.setRemarks(yansTaskOrderVO.getRemarks());
		taskOrder.setProductManagesuggest(yansTaskOrderVO.getProductManagesuggest());
		//是否用二筛
		taskOrder.setErsaiFlag(yansTaskOrderVO.getErsaiFlag());
		taskOrder.setErsaiLsh(yansTaskOrderVO.getErsaiLsh());
		taskOrder.setYansNum(yansTaskOrderVO.getYansNum());
		switch(memType){
			case DepartManage:
			case DepartMember:{
				taskOrder.setStatus(YansTaskOrderStatus.WAITTOCONFIRM_PRODUCTMANAGE.getValue());
				break;
			}
      		//只有生产调度，封测有审核功能
			case ProductManage:{
				if(taskOrder.getStatus() != YansTaskOrderStatus.COMPLETED.getValue()){
					taskOrder.setStatus(YansTaskOrderStatus.WAITTOCONFIRM_TESTCENTERMANAGE.getValue());
				}
				break;
			}
			case TestCenterManage:{
				taskOrder.setStatus(YansTaskOrderStatus.WAITTOCHARGE_TESTCENTERMANAGE.getValue());
				break;
			}
			default:{
				break;
			}
      	}
		//有附件
		if(yansTaskOrderVO.getAttachmentFlag() == 1){
			// 给附件指定所属信息项
			if (yansTaskOrderVO.getAttachids() != null) {
				for (Integer aid : yansTaskOrderVO.getAttachids()) {
					YansAttachment att = yansAttachmentDao.get(YansAttachment.class, aid);
					att.setYansTaskOrder(taskOrder);
					yansAttachmentDao.update(att);
				}
			}
		}
		yansTaskOrderDao.saveOrUpdate(taskOrder);
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
	public YansTaskOrder confirmNG(YansTaskOrderVO yansTaskOrderVO) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        YansTaskOrder taskOrder = yansTaskOrderDao.get(YansTaskOrder.class, Long.parseLong(yansTaskOrderVO.getOrderId()));
		//不通过原因
		taskOrder.setProductManagesuggest(yansTaskOrderVO.getProductManagesuggest());
		switch(memType){
			//只有生产调度，封测有审核功能
			case ProductManage:{
				taskOrder.setStatus(YansTaskOrderStatus.NOTPASS_PRODUCTMANAGE.getValue());
				break;
			}
			case TestCenterManage:{
				taskOrder.setStatus(YansTaskOrderStatus.NOTPASS_TESTCENTERMANAGE.getValue());
				break;
			}
			default:{
				break;
			}
      	}
		yansTaskOrderDao.saveOrUpdate(taskOrder);
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
	public YansTaskOrder fixComplete(YansTaskOrderVO yansTaskOrderVO) {
		YansTaskOrder taskOrder = yansTaskOrderDao.get(YansTaskOrder.class, Long.parseLong(yansTaskOrderVO.getOrderId()));
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        switch(memType){
			case TestCenterManage:{
				taskOrder.setStatus(YansTaskOrderStatus.WAITTOFIX_DEPARTMANAGE.getValue());
				break;
			}
			default:{
				taskOrder.setStatus(YansTaskOrderStatus.COMPLETED.getValue());
				break;
			}
      	}
        yansTaskOrderDao.update(taskOrder);
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
	public HttpServletResponse downloadExcel(YansTaskOrderVO yansTaskOrderVO,
			HttpServletResponse response) throws Exception {
		ExcelDateInfo excelDateInfo = new ExcelDateInfo();
		String hql = "from YansTaskOrder t";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(yansTaskOrderVO, hql, params);
		hql = addOrder(yansTaskOrderVO, hql);
		List<YansTaskOrder> l = yansTaskOrderDao.find(hql, params, yansTaskOrderVO.getPage(), yansTaskOrderVO.getRows());
		
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
		excelDateInfo.setFileName("yanstaskList_"+date);
		excelDateInfo.setSheetName("验收任务单数据");
		excelDateInfo.setTitle("验收任务单数据导出数据如下");
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
		columnTitle.add("验收条件");
		columnTitle.add("验收数量");
		columnTitle.add("价格");
		columnTitle.add("任务单状态");

		return columnTitle;
	}
	
	/**
	 * 导出数据
	 * @param consults
	 * @return
	 */
	private List<List<String>> exportChangeModel(List<YansTaskOrder> taskOrder){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<List<String>> rows = new ArrayList<List<String>>();
		List<String> columns = null;
		for (Iterator<YansTaskOrder> iterator = taskOrder.iterator(); iterator.hasNext();) {
			YansTaskOrder task = (YansTaskOrder) iterator.next();
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
			columns.add(task.getDetailRequire());//验收条件
			columns.add(String.valueOf(task.getYansNum()));//验收数量
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
	public YansTaskOrder checkPrice(PriceCheckVO priceCheckVO) {
		List<TaskPriceVO> listPrice = new ArrayList<TaskPriceVO>();
		YansTaskOrder taskOrder = yansTaskOrderDao.get(YansTaskOrder.class, Long.parseLong(priceCheckVO.getOrderId()));
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
					YansTaskPrice tp  =  new YansTaskPrice();
					PriceItem pi = priceItemDao.get(PriceItem.class, Long.parseLong(taskPriceVO.getItemId()));
					tp.setItemNum(taskPriceVO.getAmount());
					tp.setItemSum(taskPriceVO.getUnitcost());
					tp.setPriceItem(pi);
					tp.setRemarks(taskPriceVO.getRemarks());
					tp.setYansTaskOrder(taskOrder);
					taskOrder.addPriceItem(tp);
				}
			}
		}else{
			//修改核价
			Set<YansTaskPrice> taskPriceListOld = taskOrder.getTaskPriceList();
			for (TaskPriceVO taskPriceVO : listPrice) {
				String itemID = taskPriceVO.getItemId();
				boolean isExist = false;
				for (YansTaskPrice yansTaskPrice : taskPriceListOld) {
					if(yansTaskPrice.getPriceItem().getItemId().toString().equals(itemID)){
						isExist = true;
						yansTaskPrice.setItemNum(taskPriceVO.getAmount());
						yansTaskPrice.setItemSum(taskPriceVO.getUnitcost());
						yansTaskPrice.setRemarks(taskPriceVO.getRemarks());
						if(taskPriceVO.getUnitcost()==0.0){
							deleteList.add(yansTaskPrice.getTaskPriceId());
						}
					}
				}
				if(!isExist){
					if(UtilValidate.isNotEmpty(taskPriceVO.getUnitcost()) && taskPriceVO.getUnitcost()!=0.0 ){
						YansTaskPrice tp  =  new YansTaskPrice();
						PriceItem pi = priceItemDao.get(PriceItem.class, Long.parseLong(taskPriceVO.getItemId()));
						tp.setItemNum(taskPriceVO.getAmount());
						tp.setItemSum(taskPriceVO.getUnitcost());
						tp.setPriceItem(pi);
						tp.setRemarks(taskPriceVO.getRemarks());
						tp.setYansTaskOrder(taskOrder);
						taskOrder.addPriceItem(tp);
					}
				}
			}
			//
			for (Long deleteId : deleteList) {
				YansTaskPrice  taskPrice =  yansTaskPriceDao.get(YansTaskPrice.class, deleteId);
				taskOrder.removePriceItem(taskPrice);
			}
		}
		Set<YansTaskPrice> taskPriceListAfter = taskOrder.getTaskPriceList();
		Double sumPrice = 0.0;
		for (YansTaskPrice taskPrice : taskPriceListAfter) {
			sumPrice = Arith.add(sumPrice, taskPrice.getItemSum());
		}
		taskOrder.setSumPrice(sumPrice);
		//初始核价
		if(UtilValidate.isEmpty(taskOrder.getYansTaskSchedule())){
			YansTaskSchedule ts = new YansTaskSchedule();
			BeanUtils.copyProperties(priceCheckVO, ts);
			ts.setPakTime(DateUtil.getDate(ts.getPakstartDate(),ts.getPakendDate()));
//			ts.setSvTime(DateUtil.getDate(ts.getSvstartDate(),ts.getSvendDate()));
//			ts.setTestTime(DateUtil.getDate(ts.getTeststartDate(),ts.getTestendDate()));
//			ts.setSxTime(DateUtil.getDate(ts.getSxstartDate(),ts.getSxendDate()));
//			ts.setJdTime(DateUtil.getDate(ts.getJdstartDate(),ts.getJdendDate()));
			baseDao.save(ts);
			taskOrder.setYansTaskSchedule(ts);
		}else{
			//修改核价
			YansTaskSchedule ts = taskOrder.getYansTaskSchedule();
			BeanUtils.copyProperties(priceCheckVO, ts);
			ts.setPakTime(DateUtil.getDate(ts.getPakstartDate(),ts.getPakendDate()));
//			ts.setSvTime(DateUtil.getDate(ts.getSvstartDate(),ts.getSvendDate()));
//			ts.setTestTime(DateUtil.getDate(ts.getTeststartDate(),ts.getTestendDate()));
//			ts.setSxTime(DateUtil.getDate(ts.getSxstartDate(),ts.getSxendDate()));
//			ts.setJdTime(DateUtil.getDate(ts.getJdstartDate(),ts.getJdendDate()));
			taskOrder.setYansTaskSchedule(ts);
		}
		taskOrder.setStatus(YansTaskOrderStatus.WAITTOFIX_TESTCENTERMANAGE.getValue());
		yansTaskOrderDao.update(taskOrder);
		for (Long deleteId : deleteList) {
			YansTaskPrice  taskPrice =  yansTaskPriceDao.get(YansTaskPrice.class, deleteId);
			yansTaskPriceDao.delete(taskPrice);
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
        String hql = "from YansTaskOrder t where 1=1 and t.status !=0  ";
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
        return yansTaskOrderDao.count(totalHql,params).intValue();
	}
	@Override
	@Transactional
	public void remove(String id) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        YansTaskOrder taskOrder = yansTaskOrderDao.get(YansTaskOrder.class, Long.valueOf(id));
		taskOrder.setStatus(0);
		yansTaskOrderDao.update(taskOrder);
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
