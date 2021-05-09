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
					<th width="150px">���񵥺�</th>
					<td width=174 colspan=2>
						${taskOrder.lsh}
					</td>
					<th width=101 colspan=2></th>
					<td width=264 colspan=3>
					</td>
				</tr>
				<tr>
					<th style="width: 150px">��·����</th>
					<td width=174 colspan=2>
						${taskOrder.electricName}
					</td>
					<th width=121 colspan=2>��Ŀ����</th>
					<td width=264 colspan=3>
						${taskOrder.projectName}
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
					<th width=88 >�Ƿ��и���</th>
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
					<th >���Ƶ�λ</th>
					<td colspan=2>${taskOrder.superviseUnit}</td>
				</tr>
				<tr>
					<th width=88 >�ܿ����</th>
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
					<th >����</th>
					<td colspan=2>${taskOrder.detailPlanNo}</td>
				</tr>
				<tr>
					<th width=88 >ҵ����������</th>
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
								    <td width=31 rowspan=7 >
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
								   <tr>
									    <td width=83 >
									    <p ><b><span
									    >���</span></b></p>
									    </td>
									    <td width=194 >
									   		<c:choose>
										   	<c:when test="${taskOrder.stockName==1}">
										   		A��
										   	</c:when>
										   	<c:otherwise>
										   		B��
										   	</c:otherwise>
										</c:choose>
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
				</c:choose>
				<c:choose>
				   	<c:when test="${taskOrder.mixPackageFlag==1}">
				   		<tr>
							<th width=88></th>
							<td width=558 colspan=7>
								<table border="1" bordercolor="#B5C0C4" style="border-collapse:collapse;">
								   <tr >
								    <td width=31 rowspan=8 >
								    <p ><b><span >��Ϸ�װ</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >��װ״̬</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	${taskOrder.mpackageStatusNames}
								    </td>
								   </tr>
								   <tr>
								    <td width=83 >
								    <p ><b><span
								    >�����ȼ�</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.mqualityLevel}
								    </td>
								    <td width=87 >
								    <p ><b><span >ԲƬ����</span></b></p>
								    </td>
								    <td width=194 >
								  		${taskOrder.mdiscBatch}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >�� ��</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mpackageNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >оƬ��ʶ</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mchipLabel}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >�ܿ��ͺ�</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mshellType}
								    </td>
								    <td width=87 >
								    <p ><b><span
								    >ѹ��ͼ��</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mbondNum}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >��װ��ʽ</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mpackageShape}
								    </td>
								    <td width=87 >
								    <p ><b><span >���Ҫ��</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.mmarkDemand}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >ʹ��ԲƬ��</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mdiscNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >ԲƬ�Ƿ��в�/�޵�</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.mwaferFlag==1}">
										   		��
										   	</c:when>
										   	<c:otherwise>
										   		��
										   	</c:otherwise>
										</c:choose>
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >����оƬ��</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mchipNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >����Ƿ�����</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.mstockFlag==1}">
										   		��
										   	</c:when>
										   	<c:otherwise>
										   		��
										   	</c:otherwise>
										</c:choose>
								    </td>
								    <tr>
									    <td width=83 >
									    <p ><b><span
									    >���</span></b></p>
									    </td>
									    <td width=194 >
									   		<c:choose>
										   	<c:when test="${taskOrder.mstockName==1}">
										   		A��
										   	</c:when>
										   	<c:otherwise>
										   		B��
										   	</c:otherwise>
										</c:choose>
									    </td>
									    <td width=87 >
									    <p ><b><span ></span></b></p>
									    </td>
									    <td width=194 >
									    </td>
								   	</tr>
								    
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
								    <td width=31 rowspan=8 >
								    <p ><b><span >��оƬ��װ</span></b></p>
								    </td>
								    <td width=83 >
								    <p ><b><span
								    >��װ״̬</span></b></p>
								    </td>
								    <td width=505 colspan=3 >
								    	${taskOrder.mcpackageStatusNames}
								    </td>
								   </tr>
								   <tr>
								    <td width=83 >
								    <p ><b><span
								    >�����ȼ�</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.mcqualityLevel}
								    </td>
								    <td width=87 >
								    <p ><b><span >ԲƬ����</span></b></p>
								    </td>
								    <td width=194 >
								  		${taskOrder.mcdiscBatch}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >�� ��</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcpackageNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >оƬ��ʶ</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcchipLabel}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >�ܿ��ͺ�</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcshellType}
								    </td>
								    <td width=87 >
								    <p ><b><span
								    >ѹ��ͼ��</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcbondNum}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >��װ��ʽ</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcpackageShape}
								    </td>
								    <td width=87 >
								    <p ><b><span >���Ҫ��</span></b></p>
								    </td>
								    <td width=194 >
								   		${taskOrder.mcmarkDemand}
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >ʹ��ԲƬ��</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcdiscNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >ԲƬ�Ƿ��в�/�޵�</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.mcwaferFlag==1}">
										   		��
										   	</c:when>
										   	<c:otherwise>
										   		��
										   	</c:otherwise>
										</c:choose>
								    </td>
								   </tr>
								   <tr >
								    <td width=83 >
								    <p ><b><span
								    >����оƬ��</span></b></p>
								    </td>
								    <td width=194 >
								    	${taskOrder.mcchipNum}
								    </td>
								    <td width=87 >
								    <p ><b><span >����Ƿ�����</span></b></p>
								    </td>
								    <td width=194 >
								   		<c:choose>
										   	<c:when test="${taskOrder.mcstockFlag==1}">
										   		��
										   	</c:when>
										   	<c:otherwise>
										   		��
										   	</c:otherwise>
										</c:choose>
								    </td>
								   </tr>
								   <tr>
									    <td width=83 >
									    <p ><b><span
									    >���</span></b></p>
									    </td>
									    <td width=194 >
									   		<c:choose>
										   	<c:when test="${taskOrder.mcstockName==1}">
										   		A��
										   	</c:when>
										   	<c:otherwise>
										   		B��
										   	</c:otherwise>
										</c:choose>
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
				</c:choose>
				<tr>
					<th width="150px" >��������</th>
					<td style="text-align:left" colspan=2>
						${taskOrder.orderTypeName}
					</td>
					<th width="150px" colspan=2 >��Ʒ״̬</th>
					<td style="text-align:left" colspan=3>
						<c:choose>
						   	<c:when test="${taskOrder.productStatus==0}">
						   		����
						   	</c:when>
						   	<c:when test="${taskOrder.productStatus==1}">
						   		��Ʒ
						   	</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th width="150px" >ί������</th>
					<td width=174 colspan=2>
						${taskOrder.entrustNum}
					</td>
					<th width=121 colspan=2></th>
					<td width=264 colspan=3>
					</td>
				</tr>
				<tr>
					<th width=88 >������ʽ</th>
					<td width=558 colspan=7>
						${taskOrder.checkTypeName}
					</td>
				</tr>
				<tr>
					<th width=88 >�����̶�</th>
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
					<th width=88 >����ԭ��˵��</th>
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
					<th width=88 >��ע</th>
					<td width=558 colspan=7>
						${taskOrder.remarks}
					</td>
				</tr>
				<tr>
					<th width=88 >�������Ÿ��������</th>
					<td width=558 colspan=7>
						${taskOrder.productManagesuggest}
					</td>
				</tr>
			</table>
