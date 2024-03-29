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
		$("#confirm_attach").uploadify({
			uploader: contextPath+"/jslib/file/uploadify.swf",//指定文件上传的进度条的位置
			script: '${pageContext.request.contextPath}/taskManage/uploadFile',
			cancelImg: contextPath+"/jslib/file/cancel.png",//取消文件上传的图标
			fileDataName:"attach",//文件上传到服务器的名称(使用file中的name)
			method:"post",//通过post方式上传
			queueID:"confirm_attachs",//上传队列说存储的位置
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
		$("#confirm_uploadFile").click(function(){
			$("#confirm_attach").uploadifyUpload();
		});
		function fileComplete(event, ID, fileObj, response, data) {
			var rel = $.parseJSON(response);
			if(checkOp(rel)) {
				var node = "<div id="+rel.id+" newname="+rel.url+
						"<span>"+rel.localfile+"</span>" +
						"<input id="+rel.id+" type='button' style='width:50px' value='删除' class='deleteAttach'/>" +
						"<input type='hidden' name='attachids' value='"+rel.id+"'/></div>" ;
				$("#xxwhAdd_alreadyAttachs").append(node);
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
				$.post("${pageContext.request.contextPath}/taskManage/deleteAttach",{id:id},function(data){
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
			$.post("${pageContext.request.contextPath}/taskManage/deleteAttach",{id:id},function(data){
				if(data.success) {
					$(div).remove();
				} else {
					alert(data.msg);
				}
			},"json");
		});
	});
	function setDetailRow(type,checktype){
		if(type==8){
			var taskpackage = document.getElementById("confirm_task_reduction");
			taskpackage.style.display = "none";
			if(checktype){
				taskpackage.style.display = (document.all ? "block" : "table-row");
			}
		}
		if(type==9){
			var taskpackage = document.getElementById("confirm_task_dicing");
			taskpackage.style.display = "none";
			if(checktype){
				taskpackage.style.display = (document.all ? "block" : "table-row");
			}
		}
		if(type==11){
			var taskpackage = document.getElementById("confirm_task_package");
			taskpackage.style.display = "none";
			if(checktype){
				taskpackage.style.display = (document.all ? "block" : "table-row");
				$('#confirm_package_table input').validatebox({ 
					required:true 
				});
			}else{
				$('#confirm_package_table input').validatebox({ 
					required:false
				});
			}
			var selectVal = $("input[name='packageStatusIds']:checked").val();
			if(selectVal == 30)
			{
				$('#confirm_package_table input[name="discNum"]').validatebox({ 
					required:false
				});
			}
		}
		if(type==3022){
			var taskpackage = document.getElementById("confirm_task_mixpackage");
			taskpackage.style.display = "none";
			if(checktype){
				taskpackage.style.display = (document.all ? "block" : "table-row");
				$('#confirm_mixpackage_table input').validatebox({ 
					required:true 
				});
			}else{
				$('#confirm_mixpackage_table input').validatebox({ 
					required:false
				});
			}
			var selectVal = $("input[name='mpackageStatusIds']:checked").val();
			if(selectVal == 30)
			{
				$('#confirm_mixpackage_table input[name="mdiscNum"]').validatebox({ 
					required:false
				});
			}
		}
		if(type==3023){
			var taskpackage = document.getElementById("confirm_task_multipackage");
			taskpackage.style.display = "none";
			if(checktype){
				taskpackage.style.display = (document.all ? "block" : "table-row");
				$('#confirm_multipackage_table input').validatebox({ 
					required:true 
				});
			}else{
				$('#confirm_multipackage_table input').validatebox({ 
					required:false
				});
			}
			var selectVal = $("input[name='mcpackageStatusIds']:checked").val();
			if(selectVal == 30)
			{
				$('#confirm_multipackage_table input[name="mcdiscNum"]').validatebox({ 
					required:false
				});
			}
		}
	}
	function setPackageRow(value){
		if(value==30){
			$('#confirm_package_table input[name="discNum"]').validatebox({ 
				required:false
			});
		}else{
			$('#confirm_package_table input[name="discNum"]').validatebox({ 
				required:true
			});
		}
	}
	function setmPackageRow(value){
		if(value==30){
			$('#confirm_mixpackage_table input[name="mdiscNum"]').validatebox({ 
				required:false
			});
		}else{
			$('#confirm_mixpackage_table input[name="mdiscNum"]').validatebox({ 
				required:true
			});
		}
	}
	function setmcPackageRow(value){
		if(value==30){
			$('#confirm_multipackage_table input[name="mcdiscNum"]').validatebox({ 
				required:false
			});
		}else{
			$('#confirm_multipackage_table input[name="mcdiscNum"]').validatebox({ 
				required:true
			});
		}
	}
	function confirmOKSubmit(){
		var submitForm = $('#taskOrder_confirmForm');
		if(submitForm.form('validate')){
			parent.$.messager.progress({
				title : '提示',
				text : '数据提交中，请稍后....'
			});
    		$.ajax({
                type: "POST",
                url:'${pageContext.request.contextPath}/taskManage/confirmOK',
                data:submitForm.serialize(),
                dataType : 'json',
				success : function(result) {
					parent.$.messager.progress('close');
					if (result.success) {
						var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
						$('#layout_center_tabs').tabs('close', index);
						$('#taskorder_list_datagrid').datagrid('reload');
						$('#taskorder_suslist_datagrid').datagrid('load');
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
	function confirmNGSubmit(){
		var submitForm = $('#taskOrder_confirmForm');
		if(submitForm.form('validate')){
			parent.$.messager.progress({
				title : '提示',
				text : '数据提交中，请稍后....'
			});
    		$.ajax({
                type: "POST",
                url:'${pageContext.request.contextPath}/taskManage/confirmNG',
                data:submitForm.serialize(),
                dataType : 'json',
				success : function(result) {
					parent.$.messager.progress('close');
					if (result.success) {
						var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
						$('#layout_center_tabs').tabs('close', index);
						$('#taskorder_list_datagrid').datagrid('reload');
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
		var taskConfirm_attachSelect = document.getElementById("taskConfirm_attachSelect");
		var taskConfirm_attachSubmit = document.getElementById("taskConfirm_attachSubmit");
		taskConfirm_attachSelect.style.display = "none";
		taskConfirm_attachSubmit.style.display = "none";
		if(value==1){
			taskConfirm_attachSelect.style.display = (document.all ? "block" : "table-row");
			taskConfirm_attachSubmit.style.display = (document.all ? "block" : "table-row");
		}
	}
	$("#Confirm_electricId").combotree({
    	onSelect:function(node){
    		$("#Confirm_projectName").val(node.annotation);
    		$("#Confirm_topicNo").val(node.value);
    		$("#Confirm_internalModel").val(node.text);
    	}    
    });
	$('input:radio[name="controlledPlanFlag"]').change(function(){
        var a = $("input[name='controlledPlanFlag']:checked").val()
        if ( a == "1") {
        	$("input[name='countersignFlag'][value='0']").prop("checked", "checked");
        }
        if ( a == "0") {
        	$("input[name='countersignFlag'][value='1']").prop("checked", "checked");
        }
    });
	$('input:radio[name="countersignFlag"]').change(function(){
        var a = $("input[name='countersignFlag']:checked").val()
        if ( a == "1") {
        	$("input[name='controlledPlanFlag'][value='0']").prop("checked", "checked");
        }
        if ( a == "0") {
        	$("input[name='controlledPlanFlag'][value='1']").prop("checked", "checked");
        }
    });
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<div data-options="region:'center',border:false">
	<div align="center">
		<form id="taskOrder_confirmForm"method="post">
			<input type="hidden" name="orderId" value="${taskOrder.orderId}"/>
			<table class="tasktableForm" width="95%">
				<tr>
					<th width="150px">任务单号</th>
					<td width=174 colspan=2>
						${taskOrder.lsh}
					</td>
					<th width=101 colspan=2>所内型号</th>
					<td width=264 colspan=3>
						<input id="Confirm_internalModel" name="internalModel"
						class="easyui-validatebox"
						data-options="validType:'length[1,100]'"
						style="width:330px" value="${taskOrder.internalModel}" />
					</td>
				</tr>
				<tr>
					<th style="width: 150px">电路名称</th>
					<td width=174 colspan=2>
						<input id="Confirm_electricId" name="electricId" class="easyui-combotree" style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/electricList',parentField : 'pid'"
						lines="true" cascadeCheck="false" value="${taskOrder.electricId}"/>
					</td>
					<th width=121 colspan=2>项目名称</th>
					<td width=264 colspan=3>
						<input id="Confirm_projectName" name="projectName" readonly="true"  value="${taskOrder.projectName}" style="width:330px;font-size: 12px"/>
					</td>
				</tr>
				<tr>
					<th width="150px">归属部门</th>
					<td width=174 colspan=2>
						<input name="belongDeptId" class="easyui-combotree" style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/organizationList',parentField : 'pid',required:'true'"
						lines="true" cascadeCheck="false" value="${taskOrder.belongDeptId}" />
					</td>
					<th width=121 colspan=2>请求协作部门</th>
					<td width=264 colspan=3>
						<input name="helpDeptId" class="easyui-combotree" style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/organizationList',parentField : 'pid',required:'true'"
						lines="true" cascadeCheck="false" value="${taskOrder.helpDeptId}"/>
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
						<input id="Confirm_topicNo" name="topicNo" readonly="true" style="width:330px;font-size: 12px" value="${taskOrder.topicNo}"/>
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
					<th width="150px">交付物</th>
					<td width=174 colspan=2><input name="deliverable"
						class="easyui-validatebox"
						data-options="validType:'length[1,30]'"
						style="width:330px" value="${taskOrder.deliverable}"/></td>
					<th width=121 colspan=2>希望完成时间</th>
					<td width=264 colspan=3><input id="wantedEndDate" name="wantedEndDate" class="easyui-validatebox" data-options="required:true"
									onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" style="width:330px" value="<fmt:formatDate value="${taskOrder.wantedEndDate}" pattern="yyyy-MM-dd" />"/></td>
				</tr>
				<tr>
					<th width="150px" >是否有附件</th>
					<td width=174px style="text-align:left" colspan=2>
					 	<span style="white-space:nowrap;">
							<input type="radio" style="width:30px" id="attachmentYes" name="attachmentFlag" onclick="setAttachRow(this.value)" value="1" ${taskOrder.attachmentFlag == 1 ? 'checked' : ''}/>是
							<input type="radio" style="width:30px" id="attachmentNo" name="attachmentFlag" onclick="setAttachRow(this.value)" value="0" ${taskOrder.attachmentFlag == 0 ? 'checked' : ''}/>否
						</span>
					</td>
					<td width=121 colspan=2>
						<span style="white-space:nowrap;">
						<b>监制：</b>
						<input type="radio" style="width:30px" id="superviseYes" name="superviseFlag" value="1" ${taskOrder.superviseFlag == 1 ? 'checked' : ''}/>是
						<input type="radio" style="width:30px" id="superviseNo" name="superviseFlag" value="0" ${taskOrder.superviseFlag == 0 ? 'checked' : ''}/>否
						</span>
					</td>
					<th >监制单位</th>
					<td colspan=2><input name="superviseUnit"
						class="easyui-validatebox"
						data-options="validType:'length[1,30]'"
						style="width:98%" value="${taskOrder.superviseUnit}" /></td>
				</tr>
				<tr>
					<th width="150px" >受控详规</th>
					<td width=174px style="text-align:left" colspan=2>
						<span style="white-space:nowrap;">
						<label><input type="radio" style="width:30px" id="controledPlanYes" name="controlledPlanFlag" value="1" ${taskOrder.controlledPlanFlag == 1 ? 'checked' : ''}/>是</label>
						<label><input type="radio" style="width:30px" id="controledPlanNo" name="controlledPlanFlag" value="0" ${taskOrder.controlledPlanFlag == 0 ? 'checked' : ''}/>否</label>
						</span>
					</td>
					<td width=121 colspan=2>
						<span style="white-space:nowrap;">
						<b>会签稿：</b>
						<label><input type="radio" style="width:30px" id="countersignYes" name="countersignFlag" value="1" ${taskOrder.countersignFlag == 1 ? 'checked' : ''}/>是</label>
						<label><input type="radio" style="width:30px" id="countersignNo" name="countersignFlag" value="0" ${taskOrder.countersignFlag == 0 ? 'checked' : ''}/>否</label>
						</span>
					</td>
					<th >详规号</th>
					<td colspan=2><input name="detailPlanNo"
						class="easyui-validatebox"
						data-options="validType:'length[1,30]'"
						style="width:98%" value="${taskOrder.detailPlanNo}"/></td>
				</tr>
				<c:choose>
				   	<c:when test="${taskOrder.attachmentFlag==1}">
						<tr id="taskConfirm_attachSelect">
							<th width="150px" >附件上传</th>
							<td style="text-align:left" colspan=7>
								<div id="confirm_attachs"></div>
								<input type="file" id="confirm_attach" name="attach"/>
								<img id="confirm_uploadFile" alt="" width="90px";height="32px" src="../style/images/upload.jpg">
							</td>
						</tr>
						<tr id="taskConfirm_attachSubmit">
							<th width="150px" >已上传附件</th>
							<td style="text-align:left" colspan=7>
								<div id="xxwhAdd_alreadyAttachs">
									<c:forEach items="${taskOrder.attachment}" var="item">
										<div id="${item.id}" newname="${item.newName}" >
										<span>${item.oldName}</span>
										<a href="${pageContext.request.contextPath}/taskManage/attached?filePath=${item.newName}">查看附件</a>
										<input id="${item.id}" type='button' style="width:50px" value='删除' class='deleteAttachment'/>
										<input type='hidden' name='attachids' value="${item.id}"/></div>
									</c:forEach>
								</div>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr id="taskConfirm_attachSelect" style="display:none;">
							<th width="150px" >附件上传</th>
							<td style="text-align:left" colspan=7>
								<div id="confirm_attachs"></div>
								<input type="file" id="confirm_attach" name="attach"/>
								<img id="uploadFile" alt="" width="90px";height="32px" src="../style/images/upload.jpg">
							</td>
						</tr>
						<tr id="taskConfirm_attachSubmit" style="display:none;">
							<th width="150px" >已上传附件</th>
							<td style="text-align:left" colspan=7>
								<div id="xxwhAdd_alreadyAttachs"></div>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<th width="150px" >业务申请内容</th>
					<td style="text-align:left" colspan=7>
						<input type="checkbox" id="checkbox1" style="vertical-align:middle;width:30px" name="applyContentIds" onclick="setDetailRow(this.value,this.checked)" value="8" ${fn:contains(taskOrder.applyContentIds, '8')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox1">圆片减薄</label>
						<input type="checkbox" id="checkbox2" style="vertical-align:middle;width:30px" name="applyContentIds" onclick="setDetailRow(this.value,this.checked)" value="9" ${fn:contains(taskOrder.applyContentIds, '9')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox2">圆片划片</label>
						<input type="checkbox" id="checkbox3" style="vertical-align:middle;width:30px" name="applyContentIds" value="10" ${fn:contains(taskOrder.applyContentIds, '10')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox3">中测</label>
						<input type="checkbox" id="checkbox4" style="vertical-align:middle;width:30px" name="applyContentIds" onclick="setDetailRow(this.value,this.checked)" value="11" ${fn:contains(taskOrder.applyContentIds, '11')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox4">单片封装</label>
						<input type="checkbox" id="checkbox15" style="vertical-align:middle;width:30px" name="applyContentIds"onclick="setDetailRow(this.value,this.checked)" value="3022"  ${fn:contains(taskOrder.applyContentIds, '3022')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox15">混合封装</label>
						<input type="checkbox" id="checkbox16" style="vertical-align:middle;width:30px" name="applyContentIds"onclick="setDetailRow(this.value,this.checked)" value="3023"  ${fn:contains(taskOrder.applyContentIds, '3023')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox16">多芯片封装</label>
						<input type="checkbox" id="checkbox5" style="vertical-align:middle;width:30px" name="applyContentIds" value="12" ${fn:contains(taskOrder.applyContentIds, '12')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox5">去封装</label>
						<input type="checkbox" id="checkbox6" style="vertical-align:middle;width:30px" name="applyContentIds" value="13" ${fn:contains(taskOrder.applyContentIds, '13')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox6">测试</label>
						<input type="checkbox" id="checkbox7" style="vertical-align:middle;width:30px" name="applyContentIds" value="14" ${fn:contains(taskOrder.applyContentIds, '14')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox7">老化</label>
						<input type="checkbox" id="checkbox8" style="vertical-align:middle;width:30px" name="applyContentIds" value="15" ${fn:contains(taskOrder.applyContentIds, '15')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox8">筛选</label>
						<input type="checkbox" id="checkbox9" style="vertical-align:middle;width:30px" name="applyContentIds" value="16" ${fn:contains(taskOrder.applyContentIds, '16')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox9">鉴定</label>
						<input type="checkbox" id="checkbox10" style="vertical-align:middle;width:30px" name="applyContentIds" value="17" ${fn:contains(taskOrder.applyContentIds, '17')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox10">DPA</label>
						<input type="checkbox" id="checkbox11" style="vertical-align:middle;width:30px" name="applyContentIds" value="18" ${fn:contains(taskOrder.applyContentIds, '18')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox11">失效分析</label>
						<input type="checkbox" id="checkbox12" style="vertical-align:middle;width:30px" name="applyContentIds" value="19" ${fn:contains(taskOrder.applyContentIds, '19')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox12">版图设计</label>
						<input type="checkbox" id="checkbox13" style="vertical-align:middle;width:30px" name="applyContentIds" value="20" ${fn:contains(taskOrder.applyContentIds, '20')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox13">辐照</label>
						<input type="checkbox" id="checkbox14" style="vertical-align:middle;width:30px" name="applyContentIds" value="21" ${fn:contains(taskOrder.applyContentIds, '21')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="checkbox14">其它</label>
					</td>
				</tr>
				<c:choose>
				   	<c:when test="${taskOrder.reductionFlag==1}">
						<tr id="confirm_task_reduction">
							<th width="150px"></th>
							<td width=558 colspan=7>
								<table border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
								   <tr >
								    <td width=31 rowspan=3 >
								    <p ><b><span >减薄</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >圆片批次</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	<input name="reductionNo"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" value="${taskOrder.reductionNo}"/>
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >片号</span></b></p>
								    </td>
								    <td colspan=6>
								   		<input name="reductionTabletsNo"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" value="${taskOrder.reductionTabletsNo}"/>
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >减薄厚度</span></b></p>
								    </td>
								    <td colspan=6>
								   		<input name="reductionThickness"
										class="easyui-numberbox"
										data-options="validType:'length[1,30]'"
										style="width:98%" value="${taskOrder.reductionThickness}"/>
								    </td>
								    </tr>
								 </table>
							</td>
						</tr>
					</c:when>
				   	<c:otherwise>
				   		<tr id="confirm_task_reduction" style="display:none;">
							<th width="150px"></th>
							<td width=558 colspan=7>
								<table border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
								   <tr >
								    <td width=31 rowspan=3 >
								    <p ><b><span >减薄</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >圆片批次</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	<input name="reductionNo"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >片号</span></b></p>
								    </td>
								    <td colspan=6>
								   		<input name="reductionTabletsNo"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >减薄厚度</span></b></p>
								    </td>
								    <td colspan=6>
								   		<input name="reductionThickness"
										class="easyui-numberbox"
										data-options="validType:'length[1,30]'"
										style="width:98%" />
								    </td>
								    </tr>
								 </table>
							</td>
						</tr>
				   	</c:otherwise>
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.dicingFlag==1}">
				   		<tr id="confirm_task_dicing">
							<th width="150px"></th>
							<td width=558 colspan=7>
								<table border="1" bordercolor="#B5C0C4"  style="border-collapse:collapse;">
								   <tr >
								    <td width=31 rowspan=4 >
								    <p ><b><span >划片</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >圆片批次</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	<input name="dicingNo"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" value="${taskOrder.dicingNo}"/>
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >片号</span></b></p>
								    </td>
								    <td colspan=6>
								   		<input name="dicingTabletsNo"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" value="${taskOrder.dicingTabletsNo}" />
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >需管芯数量</span></b></p>
								    </td>
								    <td colspan=6>
								   		<input name="dicingTubeNum"
										class="easyui-numberbox"
										data-options="validType:'length[1,30]'"
										style="width:98%" value="${taskOrder.dicingTubeNum}"/>
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >划片方案</span></b></p>
								    </td>
								    <td colspan=6>
								   		<input name="dicingPlan"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" value="${taskOrder.dicingPlan}"/>
								    </td>
								    </tr>
								 </table>
							</td>
						</tr>
				   	</c:when>
				   	<c:otherwise>
				   		<tr id="confirm_task_dicing" style="display:none;">
							<th width="150px"></th>
							<td width=558 colspan=7>
								<table border="1" bordercolor="#B5C0C4"  style="border-collapse:collapse;">
								   <tr >
								    <td width=31 rowspan=4 >
								    <p ><b><span >划片</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >圆片批次</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	<input name="dicingNo"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >片号</span></b></p>
								    </td>
								    <td colspan=6>
								   		<input name="dicingTabletsNo"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >需管芯数量</span></b></p>
								    </td>
								    <td colspan=6>
								   		<input name="dicingTubeNum"
										class="easyui-numberbox"
										data-options="validType:'length[1,30]'"
										style="width:98%" />
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >划片方案</span></b></p>
								    </td>
								    <td colspan=6>
								   		<input name="dicingPlan"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								    </tr>
								 </table>
							</td>
						</tr>
				   	</c:otherwise>
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.packageFlag==1}">
					<tr id="confirm_task_package">
						<th width="150px"></th>
						<td width=558 colspan=7>
							<table id="confirm_package_table" border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
							   <tr >
							    <td width=31 rowspan=7 >
							    <p ><b><span >封装</span></b></p>
							    </td>
							    <td width=83 >
							    <p ><b><span
							    >封装状态</span></b></p>
							    </td>
							    <td width=505 colspan=3 >
							    	<input type="radio" id="package1" style="vertical-align:middle;width:30px" name="packageStatusIds" value="27" onclick="setPackageRow(this.value)" ${fn:contains(taskOrder.packageStatusIds, '27')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="package1">初样</label>
									<input type="radio" id="package2" style="vertical-align:middle;width:30px" name="packageStatusIds" value="28" onclick="setPackageRow(this.value)" ${fn:contains(taskOrder.packageStatusIds, '28')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="package2">正样</label>
									<input type="radio" id="package3" style="vertical-align:middle;width:30px" name="packageStatusIds" value="29" onclick="setPackageRow(this.value)" ${fn:contains(taskOrder.packageStatusIds, '29')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="package3">鉴定生产</label>
									<input type="radio" id="package4" style="vertical-align:middle;width:30px" name="packageStatusIds" value="30" onclick="setPackageRow(this.value)" ${fn:contains(taskOrder.packageStatusIds, '30')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="package4">供货生产</label>
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >质量等级</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="qualityLevel"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.qualityLevel}"/>
							    </td>
							    <td width=87 >
							    <p ><b><span >圆片批次</span></b></p>
							    </td>
							    <td width=194 >
							  		<input name="discBatch"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.discBatch}"/>
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >数 量</span></b></p>
							    </td>
							    <td width=194 >
							   	<input name="packageNum"
									class="easyui-numberbox"
									data-options="validType:'length[1,30]',required:true"
									style="width:98%" value="${taskOrder.packageNum}"/>
							    </td>
							    <td width=87 >
							    <p ><b><span >芯片标识</span></b></p>
							    </td>
							    <td width=194 >
							    	<input name="chipLabel"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.chipLabel}"/>
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >管壳型号</span></b></p>
							    </td>
							    <td width=194 >
							    <input name="shellType"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.shellType}"/>
							    </td>
							    <td width=87 >
							    <p ><b><span
							    >压焊图号</span></b></p>
							    </td>
							    <td width=194 >
							    	<input name="bondNum"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.bondNum}"/>
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >封装形式</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="packageShape"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.packageShape}"/>
							    </td>
							    <td width=87 >
							    <p ><b><span >打标要求</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="markDemand"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.markDemand}"/>
							    </td>
							   </tr>
							   <tr>
									<td width=83 >
								    <p ><b><span>使用圆片号</span></b></p>
								    </td>
								    <td width=194 >
								   		<input name="discNum"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]',required:true"
										style="width:98%" value="${taskOrder.discNum}"/>
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片是否中测/修调</span></b></p>
								    </td>
								    <td width=194 >
								   		<input type="radio" style="width:30px" name="waferFlag" value="1" ${taskOrder.waferFlag== 1 ? 'checked' : ''} />是
										<input type="radio" style="width:30px" name="waferFlag" value="0" ${taskOrder.waferFlag== 0 ? 'checked' : ''} />否
								    </td>
								</tr>
								<tr>
							    <td width=83 >
							    <p ><b><span
							    >库存</span></b></p>
							    </td>
						    	<td width=194 >
							   		<input type="radio" style="width:30px" name="stockName" value="1" ${taskOrder.stockName== 1 ? 'checked' : ''} />A库
									<input type="radio" style="width:30px" name="stockName" value="0" ${taskOrder.stockName== 0 ? 'checked' : ''} />B库
							    </td>
							    <td width=87 >
							    <p ><b><span ></span></b></p>
							    </td>
							    <td width=194 >
							    </td>
						   	</tr>
							</table>
						</td>
					</tr>
				</c:when>
				   	<c:otherwise>
				   		<tr id="confirm_task_package" style="display:none;">
							<th width="150px"></th>
							<td width=558 colspan=7>
								<table id="confirm_package_table" border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
								   <tr >
								    <td width=31 rowspan=7 >
								    <p ><b><span >封装</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >封装状态</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	<input type="radio" id="package1" style="vertical-align:middle;width:30px" name="packageStatusIds" value="27" checked="checked" onclick="setPackageRow(this.value)"><label style="vertical-align:middle;display:inline-block;" for="package1">初样</label>
										<input type="radio" id="package2" style="vertical-align:middle;width:30px" name="packageStatusIds" value="28" onclick="setPackageRow(this.value)"><label style="vertical-align:middle;display:inline-block;" for="package2">正样</label>
										<input type="radio" id="package3" style="vertical-align:middle;width:30px" name="packageStatusIds" value="29" onclick="setPackageRow(this.value)"><label style="vertical-align:middle;display:inline-block;" for="package3">鉴定生产</label>
										<input type="radio" id="package4" style="vertical-align:middle;width:30px" name="packageStatusIds" value="30" onclick="setPackageRow(this.value)"><label style="vertical-align:middle;display:inline-block;" for="package4">供货生产</label>
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >质量等级</span></b></p>
								    </td>
								    <td width=194 >
								   		<input name="qualityLevel"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片批次</span></b></p>
								    </td>
								    <td width=194 >
								  		<input name="discBatch"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >数 量</span></b></p>
								    </td>
								    <td width=194 >
								   	<input name="packageNum"
										class="easyui-numberbox"
										data-options="validType:'length[1,30]'"
										style="width:98%" value=0/>
								    </td>
								    <td width=87 >
								    <p ><b><span >芯片标识</span></b></p>
								    </td>
								    <td width=194 >
								    	<input name="chipLabel"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >管壳型号</span></b></p>
								    </td>
								    <td width=194 >
								    <input name="shellType"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								    <td width=87 >
								    <p ><b><span
								    >压焊图号</span></b></p>
								    </td>
								    <td width=194 >
								    	<input name="bondNum"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >封装形式</span></b></p>
								    </td>
								    <td width=194 >
								   		<input name="packageShape"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								    <td width=87 >
								    <p ><b><span >打标要求</span></b></p>
								    </td>
								    <td width=194 >
								   		<input name="markDemand"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								   </tr>
								   <tr>
										<td width=83 >
									    <p ><b><span>使用圆片号</span></b></p>
									    </td>
									    <td width=194 >
									   		<input name="discNum"
											class="easyui-validatebox"
											data-options="validType:'length[1,100]'"
											style="width:98%" value="${taskOrder.discNum}"/>
									    </td>
									    <td width=87 >
									    <p ><b><span >圆片是否中测/修调</span></b></p>
									    </td>
									    <td width=194 >
									   		<input type="radio" style="width:30px" name="waferFlag" value="1" ${taskOrder.waferFlag== 1 ? 'checked' : ''} />是
											<input type="radio" style="width:30px" name="waferFlag" value="0" ${taskOrder.waferFlag== 0 ? 'checked' : ''} />否
									    </td>
									</tr>
									<tr>
									    <td width=83 >
									    <p ><b><span
									    >库存</span></b></p>
									    </td>
									    <td width=194 >
									   		<input type="radio" style="width:30px" name="stockName" value="1" /> A库
											<input type="radio" style="width:30px" name="stockName" value="0" checked="checked" />B库
									    </td>
									    <td width=87 >
									    <p ><b><span ></span></b></p>
									    </td>
									    <td width=194 >
									    </td>
								   	</tr>
								</table>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.mixPackageFlag==1}">
					<tr id="confirm_task_mixpackage">
						<th width="150px"></th>
						<td width=558 colspan=7>
							<table id="confirm_mixpackage_table" border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
							   <tr >
							    <td width=31 rowspan=8 >
							    <p ><b><span >混合封装</span></b></p>
							    </td>
							    <td width=83 >
							    <p ><b><span
							    >封装状态</span></b></p>
							    </td>
							    <td width=505 colspan=3 >
							    	<input type="radio" id="package11" style="vertical-align:middle;width:30px" name="mpackageStatusIds" value="27" onclick="setmPackageRow(this.value)" ${fn:contains(taskOrder.mpackageStatusIds, '27')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="package11">初样</label>
									<input type="radio" id="package12" style="vertical-align:middle;width:30px" name="mpackageStatusIds" value="28" onclick="setmPackageRow(this.value)" ${fn:contains(taskOrder.mpackageStatusIds, '28')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="package12">正样</label>
									<input type="radio" id="package13" style="vertical-align:middle;width:30px" name="mpackageStatusIds" value="29" onclick="setmPackageRow(this.value)" ${fn:contains(taskOrder.mpackageStatusIds, '29')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="package13">鉴定生产</label>
									<input type="radio" id="package14" style="vertical-align:middle;width:30px" name="mpackageStatusIds" value="30" onclick="setmPackageRow(this.value)" ${fn:contains(taskOrder.mpackageStatusIds, '30')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="package14">供货生产</label>
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >质量等级</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="mqualityLevel"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mqualityLevel}"/>
							    </td>
							    <td width=87 >
							    <p ><b><span >圆片批次</span></b></p>
							    </td>
							    <td width=194 >
							  		<input name="mdiscBatch"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mdiscBatch}"/>
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >数 量</span></b></p>
							    </td>
							    <td width=194 >
							   	<input name="mpackageNum"
									class="easyui-numberbox"
									data-options="validType:'length[1,30]',required:true"
									style="width:98%" value="${taskOrder.mpackageNum}"/>
							    </td>
							    <td width=87 >
							    <p ><b><span >芯片标识</span></b></p>
							    </td>
							    <td width=194 >
							    	<input name="mchipLabel"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mchipLabel}"/>
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >管壳型号</span></b></p>
							    </td>
							    <td width=194 >
							    <input name="mshellType"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mshellType}"/>
							    </td>
							    <td width=87 >
							    <p ><b><span
							    >压焊图号</span></b></p>
							    </td>
							    <td width=194 >
							    	<input name="mbondNum"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mbondNum}"/>
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >封装形式</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="mpackageShape"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mpackageShape}"/>
							    </td>
							    <td width=87 >
							    <p ><b><span >打标要求</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="mmarkDemand"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mmarkDemand}"/>
							    </td>
							   </tr>
							   <tr>
									<td width=83 >
								    <p ><b><span>使用圆片号</span></b></p>
								    </td>
								    <td width=194 >
								   		<input name="mdiscNum"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]',required:true"
										style="width:98%" value="${taskOrder.mdiscNum}"/>
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片是否中测/修调</span></b></p>
								    </td>
								    <td width=194 >
								   		<input type="radio" style="width:30px" name="mwaferFlag" value="1" ${taskOrder.mwaferFlag== 1 ? 'checked' : ''} />是
										<input type="radio" style="width:30px" name="mwaferFlag" value="0" ${taskOrder.mwaferFlag== 0 ? 'checked' : ''} />否
								    </td>
								</tr>
								<tr>
							    <td width=83 >
							    <p ><b><span
							    >需求芯片数</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="mchipNum"
									class="easyui-numberbox"
									data-options="validType:'length[1,30]'"
									style="width:98%" value="${taskOrder.mchipNum}" />
							    </td>
							    <td width=87 >
							    <p ><b><span >库存是否满足</span></b></p>
							    </td>
							    <td width=194 >
							   		<input type="radio" style="width:30px" name="mstockFlag" value="1" ${taskOrder.mstockFlag== 1 ? 'checked' : ''} />是
									<input type="radio" style="width:30px" name="mstockFlag" value="0" ${taskOrder.mstockFlag== 0 ? 'checked' : ''} />否
							    </td>
						   	</tr>
						   	<tr>
							    <td width=83 >
							    <p ><b><span
							    >库存</span></b></p>
							    </td>
						    	<td width=194 >
							   		<input type="radio" style="width:30px" name="mstockName" value="1" ${taskOrder.mstockName== 1 ? 'checked' : ''} />北微库
									<input type="radio" style="width:30px" name="mstockName" value="0" ${taskOrder.mstockName== 0 ? 'checked' : ''} />民芯库
							    </td>
							    <td width=87 >
							    <p ><b><span ></span></b></p>
							    </td>
							    <td width=194 >
							    </td>
						   	</tr>
							</table>
						</td>
					</tr>
				</c:when>
				   	<c:otherwise>
				   		<tr id="confirm_task_mixpackage" style="display:none;">
							<th width="150px"></th>
							<td width=558 colspan=7>
								<table id="confirm_mixpackage_table" border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
								   <tr >
								    <td width=31 rowspan=8 >
								    <p ><b><span >混合封装</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >封装状态</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	<input type="radio" id="package11" style="vertical-align:middle;width:30px" name="mpackageStatusIds" checked="checked" value="27" onclick="setmPackageRow(this.value)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="package11">初样</label>
										<input type="radio" id="package12" style="vertical-align:middle;width:30px" name="mpackageStatusIds" value="28" onclick="setmPackageRow(this.value)" ><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="package12">正样</label>
										<input type="radio" id="package13" style="vertical-align:middle;width:30px" name="mpackageStatusIds" value="29" onclick="setmPackageRow(this.value)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="package13">鉴定生产</label>
										<input type="radio" id="package14" style="vertical-align:middle;width:30px" name="mpackageStatusIds" value="30" onclick="setmPackageRow(this.value)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="package14">供货生产</label>
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >质量等级</span></b></p>
								    </td>
								    <td width=194 >
								   		<input name="mqualityLevel"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片批次</span></b></p>
								    </td>
								    <td width=194 >
								  		<input name="mdiscBatch"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >数 量</span></b></p>
								    </td>
								    <td width=194 >
								   	<input name="mpackageNum"
										class="easyui-numberbox"
										data-options="validType:'length[1,30]'"
										style="width:98%" value=0/>
								    </td>
								    <td width=87 >
								    <p ><b><span >芯片标识</span></b></p>
								    </td>
								    <td width=194 >
								    	<input name="mchipLabel"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >管壳型号</span></b></p>
								    </td>
								    <td width=194 >
								    <input name="mshellType"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								    <td width=87 >
								    <p ><b><span
								    >压焊图号</span></b></p>
								    </td>
								    <td width=194 >
								    	<input name="mbondNum"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >封装形式</span></b></p>
								    </td>
								    <td width=194 >
								   		<input name="mpackageShape"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								    <td width=87 >
								    <p ><b><span >打标要求</span></b></p>
								    </td>
								    <td width=194 >
								   		<input name="mmarkDemand"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98% " />
								    </td>
								   </tr>
								   <tr>
									    <td width=83 >
									    <p ><b><span
									    >使用圆片号</span></b></p>
									    </td>
									    <td width=194 >
									   		<input name="mdiscNum"
											class="easyui-validatebox"
											data-options="validType:'length[1,100]'"
											style="width:98%" />
									    </td>
									    <td width=87 >
									    <p ><b><span >圆片是否中测/修调</span></b></p>
									    </td>
									    <td width=194 >
									   		<input type="radio" style="width:30px" name="mwaferFlag" value="1" />是
											<input type="radio" style="width:30px" name="mwaferFlag" value="0" checked="checked" />否
									    </td>
								   	</tr>
								   	<tr>
									    <td width=83 >
									    <p ><b><span
									    >需求芯片数</span></b></p>
									    </td>
									    <td width=194 >
									   		<input name="mchipNum"
											class="easyui-numberbox"
											data-options="validType:'length[1,30]'"
											style="width:98%" value=0/>
									    </td>
									    <td width=87 >
									    <p ><b><span >库存是否满足</span></b></p>
									    </td>
									    <td width=194 >
									   		<input type="radio" style="width:30px" name="mstockFlag" value="1" />是
											<input type="radio" style="width:30px" name="mstockFlag" value="0" checked="checked" />否
									    </td>
								   	</tr>
								   	<tr>
									    <td width=83 >
									    <p ><b><span
									    >库存</span></b></p>
									    </td>
									    <td width=194 >
									   		<input type="radio" style="width:30px" name="mstockName" value="1" /> 北微库
											<input type="radio" style="width:30px" name="mstockName" value="0" checked="checked" />民芯库
									    </td>
									    <td width=87 >
									    <p ><b><span ></span></b></p>
									    </td>
									    <td width=194 >
									    </td>
								   	</tr>
								</table>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.mcpackageFlag==1}">
					<tr id="confirm_task_multipackage">
						<th width="150px"></th>
						<td width=558 colspan=7>
							<table id="confirm_multipackage_table" border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
							   <tr >
							    <td width=31 rowspan=8 >
							    <p ><b><span >多芯片封装</span></b></p>
							    </td>
							    <td width=83 >
							    <p ><b><span
							    >封装状态</span></b></p>
							    </td>
							    <td width=505 colspan=3 >
							    	<input type="radio" id="package21" style="vertical-align:middle;width:30px" name="mcpackageStatusIds" value="27" onclick="setmcPackageRow(this.value)" ${fn:contains(taskOrder.mcpackageStatusIds, '27')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="package21">初样</label>
									<input type="radio" id="package22" style="vertical-align:middle;width:30px" name="mcpackageStatusIds" value="28" onclick="setmcPackageRow(this.value)" ${fn:contains(taskOrder.mcpackageStatusIds, '28')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="package22">正样</label>
									<input type="radio" id="package23" style="vertical-align:middle;width:30px" name="mcpackageStatusIds" value="29" onclick="setmcPackageRow(this.value)" ${fn:contains(taskOrder.mcpackageStatusIds, '29')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="package23">鉴定生产</label>
									<input type="radio" id="package24" style="vertical-align:middle;width:30px" name="mcpackageStatusIds" value="30" onclick="setmcPackageRow(this.value)" ${fn:contains(taskOrder.mcpackageStatusIds, '30')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="package24">供货生产</label>
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >质量等级</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="mcqualityLevel"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mcqualityLevel}"/>
							    </td>
							    <td width=87 >
							    <p ><b><span >圆片批次</span></b></p>
							    </td>
							    <td width=194 >
							  		<input name="mcdiscBatch"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mcdiscBatch}"/>
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >数 量</span></b></p>
							    </td>
							    <td width=194 >
							   	<input name="mcpackageNum"
									class="easyui-numberbox"
									data-options="validType:'length[1,30]',required:true"
									style="width:98%" value="${taskOrder.mcpackageNum}"/>
							    </td>
							    <td width=87 >
							    <p ><b><span >芯片标识</span></b></p>
							    </td>
							    <td width=194 >
							    	<input name="mcchipLabel"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mcchipLabel}"/>
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >管壳型号</span></b></p>
							    </td>
							    <td width=194 >
							    <input name="mcshellType"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mcshellType}"/>
							    </td>
							    <td width=87 >
							    <p ><b><span
							    >压焊图号</span></b></p>
							    </td>
							    <td width=194 >
							    	<input name="mcbondNum"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mcbondNum}"/>
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >封装形式</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="mcpackageShape"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mcpackageShape}"/>
							    </td>
							    <td width=87 >
							    <p ><b><span >打标要求</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="mcmarkDemand"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]',required:true"
									style="width:98%" value="${taskOrder.mcmarkDemand}"/>
							    </td>
							   </tr>
							   <tr>
									<td width=83 >
								    <p ><b><span>使用圆片号</span></b></p>
								    </td>
								    <td width=194 >
								   		<input name="mcdiscNum"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]',required:true"
										style="width:98%" value="${taskOrder.mcdiscNum}"/>
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片是否中测/修调</span></b></p>
								    </td>
								    <td width=194 >
								   		<input type="radio" style="width:30px" name="mcwaferFlag" value="1" ${taskOrder.mcwaferFlag== 1 ? 'checked' : ''} />是
										<input type="radio" style="width:30px" name="mcwaferFlag" value="0" ${taskOrder.mcwaferFlag== 0 ? 'checked' : ''} />否
								    </td>
								</tr>
								<tr>
							    <td width=83 >
							    <p ><b><span
							    >需求芯片数</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="mcchipNum"
									class="easyui-numberbox"
									data-options="validType:'length[1,30]'"
									style="width:98%" value="${taskOrder.mcchipNum}" />
							    </td>
							    <td width=87 >
							    <p ><b><span >库存是否满足</span></b></p>
							    </td>
							    <td width=194 >
							   		<input type="radio" style="width:30px" name="mcstockFlag" value="1" ${taskOrder.mcstockFlag== 1 ? 'checked' : ''} />是
									<input type="radio" style="width:30px" name="mcstockFlag" value="0" ${taskOrder.mcstockFlag== 0 ? 'checked' : ''} />否
							    </td>
						   	</tr>
						   	<tr>
							    <td width=83 >
							    <p ><b><span
							    >库存</span></b></p>
							    </td>
						    	<td width=194 >
							   		<input type="radio" style="width:30px" name="mcstockName" value="1" ${taskOrder.mcstockName== 1 ? 'checked' : ''} />北微库
									<input type="radio" style="width:30px" name="mcstockName" value="0" ${taskOrder.mcstockName== 0 ? 'checked' : ''} />民芯库
							    </td>
							    <td width=87 >
							    <p ><b><span ></span></b></p>
							    </td>
							    <td width=194 >
							    </td>
						   	</tr>
							</table>
						</td>
					</tr>
				</c:when>
				   	<c:otherwise>
				   		<tr id="confirm_task_multipackage" style="display:none;">
							<th width="150px"></th>
							<td width=558 colspan=7>
								<table id="confirm_multipackage_table" border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
								<tr >
							    <td width=31 rowspan=8 >
							    <p ><b><span >多芯片封装</span></b></p>
							    </td>
							    <td width=83 >
							    <p ><b><span
							    >封装状态</span></b></p>
							    </td>
							    <td width=505 colspan=3 >
							    	<input type="radio" id="package21" style="vertical-align:middle;width:30px" name="mcpackageStatusIds" checked="checked" value="27" onclick="setmcPackageRow(this.value)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="package21">初样</label>
									<input type="radio" id="package22" style="vertical-align:middle;width:30px" name="mcpackageStatusIds" value="28" onclick="setmcPackageRow(this.value)" ><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="package22">正样</label>
									<input type="radio" id="package23" style="vertical-align:middle;width:30px" name="mcpackageStatusIds" value="29" onclick="setmcPackageRow(this.value)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="package23">鉴定生产</label>
									<input type="radio" id="package24" style="vertical-align:middle;width:30px" name="mcpackageStatusIds" value="30" onclick="setmcPackageRow(this.value)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="package24">供货生产</label>
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >质量等级</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="mcqualityLevel"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]'"
									style="width:98%" />
							    </td>
							    <td width=87 >
							    <p ><b><span >圆片批次</span></b></p>
							    </td>
							    <td width=194 >
							  		<input name="mcdiscBatch"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]'"
									style="width:98%" />
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >数 量</span></b></p>
							    </td>
							    <td width=194 >
							   	<input name="mcpackageNum"
									class="easyui-numberbox"
									data-options="validType:'length[1,30]'"
									style="width:98%" value=0/>
							    </td>
							    <td width=87 >
							    <p ><b><span >芯片标识</span></b></p>
							    </td>
							    <td width=194 >
							    	<input name="mcchipLabel"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]'"
									style="width:98%" />
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >管壳型号</span></b></p>
							    </td>
							    <td width=194 >
							    <input name="mcshellType"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]'"
									style="width:98%" />
							    </td>
							    <td width=87 >
							    <p ><b><span
							    >压焊图号</span></b></p>
							    </td>
							    <td width=194 >
							    	<input name="mcbondNum"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]'"
									style="width:98%" />
							    </td>
							   </tr>
							   <tr >
							    <td width=83 >
							    <p ><b><span
							    >封装形式</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="mcpackageShape"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]'"
									style="width:98%" />
							    </td>
							    <td width=87 >
							    <p ><b><span >打标要求</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="mcmarkDemand"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]'"
									style="width:98% " />
							    </td>
							   </tr>
							   <tr>
								    <td width=83 >
								    <p ><b><span
								    >使用圆片号</span></b></p>
								    </td>
								    <td width=194 >
								   		<input name="mcdiscNum"
										class="easyui-validatebox"
										data-options="validType:'length[1,100]'"
										style="width:98%" />
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片是否中测/修调</span></b></p>
								    </td>
								    <td width=194 >
								   		<input type="radio" style="width:30px" name="mcwaferFlag" value="1" />是
										<input type="radio" style="width:30px" name="mcwaferFlag" value="0" checked="checked" />否
								    </td>
							   	</tr>
							   	<tr>
								    <td width=83 >
								    <p ><b><span
								    >需求芯片数</span></b></p>
								    </td>
								    <td width=194 >
								   		<input name="mcchipNum"
										class="easyui-numberbox"
										data-options="validType:'length[1,30]'"
										style="width:98%" value=0/>
								    </td>
								    <td width=87 >
								    <p ><b><span >库存是否满足</span></b></p>
								    </td>
								    <td width=194 >
								   		<input type="radio" style="width:30px" name="mcstockFlag" value="1" />是
										<input type="radio" style="width:30px" name="mcstockFlag" value="0" checked="checked" />否
								    </td>
							   	</tr> 
							   	<tr>
							    <td width=83 >
							    <p ><b><span
							    >库存</span></b></p>
							    </td>
							    <td width=194 >
							   		<input type="radio" style="width:30px" name="mcstockName" value="1" /> 北微库
									<input type="radio" style="width:30px" name="mcstockName" value="0" checked="checked" />民芯库
							    </td>
							    <td width=87 >
							    <p ><b><span ></span></b></p>
							    </td>
							    <td width=194 >
							    </td>
						   	</tr>	  
								</table>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.urgencyState==1}">
						<tr>
							<th width="150px" >任务类型</th>
							<td style="text-align:left" colspan=2>
								<input  name="orderTypeId" class="easyui-combotree" style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/orderTypeList',parentField : 'pid'"
								lines="true" cascadeCheck="false" value="${taskOrder.orderTypeId}"/>	
							</td>
							<th width="150px" colspan=2>产品状态</th>
							<td style="text-align:left" colspan=3>
								<label><input type="radio" style="width:30px" name="productStatus" value="0" ${taskOrder.productStatus == 0 ? 'checked' : ''} />在研</label>
								<label><input type="radio" style="width:30px" name="productStatus" value="1" ${taskOrder.productStatus == 1 ? 'checked' : ''} />老品</label>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th width="150px" >任务类型</th>
							<td style="text-align:left" colspan=2>
								<input  name="orderTypeId" class="easyui-combotree" readonly = true style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/orderTypeList',parentField : 'pid'"
								lines="true" cascadeCheck="false" value="${taskOrder.orderTypeId}"/>	
							</td>
							<th width="150px" colspan=2>产品状态</th>
							<td style="text-align:left" colspan=3>
								<input type="hidden" name="productStatus" value = ${taskOrder.productStatus}>
								<label><input type="radio" disabled style="width:30px" name="productStatus" value="0" ${taskOrder.productStatus == 0 ? 'checked' : ''} />在研</label>
								<label><input type="radio" disabled style="width:30px" name="productStatus" value="1" ${taskOrder.productStatus == 1 ? 'checked' : ''} />老品</label>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<th width="150px" >委托数量</th>
					<td width=174 colspan=2>
						<input name="entrustNum" class="easyui-validatebox" data-options="validType:'length[1,30]'" value="${taskOrder.entrustNum}" style="width:330px"/>	
					</td>
					<th width=121 colspan=2></th>
					<td width=264 colspan=3>
					</td>
				</tr>
				<tr>
					<th width="150px" >鉴定方式</th>
					<td style="text-align:left" colspan=7>
						<input type="checkbox" style="vertical-align:middle;width:30px" id="check1" name="checkTypeId" value="23" ${fn:contains(taskOrder.checkTypeId, '23')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="check1">自鉴</label>
						<input type="checkbox" style="vertical-align:middle;width:30px" id="check2" name="checkTypeId" value="24" ${fn:contains(taskOrder.checkTypeId, '24')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="check2">第三方监督</label>
						<input type="checkbox" style="vertical-align:middle;width:30px" id="check3" name="checkTypeId" value="25" ${fn:contains(taskOrder.checkTypeId, '25')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="check3">第三方直检</label>
						<input type="checkbox" style="vertical-align:middle;width:30px" id="check4" name="checkTypeId" value="55" ${fn:contains(taskOrder.checkTypeId, '55')? 'checked' : ''}><label style="vertical-align:middle;display:inline-block;" for="check4">一致性</label>
					</td>
				</tr>
				<c:choose>
				   	<c:when test="${taskOrder.urgencyState==0}">
						<tr>
							<th width="150px" >紧急程度</th>
							<td style="text-align:left" colspan=7>
								<span style="white-space:nowrap;">
									${taskOrder.urgencyName}
									<input type="hidden" style="width:30px" value="${taskOrder.urgency}" name="urgency" />
								</span>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th width="150px" >紧急程度</th>
							<td style="text-align:left" colspan=7>
								<span style="white-space:nowrap;">
								<label><input type="radio" style="width:30px" name="urgency" value="0" ${taskOrder.urgency == 0 ? 'checked' : ''} />一般</label>
								<label><input type="radio" style="width:30px" name="urgency" value="1" ${taskOrder.urgency == 1 ? 'checked' : ''} />紧急</label>
								<label><input type="radio" style="width:30px" name="urgency" value="2" ${taskOrder.urgency == 2 ? 'checked' : ''} />超紧急</label>
								</span>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<th width="150px" >申请原因及说明</th>
					<td colspan=7>
						<textarea id="applyReason" name="applyReason" class="easyui-validatebox" data-options="validType:'length[1,1000]'"  style="height: 50px; width:98%">${taskOrder.applyReason}</textarea>
					</td>
				</tr>
				<tr>
					<th width="150px">具体要求</th>
					<td colspan=7>
						<textarea id="detailRequire" name="detailRequire" class="easyui-validatebox" data-options="validType:'length[1,1000]'"  style="height: 50px; width:98%">${taskOrder.detailRequire}</textarea>
					</td>
				</tr>
				<tr>
					<th width="150px" >备注</th>
					<td colspan=7>
						<textarea id="remarks" name="remarks" class="easyui-validatebox" data-options="validType:'length[1,1000]'"  style="height: 50px; width:98%">${taskOrder.remarks}</textarea>
					</td>
				</tr>
				<tr>
					<th width="150px" >生产部门负责人意见</th>
					<td colspan=7>
						<textarea id="productManagesuggest" name="productManagesuggest" class="easyui-validatebox" data-options="validType:'length[1,1000]'"  style="height: 50px; width:98%">${taskOrder.productManagesuggest}</textarea>
					</td>
				</tr>
				<tr>
			         <td colspan=8 style="text-align:center">
			         	<a id="confirm_submit" class="l-btn" href="javascript:void(0);" onclick="javascript:confirmOKSubmit()">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">审核通过</span>
			         		</span>
			         	</a>
			         	<a id="confirm_ng" class="l-btn" href="javascript:void(0);" onclick="javascript:confirmNGSubmit()">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-cancel l-btn-icon-left" style="padding-left: 20px; ">审核不通过</span>
			         		</span>
			         	</a>
			         	<a id="confirm_close" class="l-btn" href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
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
