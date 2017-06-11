<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#taskLog_list_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/taskManage/logList',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'logId',
			sortName : 'createtime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true,
			autoRowHeight : false,
			nowrap : false,
			columns : [ [ 
			    {field : 'logId',title : '序号',hidden:true,width : 80}, 
			    {field : 'lshId',title : '任务单号',width : 80}, 
			    {field : 'createtime',title : '时间',width : 80},
			    {field : 'content',title : '详细内容内容',width : 400},
			    ] ],
		    onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
	});
	function taskLog_searchFun() {
		var lshId = $('#lshId').val();
		if(lshId.length!=0){
			$('#taskLog_list_datagrid').datagrid('load', serializeObject($('#taskLog_searchForm')));
		}else{
			$.messager.show({
				title : '提示',
				msg : "请输入任务单号"
			});
		}
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false" style="height: 150px;overflow: hidden;" align="center">
		<form id="taskLog_searchForm" method="post" >
			<table align="center" class="tableFormcx" cellSpacing=0 cellPadding=5 style="margin-top: 10px;">
				<tr>
					<td  style="width: 100px;">请输入任务单号：</td>
					<td><input id="lshId" name="lshId" /></td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center;">
						<div>
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="taskLog_searchFun();return false;">查询</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="taskLog_list_datagrid"></table>
	</div>
</div>