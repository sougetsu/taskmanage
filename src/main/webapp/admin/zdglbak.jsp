<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/dictionary/create')}">
	<script type="text/javascript">
		$.canAdd = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/dictionary/modify')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<script type="text/javascript">
var allData = new Array();
var bFound = true; 
	$(function() {
		$('#admin_zdgl_treegrid').treegrid({
			url : '${pageContext.request.contextPath}/dictionary/list',
			idField : 'id',
			treeField : 'text',
			parentField : 'pid',
			fit : true,
			border : false,
			loadFilter:function(data){ 
				if (bFound) {
					allData = data;
					bFound = false;
				}
				return data;
			},
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : 150,
				hidden : true
			}, {
				field : 'text',
				title : '字典名称',
				width : 250
			} ] ],
			columns : [ [ {
				field : 'categoryNO',
				title : '类别编号',
				width : 50
			},  {
				field : 'codeNO',
				title : '业务编号',
				width : 50
			},{
				field : 'value',
				title : '意义',
				width : 150
			},{
				field : 'expvalue',
				title : '电路名称',
				width : 150
			}, {
				field : 'seq',
				title : '排序',
				width : 50
			},{
				field : 'pid',
				title : '上级编号',
				width : 150,
				hidden : true
			}, {
				field : 'action',
				title : '动作',
				width : 50,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
						str += formatString('<img onclick="admin_zdgl_editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
						str += '&nbsp;';
						str += formatString('<img onclick="admin_zdgl_removeFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
					return str;
				}
			} ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					if ($.canAdd) {
						admin_zdgl_appendFun();
					}else{
						$.messager.alert('提示', "您没有添加字典的权限！");
					}
				}
			}, '-', {
				text : '展开',
				iconCls : 'icon-redo',
				handler : function() {
					var node = $('#admin_zdgl_treegrid').treegrid('getSelected');
					if (node) {
						$('#admin_zdgl_treegrid').treegrid('expandAll', node.cid);
					} else {
						$('#admin_zdgl_treegrid').treegrid('expandAll');
					}
				}
			}, '-', {
				text : '折叠',
				iconCls : 'icon-undo',
				handler : function() {
					var node = $('#admin_zdgl_treegrid').treegrid('getSelected');
					if (node) {
						$('#admin_zdgl_treegrid').treegrid('collapseAll', node.cid);
					} else {
						$('#admin_zdgl_treegrid').treegrid('collapseAll');
					}
				}
			}, '-', {
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {
					$('#admin_zdgl_treegrid').treegrid('reload');
				}
			},'-',{
				text: '<input id="filter" type="text"  style="height:10px;"/>',
			},{
				iconCls:'icon-search',
				handler:function () {
				   doFilter();
				}
			} ],
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			},
			onContextMenu : function(e, row) {
				e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.id);
				$('#admin_zdgl_menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
	});
	function admin_zdgl_removeFun(id) {
		$.messager.confirm('确认', '您是否要删除该字典？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/dictionary/remove?dicId='+id,
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$('#admin_zdgl_treegrid').treegrid('reload');
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
	function admin_zdgl_appendFun() {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/admin/zdglAdd.jsp',
			width : 600,
			height : 300,
			modal : true,
			title : '字典项添加',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_zdglAdd_addForm').form('submit', {
						url : '${pageContext.request.contextPath}/dictionary/create',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_zdgl_treegrid').treegrid('append', {
										parent : r.obj.pid,
										data : [ r.obj ]
									});

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
	function admin_zdgl_editFun(id) {
		if (id != undefined) {
			$('#admin_zdgl_treegrid').treegrid('select', id);
		}
		var node = $('#admin_zdgl_treegrid').treegrid('getSelected');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/admin/zdglEdit.jsp',
			width : 600,
			height : 300,
			modal : true,
			title : '字典项编辑',
			buttons : [ {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_zdglEdit_editForm').form('submit', {
						url : '${pageContext.request.contextPath}/dictionary/modify',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_zdgl_treegrid').treegrid('reload');
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
				$('#admin_zdglEdit_editForm').form('load', node);
			}
		});
	}
	function doFilter() {
        var filter = $("#filter").val();
        if (filter == "") {
            $('#admin_zdgl_treegrid').treegrid('loadData', allData);
        } else {
            var newData = new Array();
            for (var i = 0; i < allData.length; i++) {
                var item = allData[i];
                if (item.text.indexOf(filter) != -1 || item.value.indexOf(filter) != -1  ) {
                    // 定义一个数组  
                    newData.push(item);
                } else if (item.children != null && item.children.length > 0) {
                    doChildFilter(item, newData, filter);
                }
            }
            $('#admin_zdgl_treegrid').treegrid('loadData', newData);
        }
    }
    function doChildFilter(parentItem, newData, filter) {
        var list = parentItem.children;
        for (var i = 0; i < list.length; i++) {
            var item = list[i];
            if (item.Name.indexOf(filter) != -1) {
                // 定义一个数组  
                newData.push(item);
                return;
            } else if (item.children != null && item.children.length > 0) {
                doChildFilter(item, newData, filter);
            }
        }
    }
</script>
<table id="admin_zdgl_treegrid"></table>
<div id="admin_zdgl_menu" class="easyui-menu" style="width:120px;display: none;">
	<div onclick="admin_zdgl_appendFun();" data-options="iconCls:'icon-add'">增加</div>
	<div onclick="admin_zdgl_deleteFun();" data-options="iconCls:'icon-remove'">删除</div>
	<div onclick="admin_zdgl_editFun();" data-options="iconCls:'icon-edit'">编辑</div>
</div>