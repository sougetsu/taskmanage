<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
	parent.$.messager.progress('close');
	});
	function checkAndmdSubmit(){
		var submitForm = $('#taskOrdermdRadiation_createForm');
		if(submitForm.form('validate')){
			parent.$.messager.progress({
				title : '提示',
				text : '数据提交中，请稍后....'
			});
			$("#status").val("31");
    		$.ajax({
                type: "POST",
                url:'${pageContext.request.contextPath}/taskManagemdRadiation/create',
                data:submitForm.serialize(),
                dataType : 'json',
				success : function(result) {
					parent.$.messager.progress('close');
					if (result.success) {
						$.messager.confirm('确认', '添加成功，是否继续添加任务单？', function(r) {
							if (r) {
								var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
								var tab = $('#layout_center_tabs').tabs('getTab', index);
								tab.panel('refresh');
							}else{
								var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
								$('#layout_center_tabs').tabs('close', index);
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
		<form id="taskOrdermdRadiation_createForm"method="post" enctype="multipart/form-data">
		<input type="hidden" id="status" name="status">
			<table class="tasktableForm" width="95%" border="1"  bordercolor="#B5C0C4" rules="none" style="border-collapse:collapse;">
				<tr width="980px" style="height: 40px">
					<th width="25%">类别</th>
					<th width="50%" colspan="2">内容</th>
					<th width="25%">相关负责人会签</th>
				</tr>
				<tr>
					<th>电路名称</th>
					<td colspan="2"><input id="taskOrdermdRadiation_circuitName" name="circuitName"  style="width:100%;font-size: 12px"/></td>
					<td rowspan="12" width="380px">1、	电路名称一般以《详细规范》标题上的电路名称为准;<br/>
									2、	电路型号一般为电路打标表面的型号;<br/>
									3、	项目负责人需提供表中所述的《详细规范》,若无请提供电路功能说明文件<br/>
									4、	试验样品信息可以后补，先填上半部分，提交任务单，待样品交接时再填写试验样品信息。
					</td>
				</tr>
				<tr>
					<th>电路型号</th>
					<td colspan="2"><input id="taskOrdermdRadiation_circuitType" name="circuitType"  style="width:100%;font-size: 12px"/></td>
				</tr>
				<tr>
					<th>类别</th>
					<td>单粒子
						<span style="white-space:nowrap;">
							<input type="radio" style="width:30px" name="singleionsFlag" value="1" checked="checked" />是
							<input type="radio" style="width:30px" name="singleionsFlag" value="0"/>无
						</span>
					</td>
					<td>总剂量
						<span style="white-space:nowrap;">
							<input type="radio" style="width:30px" name="totalDoseFlag" value="1" checked="checked" />是
							<input type="radio" style="width:30px" name="totalDoseFlag" value="0"/>无
						</span>
					</td>
				</tr>
				<tr>
					<th>生产批次</th>
					<td><input  name="singleionsBatch"  style="width:330px;font-size: 12px"/></td>
					<td><input  name="totalDoseBatch"  style="width:330px;font-size: 12px"/></td>
				</tr>
				<tr>
					<th>样品数量</th>
					<td><input  name="singleionsSmpNum"  style="width:330px;font-size: 12px"/></td>
					<td><input  name="totalDoseSmpNum"  style="width:330px;font-size: 12px"/></td>
				</tr>
				<tr>
					<th>委托开发测试系统</th>
					<td>
						<input type="radio" style="width:30px" name="singleionsTest" value="1" />是
						<input type="radio" style="width:30px" name="singleionsTest" value="0" checked="checked"/>否
					</td>
					<td>
						<input type="radio" style="width:30px" name="totalDoseTest" value="1"  />是
						<input type="radio" style="width:30px" name="totalDoseTest" value="0" checked="checked"/>否
					</td>
				</tr>
				<tr>
					<th>芯片版本</th>
					<td colspan="2"><input name="microchipsVersion"  style="width:100%;font-size: 12px"/></td>
				</tr>
				<tr>
					<th>圆片批次</th>
					<td colspan="2"><input name="reductionNo"  style="width:100%;font-size: 12px"/></td>
				</tr>
				<tr>
					<th>单粒子指标（单粒子试验要求）</th>
					<td colspan="2"><input name="singleionsIndex"  style="width:100%;font-size: 12px"/></td>
				</tr>
				<tr>
					<th>总剂量指标（总剂量试验要求）</th>
					<td colspan="2"><input name="totalDoseIndex"  style="width:100%;font-size: 12px"/></td>
				</tr>
				<tr>
					<th>详细编号\版本\日期</th>
					<td colspan="2"><input name="detailSpecification"  style="width:100%;font-size: 12px"/></td>
				</tr>
				<tr>
					<th>详细规范状态(无详规可提供电路说明测试文件)
					</th>
					<td colspan="2">
						<input type="checkbox" id="checkbox1" style="vertical-align:middle;width:30px" name="detailSpecificationStatus" value="受控稿" ><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox1">受控稿</label>
						<input type="checkbox" id="checkbox2" style="vertical-align:middle;width:30px" name="detailSpecificationStatus" value="提前生产稿" ><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox2">提前生产稿</label>
						<input type="checkbox" id="checkbox3" style="vertical-align:middle;width:30px" name="detailSpecificationStatus" value="初稿" ><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox3">初稿</label>
						<input type="checkbox" id="checkbox4" style="vertical-align:middle;width:30px" name="detailSpecificationStatus" value="电路说明文件" ><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox4">电路说明文件</label>
					</td>
				</tr>
				<tr>
					<th colspan="3">试验样品信息（可在提供试验样品时再填写）</th>
					<th rowspan="2">试验样品提供人</th>
				</tr>
				<tr>
					<th>单粒子样品编号（初样无编号的请在芯片铅笔编号）</th>
					<td colspan="2"><input name="singleionsNo"  style="width:100%;font-size: 12px"/></td>
				</tr>
				<tr>
					<th>单粒子备用样品编号</th>
					<td colspan="2"><input name="singleionsSpareNo"  style="width:100%;font-size: 12px"/></td>
					<td>提供人:<input name="testSampleSplMember"  style="width:70%;font-size: 12px"/></td>
				</tr>
				<tr>
					<th>总剂量样品编号（初样无编号的请在芯片铅笔编号）</th>
					<td colspan="2"><input name="totalDoseNo"  style="width:100%;font-size: 12px"/></td>
					<td>日&nbsp;&nbsp;&nbsp;&nbsp;期:<input id="testSampleSplDate" name="testSampleSplDate" class="easyui-validatebox" data-options="required:true"
									onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" style="width:70%;"/></td>
				</tr>
				<tr>
					<th>总剂量对比样品编号</th>
					<td colspan="2"><input name="totalDoseCompareNo"  style="width:100%;font-size: 12px"/></td>
					<td>产&nbsp;&nbsp;&nbsp;&nbsp;值::<input name="proVal"  class="easyui-numberbox" style="width:70%;font-size: 12px"/></td>			
				</tr>
				<tr>
			         <td colspan=4 style="text-align:center">
			         	<a id="addmdradiation_submit" class="l-btn" href="javascript:void(0);" onclick="javascript:checkAndmdSubmit()">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">提交审核</span>
			         		</span>
			         	</a>
			         	<a id="addradiation_close" class="l-btn" href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
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
