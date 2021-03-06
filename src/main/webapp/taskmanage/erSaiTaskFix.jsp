<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
	});
	
	function fixSubmit(){
		var submitForm = $('#erSai_taskOrder_fixForm');
		parent.$.messager.progress({
			title : '提示',
			text : '数据提交中，请稍后....'
		});
   		$.ajax({
               type: "POST",
               url:'${pageContext.request.contextPath}/erSaiTaskManage/fixComplete',
               data:submitForm.serialize(),
               dataType : 'json',
			success : function(result) {
				parent.$.messager.progress('close');
				if (result.success) {
					var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
					$('#layout_center_tabs').tabs('close', index);
					$('#erSai_taskorder_list_datagrid').datagrid('reload');
					$('#erSai_taskorder_suslist_datagrid').datagrid('reload');	
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
	function setAttachRow(value){
		var yansNumSelect = document.getElementById("yans_erSaiTask");
		yansNumSelect.style.display = "none";
		if(value==1){
			yansNumSelect.style.display = (document.all ? "block" : "table-row");
		}
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<div data-options="region:'center',border:false">
<form id="erSai_taskOrder_fixForm"method="post">
	<div  style="width:780px;margin-left: auto;margin-right: auto;">
			<input type="hidden" name="orderId" value="${taskOrder.orderId}"/>
			<table id="PrintA" class="tasktableForm" width=100%>
				<tr>
					<th width=88>任务类型</th>
					<td width=194 colspan=2>
						${taskOrder.taskType}
					</td>
					<th width=101 colspan=2>通知单号</th>
					<td width=264 colspan=3>
						${taskOrder.reportNo}
					</td>
				</tr>
				<tr>
					<th width=88>所内型号</th>
					<td width=194 colspan=7>${taskOrder.internalModel}</td>
				</tr>
				<tr>
					<th width=88>申请部门</th>
					<td width=194 colspan=2>${taskOrder.applyDept}</td>
					<th width=101 colspan=2>申请人</th>
					<td width=106>${taskOrder.applyMember}</td>
					<th width=42>电话</th>
					<td width=115>${taskOrder.applyMemberPhone}</td>
				</tr>
				<tr>
					<th width=88>课题号</th>
					<td width=194 colspan=2>${taskOrder.topicName}</td>
					<th width=101 colspan=2>项目负责人</th>
					<td width=106>${taskOrder.projectManager}</td>
					<th width=42>电话</th>
					<td width=115>${taskOrder.projectManagerPhone}</td>
				</tr>
				<tr>
					<th width=88>请求协助部门</th>
					<td width=194 colspan=2>${taskOrder.helpDeptName}</td>
					<th width=101 colspan=2>希望完成时间</th>
					<td width=264 colspan=3><fmt:formatDate value="${taskOrder.wantedEndDate}" pattern="yyyy-MM-dd" /></td>
				</tr>
				<tr>
					<th width="150px" >是否验收：</th>
					<td width=174px style="text-align:left" colspan=2 >
						<c:choose>
						   	<c:when test="${taskOrder.checkFlag==1}">
						   		是
						   	</c:when>
						   	<c:otherwise>
						   		否
						   	</c:otherwise>
						</c:choose>
					</td>
					<th width=121 colspan=2>电路是否出库：</th>
					<td width=264 colspan=3>
						<c:choose>
						   	<c:when test="${taskOrder.outputFlag==1}">
						   		是
						   	</c:when>
						   	<c:otherwise>
						   		否
						   	</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th width="150px" >是否切金：</th>
					<td width=174px style="text-align:left" colspan=2 >
						<c:choose>
						   	<c:when test="${taskOrder.goldcutFlag==1}">
						   		是
						   	</c:when>
						   	<c:otherwise>
						   		否
						   	</c:otherwise>
						</c:choose>
					</td>
					<th width=121 colspan=2></th>
					<td width=264 colspan=3></td>
				</tr>
				<c:choose>
				   	<c:when test="${taskOrder.goldcutFlag==1}">
						<tr id="ersai_Fix_goldCut">
							<th style="width: 150px">切金编号</th>
							<td width=174 colspan=2>${taskOrder.goldcutNo}</td>
							<th width=121 colspan=2></th>
							<td width=264 colspan=3></td>
						</tr>
					</c:when>
				</c:choose>
				<tr>
					<th width=88 >是否有附件：</th>
					<td width=194px colspan=2>
						<c:choose>
						   	<c:when test="${taskOrder.attachmentFlag==1}">
						   		是
						   	</c:when>
						   	<c:otherwise>
						   		否
						   	</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<c:choose>
				   	<c:when test="${taskOrder.attachmentFlag==1}">
				   		<tr id="attachSubmit">
							<th width=88 >已上传附件：</th>
							<td width=558 colspan=7>
								<div id="xxwhAdd_alreadyAttachs">
									<c:forEach items="${taskOrder.attachment}" var="item">
										<div id="${item.id}" newname="${item.newName}" >
										<span>${item.oldName}</span>
										<a href="${pageContext.request.contextPath}/erSaiTaskManage/attached?filePath=${item.newName}">查看附件</a>
										<input type='hidden' name='attachids' value="${item.id}"/></div>
									</c:forEach>
								</div>
							</td>
						</tr>
				   	</c:when>
				</c:choose>
				<tr>
					<th width=88 >紧急程度：</th>
					<td width=558 colspan=7>
						<c:choose>
						   	<c:when test="${taskOrder.urgency==1}">
						   		紧急
						   	</c:when>
						   	<c:when test="${taskOrder.urgency==2}">
						   		超紧急
						   	</c:when>
						   	<c:otherwise>
						   		一般
						   	</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th width=88 >申请原因及说明：</th>
					<td width=558 colspan=7>
						${taskOrder.applyReason}
					</td>
				</tr>
				<tr>
					<th width=88 >具体要求：</th>
					<td width=558 colspan=7>
						${taskOrder.detailRequire}
					</td>
				</tr>
				<tr>
					<th width=88 >备注：</th>
					<td width=558 colspan=7>
						${taskOrder.remarks}
					</td>
				</tr>
				<tr>
					<th width=88 >生产部门负责人意见：</th>
					<td width=558 colspan=7>
						${taskOrder.productManagesuggest}
					</td>
				</tr>
			</table>
	</div>
	<c:choose>
		<c:when test="${taskOrder.status==43 || taskOrder.status==24 || taskOrder.status==51}">
			<%-- <div  style="width:780px;margin-left: auto;margin-right: auto;margin-top: 20px;margin-bottom: 20px;">
			<div id="priceItem" title="价格信息" class="easyui-panel" style="width:780px;margin-left: auto;margin-right: auto;">  
				<table width=100%>
					<thead>
						<tr>
							<th field="itemName" width="100">项目内容</th>
							<th field="basePrice" width="90" align="right">起步收费</th>
							<th field="price" width="90" align="right">收费标准（元）</th>
							<th field="chargeUnit" width="80" align="right">计价单位</th>
							<th field="amount" width="80" align="right">数量</th>
							<th field="unitcost" width="80" align="right">总计（元）</th>
							<th field="remarks" width="150">备注</th>
						</tr>
						<c:forEach var="item" items="${taskPrice}">
			       			<tr>
								<td align="center">${item.itemName}</td>
								<td align="right">${item.basePrice}</td>
								<td align="right">${item.price}</td>
								<td align="right">${item.chargeUnit}</td>
								<td align="right">${item.amount}</td>
								<td align="right">${item.unitcost}</td>
								<td >${item.remarks}</td>
							</tr>
						</c:forEach>
						<tr>
							<td ></td>
							<td align="right"></td>
							<td align="right"></td>
							<td align="right"></td>
							<td align="right" style=" font-weight:bold">合计（元）</td>
							<td align="right">${taskOrder.sumPrice}</td>
							<td ></td>
						</tr>
					</thead>
				</table>
			</div>
			</div>
			<div  style="width:780px;margin-left: auto;margin-right: auto;margin-top: 20px;margin-bottom: 20px;">
			<div id="scheduleDetail" title="工作进度信息" class="easyui-panel" style="width:780px;margin-left: auto;margin-right: auto;">  
				<table cellSpacing=0 cellPadding=5>
			    	<tr>
			    		<td width="10%" align="right">
	                       	开始时间
	                   	</td>
	                   	<td width="20%" align="left">
							<fmt:formatDate value="${taskSchedule.pakstartDate}" pattern="yyyy-MM-dd" />
	                   	</td>
	                    <td  width="10%" align="right" >
	                       	结束时间
	                   	</td>
	                   	<td width="20%" align="left">
							<fmt:formatDate value="${taskSchedule.pakendDate}" pattern="yyyy-MM-dd" />
	                   	</td>
	                   	<td  width="10%" align="right" >
	                       	工期
	                   	</td>
	                   	<td width="20%" id="day1" align="left">
	                   		${taskSchedule.pakTime}天
	                   	</td>
				    </tr>
			    </table>
			</div>
			</div> --%>
				<c:choose>
					<c:when test="${taskOrder.status==24}">
					<div  style="width:780px;margin-left: auto;margin-right: auto;margin-top: 20px;margin-bottom: 20px;">
					<div id="yans_panding" title="是否需要验收" class="easyui-panel" style="width:780px;margin-left: auto;margin-right: auto;">  
						<table cellSpacing=0 cellPadding=5>
							<tr>
						    	<th width="150px" >是否需要验收：</th>
								<td width=174px style="text-align:left" colspan=2 >
									<span style="white-space:nowrap;">
										<input type="radio" style="width:30px" name="yansFlag" onclick="setAttachRow(this.value)" value="1" />是
										<input type="radio" style="width:30px" name="yansFlag" onclick="setAttachRow(this.value)" checked value="0" />否
									</span>
								</td>
							</tr>
							<tr id="yans_erSaiTask" style="display:none;">
						    	<th width="150px" >验收数量：</th>
								<td width=174px style="text-align:left" colspan=2 >
									<input  name="yansNum" class="easyui-validatebox"
									data-options="validType:'length[1,100]'"
									style="width:330px" value="0"/>
								</td>
							</tr>
					    </table>
					   </div>
					  </div>
					  </c:when>
				  </c:choose>
		</c:when>
	</c:choose>
	</form>
	<div  style="width:780px;margin-left: auto;margin-right: auto;margin-bottom: 20px;" ">
		<table>
		<tr>
		<td width=646 style="text-align:center">
		<a id="erSai_fix_complete" class="l-btn" href="javascript:void(0);" target="_self" onclick="javascript:fixSubmit()">
       		<span class="l-btn-left">
       			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">确认完成</span>
       		</span>
       	</a>
       	<a id="erSai_fix_close" class="l-btn" href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
       		<span class="l-btn-left">
       			<span class="l-btn-text icon-cancel l-btn-icon-left" style="padding-left: 20px; ">关闭</span>
       		</span>
       	</a>
       	</td></tr>
       	</table>
   	</div>
</div>
</div>