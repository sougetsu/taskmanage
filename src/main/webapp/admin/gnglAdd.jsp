<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
	<form id="admin_gnglAdd_addForm" method="post">
		<table class="tableForm">
			<tr>
				<th>功能名称</th>
				<td><input name="functionName" class="easyui-validatebox"
					data-options="required:true,validType:'length[1,20]'" />
				</td>
				<th>URL</th>
				<td><input name="url" class="easyui-validatebox"
				    data-options="required:true,validType:'length[3,64]'" />
				</td>
			</tr>
			<tr>
				<th>描述</th>
				<td><input name="description" />
				</td>
				<th>状态</th>
				<td><select name="status" >
						<option value="1">启用</option>
						<option value="0">锁定</option>
				</select></td>
			</tr>
			<tr>
				<th>所属菜单</th>
				<td>
					<input id="admin_gnglAdd_menuIds" name="menuIds" class="easyui-combotree" data-options="url:'${pageContext.request.contextPath}/menu/allTreeNode',parentField : 'pid',lines : true" style="width:140px;" /><img src="${pageContext.request.contextPath}/style/images/extjs_icons/cut_red.png" onclick="$('#admin_gnglAdd_menuIds').combotree('reload');" />
				</td>
			</tr>
		</table>
	</form>
</div>