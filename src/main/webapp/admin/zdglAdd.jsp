<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
	});
</script>
<div align="center">
	<form id="admin_zdglAdd_addForm" method="post">
		<table class="tableForm">
			<tr>
				<th>编号</th>
				<td><input name="id" readonly="readonly" />
				</td>
				<th>字典描述（项目名称）</th>
				<td><input name="text" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>类别编号</th>
				<td><input name="categoryNO" />
				</td>
				<th>业务编号</th>
				<td><input name="codeNO" class="easyui-numberspinner" data-options="min:0,max:999,editable:false,required:true,missingMessage:'请选择业务编号'" value="10" style="width:155px;" />
				</td>
			</tr>
			<tr>
				<th>上级目录</th>
				<td><input id="admin_zdglAdd_pid" name="pid" class="easyui-combotree" data-options="url:'${pageContext.request.contextPath}/dictionary/allTreeNode',parentField : 'pid',lines : true" style="width:140px;" /><img src="${pageContext.request.contextPath}/style/images/extjs_icons/cut_red.png" onclick="$('#admin_zdglAdd_pid').combotree('reload');" />
				</td>
				<th>字典排序</th>
				<td><input name="seq" class="easyui-numberspinner" data-options="min:0,max:999,editable:false,required:true,missingMessage:'请选择菜单排序'" value="10" style="width: 155px;" />
				</td>
			</tr>
			<tr>
				<th>意义</th>
				<td><input name="value" />
				</td>
				<th>所属部门</th>
				<td>
					<input id="departmentId" name="departmentId"
					class="easyui-combotree"
					data-options="url:'${pageContext.request.contextPath}/dictionary/organizationList',parentField : 'pid'"
					lines="true" cascadeCheck="false"/>	
				</td>
			</tr>
			<tr>
				<th>扩展字段（电路名称）</th>
				<td><input name="expvalue" />
				</td>
				<th></th>
				<td>
				</td>
			</tr>
		</table>
	</form>
</div>