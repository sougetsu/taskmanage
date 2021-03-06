package com.sdmx.yansTask.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.sdmx.framework.util.DateUtil;
import com.sdmx.framework.util.ExceptionUtil;
import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.util.doc.MDoc;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.JsonResult;
import com.sdmx.taskmanage.service.IPriceItemService;
import com.sdmx.taskmanage.vo.OperatorQueryType;
import com.sdmx.taskmanage.vo.PriceCheckVO;
import com.sdmx.taskmanage.vo.PriceItemVO;
import com.sdmx.taskmanage.vo.TaskPriceVO;
import com.sdmx.taskmanage.vo.TaskScheduleVO;
import com.sdmx.yansTask.entity.YansAttachment;
import com.sdmx.yansTask.service.IYansAttachmentService;
import com.sdmx.yansTask.service.IYansTaskOrderService;
import com.sdmx.yansTask.service.IYansTaskPriceService;
import com.sdmx.yansTask.vo.YansTaskOrderStatus;
import com.sdmx.yansTask.vo.YansTaskOrderVO;

@Controller
@RequestMapping(value = "/yansTaskManage")
public class YansTaskOrderController {
	
	@Autowired
	private IYansTaskOrderService yansTaskOrderService;
	@Autowired
	private IYansAttachmentService yansAttachmentService;
	@Autowired
	private IPriceItemService priceItemService;
	@Autowired
	private IYansTaskPriceService yansPriceItemService;
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
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/create")
	@ResponseBody
	public JsonResult create(YansTaskOrderVO yansTaskOrderVO,
			BindingResult errors) {
		// 验证任务单信息输入
		if (errors.hasErrors()) {
			String code = errors.getFieldError().getCode();
			String msg = errors.getFieldError().getDefaultMessage();// 出错的信息
			return JsonResult.failed(msg, code);
		}
		yansTaskOrderService.create(yansTaskOrderVO);
		return JsonResult.success("新增二筛任务单成功！", yansTaskOrderVO);
	}
	/**
	 * 任务单列表查询
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public DataGrid listTaskOrder(YansTaskOrderVO yansTaskOrderVO) {
		return yansTaskOrderService.listTaskOrder(yansTaskOrderVO);
	}
	
	/**
	 * 查询-详细页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/detailPage")
	public ModelAndView detailPage(String id, HttpServletRequest request) {
		YansTaskOrderVO tInfo = yansTaskOrderService.getTaskOrderById(id);
//		if(tInfo.getStatus() == YansTaskOrderStatus.COMPLETED.getValue() ||
//			tInfo.getStatus() == YansTaskOrderStatus.WAITTOFIX_TESTCENTERMANAGE.getValue() ||
//			tInfo.getStatus() == YansTaskOrderStatus.WAITTOFIX_DEPARTMANAGE.getValue()){
//			List<TaskPriceVO> taskPriceList = yansTaskOrderService.getTaskPriceListById(id);
//			TaskScheduleVO taskSchedule = yansTaskOrderService.getTaskScheduleById(id);
//			request.setAttribute("taskPrice", taskPriceList);
//			request.setAttribute("taskSchedule", taskSchedule);
//		}
		request.setAttribute("taskOrder", tInfo);
		return new ModelAndView("/yans/yansTaskDetail");
	}
	/**
	 * 审核通过任务单
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/confirmOK")
	@ResponseBody
	public JsonResult confirmOK(YansTaskOrderVO yansTaskOrderVO,
			BindingResult errors) {
		// 验证任务单信息输入
		if (errors.hasErrors()) {
			String code = errors.getFieldError().getCode();
			String msg = errors.getFieldError().getDefaultMessage();// 出错的信息
			return JsonResult.failed(msg, code);
		}
		yansTaskOrderService.confirmOK(yansTaskOrderVO);
		return JsonResult.success("操作成功！", yansTaskOrderVO);
	}
	
	/**
	 * 审核不通过
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/confirmNG")
	@ResponseBody
	public JsonResult confirmNG(YansTaskOrderVO yansTaskOrderVO) {
		yansTaskOrderService.confirmNG(yansTaskOrderVO);
		return JsonResult.success("操作成功！", yansTaskOrderVO);
	}
	/**
	 * 修改后提交
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/editSubmit")
	@ResponseBody
	public JsonResult editSubmit(YansTaskOrderVO yansTaskOrderVO,
			BindingResult errors) {
		// 验证任务单信息输入
		if (errors.hasErrors()) {
			String code = errors.getFieldError().getCode();
			String msg = errors.getFieldError().getDefaultMessage();// 出错的信息
			return JsonResult.failed(msg, code);
		}
		yansTaskOrderService.confirmOK(yansTaskOrderVO);
		return JsonResult.success("操作成功！", yansTaskOrderVO);
	}
	
	/**
	 * 任务单待处理列表查询
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/susList")
	@ResponseBody
	public DataGrid listSusTaskOrder(YansTaskOrderVO yansTaskOrderVO) {
		yansTaskOrderVO.setClType(OperatorQueryType.SuspendingQuery.getValue());
		return yansTaskOrderService.listSusTaskOrder(yansTaskOrderVO);
	}
	/**
	 * 查询-审核页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/confirmPage")
	public ModelAndView confirmPage(String id, HttpServletRequest request) {
		YansTaskOrderVO tInfo = yansTaskOrderService.getTaskOrderById(id);
		request.setAttribute("taskOrder", tInfo);
		return new ModelAndView("/yans/yansTaskConfirm");
	}
	/**
	 * 查询-核价页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/pricePage")
	public ModelAndView pricePage(String id, HttpServletRequest request) {
		YansTaskOrderVO tInfo = yansTaskOrderService.getTaskOrderById(id);
		request.setAttribute("taskOrder", tInfo);
		return new ModelAndView("/yans/yansTaskPrice");
	}
	/**
	 * 查询-核价修改页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/pricePageEdit")
	public ModelAndView pricePageEdit(String id, HttpServletRequest request) {
		YansTaskOrderVO tInfo = yansTaskOrderService.getTaskOrderById(id);
		TaskScheduleVO taskSchedule = yansTaskOrderService.getTaskScheduleById(id);
		request.setAttribute("taskSchedule", taskSchedule);
		request.setAttribute("taskOrder", tInfo);
		return new ModelAndView("/yans/yansTaskPriceEdit");
	}
	/**
	 * 查询-修改页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/editPage")
	public ModelAndView editPage(String id, HttpServletRequest request) {
		YansTaskOrderVO tInfo = yansTaskOrderService.getTaskOrderById(id);
		request.setAttribute("taskOrder", tInfo);
		return new ModelAndView("/yans/yansTaskEdit");
	}
	
	/**
	 * 查询-确认页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/fixPage")
	public ModelAndView fixPage(String id, HttpServletRequest request) {
		YansTaskOrderVO tInfo = yansTaskOrderService.getTaskOrderById(id);
//		if(tInfo.getStatus() == YansTaskOrderStatus.COMPLETED.getValue() ||
//			tInfo.getStatus() == YansTaskOrderStatus.WAITTOFIX_TESTCENTERMANAGE.getValue() ||
//			tInfo.getStatus() == YansTaskOrderStatus.WAITTOFIX_DEPARTMANAGE.getValue()){
//			List<TaskPriceVO> taskPriceList = yansTaskOrderService.getTaskPriceListById(id);
//			TaskScheduleVO taskSchedule = yansTaskOrderService.getTaskScheduleById(id);
//			request.setAttribute("taskPrice", taskPriceList);
//			request.setAttribute("taskSchedule", taskSchedule);
//		}
		request.setAttribute("taskOrder", tInfo);
		return new ModelAndView("/yans/yansTaskFix");
	}
	
	@RequestMapping(value = "/checkPrice")
	@ResponseBody
	public JsonResult checkPrice(PriceCheckVO priceCheckVO) {
		yansTaskOrderService.checkPrice(priceCheckVO);
		return JsonResult.success("核价保存成功！", null);
	}
	
	/**
	 * 确认完成
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/fixComplete")
	@ResponseBody
	public JsonResult fixComplete(YansTaskOrderVO yansTaskOrderVO) {
		yansTaskOrderService.fixComplete(yansTaskOrderVO);
		return JsonResult.success("操作成功！", yansTaskOrderVO);
	}
	/**
	 * 附件上传
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping("/uploadFile")
	public String uploadFile(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		String savePath = ResourceUtil.getUploadPath()
				+ ResourceUtil.getYansUploadDirectory() + "/";// 文件保存目录路径
		String saveUrl = "/knowledge/" + ResourceUtil.getYansUploadDirectory()
				+ "?filePath=";// 要返回文件保存目录URL
		File uploadDir = new File(savePath);// 创建要上传文件到指定的目录
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		if (ServletFileUpload.isMultipartContent(request)) {// 判断表单是否存在enctype="multipart/form-data"
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			dfif.setSizeThreshold(1 * 1024 * 1024);// 设定当上传文件超过1M时，将产生临时文件用于缓冲
			dfif.setRepository(new File(savePath));// 放临时文件的目录存
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			try {
				for (Map.Entry<String, MultipartFile> entity : fileMap
						.entrySet()) {
					MultipartFile file = entity.getValue();
					String fileName = file.getOriginalFilename();
					if (file.getSize() > ResourceUtil.getUploadFileMaxSize()) {
						this.uploadError("上传文件超出限制大小！", fileName, response);
						return null;
					}
					try {
						// 检查文件扩展名
						String fileExt = fileName.substring(
								fileName.lastIndexOf(".") + 1).toLowerCase();
						if (!Arrays.<String> asList(
								ResourceUtil.getUploadFileExts().split(","))
								.contains(fileExt)) {
							this.uploadError("上传文件扩展名是不允许的扩展名。\n只允许"
									+ ResourceUtil.getUploadFileExts() + "格式！",
									response);
							return null;
						}
						String newFileName = UUID.randomUUID().toString()
								.replaceAll("-", "")
								+ "." + fileExt;// 新的文件名称

						if (file.getSize() > 0) {
							try {
								SaveFileFromInputStream(file.getInputStream(),
										savePath, newFileName);
								YansAttachment attach = new YansAttachment();
								attach.setAttachSize(file.getSize());
								attach.setContentType(fileExt);
								attach.setCreateDate(new Date());
								attach.setNewName(newFileName);
								attach.setOldName(fileName);
								attach = yansAttachmentService.create(attach);
								this.uploadSuccess(request.getContextPath()
										+ saveUrl + newFileName, fileName,
										attach.getId(), response);// 文件上传成功
							} catch (IOException e) {
								System.out.println(e.getMessage());
								return null;
							}
						} else {
							throw new Exception("上传失败：上传文件不能为�空");
						}
						return null;
					} catch (Exception e) {
						this.uploadError("上传文件出错！", response);
						ExceptionUtil.getExceptionMessage(e);
						return null;
					}
				}
			} catch (Exception e) {
				this.uploadError("上传文件出错！", response);
				ExceptionUtil.getExceptionMessage(e);
				return null;
			}
		} else {
			// 不是multipart/form-data表单
			this.uploadError("您没有上传任何文件！", response);
			return null;
		}
		return null;
	}

	/**
	 * 保存文件到指定目录
	 * 
	 * @param stream
	 * @param path
	 * @param filename
	 * @throws IOException
	 */
	public void SaveFileFromInputStream(InputStream stream, String path,
			String filename) throws IOException {
		FileOutputStream fs = new FileOutputStream(path + "/" + filename);
		byte[] buffer = new byte[1024 * 1024];
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}

	/**
	 * 文件上传失败后返回JSON内容
	 * 
	 * @param err
	 * @param msg
	 * @param response
	 */
	private void uploadError(String err, String msg,
			HttpServletResponse response) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("err", err);
		m.put("msg", msg);
		m.put("result", 0);
		this.writeJson(m, response);
	}

	/**
	 * 文件上传失败
	 * 
	 * @param err
	 * @param msg
	 * @param response
	 */
	private void uploadError(String err, HttpServletResponse response) {
		this.uploadError(err, "", response);
	}

	/**
	 * 文件上传成功后返回JSON内容
	 * 
	 * @param newFileName
	 * @param fileName
	 * @param id
	 * @param response
	 */
	private void uploadSuccess(String newFileName, String fileName, int id,
			HttpServletResponse response) {
		Map<String, Object> nm = new HashMap<String, Object>();
		nm.put("url", newFileName);
		nm.put("localfile", fileName);
		nm.put("id", id);
		nm.put("result", 1);
		this.writeJson(nm, response);
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @throws IOException
	 */
	private void writeJson(Object obj, HttpServletResponse response) {
		try {
			String json = JSON.toJSONStringWithDateFormat(obj,
					"yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
				+ ResourceUtil.getYansUploadDirectory() + "/" + filePath;// 文件保存目录路径
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
	 * 附件删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteAttach")
	@ResponseBody
	public JsonResult deleteAttach(int id) {
		yansAttachmentService.remove(id);
		return JsonResult.success("删除成功！", id);
	}
	
	/**
	 * 封测中心各项目价格查询
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/listPrice")
	@ResponseBody
	public DataGrid listPrice(PriceItemVO priceItemvo) {
		return priceItemService.listErsaiPriceItem(priceItemvo);
	}
	/**
	 * 封测中心各项目价格查询修改
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/listPriceEdit")
	@ResponseBody
	public DataGrid listPriceEdit(String id) {
		return yansPriceItemService.getTaskPriceVO(id);
	}
	
	/**
	 * 查询数据导出
	 * @param consult
	 * @return
	 */
	@RequestMapping(value = "/downloadExcel")
    public void downloadExcel(YansTaskOrderVO yansTaskOrderVO,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response = yansTaskOrderService.downloadExcel(yansTaskOrderVO, response);
    }

	/**
	 * 查询数据word导出
	 * @param consult
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/exportWord")
	   public void exportWord(HttpServletRequest request,
				HttpServletResponse response, HttpSession session,YansTaskOrderVO yansTaskOrderVO) throws Exception{
    		YansTaskOrderVO yansTaskOrder = yansTaskOrderService.getTaskOrderById(yansTaskOrderVO.getOrderId());
			String filePath = createWordByVO(yansTaskOrder);
			File uploadedFile = new File(filePath);
			if (uploadedFile.exists()) {
				try {
					response.setContentType(this.getContentType(filePath));
					response.setHeader("Content-disposition",
							"attachment;filename="+yansTaskOrder.getLsh()+".doc");
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
	private String createWordByVO(YansTaskOrderVO yansTaskOrderVO) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("createtime", DateUtil.formatYMD(yansTaskOrderVO.getCreatetime()));
		dataMap.put("lsh", UtilValidate.isEmpty(yansTaskOrderVO.getLsh())?"":yansTaskOrderVO.getLsh());
		dataMap.put("reportNo", UtilValidate.isEmpty(yansTaskOrderVO.getReportNo())?"":yansTaskOrderVO.getReportNo());
		dataMap.put("internalModel", UtilValidate.isEmpty(yansTaskOrderVO.getInternalModel())?"":yansTaskOrderVO.getInternalModel());
		dataMap.put("applyDept", UtilValidate.isEmpty(yansTaskOrderVO.getApplyDept())?"":yansTaskOrderVO.getApplyDept());
		dataMap.put("applyMember", UtilValidate.isEmpty(yansTaskOrderVO.getApplyMember())?"":yansTaskOrderVO.getApplyMember());
		dataMap.put("amPhone", UtilValidate.isEmpty(yansTaskOrderVO.getApplyMemberPhone())?"":yansTaskOrderVO.getApplyMemberPhone());
		dataMap.put("topicName", UtilValidate.isEmpty(yansTaskOrderVO.getTopicName())?"":yansTaskOrderVO.getTopicName());
		dataMap.put("projectManager", UtilValidate.isEmpty(yansTaskOrderVO.getProjectManager())?"":yansTaskOrderVO.getProjectManager());
		dataMap.put("pmPhone", UtilValidate.isEmpty(yansTaskOrderVO.getProjectManagerPhone())?"":yansTaskOrderVO.getProjectManagerPhone());
		dataMap.put("helpDeptName", UtilValidate.isEmpty(yansTaskOrderVO.getHelpDeptName())?"":yansTaskOrderVO.getHelpDeptName());
		dataMap.put("wantedEndDate", DateUtil.formatYMD(yansTaskOrderVO.getWantedEndDate()));
		dataMap.put("goldcutFlag", yansTaskOrderVO.getGoldcutFlag()==1?"是":"否");
		dataMap.put("goldcutNo", UtilValidate.isEmpty(yansTaskOrderVO.getGoldcutNo())?"":yansTaskOrderVO.getGoldcutNo());
		dataMap.put("borrowState", UtilValidate.isEmpty(yansTaskOrderVO.getBorrowState())?"":yansTaskOrderVO.getBorrowState());
		String urgencyStatus = "";
		if(yansTaskOrderVO.getUrgency()==1){
			urgencyStatus = "紧急";
		}else if(yansTaskOrderVO.getUrgency()==2){
			urgencyStatus = "超紧急";
		}else {
			urgencyStatus = "一般";
		}
		dataMap.put("urgency", urgencyStatus);
		dataMap.put("applyReason", UtilValidate.isEmpty(yansTaskOrderVO.getApplyReason())?"":yansTaskOrderVO.getApplyReason());
		dataMap.put("detailRequire", UtilValidate.isEmpty(yansTaskOrderVO.getDetailRequire())?"":yansTaskOrderVO.getDetailRequire());
		dataMap.put("remarks", UtilValidate.isEmpty(yansTaskOrderVO.getRemarks())?"":yansTaskOrderVO.getRemarks());
		dataMap.put("productManagesuggest", UtilValidate.isEmpty(yansTaskOrderVO.getProductManagesuggest())?"":yansTaskOrderVO.getProductManagesuggest());
		
		MDoc mdoc = new MDoc();
		String newFileName = UUID.randomUUID().toString()
				.replaceAll("-", "")+".doc";
		String fileName="C:/download/"+newFileName;
		mdoc.createDoc(dataMap, fileName,"yans.ftl");
		return fileName;
	}
	
	/**
	 * 任务单取消
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/cancelTaskOrder")
	@ResponseBody
	public JsonResult cancelTaskOrder(String id) {
		yansTaskOrderService.cancel(id);
		return JsonResult.success("取消成功！", id);
	}
	
	/**
	 * 任务单借库修改
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/borrowStock")
	@ResponseBody
	public JsonResult borrowStock(String id) {
		yansTaskOrderService.borrowStock(id);
		return JsonResult.success("修改成功！", id);
	}
	
	/**
	 * 任务单借库修改
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/noborrowStock")
	@ResponseBody
	public JsonResult noborrowStock(String id) {
		yansTaskOrderService.noborrowStock(id);
		return JsonResult.success("修改成功！", id);
	}
	
	
	/**
	 * 任务单删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/removeTaskOrder")
	@ResponseBody
	public JsonResult taskOrderService(String id) {
		yansTaskOrderService.remove(id);
		return JsonResult.success("删除成功！", id);
	}
}
