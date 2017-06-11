<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/role/create')}">
	<script type="text/javascript">
		$.canAdd = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/role/modify')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<script type="text/javascript">
	$(function() {
		$('#admin_jsgl_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/role/list',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'roleId',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'roleName',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true,
			nowrap : false,
			frozenColumns : [ [ 
			   	{field : 'roleId',title : '编号',width : 150,sortable : true,hidden : true},
			   	{field : 'roleName',title : '角色名称',width : 150,sortable : true}
			   	] ],
			columns : [ [ 
			    {field : 'functionIds',title : '功能ID',width : 300,hidden : true}, 
			    {field : 'functionNames',title : '所拥有功能',width : 300},
			    {field : 'action',title : '操作',width : 100,formatter : formatJsglOperation} 
			    ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					if ($.canAdd) {
						admin_jsgl_appendFun();
					}else{
						$.messager.alert('提示', "您没有添加角色的权限！");
					}
				}
			}],
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
	});
	function formatJsglOperation(value, row, index){
		var str = '';
		if ($.canEdit) {
			str += formatString('<img onclick="admin_jsgl_editFun(\'{0}\');" src="{1}" title="编辑"/>', row.roleId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
		}
		return str;
	}
	function admin_jsgl_editFun(roleId) {
		$('#admin_jsgl_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/admin/jsglEdit.jsp',
			width : dialog_width,
			height : dialog_height,
			modal : true,
			title : '编辑角色',
			buttons : [ {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_jsglEdit_editForm').form('submit', {
						url : '${pageContext.request.contextPath}/role/modify',
						onSubmit : function() {
							parent.$.messager.progress({
								title : '提示',
								text : '数据处理中，请稍后....'
							});
							var isValid = $(this).form('validate');
							if (!isValid) {
								parent.$.messager.progress('close');
							}
							var checknodes = resourceEditTree.tree('getChecked');
							var ids = [];
							if (checknodes && checknodes.length > 0) {
								for ( var i = 0; i < checknodes.length; i++) {
									ids.push(checknodes[i].id);
								}
							}
							$('#functionIds').val(ids);
							return isValid;
						},
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_jsgl_datagrid').datagrid('updateRow', {
										index : $('#admin_jsgl_datagrid').datagrid('getRowIndex', roleId),
										row : r.obj
									});
									d.dialog('destroy');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							} catch (e) {
								$.messager.alert('提示', result);
							}
							parent.$.messager.progress('close');
						}
					});
				}
			} ,{
                text:'关闭',
                iconCls:'icon-cancel',
		        handler:function(){
		        	var d = $(this).closest('.window-body');
					d.dialog('destroy');
                }
            }],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
				var index = $('#admin_jsgl_datagrid').datagrid('getRowIndex', roleId);
				var rows = $('#admin_jsgl_datagrid').datagrid('getRows');
				var o = rows[index];
				o.functionIds = stringToList(rows[index].functionIds);
				$('#admin_jsglEdit_editForm').form('load', o);
			}
		});
	}
	function admin_jsgl_appendFun() {
		$('#admin_jsgl_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/admin/jsglAdd.jsp',
			width : dialog_width,
			height : dialog_height,
			modal : true,
			title : '添加角色',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_jsglAdd_addForm').form('submit', {
						url : '${pageContext.request.contextPath}/role/create',
						onSubmit : function() {
							parent.$.messager.progress({
								title : '提示',
								text : '数据处理中，请稍后....'
							});
							var isValid = $(this).form('validate');
							if (!isValid) {
								parent.$.messager.progress('close');
							}
							var checknodes = resourceTree.tree('getChecked');
							var ids = [];
							if (checknodes && checknodes.length > 0) {
								for ( var i = 0; i < checknodes.length; i++) {
									ids.push(checknodes[i].id);
								}
							}
							$('#functionIds').val(ids);
							return isValid;
						},
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_jsgl_datagrid').datagrid('insertRow', {
										index : 0,
										row : r.obj
									});
									d.dialog('destroy');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							} catch (e) {
								$.messager.alert('提示', result);
							}
							parent.$.messager.progress('close');
						}
					});
				}
			} ,{
                text:'关闭',
                iconCls:'icon-cancel',
		        handler:function(){
		        	var d = $(this).closest('.window-body');
					d.dialog('destroy');
                }
            }],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	function admin_jsgl_deleteFun(roleId) {
		$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/role/remove',
					data : {
						ids : roleId
					},
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$('#admin_jsgl_datagrid').datagrid('load');
							$('#admin_jsgl_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
</script>
<table id="admin_jsgl_datagrid"></table>
<div id="jsgl_toolbar" style="display: none;">
	<c:if test="${fn:contains(sessionInfo.functionUrls, '/role/create')}">
		<a onclick="admin_jsgl_appendFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
	</c:if>
</div>
<div id="jsgl_dialog"></div>