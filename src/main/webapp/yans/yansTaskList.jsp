<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#yans_taskorder_list_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/yansTaskManage/list',
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
			    {field : 'lsh',title : '任务单号',width : 150}, 
			    {field : 'applyDept',title : '申请部门',width : 100,sortable : true}, 
			    {field : 'applyMember',title : '申请人',width : 80,sortable : true},
			    {field : 'topicName',title : '课题号',width : 100,sortable : true}, 
			    {field : 'projectManager',title : '项目负责人',width : 100}, 
			    {field : 'createtime',title : '登记日期',width : 150,sortable : true}, 
			    {field : 'wantedEndDate',title : '希望完成时间',width : 120,sortable : true},
			    {field : 'statusName',title : '任务单状态',width : 100,sortable : true},
			    {field : 'sumPrice',title : '价格',width : 100,sortable : true},
			    {field : 'action',title : '操作',width : 200,formatter : formatZxcxOperation} 
			    ] ],
		    onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
	});
	function formatZxcxOperation(value, row, index){
		var str = '';
			str += formatString('<span onclick="yans_taskorder_list_detail(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>详情</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			if(row.confirmState==1){ 
				str += '&nbsp;';
				str += formatString('<span onclick="yans_taskorder_list_confirm(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>审核</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.editState==1){ 
				str += '&nbsp;';
				str += formatString('<span onclick="yans_taskorder_list_edit(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>修改</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.priceState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="yans_taskorder_list_price(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>核价</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.priceEditState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="yans_taskorder_list_priceEdit(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>修改</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.fixState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="yans_taskorder_list_fix(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>确认</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.deleteState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="yans_taskorder_list_delete(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>删除</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
		return str;
	}
	function yans_taskorder_list_detail(id) {
		layout_center_addTabFun({
			title : '验收任务详细页',
			closable : true,
			href : '${pageContext.request.contextPath}/yansTaskManage/detailPage?id='+id
		});
	}
	function yans_taskorder_list_confirm(id) {
		layout_center_addTabFun({
			title : '验收任务审核页',
			closable : true,
			href : '${pageContext.request.contextPath}/yansTaskManage/confirmPage?id='+id
		});
	}
	function yans_taskorder_list_edit(id) {
		layout_center_addTabFun({
			title : '验收任务修改页',
			closable : true,
			href : '${pageContext.request.contextPath}/yansTaskManage/editPage?id='+id
		});
	}
	function yans_taskorder_list_price(id) {
		layout_center_addTabFun({
			title : '验收任务核价页',
			closable : true,
			href : '${pageContext.request.contextPath}/yansTaskManage/pricePage?id='+id
		});
	}
	function yans_taskorder_list_priceEdit(id) {
		layout_center_addTabFun({
			title : '验收任务核价修改页',
			closable : true,
			href : '${pageContext.request.contextPath}/yansTaskManage/pricePageEdit?id='+id
		});
	}
	function yans_taskorder_list_fix(id) {
		layout_center_addTabFun({
			title : '验收任务确认页',
			closable : true,
			href : '${pageContext.request.contextPath}/yansTaskManage/fixPage?id='+id
		});
	}
	function yans_taskList_searchFun() {
		var startTime = $('#yans_registTimeStart').val();
		var endTime = $('#yans_registTimeEnd').val();
		if(comparetime(startTime,endTime)){
			$('#yans_taskorder_list_datagrid').datagrid('load', serializeObject($('#yans_taskList_searchForm')));
		}else{
			$.messager.show({
				title : '提示',
				msg : "开始时间大于结束时间"
			});
		}
	}
	function yans_taskorder_list_delete(id) {
		$.messager.confirm('确认', '您是否要删除该任务单？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/yansTaskManage/removeTaskOrder?id='+id,
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$('#yans_taskorder_list_datagrid').datagrid('load');
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
	function yans_taskList_downloadFun() {
		var startTime = $('#yans_registTimeStart').val();
		var endTime = $('#yans_registTimeEnd').val();
		if(comparetime(startTime,endTime)){
			$('#yans_taskList_searchForm').attr('action','${pageContext.request.contextPath}/yansTaskManage/downloadExcel');
			$('#yans_taskList_searchForm').submit();
		}else{
			$.messager.show({
				title : '提示',
				msg : "开始时间大于结束时间"
			});
		}
	}
	function yans_taskList_cleanFun() {
		$('#yans_taskList_searchForm input').val('');
		$('#yans_taskorder_list_datagrid').datagrid('load', {});
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false" style="height: 150px;overflow: hidden;" align="center">
		<form id="yans_taskList_searchForm" method="post" >
			<table align="center" class="tableFormcx" cellSpacing=0 cellPadding=5 style="margin-top: 10px;">
				<tr>
					<td style="width: 60px;">任务类型</td>
					<td><input name="taskType" /></td>
					<td style="width: 60px;">申&nbsp;&nbsp;请&nbsp;&nbsp;人</td>
					<td><input name="applyMember" /></td>
					<td>通知单号</td>
					<td><input name="reportNo" /></td>
				</tr>
				<tr>
					<td style="width: 60px;">任务单号</td>
					<td><input name="lsh" /></td>
					<td style="width: 60px; text-align: right;">创建时间起</td>
					<td><input id="yans_registTimeStart" name="registTimeStart" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
					<td style="width: 60px; text-align: right;">创建时间止</td>
					<td><input id="yans_registTimeEnd" name="registTimeEnd" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center;">
						<div>
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="yans_taskList_searchFun();return false;">查询</a>&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="yans_taskList_downloadFun();return false;">导出数据</a>&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="yans_taskList_cleanFun();return false;">清空条件</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="yans_taskorder_list_datagrid"></table>
	</div>
</div>