<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#admin_yhglEdit_combogrid').combogrid({
			multiple : true,
			nowrap : false,
			url : '${pageContext.request.contextPath}/role/list',
			panelWidth : 450,
			panelHeight : 200,
			idField : 'roleId',
			textField : 'roleName',
			pagination : true,
			fitColumns : true,
			editable : true,
			rownumbers : true,
			mode : 'remote',
			delay : 500,
			pageSize : 20,
			pageList : [ 20, 50 ],
			columns : [ [ {
				field : 'roleId',
				title : '编号',
				width : 150,
				hidden : true
			}, {
				field : 'roleName',
				title : '角色名称',
				width : 150
			} ] ]
		});
	});
</script>
<div align="center">
	<form id="admin_yhglEdit_editForm" action="${pageContext.request.contextPath}/member/modify" method="post">
		<table class="tableForm">
			<input type="hidden" name="savedPassword" value="*******"/>
			<tr>
				<th style="width: 100px;">编号</th>
				<td><input name="memberId" readonly="readonly" /></td>
				<th style="width: 80px;">用户名</th>
				<td><input name="loginName" class="easyui-validatebox"
					data-options="required:true,validType:'length[2,20]'" /></td>
			</tr>
			<tr>
				<th>真实姓名</th>
				<td><input name="realName" class="easyui-validatebox"
					data-options="required:true,validType:'length[2,20]'" /></td>
				<th>Email</th>
				<td><input name="mailAddress" class="easyui-validatebox"
					data-options="validType:'email'" /></td>
			</tr>
			<tr>
				<th>拥有角色</th>
				<td><input id="admin_yhglEdit_combogrid" name="roleIds"
					data-options="required:true" /></td>
				<th>所属部门</th>
				<td><input id="departmentId" name="departmentId"
					class="easyui-combotree"
					data-options="url:'${pageContext.request.contextPath}/dictionary/organizationList',parentField : 'pid',required:'true'"
					lines="true" cascadeCheck="false" style="width:140px;" /></td>
			</tr>
		</table>
	</form>
</div>