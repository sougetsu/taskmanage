<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#taskorder_list_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/taskManageTest/list',
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
				{field : 'action',title : '操作',width : 180,formatter : formatZxcxOperation},
			    {field : 'orderId',title : '序号',hidden:true,width : 80}, 
			    {field : 'lsh',title : '任务单号',width : 130}, 
			    {field : 'projectName',title : '项目名称',width : 100,sortable : true},
			    {field : 'internalModel',title : '所内型号',width : 120},
			    {field : 'applyDept',title : '申请部门',width : 100,sortable : true}, 
			    {field : 'applyMember',title : '申请人',width : 80,sortable : true},
			    {field : 'topicNo',title : '课题号',width : 100,sortable : true}, 
			    {field : 'projectManager',title : '项目负责人',width : 100}, 
			    {field : 'createtime',title : '登记日期',width : 150,sortable : true}, 
			    {field : 'wantedEndDate',title : '希望完成时间',width : 120,sortable : true},
			    {field : 'statusName',title : '任务单状态',width : 100,sortable : true},
			    {field : 'sumPrice',title : '价格',width : 100,sortable : true}
			    ] ],
		    onLoadSuccess : function() {
				parent.$.messager.progress('close');
			},
			onDblClickRow: function (rowIndex, rowData) { 
				taskorder_list_detail(rowData.orderId);
		    }
		});
	});
	function formatZxcxOperation(value, row, index){
		var str = '';
			str += formatString('<span onclick="taskorder_list_detail(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>详情</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/information.png');
			if(row.confirmState==1){ 
				str += '&nbsp;';
				str += formatString('<span onclick="taskorder_list_confirm(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>审核</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/comment_edit.png');
			}
			if(row.editState==1){ 
				str += '&nbsp;';
				str += formatString('<span onclick="taskorder_list_edit(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>修改</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.priceState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="taskorder_list_price(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>核价</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.priceEditState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="taskorder_list_priceEdit(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>修改</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.fixState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="taskorder_list_fix(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>确认</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/disk.png');
			}
			if(row.deleteState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="taskorder_list_delete(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>删除</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/delete.png');
			}
		return str;
	}
	function taskorder_list_detail(id) {
		layout_center_addTabFun({
			title : '任务单详细页',
			closable : true,
			href : '${pageContext.request.contextPath}/taskManageTest/detailPage?id='+id
		});
	}
	function taskorder_list_confirm(id) {
		layout_center_addTabFun({
			title : '任务单审核页',
			closable : true,
			href : '${pageContext.request.contextPath}/taskManageTest/confirmPage?id='+id
		});
	}
	function taskorder_list_edit(id) {
		layout_center_addTabFun({
			title : '任务单修改页',
			closable : true,
			href : '${pageContext.request.contextPath}/taskManageTest/editPage?id='+id
		});
	}
	function taskorder_list_price(id) {
		layout_center_addTabFun({
			title : '任务单核价页',
			closable : true,
			href : '${pageContext.request.contextPath}/taskManageTest/pricePage?id='+id
		});
	}
	function taskorder_list_priceEdit(id) {
		layout_center_addTabFun({
			title : '任务单核价修改页',
			closable : true,
			href : '${pageContext.request.contextPath}/taskManageTest/pricePageEdit?id='+id
		});
	}
	function taskorder_list_fix(id) {
		layout_center_addTabFun({
			title : '任务单确认页',
			closable : true,
			href : '${pageContext.request.contextPath}/taskManageTest/fixPage?id='+id
		});
	}
	function taskList_searchFun() {
		var startTime = $('#bz_zxcx_registTimeStart').val();
		var endTime = $('#bz_zxcx_registTimeEnd').val();
		if(comparetime(startTime,endTime)){
			$('#taskorder_list_datagrid').datagrid('load', serializeObject($('#taskList_searchForm')));
		}else{
			$.messager.show({
				title : '提示',
				msg : "开始时间大于结束时间"
			});
		}
	}
	function taskorder_list_delete(id) {
		$.messager.confirm('确认', '您是否要删除该任务单？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/taskManageTest/removeTaskOrder?id='+id,
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$('#taskorder_list_datagrid').datagrid('load');
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
	function taskList_downloadFun() {
		var startTime = $('#bz_zxcx_registTimeStart').val();
		var endTime = $('#bz_zxcx_registTimeEnd').val();
		if(comparetime(startTime,endTime)){
			$('#taskList_searchForm').attr('action','${pageContext.request.contextPath}/taskManageTest/downloadExcel');
			$('#taskList_searchForm').submit();
		}else{
			$.messager.show({
				title : '提示',
				msg : "开始时间大于结束时间"
			});
		}
	}
	function taskList_cleanFun() {
		$('#taskList_searchForm input').val('');
		$('#taskorder_list_datagrid').datagrid('load', {});
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false" style="height: 150px;overflow: hidden;" align="center">
		<form id="taskList_searchForm" method="post" >
			<table align="center" class="tableFormcx" cellSpacing=0 cellPadding=5 style="margin-top: 10px;">
				<tr>
					<td style="width: 60px;">项目名称</td>
					<td><input name="projectName" /></td>
					<td style="width: 60px;">申&nbsp;&nbsp;请&nbsp;&nbsp;人</td>
					<td><input name="applyMember" /></td>
					<td>所内型号</td>
					<td><input name="internalModel" /></td>
				</tr>
				<tr>
					<td style="width: 60px;">任务单号</td>
					<td><input name="lsh" /></td>
					<td style="width: 60px; text-align: right;">创建时间起</td>
					<td><input id="bz_zxcx_registTimeStart" name="registTimeStart" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
					<td style="width: 60px; text-align: right;">创建时间止</td>
					<td><input id="bz_zxcx_registTimeEnd" name="registTimeEnd" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center;">
						<div>
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="taskList_searchFun();return false;">查询</a>&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="taskList_downloadFun();return false;">导出数据</a>&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="taskList_cleanFun();return false;">清空条件</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="taskorder_list_datagrid"></table>
	</div>
</div>
<div id="zxcx_detail"></div>
<div id="zxcx_review"></div>
<div id="zxcx_cancel"></div>