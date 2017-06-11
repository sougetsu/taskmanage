<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
	<form id="admin_currentUser_Form" action="${pageContext.request.contextPath}/member/modifyCurrentPwd" method="post">
		<table class="tableForm">
			<tr>
				<th>用户名</th>
				<td><input name="loginName" readonly="readonly" value="${sessionInfo.loginName}"/></td>
			</tr>
			<tr>
				<th>原密码</th>
				<td><input type="password" name="oldPassword" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入原密码',validType:'length[6,16]'" style="width: 150px;" />
				</td>
			</tr>
			<tr>
				<th>新密码</th>
				<td><input type="password" name="savedPassword" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入新密码',validType:'length[6,16]'" style="width: 150px;" />
				</td>
			</tr>
		</table>
	</form>
</div>