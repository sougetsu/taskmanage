<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
	});
	function printme()
	{
		var el = document.getElementById("print_area");
        var iframe = document.createElement('IFRAME');
        var doc = null;
        iframe.setAttribute('style', 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;');
        document.body.appendChild(iframe);
        doc = iframe.contentWindow.document;
        doc.write('<div>' + el.innerHTML + '</div>');
        doc.close();
        iframe.contentWindow.focus();
        iframe.contentWindow.print();
        if (navigator.userAgent.indexOf("MSIE") > 0)
        {
             document.body.removeChild(iframe);
        }
        return false;
	}
	function MakeWord(id)
	 {
		window.open("${pageContext.request.contextPath}/taskManage/exportWord?id="+id);
	 }
	
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<div data-options="region:'center',border:false" style="position:relative;left:0;top:0;"">
	<div style="width:780px;margin-left: auto;margin-right: auto;>
		<form id="taskOrder_detailForm"method="post">
			<table id="PrintA" class="tasktableForm" width=100%>
				<tr>
					<th width=88>项目名称</th>
					<td width=194 colspan=2>
						${taskOrder.projectName}
					</td>
					<th width=101 colspan=2>任务单号</th>
					<td width=264 colspan=3>
						${taskOrder.lsh}
					</td>
				</tr>
				<tr>
					<th width=88>所内型号</th>
					<td width=194 colspan=2>${taskOrder.internalModel}</td>
					<th width=101 colspan=2>请求协作部门</th>
					<td width=264 colspan=3>
						${taskOrder.helpDeptName}
					</td>
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
					<td width=194 colspan=2>${taskOrder.topicNo}</td>
					<th width=101 colspan=2>项目负责人</th>
					<td width=106>${taskOrder.projectManager}</td>
					<th width=42>电话</th>
					<td width=115>${taskOrder.projectManagerPhone}</td>
				</tr>
				<tr>
					<th width=88>交付物</th>
					<td width=194 colspan=2>${taskOrder.deliverable}</td>
					<th width=101 colspan=2>希望完成时间</th>
					<td width=264 colspan=3><fmt:formatDate value="${taskOrder.wantedEndDate}" pattern="yyyy-MM-dd" /></td>
				</tr>
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
					<td width=101 colspan=2><b>监制：</b>
						<c:choose>
						   	<c:when test="${taskOrder.superviseFlag==1}">
						   		是
						   	</c:when>
						   	<c:otherwise>
						   		否
						   	</c:otherwise>
						</c:choose>
					</td>
					<th >监制单位：</th>
					<td colspan=2>${taskOrder.superviseUnit}</td>
				</tr>
				<tr>
					<th width=88 >受控详规：</th>
					<td width=194px colspan=2>
						<c:choose>
						   	<c:when test="${taskOrder.controlledPlanFlag==1}">
						   		是
						   	</c:when>
						   	<c:otherwise>
						   		否
						   	</c:otherwise>
						</c:choose>
					</td>
					<td width=101 colspan=2><b>会签稿：</b>
						<c:choose>
						   	<c:when test="${taskOrder.countersignFlag==1}">
						   		是
						   	</c:when>
						   	<c:otherwise>
						   		否
						   	</c:otherwise>
						</c:choose>
					</td>
					<th >详规号：</th>
					<td colspan=2>${taskOrder.detailPlanNo}</td>
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
										<a href="${pageContext.request.contextPath}/taskManage/attached?filePath=${item.newName}">查看附件</a>
										<input type='hidden' name='attachids' value="${item.id}"/></div>
									</c:forEach>
								</div>
							</td>
						</tr>
				   	</c:when>
				</c:choose>
				<tr>
					<th width=88 >业务申请内容:</th>
					<td width=558 colspan=7>
						${taskOrder.applyContentNames}
					</td>
				</tr>
				<c:choose>
				   	<c:when test="${taskOrder.reductionFlag==1}">
						<tr>
							<th width=88></th>
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
								   	 	${taskOrder.reductionNo}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >片号</span></b></p>
								    </td>
								    <td colspan=6>
								    	${taskOrder.reductionTabletsNo}
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >减薄厚度</span></b></p>
								    </td>
								    <td colspan=6>
								    	${taskOrder.reductionThickness}
								    </td>
								    </tr>
								 </table>
							</td>
						</tr>
					</c:when>
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.dicingFlag==1}">
						<tr>
							<th width=88></th>
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
								    	${taskOrder.dicingNo}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >片号</span></b></p>
								    </td>
								    <td colspan=6>
								    	${taskOrder.dicingTabletsNo}
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >需管芯数量</span></b></p>
								    </td>
								    <td colspan=6>
								    	${taskOrder.dicingTubeNum}
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >划片方案</span></b></p>
								    </td>
								    <td colspan=6>
								    	${taskOrder.dicingPlan}
								    </td>
								    </tr>
								 </table>
							</td>
						</tr>
					</c:when>
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.packageFlag==1}">
				   		<tr>
							<th width=88></th>
							<td width=558 colspan=7>
								<table border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
								   <tr >
								    <td width=31 rowspan=6 >
								    <p ><b><span >封装</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >封装状态</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	${taskOrder.packageStatusNames}
								    </td>
								   </tr>
								   <tr>
								    <td width=83 >
								    <p ><b><span
								    >质量等级</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.qualityLevel}
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片批次</span></b></p>
								    </td>
								    <td width=194 >
								  		${taskOrder.discBatch}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >数 量</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.packageNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >芯片标识</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.chipLabel}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >管壳型号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.shellType}
								    </td>
								    <td width=87 >
								    <p ><b><span
								    >压焊图号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.bondNum}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >封装形式</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.packageShape}
								    </td>
								    <td width=87 >
								    <p ><b><span >打标要求</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.markDemand}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >使用圆片号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.discNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片是否中测/修调</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.waferFlag==1}">
										   		是
										   	</c:when>
										   	<c:otherwise>
										   		否
										   	</c:otherwise>
										</c:choose>
								    </td>
								   </tr>
								</table>
							</td>
						</tr>
				   	</c:when>
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.mixPackageFlag==1}">
				   		<tr>
							<th width=88></th>
							<td width=558 colspan=7>
								<table border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
								   <tr >
								    <td width=31 rowspan=7 >
								    <p ><b><span >混合封装</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >封装状态</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	${taskOrder.mpackageStatusNames}
								    </td>
								   </tr>
								   <tr>
								    <td width=83 >
								    <p ><b><span
								    >质量等级</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.mqualityLevel}
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片批次</span></b></p>
								    </td>
								    <td width=194 >
								  		${taskOrder.mdiscBatch}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >数 量</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mpackageNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >芯片标识</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mchipLabel}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >管壳型号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mshellType}
								    </td>
								    <td width=87 >
								    <p ><b><span
								    >压焊图号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mbondNum}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >封装形式</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mpackageShape}
								    </td>
								    <td width=87 >
								    <p ><b><span >打标要求</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.mmarkDemand}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >使用圆片号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mdiscNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片是否中测/修调</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.mwaferFlag==1}">
										   		是
										   	</c:when>
										   	<c:otherwise>
										   		否
										   	</c:otherwise>
										</c:choose>
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >需求芯片数</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mchipNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >库存是否满足</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.mstockFlag==1}">
										   		是
										   	</c:when>
										   	<c:otherwise>
										   		否
										   	</c:otherwise>
										</c:choose>
								    </td>
								   </tr>
								</table>
							</td>
						</tr>
				   	</c:when>
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.mcpackageFlag==1}">
				   		<tr>
							<th width=88></th>
							<td width=558 colspan=7>
								<table border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
								   <tr >
								    <td width=31 rowspan=7 >
								    <p ><b><span >多芯片封装</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >封装状态</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	${taskOrder.mcpackageStatusNames}
								    </td>
								   </tr>
								   <tr>
								    <td width=83 >
								    <p ><b><span
								    >质量等级</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.mcqualityLevel}
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片批次</span></b></p>
								    </td>
								    <td width=194 >
								  		${taskOrder.mcdiscBatch}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >数 量</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcpackageNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >芯片标识</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcchipLabel}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >管壳型号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcshellType}
								    </td>
								    <td width=87 >
								    <p ><b><span
								    >压焊图号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcbondNum}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >封装形式</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcpackageShape}
								    </td>
								    <td width=87 >
								    <p ><b><span >打标要求</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.mcmarkDemand}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >使用圆片号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcdiscNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片是否中测/修调</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.mcwaferFlag==1}">
										   		是
										   	</c:when>
										   	<c:otherwise>
										   		否
										   	</c:otherwise>
										</c:choose>
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >需求芯片数</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcchipNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >库存是否满足</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.mcstockFlag==1}">
										   		是
										   	</c:when>
										   	<c:otherwise>
										   		否
										   	</c:otherwise>
										</c:choose>
								    </td>
								   </tr>
								</table>
							</td>
						</tr>
				   	</c:when>
				</c:choose>
				<tr>
					<th width="150px" >任务类型：</th>
					<td style="text-align:left" colspan=7>
						${taskOrder.orderTypeName}
					</td>
				</tr>
				<tr>
					<th width=88 >鉴定方式：</th>
					<td width=558 colspan=7>
						${taskOrder.checkTypeName}
					</td>
				</tr>
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
				<tr>
			         <td width=646 colspan=8 style="text-align:center">
			         	<a id="detail_print" class="l-btn" href="javascript:void(0);" target="_self" onclick="printme();return false;">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-print l-btn-icon-left" style="padding-left: 20px; ">打印</span>
			         		</span>
			         	</a>
			         	<a id="detail_export" class="l-btn" href="javascript:void(0);" target="_self" onclick="MakeWord(${taskOrder.orderId});return false;">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">导出</span>
			         		</span>
			         	</a>
			         	<a id="detail_close" class="l-btn" href="javascript:void(0);" onclick="closeCurrentTab();return false;">
			         		<span class="l-btn-left">
			         			<span class="l-btn-text icon-cancel l-btn-icon-left" style="padding-left: 20px; ">关闭</span>
			         		</span>
			         	</a>
			         </td>
		     	</tr>
			</table>
		</form>
	</div>
	
	<%-- <c:choose>
		<c:when test="${taskOrder.status==43 || taskOrder.status==13 || taskOrder.status==51}">
			<div  style="width:780px;margin-left: auto;margin-right: auto;margin-top: 20px;margin-bottom: 20px;">
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
			    		<th  width="10%" align="right">封装:</th>
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
				    <tr>
			    		<th  width="10%" align="right">监制:</th>
			    		<td width="10%" align="right">
	                       	开始时间
	                   	</td>
	                   	<td width="20%" align="left">
							<fmt:formatDate value="${taskSchedule.svstartDate}" pattern="yyyy-MM-dd" />
	                   	</td>
	                    <td  width="10%" align="right" >
	                       	结束时间
	                   	</td>
	                   	<td width="20%" align="left">
							<fmt:formatDate value="${taskSchedule.svendDate}" pattern="yyyy-MM-dd" />
	                   	</td>
	                   	<td  width="10%" align="right" >
	                       	工期
	                   	</td>
	                   	<td width="20%" id="day2" align="left">
	                   	${taskSchedule.svTime}天
	                   	</td>
				    </tr>
				    <tr>
			    		<th  width="10%" align="right">成测:</th>
			    		<td width="10%" align="right">
	                       	开始时间
	                   	</td>
	                   	<td width="20%" align="left">
							<fmt:formatDate value="${taskSchedule.teststartDate}" pattern="yyyy-MM-dd" />
	                   	</td>
	                    <td  width="10%" align="right" >
	                       	结束时间
	                   	</td>
	                   	<td width="20%" align="left">
							<fmt:formatDate value="${taskSchedule.testendDate}" pattern="yyyy-MM-dd" />
	                   	</td>
	                   	<td  width="10%" align="right" >
	                       	工期
	                   	</td>
	                   	<td width="20%" id="day3"align="left">
	                   	${taskSchedule.testTime}天
	                   	</td>
				    </tr>
				    <tr>
			    		<th  width="10%" align="right">筛选:</th>
			    		<td width="10%" align="right">
	                       	开始时间
	                   	</td>
	                   	<td width="20%" align="left">
							<fmt:formatDate value="${taskSchedule.sxstartDate}" pattern="yyyy-MM-dd" />
	                   	</td>
	                    <td  width="10%" align="right" >
	                       	结束时间
	                   	</td>
	                   	<td width="20%" align="left">
							<fmt:formatDate value="${taskSchedule.sxendDate}" pattern="yyyy-MM-dd" />
	                   	</td>
	                   	<td  width="10%" align="right" >
	                       	工期
	                   	</td>
	                   	<td width="20%" id="day4" align="left">
	                   	${taskSchedule.sxTime}天
	                   	</td>
				    </tr>
				    <tr>
			    		<th  width="10%" align="right">鉴定:</th>
			    		<td width="10%" align="right">
	                       	开始时间
	                   	</td>
	                   	<td width="20%" align="left">
							<fmt:formatDate value="${taskSchedule.jdstartDate}" pattern="yyyy-MM-dd" />
	                   	</td>
	                    <td  width="10%" align="right" >
	                       	结束时间
	                   	</td>
	                   	<td width="20%" align="left">
							<fmt:formatDate value="${taskSchedule.jdendDate}" pattern="yyyy-MM-dd" />
	                   	</td>
	                   	<td  width="10%" align="right" >
	                       	工期
	                   	</td>
	                   	<td width="20%" id="day5" align="left">
	                   		${taskSchedule.jdTime}天
	                   	</td>
				    </tr>
			    </table>
			</div>
			</div>
		</c:when>
	</c:choose> --%>
	<div id="print_area" align="center" style="display:none;">
			<table class="tasktableForm" border="1" bordercolor="#CCC" style="border-collapse:collapse;">
				<tr>
					<th width=88>项目名称</th>
					<td width=194 colspan=2>
						${taskOrder.projectName}
					</td>
					<th width=101 colspan=2></th>
					<td width=264 colspan=3>
					</td>
				</tr>
				<tr>
					<th width=88>所内型号</th>
					<td width=194 colspan=2>${taskOrder.internalModel}</td>
					<th width=101 colspan=2>请求协作部门</th>
					<td width=264 colspan=3>
						${taskOrder.helpDeptName}
					</td>
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
					<td width=194 colspan=2></td>
					<th width=101 colspan=2>项目负责人</th>
					<td width=106>${taskOrder.projectManager}</td>
					<th width=42>电话</th>
					<td width=115>${taskOrder.projectManagerPhone}</td>
				</tr>
				<tr>
					<th width=88>交付物</th>
					<td width=194 colspan=2>${taskOrder.deliverable}</td>
					<th width=101 colspan=2>希望完成时间</th>
					<td width=264 colspan=3><fmt:formatDate value="${taskOrder.wantedEndDate}" pattern="yyyy-MM-dd" /></td>
				</tr>
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
					<td width=101 colspan=2><b>监制：</b>
						<c:choose>
						   	<c:when test="${taskOrder.superviseFlag==1}">
						   		是
						   	</c:when>
						   	<c:otherwise>
						   		否
						   	</c:otherwise>
						</c:choose>
					</td>
					<th >监制单位：</th>
					<td colspan=2>${taskOrder.superviseUnit}</td>
				</tr>
				<tr>
					<th width=88 >受控详规：</th>
					<td width=194px colspan=2>
						<c:choose>
						   	<c:when test="${taskOrder.controlledPlanFlag==1}">
						   		是
						   	</c:when>
						   	<c:otherwise>
						   		否
						   	</c:otherwise>
						</c:choose>
					</td>
					<td width=101 colspan=2><b>会签稿：</b>
						<c:choose>
						   	<c:when test="${taskOrder.countersignFlag==1}">
						   		是
						   	</c:when>
						   	<c:otherwise>
						   		否
						   	</c:otherwise>
						</c:choose>
					</td>
					<th >详规号：</th>
					<td colspan=2>${taskOrder.detailPlanNo}</td>
				</tr>
				<tr>
					<th width=88 >业务申请内容:</th>
					<td width=558 colspan=7>
						${taskOrder.applyContentNames}
					</td>
				</tr>
				<c:choose>
				   	<c:when test="${taskOrder.reductionFlag==1}">
						<tr>
							<th width=88></th>
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
								   	 	${taskOrder.reductionNo}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >片号</span></b></p>
								    </td>
								    <td colspan=6>
								    	${taskOrder.reductionTabletsNo}
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >减薄厚度</span></b></p>
								    </td>
								    <td colspan=6>
								    	${taskOrder.reductionThickness}
								    </td>
								    </tr>
								 </table>
							</td>
						</tr>
					</c:when>
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.dicingFlag==1}">
						<tr>
							<th width=88></th>
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
								    	${taskOrder.dicingNo}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >片号</span></b></p>
								    </td>
								    <td colspan=6>
								    	${taskOrder.dicingTabletsNo}
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >需管芯数量</span></b></p>
								    </td>
								    <td colspan=6>
								    	${taskOrder.dicingTubeNum}
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >划片方案</span></b></p>
								    </td>
								    <td colspan=6>
								    	${taskOrder.dicingPlan}
								    </td>
								    </tr>
								 </table>
							</td>
						</tr>
					</c:when>
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.packageFlag==1}">
				   		<tr>
							<th width=88></th>
							<td width=558 colspan=7>
								<table border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
								   <tr >
								    <td width=31 rowspan=6 >
								    <p ><b><span >封装</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >封装状态</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	${taskOrder.packageStatusNames}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >质量等级</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.qualityLevel}
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片批次</span></b></p>
								    </td>
								    <td width=224 >
								  		${taskOrder.discBatch}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >数 量</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.packageNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >芯片标识</span></b></p>
								    </td>
								    <td width=224 >
								    	${taskOrder.chipLabel}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >管壳型号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.shellType}
								    </td>
								    <td width=87 >
								    <p ><b><span
								    >压焊图号</span></b></p>
								    </td>
								    <td width=224 >
								    	${taskOrder.bondNum}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >封装形式</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.packageShape}
								    </td>
								    <td width=87 >
								    <p ><b><span >打标要求</span></b></p>
								    </td>
								    <td width=224 >
								   		${taskOrder.markDemand}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >使用圆片号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.discNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片是否中测/修调</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.waferFlag==1}">
										   		是
										   	</c:when>
										   	<c:otherwise>
										   		否
										   	</c:otherwise>
										</c:choose>
								    </td>
								   </tr>
								</table>
							</td>
						</tr>
				   	</c:when>
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.mixPackageFlag==1}">
				   		<tr>
							<th width=88></th>
							<td width=558 colspan=7>
								<table border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
								   <tr >
								    <td width=31 rowspan=6 >
								    <p ><b><span >封装</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >封装状态</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	${taskOrder.mpackageStatusNames}
								    </td>
								   </tr>
								   <tr>
								    <td width=83 >
								    <p ><b><span
								    >质量等级</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.mqualityLevel}
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片批次</span></b></p>
								    </td>
								    <td width=194 >
								  		${taskOrder.mdiscBatch}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >数 量</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mpackageNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >芯片标识</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mchipLabel}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >管壳型号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mshellType}
								    </td>
								    <td width=87 >
								    <p ><b><span
								    >压焊图号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mbondNum}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >封装形式</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mpackageShape}
								    </td>
								    <td width=87 >
								    <p ><b><span >打标要求</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.mmarkDemand}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >使用圆片号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mdiscNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片是否中测/修调</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.mwaferFlag==1}">
										   		是
										   	</c:when>
										   	<c:otherwise>
										   		否
										   	</c:otherwise>
										</c:choose>
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >需求芯片数</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mchipNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >库存是否满足</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.mstockFlag==1}">
										   		是
										   	</c:when>
										   	<c:otherwise>
										   		否
										   	</c:otherwise>
										</c:choose>
								    </td>
								   </tr>
								</table>
							</td>
						</tr>
				   	</c:when>
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.mcpackageFlag==1}">
				   		<tr>
							<th width=88></th>
							<td width=558 colspan=7>
								<table border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
								   <tr >
								    <td width=31 rowspan=6 >
								    <p ><b><span >封装</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >封装状态</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	${taskOrder.mcpackageStatusNames}
								    </td>
								   </tr>
								   <tr>
								    <td width=83 >
								    <p ><b><span
								    >质量等级</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.mcqualityLevel}
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片批次</span></b></p>
								    </td>
								    <td width=194 >
								  		${taskOrder.mcdiscBatch}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >数 量</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcpackageNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >芯片标识</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcchipLabel}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >管壳型号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcshellType}
								    </td>
								    <td width=87 >
								    <p ><b><span
								    >压焊图号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcbondNum}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >封装形式</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcpackageShape}
								    </td>
								    <td width=87 >
								    <p ><b><span >打标要求</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.mcmarkDemand}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >使用圆片号</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcdiscNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >圆片是否中测/修调</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.mcwaferFlag==1}">
										   		是
										   	</c:when>
										   	<c:otherwise>
										   		否
										   	</c:otherwise>
										</c:choose>
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >需求芯片数</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcchipNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >库存是否满足</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.mcstockFlag==1}">
										   		是
										   	</c:when>
										   	<c:otherwise>
										   		否
										   	</c:otherwise>
										</c:choose>
								    </td>
								   </tr>
								</table>
							</td>
						</tr>
				   	</c:when>
				</c:choose>
				<tr>
					<th width="150px" >任务类型：</th>
					<td style="text-align:left" colspan=7>
						${taskOrder.orderTypeName}
					</td>
				</tr>
				<tr>
					<th width=88 >鉴定方式：</th>
					<td width=558 colspan=7>
						${taskOrder.checkTypeName}
					</td>
				</tr>
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
</div>
</div>