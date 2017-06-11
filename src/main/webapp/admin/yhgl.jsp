<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/member/create')}">
	<script type="text/javascript">
		$.canAdd = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/member/detele')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/member/modify')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/member/modifyPwd')}">
	<script type="text/javascript">
		$.canEditPwd = true;
	</script>
</c:if>
<script type="text/javascript">
	$(function() {
		$('#admin_yhgl_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/member/list',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'memberId',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'registerDate',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true,
			frozenColumns : [ [ 
			    {field : 'memberId',title : '编号',width : 150,hidden : true}, 
			    {field : 'loginName',title : '用户名',width : 80,sortable : true} 
			    ] ],
			columns : [ [  
			    {field : 'registerDate',title : '创建时间',width : 150}, 
			    {field : 'lastLoginDate',title : '最后登录时间',width : 150,sortable : true}, 
			    {field : 'roleIds',title : '所属角色ID',width : 150,hidden : true}, 
			    {field : 'roleNames',title : '所属角色名称',width : 150}, 
			    {field : 'departmentName',title : '所属部门',width : 150}, 
			    {field : 'statusName',title : '状态',width : 150},
			    {field : 'status',title : '状态ID',width : 150,hidden : true},
			    {field : 'mailAddress',title : '邮件地址',width : 150}, 
			    {field : 'action',title : '动作',width : 100,formatter : formatYhglOperation}
			    ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					if ($.canAdd) {
						admin_yhgl_appendFun();
					}else{
						$.messager.alert('提示', "您没有添加用户权限！");
					}
				}
			}],
			onLoadSuccess : function() {
				$('#admin_yhgl_searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			},
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll').datagrid('uncheckAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
	});
	function formatYhglOperation(value, row, index){
		var str = '';
		if ($.canEdit) {
			str += $.formatString('<img onclick="admin_yhgl_editFun(\'{0}\');" src="{1}" title="编辑"/>', row.memberId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
		}
		str += '&nbsp;';
		if ($.canDelete) {
			if(row.status!=0){
				str += $.formatString('<img onclick="admin_yhgl_removeFun(\'{0}\');" src="{1}" title="删除"/>', row.memberId, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
			}else{
				str += $.formatString('<img onclick="admin_yhgl_resumeFun(\'{0}\');" src="{1}" title="恢复"/>', row.memberId, '${pageContext.request.contextPath}/style/images/extjs_icons/book_previous.png');
			}
		}
		str += '&nbsp;';
		if ($.canEditPwd) {
			str += $.formatString('<img onclick="admin_yhgl_modifyPwdFun(\'{0}\');" src="{1}" title="修改密码"/>', row.memberId, '${pageContext.request.contextPath}/style/images/extjs_icons/lock/lock_edit.png');
		}
		return str;
	}
	function admin_yhgl_searchFun() {
		$('#admin_yhgl_datagrid').datagrid('load', serializeObject($('#admin_yhgl_searchForm')));
	}
	function admin_yhgl_cleanFun() {
		$('#admin_yhgl_searchForm input').val('');
		$('#admin_yhgl_datagrid').datagrid('load', {});
	}
	function admin_yhgl_editFun(id) {
		$('#admin_yhgl_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$("#yhgl_dialog").editDialog({
			id :id,
			width : 620,
			height : 300,
            url : '${pageContext.request.contextPath}/admin/yhglEdit.jsp',
            title :"编辑用户",
            originContain : '#admin_yhgl_datagrid',
            onLoad : function() {
				var index = $('#admin_yhgl_datagrid').datagrid('getRowIndex', id);
				var rows = $('#admin_yhgl_datagrid').datagrid('getRows');
				var o = rows[index];
				o.roleIds = stringToList(rows[index].roleIds);
				$('#admin_yhglEdit_editForm').form('load', o);
			}
        });
	}
	function admin_yhgl_appendFun() {
		$('#admin_yhgl_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$("#yhgl_dialog").editDialog({
			width : 620,
			height : 300,
            url : '${pageContext.request.contextPath}/admin/yhglAdd.jsp',
            title :"增加用户",
            originContain : '#admin_yhgl_datagrid'
        });
	}
	function admin_yhgl_removeFun(id) {
		$.messager.confirm('确认', '您是否要删除当前选中的用户？', function(r) {
			if (r) {
				var currentUserId = '${sessionInfo.userId}';/*当前登录用户的ID*/
				var flag = false;
				if(currentUserId == id){
					parent.$.messager.alert('提示', '不可以删除自己！', 'info');
				}else{
					$.ajax({
						url : '${pageContext.request.contextPath}/member/remove',
						data : {
							memberIds : id
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$('#admin_yhgl_datagrid').datagrid('load');
								$('#admin_yhgl_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
							}
							$.messager.show({
								title : '提示',
								msg : result.msg
							});
						}
					});
				}
			}
		});
	}
	function admin_yhgl_resumeFun(id) {
		$.messager.confirm('确认', '您是否要恢复当前选中的用户？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/member/resume',
					data : {
						memberIds : id
					},
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$('#admin_yhgl_datagrid').datagrid('load');
							$('#admin_yhgl_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
	function admin_yhgl_modifyPwdFun(id) {
		$("#yhgl_dialog").editDialog({
			id :id,
			width : 620,
			height : 300,
            url : '${pageContext.request.contextPath}/admin/yhglEditPwd.jsp',
            title :"编辑用户密码",
            originContain : '#admin_yhgl_datagrid',
            onLoad : function() {
            	var index = $('#admin_yhgl_datagrid').datagrid('getRowIndex', id);
				var rows = $('#admin_yhgl_datagrid').datagrid('getRows');
				var o = rows[index];
				o.savedPassword='';
            	$('#admin_yhglEditPwd_editForm').form('load',o);
			}
        });
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 95px;top-padding:20px;overflow: hidden;" align="center">
		<form id="admin_yhgl_searchForm">
			<table class="tableForm">
				<tr>
					<th style="width: 170px;">检索用户名称(可模糊查询)</th>
					<td><input name="q" style="width: 200px;" /></td>
				</tr>
			</table>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="admin_yhgl_searchFun();">查询</a> <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="admin_yhgl_cleanFun();">清空条件</a>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="admin_yhgl_datagrid"></table>
	</div>
</div>
<div id="yhgl_dialog"></div>