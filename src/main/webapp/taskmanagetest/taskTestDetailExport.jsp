<%@ page contentType="application/msword;charset=GBK" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%     
response.setHeader("Content-disposition","attachment;filename=taskOrder.doc");   
%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
	});
</script>
			<table id="PrintA" border="1" bordercolor="#CCC" style="border-collapse:collapse;">
				<tr>
					<th colspan=8 style="text-align:center">任务通知单</th>
				</tr>
				<tr>
					<th width=88>项目名称</th>
					<td whidth=194 colspan=2>
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
					<th width=88>具体要求：</th>
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
