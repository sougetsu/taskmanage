<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
	})
</script>
<style type="text/css">
.certification td{ height:30px;}
</style>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false">
		<div align="center" style="margin-top:50px">
			<form id="certification_detailForm" method="post"
				enctype="multipart/form-data">
				<table width="60%" border="1" bordercolor="#B5C0C4" rules="none"
					style="text-align:center;border-collapse:collapse;" class="certification">
					<tr>
						<td rowspan="2" style="text-align:center;"><img src="../style/images/cslog.png"/></td>
						<td colspan="3" style="text-align:center;font-size:16px;font-weight:bold;padding-right:20%;">产品合格证</td>
					</tr>
					<tr>
						<td></td>
						<td>合格证编号：</td>
						<td>${certification.certificationId}</td>
					</tr>
					<tr>
						<td>产 品 名 称：</td>
						<td>${certification.productName}</td>
						<td>产 品 型 号：</td>
						<td>${certification.productType}</td>
					</tr>
					<tr>
						<td>产 品 批 次：</td>
						<td>${certification.productBatch}</td>
						<td>产 品 数 量：</td>
						<td>${certification.productNum}</td>
					</tr>
					<tr>
						<td>产品检测标准：</td>
						<td>${certification.testStandard}</td>
						<td>检测报告号：</td>
						<td>${certification.testReportId}</td>
					</tr>
					<tr>
						<td>质量保证等级：</td>
						<td>${certification.qualityStatus}</td>
						<td>用 户 单 位：</td>
						<td>${certification.userUnits}</td>
					</tr>
					<tr>
						<td>检 验 员 ：</td>
						<td>${certification.inspector}</td>
						<td>签 发 日 期：</td>
						<td><fmt:formatDate value="${certification.certificationDate}" pattern="yyyy-MM-dd" /></td>
					</tr>
					<tr>
						<td>备 注：</td>
						<td colspan="3" style="text-align:left;">${certification.remark}</td>
					</tr>
					<tr>
						<td colspan="4"
							style="text-align:center;font-size:12px;font-weight:bold">中国航天科技集团公司第九研究院第七七二研究所</td>
					</tr>
				</table>
				<table style="margin-top:10px; ">
					<tr>
						<td colspan=4 style="text-align:center">
							<a id="certification_detail_export" class="l-btn" href="${pageContext.request.contextPath}/certification/exportWord?id=${certification.id}" target="_self">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">导出</span>
			         		</span>
			         		</a>
						
						<a id="certification_detail_close" class="l-btn"
							href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
								<span class="l-btn-left"> <span
									class="l-btn-text icon-cancel l-btn-icon-left"
									style="padding-left: 20px; ">关闭</span>
							</span>
						</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
