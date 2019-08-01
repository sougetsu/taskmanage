<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
		$(function() {
		parent.$.messager.progress('close');
		
	});
	function linkUrl(value, row, index){
		var str = '';
		str += formatString('<span onclick="radiation_list_detail(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>value</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
		return str;
	}
	function formatZxcxOperation(value, row, index){
		var str = '';
			str += formatString('<span onclick="taskorder_list_detail(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>详情</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			if(row.editState==1){ 
				str += '&nbsp;';
				str += formatString('<span onclick="taskorder_list_edit(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>修改</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
			if(row.deleteState==1){
				str += '&nbsp;';
				str += formatString('<span onclick="taskorder_list_delete(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>删除</span>', row.orderId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			}
		return str;
	}
	function radiation_list_detail(id) {
		layout_center_addTabFun({
			title : '任务单详细页',
			closable : true,
			href : '${pageContext.request.contextPath}/radiation/detailPage?id='+id
		});
	}
	function taskorder_list_edit(id) {
		layout_center_addTabFun({
			title : '任务单修改页',
			closable : true,
			href : '${pageContext.request.contextPath}/taskManage/editPage?id='+id
		});
	}
	function radiationList_searchFun() {
		$('#radiationList').datagrid('load', serializeObject($('#radiationList_searchForm')));
	}
	function taskorder_list_delete(id) {
		$.messager.confirm('确认', '您是否要删除该任务单？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/taskManage/removeTaskOrder?id='+id,
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
	function radiationList_downloadFun() {
		var startTime = $('#bz_zxcx_registTimeStart').val();
		var endTime = $('#bz_zxcx_registTimeEnd').val();
		if(comparetime(startTime,endTime)){
			$('#taskList_searchForm').attr('action','${pageContext.request.contextPath}/taskManage/downloadExcel');
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
		<form id="radiationList_searchForm" method="post" >
			<table align="center" class="tableFormcx" cellSpacing=0 cellPadding=5 style="margin-top: 10px;">
				<tr>
					<td style="width: 60px;">课题类别:</td>
					<td>
						<input name="ktlb" class="easyui-combotree"  data-options="url:'${pageContext.request.contextPath}/dictionary/topicList',parentField : 'pid',required:'true'"
						lines="true" cascadeCheck="false" />
					</td>
					<td style="width: 60px;">电路类别:</td>
					<td><input name="dllb" /></td>
					<td>研发部门:</td>
					<td><input name="yfbm" /></td>
					<td>卫星型号:</td>
					<td><input name="wxxh" /></td>
				</tr>
				<tr>
					<td style="width: 60px;">工艺尺寸:</td>
					<td><input name="gycc" /></td>
					<td style="width: 60px;">生产厂家:</td>
					<td><input name="sccj" /></td>
					<td>制造工艺:</td>
					<td><input name="zzgy" /></td>
					<td>双极工艺:</td>
					<td><input name="sjgy" /></td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center;">
						<div>
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="radiationList_searchFun();return false;">查询</a>&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="radiationList_downloadFun();return false;">导出数据</a>&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="radiationList_cleanFun();return false;">清空条件</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="radiation_list_datagrid"></table>
	</div>
</div>