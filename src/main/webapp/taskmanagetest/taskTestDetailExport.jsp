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
					<th colspan=8 style="text-align:center">����֪ͨ��</th>
				</tr>
				<tr>
					<th width=88>��Ŀ����</th>
					<td whidth=194 colspan=2>
						${taskOrder.projectName}
					</td>
					<th width=101 colspan=2></th>
					<td width=264 colspan=3>
					</td>
				</tr>
				<tr>
					<th width=88>�����ͺ�</th>
					<td width=194 colspan=2>${taskOrder.internalModel}</td>
					<th width=101 colspan=2>����Э������</th>
					<td width=264 colspan=3>
						${taskOrder.helpDeptName}
					</td>
				</tr>
				<tr>
					<th width=88>���벿��</th>
					<td width=194 colspan=2>${taskOrder.applyDept}</td>
					<th width=101 colspan=2>������</th>
					<td width=106>${taskOrder.applyMember}</td>
					<th width=42>�绰</th>
					<td width=115>${taskOrder.applyMemberPhone}</td>
				</tr>
				<tr>
					<th width=88>�����</th>
					<td width=194 colspan=2>${taskOrder.topicNo}</td>
					<th width=101 colspan=2>��Ŀ������</th>
					<td width=106>${taskOrder.projectManager}</td>
					<th width=42>�绰</th>
					<td width=115>${taskOrder.projectManagerPhone}</td>
				</tr>
				<tr>
					<th width=88>������</th>
					<td width=194 colspan=2>${taskOrder.deliverable}</td>
					<th width=101 colspan=2>ϣ�����ʱ��</th>
					<td width=264 colspan=3><fmt:formatDate value="${taskOrder.wantedEndDate}" pattern="yyyy-MM-dd" /></td>
				</tr>
				<tr>
					<th width=88 >�Ƿ��и�����</th>
					<td width=194px colspan=2>
						<c:choose>
						   	<c:when test="${taskOrder.attachmentFlag==1}">
						   		��
						   	</c:when>
						   	<c:otherwise>
						   		��
						   	</c:otherwise>
						</c:choose>
					</td>
					<td width=101 colspan=2><b>���ƣ�</b>
						<c:choose>
						   	<c:when test="${taskOrder.superviseFlag==1}">
						   		��
						   	</c:when>
						   	<c:otherwise>
						   		��
						   	</c:otherwise>
						</c:choose>
					</td>
					<th >���Ƶ�λ��</th>
					<td colspan=2>${taskOrder.superviseUnit}</td>
				</tr>
				<tr>
					<th width=88 >�ܿ���棺</th>
					<td width=194px colspan=2>
						<c:choose>
						   	<c:when test="${taskOrder.controlledPlanFlag==1}">
						   		��
						   	</c:when>
						   	<c:otherwise>
						   		��
						   	</c:otherwise>
						</c:choose>
					</td>
					<td width=101 colspan=2><b>��ǩ�壺</b>
						<c:choose>
						   	<c:when test="${taskOrder.countersignFlag==1}">
						   		��
						   	</c:when>
						   	<c:otherwise>
						   		��
						   	</c:otherwise>
						</c:choose>
					</td>
					<th >���ţ�</th>
					<td colspan=2>${taskOrder.detailPlanNo}</td>
				</tr>
				<tr>
					<th width=88 >ҵ����������:</th>
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
								    <p ><b><span >����</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >ԲƬ����</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								   	 	${taskOrder.reductionNo}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >Ƭ��</span></b></p>
								    </td>
								    <td colspan=6>
								    	${taskOrder.reductionTabletsNo}
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >�������</span></b></p>
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
								    <p ><b><span >��Ƭ</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >ԲƬ����</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	${taskOrder.dicingNo}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >Ƭ��</span></b></p>
								    </td>
								    <td colspan=6>
								    	${taskOrder.dicingTabletsNo}
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >���о����</span></b></p>
								    </td>
								    <td colspan=6>
								    	${taskOrder.dicingTubeNum}
								    </td>
								    </tr>
								    <tr >
								    <td width=83 >
								    <p ><b><span
								    >��Ƭ����</span></b></p>
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
								    <p ><b><span >��װ</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >��װ״̬</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	${taskOrder.packageStatusNames}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >�����ȼ�</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.qualityLevel}
								    </td>
								    <td width=87 >
								    <p ><b><span >ԲƬ����</span></b></p>
								    </td>
								    <td width=224 >
								  		${taskOrder.discBatch}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >�� ��</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.packageNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >оƬ��ʶ</span></b></p>
								    </td>
								    <td width=224 >
								    	${taskOrder.chipLabel}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >�ܿ��ͺ�</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.shellType}
								    </td>
								    <td width=87 >
								    <p ><b><span
								    >ѹ��ͼ��</span></b></p>
								    </td>
								    <td width=224 >
								    	${taskOrder.bondNum}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >��װ��ʽ</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.packageShape}
								    </td>
								    <td width=87 >
								    <p ><b><span >���Ҫ��</span></b></p>
								    </td>
								    <td width=224 >
								   		${taskOrder.markDemand}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >ʹ��ԲƬ��</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.discNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >ԲƬ�Ƿ��в�/�޵�</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.waferFlag==1}">
										   		��
										   	</c:when>
										   	<c:otherwise>
										   		��
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
					<th width=88 >������ʽ��</th>
					<td width=558 colspan=7>
						${taskOrder.checkTypeName}
					</td>
				</tr>
				<tr>
					<th width=88 >�����̶ȣ�</th>
					<td width=558 colspan=7>
						<c:choose>
						   	<c:when test="${taskOrder.urgency==1}">
						   		����
						   	</c:when>
						   	<c:when test="${taskOrder.urgency==2}">
						   		������
						   	</c:when>
						   	<c:otherwise>
						   		һ��
						   	</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th width=88 >����ԭ��˵����</th>
					<td width=558 colspan=7>
						${taskOrder.applyReason}
					</td>
				</tr>
				<tr>
					<th width=88>����Ҫ��</th>
					<td width=558 colspan=7>
						${taskOrder.detailRequire}
					</td>
				</tr>
				<tr>
					<th width=88 >��ע��</th>
					<td width=558 colspan=7>
						${taskOrder.remarks}
					</td>
				</tr>
				<tr>
					<th width=88 >�������Ÿ����������</th>
					<td width=558 colspan=7>
						${taskOrder.productManagesuggest}
					</td>
				</tr>
			</table>