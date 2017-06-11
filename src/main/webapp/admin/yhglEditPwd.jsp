<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
	<form id="admin_yhglEditPwd_editForm" action="${pageContext.request.contextPath}/member/modifyPwd" method="post">
		<input type="hidden" name="memberId">
		<table class="tableForm">
			<tr>
				<th>用户名</th>
				<td><input name="loginName" readonly="readonly" /></td>
			</tr>
			<tr>
				<th>新密码</th>
				<td><input type="password" name="savedPassword" class="easyui-validatebox" data-options="required:true" style="width: 150px;" />
				</td>
			</tr>
		</table>
	</form>
</div>