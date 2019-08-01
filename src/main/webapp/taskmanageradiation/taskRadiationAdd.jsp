<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
	});
	function checkAndSubmit() {
		var submitForm = $('#taskOrderRadiation_createForm');
		if (submitForm.form('validate')) {
			parent.$.messager.progress({
				title : '提示',
				text : '数据提交中，请稍后....'
			});
			$("#status").val("31");
			$
					.ajax({
						type : "POST",
						url : '${pageContext.request.contextPath}/taskManageRadiation/create',
						data : submitForm.serialize(),
						dataType : 'json',
						success : function(result) {
							parent.$.messager.progress('close');
							if (result.success) {
								$.messager
										.confirm(
												'确认',
												'添加成功，是否继续添加任务单？',
												function(r) {
													if (r) {
														var index = $(
																'#layout_center_tabs')
																.tabs(
																		'getTabIndex',
																		$(
																				'#layout_center_tabs')
																				.tabs(
																						'getSelected'));
														var tab = $(
																'#layout_center_tabs')
																.tabs('getTab',
																		index);
														tab.panel('refresh');
													} else {
														var index = $(
																'#layout_center_tabs')
																.tabs(
																		'getTabIndex',
																		$(
																				'#layout_center_tabs')
																				.tabs(
																						'getSelected'));
														$('#layout_center_tabs')
																.tabs('close',
																		index);
													}
												});
							}
						}
					});
		}
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false">
		<div align="center" style="margin-top:20px;">
			<form id="taskOrderRadiation_createForm" method="post"
				enctype="multipart/form-data">
				<input type="hidden" id="status" name="status">
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
								name="type" value="1" />摸底类 </span>
						</td>
						<td>
							<table>
								<th>课题号</th>
								<td><input name="topicNoName"
									style="width:100%;font-size: 12px" /></td>
							</table>
						</td>
						<td rowspan="17">1、 电路名称一般以《详细规范》标题上的电路名称为准;<br /> 2、
							电路型号一般为电路打标表面的型号;<br /> 3、 务必准确填写《详细规范》版本和状态信息;<br /> 4、
							试验样品信息可以后补，先填上半部分，提交任务单，待样品交接时再填写试验样品信息。</td>
					</tr>
					<tr>
						<th>电路名称</th>
						<td colspan="2"><input id="taskOrderRadiation_circuitName"
							name="circuitName" style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>电路型号</th>
						<td colspan="2"><input id="taskOrderRadiation_circuitType"
							name="circuitType" style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>类别</th>
						<td>单粒子 <span style="white-space:nowrap;"> <input
								type="radio" style="width:30px" name="singleionsFlag" value="1"
								checked="checked" />是 <input type="radio" style="width:30px"
								name="singleionsFlag" value="0" />无 </span>
						</td>
						<td>总剂量 <span style="white-space:nowrap;"> <input
								type="radio" style="width:30px" name="totalDoseFlag" value="1"
								checked="checked" />是 <input type="radio" style="width:30px"
								name="totalDoseFlag" value="0" />无 </span>
						</td>
					</tr>
					<tr>
						<th>生产批次</th>
						<td><input name="singleionsBatch" style="font-size: 12px" />
						</td>
						<td><input name="totalDoseBatch" style="font-size: 12px" />
						</td>
					</tr>
					<tr>
						<th>样品数量</th>
						<td><input name="singleionsSmpNum" style="font-size: 12px" />
						</td>
						<td><input name="totalDoseSmpNum" style="font-size: 12px" />
						</td>
					</tr>
					<tr>
						<th>委托开发测试系统</th>
						<td><input type="radio" style="width:30px"
							name="singleionsTest" value="1" />是 <input type="radio"
							style="width:30px" name="singleionsTest" value="0"
							checked="checked" />否</td>
						<td><input type="radio" style="width:30px"
							name="totalDoseTest" value="1" />是 <input type="radio"
							style="width:30px" name="totalDoseTest" value="0"
							checked="checked" />否</td>
					</tr>
					<tr>
					<th >紧急程度：</th>
						<td >
							<span style="white-space:nowrap;">
							<input type="radio" style="width:30px" name="urgency" value="0" checked="checked" disabled="disabled"/>一般
							<input type="radio" style="width:30px" name="urgency" value="1" disabled="disabled" />紧急
							<input type="radio" style="width:30px" name="urgency" value="2" disabled="disabled"/>超紧急
							</span>
						</td>
					</tr>
					<tr>
						<th>芯片版本</th>
						<td colspan="2"><input name="microchipsVersion"
							style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>圆片批次</th>
						<td colspan="2"><input name="reductionNo"
							style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>单粒子指标</th>
						<td colspan="2"><input name="singleionsIndex"
							style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>总剂量指标</th>
						<td colspan="2"><input name="totalDoseIndex"
							style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>委托监督单位</th>
						<td colspan="2"><input name="entrustedUnits"
							style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>详细规范编号\版本\日期</th>
						<td colspan="2"><input name="detailSpecification"
							style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>详细规范状态</th>
						<td colspan="2"><input type="checkbox" id="checkbox1"
							style="vertical-align:middle;width:30px"
							name="detailSpecificationStatus" value="受控稿"><label
							style="vertical-align:middle;display:inline-block;font-size:12px;"
							for="checkbox1">受控稿</label> <input type="checkbox" id="checkbox2"
							style="vertical-align:middle;width:30px"
							name="detailSpecificationStatus" value="提前生产稿"><label
							style="vertical-align:middle;display:inline-block;font-size:12px;"
							for="checkbox2">提前生产稿</label> <input type="checkbox"
							id="checkbox3" style="vertical-align:middle;width:30px"
							name="detailSpecificationStatus" value="初稿"><label
							style="vertical-align:middle;display:inline-block;font-size:12px;"
							for="checkbox3">初稿</label> <input type="checkbox"
							id="checkbox4" style="vertical-align:middle;width:30px"
							name="detailSpecificationStatus" value="电路说明文件"><label
							style="vertical-align:middle;display:inline-block;font-size:12px;"
							for="checkbox4">电路说明文件</label>
						</td>
					</tr>
					<tr>
						<th>用户单位</th>
						<td colspan="2"><input name="userUnits"
							style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th>工程型号</th>
						<td colspan="2"><input name="workModel"
							style="width:100%;font-size: 12px" /></td>
					</tr>
					<tr>
						<th colspan="3">试验样品信息（可在提供试验样品时再填写）</th>
						<th>试验样品提供人</th>
					</tr>
					<tr>
						<th>单粒子样品编号</th>
						<td colspan="2"><input name="singleionsNo"
							style="width:100%;font-size: 12px" /></td>
						<td>提&nbsp;&nbsp;供&nbsp;&nbsp;人&nbsp;&nbsp;&nbsp;:<input
							name="testSampleSplMember" style="width:70%;font-size: 12px" />
						</td>
					</tr>
					<tr>
						<th>单粒子备用样品编号</th>
						<td colspan="2"><input name="singleionsSpareNo"
							style="width:100%;font-size: 12px" /></td>
						<td>提供日期&nbsp;&nbsp;&nbsp;:<input id="testSampleSplDate"
							name="testSampleSplDate" class="easyui-validatebox"
							onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
							style="width:70%;" /></td>
					</tr>
					<tr>
						<th>总剂量样品编号</th>
						<td colspan="2"><input name="totalDoseNo"
							style="width:100%;font-size: 12px" /></td>
						<td>单剂量产值:<input name="singleProVal" class="easyui-numberbox"
							style="width:70%;font-size: 12px" value=0/></td>
					</tr>
					<tr>
						<th>总剂量对比样品编号</th>
						<td colspan="2"><input name="totalDoseCompareNo"
							style="width:100%;font-size: 12px" /></td>
						<td>总剂量产值:<input name="totalProVal" class="easyui-numberbox"
							style="width:70%;font-size: 12px" value=0/></td>
					</tr>
					<tr>
						<td colspan="6">
							<table width="100%">
								<tr>
									<td>编写：</td>
									<td><input name="projectManager"
										style="width:70%;font-size: 12px" /></td>
									<td>初核：</td>
									<td><input name="productManager"
										style="width:70%;font-size: 12px" /></td>
									<td>校对：</td>
									<td><input name="testManager"
										style="width:70%;font-size: 12px" /></td>
									<td>接受：</td>
									<td><input name="radiationLeader"
										style="width:70%;font-size: 12px" /></td>
								</tr>
								<tr>
									<td>日期：</td>
									<td><input name="projectManagerTime"
										class="easyui-validatebox" 
										onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
										style="width:70%;font-size: 12px" /></td>
									<td>日期：</td>
									<td><input name="productManagerTime"
										class="easyui-validatebox" 
										onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
										style="width:70%;font-size: 12px" /></td>
									<td>日期：</td>
									<td><input name="testManagerTime"
										class="easyui-validatebox" 
										onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
										style="width:70%;font-size: 12px" /></td>
									<td>日期：</td>
									<td><input name="radiationLeaderTime"
										class="easyui-validatebox" 
										onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
										style="width:70%;font-size: 12px" /></td>
								</tr>
								<tr>
									<td colspan="2">（项目负责人）</td>
									<td colspan="2">（科生一部调度）</td>
									<td colspan="2">（抗加部门调度）</td>
									<td colspan="2">（辐射试验组长）</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan=4 style="text-align:center"><a
							id="addradiation_submit" class="l-btn" href="javascript:void(0);"
							onclick="javascript:checkAndSubmit()"> <span
								class="l-btn-left"> <span
									class="l-btn-text icon-save l-btn-icon-left"
									style="padding-left: 20px; ">提交审核</span> </span> </a> <a
							id="addradiation_close" class="l-btn" href="javascript:void(0);"
							onclick="javascript:closeCurrentTab()"> <span
								class="l-btn-left"> <span
									class="l-btn-text icon-cancel l-btn-icon-left"
									style="padding-left: 20px; ">关闭</span> </span> </a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
