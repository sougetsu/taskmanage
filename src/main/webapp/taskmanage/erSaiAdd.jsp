<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		var contextPath = '${pageContext.request.contextPath}';
		var sessionId = $("#sessionId").val();
		var allowDescType = "jpg;png;gif;txt;doc;xls;pdf;rar";
		var allowType = "*.jpg;*.png;*.gif;*.txt;*.doc;*.xls;*.docx;*.xlsx;*.pdf;*.rar";
		$("#erSaiAdd_attach").uploadify({
			uploader: contextPath+"/jslib/file/uploadify.swf?var='+(new Date()).getTime()' ",//指定文件上传的进度条的位置
			script: '${pageContext.request.contextPath}/erSaiTaskManage/uploadFile',
			cancelImg: contextPath+"/jslib/file/cancel.png",//取消文件上传的图标
			fileDataName:"attach",//文件上传到服务器的名称(使用file中的name)
			method:"post",//通过post方式上传
			queueID:"erSaiAdd_attachs",//上传队列说存储的位置
			auto:false,//是否选中之后就上传,false表示选中之后不上传
			multi:true,//时候支持多文件上传
			buttonImg:contextPath+"/style/images/liulan.jpg",
			height: 32,
		    width: 55,
			fileDesc:"请选择"+allowDescType,
			fileExt:allowType,
			onComplete: fileComplete,
			onAllComplete:bindEvent
		});
		function fileComplete(event, ID, fileObj, response, data) {
			var rel = $.parseJSON(response);
			if(checkOp(rel)) {
				var node = "<div id="+rel.id+" newname="+rel.url+
				"<span>"+rel.localfile+"</span>" +
				"<a id="+rel.id+" href='javascript:void(0);' style='padding-left:10px;' class='deleteAttach'>删除</a>" +
				"<input type='hidden' name='attachids' value='"+rel.id+"'/></div>" ;
				$("#erSaiAdd_xxwhAdd_alreadyAttachs").append(node);
			} else {
				alert(rel.err);
			}
		}
		function checkOp(data) {
			if(data.result==1) return true
			else return false;
		}
		function bindEvent() {
			$(".deleteAttach").click(function(){
				var id = this.id;
				var div = $(this).parent("div");
				$.post("${pageContext.request.contextPath}/erSaiTaskManage/deleteAttach",{id:id},function(data){
					if(data.success) {
						$(div).remove();
					} else {
						alert(data.msg);
					}
				},"json");
			});
		}
	});
	function setErsaiAddAttachRow(value){
		var attachSelect = document.getElementById("erSaiAdd_attachSelect");
		var attachSubmit = document.getElementById("erSaiAdd_attachSubmit");
		attachSelect.style.display = "none";
		attachSubmit.style.display = "none";
		if(value==1){
			attachSelect.style.display = (document.all ? "block" : "table-row");
			attachSubmit.style.display = (document.all ? "block" : "table-row");
		}
	}
	function setGoldCutRow(value){
		var goldCutSelect = document.getElementById("ersai_Add_goldno");
		goldCutSelect.style.display = "none";
		if(value==1){
			goldCutSelect.style.display = "table-row";
		}
	}
	function checkAndSubmitErsai(){
		var submitForm = $('#taskOrderErsai_createForm');
		if(submitForm.form('validate')){
			parent.$.messager.progress({
				title : '提示',
				text : '数据提交中，请稍后....'
			});
			$("#status").val("31");
    		$.ajax({
                type: "POST",
                url:'${pageContext.request.contextPath}/erSaiTaskManage/create',
                data:submitForm.serialize(),
                dataType : 'json',
				success : function(result) {
					parent.$.messager.progress('close');
					if (result.success) {
						$.messager.confirm('确认', '添加成功，是否继续添加二筛任务单？', function(r) {
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
	$("#Add_projectId").combotree({
    	onSelect:function(node){
    		$("#Add_topicNoId").val(node.value);
    	}    
    });
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<div data-options="region:'center',border:false">
	<div align="center">
		<form id="taskOrderErsai_createForm"method="post" enctype="multipart/form-data">
		<input type="hidden" id="status" name="status">
			<table class="tasktableForm" width="95%" border="1"  bordercolor="#B5C0C4" rules="none" style="border-collapse:collapse;">
				<tr>
					<th style="width: 150px">任务类型</th>
					<td width=174 colspan=2>
						<input  name="taskType" value="二筛任务"
						readonly="true" style="width:330px" />
					</td>
					<th width=121 colspan=2>通知单号</th>
					<td width=264 colspan=3>
						<input name="reportNo"
						class="easyui-validatebox"
						data-options="required:true,validType:'length[1,100]'"
						style="width:330px" /></td>
					</td>
				</tr>
				<tr>
					<th width="150px">所内型号</th>
					<td  colspan=7>
						<textarea id="ersaiAdd_internalModel" name="internalModel" class="easyui-validatebox" data-options="validType:'length[1,4000]'"  style="height: 50px; width:98%" value="" />
					</td>
				</tr>
				<tr>
					<th width="150px">申请部门</th>
					<td width=174 colspan=2><input name="applyDept"
						readonly="true"
						style="width:330px;font-size: 12px;" value="${sessionInfo.orgnizationName}"/></td>
					<th width=121 colspan=2>申请人</th>
					<td width=106><input name="applyMember"
						class="easyui-validatebox"
						data-options="validType:'length[1,30]'"
						style="width:98%" /></td>
					<th width=42>电话</th>
					<td width=115><input name="applyMemberPhone"
						class="easyui-validatebox"
						data-options="validType:'length[1,30]'"
						style="width:98%" /></td>
				</tr>
				<tr>
					<th width="150px">课题号</th>
					<td width=174 colspan=2>
						<input name="topicId" class="easyui-combotree" style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/ersaitopicList',parentField : 'pid',required:'true'"
						lines="true" cascadeCheck="false"/>
					</td>
					
					<th width=121 colspan=2>项目负责人</th>
					<td width=106><input name="projectManager"
						class="easyui-validatebox"
						data-options="validType:'length[1,30]'"
						style="width:98%" /></td>
					<th width=42>电话</th>
					<td width=115><input name="projectManagerPhone"
						class="easyui-validatebox"
						data-options="validType:'length[1,30]'"
						style="width:98%" /></td>
				</tr>
				<tr>
					<th width="150px">请求协助部门</th>
					<td width=174 colspan=2><input name="helpDeptId" class="easyui-combotree" style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/organizationList',parentField : 'pid',required:'true'"
						lines="true" cascadeCheck="false"/></td>
					<th width=121 colspan=2>希望完成时间</th>
					<td width=264 colspan=3><input id="wantedEndDate" name="wantedEndDate" class="easyui-validatebox" data-options="required:true"
									onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" style="width:330px"/></td>
				</tr>
				<tr>
					<th width="150px" >是否验收：</th>
					<td width=174px style="text-align:left" colspan=2 >
						<span style="white-space:nowrap;">
							<input type="radio" style="width:30px" name="checkFlag"  value="1" />是
							<input type="radio" style="width:30px" name="checkFlag"  checked value="0" />否
						</span>
					</td>
					<th width=121 colspan=2>电路是否出库：</th>
					<td width=264 colspan=3>
						<span style="white-space:nowrap;">
							<input type="radio" style="width:30px" name="outputFlag"  value="1" />是
							<input type="radio" style="width:30px" name="outputFlag"  checked value="0" />否
						</span>
					</td>
				</tr>
				<tr>
					<th width="150px" >是否切金：</th>
					<td width=174px style="text-align:left" colspan=2 >
						<span style="white-space:nowrap;">
							<input type="radio" style="width:30px" name="goldcutFlag"  onclick="setGoldCutRow(this.value)" value="1" />是
							<input type="radio" style="width:30px" name="goldcutFlag"  onclick="setGoldCutRow(this.value)" checked value="0" />否
						</span>
					</td>
					<th width=121 colspan=2></th>
					<td width=264 colspan=3></td>
				</tr>
				<tr id="ersai_Add_goldno" style="display:none;">
					<th width="150px" >切金编号</th>
					<td width=174px style="text-align:left" colspan=2 >
						<input name="goldcutNo" style="width:330px"/>
					</td>
					<th width=121 colspan=2></th>
					<td width=264 colspan=3></td>
				</tr>
				<tr>
					<th width="150px" >是否有附件：</th>
					<td width=174px style="text-align:left" colspan=2 >
						<span style="white-space:nowrap;">
							<input type="radio" style="width:30px" name="attachmentFlag" onclick="setErsaiAddAttachRow(this.value)" value="1" />是
							<input type="radio" style="width:30px" name="attachmentFlag" onclick="setErsaiAddAttachRow(this.value)" checked value="0" />否
						</span>
					</td>
				</tr>
				<tr id="erSaiAdd_attachSelect" style="display:none;">
					<th width="150px" >附件上传：</th>
					<td style="text-align:left" colspan=7>
						<div id="erSaiAdd_attachs"></div>
						<input type="file" id="erSaiAdd_attach" name="attach"/>
						<br/>
						<a href="javascript:$('#erSaiAdd_attach').uploadifyUpload()">上传文件</a>
					</td>
				</tr>
				<tr id="erSaiAdd_attachSubmit" style="display:none;">
					<th width="150px" >已上传附件：</th>
					<td style="text-align:left" colspan=7>
						<div id="erSaiAdd_xxwhAdd_alreadyAttachs"></div>
					</td>
				</tr>
				<c:if test="${sessionScope.sessionInfo.roleNames eq '生产部门管理员'}">
				<tr>
					<th width="150px" >紧急程度：</th>
					<td style="text-align:left" colspan=7>
						<span style="white-space:nowrap;">
						<input type="radio" style="width:30px" name="urgency" value="0" checked="checked" />一般
						<input type="radio" style="width:30px" name="urgency" value="1"  />紧急
						<input type="radio" style="width:30px" name="urgency" value="2" />超紧急
						</span>
					</td>
				</tr>
				</c:if>
				<tr>
					<th width="150px" >申请原因及说明：</th>
					<td colspan=7>
						<textarea id="erSaiAdd_applyReason" name="applyReason" class="easyui-validatebox" data-options="validType:'length[1,4000]'"  style="height: 50px; width:98%" value="" />
					</td>
				</tr>
				<tr>
					<th width="150px">二筛条件（型号、批次、数量）：</th>
					<td colspan=7>
						<textarea id="erSaiAdd_detailRequire" name="detailRequire" class="easyui-validatebox" data-options="validType:'length[1,4000]'"  style="height: 50px; width:98%" value="" />
					</td>
				</tr>
				<tr>
					<th width="150px" >备注：</th>
					<td  colspan=7>
						<textarea id="erSaiAdd_remarks" name="remarks" class="easyui-validatebox" data-options="validType:'length[1,4000]'"  style="height: 50px; width:98%" value="" />
					</td>
				</tr>
				<tr>
					<th width="150px" >生产部门负责人意见：</th>
					<td  colspan=7>
						<textarea id="erSaiAdd_productManagesuggest" name="productManagesuggest" class="easyui-validatebox" data-options="validType:'length[1,4000]'"  style="height: 50px; width:98%" value="" />
					</td>
				</tr>
				<tr>
			         <td colspan=8 style="text-align:center">
			         	<a id="erSaiAdd_zxcl_submit" class="l-btn" href="javascript:void(0);" onclick="javascript:checkAndSubmitErsai()">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">提交审核</span>
			         		</span>
			         	</a>
			         	<a id="erSaiAdd_zxcl_close" class="l-btn" href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
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
