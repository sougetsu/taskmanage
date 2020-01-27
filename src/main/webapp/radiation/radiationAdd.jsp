<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
$(function() {
	parent.$.messager.progress('close');
	var contextPath = '${pageContext.request.contextPath}';
	var sessionId = $("#sessionId").val();
	var allowDescType = "jpg;png;gif;txt;doc;xls;pdf;rar";
	var allowType = "*.jpg;*.png;*.gif;*.txt;*.doc;*.xls;*.docx;*.xlsx;*.pdf;*.rar";
	var editRow_single = undefined;
	var editRow_integral = undefined;
	$('#single_datagrid').datagrid({
	    toolbar : [ {
	    	text: '添加', iconCls: 'icon-add', handler: function () {
	         	if (editRow_single != undefined) {
	                    $("#single_datagrid").datagrid('endEdit', editRow_single);
	                }
	                if (editRow_single == undefined) {
	                    $("#single_datagrid").datagrid('insertRow', {
	                        index: 0,
	                        row: {}
	                    });
	 
	                    $("#single_datagrid").datagrid('beginEdit', 0);
	                    editRow_single = 0;
	                }
	            }
		        }, '-', {
		            text: '保存', iconCls: 'icon-save', handler: function () {
		                $("#single_datagrid").datagrid('endEdit', editRow_single);
		 
		                //如果调用acceptChanges(),使用getChanges()则获取不到编辑和新增的数据。
		 
		                //使用JSON序列化datarow对象，发送到后台。
		                var rows = $("#single_datagrid").datagrid('getChanges');
		 
		                var rowstr = JSON.stringify(rows);
		                $.post('/Home/Create', rowstr, function (data) {
		                     
		                });
		            }
		        }, '-', {
		            text: '撤销', iconCls: 'icon-redo', handler: function () {
		                editRow_single = undefined;
		                $("#single_datagrid").datagrid('rejectChanges');
		                $("#single_datagrid").datagrid('unselectAll');
		            }
		        }, '-', {
		            text: '删除', iconCls: 'icon-remove', handler: function () {
		                var row = $("#single_datagrid").datagrid('getSelected');
		 				var index = $("#single_datagrid").datagrid('getRowIndex', row);
		                $("#single_datagrid").datagrid('deleteRow',index);
		            }
		        }, '-', {
			            text: '修改', iconCls: 'icon-edit', handler: function () {
				                var row = $("#single_datagrid").datagrid('getSelected');
				                if (row !=null) {
				                    if (editRow_single != undefined) {
				                        $("#single_datagrid").datagrid('endEdit', editRow_single);
				                    }
				 
				                    if (editRow_single == undefined) {
				                        var index = $("#single_datagrid").datagrid('getRowIndex', row);
				                        $("#single_datagrid").datagrid('beginEdit', index);
				                        editRow_single = index;
				                        $("#single_datagrid").datagrid('unselectAll');
				                    }
				                } else {
				                     
				                }
				            }
				        }],
		        onAfterEdit: function (rowIndex, rowData, changes) {
		            editRow_single = undefined;
		        },
		        onDblClickRow:function (rowIndex, rowData) {
		            if (editRow_single != undefined) {
		                $("#single_datagrid").datagrid('endEdit', editRow_single);
		            }
		 
		            if (editRow_single == undefined) {
		                $("#single_datagrid").datagrid('beginEdit', rowIndex);
		                editRow_single = rowIndex;
		            }
		        },
		        onClickRow:function(rowIndex,rowData){
		            if (editRow_single != undefined) {
		                $("#single_datagrid").datagrid('endEdit', editRow_single);
		 
		            }
		            
		        }
	}) ;
	$('#integral_datagrid').datagrid({
	    toolbar : [ {
	    	text: '添加', iconCls: 'icon-add', handler: function () {
	         	if (editRow_integral != undefined) {
	                    $("#integral_datagrid").datagrid('endEdit', editRow_integral);
	                }
	                if (editRow_integral == undefined) {
	                    $("#integral_datagrid").datagrid('insertRow', {
	                        index: 0,
	                        row: {}
	                    });
	 
	                    $("#integral_datagrid").datagrid('beginEdit', 0);
	                    editRow_integral = 0;
	                }
	            }
		        }, '-', {
		            text: '保存', iconCls: 'icon-save', handler: function () {
		                $("#integral_datagrid").datagrid('endEdit', editRow_integral);
		 
		                //如果调用acceptChanges(),使用getChanges()则获取不到编辑和新增的数据。
		 
		                //使用JSON序列化datarow对象，发送到后台。
		                var rows = $("#integral_datagrid").datagrid('getChanges');
		 
		                var rowstr = JSON.stringify(rows);
		                $.post('/Home/Create', rowstr, function (data) {
		                     
		                });
		            }
		        }, '-', {
		            text: '撤销', iconCls: 'icon-redo', handler: function () {
		                editRow_integral = undefined;
		                $("#integral_datagrid").datagrid('rejectChanges');
		                $("#integral_datagrid").datagrid('unselectAll');
		            }
		        }, '-', {
		            text: '删除', iconCls: 'icon-remove', handler: function () {
		                var row = $("#integral_datagrid").datagrid('getSelected');
		                var index = $("#integral_datagrid").datagrid('getRowIndex', row);
		                $("#integral_datagrid").datagrid('deleteRow',index);
		            }
		        }, '-', {
		            text: '修改', iconCls: 'icon-edit', handler: function () {
		                var row = $("#integral_datagrid").datagrid('getSelected');
		                if (row !=null) {
		                    if (editRow_integral != undefined) {
		                        $("#integral_datagrid").datagrid('endEdit', editRow_integral);
		                    }
		 
		                    if (editRow_integral == undefined) {
		                        var index = $("#integral_datagrid").datagrid('getRowIndex', row);
		                        $("#integral_datagrid").datagrid('beginEdit', index);
		                        editRow_integral = index;
		                        $("#integral_datagrid").datagrid('unselectAll');
		                    }
		                } else {
		                     
		                }
		            }
		        }],
		        onAfterEdit: function (rowIndex, rowData, changes) {
		            editRow_integral = undefined;
		        },
		        onDblClickRow:function (rowIndex, rowData) {
		            if (editRow_integral != undefined) {
		                $("#integral_datagrid").datagrid('endEdit', editRow_integral);
		            }
		 
		            if (editRow_integral == undefined) {
		                $("#integral_datagrid").datagrid('beginEdit', rowIndex);
		                editRow_integral = rowIndex;
		            }
		        },
		        onClickRow:function(rowIndex,rowData){
		            if (editRow_integral != undefined) {
		                $("#integral_datagrid").datagrid('endEdit', editRow_integral);
		 
		            }
		            
		        }
	}) ;
	$("#attach").uploadify({
		uploader: contextPath+"/jslib/file/uploadify.swf?var="+(new Date()).getTime() ,//指定文件上传的进度条的位置
		script: '${pageContext.request.contextPath}/taskManage/uploadFile',
		cancelImg: contextPath+"/jslib/file/cancel.png",//取消文件上传的图标
		fileDataName:"attach",//文件上传到服务器的名称(使用file中的name)
		method:"post",//通过post方式上传
		queueID:"attachs",//上传队列说存储的位置
		auto:true,//是否选中之后就上传,false表示选中之后不上传
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
function checkAndSubmit(){
	var submitForm = $('#radiation_createForm');
	if(submitForm.form('validate')){
		parent.$.messager.progress({
			title : '提示',
			text : '数据提交中，请稍后....'
		});
		$.ajax({
            type: "POST",
            url:'${pageContext.request.contextPath}/radiation/create',
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
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false">
		<div align="center">
			<form id="radiation_createForm" method="post"
				enctype="multipart/form-data">
				<input type="hidden" id="status" name="status">
				<table class="tasktableForm" width="95%" border="1"
					bordercolor="#B5C0C4" rules="none"
					style="border-collapse:collapse;">
					<tr>
						<td>
							<table width="100%">
								<tr>
									<th width="80px">电路名称</th>
									<td colspan="3"><input name="dlmc"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:99%" />
									</td>
									<th width="80px">电路型号</th>
									<td><input name="dlxh"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">电路类别</th>
									<td colspan="3"><input name="dllb"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:99%" />
									</td>
								</tr>
								<tr>
									<th width="80px">课题类别</th>
									<td width="80px"><input name="ktlb"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">课题号</th>
									<td><input name="kth"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">研发部门</th>
									<td><input name="yfbm"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">项目负责人</th>
									<td><input name="xmfzr"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">联系电话</th>
									<td><input name="lxdh"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
								</tr>
								<tr>
									<th width="8px">用户单位</th>
									<td><input name="yhdw"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">监制单位</th>
									<td><input name="jzdw"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">卫星型号</th>
									<td><input name="wxxh"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">单粒子负责人</th>
									<td><input name="dlzfzr"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">总剂量负责人</th>
									<td><input name="zjlfzr"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td
							style="width:98%;font-size:15px;font-weight:bold;text-align:center">电路信息
						
						</th>
					</tr>
					<tr>
						<td>
							<table width="100%">
								<tr>
									<th width="80px">工艺尺寸</th>
									<td nowrap="nowrap"><input name="gycc"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
										<span>um</span>
									</td>
									<th width="80px">生产厂家</th>
									<td><input name="sccj"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">制造工艺</th>
									<td><input name="zcgy"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">双极工艺</th>
									<td><input name="sjgy"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">详规编号</th>
									<td><input name="xgbh"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
								</tr>
								<tr>
									<th width="80px">封装形式</th>
									<td><input name="fzxs"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">工作电压</th>
									<td><input name="gzdy"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">电测系统</th>
									<td><input name="dcxt"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">存储位数</th>
									<td><input name="ccws"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">典型频率</th>
									<td><input name="dxpl"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
								</tr>
								<tr>
									<th width="80px">质量等级</th>
									<td colspan="3"><input name="zldj"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:99%" />
									</td>
									<th width="80px">触发器数</th>
									<td><input name="cfqs"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">电路门数</th>
									<td><input name="dlms"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<th width="80px">最高频率</th>
									<td><input name="zgpl"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td
							style="width:98%;font-size:15px;font-weight:bold;text-align:center">辐射指标</td>
					</tr>
					<tr>
						<td>
							<table>
								<tr>
									<th width="19" rowspan=2>单粒子</th>
									<th>SEU阈值</th>
									<td nowrap="nowrap"><input name="seufz"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" /> <span>MeV*cm2/mg</span>
									</td>
									<th>SEU在轨错误率</th>
									<td nowrap="nowrap"><input name="seuzgcwl"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" /> <span>次/天•位</span>
									</td>
									<th>闩锁阈值</th>
									<td nowrap="nowrap"><input name="ssfz"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" /> <span>MeV*cm2/mg</span>
									</td>
								</tr>
								<tr>
									<th>SEFI阈值</th>
									<td nowrap="nowrap"><input name="sefifz"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" /> <span>MeV*cm2/mg</span>
									</td>
									<th>SEFI在轨错误率</th>
									<td nowrap="nowrap"><input name="sefizgcwl"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" /> <span>次/天•位</span>
									</td>
									<th>质子单粒子</th>
									<td nowrap="nowrap"><input name="zzdlz"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" /> <span>次/天•位</span>
									</td>
								</tr>
								<tr>
									<th colspan="2">总剂量</th>
									<td nowrap="nowrap"><input name="zjl"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" /> <span>krad（Si）</span>
									</td>
									<th>位移损伤</th>
									<td nowrap="nowrap"><span>10MeV质子</span> <input
										name="wyss" class="easyui-validatebox"
										data-options="validType:'length[1,30]'" /> <span>cm2</span>
									</td>
									<th>瞬态剂量率</th>
									<td nowrap="nowrap"><input name="stjll"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" /> <span>Rad(Si)/s</span>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td
							style="width:98%;font-size:15px;font-weight:bold;text-align:center">试验进度</td>
					</tr>
					<tr>
						<td>
							<table width="100%" nowrap="nowrap">
								<tr>
									<th width="10%">类别</th>
									<th width="10%">性质</th>
									<th width="10%">登记</th>
									<th width="10%">启动</th>
									<th width="10%">方案</th>
									<th width="10%">系统</th>
									<th width="10%">摸底</th>
									<th width="10%">鉴定</th>
									<th width="10%">报告</th>
									<th width="10%">备注</th>
								</tr>
								<tr>
									<th width="10%">单粒子</th>
									<td width="10%"><input name="type"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="registerDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="startDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="planDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="systemDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="testDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="checkDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="reportDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="remark"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
								</tr>
								<tr>
									<th>总剂量</th>
									<td width="10%"><input name="type"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="registerDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="startDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="planDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="systemDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="testDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="checkDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="reportDate"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
									<td width="10%"><input name="remark"
										class="easyui-validatebox"
										data-options="validType:'length[1,30]'" style="width:98%" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td
							style="width:98%;font-size:15px;font-weight:bold;text-align:center">试验文档</td>
					</tr>
					<tr>
						<td>
							<table>
								<tr>
									<th>详细规范号</th>
									<td width="30%">
										<div id="attachs"></div> <input type="file" id="attach"
										name="attach" />
										<div id="xxwhAdd_alreadyAttachs"></div>
									</td>
									<th>其他文档</th>
									<td width="30%"></td>
								</tr>
								<tr>
									<th>单粒子方案</th>
									<td></td>
									<th>总剂量方案</th>
									<td></td>
								</tr>
								<tr>
									<th>单粒子报告</th>
									<td></td>
									<th>总剂量报告</th>
									<td></td>
								</tr>
								<tr>
									<th>单粒子数据</th>
									<td></td>
									<th>总剂量数据</th>
									<td></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td style="width:98%;font-size:15px;font-weight:bold;text-align:center">单粒子试验日志</td>
					</tr>
					<tr>
						<td>
							<table id="single_datagrid" width="100%" style="height:auto"
								singleSelect="true" idField="itemId" url="">
								<thead>
									<tr width="100%">
										<th field="itemId" width="50px" editor="{type:'text'}">NO</th>
										<th field="testDate" width="80px" editor="{type:'text'}">日期</th>
										<th field="testPlace" width="80px" editor="{type:'text'}" align="right">地点</th>
										<th field="productBatch" width="80px" editor="{type:'text'}" align="right">生产批次</th>
										<th field="version" width="80px" editor="{type:'text'}" align="right">芯片版本</th>
										<th field="amount" width="50px" editor="{type:'text'}" align="right">数量</th>
										<th field="sampleNumber" width="80px" editor="{type:'text'}" align="right">样品编号</th>
										<th field="nature" width="50px" editor="{type:'text'}">性质</th>
										<th field="producer" width="50px" editor="{type:'text'}">监制</th>
										<th field="cost" width="50px" editor="{type:'text'}">费用</th>
										<th field="resultInfo" width="180px" editor="{type:'text'}">结果</th>
									</tr>
								</thead>
							</table>
						</td>
					</tr>
					<tr>
						<td style="width:98%;font-size:15px;font-weight:bold;text-align:center">总剂量试验日志</td>
					</tr>
					<tr>
						<td>
							<table id="integral_datagrid" width="100%" style="height:auto"
								singleSelect="true" idField="itemId" url="">
								<thead>
									<tr width="100%">
										<th field="itemId" width="50px" editor="{type:'text'}">NO</th>
										<th field="testDate" width="80px" editor="{type:'text'}">日期</th>
										<th field="testPlace" width="80px" editor="{type:'text'}" align="right">地点</th>
										<th field="productBatch" width="80px" editor="{type:'text'}" align="right">生产批次</th>
										<th field="version" width="80px" editor="{type:'text'}" align="right">晶圆批次</th>
										<th field="amount" width="50px" editor="{type:'text'}" align="right">数量</th>
										<th field="sampleNumber" width="80px" editor="{type:'text'}" align="right">样品编号</th>
										<th field="nature" width="50px" editor="{type:'text'}">性质</th>
										<th field="producer" width="50px" editor="{type:'text'}">监制</th>
										<th field="cost" width="50px" editor="{type:'text'}">费用</th>
										<th field="resultInfo" width="180px" editor="{type:'text'}">结果</th>
									</tr>
								</thead>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%">
								<tr>
									<td>
										<th width="80px">备注</th>
										<td ><input name="bz"
											class="easyui-validatebox"
											data-options="validType:'length[1,30]'" style="width:99%" />
										</td>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
			         <td style="text-align:center">
			         	<a id="fsrwd_submit" class="l-btn" href="javascript:void(0);" onclick="javascript:checkAndSubmit()">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">保存</span>
			         		</span>
			         	</a>
			         	<a id="zxcl_close" class="l-btn" href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-cancel l-btn-icon-left" style="padding-left: 20px; ">清空</span>
			         		</span>
			         	</a>
			         </td>
		     		</tr>
				</table>
			</form>
		</div>
	</div>
</div>
