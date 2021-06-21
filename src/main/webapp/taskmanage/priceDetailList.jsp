<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#pricedetail_list_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/priceDetail/list',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			idField : 'orderId',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'priceDetailId',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true,
			columns : [ [ 
			    {field : 'priceDetailId',title : '序号',hidden:true,width : 80}, 
			    {field : 'cpxh',title : '产品型号',width : 130,sortable : true}, 
			    {field : 'dlmc',title : '电路名称',width : 100,sortable : true},
			    {field : 'fzlx',title : '封装类型',width : 120,sortable : true},
			    {field : 'cplb',title : '产品类别',width : 100,sortable : true}, 
			    {field : 'csjt',title : '测试机台',width : 80,sortable : true},
			    {field : 'fzPrice',title : '封装单价(元/只)',width : 100}, 
			    {field : 'jdghpcsPrice',title : '鉴定供货批测试费(元/只)',width : 100}, 
			    {field : 'sxPrice',title : '筛选费用(元/只)',width : 150}, 
			    {field : 'jdyzxjcPrice',title : '鉴定/一致性检测费(元/批)',width : 120},
			    {field : 'ysPrice',title : '验收/只',width : 100},
			    {field : 'bcsxPrice',title : '补充筛选',width : 100},
			    {field : 'qtscfPrice',title : '其他生产费（切筋成型植球植柱）(元/只）',width : 100},
			    {field : 'swhgpcsPrice',title : '三温合格品测试费(元/只)',width : 100},
			    {field : 'cwcpPrice',title : '常温产品(元/只)',width : 100},
			    {field : 'action',title : '操作',width : 180,formatter : formatZxcxOperation} 
			    ] ],
		    onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
	});
	function formatZxcxOperation(value, row, index){
		var str = '';
			str += formatString('<span onclick="pricedetail_list_detail(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>详情</span>', row.priceDetailId, '${pageContext.request.contextPath}/style/images/extjs_icons/information.png');
			str += '&nbsp;';
			str += formatString('<span onclick="pricedetail_list_edit(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>修改</span>', row.priceDetailId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
			str += '&nbsp;';
			str += formatString('<span onclick="pricedetail_list_delete(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>删除</span>', row.priceDetailId, '${pageContext.request.contextPath}/style/images/extjs_icons/delete.png');
		return str;
	}
	function pricedetail_list_detail(id) {
		layout_center_addTabFun({
			title : '核价项详细页',
			closable : true,
			href : '${pageContext.request.contextPath}/priceDetail/detailPage?id='+id
		});
	}
	function pricedetail_list_edit(id) {
		layout_center_addTabFun({
			title : '核价项修改页',
			closable : true,
			href : '${pageContext.request.contextPath}/priceDetail/editPage?id='+id
		});
	}
	function priceList_searchFun() {
		$('#pricedetail_list_datagrid').datagrid('load', serializeObject($('#pricedetail_searchForm')));
	}

	function pricedetail_list_delete(id) {
		$.messager.confirm('确认', '您是否要删除该任务单？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/priceDetail/removePriceDetail?id='+id,
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$('#pricedetail_list_datagrid').datagrid('load');
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
	function priceList_downloadFun() {
		$('#pricedetail_searchForm').attr('action','${pageContext.request.contextPath}/taskManage/downloadExcel');
		$('#pricedetail_searchForm').submit();
	}
	function priceList_cleanFun() {
		$('#pricedetail_searchForm input').val('');
		$('#pricedetail_list_datagrid').datagrid('load', {});
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false" style="overflow: hidden;" align="center">
		<form id="pricedetail_searchForm" method="post" >
			<table align="center" class="tableFormcx" cellSpacing=0 cellPadding=5 style="margin-top: 10px;">
				<tr>
					<td style="width: 60px;">产品型号</td>
					<td><input name="cpxh" /></td>
					<td style="width: 60px;">电路名称</td>
					<td><input name="dlmc" /></td>
					<td>封装类型</td>
					<td><input name="fzlx" /></td>
					<td>测试机台</td>
					<td><input name="csjt" /></td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center;">
						<div>
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="priceList_searchFun();return false;">查询</a>&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="priceList_downloadFun();return false;">导出数据</a>&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="priceList_cleanFun();return false;">清空条件</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="pricedetail_list_datagrid"></table>
	</div>
</div>