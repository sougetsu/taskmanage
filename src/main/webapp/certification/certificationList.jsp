<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#certification_list_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/certification/list',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'createtime',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true,
			columns : [ [ 
			    {field : 'id',title : '序号',hidden:true,width : 80}, 
			    {field : 'certificationId',title : '合格证编号',width : 180}, 
			    {field : 'productName',title : '产品名称',width : 100,sortable : true},
			    {field : 'productType',title : '产品型号',width : 120},
			    {field : 'productBatch',title : '产品批次',width : 100,sortable : true}, 
			    {field : 'productNum',title : '产品数量',width : 80,sortable : true},
			    {field : 'inspector',title : '检验员',width : 100,sortable : true}, 
			    {field : 'certificationDate',title : '签发日期',width : 150,sortable : true}, 
			    {field : 'createTime',title : '录入时间',width : 120,sortable : true},
			    {field : 'action',title : '操作',width : 180,formatter : formathgzOperation} 
			    ] ],
		    onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
	});
	function formathgzOperation(value, row, index){
		var str = '';
		str += formatString('<span onclick="certification_list_detail(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>详情</span>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
		str += '&nbsp;';
		str += formatString('<span onclick="certification_list_edit(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>修改</span>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
		str += '&nbsp;';
		str += formatString('<span onclick="certification_list_delete(\'{0}\');" style="cursor:pointer " ><img src="{1}"/>删除</span>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
		return str;
	}
	function certification_list_detail(id) {
		layout_center_addTabFun({
			title : '合格证详细页',
			closable : true,
			href : '${pageContext.request.contextPath}/certification/detailPage?id='+id
		});
	}
	function certification_list_edit(id) {
		layout_center_addTabFun({
			title : '合格证修改页',
			closable : true,
			href : '${pageContext.request.contextPath}/certification/editPage?id='+id
		});
	}
	function certificationList_searchFun() {
		var startTime = $('#certificationList_registTimeStart').val();
		var endTime = $('#certificationList_registTimeEnd').val();
		if(comparetime(startTime,endTime)){
			$('#certification_list_datagrid').datagrid('load', serializeObject($('#certificationList_searchForm')));
		}else{
			$.messager.show({
				title : '提示',
				msg : "开始时间大于结束时间"
			});
		}
	}
	function certification_list_delete(id) {
		$.messager.confirm('确认', '您是否要删除该合格证？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/certification/removeCertification?id='+id,
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$('#certification_list_datagrid').datagrid('load');
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
	function certificationList_downloadFun() {
		var startTime = $('#certificationList_registTimeStart').val();
		var endTime = $('#certificationList_registTimeEnd').val();
		if(comparetime(startTime,endTime)){
			$('#certificationList_searchForm').attr('action','${pageContext.request.contextPath}/certification/downloadList');
			$('#certificationList_searchForm').submit();
		}else{
			$.messager.show({
				title : '提示',
				msg : "开始时间大于结束时间"
			});
		}
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false" style="height: 150px;overflow: hidden;" align="center">
		<form id="certificationList_searchForm" method="post" >
			<table align="center" class="tableFormcx" cellSpacing=0 cellPadding=5 style="margin-top: 10px;">
				<tr>
					<td style="width: 60px;">合格证编号</td>
					<td><input name="certificationId" /></td>
					<td style="width: 60px;">产 品 名 称</td>
					<td><input name="productName" /></td>
					<td>产 品 型 号</td>
					<td><input name="productType" /></td>
				</tr>
				<tr>
					<td style="width: 60px;">检  验  员</td>
					<td><input name="inspector" /></td>
					<td style="width: 60px; text-align: right;">签发日期起</td>
					<td><input id="certificationList_registTimeStart" name="certificationStart" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
					<td style="width: 60px; text-align: right;">签发日期止</td>
					<td><input id="certificationList_registTimeEnd" name="certificationEnd" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center;">
						<div>
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="certificationList_searchFun();return false;">查询</a>&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="certificationList_downloadFun();return false;">导出数据</a>&nbsp;
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="certification_list_datagrid"></table>
	</div>
</div>