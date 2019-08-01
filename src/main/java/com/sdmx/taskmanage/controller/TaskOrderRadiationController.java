package com.sdmx.taskmanage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sdmx.framework.util.DateUtil;
import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.util.doc.MDoc;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.JsonResult;
import com.sdmx.taskmanage.service.IRadiationTaskOrderService;
import com.sdmx.taskmanage.vo.LogVO;
import com.sdmx.taskmanage.vo.MonthChart;
import com.sdmx.taskmanage.vo.OperatorQueryType;
import com.sdmx.taskmanage.vo.PieRate;
import com.sdmx.taskmanage.vo.RadiationTaskOrderVO;
import com.sdmx.taskmanage.vo.TaskOrderStatus;

@Controller
@RequestMapping(value = "/taskManageRadiation")
public class TaskOrderRadiationController {

	@Autowired
	private IRadiationTaskOrderService radiationTaskOrderService;
	
	/**
	 * 日期处理
	 * @param binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	/**
	 * 新增任务单
	 * 
	 * @param radiationTaskOrderVO
	 * @return
	 */
	@RequestMapping(value = "/create")
	@ResponseBody
	public JsonResult create(RadiationTaskOrderVO radiationTaskOrderVO,
			BindingResult errors) {
		// 验证任务单信息输入
		if (errors.hasErrors()) {
			String code = errors.getFieldError().getCode();
			String msg = errors.getFieldError().getDefaultMessage();// 出错的信息
			return JsonResult.failed(msg, code);
		}
		radiationTaskOrderService.create(radiationTaskOrderVO);
		return JsonResult.success("新增任务单成功！", radiationTaskOrderVO);
	}
	
	/**
	 * 获取分类
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSumResult")
	@ResponseBody
	public JsonResult getSumResult() {
		List<PieRate> pierate = radiationTaskOrderService.getPieRate();
		
		return JsonResult.success("成功！", pierate);
	}
	/**
	 * 获取分类
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSumByMonth")
	@ResponseBody
	public JsonResult getSumByMonth() {
		List<MonthChart> pierate = radiationTaskOrderService.getSumByMonth();
		
		return JsonResult.success("成功！", pierate);
	}
	
	/**
	 * 审核通过任务单
	 * 
	 * @param radiationTaskOrderVO
	 * @return
	 */
	@RequestMapping(value = "/confirmOK")
	@ResponseBody
	public JsonResult confirmOK(RadiationTaskOrderVO radiationTaskOrderVO,
			BindingResult errors) {
		// 验证任务单信息输入
		if (errors.hasErrors()) {
			String code = errors.getFieldError().getCode();
			String msg = errors.getFieldError().getDefaultMessage();// 出错的信息
			return JsonResult.failed(msg, code);
		}
		radiationTaskOrderService.confirmOK(radiationTaskOrderVO);
		return JsonResult.success("操作成功！", radiationTaskOrderVO);
	}
	
	/**
	 * 修改后提交
	 * 
	 * @param radiationTaskOrderVO
	 * @return
	 */
	@RequestMapping(value = "/editSubmit")
	@ResponseBody
	public JsonResult editSubmit(RadiationTaskOrderVO radiationTaskOrderVO,
			BindingResult errors) {
		// 验证任务单信息输入
		if (errors.hasErrors()) {
			String code = errors.getFieldError().getCode();
			String msg = errors.getFieldError().getDefaultMessage();// 出错的信息
			return JsonResult.failed(msg, code);
		}
		radiationTaskOrderService.confirmOK(radiationTaskOrderVO);
		return JsonResult.success("操作成功！", radiationTaskOrderVO);
	}
	
	/**
	 * 审核不通过
	 * 
	 * @param radiationTaskOrderVO
	 * @return
	 */
	@RequestMapping(value = "/confirmNG")
	@ResponseBody
	public JsonResult confirmNG(RadiationTaskOrderVO radiationTaskOrderVO) {
		radiationTaskOrderService.confirmNG(radiationTaskOrderVO);
		return JsonResult.success("操作成功！", radiationTaskOrderVO);
	}
	
	
	/**
	 * 确认完成
	 * 
	 * @param radiationTaskOrderVO
	 * @return
	 */
	@RequestMapping(value = "/fixComplete")
	@ResponseBody
	public JsonResult fixComplete(RadiationTaskOrderVO radiationTaskOrderVO) {
		radiationTaskOrderService.fixComplete(radiationTaskOrderVO);
		return JsonResult.success("操作成功！", radiationTaskOrderVO);
	}
	
	/**
	 * 任务单列表查询
	 * 
	 * @param radiationTaskOrderVO
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public DataGrid listTaskOrder(RadiationTaskOrderVO radiationTaskOrderVO) {
		return radiationTaskOrderService.listTaskOrder(radiationTaskOrderVO);
	}
	
	/**
	 * 任务单待处理列表查询
	 * 
	 * @param radiationTaskOrderVO
	 * @return
	 */
	@RequestMapping(value = "/susList")
	@ResponseBody
	public DataGrid listSusTaskOrder(RadiationTaskOrderVO radiationTaskOrderVO) {
		radiationTaskOrderVO.setClType(OperatorQueryType.SuspendingQuery.getValue());
		return radiationTaskOrderService.listSusTaskOrder(radiationTaskOrderVO);
	}
	
	/**
	 * 查询-详细页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/detailPage")
	public ModelAndView detailPage(String id, HttpServletRequest request) {
		RadiationTaskOrderVO tInfo = radiationTaskOrderService.getTaskOrderById(id);
		request.setAttribute("taskOrder", tInfo);
		return new ModelAndView("/taskmanageradiation/taskRadiationDetail");
	}
	
	/**
	 * 查询-审核页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/confirmPage")
	public ModelAndView confirmPage(String id, HttpServletRequest request) {
		RadiationTaskOrderVO tInfo = radiationTaskOrderService.getTaskOrderById(id);
		request.setAttribute("taskOrder", tInfo);
		return new ModelAndView("/taskmanageradiation/taskRadiationConfirm");
	}
	
	/**
	 * 查询-修改页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/editPage")
	public ModelAndView editPage(String id, HttpServletRequest request) {
		RadiationTaskOrderVO tInfo = radiationTaskOrderService.getTaskOrderById(id);
		request.setAttribute("taskOrder", tInfo);
		return new ModelAndView("/taskmanageradiation/taskRadiationEdit");
	}
	
	/**
	 * 查询-确认页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/fixPage")
	public ModelAndView fixPage(String id, HttpServletRequest request) {
		RadiationTaskOrderVO tInfo = radiationTaskOrderService.getTaskOrderById(id);
		if(tInfo.getStatus() == TaskOrderStatus.COMPLETED.getValue() ||
			tInfo.getStatus() == TaskOrderStatus.WAITTOFIX_TESTCENTERMANAGE.getValue() ||
			tInfo.getStatus() == TaskOrderStatus.WAITTOFIX_DEPARTMANAGE.getValue()){
		}
		request.setAttribute("taskOrder", tInfo);
		return new ModelAndView("/taskmanageradiation/taskRadiationFix");
	}
	
	/**
	 * 查询数据导出
	 * @param consult
	 * @return
	 */
	@RequestMapping(value = "/downloadExcel")
    public void downloadExcel(RadiationTaskOrderVO radiationTaskOrderVO,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response = radiationTaskOrderService.downloadExcel(radiationTaskOrderVO, response);
    }
	


	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping("/attached")
	public void attached(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, String filePath) {
		filePath = ResourceUtil.getUploadPath()
				+ ResourceUtil.getUploadDirectory() + "/" + filePath;// 文件保存目录路径
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1,
				filePath.length());
		File uploadedFile = new File(filePath);
		if (uploadedFile.exists()) {
			try {
				response.setContentType(this.getContentType(fileName));
				response.setHeader("Content-disposition",
						"attachment;filename=" + fileName);
				OutputStream out = response.getOutputStream();
				FileInputStream in = new FileInputStream(uploadedFile);
				try {
					byte[] buf = new byte[512];
					int len = 0;
					while ((len = in.read(buf)) != -1) {
						out.write(buf, 0, len);
					}
				} finally {
					in.close();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置文件类型
	 * 
	 * @param fileName
	 * @return
	 */
	private String getContentType(String fileName) {
		String fileNameTmp = fileName.toLowerCase();
		String ret = "";
		if (fileNameTmp.endsWith("txt")) {
			ret = "text/plain";
		}
		if (fileNameTmp.endsWith("gif")) {
			ret = "image/gif";
		}
		if (fileNameTmp.endsWith("jpg")) {
			ret = "image/jpeg";
		}
		if (fileNameTmp.endsWith("jpeg")) {
			ret = "image/jpeg";
		}
		if (fileNameTmp.endsWith("jpe")) {
			ret = "image/jpeg";
		}
		if (fileNameTmp.endsWith("zip")) {
			ret = "application/zip";
		}
		if (fileNameTmp.endsWith("rar")) {
			ret = "application/rar";
		}
		if (fileNameTmp.endsWith("doc")) {
			ret = "application/msword";
		}
		if (fileNameTmp.endsWith("ppt")) {
			ret = "application/vnd.ms-powerpoint";
		}
		if (fileNameTmp.endsWith("xls")) {
			ret = "application/vnd.ms-excel";
		}
		if (fileNameTmp.endsWith("html")) {
			ret = "text/html";
		}
		if (fileNameTmp.endsWith("htm")) {
			ret = "text/html";
		}
		if (fileNameTmp.endsWith("tif")) {
			ret = "image/tiff";
		}
		if (fileNameTmp.endsWith("tiff")) {
			ret = "image/tiff";
		}
		if (fileNameTmp.endsWith("pdf")) {
			ret = "application/pdf";
		}
		return ret;
	}
	/**
	 * 任务单删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/removeTaskOrder")
	@ResponseBody
	public JsonResult radiationTaskOrderService(String id) {
		radiationTaskOrderService.remove(id);
		return JsonResult.success("删除成功！", id);
	}
	/**
	 * 任务单日志查询
	 * 
	 * @param radiationTaskOrderVO
	 * @return
	 */
	@RequestMapping(value = "/logList")
	@ResponseBody
	public DataGrid logList(LogVO logVO) {
		return radiationTaskOrderService.logList(logVO);
	}
	
	
	/**
	 * 查询数据word导出
	 * @param consult
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/exportWord")
    public void exportWord(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,RadiationTaskOrderVO radiationTaskOrderVO) throws Exception{
			RadiationTaskOrderVO radiationTaskOrder = radiationTaskOrderService.getTaskOrderById(String.valueOf(radiationTaskOrderVO.getOrderId()));	
			String filePath = createWordByVO(radiationTaskOrder);
			File uploadedFile = new File(filePath);
			if (uploadedFile.exists()) {
				try {
					response.setContentType(this.getContentType(filePath));
					response.setHeader("Content-disposition",
							"attachment;filename="+radiationTaskOrder.getLsh()+".doc");
					OutputStream out = response.getOutputStream();
					FileInputStream in = new FileInputStream(uploadedFile);
					try {
						byte[] buf = new byte[512];
						int len = 0;
						while ((len = in.read(buf)) != -1) {
							out.write(buf, 0, len);
						}
					} finally {
						in.close();
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
    }
	private String createWordByVO(RadiationTaskOrderVO ersaiTaskOrderVO) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("createtime", UtilValidate.isEmpty(ersaiTaskOrderVO.getCreatetime())?"":DateUtil.formatYMD(ersaiTaskOrderVO.getCreatetime()));
		dataMap.put("lsh", UtilValidate.isEmpty(ersaiTaskOrderVO.getLsh())?"":ersaiTaskOrderVO.getLsh());
		dataMap.put("typeName", UtilValidate.isEmpty(ersaiTaskOrderVO.getTypeName())?"":ersaiTaskOrderVO.getTypeName());
		dataMap.put("topicNoName", UtilValidate.isEmpty(ersaiTaskOrderVO.getTopicNoName())?"":ersaiTaskOrderVO.getTopicNoName());
		dataMap.put("circuitName", UtilValidate.isEmpty(ersaiTaskOrderVO.getCircuitName())?"":ersaiTaskOrderVO.getCircuitName());
		dataMap.put("circuitType", UtilValidate.isEmpty(ersaiTaskOrderVO.getCircuitType())?"":ersaiTaskOrderVO.getCircuitType());
		dataMap.put("dlz", ersaiTaskOrderVO.getSingleionsFlag()==1?"是":"无");
		dataMap.put("all", ersaiTaskOrderVO.getTotalDoseFlag()==1?"是":"无");
		dataMap.put("singleionsBatch", UtilValidate.isEmpty(ersaiTaskOrderVO.getSingleionsBatch())?"":ersaiTaskOrderVO.getSingleionsBatch());
		dataMap.put("singleionsNum", UtilValidate.isEmpty(ersaiTaskOrderVO.getSingleionsSmpNum())?"":ersaiTaskOrderVO.getSingleionsSmpNum());
		dataMap.put("singleionsTest", ersaiTaskOrderVO.getSingleionsTest()==1?"有":"无");
		dataMap.put("totalDoseBatch", UtilValidate.isEmpty(ersaiTaskOrderVO.getTotalDoseBatch())?"":ersaiTaskOrderVO.getTotalDoseBatch());
		dataMap.put("totalDoseNum", UtilValidate.isEmpty(ersaiTaskOrderVO.getTotalDoseSmpNum())?"":ersaiTaskOrderVO.getTotalDoseSmpNum());
		dataMap.put("totalDoseTest", ersaiTaskOrderVO.getTotalDoseTest()==1?"有":"无");
		dataMap.put("microchipsVersion", UtilValidate.isEmpty(ersaiTaskOrderVO.getMicrochipsVersion())?"":ersaiTaskOrderVO.getMicrochipsVersion());
		dataMap.put("reductionNo", UtilValidate.isEmpty(ersaiTaskOrderVO.getReductionNo())?"":ersaiTaskOrderVO.getReductionNo());
		dataMap.put("singleionsIndex", UtilValidate.isEmpty(ersaiTaskOrderVO.getSingleionsIndex())?"":ersaiTaskOrderVO.getSingleionsIndex());
		dataMap.put("totalDoseIndex", UtilValidate.isEmpty(ersaiTaskOrderVO.getTotalDoseIndex())?"":ersaiTaskOrderVO.getTotalDoseIndex());
		dataMap.put("entrustedUnits", UtilValidate.isEmpty(ersaiTaskOrderVO.getEntrustedUnits())?"":ersaiTaskOrderVO.getEntrustedUnits());
		dataMap.put("detailSpecification", UtilValidate.isEmpty(ersaiTaskOrderVO.getDetailSpecification())?"":ersaiTaskOrderVO.getDetailSpecification());
		dataMap.put("detailSpecificationStatus", UtilValidate.isEmpty(ersaiTaskOrderVO.getDetailSpecificationStatus())?"":ersaiTaskOrderVO.getDetailSpecificationStatus());
		dataMap.put("userUnits", UtilValidate.isEmpty(ersaiTaskOrderVO.getUserUnits())?"":ersaiTaskOrderVO.getUserUnits());
		dataMap.put("workModel", UtilValidate.isEmpty(ersaiTaskOrderVO.getWorkModel())?"":ersaiTaskOrderVO.getWorkModel());
		dataMap.put("singleionsNo", UtilValidate.isEmpty(ersaiTaskOrderVO.getSingleionsNo())?"":ersaiTaskOrderVO.getSingleionsNo());
		dataMap.put("singleionsSpareNo", UtilValidate.isEmpty(ersaiTaskOrderVO.getSingleionsSpareNo())?"":ersaiTaskOrderVO.getSingleionsSpareNo());
		dataMap.put("totalDoseNo", UtilValidate.isEmpty(ersaiTaskOrderVO.getTotalDoseNo())?"":ersaiTaskOrderVO.getTotalDoseNo());
		dataMap.put("totalDoseCompareNo", UtilValidate.isEmpty(ersaiTaskOrderVO.getTotalDoseCompareNo())?"":ersaiTaskOrderVO.getTotalDoseCompareNo());
		dataMap.put("member", UtilValidate.isEmpty(ersaiTaskOrderVO.getTestSampleSplMember())?"":ersaiTaskOrderVO.getTestSampleSplMember());
		dataMap.put("date", UtilValidate.isEmpty(ersaiTaskOrderVO.getTestSampleSplDate())?"":DateUtil.formatYMD(ersaiTaskOrderVO.getTestSampleSplDate()));
		dataMap.put("singleProVal", UtilValidate.isEmpty(ersaiTaskOrderVO.getSingleProVal())?"":ersaiTaskOrderVO.getSingleProVal());
		dataMap.put("totalProVal", UtilValidate.isEmpty(ersaiTaskOrderVO.getTotalProVal())?"":ersaiTaskOrderVO.getTotalProVal());
		dataMap.put("projectM", UtilValidate.isEmpty(ersaiTaskOrderVO.getProjectManager())?"":ersaiTaskOrderVO.getProjectManager());
		dataMap.put("projectMT", UtilValidate.isEmpty(ersaiTaskOrderVO.getProjectManagerTime())?"":DateUtil.formatYMD(ersaiTaskOrderVO.getProjectManagerTime()));
		dataMap.put("productM", UtilValidate.isEmpty(ersaiTaskOrderVO.getProductManager())?"":ersaiTaskOrderVO.getProductManager());
		dataMap.put("productMT", UtilValidate.isEmpty(ersaiTaskOrderVO.getProductManagerTime())?"":DateUtil.formatYMD(ersaiTaskOrderVO.getProductManagerTime()));
		dataMap.put("testM", UtilValidate.isEmpty(ersaiTaskOrderVO.getTestManager())?"":ersaiTaskOrderVO.getTestManager());
		dataMap.put("testMT", UtilValidate.isEmpty(ersaiTaskOrderVO.getTestManagerTime())?"":DateUtil.formatYMD(ersaiTaskOrderVO.getTestManagerTime()));
		dataMap.put("radiationM", UtilValidate.isEmpty(ersaiTaskOrderVO.getRadiationLeader())?"":ersaiTaskOrderVO.getRadiationLeader());
		dataMap.put("radiationMT", UtilValidate.isEmpty(ersaiTaskOrderVO.getRadiationLeaderTime())?"":DateUtil.formatYMD(ersaiTaskOrderVO.getRadiationLeaderTime()));
		
		MDoc mdoc = new MDoc();
		String newFileName = UUID.randomUUID().toString()
				.replaceAll("-", "")+".doc";
		String fileName="C:/download/"+newFileName;
		mdoc.createDoc(dataMap, fileName,"radiation.ftl");
		return fileName;
	}
}
