<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#admin_yhglAdd_combogrid').combogrid({
			multiple : true,
			nowrap : false,
			url : '${pageContext.request.contextPath}/role/list',
			panelWidth : 450,
			panelHeight : 200,
			idField : 'roleId',
			textField : 'roleName',
			pagination : true,
			fitColumns : true,
			rownumbers : true,
			editable : true,
			mode : 'remote',
			delay : 500,
			pageSize : 5,
			pageList : [ 5, 10 ],
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
	<form id="admin_yhglAdd_createForm" action="${pageContext.request.contextPath}/member/create" method="post">
		<table class="tableForm">
			<tr>
				<th style="width: 80px;">用户名</th>
				<td><input name="loginName" class="easyui-validatebox"
					data-options="required:true,validType:'length[3,20]'" />
				</td>
				<th>密码</th>
				<td><input name="savedPassword" type="password"
					class="easyui-validatebox"
					data-options="required:true,validType:'length[6,16]'" />
				</td>
			</tr>
			<tr>
				<th>真实姓名</th>
				<td><input name="realName" class="easyui-validatebox"
					data-options="required:true,validType:'length[2,20]'" />
				</td>
				<th>Email</th>
				<td><input name="mailAddress" class="easyui-validatebox"
					data-options="validType:'email'" />
				</td>
			</tr>
			<tr>
				<th>拥有角色</th>
				<td><input id="admin_yhglAdd_combogrid" name="roleIds"
					data-options="required:true" /></td>
				<th>所属部门</th>
				<td><input id="departmentId" name="departmentId"
					class="easyui-combotree"
					data-options="url:'${pageContext.request.contextPath}/dictionary/organizationList',parentField : 'pid',required:'true'"
					lines="true" cascadeCheck="false"/></td>
			</tr>
		</table>
	</form>
</div>