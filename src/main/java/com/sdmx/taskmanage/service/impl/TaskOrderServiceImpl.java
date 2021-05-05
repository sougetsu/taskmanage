package com.sdmx.taskmanage.service.impl;

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
import com.sdmx.framework.util.StringUtils;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.RoleType;
import com.sdmx.framework.vo.SessionInfo;
import com.sdmx.taskmanage.dao.IAttachmentDao;
import com.sdmx.taskmanage.dao.ILshDao;
import com.sdmx.taskmanage.dao.IOperateLogDao;
import com.sdmx.taskmanage.dao.IPriceItemDao;
import com.sdmx.taskmanage.dao.ITaskDicingDao;
import com.sdmx.taskmanage.dao.ITaskMixPackageDao;
import com.sdmx.taskmanage.dao.ITaskMultiChipPackageDao;
import com.sdmx.taskmanage.dao.ITaskOrderDao;
import com.sdmx.taskmanage.dao.ITaskPackageDao;
import com.sdmx.taskmanage.dao.ITaskPriceDao;
import com.sdmx.taskmanage.dao.ITaskReductionDao;
import com.sdmx.taskmanage.entity.Attachment;
import com.sdmx.taskmanage.entity.OperateLog;
import com.sdmx.taskmanage.entity.PriceItem;
import com.sdmx.taskmanage.entity.TaskDicing;
import com.sdmx.taskmanage.entity.TaskMixPackage;
import com.sdmx.taskmanage.entity.TaskMultiChipPackage;
import com.sdmx.taskmanage.entity.TaskOrder;
import com.sdmx.taskmanage.entity.TaskPackage;
import com.sdmx.taskmanage.entity.TaskPrice;
import com.sdmx.taskmanage.entity.TaskReduction;
import com.sdmx.taskmanage.entity.TaskSchedule;
import com.sdmx.taskmanage.service.IAttachmentService;
import com.sdmx.taskmanage.service.ITaskOrderService;
import com.sdmx.taskmanage.vo.AttachmentVO;
import com.sdmx.taskmanage.vo.DateUtil;
import com.sdmx.taskmanage.vo.LogVO;
import com.sdmx.taskmanage.vo.OperatorQueryType;
import com.sdmx.taskmanage.vo.PriceCheckVO;
import com.sdmx.taskmanage.vo.TaskOrderStatus;
import com.sdmx.taskmanage.vo.TaskOrderVO;
import com.sdmx.taskmanage.vo.TaskPriceVO;
import com.sdmx.taskmanage.vo.TaskScheduleVO;

@Service("taskOrderService")
public class TaskOrderServiceImpl implements ITaskOrderService{
	@Autowired
	private IDictionaryDao dictionaryDao;
	@Autowired
	private ITaskOrderDao taskOrderDao;
	@Autowired
	private IExportService exportExcelService;
	@Autowired
	private IAttachmentService attachmentService;
	@Autowired
	private IMemberDao memberDao;
	@Autowired
	private IPriceItemDao priceItemDao;
	@Autowired
	private IBaseDao<TaskSchedule> baseDao;
	@Autowired
	private IAttachmentDao attachmentDao;
	@Autowired
	private ITaskDicingDao taskDicingDao;
	@Autowired
	private ITaskPackageDao taskPackageDao;
	@Autowired
	private ITaskMixPackageDao taskMixPackageDao;
	@Autowired
	private ITaskMultiChipPackageDao taskMultiChipPackageDao;
	@Autowired
	private ITaskReductionDao taskReductionDao;
	@Autowired
	private ITaskPriceDao taskPriceDao;
	@Autowired
	private IOperateLogDao operatelogDao;
	@Autowired
	private ILshDao lshDao;
	@Override
	public DataGrid listTaskOrder(TaskOrderVO taskOrdervo) {
		DataGrid dg = new DataGrid();
		String hql = "from TaskOrder t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(taskOrdervo, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(taskOrdervo, hql);
		List<TaskOrder> l = taskOrderDao.find(hql, params, taskOrdervo.getPage(), taskOrdervo.getRows());
		List<TaskOrderVO> nl = new ArrayList<TaskOrderVO>();
		changeModelList(l, nl);
		dg.setTotal(taskOrderDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	private String addWhere(TaskOrderVO taskOrdervo, String hql, Map<String, Object> params) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        Member currentMember = memberDao.get(Member.class, Long.parseLong(sessionInfo.getUserId()));
		hql += " where 1=1 and t.status !=0 and (t.taskOrderType=0 or t.taskOrderType is null) ";
		//项目名称
		if (UtilValidate.isNotEmpty(taskOrdervo.getProjectName())) {
			hql += " and t.project.annotation like :projectName";
			params.put("projectName", "%%" +taskOrdervo.getProjectName().trim() + "%%");
		}
		//申请人
		if (UtilValidate.isNotEmpty(taskOrdervo.getApplyMember())) {
			hql += " and t.applyMember like :applyMember";
			params.put("applyMember", "%%" +taskOrdervo.getApplyMember().trim() + "%%");
		}
		//所内型号
		if (UtilValidate.isNotEmpty(taskOrdervo.getInternalModel())) {
			hql += " and t.internalModel like :internalModel";
			params.put("internalModel", "%%" +taskOrdervo.getInternalModel().trim() + "%%");
		}
		//任务类型
		if (UtilValidate.isNotEmpty(taskOrdervo.getOrderTypeId())) {
			hql += " and t.orderType.dictionaryId = :orderTypeId";
			params.put("orderTypeId", Long.valueOf(taskOrdervo.getOrderTypeId()));
		}
		//课题号
		if (UtilValidate.isNotEmpty(taskOrdervo.getTopicNo())) {
			hql += " and t.topic.value like :topicNo";
			params.put("topicNo", "%%" +taskOrdervo.getTopicNo().trim() + "%%");
		}
		//任务单号
		if (UtilValidate.isNotEmpty(taskOrdervo.getLsh())) {
			hql += " and t.lsh= :lshNo";
			params.put("lshNo", taskOrdervo.getLsh().trim());
		}
		//开始登记时间
		if (taskOrdervo.getRegistTimeStart() != null) {
			hql += " and t.createtime >= :regStartDate";
			params.put("regStartDate", taskOrdervo.getRegistTimeStart());
		}
		//结束登记时间
		if (taskOrdervo.getRegistTimeEnd() != null) {
			hql += " and t.createtime <= :regEndDate";
			params.put("regEndDate", taskOrdervo.getRegistTimeEnd());
		}
		//根据角色确定内容
		RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
		switch(memType){
			case MarketManger:
			case DepartMember :{
				//待处理查询
				if(OperatorQueryType.SuspendingQuery.getValue().equals(taskOrdervo.getClType())){
					hql += " and t.status in (11,12,13)";
		        	hql += " and t.member.memberId = :getMemberId ";
					params.put("getMemberId", Long.parseLong(sessionInfo.getUserId()));
				}else{
					hql += " and t.member.memberId = :getMemberId ";
					params.put("getMemberId", Long.parseLong(sessionInfo.getUserId()));
				}
				break;
			}
			case DepartManage :{
				//待处理查询
				if(OperatorQueryType.SuspendingQuery.getValue().equals(taskOrdervo.getClType())){
					hql += " and t.status in (21,22,23)";
					hql += " and t.member.organization.dictionaryId = :getDictionaryId ";
					params.put("getDictionaryId", currentMember.getOrganization().getDictionaryId());
				}else{
					hql += " and t.member.organization.dictionaryId = :getDictionaryId ";
					params.put("getDictionaryId", currentMember.getOrganization().getDictionaryId());
				}
				break;
			}
			case ProductManage:{
				//待处理查询
				if(OperatorQueryType.SuspendingQuery.getValue().equals(taskOrdervo.getClType())){
					hql += " and (t.status ='31' " +
							" or (t.status ='23' and t.member.memberId = :getMemberId))";
					params.put("getMemberId", Long.parseLong(sessionInfo.getUserId()));
				}
				break;
			}
			case TestCenterManage:{
				//待处理查询
				if(OperatorQueryType.SuspendingQuery.getValue().equals(taskOrdervo.getClType())){
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
	private String addOrder(TaskOrderVO taskOrdervo, String hql){
		if (taskOrdervo.getSort() != null) {
			if("statusName".equals(taskOrdervo.getSort())){
				hql += " order by t.status "  + taskOrdervo.getOrder();
			}else if("urgencyName".equals(taskOrdervo.getSort())){
				hql += " order by t.urgency "  + taskOrdervo.getOrder();
			}else if("projectName".equals(taskOrdervo.getSort())){
				hql += " order by t.project.value "  + taskOrdervo.getOrder();
			}else if("topicNo".equals(taskOrdervo.getSort())){
				hql += " order by t.topic.value "  + taskOrdervo.getOrder();
			}else if("orderTypeName".equals(taskOrdervo.getSort())){
				hql += " order by t.orderType.value "  + taskOrdervo.getOrder();
			}else{
				hql += " order by " + taskOrdervo.getSort() + " " + taskOrdervo.getOrder();
			}
		}else{
			hql += " order by t.createtime desc";
		}
		return hql;
	}
	@Override
	@Transactional
	public TaskOrder create(TaskOrderVO taskOrdervo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        Member currentMember = memberDao.get(Member.class, Long.parseLong(sessionInfo.getUserId()));
		TaskOrder taskOrder = new TaskOrder();
		BeanUtilsEx.copyProperties(taskOrder, taskOrdervo);
		taskOrder.setTaskOrderType(0);
		//项目名称、课题号
		if(UtilValidate.isNotEmpty(taskOrdervo.getProjectId())) {
			Dictionary project = dictionaryDao.get(Dictionary.class, Long.parseLong(taskOrdervo.getProjectId()));
			taskOrder.setProject(project);
			taskOrder.setTopic(project);
		}
		//电路名称
		if(UtilValidate.isNotEmpty(taskOrdervo.getElectricId())) {
			Dictionary electric = dictionaryDao.get(Dictionary.class, Long.parseLong(taskOrdervo.getElectricId()));
			taskOrder.setElectric(electric);
		}
		//成本归集课题号
		//Dictionary costTopicNo = dictionaryDao.get(Dictionary.class, Long.parseLong(taskOrdervo.getCostTopicNoId()));
		//taskOrder.setCostTopicNo(costTopicNo);
		//协助部门
		Dictionary helpDept = dictionaryDao.get(Dictionary.class, Long.parseLong(taskOrdervo.getHelpDeptId()));
		taskOrder.setHelpDept(helpDept);
		
		//任务类型
		if(UtilValidate.isNotEmpty(taskOrdervo.getOrderTypeId())) {
			Dictionary orderType = dictionaryDao.get(Dictionary.class, Long.parseLong(taskOrdervo.getOrderTypeId()));
			taskOrder.setOrderType(orderType);
		}
		taskOrder.setProductStatus(taskOrdervo.getProductStatus());		
		//业务申请内容
		List<Dictionary> applyContent = new  ArrayList<Dictionary>();
		if (UtilValidate.isNotEmpty(taskOrdervo.getApplyContentIds())) {
			for (String contentId : taskOrdervo.getApplyContentIds().split(",")) {
				Dictionary d = dictionaryDao.get(Dictionary.class, Long.parseLong(contentId));
				applyContent.add(d);
			}
			String[] contentIds = taskOrdervo.getApplyContentIds().split(",");
			//减薄
			if(StringUtils.isHave(contentIds, "8")){
				TaskReduction taskReduction = new TaskReduction();
				taskReduction.setReductionNo(taskOrdervo.getReductionNo());
				taskReduction.setReductionTabletsNo(taskOrdervo.getReductionTabletsNo());
				taskReduction.setReductionThickness(taskOrdervo.getReductionThickness());
				taskReductionDao.save(taskReduction);
				taskOrder.setTaskReduction(taskReduction);
			}
			//划片
			if(StringUtils.isHave(contentIds, "9")){
				TaskDicing taskDicing = new TaskDicing();
				taskDicing.setDicingNo(taskOrdervo.getDicingNo());
				taskDicing.setDicingTabletsNo(taskOrdervo.getDicingTabletsNo());
				taskDicing.setDicingTubeNum(taskOrdervo.getDicingTubeNum());
				taskDicing.setDicingPlan(taskOrdervo.getDicingPlan());
				taskDicingDao.save(taskDicing);
				taskOrder.setTaskDicing(taskDicing);
			}
			//封装
			if(StringUtils.isHave(contentIds, "11")){
				TaskPackage taskPackage = new TaskPackage();
				List<Dictionary> packageStatus = new  ArrayList<Dictionary>();
				if (UtilValidate.isNotEmpty(taskOrdervo.getPackageStatusIds())) {
					for (String packageStatusId : taskOrdervo.getPackageStatusIds().split(",")) {
						Dictionary d = dictionaryDao.get(Dictionary.class, Long.parseLong(packageStatusId));
						packageStatus.add(d);
					}
				}
				taskPackage.setPackageStatus(packageStatus);
				taskPackage.setBondNum(taskOrdervo.getBondNum());
				taskPackage.setChipLabel(taskOrdervo.getChipLabel());
				taskPackage.setDiscBatch(taskOrdervo.getDiscBatch());
				taskPackage.setMarkDemand(taskOrdervo.getMarkDemand());
				taskPackage.setPackageNum(taskOrdervo.getPackageNum());
				taskPackage.setPackageShape(taskOrdervo.getPackageShape());
				taskPackage.setQualityLevel(taskOrdervo.getQualityLevel());
				taskPackage.setShellType(taskOrdervo.getShellType());
				taskPackage.setDiscNum(taskOrdervo.getDiscNum());
				taskPackage.setWaferFlag(taskOrdervo.getWaferFlag());
				taskPackage.setStockName(taskOrdervo.getStockName());
				taskPackageDao.save(taskPackage);
				taskOrder.setTaskPackage(taskPackage);
			}
			//混合封装
			if(StringUtils.isHave(contentIds, "3022")){
				TaskMixPackage taskPackage = new TaskMixPackage();
				List<Dictionary> packageStatus = new  ArrayList<Dictionary>();
				if (UtilValidate.isNotEmpty(taskOrdervo.getMpackageStatusIds())) {
					for (String packageStatusId : taskOrdervo.getMpackageStatusIds().split(",")) {
						Dictionary d = dictionaryDao.get(Dictionary.class, Long.parseLong(packageStatusId));
						packageStatus.add(d);
					}
				}
				taskPackage.setMpackageStatus(packageStatus);
				taskPackage.setMbondNum(taskOrdervo.getMbondNum());
				taskPackage.setMchipLabel(taskOrdervo.getMchipLabel());
				taskPackage.setMdiscBatch(taskOrdervo.getMdiscBatch());
				taskPackage.setMmarkDemand(taskOrdervo.getMmarkDemand());
				taskPackage.setMpackageNum(taskOrdervo.getMpackageNum());
				taskPackage.setMpackageShape(taskOrdervo.getMpackageShape());
				taskPackage.setMqualityLevel(taskOrdervo.getMqualityLevel());
				taskPackage.setMshellType(taskOrdervo.getMshellType());
				taskPackage.setMdiscNum(taskOrdervo.getMdiscNum());
				taskPackage.setMwaferFlag(taskOrdervo.getMwaferFlag());
				taskPackage.setMchipNum(taskOrdervo.getMchipNum());
				taskPackage.setMstockFlag(taskOrdervo.getMstockFlag());
				taskPackage.setMstockName(taskOrdervo.getMstockName());
				taskMixPackageDao.save(taskPackage);
				taskOrder.setTaskMixPackage(taskPackage);
			}
			//多芯片封装
			if(StringUtils.isHave(contentIds, "3023")){
				TaskMultiChipPackage taskPackage = new TaskMultiChipPackage();
				List<Dictionary> packageStatus = new  ArrayList<Dictionary>();
				if (UtilValidate.isNotEmpty(taskOrdervo.getMcpackageStatusIds())) {
					for (String packageStatusId : taskOrdervo.getMcpackageStatusIds().split(",")) {
						Dictionary d = dictionaryDao.get(Dictionary.class, Long.parseLong(packageStatusId));
						packageStatus.add(d);
					}
				}
				taskPackage.setMcpackageStatus(packageStatus);
				taskPackage.setMcbondNum(taskOrdervo.getMcbondNum());
				taskPackage.setMcchipLabel(taskOrdervo.getMcchipLabel());
				taskPackage.setMcdiscBatch(taskOrdervo.getMcdiscBatch());
				taskPackage.setMcmarkDemand(taskOrdervo.getMcmarkDemand());
				taskPackage.setMcpackageNum(taskOrdervo.getMcpackageNum());
				taskPackage.setMcpackageShape(taskOrdervo.getMcpackageShape());
				taskPackage.setMcqualityLevel(taskOrdervo.getMcqualityLevel());
				taskPackage.setMcshellType(taskOrdervo.getMcshellType());
				taskPackage.setMcdiscNum(taskOrdervo.getMcdiscNum());
				taskPackage.setMcwaferFlag(taskOrdervo.getMcwaferFlag());
				taskPackage.setMcchipNum(taskOrdervo.getMcchipNum());
				taskPackage.setMcstockFlag(taskOrdervo.getMcstockFlag());
				taskPackage.setMcstockName(taskOrdervo.getMcstockName());
				taskMultiChipPackageDao.save(taskPackage);
				taskOrder.setTaskMultiChipPackage(taskPackage);
			}
		}
		taskOrder.setApplyContent(applyContent);
		//委托数量
		taskOrder.setEntrustNum(taskOrdervo.getEntrustNum());
		//鉴定方式
		List<Dictionary> checkType = new  ArrayList<Dictionary>();
		if (UtilValidate.isNotEmpty(taskOrdervo.getCheckTypeId())) {
			for (String checkTypeId : taskOrdervo.getCheckTypeId().split(",")) {
				Dictionary d = dictionaryDao.get(Dictionary.class, Long.parseLong(checkTypeId));
				checkType.add(d);
			}
		}
		taskOrder.setCheckType(checkType);
		//希望完成时间
		taskOrder.setWantedEndDate(taskOrdervo.getWantedEndDate());
		taskOrder.setCreatetime(new Date());
		switch(memType){
      	//只有部门员工，部门调度，生产调度有创建功能
			case DepartMember :{
				taskOrder.setStatus(TaskOrderStatus.WAITTOCONFIRM_DEPARTMANAGE.getValue());
				break;
			}
			case DepartManage :{
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
		taskOrder.setLsh(lshDao.getLsh());
		
		taskOrderDao.save(taskOrder);
		//有附件
		if(taskOrdervo.getAttachmentFlag() == 1){
			// 给附件指定所属信息项
			if (taskOrdervo.getAttachids() != null) {
				for (Integer aid : taskOrdervo.getAttachids()) {
					Attachment att = attachmentDao.get(Attachment.class, aid);
					att.setTaskOrder(taskOrder);
					attachmentDao.update(att);
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
	/**
	 * 根据实体转化页面展示项(集合)
	 * @param l
	 * @param nl
	 */
	private void changeModelList(List<TaskOrder> l, List<TaskOrderVO> nl) {
		if (l != null && l.size() > 0) {
			for (Iterator<TaskOrder> iterator = l.iterator(); iterator.hasNext();) {
				TaskOrder taskOrder = (TaskOrder) iterator.next();
				TaskOrderVO taskOrderVO = getDtoData(taskOrder);
				nl.add(taskOrderVO);
			}
		}
	}
	
	private TaskOrderVO getDtoData(TaskOrder taskOrder){
		TaskOrderVO taskOrderVO = new TaskOrderVO();
		BeanUtilsEx.copyProperties(taskOrderVO,taskOrder);
		if(taskOrder.getProject()!=null) {
			taskOrderVO.setProjectId(String.valueOf(taskOrder.getProject().getDictionaryId()));
			taskOrderVO.setProjectName(taskOrder.getProject().getAnnotation());
			taskOrderVO.setTopicNoId(String.valueOf(taskOrder.getProject().getDictionaryId()));
			taskOrderVO.setTopicNo(taskOrder.getProject().getValue());
		}
		if(taskOrder.getElectric()!=null) {
			taskOrderVO.setElectricId(String.valueOf(taskOrder.getElectric().getDictionaryId()));
			taskOrderVO.setElectricName(taskOrder.getElectric().getValue());
		}
		//taskOrderVO.setCostTopicNoId(String.valueOf(taskOrder.getCostTopicNo().getDictionaryId()));
		//taskOrderVO.setCostTopicNoName(taskOrder.getCostTopicNo().getAnnotation());
		//业务申请内容
		List<Dictionary> applyContent = taskOrder.getApplyContent();
		String applyContentIds = "";
		String applyContentNames = "";
		for(Dictionary dic: applyContent){
			if (applyContentIds.length() > 0) {
				applyContentIds += ",";
				applyContentNames +=",";
			}
			applyContentIds += dic.getDictionaryId();
			applyContentNames += dic.getValue();
		}
		taskOrderVO.setApplyContentIds(applyContentIds);
		taskOrderVO.setApplyContentNames(applyContentNames);
		if(applyContent.size()>0){
			String[] contentIds = applyContentIds.split(",");
			//减薄
			if(StringUtils.isHave(contentIds, "8")){
				taskOrderVO.setReductionFlag(1);
				TaskReduction taskReduction = taskOrder.getTaskReduction();
				taskOrderVO.setReductionNo(taskReduction.getReductionNo());
				taskOrderVO.setReductionTabletsNo(taskReduction.getReductionTabletsNo());
				taskOrderVO.setReductionThickness(taskReduction.getReductionThickness());
			}
			//划片
			if(StringUtils.isHave(contentIds, "9")){
				taskOrderVO.setDicingFlag(1);
				TaskDicing taskDicing = taskOrder.getTaskDicing();
				taskOrderVO.setDicingNo(taskDicing.getDicingNo());
				taskOrderVO.setDicingTabletsNo(taskDicing.getDicingTabletsNo());
				taskOrderVO.setDicingTubeNum(taskDicing.getDicingTubeNum());
				taskOrderVO.setDicingPlan(taskDicing.getDicingPlan());
			}
			//封装
			if(StringUtils.isHave(contentIds, "11")){
				taskOrderVO.setPackageFlag(1);
				TaskPackage taskPackage = taskOrder.getTaskPackage();
				if(UtilValidate.isNotEmpty(taskPackage)) {
					List<Dictionary> packageStatus = taskPackage.getPackageStatus();
					String packageStatusIds = "";
					String packageStatusNames = "";
					for(Dictionary dic: packageStatus){
						if (packageStatusIds.length() > 0) {
							packageStatusIds += ",";
							packageStatusNames +=",";
						}
						packageStatusIds += dic.getDictionaryId();
						packageStatusNames += dic.getValue();
					}
					taskOrderVO.setPackageStatusIds(packageStatusIds);
					taskOrderVO.setPackageStatusNames(packageStatusNames);
					taskOrderVO.setBondNum(taskPackage.getBondNum());
					taskOrderVO.setChipLabel(taskPackage.getChipLabel());
					taskOrderVO.setDiscBatch(taskPackage.getDiscBatch());
					taskOrderVO.setMarkDemand(taskPackage.getMarkDemand());
					taskOrderVO.setPackageNum(taskPackage.getPackageNum());
					taskOrderVO.setPackageShape(taskPackage.getPackageShape());
					taskOrderVO.setQualityLevel(taskPackage.getQualityLevel());
					taskOrderVO.setShellType(taskPackage.getShellType());
					taskOrderVO.setDiscNum(taskPackage.getDiscNum());
					taskOrderVO.setWaferFlag(taskPackage.getWaferFlag());
					taskOrderVO.setStockName(taskPackage.getStockName());
				}
			}
			//混合封装
			if(StringUtils.isHave(contentIds, "3022")){
				taskOrderVO.setMixPackageFlag(1);
				TaskMixPackage tasMixkPackage = taskOrder.getTaskMixPackage();
				if(UtilValidate.isNotEmpty(tasMixkPackage)) {
					List<Dictionary> packageStatus = tasMixkPackage.getMpackageStatus();
					String packageStatusIds = "";
					String packageStatusNames = "";
					for(Dictionary dic: packageStatus){
						if (packageStatusIds.length() > 0) {
							packageStatusIds += ",";
							packageStatusNames +=",";
						}
						packageStatusIds += dic.getDictionaryId();
						packageStatusNames += dic.getValue();
					}
					taskOrderVO.setMpackageStatusIds(packageStatusIds);
					taskOrderVO.setMpackageStatusNames(packageStatusNames);
					taskOrderVO.setMbondNum(tasMixkPackage.getMbondNum());
					taskOrderVO.setMchipLabel(tasMixkPackage.getMchipLabel());
					taskOrderVO.setMdiscBatch(tasMixkPackage.getMdiscBatch());
					taskOrderVO.setMmarkDemand(tasMixkPackage.getMmarkDemand());
					taskOrderVO.setMpackageNum(tasMixkPackage.getMpackageNum());
					taskOrderVO.setMpackageShape(tasMixkPackage.getMpackageShape());
					taskOrderVO.setMqualityLevel(tasMixkPackage.getMqualityLevel());
					taskOrderVO.setMshellType(tasMixkPackage.getMshellType());
					taskOrderVO.setMdiscNum(tasMixkPackage.getMdiscNum());
					taskOrderVO.setMwaferFlag(tasMixkPackage.getMwaferFlag());
					taskOrderVO.setMchipNum(tasMixkPackage.getMchipNum());
					taskOrderVO.setMstockFlag(tasMixkPackage.getMstockFlag());
					taskOrderVO.setMstockName(tasMixkPackage.getMstockName());
					
				}
			}
			//多芯片封装
			if(StringUtils.isHave(contentIds, "3023")){
				taskOrderVO.setMcpackageFlag(1);
				TaskMultiChipPackage taskMultiPackage = taskOrder.getTaskMultiChipPackage();
				if(UtilValidate.isNotEmpty(taskMultiPackage)) {
					List<Dictionary> packageStatus = taskMultiPackage.getMcpackageStatus();
					String packageStatusIds = "";
					String packageStatusNames = "";
					for(Dictionary dic: packageStatus){
						if (packageStatusIds.length() > 0) {
							packageStatusIds += ",";
							packageStatusNames +=",";
						}
						packageStatusIds += dic.getDictionaryId();
						packageStatusNames += dic.getValue();
					}
					taskOrderVO.setMcpackageStatusIds(packageStatusIds);
					taskOrderVO.setMcpackageStatusNames(packageStatusNames);
					taskOrderVO.setMcbondNum(taskMultiPackage.getMcbondNum());
					taskOrderVO.setMcchipLabel(taskMultiPackage.getMcchipLabel());
					taskOrderVO.setMcdiscBatch(taskMultiPackage.getMcdiscBatch());
					taskOrderVO.setMcmarkDemand(taskMultiPackage.getMcmarkDemand());
					taskOrderVO.setMcpackageNum(taskMultiPackage.getMcpackageNum());
					taskOrderVO.setMcpackageShape(taskMultiPackage.getMcpackageShape());
					taskOrderVO.setMcqualityLevel(taskMultiPackage.getMcqualityLevel());
					taskOrderVO.setMcshellType(taskMultiPackage.getMcshellType());
					taskOrderVO.setMcdiscNum(taskMultiPackage.getMcdiscNum());
					taskOrderVO.setMcwaferFlag(taskMultiPackage.getMcwaferFlag());
					taskOrderVO.setMcchipNum(taskMultiPackage.getMcchipNum());
					taskOrderVO.setMcstockFlag(taskMultiPackage.getMcstockFlag());
					taskOrderVO.setMcstockName(taskMultiPackage.getMcstockName());
				}
			}
		}
		//任务类型
		Dictionary orderType = taskOrder.getOrderType();
		if(UtilValidate.isNotEmpty(orderType)) {
			taskOrderVO.setOrderTypeId(String.valueOf(orderType.getDictionaryId()));
			taskOrderVO.setOrderTypeName(orderType.getAnnotation());
		}else {
			taskOrderVO.setOrderTypeName("");
		}
		
		//委托数量
		
		//鉴定方式
		List<Dictionary> checkType = taskOrder.getCheckType();
		String checkTypeIds = "";
		String checkTypeNames = "";
		for(Dictionary dic: checkType){
			if (checkTypeIds.length() > 0) {
				checkTypeIds += ",";
				checkTypeNames +=",";
			}
			checkTypeIds += dic.getDictionaryId();
			checkTypeNames += dic.getValue();
		}
		taskOrderVO.setCheckTypeId(checkTypeIds);
		taskOrderVO.setCheckTypeName(checkTypeNames);
		
		taskOrderVO.setOrderId(String.valueOf(taskOrder.getOrderId()));
		taskOrderVO.setHelpDeptId(String.valueOf(taskOrder.getHelpDept().getDictionaryId()));
		taskOrderVO.setHelpDeptName(taskOrder.getHelpDept().getValue());
		//状态名称
		for (TaskOrderStatus type : TaskOrderStatus.values()) {
			if(taskOrder.getStatus()== type.getValue()){
				taskOrderVO.setStatusName(type.getLabel());
			}
		}
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        //如果是部门待确认状态，任务单创建者具有确认权限
        if(taskOrder.getStatus() == TaskOrderStatus.WAITTOFIX_DEPARTMANAGE.getValue()){
        	if(String.valueOf(taskOrder.getMember().getMemberId()).equals(sessionInfo.getUserId())){
        		taskOrderVO.setFixState(1);
        	}else{
        		taskOrderVO.setFixState(0);
        	}
        }
        //设置审核权限和核价，修改权限
        switch(memType){
      	//只有部门调度，生产调度，封测有审核功能
	        case DepartMember:{
	        	if(taskOrder.getStatus() == TaskOrderStatus.NOTPASS_DEPARTMANAGE.getValue()){
	        		taskOrderVO.setEditState(1);
	        	}
	        	break;
	        }
			case DepartManage :{
				if(taskOrder.getStatus() == TaskOrderStatus.WAITTOCONFIRM_DEPARTMANAGE.getValue()||
					taskOrder.getStatus() == TaskOrderStatus.NOTPASS_PRODUCTMANAGE.getValue()||
					taskOrder.getStatus() == TaskOrderStatus.NOTPASS_TESTCENTERMANAGE.getValue()){
					taskOrderVO.setConfirmState(1);
				}
				break;
			}
			case ProductManage:{
				if(taskOrder.getStatus() == TaskOrderStatus.WAITTOCONFIRM_PRODUCTMANAGE.getValue()){
					taskOrderVO.setConfirmState(1);
				}
				taskOrderVO.setEditState(1);
				taskOrderVO.setUrgencyState(1);
				taskOrderVO.setDeleteState(1);
				break;
			}
			case TestCenterManage:{
				if(taskOrder.getStatus() == TaskOrderStatus.WAITTOCONFIRM_TESTCENTERMANAGE.getValue()){
					taskOrderVO.setConfirmState(1);
				}
				if(taskOrder.getStatus() == TaskOrderStatus.WAITTOCHARGE_TESTCENTERMANAGE.getValue()){
					taskOrderVO.setPriceState(1);
				}
				if(taskOrder.getStatus() == TaskOrderStatus.WAITTOFIX_TESTCENTERMANAGE.getValue()){
					taskOrderVO.setFixState(1);
					taskOrderVO.setPriceEditState(1);
				}
				break;
			}
			default:{
				taskOrderVO.setConfirmState(0);
				taskOrderVO.setPriceState(0);
				break;
			}
		
        }
        //附件
        if(taskOrder.getAttachmentFlag() == 1){
        	List<Attachment> attach = attachmentService.listByCode(taskOrder.getOrderId());
    		if (UtilValidate.isNotEmpty(attach)) {
    			List<AttachmentVO> attachvo = new ArrayList<AttachmentVO>();
    			for (Attachment attachment : attach) {
    				AttachmentVO avo = new AttachmentVO();
    				BeanUtilsEx.copyProperties(avo, attachment);
    				attachvo.add(avo);
    			}
    			taskOrderVO.setAttachment(attachvo);// 设置附件信息
    		}
        }
        //紧急程度
        if(taskOrder.getUrgency()!=null)
		{
			if(taskOrder.getUrgency()==1){
				taskOrderVO.setUrgencyName("紧急");
			}else if(taskOrder.getUrgency()==2){
				taskOrderVO.setUrgencyName("超紧急");
			}else{
				taskOrderVO.setUrgencyName("一般");
			}
		}else{
			taskOrderVO.setUrgencyName("一般");
		}
        
		return taskOrderVO;
	}

	@Override
	public TaskOrderVO getTaskOrderById(String taskId) {
		TaskOrder taskOrder = taskOrderDao.get(TaskOrder.class, Long.parseLong(taskId));
		TaskOrderVO taskOrderVO = getDtoData(taskOrder);
		return taskOrderVO;
	}
	
	/**
	 * 审核通过
	 */
	@Override
	@Transactional
	public TaskOrder confirmOK(TaskOrderVO taskOrdervo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
		TaskOrder taskOrder = taskOrderDao.get(TaskOrder.class, Long.parseLong(taskOrdervo.getOrderId()));
		//项目名称、课题号
		Dictionary project = dictionaryDao.get(Dictionary.class, Long.parseLong(taskOrdervo.getProjectId()));
		taskOrder.setProject(project);
		taskOrder.setTopic(project);
		
		//电路名称
		if(UtilValidate.isNotEmpty(taskOrdervo.getElectricId())) {
			Dictionary electric = dictionaryDao.get(Dictionary.class, Long.parseLong(taskOrdervo.getElectricId()));
			taskOrder.setElectric(electric);
		}
		//成本归集课题号
		//Dictionary costTopicNo = dictionaryDao.get(Dictionary.class, Long.parseLong(taskOrdervo.getCostTopicNoId()));
		//taskOrder.setCostTopicNo(costTopicNo);
		//所内型号
		taskOrder.setInternalModel(taskOrdervo.getInternalModel());
		//协助部门
		Dictionary helpDept = dictionaryDao.get(Dictionary.class, Long.parseLong(taskOrdervo.getHelpDeptId()));
		taskOrder.setHelpDept(helpDept);
		taskOrder.setApplyDept(taskOrdervo.getApplyDept());
		taskOrder.setApplyMember(taskOrdervo.getApplyMember());
		taskOrder.setApplyMemberPhone(taskOrdervo.getApplyMemberPhone());
		taskOrder.setProjectManager(taskOrdervo.getProjectManager());
		taskOrder.setProjectManagerPhone(taskOrdervo.getProjectManagerPhone());
		taskOrder.setDeliverable(taskOrdervo.getDeliverable());
		taskOrder.setWantedEndDate(taskOrdervo.getWantedEndDate());
		taskOrder.setAttachmentFlag(taskOrdervo.getAttachmentFlag());
		taskOrder.setSuperviseFlag(taskOrdervo.getSuperviseFlag());
		taskOrder.setSuperviseUnit(taskOrdervo.getSuperviseUnit());
		taskOrder.setControlledPlanFlag(taskOrdervo.getControlledPlanFlag());
		taskOrder.setCountersignFlag(taskOrdervo.getCountersignFlag());
		taskOrder.setDetailPlanNo(taskOrdervo.getDetailPlanNo());

		//任务类型
		if(UtilValidate.isNotEmpty(taskOrdervo.getOrderTypeId())) {
			Dictionary orderType = dictionaryDao.get(Dictionary.class, Long.parseLong(taskOrdervo.getOrderTypeId()));
			taskOrder.setOrderType(orderType);
		}
		//产品状态
		taskOrder.setProductStatus(taskOrdervo.getProductStatus());
		//业务申请内容
		List<Dictionary> applyContent = new  ArrayList<Dictionary>();
		if (UtilValidate.isNotEmpty(taskOrdervo.getApplyContentIds())) {
			for (String contentId : taskOrdervo.getApplyContentIds().split(",")) {
				Dictionary d = dictionaryDao.get(Dictionary.class, Long.parseLong(contentId));
				applyContent.add(d);
			}
			String[] contentIds = taskOrdervo.getApplyContentIds().split(",");
			//减薄
			if(StringUtils.isHave(contentIds, "8")){
				TaskReduction taskReduction = new TaskReduction();
				taskReduction.setReductionNo(taskOrdervo.getReductionNo());
				taskReduction.setReductionTabletsNo(taskOrdervo.getReductionTabletsNo());
				taskReduction.setReductionThickness(taskOrdervo.getReductionThickness());
				taskReductionDao.save(taskReduction);
				taskOrder.setTaskReduction(taskReduction);
			}
			//划片
			if(StringUtils.isHave(contentIds, "9")){
				TaskDicing taskDicing = new TaskDicing();
				taskDicing.setDicingNo(taskOrdervo.getDicingNo());
				taskDicing.setDicingTabletsNo(taskOrdervo.getDicingTabletsNo());
				taskDicing.setDicingTubeNum(taskOrdervo.getDicingTubeNum());
				taskDicing.setDicingPlan(taskOrdervo.getDicingPlan());
				taskDicingDao.save(taskDicing);
				taskOrder.setTaskDicing(taskDicing);
			}
			//封装
			if(StringUtils.isHave(contentIds, "11")){
				TaskPackage taskPackage = new TaskPackage();
				List<Dictionary> packageStatus = new  ArrayList<Dictionary>();
				if (UtilValidate.isNotEmpty(taskOrdervo.getPackageStatusIds())) {
					for (String packageStatusId : taskOrdervo.getPackageStatusIds().split(",")) {
						Dictionary d = dictionaryDao.get(Dictionary.class, Long.parseLong(packageStatusId));
						packageStatus.add(d);
					}
				}
				taskPackage.setPackageStatus(packageStatus);
				taskPackage.setBondNum(taskOrdervo.getBondNum());
				taskPackage.setChipLabel(taskOrdervo.getChipLabel());
				taskPackage.setDiscBatch(taskOrdervo.getDiscBatch());
				taskPackage.setMarkDemand(taskOrdervo.getMarkDemand());
				taskPackage.setPackageNum(taskOrdervo.getPackageNum());
				taskPackage.setPackageShape(taskOrdervo.getPackageShape());
				taskPackage.setQualityLevel(taskOrdervo.getQualityLevel());
				taskPackage.setShellType(taskOrdervo.getShellType());
				taskPackage.setDiscNum(taskOrdervo.getDiscNum());
				taskPackage.setWaferFlag(taskOrdervo.getWaferFlag());
				taskPackage.setStockName(taskOrdervo.getStockName());
				taskPackageDao.save(taskPackage);
				taskOrder.setTaskPackage(taskPackage);
			}
			//混合封装
			if(StringUtils.isHave(contentIds, "3022")){
				TaskMixPackage taskPackage = new TaskMixPackage();
				List<Dictionary> packageStatus = new  ArrayList<Dictionary>();
				if (UtilValidate.isNotEmpty(taskOrdervo.getMpackageStatusIds())) {
					for (String packageStatusId : taskOrdervo.getMpackageStatusIds().split(",")) {
						Dictionary d = dictionaryDao.get(Dictionary.class, Long.parseLong(packageStatusId));
						packageStatus.add(d);
					}
				}
				taskPackage.setMpackageStatus(packageStatus);
				taskPackage.setMbondNum(taskOrdervo.getMbondNum());
				taskPackage.setMchipLabel(taskOrdervo.getMchipLabel());
				taskPackage.setMdiscBatch(taskOrdervo.getMdiscBatch());
				taskPackage.setMmarkDemand(taskOrdervo.getMmarkDemand());
				taskPackage.setMpackageNum(taskOrdervo.getMpackageNum());
				taskPackage.setMpackageShape(taskOrdervo.getMpackageShape());
				taskPackage.setMqualityLevel(taskOrdervo.getMqualityLevel());
				taskPackage.setMshellType(taskOrdervo.getMshellType());
				taskPackage.setMdiscNum(taskOrdervo.getMdiscNum());
				taskPackage.setMwaferFlag(taskOrdervo.getMwaferFlag());
				taskPackage.setMchipNum(taskOrdervo.getMchipNum());
				taskPackage.setMstockFlag(taskOrdervo.getMstockFlag());
				taskPackage.setMstockName(taskOrdervo.getMstockName());
				taskMixPackageDao.save(taskPackage);
				taskOrder.setTaskMixPackage(taskPackage);
			}
			//多芯片封装
			if(StringUtils.isHave(contentIds, "3023")){
				TaskMultiChipPackage taskPackage = new TaskMultiChipPackage();
				List<Dictionary> packageStatus = new  ArrayList<Dictionary>();
				if (UtilValidate.isNotEmpty(taskOrdervo.getMcpackageStatusIds())) {
					for (String packageStatusId : taskOrdervo.getMcpackageStatusIds().split(",")) {
						Dictionary d = dictionaryDao.get(Dictionary.class, Long.parseLong(packageStatusId));
						packageStatus.add(d);
					}
				}
				taskPackage.setMcpackageStatus(packageStatus);
				taskPackage.setMcbondNum(taskOrdervo.getMcbondNum());
				taskPackage.setMcchipLabel(taskOrdervo.getMcchipLabel());
				taskPackage.setMcdiscBatch(taskOrdervo.getMcdiscBatch());
				taskPackage.setMcmarkDemand(taskOrdervo.getMcmarkDemand());
				taskPackage.setMcpackageNum(taskOrdervo.getMcpackageNum());
				taskPackage.setMcpackageShape(taskOrdervo.getMcpackageShape());
				taskPackage.setMcqualityLevel(taskOrdervo.getMcqualityLevel());
				taskPackage.setMcshellType(taskOrdervo.getMcshellType());
				taskPackage.setMcdiscNum(taskOrdervo.getMcdiscNum());
				taskPackage.setMcwaferFlag(taskOrdervo.getMcwaferFlag());
				taskPackage.setMcchipNum(taskOrdervo.getMcchipNum());
				taskPackage.setMcstockFlag(taskOrdervo.getMcstockFlag());
				taskPackage.setMcstockName(taskOrdervo.getMcstockName());
				taskMultiChipPackageDao.save(taskPackage);
				taskOrder.setTaskMultiChipPackage(taskPackage);
			}
		}
		taskOrder.setApplyContent(applyContent);
		//委托数量
		taskOrder.setEntrustNum(taskOrdervo.getEntrustNum());
		//鉴定方式
		List<Dictionary> checkType = new  ArrayList<Dictionary>();
		if (UtilValidate.isNotEmpty(taskOrdervo.getCheckTypeId())) {
			for (String checkTypeId : taskOrdervo.getCheckTypeId().split(",")) {
				Dictionary d = dictionaryDao.get(Dictionary.class, Long.parseLong(checkTypeId));
				checkType.add(d);
			}
		}
		taskOrder.setCheckType(checkType);
		taskOrder.setApplyReason(taskOrdervo.getApplyReason());
		taskOrder.setUrgency(taskOrdervo.getUrgency());
		taskOrder.setDetailRequire(taskOrdervo.getDetailRequire());
		taskOrder.setRemarks(taskOrdervo.getRemarks());
		taskOrder.setProductManagesuggest(taskOrdervo.getProductManagesuggest());
		
		switch(memType){
      	//只有部门调度，生产调度，封测有审核功能
			case DepartMember :{
				taskOrder.setStatus(TaskOrderStatus.WAITTOCONFIRM_DEPARTMANAGE.getValue());
				break;
			}
			case DepartManage :{
				taskOrder.setStatus(TaskOrderStatus.WAITTOCONFIRM_PRODUCTMANAGE.getValue());
				break;
			}
			case ProductManage:{
				if(taskOrder.getStatus() != TaskOrderStatus.COMPLETED.getValue()){
					taskOrder.setStatus(TaskOrderStatus.WAITTOCONFIRM_TESTCENTERMANAGE.getValue());
				}
				break;
			}
			case TestCenterManage:{
				//2020.01.27取消封测核价操作
				//taskOrder.setStatus(TaskOrderStatus.WAITTOCHARGE_TESTCENTERMANAGE.getValue());
				taskOrder.setStatus(TaskOrderStatus.WAITTOFIX_DEPARTMANAGE.getValue());
				break;
			}
			default:{
				break;
			}
      	}
		//有附件
		if(taskOrdervo.getAttachmentFlag() == 1){
			// 给附件指定所属信息项
			if (taskOrdervo.getAttachids() != null) {
				for (Integer aid : taskOrdervo.getAttachids()) {
					Attachment att = attachmentDao.get(Attachment.class, aid);
					att.setTaskOrder(taskOrder);
					attachmentDao.update(att);
				}
			}
		}
		taskOrderDao.saveOrUpdate(taskOrder);
		//操作日志
		OperateLog opLog = new OperateLog();
		opLog.setLshId(taskOrder.getLsh());
		opLog.setContent(sessionInfo.getLoginName()+"审核通过了任务单"+taskOrder.toLogInfo());
		opLog.setMemberId(Long.valueOf(sessionInfo.getUserId()));
		opLog.setCreatetime(new Date());
		operatelogDao.save(opLog);
		return taskOrder;
	}
	
	/**
	 * 审核不通过
	 */
	@Override
	@Transactional
	public TaskOrder confirmNG(TaskOrderVO taskOrdervo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
		TaskOrder taskOrder = taskOrderDao.get(TaskOrder.class, Long.parseLong(taskOrdervo.getOrderId()));
		//不通过原因
		taskOrder.setProductManagesuggest(taskOrdervo.getProductManagesuggest());
		switch(memType){
		//只有部门调度，生产调度，封测有审核功能
			case DepartManage :{
				taskOrder.setStatus(TaskOrderStatus.NOTPASS_DEPARTMANAGE.getValue());
				break;
			}
			case ProductManage:{
				taskOrder.setStatus(TaskOrderStatus.NOTPASS_PRODUCTMANAGE.getValue());
				break;
			}
			case TestCenterManage:{
				taskOrder.setStatus(TaskOrderStatus.NOTPASS_TESTCENTERMANAGE.getValue());
				break;
			}
			default:{
				break;
			}
      	}
		taskOrderDao.saveOrUpdate(taskOrder);
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
	public HttpServletResponse downloadExcel(TaskOrderVO taskOrdervo,
			HttpServletResponse response) throws Exception {
		ExcelDateInfo excelDateInfo = new ExcelDateInfo();
		String hql = "from TaskOrder t";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(taskOrdervo, hql, params);
		hql = addOrder(taskOrdervo, hql);
		List<TaskOrder> l = taskOrderDao.find(hql, params, taskOrdervo.getPage(), taskOrdervo.getRows());
		
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
		excelDateInfo.setFileName("taskList_"+date);
		excelDateInfo.setSheetName("任务单数据");
		excelDateInfo.setTitle("任务单数据导出数据如下");
		excelDateInfo.setColumnTitle(columnTitle());
	}
	/**
	 * 明细列标题
	 * @return
	 */
	private List<String> columnTitle(){
		List<String> columnTitle = new ArrayList<String>();
		columnTitle.add("任务单号");
		columnTitle.add("项目名称");
		columnTitle.add("所内型号");
		columnTitle.add("申请部门 ");
		columnTitle.add("申请人");
		columnTitle.add("课题号");
		columnTitle.add("项目负责人");
		columnTitle.add("申请日期");
		columnTitle.add("希望完成时间");
		columnTitle.add("任务单状态");
		columnTitle.add("交付物 ");
		columnTitle.add("业务申请内容");
		columnTitle.add("申请原因");
		columnTitle.add("任务类型");
		columnTitle.add("紧急程度");
		columnTitle.add("产品状态");

		return columnTitle;
	}
	
	/**
	 * 导出数据
	 * @param consults
	 * @return
	 */
	private List<List<String>> exportChangeModel(List<TaskOrder> taskOrder){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<List<String>> rows = new ArrayList<List<String>>();
		List<String> columns = null;
		for (Iterator<TaskOrder> iterator = taskOrder.iterator(); iterator.hasNext();) {
			TaskOrder task = (TaskOrder) iterator.next();
			columns = new ArrayList<String>();
			columns.add(task.getLsh());
			columns.add(task.getProject().getValue());
			columns.add(task.getInternalModel());
			columns.add(task.getApplyDept());
			columns.add(task.getApplyMember());
			columns.add(task.getTopic().getValue());
			columns.add(task.getProjectManager());
			columns.add(task.getCreatetime()==null?"":sdf.format(task.getCreatetime()));
			columns.add(task.getWantedEndDate()==null?"":sdf.format(task.getWantedEndDate()));
			//状态名称
			String statusName="";
			for (TaskOrderStatus type : TaskOrderStatus.values()) {
				if(task.getStatus()== type.getValue()){
					statusName = type.getLabel();
				}
			}
			columns.add(statusName);
			columns.add(task.getDeliverable());
			//业务申请内容
			List<Dictionary> applyContent = task.getApplyContent();
			String applyContentIds = "";
			String applyContentNames = "";
			for(Dictionary dic: applyContent){
				if (applyContentIds.length() > 0) {
					applyContentIds += ",";
					applyContentNames +=",";
				}
				applyContentIds += dic.getDictionaryId();
				applyContentNames += dic.getValue();
			}
			columns.add(applyContentNames);
			//申请原因
			columns.add(task.getApplyReason());
			//任务类型
			Dictionary orderType = task.getOrderType();
			
			if(UtilValidate.isNotEmpty(orderType)) {
				columns.add(task.getOrderType().getAnnotation());
			}else {
				columns.add("");
			}
			
			//紧急程度
			if(task.getUrgency()!=null)
			{
				if(task.getUrgency()==1){
					columns.add("紧急");
				}else if(task.getUrgency()==2){
					columns.add("超紧急");
				}else{
					columns.add("一般");
				}
			}else{
				columns.add("一般");
			}
			//在研
			if(task.getProductStatus()!=null)
			{
				if(task.getProductStatus() == 0) {
					columns.add("在研");	
				}else if(task.getProductStatus() == 1){
					columns.add("老品");
				}
			}else {
				columns.add("");
			}
			rows.add(columns);
		}
		
		return rows;
	}
	@Override
	@Transactional
	public TaskOrder checkPrice(PriceCheckVO priceCheckVO) {
		List<TaskPriceVO> listPrice = new ArrayList<TaskPriceVO>();
		TaskOrder taskOrder = taskOrderDao.get(TaskOrder.class, Long.parseLong(priceCheckVO.getOrderId()));
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
					TaskPrice tp  =  new TaskPrice();
					PriceItem pi = priceItemDao.get(PriceItem.class, Long.parseLong(taskPriceVO.getItemId()));
					tp.setItemNum(taskPriceVO.getAmount());
					tp.setItemSum(taskPriceVO.getUnitcost());
					tp.setPriceItem(pi);
					tp.setRemarks(taskPriceVO.getRemarks());
					tp.setTaskOrder(taskOrder);
					taskOrder.addPriceItem(tp);
				}
			}
		}else{
			//修改核价
			Set<TaskPrice> taskPriceListOld = taskOrder.getTaskPriceList();
			for (TaskPriceVO taskPriceVO : listPrice) {
				String itemID = taskPriceVO.getItemId();
				boolean isExist = false;
				for (TaskPrice taskPrice : taskPriceListOld) {
					if(taskPrice.getPriceItem().getItemId().toString().equals(itemID)){
						isExist = true;
						taskPrice.setItemNum(taskPriceVO.getAmount());
						taskPrice.setItemSum(taskPriceVO.getUnitcost());
						taskPrice.setRemarks(taskPriceVO.getRemarks());
						if(taskPriceVO.getUnitcost()==0.0){
							deleteList.add(taskPrice.getTaskPriceId());
						}
					}
				}
				if(!isExist){
					if(UtilValidate.isNotEmpty(taskPriceVO.getUnitcost()) && taskPriceVO.getUnitcost()!=0.0 ){
						TaskPrice tp  =  new TaskPrice();
						PriceItem pi = priceItemDao.get(PriceItem.class, Long.parseLong(taskPriceVO.getItemId()));
						tp.setItemNum(taskPriceVO.getAmount());
						tp.setItemSum(taskPriceVO.getUnitcost());
						tp.setPriceItem(pi);
						tp.setRemarks(taskPriceVO.getRemarks());
						tp.setTaskOrder(taskOrder);
						taskOrder.addPriceItem(tp);
					}
				}
			}
			//
			for (Long deleteId : deleteList) {
				TaskPrice  taskPrice =  taskPriceDao.get(TaskPrice.class, deleteId);
				taskOrder.removePriceItem(taskPrice);
			}
		}
		Set<TaskPrice> taskPriceListAfter = taskOrder.getTaskPriceList();
		Double sumPrice = 0.0;
		for (TaskPrice taskPrice : taskPriceListAfter) {
			sumPrice = Arith.add(sumPrice, taskPrice.getItemSum());
		}
		taskOrder.setSumPrice(sumPrice);
		//初始核价
		if(UtilValidate.isEmpty(taskOrder.getTaskSchedule())){
			TaskSchedule ts = new TaskSchedule();
			BeanUtils.copyProperties(priceCheckVO, ts);
			ts.setPakTime(DateUtil.getDate(ts.getPakstartDate(),ts.getPakendDate()));
			ts.setSvTime(DateUtil.getDate(ts.getSvstartDate(),ts.getSvendDate()));
			ts.setTestTime(DateUtil.getDate(ts.getTeststartDate(),ts.getTestendDate()));
			ts.setSxTime(DateUtil.getDate(ts.getSxstartDate(),ts.getSxendDate()));
			ts.setJdTime(DateUtil.getDate(ts.getJdstartDate(),ts.getJdendDate()));
			baseDao.save(ts);
			taskOrder.setTaskSchedule(ts);
		}else{
			//修改核价
			TaskSchedule ts = taskOrder.getTaskSchedule();
			BeanUtils.copyProperties(priceCheckVO, ts);
			ts.setPakTime(DateUtil.getDate(ts.getPakstartDate(),ts.getPakendDate()));
			ts.setSvTime(DateUtil.getDate(ts.getSvstartDate(),ts.getSvendDate()));
			ts.setTestTime(DateUtil.getDate(ts.getTeststartDate(),ts.getTestendDate()));
			ts.setSxTime(DateUtil.getDate(ts.getSxstartDate(),ts.getSxendDate()));
			ts.setJdTime(DateUtil.getDate(ts.getJdstartDate(),ts.getJdendDate()));
			taskOrder.setTaskSchedule(ts);
		}
		taskOrder.setStatus(TaskOrderStatus.WAITTOFIX_TESTCENTERMANAGE.getValue());
		taskOrderDao.update(taskOrder);
		for (Long deleteId : deleteList) {
			TaskPrice  taskPrice =  taskPriceDao.get(TaskPrice.class, deleteId);
			taskPriceDao.delete(taskPrice);
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
	public List<TaskPriceVO> getTaskPriceListById(String taskId) {
		TaskOrder taskOrder = taskOrderDao.get(TaskOrder.class, Long.parseLong(taskId));
		Set<TaskPrice> tpList = taskOrder.getTaskPriceList();
		List<TaskPriceVO> taskPriceList = new ArrayList<TaskPriceVO>();
		for (TaskPrice tp : tpList) {
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
		TaskOrder taskOrder = taskOrderDao.get(TaskOrder.class, Long.parseLong(taskId));
		TaskScheduleVO taskScheduleVO = new TaskScheduleVO();
		TaskSchedule taskSchedule = taskOrder.getTaskSchedule();
		BeanUtils.copyProperties(taskSchedule, taskScheduleVO);
		return taskScheduleVO;
	}
	@Override
	@Transactional
	public TaskOrder fixComplete(TaskOrderVO taskOrdervo) {
		TaskOrder taskOrder = taskOrderDao.get(TaskOrder.class, Long.parseLong(taskOrdervo.getOrderId()));
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        switch(memType){
			case TestCenterManage:{
				taskOrder.setStatus(TaskOrderStatus.WAITTOFIX_DEPARTMANAGE.getValue());
				break;
			}
			default:{
				taskOrder.setStatus(TaskOrderStatus.COMPLETED.getValue());
				break;
			}
      	}
        taskOrderDao.update(taskOrder);
        //操作日志
  		OperateLog opLog = new OperateLog();
  		opLog.setLshId(taskOrder.getLsh());
  		opLog.setContent(sessionInfo.getLoginName()+"对任务单进行了确认");
  		opLog.setMemberId(Long.valueOf(sessionInfo.getUserId()));
  		opLog.setCreatetime(new Date());
  		operatelogDao.save(opLog);
        return taskOrder;
	}
	
	public int getTaskNum(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        Member currentMember = memberDao.get(Member.class, Long.parseLong(sessionInfo.getUserId()));
        Map<String, Object> params = new HashMap<String, Object>();
        String hql = "from TaskOrder t where 1=1 and t.status !=0 and (t.taskOrderType=0 or t.taskOrderType is null) ";
        switch(memType){
	        case DepartMember :{
	        	hql += " and t.status in (11,12,13)";
	        	hql += " and t.member.memberId = :getMemberId ";
				params.put("getMemberId", Long.parseLong(sessionInfo.getUserId()));
				break;
			}
			case DepartManage :{
				hql += " and t.status in (21,22,23)";
				hql += " and t.member.organization.dictionaryId = :getDictionaryId ";
				params.put("getDictionaryId", currentMember.getOrganization().getDictionaryId());
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
        return taskOrderDao.count(totalHql,params).intValue();
	}
	
	@Transactional
	public void cancel(String id){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		TaskOrder taskOrder = taskOrderDao.get(TaskOrder.class, Long.valueOf(id));
		taskOrder.setStatus(TaskOrderStatus.CANCELED.getValue());
		taskOrderDao.update(taskOrder);
		//操作日志
  		OperateLog opLog = new OperateLog();
  		opLog.setLshId(taskOrder.getLsh());
  		opLog.setContent(sessionInfo.getLoginName()+"取消了任务单"+id);
  		opLog.setMemberId(Long.valueOf(sessionInfo.getUserId()));
  		opLog.setCreatetime(new Date());
  		operatelogDao.save(opLog);
	}
	
	@Transactional
	public void copy(String id){
		TaskOrder taskOrder = taskOrderDao.get(TaskOrder.class, Long.valueOf(id));
		TaskOrderVO taskOrderVO = getDtoData(taskOrder);
		taskOrderVO.setOrderId(null);
		create(taskOrderVO);
	}
	
	
	@Transactional
	public void remove(String id){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		TaskOrder taskOrder = taskOrderDao.get(TaskOrder.class, Long.valueOf(id));
		taskOrder.setStatus(0);
		taskOrderDao.update(taskOrder);
		//操作日志
  		OperateLog opLog = new OperateLog();
  		opLog.setLshId(taskOrder.getLsh());
  		opLog.setContent(sessionInfo.getLoginName()+"删除了任务单"+id);
  		opLog.setMemberId(Long.valueOf(sessionInfo.getUserId()));
  		opLog.setCreatetime(new Date());
  		operatelogDao.save(opLog);
	}
	public DataGrid logList(LogVO logVO){
		DataGrid dg = new DataGrid();
		if(UtilValidate.isNotEmpty(logVO.getLshId())){
			String hql = "from OperateLog t ";
			hql += " Where t.lshId=:getLshId order by t.createtime";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("getLshId", logVO.getLshId());
			String totalHql = "select count(*) " + hql;
			List<OperateLog> l = operatelogDao.find(hql,params);
			List<LogVO> nl = new ArrayList<LogVO>();
			if (l != null && l.size() > 0) {
				for (Iterator<OperateLog> iterator = l.iterator(); iterator.hasNext();) {
					OperateLog operateLog = (OperateLog) iterator.next();
					LogVO log = new LogVO();
					BeanUtilsEx.copyProperties(log,operateLog);
					nl.add(log);
				}
			}
			dg.setTotal(taskOrderDao.count(totalHql, params));
			dg.setRows(nl);
		}
		return dg;
	}
	@Override
	public DataGrid listSusTaskOrder(TaskOrderVO taskOrdervo) {
		DataGrid dg = new DataGrid();
		String hql = "from TaskOrder t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(taskOrdervo, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(taskOrdervo, hql);
		List<TaskOrder> l = taskOrderDao.find(hql, params, taskOrdervo.getPage(), taskOrdervo.getRows());
		List<TaskOrderVO> nl = new ArrayList<TaskOrderVO>();
		changeModelList(l, nl);
		dg.setTotal(taskOrderDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
}
