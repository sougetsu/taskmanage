<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		var contextPath = '${pageContext.request.contextPath}';
		var sessionId = $("#sessionId").val();
	});

	function checkAndSubmit(){
		var submitForm = $('#priceDetail_createForm');
		if(submitForm.form('validate')){
			parent.$.messager.progress({
				title : '提示',
				text : '数据提交中，请稍后....'
			});
    		$.ajax({
                type: "POST",
                url:'${pageContext.request.contextPath}/priceDetail/create',
                data:submitForm.serialize(),
                dataType : 'json',
				success : function(result) {
					parent.$.messager.progress('close');
					if (result.success) {
						$.messager.confirm('确认', '添加成功，是否继续添加任务单？', function(r) {
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
	$("#Add_electricCpxh").combotree({
    	onSelect:function(node){
    		$("#Add_electricDlmc").val(node.annotation);
    	}
    });
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<div data-options="region:'center',border:false">
	<div align="center" style="margin-top:20px;">
		<form id="priceDetail_createForm"method="post" enctype="multipart/form-data">
			<table class="tasktableForm" width="95%" border="1"  bordercolor="#B5C0C4" rules="none" style="border-collapse:collapse;">
				<tr>
					<th >产品型号</th>
					<td >
						<input  id="Add_electricCpxh" name="cpxh" class="easyui-combotree" style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/electricListText',parentField : 'pid'"
						lines="true" cascadeCheck="false" />
					</td>
					<th >电路名称</th>
					<td >
						<input id="Add_electricDlmc"  name="dlmc"   style="width:330px;font-size: 12px"/>
					</td>
				</tr>
				<tr>
					<th >封装类型</th>
					<td >
						<input  name="fzlx"   style="width:330px;font-size: 12px"/>
					</td>
					<th >产品类别</th>
					<td>
						<input name="cplb"   style="width:330px;font-size: 12px"/>
					</td>
				</tr>
				<tr>
					<th >测试机台</th>
					<td >
						<input  name="csjt"   style="width:330px;font-size: 12px"/>
					</td>
					<th >封装单价(元/只)</th>
					<td >
						<input  name="fzPrice" class="easyui-numberbox" data-options="validType:'length[1,30]'"  style="width:330px;font-size: 12px"/>
					</td>
				</tr>
				<tr>
					<th >鉴定供货批测试费(元/只)</th>
					<td >
						<input  name="jdghpcsPrice" class="easyui-numberbox" data-options="validType:'length[1,30]'"  style="width:330px;font-size: 12px"/>
					</td>
					<th >筛选费用(元/只)</th>
					<td>
						<input name="sxPrice" class="easyui-numberbox" data-options="validType:'length[1,30]'"  style="width:330px;font-size: 12px"/>
					</td>
				</tr>
				<tr>
					<th >鉴定/一致性检测费(元/批)</th>
					<td >
						<input  name="jdyzxjcPrice"  class="easyui-numberbox" data-options="validType:'length[1,30]'" style="width:330px;font-size: 12px"/>
					</td>
					<th >验收/只</th>
					<td >
						<input  name="ysPrice" class="easyui-numberbox" data-options="validType:'length[1,30]'"  style="width:330px;font-size: 12px"/>
					</td>
				</tr>
				<tr>
					<th >补充筛选</th>
					<td >
						<input  name="bcsxPrice" class="easyui-numberbox" data-options="validType:'length[1,30]'"  style="width:330px;font-size: 12px"/>
					</td>
					<th >其他生产费（切筋成型植球植柱）(元/只）</th>
					<td>
						<input name="qtscfPrice"  class="easyui-numberbox" data-options="validType:'length[1,30]'" style="width:330px;font-size: 12px"/>
					</td>
				</tr>
				
				<tr>
					<th >三温合格品测试费(元/只)</th>
					<td >
						<input  name="swhgpcsPrice" class="easyui-numberbox" data-options="validType:'length[1,30]'"  style="width:330px;font-size: 12px"/>
					</td>
					<th >常温产品(元/只)</th>
					<td >
						<input  name="cwcpPrice"  class="easyui-numberbox" data-options="validType:'length[1,30]'" style="width:330px;font-size: 12px"/>
					</td>
				</tr>
				<tr>
			         <td colspan=8 style="text-align:center">
			         	<a class="l-btn" href="javascript:void(0);" onclick="javascript:checkAndSubmit()">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">保存</span>
			         		</span>
			         	</a>
			         	<a  class="l-btn" href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-cancel l-btn-icon-left" style="padding-left: 20px; ">关闭</span>
			         		</span>
			         	</a>
			         </td>
		     	</tr>
			</table>
		</form>
	</div>
</div>
</div>
