package com.sdmx.taskmanage.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sdmx.exportexcel.dto.ExcelDateInfo;
import com.sdmx.exportexcel.service.IExportService;
import com.sdmx.framework.dao.IMemberDao;
import com.sdmx.framework.entity.Member;
import com.sdmx.framework.util.BeanUtilsEx;
import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.RoleType;
import com.sdmx.framework.vo.SessionInfo;
import com.sdmx.taskmanage.dao.IOperateLogDao;
import com.sdmx.taskmanage.dao.IRadiationLshDao;
import com.sdmx.taskmanage.dao.IRadiationTaskOrderDao;
import com.sdmx.taskmanage.entity.OperateLog;
import com.sdmx.taskmanage.entity.RadiationTaskOrder;
import com.sdmx.taskmanage.service.IRadiationTaskOrderService;
import com.sdmx.taskmanage.vo.LogVO;
import com.sdmx.taskmanage.vo.MonthChart;
import com.sdmx.taskmanage.vo.OperatorQueryType;
import com.sdmx.taskmanage.vo.PieRate;
import com.sdmx.taskmanage.vo.RadiationTaskOrderStatus;
import com.sdmx.taskmanage.vo.RadiationTaskOrderVO;

@Service("radiationTaskOrderService")
public class RadiationTaskOrderServiceImpl implements IRadiationTaskOrderService{
	@Autowired
	private IRadiationTaskOrderDao radiationTaskOrderDao;
	@Autowired
	private IMemberDao memberDao;
	@Autowired
	private IExportService exportExcelService;
	@Autowired
	private IOperateLogDao operatelogDao;
	@Autowired
	private IRadiationLshDao lshDao;
	@Override
	public DataGrid listTaskOrder(RadiationTaskOrderVO radiationTaskOrderVO) {
		DataGrid dg = new DataGrid();
		String hql = "from RadiationTaskOrder t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(radiationTaskOrderVO, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(radiationTaskOrderVO, hql);
		List<RadiationTaskOrder> l = radiationTaskOrderDao.find(hql, params, radiationTaskOrderVO.getPage(), radiationTaskOrderVO.getRows());
		List<RadiationTaskOrderVO> nl = new ArrayList<RadiationTaskOrderVO>();
		changeModelList(l, nl);
		dg.setTotal(radiationTaskOrderDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	
	public List<PieRate> getPieRate() {
		List<PieRate> list = new ArrayList<PieRate>();
		String hql = "from RadiationTaskOrder t  where 1=1 and t.status !=0  and t.type=0";
		Map<String, Object> params = new HashMap<String, Object>();
		List<RadiationTaskOrder> l = radiationTaskOrderDao.find(hql, params);
		int scdsh = 0;
		int kjdsh = 0;
		int kjdqr = 0;
		int wc = 0;
		int qt = 0;
		for (RadiationTaskOrder radiationTaskOrder : l) {
			if(radiationTaskOrder.getStatus()==31){
				scdsh +=1;
			}else if (radiationTaskOrder.getStatus()==61){
				kjdsh+=1;
			}else if (radiationTaskOrder.getStatus()==62){
				kjdqr+=1;
			}else if (radiationTaskOrder.getStatus()==51){
				wc+=1;
			}else{
				qt+=1;
			}
		}
		
		PieRate pie = new PieRate();
		pie.setName("生产待审核");
		pie.setRate(scdsh);
		list.add(pie);
		PieRate pie1 = new PieRate();
		pie1.setName("抗加待审核");
		pie1.setRate(kjdsh);
		list.add(pie1);
		PieRate pie2 = new PieRate();
		pie2.setName("抗加待确认");
		pie2.setRate(kjdqr);
		list.add(pie2);
		PieRate pie3 = new PieRate();
		pie3.setName("完成");
		pie3.setRate(wc);
		list.add(pie3);
		PieRate pie4 = new PieRate();
		pie4.setName("其他");
		pie4.setRate(qt);
		list.add(pie4);
		return list;
	}
	public List<MonthChart> getSumByMonth()
	{
		List<MonthChart> list = new ArrayList<MonthChart>();
		MonthChart jdl = new MonthChart();
		jdl.setName("鉴定类");
		int[] sumNum = radiationTaskOrderDao.getSumByMonth();
		jdl.setData(sumNum);
		list.add(jdl);
		MonthChart mdl = new MonthChart();
		mdl.setName("摸底类");
		int[] sumNum1 = radiationTaskOrderDao.getSumByMonthMD();
		mdl.setData(sumNum1);
		list.add(mdl);
		return list;
	}
	private String addWhere(RadiationTaskOrderVO radiationTaskOrderVO, String hql, Map<String, Object> params) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        //Member currentMember = memberDao.get(Member.class, Long.parseLong(sessionInfo.getUserId()));
		hql += " where 1=1 and t.status !=0";
		//电路名称
		if (UtilValidate.isNotEmpty(radiationTaskOrderVO.getCircuitName())) {
			hql += " and t.circuitName like :circuitName";
			params.put("circuitName", "%%" +radiationTaskOrderVO.getCircuitName().trim() + "%%");
		}
		//电路型号
		if (UtilValidate.isNotEmpty(radiationTaskOrderVO.getCircuitType())) {
			hql += " and t.circuitType like :circuitType";
			params.put("circuitType", "%%" +radiationTaskOrderVO.getCircuitType().trim() + "%%");
		}
		//任务单号
		if (UtilValidate.isNotEmpty(radiationTaskOrderVO.getLsh())) {
			hql += " and t.lsh= :lshNo";
			params.put("lshNo", radiationTaskOrderVO.getLsh().trim());
		}
		//任务单状态
		if (radiationTaskOrderVO.getOrderStatus()!=0) {
			if(radiationTaskOrderVO.getOrderStatus()==2)
			{
				hql += " and t.status=51 ";
			}else{
				hql += " and t.status != 51";
			}
			
		}
		//开始登记时间
		if (radiationTaskOrderVO.getRegistTimeStart() != null) {
			hql += " and t.createtime >= :regStartDate";
			params.put("regStartDate", radiationTaskOrderVO.getRegistTimeStart());
		}
		//结束登记时间
		if (radiationTaskOrderVO.getRegistTimeEnd() != null) {
			hql += " and t.createtime <= :regEndDate";
			params.put("regEndDate", radiationTaskOrderVO.getRegistTimeEnd());
		}
		//根据角色确定内容
		RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
		switch(memType){
			case MarketManger:
			case DepartMember:
			case DepartManage:{
				//待处理查询
				if(OperatorQueryType.SuspendingQuery.getValue().equals(radiationTaskOrderVO.getClType())){
					hql += " and t.status in (22,24)";
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
				if(OperatorQueryType.SuspendingQuery.getValue().equals(radiationTaskOrderVO.getClType())){
					hql += " and (t.status ='31' " +
							" or (t.status ='23' and t.member.memberId = :getMemberId))";
					params.put("getMemberId", Long.parseLong(sessionInfo.getUserId()));
				}
				break;
			}
			case KangjiaManage:{
				//待处理查询
				if(OperatorQueryType.SuspendingQuery.getValue().equals(radiationTaskOrderVO.getClType())){
					hql += " and t.status in (61,62)";
				}
				break;
			}
			default:{
				break;
			}
		}
		
		return hql;
	}
	private String addOrder(RadiationTaskOrderVO radiationTaskOrderVO, String hql){
		if (radiationTaskOrderVO.getSort() != null) {
			if("statusName".equals(radiationTaskOrderVO.getSort())){
				hql += " order by t.status "  + radiationTaskOrderVO.getOrder();
			}else if("circuitName".equals(radiationTaskOrderVO.getSort())){
				hql += " order by t.circuitName "  + radiationTaskOrderVO.getOrder();
			}else if("microchipsVersion".equals(radiationTaskOrderVO.getSort())){
				hql += " order by t.microchipsVersion "  + radiationTaskOrderVO.getOrder();
			}else{
				hql += " order by " + radiationTaskOrderVO.getSort() + " " + radiationTaskOrderVO.getOrder();
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
	private void changeModelList(List<RadiationTaskOrder> l, List<RadiationTaskOrderVO> nl) {
		if (l != null && l.size() > 0) {
			for (Iterator<RadiationTaskOrder> iterator = l.iterator(); iterator.hasNext();) {
				RadiationTaskOrder taskOrder = (RadiationTaskOrder) iterator.next();
				RadiationTaskOrderVO taskOrderVO = getDtoData(taskOrder);
				nl.add(taskOrderVO);
			}
		}
	}
	
	private RadiationTaskOrderVO getDtoData(RadiationTaskOrder taskOrder){
		RadiationTaskOrderVO taskOrderVO = new RadiationTaskOrderVO();
		BeanUtilsEx.copyProperties(taskOrderVO,taskOrder);
		
		for (RadiationTaskOrderStatus type : RadiationTaskOrderStatus.values()) {
			if(taskOrder.getStatus()== type.getValue()){
				taskOrderVO.setStatusName(type.getLabel());
			}
		}
		//性质
		if(taskOrder.getType()==0)
		{
			taskOrderVO.setTypeName("鉴定类");
		}else{
			taskOrderVO.setTypeName("摸底类");
		}
		//类别
		String typeFlag = "";
		//生产批次
		String batch = "";
		String proVal = "";
		if(taskOrder.getSingleionsFlag()==1)
		{	
			if(taskOrder.getTotalDoseFlag()==1)
			{
				typeFlag = "单例子/总剂量";
				batch = taskOrder.getSingleionsBatch()+"/"+taskOrder.getTotalDoseBatch();
				proVal =taskOrder.getSingleProVal()+"/"+taskOrder.getTotalProVal();
			}else{
				typeFlag = "单例子";
				batch = taskOrder.getSingleionsBatch();
				proVal = taskOrder.getSingleProVal().toString();
			}
		}else{
			typeFlag = "总剂量";
			batch = taskOrder.getTotalDoseBatch();
			proVal = taskOrder.getTotalProVal().toString();
		}
		taskOrderVO.setFlagName(typeFlag);
		//生产批次
		taskOrderVO.setBatch(batch);
		//产值
		taskOrderVO.setProVal(proVal);
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        //设置审核权限和核价，修改权限
        switch(memType){
      		//只有生产调度，抗加有审核功能
	        case DepartMember:
	        case DepartManage:{
	        	if(taskOrder.getStatus() == RadiationTaskOrderStatus.NOTPASS_PRODUCTMANAGE.getValue() || 
	        			taskOrder.getStatus() == RadiationTaskOrderStatus.NOTPASS_KANGJIAMANAGE.getValue()){
	        		taskOrderVO.setEditState(1);
	        	}
	        	break;
	        }
			case ProductManage:{
				if(taskOrder.getStatus() == RadiationTaskOrderStatus.WAITTOCONFIRM_PRODUCTMANAGE.getValue()){
					taskOrderVO.setConfirmState(1);
				}
				taskOrderVO.setEditState(1);
				taskOrderVO.setDeleteState(1);
				taskOrderVO.setUrgencyState(1);
				break;
			}
			case KangjiaManage:{
				if(taskOrder.getStatus() == RadiationTaskOrderStatus.WAITTOCONFIRM_KANGJIAMANAGE.getValue()){
					taskOrderVO.setConfirmState(1);
				}
				if(taskOrder.getStatus() == RadiationTaskOrderStatus.WAITTOFIX_KANGJIAMANAGE.getValue()){
					taskOrderVO.setFixState(1);
				}
				break;
			}
			default:{
				taskOrderVO.setConfirmState(0);
				break;
			}
		
        }
      
		return taskOrderVO;
	}
	
	@Override
	public DataGrid listSusTaskOrder(RadiationTaskOrderVO radiationTaskOrderVO) {
		DataGrid dg = new DataGrid();
		String hql = "from RadiationTaskOrder t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(radiationTaskOrderVO, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(radiationTaskOrderVO, hql);
		List<RadiationTaskOrder> l = radiationTaskOrderDao.find(hql, params, radiationTaskOrderVO.getPage(), radiationTaskOrderVO.getRows());
		List<RadiationTaskOrderVO> nl = new ArrayList<RadiationTaskOrderVO>();
		changeModelList(l, nl);
		dg.setTotal(radiationTaskOrderDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	@Override
	@Transactional
	public RadiationTaskOrder create(RadiationTaskOrderVO radiationTaskOrderVO) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        Member currentMember = memberDao.get(Member.class, Long.parseLong(sessionInfo.getUserId()));
        RadiationTaskOrder taskOrder = new RadiationTaskOrder();
		BeanUtilsEx.copyProperties(taskOrder, radiationTaskOrderVO);
		//希望完成时间
		taskOrder.setTestSampleSplDate(radiationTaskOrderVO.getTestSampleSplDate());
		taskOrder.setProjectManagerTime(radiationTaskOrderVO.getProjectManagerTime());
		taskOrder.setProductManagerTime(radiationTaskOrderVO.getProductManagerTime());
		taskOrder.setTestManagerTime(radiationTaskOrderVO.getTestManagerTime());
		taskOrder.setRadiationLeaderTime(radiationTaskOrderVO.getRadiationLeaderTime());
		
		taskOrder.setCreatetime(new Date());
		taskOrder.setType(radiationTaskOrderVO.getType());
		switch(memType){
      	//只有部门员工，部门调度，生产调度有创建功能
			case DepartMember :
			case DepartManage :{
				taskOrder.setStatus(RadiationTaskOrderStatus.WAITTOCONFIRM_PRODUCTMANAGE.getValue());
				break;
			}
			case ProductManage:{
				taskOrder.setStatus(RadiationTaskOrderStatus.WAITTOCONFIRM_KANGJIAMANAGE.getValue());
				break;
			}
			default:{
				break;
			}
      	}
		taskOrder.setMember(currentMember);
		//生成流水号
		taskOrder.setLsh(lshDao.getRadiationLsh());
		
		radiationTaskOrderDao.save(taskOrder);
		OperateLog opLog = new OperateLog();
		opLog.setLshId(taskOrder.getLsh());
		opLog.setContent(sessionInfo.getLoginName()+"创建了任务单");
		opLog.setMemberId(currentMember.getMemberId());
		opLog.setCreatetime(new Date());
		operatelogDao.save(opLog);
		return taskOrder;
	}
	@Override
	public RadiationTaskOrderVO getTaskOrderById(String taskId) {
		RadiationTaskOrder radiationTaskOrder = radiationTaskOrderDao.get(RadiationTaskOrder.class, Long.parseLong(taskId));
		RadiationTaskOrderVO radiationTaskOrderVO = getDtoData(radiationTaskOrder);
		return radiationTaskOrderVO;
	}
	@Override
	@Transactional
	public RadiationTaskOrder confirmOK(
			RadiationTaskOrderVO radiationTaskOrderVO) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        RadiationTaskOrder taskOrder = radiationTaskOrderDao.get(RadiationTaskOrder.class, radiationTaskOrderVO.getOrderId());
		//BeanUtilsEx.copyProperties(taskOrder, radiationTaskOrderVO);
		//希望完成时间
        taskOrder.setProjectManager(radiationTaskOrderVO.getProjectManager());
		taskOrder.setProjectManagerTime(radiationTaskOrderVO.getProjectManagerTime());
        taskOrder.setProductManager(radiationTaskOrderVO.getProductManager());
		taskOrder.setProductManagerTime(radiationTaskOrderVO.getProductManagerTime());
		taskOrder.setTestManager(radiationTaskOrderVO.getTestManager());
		taskOrder.setTestManagerTime(radiationTaskOrderVO.getTestManagerTime());
		taskOrder.setRadiationLeader(radiationTaskOrderVO.getRadiationLeader());
		taskOrder.setRadiationLeaderTime(radiationTaskOrderVO.getRadiationLeaderTime());
		taskOrder.setCircuitName(radiationTaskOrderVO.getCircuitName());
		taskOrder.setCircuitType(radiationTaskOrderVO.getCircuitType());
		taskOrder.setSingleionsFlag(radiationTaskOrderVO.getSingleionsFlag());
		taskOrder.setTotalDoseFlag(radiationTaskOrderVO.getTotalDoseFlag());
		taskOrder.setSingleionsBatch(radiationTaskOrderVO.getSingleionsBatch());
		taskOrder.setSingleionsSmpNum(radiationTaskOrderVO.getSingleionsSmpNum());
		taskOrder.setSingleionsTest(radiationTaskOrderVO.getSingleionsTest());
		taskOrder.setTotalDoseBatch(radiationTaskOrderVO.getTotalDoseBatch());
		taskOrder.setTotalDoseSmpNum(radiationTaskOrderVO.getTotalDoseSmpNum());
		taskOrder.setTotalDoseTest(radiationTaskOrderVO.getTotalDoseTest());
		taskOrder.setMicrochipsVersion(radiationTaskOrderVO.getMicrochipsVersion());
		taskOrder.setReductionNo(radiationTaskOrderVO.getReductionNo());
		taskOrder.setSingleionsIndex(radiationTaskOrderVO.getSingleionsIndex());
		taskOrder.setTotalDoseIndex(radiationTaskOrderVO.getTotalDoseIndex());
		taskOrder.setEntrustedUnits(radiationTaskOrderVO.getEntrustedUnits());
		taskOrder.setDetailSpecification(radiationTaskOrderVO.getDetailSpecification());
		taskOrder.setDetailSpecificationStatus(radiationTaskOrderVO.getDetailSpecificationStatus());
		taskOrder.setUserUnits(radiationTaskOrderVO.getUserUnits());
		taskOrder.setWorkModel(radiationTaskOrderVO.getWorkModel());
		taskOrder.setSingleionsNo(radiationTaskOrderVO.getSingleionsNo());
		taskOrder.setSingleionsSpareNo(radiationTaskOrderVO.getSingleionsSpareNo());
		taskOrder.setTotalDoseNo(radiationTaskOrderVO.getTotalDoseNo());
		taskOrder.setTotalDoseCompareNo(radiationTaskOrderVO.getTotalDoseCompareNo());
		taskOrder.setTestSampleSplMember(radiationTaskOrderVO.getTestSampleSplMember());
		taskOrder.setTestSampleSplDate(radiationTaskOrderVO.getTestSampleSplDate());
		taskOrder.setSingleProVal(radiationTaskOrderVO.getSingleProVal());
		taskOrder.setTotalProVal(radiationTaskOrderVO.getTotalProVal());
		taskOrder.setType(radiationTaskOrderVO.getType());
		taskOrder.setTopicNoName(radiationTaskOrderVO.getTopicNoName());
		taskOrder.setUrgency(radiationTaskOrderVO.getUrgency());
		switch(memType){
      	//生产调度、有创建功能
			case DepartMember:
			case DepartManage :{
				taskOrder.setStatus(RadiationTaskOrderStatus.WAITTOCONFIRM_PRODUCTMANAGE.getValue());
				break;
			}
			case ProductManage :{
				taskOrder.setStatus(RadiationTaskOrderStatus.WAITTOCONFIRM_KANGJIAMANAGE.getValue());
				break;
			}
			case KangjiaManage:{
				taskOrder.setStatus(RadiationTaskOrderStatus.WAITTOFIX_KANGJIAMANAGE.getValue());
				break;
			}
			default:{
				break;
			}
      	}
		radiationTaskOrderDao.saveOrUpdate(taskOrder);
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
	public RadiationTaskOrder confirmNG(
			RadiationTaskOrderVO radiationTaskOrderVO) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        RadiationTaskOrder taskOrder = radiationTaskOrderDao.get(RadiationTaskOrder.class, radiationTaskOrderVO.getOrderId());
        switch(memType){
      	//生产调度、有创建功能
		case ProductManage :{
				taskOrder.setStatus(RadiationTaskOrderStatus.NOTPASS_PRODUCTMANAGE.getValue());
				break;
			}
			case KangjiaManage:{
				taskOrder.setStatus(RadiationTaskOrderStatus.NOTPASS_KANGJIAMANAGE.getValue());
				break;
			}
			default:{
				break;
			}
      	}
        radiationTaskOrderDao.saveOrUpdate(taskOrder);
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
	public RadiationTaskOrder fixComplete(
			RadiationTaskOrderVO radiationTaskOrderVO) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RadiationTaskOrder taskOrder = radiationTaskOrderDao.get(RadiationTaskOrder.class, radiationTaskOrderVO.getOrderId());
		//BeanUtilsEx.copyProperties(taskOrder, radiationTaskOrderVO);
		//希望完成时间
        taskOrder.setProjectManager(radiationTaskOrderVO.getProjectManager());
      	taskOrder.setProjectManagerTime(radiationTaskOrderVO.getProjectManagerTime());
        taskOrder.setProductManager(radiationTaskOrderVO.getProductManager());
      	taskOrder.setProductManagerTime(radiationTaskOrderVO.getProductManagerTime());
      	taskOrder.setTestManager(radiationTaskOrderVO.getTestManager());
      	taskOrder.setTestManagerTime(radiationTaskOrderVO.getTestManagerTime());
      	taskOrder.setRadiationLeader(radiationTaskOrderVO.getRadiationLeader());
      	taskOrder.setRadiationLeaderTime(radiationTaskOrderVO.getRadiationLeaderTime());
		taskOrder.setCircuitName(radiationTaskOrderVO.getCircuitName());
		taskOrder.setCircuitType(radiationTaskOrderVO.getCircuitType());
		taskOrder.setSingleionsFlag(radiationTaskOrderVO.getSingleionsFlag());
		taskOrder.setTotalDoseFlag(radiationTaskOrderVO.getTotalDoseFlag());
		taskOrder.setSingleionsBatch(radiationTaskOrderVO.getSingleionsBatch());
		taskOrder.setSingleionsSmpNum(radiationTaskOrderVO.getSingleionsSmpNum());
		taskOrder.setSingleionsTest(radiationTaskOrderVO.getSingleionsTest());
		taskOrder.setTotalDoseBatch(radiationTaskOrderVO.getTotalDoseBatch());
		taskOrder.setTotalDoseSmpNum(radiationTaskOrderVO.getTotalDoseSmpNum());
		taskOrder.setTotalDoseTest(radiationTaskOrderVO.getTotalDoseTest());
		taskOrder.setMicrochipsVersion(radiationTaskOrderVO.getMicrochipsVersion());
		taskOrder.setReductionNo(radiationTaskOrderVO.getReductionNo());
		taskOrder.setSingleionsIndex(radiationTaskOrderVO.getSingleionsIndex());
		taskOrder.setTotalDoseIndex(radiationTaskOrderVO.getTotalDoseIndex());
		taskOrder.setEntrustedUnits(radiationTaskOrderVO.getEntrustedUnits());
		taskOrder.setDetailSpecification(radiationTaskOrderVO.getDetailSpecification());
		taskOrder.setDetailSpecificationStatus(radiationTaskOrderVO.getDetailSpecificationStatus());
		taskOrder.setUserUnits(radiationTaskOrderVO.getUserUnits());
		taskOrder.setWorkModel(radiationTaskOrderVO.getWorkModel());
		taskOrder.setSingleionsNo(radiationTaskOrderVO.getSingleionsNo());
		taskOrder.setSingleionsSpareNo(radiationTaskOrderVO.getSingleionsSpareNo());
		taskOrder.setTotalDoseNo(radiationTaskOrderVO.getTotalDoseNo());
		taskOrder.setTotalDoseCompareNo(radiationTaskOrderVO.getTotalDoseCompareNo());
		taskOrder.setTestSampleSplMember(radiationTaskOrderVO.getTestSampleSplMember());
		taskOrder.setTestSampleSplDate(radiationTaskOrderVO.getTestSampleSplDate());
		taskOrder.setSingleProVal(radiationTaskOrderVO.getSingleProVal());
		taskOrder.setTotalProVal(radiationTaskOrderVO.getTotalProVal());
		taskOrder.setType(radiationTaskOrderVO.getType());
		taskOrder.setTopicNoName(radiationTaskOrderVO.getTopicNoName());
		taskOrder.setStatus(RadiationTaskOrderStatus.COMPLETED.getValue());
		taskOrder.setCompletetime(new Date());
		radiationTaskOrderDao.saveOrUpdate(taskOrder);
		//操作日志
		OperateLog opLog = new OperateLog();
		opLog.setLshId(taskOrder.getLsh());
		opLog.setContent(sessionInfo.getLoginName()+"确认完成了任务单"+taskOrder.toLogInfo());
		opLog.setMemberId(Long.valueOf(sessionInfo.getUserId()));
		opLog.setCreatetime(new Date());
		operatelogDao.save(opLog);
		return taskOrder;
	}
	@Override
	public HttpServletResponse downloadExcel(
			RadiationTaskOrderVO radiationTaskOrderVO,
			HttpServletResponse response) throws Exception {
		ExcelDateInfo excelDateInfo = new ExcelDateInfo();
		String hql = "from RadiationTaskOrder t";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(radiationTaskOrderVO, hql, params);
		hql = addOrder(radiationTaskOrderVO, hql);
		List<RadiationTaskOrder> l = radiationTaskOrderDao.find(hql, params, radiationTaskOrderVO.getPage(), radiationTaskOrderVO.getRows());
		List<RadiationTaskOrderVO> nl = new ArrayList<RadiationTaskOrderVO>();
		changeModelList(l, nl);
		editExcelInfo(excelDateInfo);
		excelDateInfo.setDateInfo(exportChangeModel(nl));
		return exportExcelService.export(excelDateInfo,response);
	}
	/**
	 * 设置导出文件的标题
	 * @param excelDateInfo
	 */
	private void editExcelInfo(ExcelDateInfo excelDateInfo){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		excelDateInfo.setFileName("RadiationtaskList_"+date);
		excelDateInfo.setSheetName("辐射任务单数据");
		excelDateInfo.setTitle("辐射任务单数据导出数据如下");
		excelDateInfo.setColumnTitle(columnTitle());
	}
	/**
	 * 明细列标题
	 * @return
	 */
	private List<String> columnTitle(){
		List<String> columnTitle = new ArrayList<String>();
		columnTitle.add("任务单号");
		columnTitle.add("任务单状态");
		//columnTitle.add("产值");
		columnTitle.add("电路名称");
		columnTitle.add("电路型号");
		columnTitle.add("课题号");
		columnTitle.add("性质");
		columnTitle.add("类别");
		columnTitle.add("生产批次");
		columnTitle.add("项目负责人");
		columnTitle.add("试验负责人");
		columnTitle.add("产值");
		columnTitle.add("开始日期");
		columnTitle.add("完成日期");
		
		columnTitle.add("单粒子委托开发测试系统");
		columnTitle.add("总剂量委托开发测试系统");
		columnTitle.add("芯片版本");
		columnTitle.add("圆片批次");
		columnTitle.add("单粒子指标");
		columnTitle.add("总剂量指标 ");
		columnTitle.add("委托监督单位");
		columnTitle.add("详细规范编号\\版本\\日期");
		columnTitle.add("详细规范状态");
		columnTitle.add("用户单位");
		columnTitle.add("工程型号");
		columnTitle.add("单例子样品编号");
		columnTitle.add("单粒子备用样品编号");
		columnTitle.add("总剂量样品编号");
		columnTitle.add("总剂量对比样品编号");
		columnTitle.add("试验样品提供人");
		columnTitle.add("试验样品提供日期");
		return columnTitle;
	}
	/**
	 * 导出数据
	 * @param consults
	 * @return
	 */
	private List<List<String>> exportChangeModel(List<RadiationTaskOrderVO> taskOrder){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<List<String>> rows = new ArrayList<List<String>>();
		List<String> columns = null;
		int sumSingleProValue = 0;//单粒子产值
		int sumTotalProValue = 0;//总剂量产值
		int sumSingleCount = 0;
		int sumTotalCount = 0;
		int sumCountVal = 0;
		for (Iterator<RadiationTaskOrderVO> iterator = taskOrder.iterator(); iterator.hasNext();) {
			RadiationTaskOrderVO task = (RadiationTaskOrderVO) iterator.next();
			columns = new ArrayList<String>();
			columns.add(task.getLsh());
			//状态名称
			columns.add(task.getStatusName());
			columns.add(task.getCircuitName());
			columns.add(task.getCircuitType());
			columns.add(task.getTopicNoName());
			columns.add(task.getTypeName());
			columns.add(task.getFlagName());
			columns.add(task.getBatch());
			columns.add(task.getProjectManager());
			columns.add(task.getTestManager());
			columns.add(task.getProVal());
			columns.add(task.getCreatetime()==null?"":sdf.format(task.getCreatetime()));
			columns.add(task.getCompletetime()==null?"":sdf.format(task.getCompletetime()));
			columns.add(task.getSingleionsTest()==1?"有":"无");
			columns.add(task.getTotalDoseTest()==1?"有":"无");
			columns.add(task.getMicrochipsVersion());
			columns.add(task.getReductionNo());
			columns.add(task.getSingleionsIndex());
			columns.add(task.getTotalDoseIndex());
			columns.add(task.getEntrustedUnits());
			columns.add(task.getDetailSpecification());
			columns.add(task.getDetailSpecificationStatus());
			columns.add(task.getUserUnits());
			columns.add(task.getWorkModel());
			columns.add(task.getSingleionsNo());
			columns.add(task.getSingleionsSpareNo());
			columns.add(task.getTotalDoseNo());
			columns.add(task.getTotalDoseCompareNo());
			columns.add(task.getTestSampleSplMember());
			columns.add(task.getTestSampleSplDate()==null?"":sdf.format(task.getTestSampleSplDate()));
			int singleProValue = task.getSingleProVal();
			int totalProValue = task.getTotalProVal();
			sumSingleProValue = sumSingleProValue + singleProValue;
			sumTotalProValue = sumTotalProValue + totalProValue;
			if(task.getSingleionsFlag()==1)
			{
				sumSingleCount = sumSingleCount+1;
			}
			if(task.getTotalDoseFlag()==1)
			{
				sumTotalCount = sumTotalCount+1;
			}
			sumCountVal = sumCountVal+1;
			rows.add(columns);
		}
		List<String> sumtitle = new ArrayList<String>();
		sumtitle.add("类别");
		sumtitle.add("数量");
		sumtitle.add("产值");
		rows.add(sumtitle);
		List<String> sumSingle = new ArrayList<String>();
		sumSingle.add("单粒子");
		sumSingle.add(String.valueOf(sumSingleCount));
		sumSingle.add(String.valueOf(sumSingleProValue));
		rows.add(sumSingle);
		List<String> sumTotal = new ArrayList<String>();
		sumTotal.add("总剂量");
		sumTotal.add(String.valueOf(sumTotalCount));
		sumTotal.add(String.valueOf(sumTotalProValue));
		rows.add(sumTotal);
		List<String> sumCount = new ArrayList<String>();
		sumCount.add("合计：");
		sumCount.add(String.valueOf(sumCountVal));
		sumCount.add(String.valueOf(sumSingleProValue+sumTotalProValue));
		rows.add(sumCount);
		return rows;
	}
	@Override
	public int getTaskNum() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
        Map<String, Object> params = new HashMap<String, Object>();
        String hql = "from RadiationTaskOrder t where 1=1 and t.status !=0 ";
        switch(memType){
	        case DepartMember :
	        case DepartManage :{
	        	hql += " and t.status in (22,24)";
	        	hql += " and t.member.memberId = :getMemberId ";
				params.put("getMemberId", Long.parseLong(sessionInfo.getUserId()));
				break;
			}
			case ProductManage:{
				hql += " and (t.status ='31')";
				break;
			}
			case KangjiaManage:{
				hql += " and t.status in (61,62)";
				break;
			}
			default:{
				hql += " and 1=2";
				break;
			}
        }
        String totalHql = "select count(*) " + hql;
        return radiationTaskOrderDao.count(totalHql,params).intValue();
	}
	@Override
	@Transactional
	public void remove(String id) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        RadiationTaskOrder taskOrder = radiationTaskOrderDao.get(RadiationTaskOrder.class, Long.valueOf(id));
        taskOrder.setStatus(0);
        radiationTaskOrderDao.update(taskOrder);
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
