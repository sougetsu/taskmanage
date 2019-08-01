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
import com.sdmx.taskmanage.dao.ILshDao;
import com.sdmx.taskmanage.dao.IOperateLogDao;
import com.sdmx.taskmanage.dao.IRadiationTaskOrderDao;
import com.sdmx.taskmanage.entity.OperateLog;
import com.sdmx.taskmanage.entity.RadiationTaskOrder;
import com.sdmx.taskmanage.service.IRadiationmdTaskOrderService;
import com.sdmx.taskmanage.vo.LogVO;
import com.sdmx.taskmanage.vo.OperatorQueryType;
import com.sdmx.taskmanage.vo.PieRate;
import com.sdmx.taskmanage.vo.RadiationTaskOrderVO;
import com.sdmx.taskmanage.vo.TaskOrderStatus;

@Service("radiationmdTaskOrderService")
public class RadiationmdTaskOrderServiceImpl implements
		IRadiationmdTaskOrderService {
	@Autowired
	private IRadiationTaskOrderDao radiationTaskOrderDao;
	@Autowired
	private IMemberDao memberDao;
	@Autowired
	private IExportService exportExcelService;
	@Autowired
	private IOperateLogDao operatelogDao;
	@Autowired
	private ILshDao lshDao;

	@Override
	public DataGrid listTaskOrder(RadiationTaskOrderVO radiationTaskOrderVO) {
		DataGrid dg = new DataGrid();
		String hql = "from RadiationTaskOrder t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(radiationTaskOrderVO, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(radiationTaskOrderVO, hql);
		List<RadiationTaskOrder> l = radiationTaskOrderDao.find(hql, params,
				radiationTaskOrderVO.getPage(), radiationTaskOrderVO.getRows());
		List<RadiationTaskOrderVO> nl = new ArrayList<RadiationTaskOrderVO>();
		changeModelList(l, nl);
		dg.setTotal(radiationTaskOrderDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}

	public List<PieRate> getPieRate() {
		List<PieRate> list = new ArrayList<PieRate>();
		String hql = "from RadiationTaskOrder t  where 1=1 and t.status !=0  and t.type=1";
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

	private String addWhere(RadiationTaskOrderVO radiationTaskOrderVO,
			String hql, Map<String, Object> params) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ResourceUtil.getSessionInfoName());
		// Member currentMember = memberDao.get(Member.class,
		// Long.parseLong(sessionInfo.getUserId()));
		hql += " where 1=1 and t.status !=0  and t.type=1";
		// 电路名称
		if (UtilValidate.isNotEmpty(radiationTaskOrderVO.getCircuitName())) {
			hql += " and t.circuitName like :circuitName";
			params.put("circuitName", "%%"
					+ radiationTaskOrderVO.getCircuitName().trim() + "%%");
		}
		// 电路型号
		if (UtilValidate.isNotEmpty(radiationTaskOrderVO.getCircuitType())) {
			hql += " and t.circuitType like :circuitType";
			params.put("circuitType", "%%"
					+ radiationTaskOrderVO.getCircuitType().trim() + "%%");
		}
		// 任务单号
		if (UtilValidate.isNotEmpty(radiationTaskOrderVO.getLsh())) {
			hql += " and t.lsh= :lshNo";
			params.put("lshNo", radiationTaskOrderVO.getLsh().trim());
		}
		// 任务单状态
		if (radiationTaskOrderVO.getOrderStatus() != 0) {
			if (radiationTaskOrderVO.getOrderStatus() == 2) {
				hql += " and t.status=51 ";
			} else {
				hql += " and t.status != 51";
			}

		}
		// 开始登记时间
		if (radiationTaskOrderVO.getRegistTimeStart() != null) {
			hql += " and t.createtime >= :regStartDate";
			params.put("regStartDate",
					radiationTaskOrderVO.getRegistTimeStart());
		}
		// 结束登记时间
		if (radiationTaskOrderVO.getRegistTimeEnd() != null) {
			hql += " and t.createtime <= :regEndDate";
			params.put("regEndDate", radiationTaskOrderVO.getRegistTimeEnd());
		}
		// 根据角色确定内容
		RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
		switch (memType) {
		case MarketManger:
		case DepartMember:
		case DepartManage: {
			// 待处理查询
			if (OperatorQueryType.SuspendingQuery.getValue().equals(
					radiationTaskOrderVO.getClType())) {
				hql += " and t.status in (22,24)";
				hql += " and t.member.memberId = :getMemberId ";
				params.put("getMemberId",
						Long.parseLong(sessionInfo.getUserId()));
			} else {
				hql += " and t.member.memberId = :getMemberId ";
				params.put("getMemberId",
						Long.parseLong(sessionInfo.getUserId()));
			}
			break;
		}
		case ProductManage: {
			// 待处理查询
			if (OperatorQueryType.SuspendingQuery.getValue().equals(
					radiationTaskOrderVO.getClType())) {
				hql += " and (t.status ='31' "
						+ " or (t.status ='23' and t.member.memberId = :getMemberId))";
				params.put("getMemberId",
						Long.parseLong(sessionInfo.getUserId()));
			}
			break;
		}
		case KangjiaManage: {
			// 待处理查询
			if (OperatorQueryType.SuspendingQuery.getValue().equals(
					radiationTaskOrderVO.getClType())) {
				hql += " and t.status in (61,62)";
			}
			break;
		}
		default: {
			break;
		}
		}

		return hql;
	}

	private String addOrder(RadiationTaskOrderVO radiationTaskOrderVO,
			String hql) {
		if (radiationTaskOrderVO.getSort() != null) {
			if ("statusName".equals(radiationTaskOrderVO.getSort())) {
				hql += " order by t.status " + radiationTaskOrderVO.getOrder();
			} else if ("circuitName".equals(radiationTaskOrderVO.getSort())) {
				hql += " order by t.circuitName "
						+ radiationTaskOrderVO.getOrder();
			} else if ("microchipsVersion".equals(radiationTaskOrderVO
					.getSort())) {
				hql += " order by t.microchipsVersion "
						+ radiationTaskOrderVO.getOrder();
			} else {
				hql += " order by " + radiationTaskOrderVO.getSort() + " "
						+ radiationTaskOrderVO.getOrder();
			}
		} else {
			hql += " order by t.createtime desc";
		}
		return hql;
	}

	/**
	 * 根据实体转化页面展示项(集合)
	 * 
	 * @param l
	 * @param nl
	 */
	private void changeModelList(List<RadiationTaskOrder> l,
			List<RadiationTaskOrderVO> nl) {
		if (l != null && l.size() > 0) {
			for (Iterator<RadiationTaskOrder> iterator = l.iterator(); iterator
					.hasNext();) {
				RadiationTaskOrder taskOrder = (RadiationTaskOrder) iterator
						.next();
				RadiationTaskOrderVO taskOrderVO = getDtoData(taskOrder);
				nl.add(taskOrderVO);
			}
		}
	}

	private RadiationTaskOrderVO getDtoData(RadiationTaskOrder taskOrder) {
		RadiationTaskOrderVO taskOrderVO = new RadiationTaskOrderVO();
		BeanUtilsEx.copyProperties(taskOrderVO, taskOrder);

		for (TaskOrderStatus type : TaskOrderStatus.values()) {
			if (taskOrder.getStatus() == type.getValue()) {
				taskOrderVO.setStatusName(type.getLabel());
			}
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ResourceUtil.getSessionInfoName());
		RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
		// 如果是部门待确认状态，任务单创建者具有确认权限
		if (taskOrder.getStatus() == TaskOrderStatus.WAITTOFIX_DEPARTMANAGE
				.getValue()) {
			if (String.valueOf(taskOrder.getMember().getMemberId()).equals(
					sessionInfo.getUserId())) {
				taskOrderVO.setFixState(1);
			} else {
				taskOrderVO.setFixState(0);
			}
		}
		// 设置审核权限和核价，修改权限
		switch (memType) {
		// 只有生产调度，抗加有审核功能
		case DepartMember:
		case DepartManage: {
			if (taskOrder.getStatus() == TaskOrderStatus.NOTPASS_PRODUCTMANAGE
					.getValue()
					|| taskOrder.getStatus() == TaskOrderStatus.NOTPASS_KANGJIAMANAGE
							.getValue()) {
				taskOrderVO.setEditState(1);
			}
			break;
		}
		case ProductManage: {
			if (taskOrder.getStatus() == TaskOrderStatus.WAITTOCONFIRM_PRODUCTMANAGE
					.getValue()) {
				taskOrderVO.setConfirmState(1);
			}
			taskOrderVO.setEditState(1);
			taskOrderVO.setDeleteState(1);
			taskOrderVO.setUrgencyState(1);
			break;
		}
		case KangjiaManage: {
			if (taskOrder.getStatus() == TaskOrderStatus.WAITTOCONFIRM_KANGJIAMANAGE
					.getValue()) {
				taskOrderVO.setConfirmState(1);
			}
			if (taskOrder.getStatus() == TaskOrderStatus.WAITTOFIX_KANGJIAMANAGE
					.getValue()) {
				taskOrderVO.setFixState(1);
			}
			break;
		}
		default: {
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
		List<RadiationTaskOrder> l = radiationTaskOrderDao.find(hql, params,
				radiationTaskOrderVO.getPage(), radiationTaskOrderVO.getRows());
		List<RadiationTaskOrderVO> nl = new ArrayList<RadiationTaskOrderVO>();
		changeModelList(l, nl);
		dg.setTotal(radiationTaskOrderDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}

	@Override
	@Transactional
	public RadiationTaskOrder create(RadiationTaskOrderVO radiationTaskOrderVO) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ResourceUtil.getSessionInfoName());
		RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
		Member currentMember = memberDao.get(Member.class,
				Long.parseLong(sessionInfo.getUserId()));
		RadiationTaskOrder taskOrder = new RadiationTaskOrder();
		BeanUtilsEx.copyProperties(taskOrder, radiationTaskOrderVO);
		// 希望完成时间
		taskOrder.setTestSampleSplDate(radiationTaskOrderVO
				.getTestSampleSplDate());
		taskOrder.setCreatetime(new Date());
		taskOrder.setType(1);
		switch (memType) {
		// 只有部门员工，部门调度，生产调度有创建功能
		case DepartMember:
		case DepartManage: {
			taskOrder.setStatus(TaskOrderStatus.WAITTOCONFIRM_PRODUCTMANAGE
					.getValue());
			break;
		}
		case ProductManage: {
			taskOrder.setStatus(TaskOrderStatus.WAITTOCONFIRM_KANGJIAMANAGE
					.getValue());
			break;
		}
		default: {
			break;
		}
		}
		taskOrder.setMember(currentMember);
		// 生成流水号
		taskOrder.setLsh(lshDao.getLsh());

		radiationTaskOrderDao.save(taskOrder);
		OperateLog opLog = new OperateLog();
		opLog.setLshId(taskOrder.getLsh());
		opLog.setContent(sessionInfo.getLoginName() + "创建了任务单");
		opLog.setMemberId(currentMember.getMemberId());
		opLog.setCreatetime(new Date());
		operatelogDao.save(opLog);
		return taskOrder;
	}

	@Override
	public RadiationTaskOrderVO getTaskOrderById(String taskId) {
		RadiationTaskOrder radiationTaskOrder = radiationTaskOrderDao.get(
				RadiationTaskOrder.class, Long.parseLong(taskId));
		RadiationTaskOrderVO radiationTaskOrderVO = getDtoData(radiationTaskOrder);
		return radiationTaskOrderVO;
	}

	@Override
	@Transactional
	public RadiationTaskOrder confirmOK(
			RadiationTaskOrderVO radiationTaskOrderVO) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ResourceUtil.getSessionInfoName());
		RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
		RadiationTaskOrder taskOrder = radiationTaskOrderDao.get(
				RadiationTaskOrder.class, radiationTaskOrderVO.getOrderId());
		// BeanUtilsEx.copyProperties(taskOrder, radiationTaskOrderVO);
		// 希望完成时间
		taskOrder.setTestSampleSplDate(radiationTaskOrderVO
				.getTestSampleSplDate());
		taskOrder.setCircuitName(radiationTaskOrderVO.getCircuitName());
		taskOrder.setCircuitType(radiationTaskOrderVO.getCircuitType());
		taskOrder.setSingleionsFlag(radiationTaskOrderVO.getSingleionsFlag());
		taskOrder.setTotalDoseFlag(radiationTaskOrderVO.getTotalDoseFlag());
		taskOrder.setSingleionsBatch(radiationTaskOrderVO.getSingleionsBatch());
		taskOrder.setSingleionsSmpNum(radiationTaskOrderVO
				.getSingleionsSmpNum());
		taskOrder.setSingleionsTest(radiationTaskOrderVO.getSingleionsTest());
		taskOrder.setTotalDoseBatch(radiationTaskOrderVO.getTotalDoseBatch());
		taskOrder.setTotalDoseSmpNum(radiationTaskOrderVO.getTotalDoseSmpNum());
		taskOrder.setTotalDoseTest(radiationTaskOrderVO.getTotalDoseTest());
		taskOrder.setMicrochipsVersion(radiationTaskOrderVO
				.getMicrochipsVersion());
		taskOrder.setReductionNo(radiationTaskOrderVO.getReductionNo());
		taskOrder.setSingleionsIndex(radiationTaskOrderVO.getSingleionsIndex());
		taskOrder.setTotalDoseIndex(radiationTaskOrderVO.getTotalDoseIndex());
		taskOrder.setEntrustedUnits(radiationTaskOrderVO.getEntrustedUnits());
		taskOrder.setDetailSpecification(radiationTaskOrderVO
				.getDetailSpecification());
		taskOrder.setDetailSpecificationStatus(radiationTaskOrderVO
				.getDetailSpecificationStatus());
		taskOrder.setUserUnits(radiationTaskOrderVO.getUserUnits());
		taskOrder.setWorkModel(radiationTaskOrderVO.getWorkModel());
		taskOrder.setSingleionsNo(radiationTaskOrderVO.getSingleionsNo());
		taskOrder.setSingleionsSpareNo(radiationTaskOrderVO
				.getSingleionsSpareNo());
		taskOrder.setTotalDoseNo(radiationTaskOrderVO.getTotalDoseNo());
		taskOrder.setTotalDoseCompareNo(radiationTaskOrderVO
				.getTotalDoseCompareNo());
		taskOrder.setTestSampleSplMember(radiationTaskOrderVO
				.getTestSampleSplMember());
		taskOrder.setTestSampleSplDate(radiationTaskOrderVO
				.getTestSampleSplDate());
		taskOrder.setUrgency(radiationTaskOrderVO.getUrgency());
		//taskOrder.setProVal(radiationTaskOrderVO.getProVal());
		switch (memType) {
		// 生产调度、有创建功能
		case DepartMember:
		case DepartManage: {
			taskOrder.setStatus(TaskOrderStatus.WAITTOCONFIRM_PRODUCTMANAGE
					.getValue());
			break;
		}
		case ProductManage: {
			taskOrder.setStatus(TaskOrderStatus.WAITTOCONFIRM_KANGJIAMANAGE
					.getValue());
			break;
		}
		case KangjiaManage: {
			taskOrder.setStatus(TaskOrderStatus.WAITTOFIX_KANGJIAMANAGE
					.getValue());
			break;
		}
		default: {
			break;
		}
		}
		radiationTaskOrderDao.saveOrUpdate(taskOrder);
		// 操作日志
		OperateLog opLog = new OperateLog();
		opLog.setLshId(taskOrder.getLsh());
		opLog.setContent(sessionInfo.getLoginName() + "审核通过了任务单"
				+ taskOrder.toLogInfo());
		opLog.setMemberId(Long.valueOf(sessionInfo.getUserId()));
		opLog.setCreatetime(new Date());
		operatelogDao.save(opLog);
		return taskOrder;

	}

	@Override
	@Transactional
	public RadiationTaskOrder confirmNG(
			RadiationTaskOrderVO radiationTaskOrderVO) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ResourceUtil.getSessionInfoName());
		RoleType memType = RoleType.getType(sessionInfo.getRoleNames());
		RadiationTaskOrder taskOrder = radiationTaskOrderDao.get(
				RadiationTaskOrder.class, radiationTaskOrderVO.getOrderId());
		switch (memType) {
		// 生产调度、有创建功能
		case ProductManage: {
			taskOrder.setStatus(TaskOrderStatus.NOTPASS_PRODUCTMANAGE
					.getValue());
			break;
		}
		case KangjiaManage: {
			taskOrder.setStatus(TaskOrderStatus.NOTPASS_KANGJIAMANAGE
					.getValue());
			break;
		}
		default: {
			break;
		}
		}
		radiationTaskOrderDao.saveOrUpdate(taskOrder);
		// 操作日志
		OperateLog opLog = new OperateLog();
		opLog.setLshId(taskOrder.getLsh());
		opLog.setContent(sessionInfo.getLoginName() + "审核未通过任务单"
				+ taskOrder.toLogInfo());
		opLog.setMemberId(Long.valueOf(sessionInfo.getUserId()));
		opLog.setCreatetime(new Date());
		operatelogDao.save(opLog);
		return taskOrder;
	}

	@Override
	@Transactional
	public RadiationTaskOrder fixComplete(
			RadiationTaskOrderVO radiationTaskOrderVO) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ResourceUtil.getSessionInfoName());
		RadiationTaskOrder taskOrder = radiationTaskOrderDao.get(
				RadiationTaskOrder.class, radiationTaskOrderVO.getOrderId());
		// BeanUtilsEx.copyProperties(taskOrder, radiationTaskOrderVO);
		// 希望完成时间
		taskOrder.setTestSampleSplDate(radiationTaskOrderVO
				.getTestSampleSplDate());
		taskOrder.setCircuitName(radiationTaskOrderVO.getCircuitName());
		taskOrder.setCircuitType(radiationTaskOrderVO.getCircuitType());
		taskOrder.setSingleionsFlag(radiationTaskOrderVO.getSingleionsFlag());
		taskOrder.setTotalDoseFlag(radiationTaskOrderVO.getTotalDoseFlag());
		taskOrder.setSingleionsBatch(radiationTaskOrderVO.getSingleionsBatch());
		taskOrder.setSingleionsSmpNum(radiationTaskOrderVO
				.getSingleionsSmpNum());
		taskOrder.setSingleionsTest(radiationTaskOrderVO.getSingleionsTest());
		taskOrder.setTotalDoseBatch(radiationTaskOrderVO.getTotalDoseBatch());
		taskOrder.setTotalDoseSmpNum(radiationTaskOrderVO.getTotalDoseSmpNum());
		taskOrder.setTotalDoseTest(radiationTaskOrderVO.getTotalDoseTest());
		taskOrder.setMicrochipsVersion(radiationTaskOrderVO
				.getMicrochipsVersion());
		taskOrder.setReductionNo(radiationTaskOrderVO.getReductionNo());
		taskOrder.setSingleionsIndex(radiationTaskOrderVO.getSingleionsIndex());
		taskOrder.setTotalDoseIndex(radiationTaskOrderVO.getTotalDoseIndex());
		taskOrder.setEntrustedUnits(radiationTaskOrderVO.getEntrustedUnits());
		taskOrder.setDetailSpecification(radiationTaskOrderVO
				.getDetailSpecification());
		taskOrder.setDetailSpecificationStatus(radiationTaskOrderVO
				.getDetailSpecificationStatus());
		taskOrder.setUserUnits(radiationTaskOrderVO.getUserUnits());
		taskOrder.setWorkModel(radiationTaskOrderVO.getWorkModel());
		taskOrder.setSingleionsNo(radiationTaskOrderVO.getSingleionsNo());
		taskOrder.setSingleionsSpareNo(radiationTaskOrderVO
				.getSingleionsSpareNo());
		taskOrder.setTotalDoseNo(radiationTaskOrderVO.getTotalDoseNo());
		taskOrder.setTotalDoseCompareNo(radiationTaskOrderVO
				.getTotalDoseCompareNo());
		taskOrder.setTestSampleSplMember(radiationTaskOrderVO
				.getTestSampleSplMember());
		taskOrder.setTestSampleSplDate(radiationTaskOrderVO
				.getTestSampleSplDate());
		//taskOrder.setProVal(radiationTaskOrderVO.getProVal());
		taskOrder.setStatus(TaskOrderStatus.COMPLETED.getValue());
		radiationTaskOrderDao.saveOrUpdate(taskOrder);
		// 操作日志
		OperateLog opLog = new OperateLog();
		opLog.setLshId(taskOrder.getLsh());
		opLog.setContent(sessionInfo.getLoginName() + "确认完成了任务单"
				+ taskOrder.toLogInfo());
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
		String hql = "from RadiationTaskOrder t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(radiationTaskOrderVO, hql, params);
		hql = addOrder(radiationTaskOrderVO, hql);
		List<RadiationTaskOrder> l = radiationTaskOrderDao.find(hql, params,
				radiationTaskOrderVO.getPage(), radiationTaskOrderVO.getRows());

		editExcelInfo(excelDateInfo);
		excelDateInfo.setDateInfo(exportChangeModel(l));
		return exportExcelService.export(excelDateInfo, response);
	}

	/**
	 * 设置导出文件的标题
	 * 
	 * @param excelDateInfo
	 */
	private void editExcelInfo(ExcelDateInfo excelDateInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		excelDateInfo.setFileName("RadiationtaskList_" + date);
		excelDateInfo.setSheetName("辐射任务单数据");
		excelDateInfo.setTitle("辐射任务单数据导出数据如下");
		excelDateInfo.setColumnTitle(columnTitle());
	}

	/**
	 * 明细列标题
	 * 
	 * @return
	 */
	private List<String> columnTitle() {
		List<String> columnTitle = new ArrayList<String>();
		columnTitle.add("任务单号");
		columnTitle.add("产值");
		columnTitle.add("电路名称");
		columnTitle.add("电路型号");
		columnTitle.add("单粒子 ");
		columnTitle.add("单粒子生产批次");
		columnTitle.add("单粒子样品数量");
		columnTitle.add("单粒子委托开发测试系统");
		columnTitle.add("总剂量 ");
		columnTitle.add("总剂量生产批次");
		columnTitle.add("总剂量样品数量");
		columnTitle.add("总剂量委托开发测试系统");
		columnTitle.add("芯片版本");
		columnTitle.add("圆片批次");
		columnTitle.add("单粒子指标");
		columnTitle.add("总剂量指标 ");
		columnTitle.add("详细规范编号\\版本\\日期");
		columnTitle.add("详细规范状态");
		columnTitle.add("单例子样品编号");
		columnTitle.add("单粒子备用样品编号");
		columnTitle.add("总剂量样品编号");
		columnTitle.add("总剂量对比样品编号");
		columnTitle.add("试验样品提供人");
		columnTitle.add("试验样品提供日期");
		columnTitle.add("任务单状态");
		columnTitle.add("创建人");
		columnTitle.add("创建时间");
		return columnTitle;
	}

	/**
	 * 导出数据
	 * 
	 * @param consults
	 * @return
	 */
	private List<List<String>> exportChangeModel(
			List<RadiationTaskOrder> taskOrder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<List<String>> rows = new ArrayList<List<String>>();
		List<String> columns = null;
		int sumProValue = 0;
		for (Iterator<RadiationTaskOrder> iterator = taskOrder.iterator(); iterator
				.hasNext();) {
			RadiationTaskOrder task = (RadiationTaskOrder) iterator.next();
			columns = new ArrayList<String>();
			columns.add(task.getLsh());
			//columns.add(task.getProVal() == null ? "0" : String.valueOf(task
			//		.getProVal()));
			columns.add(task.getCircuitName());
			columns.add(task.getCircuitType());
			columns.add(task.getSingleionsFlag() == 1 ? "有" : "无");
			columns.add(task.getTotalDoseFlag() == 1 ? "有" : "无");
			columns.add(task.getSingleionsBatch());
			columns.add(task.getSingleionsSmpNum());
			columns.add(task.getSingleionsTest() == 1 ? "有" : "无");
			columns.add(task.getTotalDoseBatch());
			columns.add(task.getTotalDoseSmpNum());
			columns.add(task.getTotalDoseTest() == 1 ? "有" : "无");
			columns.add(task.getMicrochipsVersion());
			columns.add(task.getReductionNo());
			columns.add(task.getSingleionsIndex());
			columns.add(task.getTotalDoseIndex());
			columns.add(task.getDetailSpecification());
			columns.add(task.getDetailSpecificationStatus());
			columns.add(task.getSingleionsNo());
			columns.add(task.getSingleionsSpareNo());
			columns.add(task.getTotalDoseNo());
			columns.add(task.getTotalDoseCompareNo());
			columns.add(task.getTestSampleSplMember());
			columns.add(task.getTestSampleSplDate() == null ? "" : sdf
					.format(task.getTestSampleSplDate()));
			//int proValue = task.getProVal() == null ? 0 : task.getProVal();
			//sumProValue = sumProValue + proValue;
			// 状态名称
			String statusName = "";
			for (TaskOrderStatus type : TaskOrderStatus.values()) {
				if (task.getStatus() == type.getValue()) {
					statusName = type.getLabel();
				}
			}
			columns.add(statusName);
			columns.add(task.getMember().getRealName());
			columns.add(task.getCreatetime() == null ? "" : sdf.format(task
					.getCreatetime()));
			rows.add(columns);
		}
		List<String> sumColumns = new ArrayList<String>();
		sumColumns.add("产值合计：");
		sumColumns.add(String.valueOf(sumProValue));

		rows.add(sumColumns);
		return rows;
	}

	@Override
	public int getTaskNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional
	public void remove(String id) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ResourceUtil.getSessionInfoName());
		RadiationTaskOrder taskOrder = radiationTaskOrderDao.get(
				RadiationTaskOrder.class, Long.valueOf(id));
		taskOrder.setStatus(0);
		radiationTaskOrderDao.update(taskOrder);
		// 操作日志
		OperateLog opLog = new OperateLog();
		opLog.setLshId(taskOrder.getLsh());
		opLog.setContent(sessionInfo.getLoginName() + "删除了任务单" + id);
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
