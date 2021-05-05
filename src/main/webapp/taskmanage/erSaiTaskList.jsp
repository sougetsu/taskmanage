<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#erSai_taskorder_list_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/erSaiTaskManage/list',
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
			},
			onDblClickRow: function (rowIndex, rowData) { 
				erSai_taskorder_list_detail(rowData.orderId);
		    }
		});
	});
	function formatZxcxOperation(value, row, index){
		var str = '';
			str += formatString('<span onclick="erSai_taskorder_list_detail(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>详情</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			if(row.confirmState==1){ 
				str += '&nbsp;';
				str += formatString('<span onclick="erSai_taskorder_list_confirm(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>审核</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.editState==1){ 
				str += '&nbsp;';
				str += formatString('<span onclick="erSai_taskorder_list_edit(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>修改</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.priceState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="erSai_taskorder_list_price(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>核价</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.priceEditState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="erSai_taskorder_list_priceEdit(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>修改</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.fixState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="erSai_taskorder_list_fix(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>确认</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.deleteState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="erSai_taskorder_list_cancel(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>取消</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.deleteState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="erSai_taskorder_list_delete(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>删除</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
		return str;
	}
	function erSai_taskorder_list_detail(id) {
		layout_center_addTabFun({
			title : '二筛任务详细页',
			closable : true,
			href : '${pageContext.request.contextPath}/erSaiTaskManage/detailPage?id='+id
		});
	}
	function erSai_taskorder_list_confirm(id) {
		layout_center_addTabFun({
			title : '二筛任务审核页',
			closable : true,
			href : '${pageContext.request.contextPath}/erSaiTaskManage/confirmPage?id='+id
		});
	}
	function erSai_taskorder_list_edit(id) {
		layout_center_addTabFun({
			title : '二筛任务修改页',
			closable : true,
			href : '${pageContext.request.contextPath}/erSaiTaskManage/editPage?id='+id
		});
	}
	function erSai_taskorder_list_price(id) {
		layout_center_addTabFun({
			title : '二筛任务核价页',
			closable : true,
			href : '${pageContext.request.contextPath}/erSaiTaskManage/pricePage?id='+id
		});
	}
	function erSai_taskorder_list_priceEdit(id) {
		layout_center_addTabFun({
			title : '二筛任务核价修改页',
			closable : true,
			href : '${pageContext.request.contextPath}/erSaiTaskManage/pricePageEdit?id='+id
		});
	}
	function erSai_taskorder_list_fix(id) {
		layout_center_addTabFun({
			title : '二筛任务确认页',
			closable : true,
			href : '${pageContext.request.contextPath}/erSaiTaskManage/fixPage?id='+id
		});
	}
	function erSai_taskList_searchFun() {
		var startTime = $('#erSai_taskList_registTimeStart').val();
		var endTime = $('#erSai_taskList_registTimeEnd').val();
		if(comparetime(startTime,endTime)){
			$('#erSai_taskorder_list_datagrid').datagrid('load', serializeObject($('#erSai_taskList_searchForm')));
		}else{
			$.messager.show({
				title : '提示',
				msg : "开始时间大于结束时间"
			});
		}
	}
	function erSai_taskorder_list_cancel(id) {
		$.messager.confirm('确认', '您是否要取消该任务单？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/erSaiTaskManage/cancelTaskOrder?id='+id,
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$('#erSai_taskorder_list_datagrid').datagrid('load');
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
	function erSai_taskorder_list_delete(id) {
		$.messager.confirm('确认', '您是否要删除该任务单？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/erSaiTaskManage/removeTaskOrder?id='+id,
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$('#erSai_taskorder_list_datagrid').datagrid('load');
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
	function erSai_taskList_downloadFun() {
		var startTime = $('#erSai_taskList_registTimeStart').val();
		var endTime = $('#erSai_taskList_registTimeEnd').val();
		if(comparetime(startTime,endTime)){
			$('#erSai_taskList_searchForm').attr('action','${pageContext.request.contextPath}/erSaiTaskManage/downloadExcel');
			$('#erSai_taskList_searchForm').submit();
		}else{
			$.messager.show({
				title : '提示',
				msg : "开始时间大于结束时间"
			});
		}
	}
	function erSai_taskList_cleanFun() {
		$('#erSai_taskList_searchForm input').val('');
		$('#erSai_taskorder_list_datagrid').datagrid('load', {});
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false" style="height: 150px;overflow: hidden;" align="center">
		<form id="erSai_taskList_searchForm" method="post" >
			<table align="center" class="tableFormcx" cellSpacing=0 cellPadding=5 style="margin-top: 10px;">
				<tr>
					<td style="width: 60px;">通知单号</td>
					<td><input name="reportNo" /></td>
					<td style="width: 60px;">申&nbsp;&nbsp;请&nbsp;&nbsp;人</td>
					<td><input name="applyMember" /></td>
					<td>任务类型</td>
					<td><input name="taskType" /></td>
				</tr>
				<tr>
					<td style="width: 60px;">任务单号</td>
					<td><input name="lsh" /></td>
					<td style="width: 60px; text-align: right;">创建时间起</td>
					<td><input id="erSai_taskList_registTimeStart" name="registTimeStart" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
					<td style="width: 60px; text-align: right;">创建时间止</td>
					<td><input id="erSai_taskList_registTimeEnd" name="registTimeEnd" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center;">
						<div>
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="erSai_taskList_searchFun();return false;">查询</a>&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="erSai_taskList_downloadFun();return false;">导出数据</a>&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="erSai_taskList_cleanFun();return false;">清空条件</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="erSai_taskorder_list_datagrid"></table>
	</div>
</div>