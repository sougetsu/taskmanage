<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/dictionary/create')}">
	<script type="text/javascript">
		$.canAdd = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/dictionary/remove')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.functionUrls, '/dictionary/modify')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<script type="text/javascript">
	$(function() {
		$('#admin_zdgl_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/dictionary/datalist',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'CATEGORYNO',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true,
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
			},
			    {field : 'action',title : '动作',width : 100,formatter : formatZdglOperation}
			    ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					if ($.canAdd) {
						admin_zdgl_appendFun();
					}else{
						$.messager.alert('提示', "您没有添加用户权限！");
					}
				}
			}],
			onLoadSuccess : function() {
				$('#admin_zdgl_searchForm table').show();
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
	function formatZdglOperation(value, row, index){
		var str = '';
			str += $.formatString('<img onclick="admin_zdgl_editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
		str += '&nbsp;';
			str += $.formatString('<img onclick="admin_zdgl_removeFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
		return str;
	}
	function admin_zdgl_searchFun() {
		$('#admin_zdgl_datagrid').datagrid('load', serializeObject($('#admin_zdgl_searchForm')));
	}
	function admin_zdgl_cleanFun() {
		$('#admin_zdgl_searchForm input').val('');
		$('#admin_zdgl_datagrid').datagrid('load', {});
	}
	function admin_zdgl_editFun(id) {
		$('#admin_zdgl_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$("#zdgl_dialog").editDialog({
			id :id,
			width : 620,
			height : 300,
            url : '${pageContext.request.contextPath}/admin/zdglEdit.jsp',
            title :"编辑字典",
            originContain : '#admin_zdgl_datagrid',
            onLoad : function() {
				var index = $('#admin_zdgl_datagrid').datagrid('getRowIndex', id);
				var rows = $('#admin_zdgl_datagrid').datagrid('getRows');
				var o = rows[index];
				o.roleIds = stringToList(rows[index].roleIds);
				$('#admin_zdglEdit_editForm').form('load', o);
			}
        });
	}
	function admin_zdgl_appendFun() {
		$('#admin_zdgl_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$("#zdgl_dialog").editDialog({
			width : 620,
			height : 300,
            url : '${pageContext.request.contextPath}/admin/zdglAdd.jsp',
            title :"增加字典",
            originContain : '#admin_zdgl_datagrid'
        });
	}
	function admin_zdgl_removeFun(id) {
		$.messager.confirm('确认', '您是否要删除该字典？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/dictionary/remove?dicId='+id,
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$('#admin_zdgl_datagrid').datagrid('load');
							$('#admin_zdgl_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 95px;top-padding:20px;overflow: hidden;" align="center">
		<form id="admin_zdgl_searchForm">
			<table class="tableForm">
				<tr>
					<th style="width: 50px;">字典类型</th>
					<td>
						<select name=categoryNO style="width: 150px;height:26px">
							<option value="">请选择</option>
							<option value="0001">业务申请内容</option>
							<option value="0002">鉴定方式</option>
  							<option value="0003">封装状态</option>	
  							<option value="0004">电路名称</option>	
  							<option value="0005">部门名称</option>	
  							<option value="0006">成本归集课题号</option>	
  							<option value="0009">任务单分类</option>	
  							<option value="0012">库存单位</option>	
  							<option value="0016">二筛课题号</option>
  							<option value="0026">验收课题号</option>
						</select>
					</td>
					<th style="width: 50px;">字典名称</th>
					<td><input name="text" style="width: 150px;" /></td>
					<th style="width: 50px;">课题号</th>
					<td><input name="value" style="width: 150px;" /></td>
					<th style="width: 50px;">电路名称</th>
					<td><input name="expvalue" style="width: 150px;" /></td>
					
				</tr>
			</table>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="admin_zdgl_searchFun();">查询</a> <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="admin_zdgl_cleanFun();">清空条件</a>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="admin_zdgl_datagrid"></table>
	</div>
</div>
<div id="zdgl_dialog"></div>