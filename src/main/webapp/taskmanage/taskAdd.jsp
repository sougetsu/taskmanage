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
		$("#attach").uploadify({
			uploader: contextPath+"/jslib/file/uploadify.swf?var='+(new Date()).getTime()' ",//指定文件上传的进度条的位置
			script: '${pageContext.request.contextPath}/taskManage/uploadFile',
			cancelImg: contextPath+"/jslib/file/cancel.png",//取消文件上传的图标
			fileDataName:"attach",//文件上传到服务器的名称(使用file中的name)
			method:"post",//通过post方式上传
			queueID:"attachs",//上传队列说存储的位置
			auto:false,//是否选中之后就上传,false表示选中之后不上传
			multi:true,//时候支持多文件上传
			buttonImg:'..//style/images/liulan.jpg',
			height: 32,
		    width: 55,
			fileDesc:"请选择"+allowDescType,
			fileExt:allowType,
			onComplete: fileComplete,
			onAllComplete:bindEvent
		});
		$("#uploadFile").click(function(){
			$("#attach").uploadifyUpload();
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
	});
	function setAttachRow(value){
		var attachSelect = document.getElementById("attachSelect");
		var attachSubmit = document.getElementById("attachSubmit");
		attachSelect.style.display = "none";
		attachSubmit.style.display = "none";
		if(value==1){
			attachSelect.style.display = (document.all ? "block" : "table-row");
			attachSubmit.style.display = (document.all ? "block" : "table-row");
		}
	}
	function setDetailRow(type,checktype){
		if(type==8){
			var taskpackage = document.getElementById("task_reduction");
			taskpackage.style.display = "none";
			if(checktype){
				taskpackage.style.display = (document.all ? "block" : "table-row");
			}
		}
		if(type==9){
			var taskpackage = document.getElementById("task_dicing");
			taskpackage.style.display = "none";
			if(checktype){
				taskpackage.style.display = (document.all ? "block" : "table-row");
			}
		}
		if(type==11){
			var taskpackage = document.getElementById("task_package");
			taskpackage.style.display = "none";
			if(checktype){
				taskpackage.style.display = (document.all ? "block" : "table-row");
				$('#create_package_table input').validatebox({ 
					required:true 
				});
			}else{
				$('#create_package_table input').validatebox({ 
					required:false
				});
			}
			var selectVal = $("input[name='packageStatusIds']:checked").val();
			if(selectVal == 30)
			{
				$('#create_package_table input[name="discNum"]').validatebox({ 
					required:false
				});
			}
		}
		if(type==3022){
			var taskpackage = document.getElementById("task_mix_package");
			taskpackage.style.display = "none";
			if(checktype){
				taskpackage.style.display = (document.all ? "block" : "table-row");
				$('#create_mix_package_table input').validatebox({ 
					required:true 
				});
			}else{
				$('#create_mix_package_table input').validatebox({ 
					required:false
				});
			}
			var selectVal = $("input[name='mpackageStatusIds']:checked").val();
			if(selectVal == 30)
			{
				$('#create_mix_package_table input[name="mdiscNum"]').validatebox({ 
					required:false
				});
			}
		}
		if(type==3023){
			var taskpackage = document.getElementById("task_multichip_package");
			taskpackage.style.display = "none";
			if(checktype){
				taskpackage.style.display = (document.all ? "block" : "table-row");
				$('#create_multichip_package_table input').validatebox({ 
					required:true 
				});
			}else{
				$('#create_multichip_package_table input').validatebox({ 
					required:false
				});
			}
			var selectVal = $("input[name='mcpackageStatusIds']:checked").val();
			if(selectVal == 30)
			{
				$('#create_multichip_package_table input[name="mcdiscNum"]').validatebox({ 
					required:false
				});
			}
		}
	}
	
	function setPackageRow(value){
		if(value==30){
			$('#create_package_table input[name="discNum"]').validatebox({ 
				required:false
			});
		}else{
			$('#create_package_table input[name="discNum"]').validatebox({ 
				required:true
			});
		}
	}
	function setmPackageRow(value){
		if(value==30){
			$('#create_mix_package_table input[name="mdiscNum"]').validatebox({ 
				required:false
			});
		}else{
			$('#create_mix_package_table input[name="mdiscNum"]').validatebox({ 
				required:true
			});
		}
	}
	function setmcPackageRow(value){
		if(value==30){
			$('#create_multichip_package_table input[name="mcdiscNum"]').validatebox({ 
				required:false
			});
		}else{
			$('#create_multichip_package_table input[name="mcdiscNum"]').validatebox({ 
				required:true
			});
		}
	}
	
	function checkAndSubmit(){
		var submitForm = $('#taskOrder_createForm');
		if(submitForm.form('validate')){
			parent.$.messager.progress({
				title : '提示',
				text : '数据提交中，请稍后....'
			});
			$("#status").val("21");
    		$.ajax({
                type: "POST",
                url:'${pageContext.request.contextPath}/taskManage/create',
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
	$("#Add_electricId").combotree({
    	onSelect:function(node){
    		$("#Add_projectName").val(node.annotation);
    		$("#Add_topicNo").val(node.value);
    		$("#Add_internalModel").val(node.text);
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
	<div align="center" style="margin-top:20px;">
		<form id="taskOrder_createForm"method="post" enctype="multipart/form-data">
		<input type="hidden" id="status" name="status">
		<input type="hidden" id="Add_internalModel" name="internalModel">
			<table class="tasktableForm" width="95%" border="1"  bordercolor="#B5C0C4" rules="none" style="border-collapse:collapse;">
				<tr>
					<th style="width: 150px">电路名称</th>
					<td width=174 colspan=2>
						<input id="Add_electricId" name="electricId" class="easyui-combotree" style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/electricList',parentField : 'pid',required:'true'"
						lines="true" cascadeCheck="false" />
					</td>
					<th width=121 colspan=2>项目名称</th>
					<td width=264 colspan=3>
						<input id="Add_projectName" name="projectName" readonly="true"  style="width:330px;font-size: 12px"/>
					</td>
				</tr>
				<tr>
					<th width="150px">归属部门</th>
					<td width=174 colspan=2>
						<input name="belongDeptId" class="easyui-combotree" style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/organizationList',parentField : 'pid',required:'true'"
						lines="true" cascadeCheck="false" value="${sessionInfo.orgnizationId}"/>
					</td>
					<th width=121 colspan=2>请求协作部门</th>
					<td width=264 colspan=3>
						<input name="helpDeptId" class="easyui-combotree" style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/organizationList',parentField : 'pid',required:'true'"
						lines="true" cascadeCheck="false"/>
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
						<input id="Add_topicNo" name="topicNo" readonly="true" style="width:330px;font-size: 12px"/>
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
					<th width="150px">交付物</th>
					<td width=174 colspan=2><input name="deliverable"
						class="easyui-validatebox"
						data-options="validType:'length[1,30]'"
						style="width:330px" /></td>
					<th width=121 colspan=2>希望完成时间</th>
					<td width=264 colspan=3>
						<input id="wantedEndDate" name="wantedEndDate" class="easyui-validatebox" data-options="required:true"
									onFocus="WdatePicker({readOnly:false,dateFmt:'yyyy-MM-dd'})" style="width:330px"/>
					</td>
				</tr>
				<tr>
					<th width="150px" >是否有附件</th>
					<td width=174px style="text-align:left" colspan=2 >
						<span style="white-space:nowrap;">
							<label><input type="radio" style="width:30px" name="attachmentFlag" onclick="setAttachRow(this.value)" value="1" />是</label>
							<label><input type="radio" style="width:30px" name="attachmentFlag" onclick="setAttachRow(this.value)" value="0" checked="checked"/>否</label>
						</span>
					</td>
					<td width=121 colspan=2>
						<span style="white-space:nowrap;">
						<b>监制</b>
						<label><input type="radio" style="width:30px" name="superviseFlag" value="1" />是</label>
						<label><input type="radio" style="width:30px" name="superviseFlag" value="0" checked="checked"/>否</label>
						</span>
					</td>
					<th >监制单位</th>
					<td colspan=2><input name="superviseUnit"
						class="easyui-validatebox"
						data-options="validType:'length[1,30]'"
						style="width:98%" /></td>
				</tr>
				<tr>
					<th width="150px" >受控详规</th>
					<td width=174px style="text-align:left" colspan=2>
						<span style="white-space:nowrap;">
						<label><input type="radio" style="width:30px" id="controledPlanYes" name="controlledPlanFlag" value="1" />是</label>
						<label><input type="radio" style="width:30px" id="controledPlanNo" name="controlledPlanFlag" value="0" checked="checked" />否</label>
						</span>
					</td>
					<td width=121 colspan=2>
						<span style="white-space:nowrap;">
						<b>会签稿</b>
						<label><input type="radio" style="width:30px" id="countersignYes" name="countersignFlag" value="1" checked="checked"/>是</label>
						<label><input type="radio" style="width:30px" id="countersignNo" name="countersignFlag" value="0"  />否</label>
						</span>
					</td>
					<th >详规号</th>
					<td colspan=2 ><input name="detailPlanNo"
						class="easyui-validatebox" 
						data-options="validType:'length[1,30]'"
						style="width:98%" /></td>
				</tr>
				<tr id="attachSelect" style="display:none;">
					<th width="150px" >附件上传</th>
					<td style="text-align:left" colspan=7>
						<div id="attachs"></div>
						<input type="file" id="attach" name="attach"/>
						<img id="uploadFile" alt="" width="90px";height="32px" src="../style/images/upload.jpg">
					</td>
				</tr>
				<tr id="attachSubmit" style="display:none;">
					<th width="150px" >已上传附件</th>
					<td style="text-align:left" colspan=7>
						<div id="xxwhAdd_alreadyAttachs"></div>
					</td>
				</tr>
				<tr>
					<th width="150px">业务申请内容</th>
					<td style="text-align:left" colspan=7 >
						<input type="checkbox" id="checkbox1" style="vertical-align:middle;width:30px" name="applyContentIds" value="8" onclick="setDetailRow(this.value,this.checked)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox1">圆片减薄</label>
						<input type="checkbox" id="checkbox2" style="vertical-align:middle;width:30px" name="applyContentIds" value="9" onclick="setDetailRow(this.value,this.checked)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox2">圆片划片</label>
						<input type="checkbox" id="checkbox3" style="vertical-align:middle;width:30px" name="applyContentIds" value="10"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox3">中测</label>
						<input type="checkbox" id="checkbox4" style="vertical-align:middle;width:30px" name="applyContentIds" value="11" onclick="setDetailRow(this.value,this.checked)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox4">单片封装</label>
						<input type="checkbox" id="checkbox15" style="vertical-align:middle;width:30px" name="applyContentIds" value="3022" onclick="setDetailRow(this.value,this.checked)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox15">混合封装</label>
						<input type="checkbox" id="checkbox16" style="vertical-align:middle;width:30px" name="applyContentIds" value="3023" onclick="setDetailRow(this.value,this.checked)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox16">多芯片封装</label>
						<input type="checkbox" id="checkbox5" style="vertical-align:middle;width:30px" name="applyContentIds" value="12"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox5">去封装</label>
						<input type="checkbox" id="checkbox6" style="vertical-align:middle;width:30px" name="applyContentIds" value="13"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox6">测试</label>
						<input type="checkbox" id="checkbox7" style="vertical-align:middle;width:30px" name="applyContentIds" value="14"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox7">老化</label>
						<input type="checkbox" id="checkbox8" style="vertical-align:middle;width:30px" name="applyContentIds" value="15"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox8">筛选</label>
						<input type="checkbox" id="checkbox9" style="vertical-align:middle;width:30px" name="applyContentIds" value="16"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox9">鉴定</label>
						<input type="checkbox" id="checkbox10" style="vertical-align:middle;width:30px" name="applyContentIds" value="17"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox10">DPA</label>
						<input type="checkbox" id="checkbox11" style="vertical-align:middle;width:30px" name="applyContentIds" value="18"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox11">失效分析</label>
						<input type="checkbox" id="checkbox12" style="vertical-align:middle;width:30px" name="applyContentIds" value="19"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox12">版图设计</label>
						<input type="checkbox" id="checkbox13" style="vertical-align:middle;width:30px" name="applyContentIds" value="20"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox13">辐照</label>
						<input type="checkbox" id="checkbox14" style="vertical-align:middle;width:30px" name="applyContentIds" value="21"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="checkbox14">其它</label>
					</td>
				</tr>
				<tr id="task_reduction" style="display:none;">
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
				<tr id="task_dicing" style="display:none;">
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
				<tr id="task_package" style="display:none;">
					<th width="150px"></th>
					<td width=558 colspan=7>
						<table id="create_package_table" border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
						   <tr >
						    <td width=31 rowspan=7 >
						    <p ><b><span >单片封装</span></b></p>
						    </td>
						    <td width=83 >
						    <p ><b><span
						    >封装状态</span></b></p>
						    </td>
						    <td width=505 colspan=3 >
						    	<input type="radio" id="package1" style="vertical-align:middle;width:30px" name="packageStatusIds" checked="checked" value="27" onclick="setPackageRow(this.value)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="package1">初样</label>
								<input type="radio" id="package2" style="vertical-align:middle;width:30px" name="packageStatusIds" value="28" onclick="setPackageRow(this.value)" ><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="package2">正样</label>
								<input type="radio" id="package3" style="vertical-align:middle;width:30px" name="packageStatusIds" value="29" onclick="setPackageRow(this.value)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="package3">鉴定生产</label>
								<input type="radio" id="package4" style="vertical-align:middle;width:30px" name="packageStatusIds" value="30" onclick="setPackageRow(this.value)"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="package4">供货生产</label>
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
								style="width:98% " />
						    </td>
						   </tr>
						   <tr>
							    <td width=83 >
							    <p ><b><span
							    >使用圆片号</span></b></p>
							    </td>
							    <td width=194 >
							   		<input name="discNum"
									class="easyui-validatebox"
									data-options="validType:'length[1,100]'"
									style="width:98%" />
							    </td>
							    <td width=87 >
							    <p ><b><span >圆片是否中测/修调</span></b></p>
							    </td>
							    <td width=194 >
							   		<input type="radio" style="width:30px" name="waferFlag" value="1" />是
									<input type="radio" style="width:30px" name="waferFlag" value="0" checked="checked" />否
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
				<tr id="task_mix_package" style="display:none;">
					<th width="150px"></th>
					<td width=558 colspan=7>
						<table id="create_mix_package_table" border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
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
				<tr id="task_multichip_package" style="display:none;">
					<th width="150px"></th>
					<td width=558 colspan=7>
						<table id="create_multichip_package_table" border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
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
									style="width:98%" value=0 />
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
							   		<input type="radio" style="width:30px" name="mcstockName" value="1" /> A库
									<input type="radio" style="width:30px" name="mcstockName" value="0" checked="checked" />B库
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
				<c:if test="${sessionScope.sessionInfo.roleNames eq '生产部门管理员'}">
					<tr>
						<th width="150px" >任务类型</th>
						<td style="text-align:left" colspan=2>
							<input  name="orderTypeId" class="easyui-combotree" style="width:330px;height:26px" data-options="url:'${pageContext.request.contextPath}/dictionary/orderTypeList',parentField : 'pid'"
							lines="true" cascadeCheck="false" value="${taskOrder.orderTypeId}"/>	
						</td>
						<th width="150px" colspan=2 >产品状态</th>
						<td style="text-align:left" colspan=3>
							<label><input type="radio" style="width:30px" name="productStatus" value="0" checked="checked"/>在研</label>
							<label><input type="radio" style="width:30px" name="productStatus" value="1" />老品</label>
						</td>
					</tr>
					
				</c:if>
				<tr>
						<th width="150px" >委托数量</th>
						<td width=174 colspan=2>
							<input name="entrustNum" class="easyui-validatebox" data-options="validType:'length[1,30]'"  style="width:330px"/>	
						</td>
						<th width=121 colspan=2></th>
						<td width=264 colspan=3>
						</td>
					</tr>
				<tr>
					<th width="150px" >鉴定方式</th>
					<td style="text-align:left" colspan=7>
						<input type="checkbox" style="vertical-align:middle;width:30px" id="check1" name="checkTypeId" value="23"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="check1">自鉴</label>
						<input type="checkbox" style="vertical-align:middle;width:30px" id="check2" name="checkTypeId" value="24"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="check2">第三方监督</label>
						<input type="checkbox" style="vertical-align:middle;width:30px" id="check3" name="checkTypeId" value="25"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="check3">第三方直检</label>
						<input type="checkbox" style="vertical-align:middle;width:30px" id="check4" name="checkTypeId" value="55"><label style="vertical-align:middle;display:inline-block;font-size:12px;" for="check4">一致性</label>
					</td>
				</tr>
				<c:if test="${sessionScope.sessionInfo.roleNames eq '生产部门管理员'}">
					<tr>
						<th width="150px" >紧急程度</th>
						<td style="text-align:left" colspan=7>
							<span style="white-space:nowrap;">
							<label><input type="radio" style="width:30px" name="urgency" value="0" checked="checked" />一般</label>
							<label><input type="radio" style="width:30px" name="urgency" value="1"  />紧急</label>
							<label><input type="radio" style="width:30px" name="urgency" value="2" />超紧急</label>
							</span>
						</td>
					</tr>
				</c:if>
				<tr>
					<th width="150px" >申请原因及说明</th>
					<td colspan=7>
						<textarea id="applyReason" name="applyReason" class="easyui-validatebox" data-options="validType:'length[1,1000]'"  style="height: 50px; width:98%" value="" />
					</td>
				</tr>
				<tr>
					<th width="150px">具体要求</th>
					<td colspan=7>
						<textarea id="detailRequire" name="detailRequire" class="easyui-validatebox" data-options="validType:'length[1,1000]'"  style="height: 50px; width:98%" value="" />
					</td>
				</tr>
				<tr>
					<th width="150px" >备注</th>
					<td  colspan=7>
						<textarea id="remarks" name="remarks" class="easyui-validatebox" data-options="validType:'length[1,1000]'"  style="height: 50px; width:98%" value="" />
					</td>
				</tr>
				<tr>
					<th width="150px" >生产部门负责人意见</th>
					<td  colspan=7>
						<textarea id="productManagesuggest" name="productManagesuggest" class="easyui-validatebox" data-options="validType:'length[1,1000]'"  style="height: 50px; width:98%" value="" />
					</td>
				</tr>
				<tr>
			         <td colspan=8 style="text-align:center">
			         	<a id="zxcl_submit" class="l-btn" href="javascript:void(0);" onclick="javascript:checkAndSubmit()">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">提交审核</span>
			         		</span>
			         	</a>
			         	<a id="zxcl_close" class="l-btn" href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
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
