<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
	});
	function confirmOKSubmit() {
		var submitForm = $('#taskOrderRadiation_ConfirmForm');
		if (submitForm.form('validate')) {
			parent.$.messager.progress({
				title : '提示',
				text : '数据提交中，请稍后....'
			});
			$
					.ajax({
						type : "POST",
						url : '${pageContext.request.contextPath}/taskManageRadiation/confirmOK',
						data : submitForm.serialize(),
						dataType : 'json',
						success : function(result) {
							parent.$.messager.progress('close');
							if (result.success) {
								var index = $('#layout_center_tabs').tabs(
										'getTabIndex',
										$('#layout_center_tabs').tabs(
												'getSelected'));
								$('#layout_center_tabs').tabs('close', index);
								$('#taskRadiationOrder_list_datagrid')
										.datagrid('reload');
								$('#taskRadiationOrder_susList_datagrid')
										.datagrid('reload');
							}
							$.messager
									.show({
										title : '提示',
										msg : result.msg,
										timeout : 3000,
										showType : 'fade',
										style : {
											right : '',
											bottom : '-document.body.scrollTop-document.documentElement.scrollTop'
										}
									});
						}
					});
		}
	}
	function confirmNGSubmit() {
		var submitForm = $('#taskOrderRadiation_ConfirmForm');
		if (submitForm.form('validate')) {
			parent.$.messager.progress({
				title : '提示',
				text : '数据提交中，请稍后....'
			});
			$
					.ajax({
						type : "POST",
						url : '${pageContext.request.contextPath}/taskManageRadiation/confirmNG',
						data : submitForm.serialize(),
						dataType : 'json',
						success : function(result) {
							parent.$.messager.progress('close');
							if (result.success) {
								var index = $('#layout_center_tabs').tabs(
										'getTabIndex',
										$('#layout_center_tabs').tabs(
												'getSelected'));
								$('#layout_center_tabs').tabs('close', index);
								$('#taskRadiationOrder_list_datagrid')
										.datagrid('reload');
								$('#taskRadiationOrder_susList_datagrid')
										.datagrid('reload');
							}
							$.messager
									.show({
										title : '提示',
										msg : result.msg,
										timeout : 3000,
										showType : 'fade',
										style : {
											right : '',
											bottom : '-document.body.scrollTop-document.documentElement.scrollTop'
										}
									});
						}
					});
		}
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false">
		<div align="center" style="margin-top:20px;">
			<form id="taskOrderRadiation_ConfirmForm" method="post"
				enctype="multipart/form-data">
				<input type="hidden" name="orderId" value="${taskOrder.orderId}" />
				<table class="tasktableForm" width="95%" border="1"
					bordercolor="#B5C0C4" rules="none"
					style="border-collapse:collapse;">
					<tr width="980px" style="height: 40px">
						<th width="25%">类别</th>
						<th width="50%" colspan="2">内容</th>
						<th width="25%">备注</th>
					</tr>
					<tr>
						<th>任务单类型</th>
						<td><span style="white-space:nowrap;"> <input
								type="radio" style="width:30px" name="type" value="0"
								checked="checked" />鉴定类 <input type="radio" style="width:30px"
								name="type" value="1" />摸底类 </span></td>
						<td>
							<table border="0">
								<th>课题号</th>
								<td><input name="topicNoName"
									style="width:100%;font-size: 12px"
									value="${taskOrder.topicNoName }" /></td>
							</table></td>
					</tr>
					<tr>
						<th>电路名称</th>
						<td colspan="2"><input id="taskOrderRadiation_circuitName"
							name="circuitName" value="${taskOrder.circuitName}"
							style="width:100%;font-size: 12px" />
						</td>
						<td rowspan="16" width="380px">1、 电路名称一般以《详细规范》标题上的电路名称为准;<br />
							2、 电路型号一般为电路打标表面的型号;<br /> 3、 务必准确填写《详细规范》版本和状态信息;<br /> 4、
							试验样品信息可以后补，先填上半部分，提交任务单，待样品交接时再填写试验样品信息。</td>
					</tr>
					<tr>
						<th>电路型号</th>
						<td colspan="2"><input id="taskOrderRadiation_circuitType"
							name="circuitType" value="${taskOrder.circuitType
							}" style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>类别</th>
						<td>单粒子 <span style="white-space:nowrap;"> <input
								type="radio" style="width:30px" name="singleionsFlag" value="1"
								${taskOrder.singleionsFlag== 1 ? 'checked' : ''} />是 <input
								type="radio" style="width:30px" name="singleionsFlag" value="0"
								${taskOrder.singleionsFlag== 0 ? 'checked' : ''} />无 </span></td>
						<td>总剂量 <span style="white-space:nowrap;"> <input
								type="radio" style="width:30px" name="totalDoseFlag" value="1"
								${taskOrder.totalDoseFlag== 1 ? 'checked' : ''}  />是 <input
								type="radio" style="width:30px" name="totalDoseFlag" value="0"
								${taskOrder.totalDoseFlag== 0 ? 'checked' : ''} />无 </span></td>
					</tr>
					<tr>
						<th>生产批次</th>
						<td><input name="singleionsBatch"
							value="${taskOrder.singleionsBatch }" style="font-size: 12px" /></td>
						<td><input name="totalDoseBatch"
							value="${taskOrder.totalDoseBatch }" style="font-size: 12px" /></td>
					</tr>
					<tr>
						<th>样品数量</th>
						<td><input name="singleionsSmpNum"
							value="${taskOrder.singleionsSmpNum }" style="font-size: 12px" /></td>
						<td><input name="totalDoseSmpNum"
							value="${taskOrder.totalDoseSmpNum }" style="font-size: 12px" /></td>
					</tr>
					<tr>
						<th>委托开发测试系统</th>
						<td><input type="radio" style="width:30px"
							name="singleionsTest" value="1" ${taskOrder.singleionsTest== 1 ? 'checked' : ''} />是
							<input type="radio" style="width:30px" name="singleionsTest"
							value="0" ${taskOrder.singleionsTest== 0 ? 'checked' : ''} />否</td>
						<td><input type="radio" style="width:30px"
							name="totalDoseTest" value="1" ${taskOrder.totalDoseTest== 1 ? 'checked' : ''} />是
							<input type="radio" style="width:30px" name="totalDoseTest"
							value="0" ${taskOrder.totalDoseTest== 0 ? 'checked' : ''}/>否</td>
					</tr>
					<tr>
					<th width="150px" >紧急程度：</th>
					<td>
						<span style="white-space:nowrap;">
						<input type="radio" style="width:30px" name="urgency" value="0" ${taskOrder.urgency == 0 ? 'checked' : ''}  ${taskOrder.urgencyState == 0 ? 'disabled' : ''}/>一般
						<input type="radio" style="width:30px" name="urgency" value="1" ${taskOrder.urgency == 1 ? 'checked' : ''}   ${taskOrder.urgencyState == 0 ? 'disabled' : ''}/>紧急
						<input type="radio" style="width:30px" name="urgency" value="2" ${taskOrder.urgency == 2 ? 'checked' : ''}   ${taskOrder.urgencyState == 0 ? 'disabled' : ''}/>超紧急
						</span>
					</td>
				</tr>
					<tr>
						<th>芯片版本</th>
						<td colspan="2"><input name="microchipsVersion"
							value="${taskOrder.microchipsVersion}"  /></td>
					</tr>
					<tr>
						<th>圆片批次</th>
						<td colspan="2"><input name="reductionNo"
							value="${taskOrder.reductionNo}"  style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>单粒子指标</th>
						<td colspan="2"><input name="singleionsIndex"
							value="<c:out value="${taskOrder.singleionsIndex}" escapeXml="true"/>" style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>总剂量指标</th>
						<td colspan="2"><input name="totalDoseIndex"
							value="${taskOrder.totalDoseIndex}"   style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>委托监督单位</th>
						<td colspan="2"><input name="entrustedUnits"
							value="${taskOrder.entrustedUnits}"  style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>详细规范编号\版本\日期</th>
						<td colspan="2"><input name="detailSpecification"
							value="${taskOrder.detailSpecification}" style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>详细规范状态</th>
						<td colspan="2"><input type="checkbox" id="checkbox1"
							style="vertical-align:middle;width:30px"
							name="detailSpecificationStatus" value="受控稿"
							${fn:contains(taskOrder.detailSpecificationStatus, '受控稿')? 'checked' : ''}><label
							style="vertical-align:middle;display:inline-block;font-size:12px;"
							for="checkbox1">受控稿</label> <input type="checkbox" id="checkbox2"
							style="vertical-align:middle;width:30px"
							name="detailSpecificationStatus" value="提前生产稿"
							${fn:contains(taskOrder.detailSpecificationStatus, '提前生产稿')? 'checked' : ''}><label
							style="vertical-align:middle;display:inline-block;font-size:12px;"
							for="checkbox2">提前生产稿</label> <input type="checkbox"
							id="checkbox3" style="vertical-align:middle;width:30px"
							name="detailSpecificationStatus" value="初稿"
							${fn:contains(taskOrder.detailSpecificationStatus, '初稿')? 'checked' : ''} ><label
							style="vertical-align:middle;display:inline-block;font-size:12px;"
							for="checkbox3">初稿</label> <input type="checkbox" id="checkbox4"
							style="vertical-align:middle;width:30px"
							name="detailSpecificationStatus" value="电路说明文件"
							${fn:contains(taskOrder.detailSpecificationStatus, '电路说明文件')? 'checked' : ''}><label
							style="vertical-align:middle;display:inline-block;font-size:12px;"
							for="checkbox4">电路说明文件</label>
						</td>
					</tr>
					<tr>
						<th>用户单位</th>
						<td colspan="2"><input name="userUnits"
							value="${taskOrder.userUnits }" style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>工程型号</th>
						<td colspan="2"><input name="workModel"
							value="${taskOrder.workModel }"  style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th colspan="3">试验样品信息（可在提供试验样品时再填写）</th>
						<th>试验样品提供人</th>
					</tr>
					<tr>
						<th>单粒子样品编号</th>
						<td colspan="2"><input name="singleionsNo"
							value="${taskOrder.singleionsNo}" style="width:100%;font-size: 12px" /></td>
						<td>提&nbsp;&nbsp;供&nbsp;&nbsp;人&nbsp;&nbsp;&nbsp;:<input
							name="testSampleSplMember" value="${taskOrder.testSampleSplMember}"  style="width:70%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>单粒子备用样品编号</th>
						<td colspan="2"><input name="singleionsSpareNo"
							value="${taskOrder.singleionsSpareNo}" style="width:100%;font-size: 12px" /></td>
						<td>提供日期&nbsp;&nbsp;&nbsp;:<input id="testSampleSplDate"
							name="testSampleSplDate" class="easyui-validatebox"
							onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
							style="width:70%;"
							value="<fmt:formatDate value="${taskOrder.testSampleSplDate}" pattern="yyyy-MM-dd" />" />
						</td>
					</tr>
					<tr>
						<th>总剂量样品编号</th>
						<td colspan="2"><input name="totalDoseNo"
							value="${taskOrder.totalDoseNo}" style="width:100%;font-size: 12px" /></td>
						<td>单剂量产值:<input name="singleProVal" class="easyui-numberbox"
							style="width:70%;font-size: 12px" value="${taskOrder.singleProVal }"/></td>
					</tr>
					<tr>
						<th>总剂量对比样品编号</th>
						<td colspan="2"><input name="totalDoseCompareNo"
							value="${taskOrder.totalDoseCompareNo}" style="width:100%;font-size: 12px" /></td>
						<td>总剂量产值:<input name="totalProVal" class="easyui-numberbox"
							style="width:70%;font-size: 12px" value="${taskOrder.totalProVal }"/></td>
					</tr>
					<tr>
						<td colspan="6">
							<table width="100%">
								<tr>
									<td>编写：</td>
									<td><input name="projectManager"
										value="${taskOrder.projectManager}"
										style="width:70%;font-size: 12px" /></td>
									<td>初核：</td>
									<td><input name="productManager"
										style="width:70%;font-size: 12px" value="${taskOrder.productManager}"/></td>
									<td>校对：</td>
									<td><input name="testManager"
										value="${taskOrder.testManager}"
										style="width:70%;font-size: 12px" /></td>
									<td>接受：</td>
									<td><input name="radiationLeader"
										value="${taskOrder.radiationLeader}"
										style="width:70%;font-size: 12px" /></td>
								</tr>
								<tr>
									<td>日期：</td>
									<td><input name="projectManagerTime"
										class="easyui-validatebox"
										onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
										style="width:70%;font-size: 12px"
										value="<fmt:formatDate value="${taskOrder.projectManagerTime}" pattern="yyyy-MM-dd" />" />
									</td>
									<td>日期：</td>
									<td><input name="productManagerTime"
										class="easyui-validatebox"
										onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
										style="width:70%;font-size: 12px"
										value="<fmt:formatDate value="${taskOrder.productManagerTime}" pattern="yyyy-MM-dd" />" />
									</td>
									<td>日期：</td>
									<td><input name="testManagerTime"
										class="easyui-validatebox"
										onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
										style="width:70%;font-size: 12px"
										value="<fmt:formatDate value="${taskOrder.testManagerTime}" pattern="yyyy-MM-dd" />" />
									</td>
									<td>日期：</td>
									<td><input name="radiationLeaderTime"
										class="easyui-validatebox"
										onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
										style="width:70%;font-size: 12px"
										value="<fmt:formatDate value="${taskOrder.radiationLeaderTime}" pattern="yyyy-MM-dd" />" />
									</td>
								</tr>
								<tr>
									<td colspan="2">（项目负责人）</td>
									<td colspan="2">（科生一部调度）</td>
									<td colspan="2">（抗加部门调度）</td>
									<td colspan="2">（辐射试验组长）</td>
								</tr>
							</table></td>
					</tr>
					<tr>
						<td colspan=4 style="text-align:center"><a
							id="radiation_confirm_submit" class="l-btn"
							href="javascript:void(0);" onclick="javascript:confirmOKSubmit()">
								<span class="l-btn-left"> <span
									class="l-btn-text icon-save l-btn-icon-left"
									style="padding-left: 20px; ">审核通过</span> </span> </a> <a
							id="radiation_confirm_ng" class="l-btn"
							href="javascript:void(0);" onclick="javascript:confirmNGSubmit()">
								<span class="l-btn-left"> <span
									class="l-btn-text icon-cancel l-btn-icon-left"
									style="padding-left: 20px; ">审核不通过</span> </span> </a> <a
							id="radiation_confirm_close" class="l-btn"
							href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
								<span class="l-btn-left"> <span
									class="l-btn-text icon-cancel l-btn-icon-left"
									style="padding-left: 20px; ">关闭</span> </span> </a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
