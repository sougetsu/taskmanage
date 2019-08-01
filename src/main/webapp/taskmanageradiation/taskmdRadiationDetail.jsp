<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
	});
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<div data-options="region:'center',border:false" style="position:relative;left:0;top:0;"">
	<div style="width:780px;margin-left: auto;margin-right: auto;>
		<form id="taskRadiationOrder_detailForm"method="post">
		<table class="tasktableForm" width="95%" border="1"  bordercolor="#B5C0C4" rules="none" style="border-collapse:collapse;">
				<tr width="980px" style="height: 40px">
					<th width="25%">类别</th>
					<th width="50%" colspan="2">内容</th>
					<th width="25%">相关负责人会签</th>
				</tr>
				<tr>
					<th>电路名称</th>
					<td colspan="2">
						${taskOrder.circuitName}
					</td>
					<td rowspan="12" width="380px">1、	电路名称一般以《详细规范》标题上的电路名称为准;<br/>
									2、	电路型号一般为电路打标表面的型号;<br/>
									3、	项目负责人需提供表中所述的《详细规范》,若无请提供电路功能说明文件<br/>
									4、	试验样品信息可以后补，先填上半部分，提交任务单，待样品交接时再填写试验样品信息。
					</td>
				</tr>
				<tr>
					<th>电路型号</th>
					<td colspan="2">
						${taskOrder.circuitType}
					</td>
				</tr>
				<tr>
					<th>类别</th>
					<td>单粒子
						<span style="white-space:nowrap;">
							<c:choose>
							   	<c:when test="${taskOrder.singleionsFlag==1}">
							   		是
							   	</c:when>
							   	<c:otherwise>
							   		无
							   	</c:otherwise>
							</c:choose>
						</span>
					</td>
					<td>总剂量
						<span style="white-space:nowrap;">
							<c:choose>
							   	<c:when test="${taskOrder.totalDoseFlag==1}">
							   		是
							   	</c:when>
							   	<c:otherwise>
							   		无
							   	</c:otherwise>
							</c:choose>
						</span>
					</td>
				</tr>
				<tr>
					<th>生产批次</th>
					<td>${taskOrder.singleionsBatch}</td>
					<td>${taskOrder.totalDoseBatch}</td>
				</tr>
				<tr>
					<th>样品数量</th>
					<td>${taskOrder.singleionsSmpNum}</td>
					<td>${taskOrder.totalDoseSmpNum}</td>
				</tr>
				<tr>
					<th>委托开发测试系统</th>
					<td>
						<c:choose>
						   	<c:when test="${taskOrder.singleionsTest==1}">
						   		是
						   	</c:when>
						   	<c:otherwise>
						   		否
						   	</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
						   	<c:when test="${taskOrder.totalDoseTest==1}">
						   		是
						   	</c:when>
						   	<c:otherwise>
						   		否
						   	</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>芯片版本</th>
					<td colspan="2">${taskOrder.microchipsVersion}</td>
				</tr>
				<tr>
					<th>圆片批次</th>
					<td colspan="2">${taskOrder.reductionNo}</td>
				</tr>
				<tr>
					<th>单粒子指标（单粒子试验要求）</th>
					<td colspan="2">${taskOrder.singleionsIndex}</td>
				</tr>
				<tr>
					<th>总剂量指标（总剂量试验要求）</th>
					<td colspan="2">${taskOrder.totalDoseIndex}</td>
				</tr>
				<tr>
					<th>详细规范编号\版本\日期</th>
					<td colspan="2">${taskOrder.detailSpecification}</td>
				</tr>
				<tr>
					<th>详细规范状态</th>
					<td colspan="2">
						${taskOrder.detailSpecificationStatus}
					</td>
				</tr>
				<tr>
					<th colspan="3">试验样品信息（可在提供试验样品时再填写）</th>
					<th rowspan="2">试验样品提供人</th>
				</tr>
				<tr>
					<th>单粒子样品编号（初样无编号的请在芯片铅笔编号）</th>
					<td colspan="2">${taskOrder.singleionsNo}</td>
				</tr>
				<tr>
					<th>单粒子备用样品编号</th>
					<td colspan="2">${taskOrder.singleionsSpareNo}</td>
					<td>提供人:${taskOrder.testSampleSplMember}</td>
				</tr>
				<tr>
					<th>总剂量样品编号（初样无编号的请在芯片铅笔编号）</th>
					<td colspan="2">${taskOrder.totalDoseNo}</td>
					<td>日&nbsp;&nbsp;&nbsp;&nbsp;期:
						<fmt:formatDate value="${taskOrder.testSampleSplDate}" pattern="yyyy-MM-dd" />
					</td>
				</tr>
				<tr>
					<th>总剂量对比样品编号</th>
					<td colspan="2">${taskOrder.totalDoseCompareNo}</td>
					<td>产&nbsp;&nbsp;&nbsp;&nbsp;值:${taskOrder.proVal}
					</td>
				</tr>
				<tr>
			         <td colspan=4 style="text-align:center">
			         	<a id="detailradiation_export" class="l-btn" href="${pageContext.request.contextPath}/taskManagemdRadiation/exportWord?orderId=${taskOrder.orderId}" target="_self">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">导出</span>
			         		</span>
			         	</a>
			         	<a id="detailradiation_close" class="l-btn" href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-cancel l-btn-icon-left" style="padding-left: 20px; ">关闭</span>
			         		</span>
			         	</a>
			         </td>
		     	</tr>
			</table>
		</form>
	</div>
</div>
</div>