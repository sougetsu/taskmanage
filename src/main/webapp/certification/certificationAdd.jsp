<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
	})
	function checkAndSubmit(){
		var submitForm = $('#certification_createForm');
		if(submitForm.form('validate')){
			parent.$.messager.progress({
				title : '提示',
				text : '数据提交中，请稍后....'
			});
    		$.ajax({
                type: "POST",
                url:'${pageContext.request.contextPath}/certification/create',
                data:submitForm.serialize(),
                dataType : 'json',
				success : function(result) {
					parent.$.messager.progress('close');
					if (result.success) {
						$.messager.confirm('确认', '添加成功，是否继续添加合格证？', function(r) {
							if (r) {
								var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
								var tab = $('#layout_center_tabs').tabs('getTab', index);
								tab.panel('refresh');
							}else{
								var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
								$('#layout_center_tabs').tabs('close', index);
							}
						});
					}
				}
            });
    	}
	}
</script>
<style type="text/css">
.certification td{ height:30px;}
</style>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false">
		<div align="center" style="margin-top:50px">
			<form id="certification_createForm" method="post"
				enctype="multipart/form-data">
				<table width="60%" border="1" bordercolor="#B5C0C4" rules="none"
					style="text-align:center;border-collapse:collapse;" class="certification">
					<tr>
						<td rowspan="2" style="text-align:center;"><img src="../style/images/cslog.png"/></td>
						<td colspan="3" style="text-align:center;font-size:16px;font-weight:bold;padding-right:20%;">产品合格证</td>
					</tr>
					<tr>
						<td></td>
						<td>合格证编号：</td>
						<td></td>
					</tr>
					<tr>
						<td>产 品 名 称：</td>
						<td><input name="productName" class="easyui-validatebox"
							data-options="required:true,validType:'length[1,100]'" /></td>
						<td>产 品 型 号：</td>
						<td><input name="productType" class="easyui-validatebox"
							data-options="required:true,validType:'length[1,100]'" /></td>
					</tr>
					<tr>
						<td>产 品 批 次：</td>
						<td><input name="productBatch" class="easyui-validatebox"
							data-options="required:true,validType:'length[1,100]'" /></td>
						<td>产 品 数 量：</td>
						<td><input name="productNum" class="easyui-validatebox"
							data-options="required:true,validType:'length[1,100]'" /></td>
					</tr>
					<tr>
						<td>产品检测标准：</td>
						<td><input name="testStandard" class="easyui-validatebox"
							data-options="required:true,validType:'length[1,100]'" /></td>
						<td>检测报告号：</td>
						<td><input name="testReportId" class="easyui-validatebox"
							data-options="required:true,validType:'length[1,100]'" /></td>
					</tr>
					<tr>
						<td>质 量 状 态：</td>
						<td><input name="qualityStatus" class="easyui-validatebox"
							data-options="required:true,validType:'length[1,100]'" /></td>
						<td>用 户 单 位：</td>
						<td><input name="userUnits" class="easyui-validatebox"
							data-options="required:true,validType:'length[1,100]'" /></td>
					</tr>
					<tr>
						<td>检 验 员 ：</td>
						<td><input name="inspector" class="easyui-validatebox"
							data-options="required:true,validType:'length[1,100]'" /></td>
						<td>签 发 日 期：</td>
						<td><input name="certificationDate" class="easyui-validatebox" data-options="required:true"
									onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
					<tr>
						<td>备 注：</td>
						<td colspan="3"><input name="remark" class="easyui-validatebox"
							data-options="required:true,validType:'length[1,100]'" style="width:85%"/></td>
					</tr>
					<tr>
						<td colspan="4"
							style="text-align:center;font-size:12px;font-weight:bold">中国航天科技集团公司第九研究院第七七二研究所</td>
					</tr>
				</table>
				<table style="margin-top:10px; ">
					<tr>
						<td colspan=4 style="text-align:center"><a
							id="certification_add_submit" class="l-btn"
							href="javascript:void(0);" onclick="javascript:checkAndSubmit()">
								<span class="l-btn-left"> <span
									class="l-btn-text icon-save l-btn-icon-left"
									style="padding-left: 20px; ">保存</span>
							</span>
						</a> <a id="certification_add_close" class="l-btn"
							href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
								<span class="l-btn-left"> <span
									class="l-btn-text icon-cancel l-btn-icon-left"
									style="padding-left: 20px; ">关闭</span>
							</span>
						</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
