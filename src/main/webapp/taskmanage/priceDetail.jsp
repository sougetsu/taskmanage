<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		var contextPath = '${pageContext.request.contextPath}';
		var sessionId = $("#sessionId").val();
	});


</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<div data-options="region:'center',border:false">
	<div align="center" style="margin-top:20px;">
		<form id="priceDetail_editForm"method="post" enctype="multipart/form-data">
			<input type="hidden" name="priceDetailId" value="${priceDetail.priceDetailId}"/>
			<table class="tasktableForm" width="95%" border="1"  bordercolor="#B5C0C4" rules="none" style="border-collapse:collapse;">
				<tr>
					<th >产品型号</th>
					<td >
						${priceDetail.cpxh}
					</td>
					<th >电路名称</th>
					<td >
						${priceDetail.dlmc}
					</td>
				</tr>
				<tr>
				<th >封装类型</th>
					<td >
						${priceDetail.fzlx}
					</td>
					<th >产品类别</th>
					<td>
						${priceDetail.cplb}
					</td>
				</tr>
				<tr>
					<th >测试机台</th>
					<td >
						${priceDetail.csjt}
					<th >封装单价(元/只)</th>
					<td >
						${priceDetail.fzPrice}
					</td>
				</tr>
				<tr>
					<th >鉴定供货批测试费(元/只)</th>
					<td >
						${priceDetail.jdghpcsPrice}
					</td>
					<th >筛选费用(元/只)</th>
					<td>
						${priceDetail.sxPrice}
					</td>
				</tr>
				<tr>
					<th >鉴定/一致性检测费(元/批)</th>
					<td >
						${priceDetail.jdyzxjcPrice}
					</td>
					<th >验收/只</th>
					<td >
						${priceDetail.ysPrice}
					</td>
				</tr>
				<tr>
					<th >补充筛选</th>
					<td >
						${priceDetail.bcsxPrice}
					</td>
					<th >其他生产费（切筋成型植球植柱）(元/只）</th>
					<td>
						${priceDetail.qtscfPrice}
					</td>
				</tr>
				<tr>
					<th >三温合格品测试费(元/只)</th>
					<td >
						${priceDetail.swhgpcsPrice}
					</td>
					<th >常温产品(元/只)</th>
					<td >
						${priceDetail.cwcpPrice}
					</td>
				</tr>
				<tr>
			         <td colspan=8 style="text-align:center">
			         	<a  class="l-btn" href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
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
