<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/menu/create')}">
	<script type="text/javascript">
		$.canAdd = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/menu/remove')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/menu/modify')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<script type="text/javascript">
	$(function() {
		$('#admin_cdgl_treegrid').treegrid({
			url : '${pageContext.request.contextPath}/menu/list',
			idField : 'id',
			treeField : 'text',
			parentField : 'pid',
			fit : true,
			fitColumns : true,
			border : false,
			frozenColumns : [ [ 
			   {title : '编号',field : 'id',width : 150,hidden : true}, 
			   {field : 'text',title : '菜单名称',width : 200} 
			   ] ],
			columns : [ [ 
			   {field : 'url',title : '菜单路径',width : 200}, 
			   {field : 'seq',title : '排序',width : 50,hidden : true},
			   {field : 'pid',title : '上级菜单ID',width : 150,hidden : true},
			   {field : 'pname',title : '上级菜单名称',width : 80,hidden : true},
			   {field : 'action',title : '动作',width : 50,formatter : formatCdglOperation}
				] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					if ($.canAdd) {
						admin_cdgl_appendFun();
					}else{
						$.messager.alert('提示', "您没有添加菜单权限！");
					}
				}
			}, '-', {
				text : '展开',
				iconCls : 'icon-redo',
				handler : function() {
					var node = $('#admin_cdgl_treegrid').treegrid('getSelected');
					if (node) {
						$('#admin_cdgl_treegrid').treegrid('expandAll', node.cid);
					} else {
						$('#admin_cdgl_treegrid').treegrid('expandAll');
					}
				}
			}, '-', {
				text : '折叠',
				iconCls : 'icon-undo',
				handler : function() {
					var node = $('#admin_cdgl_treegrid').treegrid('getSelected');
					if (node) {
						$('#admin_cdgl_treegrid').treegrid('collapseAll', node.cid);
					} else {
						$('#admin_cdgl_treegrid').treegrid('collapseAll');
					}
				}
			}, '-', {
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {
					$('#admin_cdgl_treegrid').treegrid('reload');
				}
			} ],
			onContextMenu : function(e, row) {
				e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.id);
				$('#admin_cdgl_menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
	});
	function formatCdglOperation(value, row, index){
		var str = '';
		if ($.canEdit) {
			str += formatString('<img onclick="admin_cdgl_editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
		}
		str += '&nbsp;';
		if ($.canDelete) {
			str += formatString('<img onclick="admin_cdgl_deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
		}
		return str;
	}
	function admin_cdgl_appendFun() {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/admin/cdglAdd.jsp',
			width : 600,
			height : 300,
			modal : true,
			title : '菜单添加',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_cdglAdd_addForm').form('submit', {
						url : '${pageContext.request.contextPath}/menu/create',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_cdgl_treegrid').treegrid('reload');
									d.dialog('destroy');

									$('#layout_west_tree').tree('reload');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							} catch (e) {
								$.messager.alert('提示', result);
							}
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	function admin_cdgl_editFun(id) {
		if (id != undefined) {
			$('#admin_cdgl_treegrid').treegrid('select', id);
		}
		var node = $('#admin_cdgl_treegrid').treegrid('getSelected');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/admin/cdglEdit.jsp',
			width : 600,
			height : 300,
			modal : true,
			title : '菜单编辑',
			buttons : [ {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_cdglEdit_editForm').form('submit', {
						url : '${pageContext.request.contextPath}/menu/modify',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_cdgl_treegrid').treegrid('reload');
									d.dialog('destroy');

									$('#layout_west_tree').tree('reload');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							} catch (e) {
								$.messager.alert('提示', result);
							}
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
				$('#admin_cdglEdit_editForm').form('load', node);
			}
		});
	}
	function admin_cdgl_deleteFun(id) {
		if (id != undefined) {
			$('#admin_cdgl_treegrid').treegrid('select', id);
		}
		var node = $('#admin_cdgl_treegrid').treegrid('getSelected');
		if (node) {
			$.messager.confirm('询问', '您确定要删除【' + node.text + '】？', function(b) {
				if (b) {
					$.ajax({
						url : '${pageContext.request.contextPath}/menu/remove',
						data : {
							id : node.id
						},
						cache : false,
						dataType : 'JSON',
						success : function(r) {
							if (r.success) {
								$('#admin_cdgl_treegrid').treegrid('reload');
								$('#layout_west_tree').tree('reload');
							}
							$.messager.show({
								msg : r.msg,
								title : '提示'
							});
						}
					});
				}
			});
		}
	}
</script>
<table id="admin_cdgl_treegrid"></table>
<div id="admin_cdgl_menu" class="easyui-menu" style="width:120px;display: none;">
	<div onclick="admin_cdgl_appendFun();" data-options="iconCls:'icon-add'">增加</div>
	<div onclick="admin_cdgl_deleteFun();" data-options="iconCls:'icon-remove'">删除</div>
	<div onclick="admin_cdgl_editFun();" data-options="iconCls:'icon-edit'">编辑</div>
</div>