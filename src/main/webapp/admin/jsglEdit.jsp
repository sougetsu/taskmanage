<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
var resourceEditTree;
	$(function() {
		resourceEditTree = $('#functionEditTree').tree({
			url : '${pageContext.request.contextPath}/function/list',
			checkbox : true,
			parentField : 'pid',
			multiple : 'true',
			lines : true,
			cascadeCheck : true,
			onLoadSuccess : function(node, data) {
				var obj =  $("#functionIds").val();
				var ids = stringToList(obj);
				if (ids.length > 0) {
					for ( var i = 0; i < ids.length; i++) {
						var id = 'f_'+ids[i];
						if (resourceEditTree.tree('find', id)) {
							resourceEditTree.tree('check', resourceEditTree.tree('find', id).target);
						}
					}
				}
				parent.$.messager.progress('close');
			}
		});
	});
	function checkAll() {
		var nodes = resourceEditTree.tree('getChecked', 'unchecked');
		if (nodes && nodes.length > 0) {
			for ( var i = 0; i < nodes.length; i++) {
				resourceEditTree.tree('check', nodes[i].target);
			}
		}
	}
	function uncheckAll() {
		var nodes = resourceEditTree.tree('getChecked');
		if (nodes && nodes.length > 0) {
			for ( var i = 0; i < nodes.length; i++) {
				resourceEditTree.tree('uncheck', nodes[i].target);
			}
		}
	}
	function checkInverse() {
		var unchecknodes = resourceEditTree.tree('getChecked', 'unchecked');
		var checknodes = resourceEditTree.tree('getChecked');
		if (unchecknodes && unchecknodes.length > 0) {
			for ( var i = 0; i < unchecknodes.length; i++) {
				resourceEditTree.tree('check', unchecknodes[i].target);
			}
		}
		if (checknodes && checknodes.length > 0) {
			for ( var i = 0; i < checknodes.length; i++) {
				resourceEditTree.tree('uncheck', checknodes[i].target);
			}
		}
	}
</script>
<div id="roleGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'west'" title="系统资源" style="width: 300px; padding: 1px;">
		<div class="well well-small">
			<form id="admin_jsglEdit_editForm" method="post">
				<table class="tableForm">
					<tr>
						<th>编号</th>
						<td><input name="roleId" readonly="readonly" /></td>
					</tr>
					<tr>
						<th>角色名称</th>
						<td><input name="roleName" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]'" /></td>
					</tr>
					<tr>
						<th>角色授权</th>
						<td colspan="3">
							<input id="functionIds" name="functionIds" type="hidden" />
							<ul id="functionEditTree"></ul>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div data-options="region:'center'" title="" style="overflow: hidden; padding: 10px;">
		<div class="well well-large">
			<button class="btn btn-success" onclick="checkAll();">全选</button>
			<br /> <br />
			<button class="btn btn-warning" onclick="checkInverse();">反选</button>
			<br /> <br />
			<button class="btn btn-inverse" onclick="uncheckAll();">取消</button>
		</div>
	</div>
</div>
