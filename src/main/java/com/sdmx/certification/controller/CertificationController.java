package com.sdmx.certification.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.sdmx.certification.service.ICertificationService;
import com.sdmx.certification.vo.CertificationVO;
import com.sdmx.framework.util.DateUtil;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.util.doc.MDoc;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.JsonResult;

@Controller
@RequestMapping(value = "/certification")
public class CertificationController {
	
	
	@Autowired
	private ICertificationService certificationService;
	
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
	public JsonResult create(CertificationVO certificationVO,
			BindingResult errors) {
		// 验证任务单信息输入
		if (errors.hasErrors()) {
			String code = errors.getFieldError().getCode();
			String msg = errors.getFieldError().getDefaultMessage();// 出错的信息
			return JsonResult.failed(msg, code);
		}
		certificationService.create(certificationVO);
		return JsonResult.success("新增合格证成功！", certificationVO);
	}
	
	/**
	 * 查询数据word导出
	 * @param consult
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/exportWord")
    public void exportWord(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,String id) throws Exception{
			CertificationVO certificationVO = certificationService.getCertificationById(id);
			String filePath = createWordByVO(certificationVO);
			File uploadedFile = new File(filePath);
			if (uploadedFile.exists()) {
				try {
					response.setContentType(this.getContentType(filePath));
					response.setHeader("Content-disposition",
							"attachment;filename="+"合格证导出"+".doc");
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
	 * 列表数据word导出
	 * @param consult
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/downloadList")
    public void downloadList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,CertificationVO certificationvo) throws Exception{
			String filePath = createWordListByVO(certificationvo);
			File uploadedFile = new File(filePath);
			if (uploadedFile.exists()) {
				try {
					response.setContentType(this.getContentType(filePath));
					response.setHeader("Content-disposition",
							"attachment;filename="+"certificate"+".doc");
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
	private String createWordByVO(CertificationVO certificationVO) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("certificationId", UtilValidate.isEmpty(certificationVO.getCertificationId())?"":certificationVO.getCertificationId());
		dataMap.put("productName", UtilValidate.isEmpty(certificationVO.getProductName())?"":certificationVO.getProductName());
		dataMap.put("productType", UtilValidate.isEmpty(certificationVO.getProductType())?"":certificationVO.getProductType());
		dataMap.put("productBatch", UtilValidate.isEmpty(certificationVO.getProductBatch())?"":certificationVO.getProductBatch());
		dataMap.put("productNum", UtilValidate.isEmpty(certificationVO.getProductNum())?"":certificationVO.getProductNum());
		dataMap.put("testStandard", UtilValidate.isEmpty(certificationVO.getTestStandard())?"":certificationVO.getTestStandard());
		dataMap.put("testReportId", UtilValidate.isEmpty(certificationVO.getTestReportId())?"":certificationVO.getTestReportId());
		dataMap.put("qualityStatus", UtilValidate.isEmpty(certificationVO.getQualityStatus())?"":certificationVO.getQualityStatus());
		dataMap.put("userUnits", UtilValidate.isEmpty(certificationVO.getUserUnits())?"":certificationVO.getUserUnits());
		dataMap.put("inspector", UtilValidate.isEmpty(certificationVO.getInspector())?"":certificationVO.getInspector());
		dataMap.put("certificationDate", DateUtil.formatYMD(certificationVO.certificationDate));
		dataMap.put("remark", UtilValidate.isEmpty(certificationVO.getRemark())?"":certificationVO.getRemark());
		
		MDoc mdoc = new MDoc();
		String newFileName = UUID.randomUUID().toString()
				.replaceAll("-", "")+".doc";
		String fileName="C:/download/"+newFileName;
		mdoc.createDoc(dataMap, fileName,"hgz.ftl");
		return fileName;
	}
	
	private String createWordListByVO(CertificationVO cfaVO) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
		List<CertificationVO> cvo = certificationService.listCertification(cfaVO).getRows();
		for (CertificationVO certificationVO : cvo) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("certificationId", UtilValidate.isEmpty(certificationVO.getCertificationId())?"":certificationVO.getCertificationId());
			dataMap.put("productName", UtilValidate.isEmpty(certificationVO.getProductName())?"":certificationVO.getProductName());
			dataMap.put("productType", UtilValidate.isEmpty(certificationVO.getProductType())?"":certificationVO.getProductType());
			dataMap.put("productBatch", UtilValidate.isEmpty(certificationVO.getProductBatch())?"":certificationVO.getProductBatch());
			dataMap.put("productNum", UtilValidate.isEmpty(certificationVO.getProductNum())?"":certificationVO.getProductNum());
			dataMap.put("testStandard", UtilValidate.isEmpty(certificationVO.getTestStandard())?"":certificationVO.getTestStandard());
			dataMap.put("testReportId", UtilValidate.isEmpty(certificationVO.getTestReportId())?"":certificationVO.getTestReportId());
			dataMap.put("qualityStatus", UtilValidate.isEmpty(certificationVO.getQualityStatus())?"":certificationVO.getQualityStatus());
			dataMap.put("userUnits", UtilValidate.isEmpty(certificationVO.getUserUnits())?"":certificationVO.getUserUnits());
			dataMap.put("inspector", UtilValidate.isEmpty(certificationVO.getInspector())?"":certificationVO.getInspector());
			dataMap.put("certificationDate", DateUtil.formatYMD(certificationVO.certificationDate));
			dataMap.put("remark", UtilValidate.isEmpty(certificationVO.getRemark())?"":certificationVO.getRemark());
			datalist.add(dataMap);
		}
		resultMap.put("dataList", datalist);
		MDoc mdoc = new MDoc();
		String newFileName = UUID.randomUUID().toString()
				.replaceAll("-", "")+".doc";
		String fileName="C:/download/"+newFileName;
		mdoc.createDoc(resultMap, fileName,"hgzlist.ftl");
		return fileName;
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
	 * 任务单列表查询
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public DataGrid listCertification(CertificationVO certificationvo) {
		return certificationService.listCertification(certificationvo);
	}
	
	/**
	 * 查询-详细页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/detailPage")
	public ModelAndView detailPage(String id, HttpServletRequest request) {
		CertificationVO cInfo = certificationService.getCertificationById(id);
		request.setAttribute("certification", cInfo);
		return new ModelAndView("/certification/certificationDetail");
	}
	
	/**
	 * 查询-修改页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/editPage")
	public ModelAndView editPage(String id, HttpServletRequest request) {
		CertificationVO cInfo = certificationService.getCertificationById(id);
		request.setAttribute("certification", cInfo);
		return new ModelAndView("/certification/certificationEdit");
	}
	
	/**
	 * 合格证删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/removeCertification")
	@ResponseBody
	public JsonResult removeCertification(String id) {
		certificationService.remove(id);
		return JsonResult.success("删除成功！", id);
	}
	/**
	 * 修改后提交
	 * 
	 * @param taskOrdervo
	 * @return
	 */
	@RequestMapping(value = "/editSubmit")
	@ResponseBody
	public JsonResult editSubmit(CertificationVO certificationVO,
			BindingResult errors) {
		// 验证任务单信息输入
		if (errors.hasErrors()) {
			String code = errors.getFieldError().getCode();
			String msg = errors.getFieldError().getDefaultMessage();// 出错的信息
			return JsonResult.failed(msg, code);
		}
		certificationService.editSubmit(certificationVO);
		return JsonResult.success("操作成功！", certificationVO);
	}
}
