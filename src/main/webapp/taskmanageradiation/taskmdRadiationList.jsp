<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#taskmdRadiationOrder_list_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/taskManagemdRadiation/list',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			idField : 'orderId',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'createtime',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true,
			columns : [ [ 
			    {field : 'orderId',title : '序号',hidden:true,width : 80}, 
			    {field : 'lsh',title : '任务单号',width : 180}, 
			    {field : 'statusName',title : '任务单状态',width : 100,sortable : true},
			    {field : 'circuitName',title : '电路名称',width : 100,sortable : true},
			    {field : 'circuitType',title : '电路型号',width : 120},
			    {field : 'microchipsVersion',title : '芯片版本',width : 100,sortable : true}, 
			    {field : 'reductionNo',title : '圆片批次',width : 80,sortable : true},
			    {field : 'singleionsIndex',title : '单粒子指标',width : 100,sortable : true}, 
			    {field : 'totalDoseIndex',title : '总剂量指标',width : 100}, 
			    {field : 'testSampleSplMember',title : '样品提供人',width : 150}, 
			    {field : 'testSampleSplDate',title : '样品提供日期',width : 120,sortable : true},
			    {field : 'createtime',title : '创建日期',width : 150,sortable : true}, 
			    {field : 'action',title : '操作',width : 180,formatter : formatZxcxOperation} 
			    ] ],
		    onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
	});
	function formatZxcxOperation(value, row, index){
		var str = '';
			str += formatString('<span onclick="taskmdRadiationOrder_list_detail(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>详情</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			if(row.confirmState==1){ 
				str += '&nbsp;';
				str += formatString('<span onclick="taskmdRadiationOrder_list_confirm(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>审核</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.editState==1){ 
				str += '&nbsp;';
				str += formatString('<span onclick="taskmdRadiationOrder_list_edit(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>修改</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.priceState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="taskmdRadiationOrder_list_price(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>核价</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.priceEditState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="taskmdRadiationOrder_list_priceEdit(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>修改</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.fixState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="taskmdRadiationOrder_list_fix(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>确认</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.deleteState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="taskmdRadiationOrder_list_delete(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>删除</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
		return str;
	}
	function taskmdRadiationOrder_list_detail(id) {
		layout_center_addTabFun({
			title : '任务单详细页',
			closable : true,
			href : '${pageContext.request.contextPath}/taskManagemdRadiation/detailPage?id='+id
		});
	}
	function taskmdRadiationOrder_list_confirm(id) {
		layout_center_addTabFun({
			title : '任务单审核页',
			closable : true,
			href : '${pageContext.request.contextPath}/taskManagemdRadiation/confirmPage?id='+id
		});
	}
	function taskmdRadiationOrder_list_edit(id) {
		layout_center_addTabFun({
			title : '任务单修改页',
			closable : true,
			href : '${pageContext.request.contextPath}/taskManagemdRadiation/editPage?id='+id
		});
	}
	function taskmdRadiationOrder_list_price(id) {
		layout_center_addTabFun({
			title : '任务单核价页',
			closable : true,
			href : '${pageContext.request.contextPath}/taskManagemdRadiation/pricePage?id='+id
		});
	}
	function taskmdRadiationOrder_list_priceEdit(id) {
		layout_center_addTabFun({
			title : '任务单核价修改页',
			closable : true,
			href : '${pageContext.request.contextPath}/taskManagemdRadiation/pricePageEdit?id='+id
		});
	}
	function taskmdRadiationOrder_list_fix(id) {
		layout_center_addTabFun({
			title : '任务单确认页',
			closable : true,
			href : '${pageContext.request.contextPath}/taskManagemdRadiation/fixPage?id='+id
		});
	}
	function taskmdRadiationList_searchFun() {
		var startTime = $('#task_mdradiation_registTimeStart').val();
		var endTime = $('#task_mdradiation_registTimeEnd').val();
		if(comparetime(startTime,endTime)){
			$('#taskmdRadiationOrder_list_datagrid').datagrid('load', serializeObject($('#taskmdRadiationList_searchForm')));
		}else{
			$.messager.show({
				title : '提示',
				msg : "开始时间大于结束时间"
			});
		}
	}
	function taskmdRadiationOrder_list_delete(id) {
		$.messager.confirm('确认', '您是否要删除该任务单？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/taskManagemdRadiation/removeTaskOrder?id='+id,
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$('#taskmdRadiationOrder_list_datagrid').datagrid('load');
						}
						$.messager.show({
							title : '提示',
							msg : result.msg
						});
					}
				});
			}
		});
	}
	function taskmdRadiationList_downloadFun() {
		var startTime = $('#task_mdradiation_registTimeStart').val();
		var endTime = $('#task_mdradiation_registTimeEnd').val();
		if(comparetime(startTime,endTime)){
			$('#taskmdRadiationList_searchForm').attr('action','${pageContext.request.contextPath}/taskManagemdRadiation/downloadExcel');
			$('#taskmdRadiationList_searchForm').submit();
		}else{
			$.messager.show({
				title : '提示',
				msg : "开始时间大于结束时间"
			});
		}
	}
	function taskmdRadiationList_cleanFun() {
		$('#taskmdRadiationList_searchForm input').val('');
		$('#taskmdRadiationOrder_list_datagrid').datagrid('load', {});
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false" style="height: 150px;overflow: hidden;" align="center">
		<form id="taskmdRadiationList_searchForm" method="post" >
			<table align="center" class="tableFormcx" cellSpacing=0 cellPadding=5 style="margin-top: 10px;">
				<tr>
					<td style="width: 60px;">电路名称</td>
					<td><input name="circuitName" /></td>
					<td style="width: 60px;">电路型号</td>
					<td><input name="circuitType" /></td>
					<td>任务单号</td>
					<td><input name="lsh" /></td>
				</tr>
				<tr>
					<td style="width: 60px;">任务单状态</td>
					<td>
						<select style="width:130px" name="OrderStatus">
							<option value ="0">请选择</option>
							<option value ="1">处理中</option>
							<option value ="2">已完成</option>
						</select>
					</td>
					<td style="width: 60px; text-align: right;">创建时间起</td>
					<td><input id="task_mdradiation_registTimeStart" name="registTimeStart" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
					<td style="width: 60px; text-align: right;">创建时间止</td>
					<td><input id="task_mdradiation_registTimeEnd" name="registTimeEnd" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center;">
						<div>
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="taskmdRadiationList_searchFun();return false;">查询</a>&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="taskmdRadiationList_downloadFun();return false;">导出数据</a>&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="taskmdRadiationList_cleanFun();return false;">清空条件</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="taskmdRadiationOrder_list_datagrid"></table>
	</div>
</div>