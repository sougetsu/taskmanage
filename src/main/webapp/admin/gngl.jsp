<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/function/create')}">
	<script type="text/javascript">
		$.canAdd = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/function/remove')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/function/modify')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<script type="text/javascript">
    var funcTreeGrid;
	$(function() {
		funcTreeGrid = $('#admin_gngl_treegrid').treegrid({
			url : '${pageContext.request.contextPath}/function/list',
			idField : 'id',
			treeField : 'text',
			parentField : 'pid',
			fit : true,
			fitColumns : true,
			border : false,
			frozenColumns : [ [ 
			   {field : 'id',title : '编号',width : 150,hidden : true}, 
			   {field : 'text',title : '资源名称',width : 250} 
			   ] ],
			columns : [ [ 
			   {field : 'url',title : '资源路径',width : 200},
			   {field : 'menuIds',title : '所属菜单ID',width : 200,hidden : true},
			   {field : 'status',title : '状态',width : 200,hidden : true},
			   {field : 'itemStatus',title : '项目类型',width : 200,hidden : true},
			   {field : 'description',title : '描述',width : 200,hidden : true},
			   {field : 'action',title : '动作',width : 50,formatter : formatGnglOperation }
			   ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					if ($.canAdd) {
						admin_gngl_appendFun();
					}else{
						$.messager.alert('提示', "您没有添加功能权限！");
					}
				}
			}, '-', {
				text : '展开',
				iconCls : 'icon-redo',
				handler : function() {
					var node = $('#admin_gngl_treegrid').treegrid('getSelected');
					if (node) {
						$('#admin_gngl_treegrid').treegrid('expandAll', node.cid);
					} else {
						$('#admin_gngl_treegrid').treegrid('expandAll');
					}
				}
			}, '-', {
				text : '折叠',
				iconCls : 'icon-undo',
				handler : function() {
					var node = $('#admin_gngl_treegrid').treegrid('getSelected');
					if (node) {
						$('#admin_gngl_treegrid').treegrid('collapseAll', node.cid);
					} else {
						$('#admin_gngl_treegrid').treegrid('collapseAll');
					}
				}
			}, '-', {
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {
					$('#admin_gngl_treegrid').treegrid('reload');
				}
			} ],
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
	});
	function formatGnglOperation(value, row, index){
		if(row.itemStatus==0){
			return '';
		}
		var str = '';
		if ($.canEdit) {
			str += formatString('<img onclick="admin_gngl_editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
		}
		str += '&nbsp;';
		if ($.canDelete) {
			str += formatString('<img onclick="admin_gngl_removeFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
		}
		return str;
	}
	function admin_gngl_editFun(id) {
		if (id != undefined) {
			$('#admin_gngl_treegrid').treegrid('select', id);
		}
		var node = $('#admin_gngl_treegrid').treegrid('getSelected');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/admin/gnglEdit.jsp',
			width : 600,
			height : 300,
			modal : true,
			title : '编辑功能',
			buttons : [ {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_gnglEdit_editForm').form('submit', {
						onSubmit : function() {
							parent.$.messager.progress({
								title : '提示',
								text : '数据处理中，请稍后....'
							});
							var isValid = $(this).form('validate');
							if (!isValid) {
								parent.$.messager.progress('close');
							}
							return isValid;
						},
						url : '${pageContext.request.contextPath}/function/modify',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_gngl_treegrid').treegrid('reload');
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
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
				var o = node;
				o.functionId=node.id;
				o.functionName=node.text;
				$('#admin_gnglEdit_editForm').form('load', o);
			}
		});
	}
	function admin_gngl_appendFun() {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/admin/gnglAdd.jsp',
			width : 600,
			height : 300,
			modal : true,
			title : '添加功能',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_gnglAdd_addForm').form('submit', {
						url : '${pageContext.request.contextPath}/function/create',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_gngl_treegrid').treegrid('reload');
									d.dialog('destroy');
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
	function admin_gngl_removeFun(id) {
		if (id != undefined) {
			$('#admin_gngl_treegrid').treegrid('select', id);
		}
		var node = $('#admin_gngl_treegrid').treegrid('getSelected');
		if (node) {
			$.messager.confirm('询问', '您确定要删除【' + node.text + '】？', function(b) {
				if (b) {
					$.ajax({
						url : '${pageContext.request.contextPath}/function/remove',
						data : {
							id : node.id
						},
						cache : false,
						dataType : 'JSON',
						success : function(r) {
							if (r.success) {
								$('#admin_gngl_treegrid').treegrid('reload');
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
<table id="admin_gngl_treegrid"></table>