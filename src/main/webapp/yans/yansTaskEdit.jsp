<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		var contextPath = '${pageContext.request.contextPath}';
		var sessionId = $("#sessionId").val();
		var allowDescType = "jpg;png;gif;txt;doc;xls;pdf;rar";
		var allowType = "*.jpg;*.png;*.gif;*.txt;*.doc;*.xls;*.docx;*.xlsx;*.pdf;*.rar";
		$("#yans_taskEdit_attach").uploadify({
			uploader: contextPath+"/jslib/file/uploadify.swf?var='+(new Date()).getTime()' ",//指定文件上传的进度条的位置
			script: '${pageContext.request.contextPath}/yansTaskManage/uploadFile',
			cancelImg: contextPath+"/jslib/file/cancel.png",//取消文件上传的图标
			fileDataName:"attach",//文件上传到服务器的名称(使用file中的name)
			method:"post",//通过post方式上传
			queueID:"yans_taskEdit_attachs",//上传队列说存储的位置
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
				$("#yans_taskEdit_alreadyAttachs").append(node);
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
				$.post("${pageContext.request.contextPath}/yansTaskManage/deleteAttach",{id:id},function(data){
					if(data.success) {
						$(div).remove();
					} else {
						alert(data.msg);
					}
				},"json");
			});
		}
		$(".deleteAttachment").click(function(){
			var id = this.id;
			var div = $(this).parent("div");
			$.post("${pageContext.request.contextPath}/yansTaskManage/deleteAttach",{id:id},function(data){
				if(data.success) {
					$(div).remove();
				} else {
					alert(data.msg);
				}
			},"json");
		});
	});
	function editSubmit(){
		var submitForm = $('#yans_taskOrder_editForm');
		if(submitForm.form('validate')){
			parent.$.messager.progress({
				title : '提示',
				text : '数据提交中，请稍后....'
			});
    		$.ajax({
                type: "POST",
                url:'${pageContext.request.contextPath}/yansTaskManage/editSubmit',
                data:submitForm.serialize(),
                dataType : 'json',
				success : function(result) {
					parent.$.messager.progress('close');
					if (result.success) {
						var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
						$('#layout_center_tabs').tabs('close', index);
						$('#yans_taskorder_list_datagrid').datagrid('reload');	
						$('#yans_taskorder_suslist_datagrid').datagrid('reload');
					}
					$.messager.show({
						title : '提示',
						msg : result.msg,
						timeout:3000,
						showType:'fade',
						style:{
							right:'',
							bottom:'-document.body.scrollTop-document.documentElement.scrollTop'
						}
					});
				}
            });
    	}
	}
	function setAttachRow(value){
		var taskedit_attachSelect = document.getElementById("yans_taskEdit_attachSelect");
		var taskedit_attachSubmit = document.getElementById("yans_taskEdit_attachSubmit");
		taskedit_attachSelect.style.display = "none";
		taskedit_attachSubmit.style.display = "none";
		if(value==1){
			taskedit_attachSelect.style.display = (document.all ? "block" : "table-row");
			taskedit_attachSubmit.style.display = (document.all ? "block" : "table-row");
		}
	}
	function setErsaiRow(value){
		var ersaiNumSelect = document.getElementById("yans_taskEdit_ersaiNum");
		ersaiNumSelect.style.display = "none";
		if(value==1){
			ersaiNumSelect.style.display = "table-row";
		}
	}
	function setGoldCutRow(value){
		var goldCutSelect = document.getElementById("yans_Edit_goldno");
		goldCutSelect.style.display = "none";
		if(value==1){
			goldCutSelect.style.display = "table-row";
		}
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<div data-options="region:'center',border:false">
	<div align="center">
		<form id="yans_taskOrder_editForm"method="post">
			<input type="hidden" name="orderId" value="${taskOrder.orderId}"/>
			<table class="tasktableForm" width="95%">
				<tr>
					<th style="width: 150px">任务类型</th>
					<td width=174 colspan=2>
						<input  name="taskType" class="easyui-validatebox"
						data-options="required:true,validType:'length[1,100]'"
						style="width:330px" value="${taskOrder.taskType}"/>
					</td>
					<th width=121 colspan=2>通知单号</th>
					<td width=264 colspan=3>
						<input name="reportNo"
						class="easyui-validatebox"
						data-options="required:true,validType:'length[1,100]'"
						style="width:330px" value="${taskOrder.reportNo}"/></td>
					</td>
				</tr>
				<tr>
					<th width="150px" >所内型号：</th>
					<td colspan=7>
						<textarea  name="internalModel" class="easyui-validatebox" data-options="validType:'length[1,4000]'"  style="height: 50px; width:98%">${taskOrder.internalModel}</textarea>
					</td>
				</tr>
				<tr>
					<th width="150px">申请部门</th>
					<td width=174 colspan=2><input name="applyDept"
						readonly="true"
						style="width:330px;font-size: 12px;" value="${taskOrder.applyDept}"/></td>
					<th width=121 colspan=2>申请人</th>
					<td width=106><input name="applyMember"
						class="easyui-validatebox"
						data-options="validType:'length[1,30]'"
						style="width:98%" value="${taskOrder.applyMember}"/></td>
					<th width=42>电话</th>
					<td width=115><input name="applyMemberPhone"
						class="easyui-validatebox"
						data-options="validType:'length[1,30]'"
						style="width:98%" value="${taskOrder.applyMemberPhone}"/></td>
				</tr>
				<tr>
					<th width="150px">课题号</th>
					<td width=174 colspan=2>
						<input name="topicId" class="easyui-combotree" style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/yanstopicList',parentField : 'pid',required:'true'"
						lines="true" cascadeCheck="false" value="${taskOrder.topicId}"/>
					</td>
					<th width=121 colspan=2>项目负责人</th>
					<td width=106><input name="projectManager"
						class="easyui-validatebox"
						data-options="validType:'length[1,30]'"
						style="width:98%" value="${taskOrder.projectManager}"/></td>
					<th width=42>电话</th>
					<td width=115><input name="projectManagerPhone"
						class="easyui-validatebox"
						data-options="validType:'length[1,30]'"
						style="width:98%" value="${taskOrder.projectManagerPhone}"/></td>
				</tr>
				<tr>
					<th width=121 colspan=>请求协作部门</th>
					<td width=264 colspan=2>
						<input name="helpDeptId" class="easyui-combotree" style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/organizationList',parentField : 'pid',required:'true'"
						lines="true" cascadeCheck="false" value="${taskOrder.helpDeptId}"/>
					</td>
					<th width=121 colspan=2>希望完成时间</th>
					<td width=264 colspan=3><input id="wantedEndDate" name="wantedEndDate" class="easyui-validatebox" data-options="required:true"
									onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" style="width:330px" value="<fmt:formatDate value="${taskOrder.wantedEndDate}" pattern="yyyy-MM-dd" />"/></td>
				</tr>
				<tr>
					<th width="150px" >是否切金：</th>
					<td width=174px style="text-align:left" colspan=2 >
						<span style="white-space:nowrap;">
							<input type="radio" style="width:30px" name="goldcutFlag"  onclick="setGoldCutRow(this.value)" value="1" ${taskOrder.goldcutFlag == 1 ? 'checked' : ''}/>是
							<input type="radio" style="width:30px" name="goldcutFlag"  onclick="setGoldCutRow(this.value)" value="0" ${taskOrder.goldcutFlag == 0 ? 'checked' : ''}/>否
						</span>
					</td>
					<th width=121 colspan=2></th>
					<td width=264 colspan=3></td>
				</tr>
				
				<c:choose>
				   	<c:when test="${taskOrder.goldcutFlag==1}">
						<tr id="yans_Edit_goldno" >
							<th width="150px" >切金编号</th>
							<td width=174px style="text-align:left" colspan=2 >
								<input name="goldcutNo" style="width:330px" value="${taskOrder.goldcutNo}"/>
							</td>
							<th width=121 colspan=2></th>
							<td width=264 colspan=3></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr id="yans_Edit_goldno" style="display:none;">
							<th width="150px" >切金编号</th>
							<td width=174px style="text-align:left" colspan=2 >
								<input name="goldcutNo" style="width:330px"/>
							</td>
							<th width=121 colspan=2></th>
							<td width=264 colspan=3></td>
						</tr>
					</c:otherwise>
				</c:choose>
				
				<tr>
					<th width="150px" >是否有附件：</th>
					<td width=174px style="text-align:left" colspan=7>
					 	<span style="white-space:nowrap;">
							<input type="radio" style="width:30px" id="attachmentYes" name="attachmentFlag" onclick="setAttachRow(this.value)" value="1" ${taskOrder.attachmentFlag == 1 ? 'checked' : ''}/>是
							<input type="radio" style="width:30px" id="attachmentNo" name="attachmentFlag" onclick="setAttachRow(this.value)" value="0" ${taskOrder.attachmentFlag == 0 ? 'checked' : ''}/>否
						</span>
					</td>
				</tr>
				
				<c:choose>
				   	<c:when test="${taskOrder.attachmentFlag==1}">
						<tr id="yans_taskEdit_attachSelect">
							<th width="150px" >附件上传：</th>
							<td style="text-align:left" colspan=7>
								<div id="yans_taskEdit_attachs"></div>
								<input type="file" id="yans_taskEdit_attach" name="attach"/>
								<br/>
								<a href="javascript:$('#yans_taskEdit_attach').uploadifyUpload()">上传文件</a>
							</td>
						</tr>
						<tr id="yans_taskEdit_attachSubmit">
							<th width="150px" >已上传附件：</th>
							<td style="text-align:left" colspan=7>
								<div id="yans_taskEdit_alreadyAttachs">
									<c:forEach items="${taskOrder.attachment}" var="item">
										<div id="${item.id}" newname="${item.newName}" >
										<span>${item.oldName}</span>
										<a href="${pageContext.request.contextPath}/yansTaskManage/attached?filePath=${item.newName}">查看附件</a>
										<a id="${item.id}" href='javascript:void(0);' style='padding-left:10px;' class='deleteAttachment'>删除</a>
										<input type='hidden' name='attachids' value="${item.id}"/></div>
									</c:forEach>
								</div>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr id="yans_taskEdit_attachSelect" style="display:none;">
							<th width="150px" >附件上传：</th>
							<td style="text-align:left" colspan=7>
								<div id="yans_taskEdit_attachs"></div>
								<input type="file" id="yans_taskEdit_attach" name="attach"/>
								<br/>
								<a href="javascript:$('#yans_taskEdit_attach').uploadifyUpload()">上传文件</a>
							</td>
						</tr>
						<tr id="yans_taskEdit_attachSubmit" style="display:none;">
							<th width="150px" >已上传附件：</th>
							<td style="text-align:left" colspan=7>
								<div id="yans_taskEdit_alreadyAttachs"></div>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<th width="150px" >是否使用二筛过的电路</th>
					<td width=174px style="text-align:left" colspan=2 >
						<span style="white-space:nowrap;">
							<input type="radio" style="width:30px" name="ersaiFlag" onclick="setErsaiRow(this.value)" value="1" ${taskOrder.ersaiFlag == 1 ? 'checked' : ''}/>是
							<input type="radio" style="width:30px" name="ersaiFlag" onclick="setErsaiRow(this.value)" value="0" ${taskOrder.ersaiFlag == 0 ? 'checked' : ''}/>否
						</span>
					</td>
				</tr>
				<c:choose>
				   	<c:when test="${taskOrder.ersaiFlag==1}">
						<tr id="yans_taskEdit_ersaiNum">
							<th style="width: 150px">二筛任务单号</th>
							<td width=174 colspan=2>
								<input  name="ersaiLsh"
								style="width:330px" value="${taskOrder.ersaiLsh}"/>
							</td>
							<th width=121 colspan=2>数量</th>
							<td width=264 colspan=3>
								<input name="yansNum"
								class="easyui-validatebox"
								data-options="validType:'length[1,100]'"
								style="width:330px" value="${taskOrder.yansNum}"/>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr id="yans_taskEdit_ersaiNum" style="display:none;">
							<th style="width: 150px">二筛任务单号</th>
							<td width=174 colspan=2>
								<input  name="ersaiLsh"
								style="width:330px" />
							</td>
							<th width=121 colspan=2>数量</th>
							<td width=264 colspan=3>
								<input name="yansNum"
								class="easyui-validatebox"
								data-options="validType:'length[1,100]' "
								style="width:330px" value="0"/>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<th width="150px" >电路是否借库</th>
					<td width=174px style="text-align:left" colspan=2 >
						<span style="white-space:nowrap;">
							<input type="radio" style="width:30px" name="borrow"  value="1" ${taskOrder.borrow == 1 ? 'checked' : ''} ${taskOrder.borrowEditState == 0 ? 'disabled' : ''} />是
							<input type="radio" style="width:30px" name="borrow"  value="0" ${taskOrder.borrow == 0 ? 'checked' : ''} ${taskOrder.borrowEditState == 0 ? 'disabled' : ''}/>否
						</span>
					</td>
					<th width=121 colspan=2></th>
					<td width=264 colspan=3></td>
				</tr>
				<tr>
					<th width="150px" >紧急程度：</th>
					<td style="text-align:left" colspan=7>
						<span style="white-space:nowrap;">
						<input type="radio" style="width:30px" name="urgency" value="0" ${taskOrder.urgency == 0 ? 'checked' : ''}  ${taskOrder.urgencyState == 0 ? 'disabled' : ''}/>一般
						<input type="radio" style="width:30px" name="urgency" value="1" ${taskOrder.urgency == 1 ? 'checked' : ''}   ${taskOrder.urgencyState == 0 ? 'disabled' : ''}/>紧急
						<input type="radio" style="width:30px" name="urgency" value="2" ${taskOrder.urgency == 2 ? 'checked' : ''}   ${taskOrder.urgencyState == 0 ? 'disabled' : ''}/>超紧急
						</span>
					</td>
				</tr>
				<tr>
					<th width="150px" >申请原因及说明：</th>
					<td colspan=7>
						<textarea id="applyReason" name="applyReason" class="easyui-validatebox" data-options="validType:'length[1,4000]'"  style="height: 50px; width:98%">${taskOrder.applyReason}</textarea>
					</td>
				</tr>
				<tr>
					<th width="150px">具体要求：</th>
					<td colspan=7>
						<textarea id="detailRequire" name="detailRequire" class="easyui-validatebox" data-options="validType:'length[1,4000]'"  style="height: 50px; width:98%">${taskOrder.detailRequire}</textarea>
					</td>
				</tr>
				<tr>
					<th width="150px" >备注：</th>
					<td colspan=7>
						<textarea id="remarks" name="remarks" class="easyui-validatebox" data-options="validType:'length[1,4000]'"  style="height: 50px; width:98%">${taskOrder.remarks}</textarea>
					</td>
				</tr>
				<tr>
					<th width="150px" >生产部门负责人意见：</th>
					<td colspan=7>
						<textarea id="productManagesuggest" name="productManagesuggest" class="easyui-validatebox" data-options="validType:'length[1,4000]'"  style="height: 50px; width:98%">${taskOrder.productManagesuggest}</textarea>
					</td>
				</tr>
				<tr>
			         <td colspan=8 style="text-align:center">
			         <c:choose>
				   		<c:when test="${taskOrder.status==51}">
				         	<a id="edit_submit" class="l-btn" href="javascript:void(0);" onclick="javascript:editSubmit()">
				         		<span class="l-btn-left">
				         			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">保存</span>
				         		</span>
				         	</a>
				         </c:when>
				         <c:otherwise>
				         	<a id="edit_submit" class="l-btn" href="javascript:void(0);" onclick="javascript:editSubmit()">
				         		<span class="l-btn-left">
				         			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">提交审核</span>
				         		</span>
				         	</a>
				         </c:otherwise>
				        </c:choose>
			         	<a id="edit_close" class="l-btn" href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
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
