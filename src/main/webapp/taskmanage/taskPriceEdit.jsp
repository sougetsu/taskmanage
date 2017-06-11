<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@page import ="java.util.*,java.text.*"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	$(function(){
		parent.$.messager.progress('close');
		var lastIndex;
		var editable=false;
		$('#checkPriceEdit_datagrid').datagrid({
		    onClickRow:function(rowIndex){  
				if(editable==false){
					return false;
				}
		        if (lastIndex != rowIndex){  
		            $('#checkPriceEdit_datagrid').datagrid('endEdit', lastIndex);  
		            $('#checkPriceEdit_datagrid').datagrid('beginEdit', rowIndex);  
		            setEditing(rowIndex);  
		        }
		        lastIndex = rowIndex;  
		    },
		    toolbar : [ {
                text : "开始编辑",
                iconCls : "icon-edit",
                handler : function() {
                	editable = true;
                }
            },{
                text : "结束编辑",
                iconCls : "icon-edit",
                handler : function() {
                	endEdit();
                	editable = false;
                }
            }]
		}) 
	});
	function setEditing(rowIndex){ 
	    var editors = $('#checkPriceEdit_datagrid').datagrid('getEditors', rowIndex);  
	    var rows = $('#checkPriceEdit_datagrid').datagrid('getRows'); 
	    var basePrice = rows[rowIndex].basePrice;
	    var priceEditor = rows[rowIndex].price;
	    var amountEditor = editors[0];  
	    var costEditor = editors[1];   
	    amountEditor.target.bind('change', function(){  
	        calculate();  
	    });  
	    function calculate(){
	    	var cost;
	    	if(amountEditor.target.val()==0){
	    		cost = 0.0;
	    	}else{
	    		cost = basePrice+priceEditor * amountEditor.target.val(); 
	    	}
	        $(costEditor.target).numberbox('setValue',cost);
	        var content1 = parseInt($("#sumPrice").html());
	        var sumP = content1+cost;
	        $("#sumPrice").text(sumP);
	    }
	}
	function endEdit(){
		var sumPrice=0;
        var rows = $('#checkPriceEdit_datagrid').datagrid('getRows');
        for ( var i = 0; i < rows.length; i++) {
        	var price = rows[i].unitcost;
        	if (typeof(price)!="undefined" && price !="" )
        	{
        		sumPrice = sumPrice + price*1;
        	}
        	$('#checkPriceEdit_datagrid').datagrid('endEdit', i); 
        }
        $("#sumPrice").text(sumPrice);
    }
	function checkPriceSubmit(){
		var pakstartDate = $("#pakstartDate").val();
		var pakendDate = $("#pakendDate").val();
		var svstartDate = $("#svstartDate").val();
		var svendDate = $("#svendDate").val();
		var teststartDate = $("#teststartDate").val();
		var testendDate = $("#testendDate").val();
		var sxstartDate = $("#sxstartDate").val();
		var sxendDate = $("#sxendDate").val();
		var jdstartDate = $("#jdstartDate").val();
		var jdendDate = $("#jdendDate").val();
		if(!comparetime(pakstartDate,pakendDate)){
			alert("封装开始时间大于结束时间,请重新选择！");
			return;
		}
		if(!comparetime(svstartDate,svendDate)){
			alert("监制开始时间大于结束时间,请重新选择！");
			return;
		}
		if(!comparetime(teststartDate,testendDate)){
			alert("成测开始时间大于结束时间,请重新选择！");
			return;
		}
		if(!comparetime(sxstartDate,sxendDate)){
			alert("筛选开始时间大于结束时间,请重新选择！");
			return;
		}
		if(!comparetime(jdstartDate,jdendDate)){
			alert("鉴定开始时间大于结束时间,请重新选择！");
			return;
		}
	            var updated = $('#checkPriceEdit_datagrid').datagrid('getChanges', "updated");
	            var effectRow = new Object();
	            if (updated.length) {
	           	 effectRow = obj2str(updated);
	           	 $("#priceEditItems").val(effectRow);
	            }
	            var submitForm = $('#taskOrder_priceEdit_form');
	            $.ajax({
	           	type: "POST",
				url : '${pageContext.request.contextPath}/taskManage/checkPrice',
				data:submitForm.serialize(),
	               dataType : 'json',
				success : function(result) {
					if (result.success) {
						var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
						$('#layout_center_tabs').tabs('close', index);
						$('#taskorder_list_datagrid').datagrid('reload');
						$('#taskorder_suslist_datagrid').datagrid('load');
					}
					$.messager.show({
						title : '提示',
						msg : result.msg
					});
				}
			});
	}
	function getDays(dateStart,dateEnd,dayPositon){
		var strDateStart = document.getElementById(dateStart).value;
		var strDateEnd = document.getElementById(dateEnd).value;
		if(comparetime(strDateStart,strDateEnd)){
			var strSeparator = "-"; //日期分隔符
		   	var oDate1;
		   	var oDate2;
		   	var iDays;
		   	oDate1= strDateStart.split(strSeparator);
		   	oDate2= strDateEnd.split(strSeparator);
		   	var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
		   	var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
		   	var base = 1000 * 60 * 60 * 24;
		   	//iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24);//把相差的毫秒数转换为天数 
		   	var  workingDays = 0;
		   	while (strDateE.getTime() - strDateS.getTime() >= 0){
		        if(strDateS.getDay() != 6 && strDateS.getDay() != 0) {
		            workingDays ++;
		        }
		        strDateS = new Date(strDateS.getTime() + base);
		    }
		   	var dateEnd = addDate(strDateE,1);
		   	if(dayPositon=='1'){
		   		$("#day1").text(workingDays+"天");
		   		$("#day2").text("1天");
		   		$("#day3").text("1天");
		   		$("#day4").text("1天");
		   		$("#day5").text("1天");
		   		$("#svstartDate").val(dateEnd);
		   		$("#svendDate").val(dateEnd);
		   		$("#teststartDate").val(dateEnd);
		   		$("#testendDate").val(dateEnd);
		   		$("#sxstartDate").val(dateEnd);
		   		$("#sxendDate").val(dateEnd);
		   		$("#jdstartDate").val(dateEnd);
		   		$("#jdendDate").val(dateEnd);
		   	}
		   	if(dayPositon=='2'){
		   		$("#day2").text(workingDays+"天");
		   		$("#day3").text("1天");
		   		$("#day4").text("1天");
		   		$("#day5").text("1天");
		   		$("#teststartDate").val(dateEnd);
		   		$("#testendDate").val(dateEnd);
		   		$("#sxstartDate").val(dateEnd);
		   		$("#sxendDate").val(dateEnd);
		   		$("#jdstartDate").val(dateEnd);
		   		$("#jdendDate").val(dateEnd);
		   	}
		   	if(dayPositon=='3'){
		   		$("#day3").text(workingDays+"天");
		   		$("#day4").text("1天");
		   		$("#sxstartDate").val(dateEnd);
		   		$("#sxendDate").val(dateEnd);
		   		$("#jdstartDate").val(dateEnd);
		   		$("#jdendDate").val(dateEnd);
		   	}
		   	if(dayPositon=='4'){
		   		$("#day4").text(workingDays+"天");
		   		$("#jdstartDate").val(dateEnd);
		   		$("#jdendDate").val(dateEnd);
		   	}
		   	if(dayPositon=='5'){
		   		$("#day5").text(workingDays+"天");
		   	}
		}else{
			alert("开始时间大于结束时间,请重新选择！");
		}
	   
	}
	function addDate(date,days){ 
       var d=new Date(date); 
       d.setDate(d.getDate()+days); 
       var m=d.getMonth()+1; 
       return d.getFullYear()+'-'+m+'-'+d.getDate(); 
     }
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false" style="position:relative;left:0;top:0;">
		<form id="taskOrder_priceEdit_form" method="post">
		<div align="center" style="width:780px;margin-left: auto;margin-right: auto;">
			<input type="hidden"  name="orderId" value="${taskOrder.orderId}"/>
			<input type="hidden" id="priceEditItems" name="priceItems"/>
			<table id="checkPriceEdit_datagrid" style="width:auto;height:auto"
				title="封测中心内部结算"
				singleSelect="true" idField="itemId" url="${pageContext.request.contextPath}/taskManage/listPriceEdit?id=${taskOrder.orderId}">
				<thead>
					<tr>
						<th field="itemId" hidden = true width="80">Item ID</th>
						<th field="itemName" width="100">项目内容</th>
						<th field="basePrice" width="90" align="right">起步收费</th>
						<th field="price" width="90" align="right">收费标准（元）</th>
						<th field="chargeUnit" width="80" align="right">计价单位</th>
						<th field="amount" width="80" align="right"
							editor="{type:'numberbox',options:{precision:0}}">数量</th>
						<th field="unitcost" width="80" align="right" editor="{type:'numberbox',options:{precision:1}}">总计（元）</th>
						<th field="remarks" width="150" editor="{type:'text'}">备注</th>
					</tr>
				</thead>
			</table>
			<table>
				<tr>
					<th width="440" align="right">总合计</th>
					<td width="80" align="right" id ="sumPrice">${taskOrder.sumPrice}</td>
					<td width="260" align="left">
			        </td>
				<tr>
			</table>
		</div>
		<div align="center" style="width:780px;margin-left: auto;margin-right: auto;margin-top: 20px;margin-bottom: 20px;"> 
			<div title="安排时间" class="easyui-panel"> 
				<% 
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
				 %>
			   	<table id="tt_confirm" style="width:90%;height:auto" title="确认消息" cellSpacing=0 cellPadding=5>
			    	<tr>
			    		<th  width="10%" align="right">封装:</th>
			    		<td width="10%" align="right">
	                       	开始时间
	                   	</td>
	                   	<td width="20%" align="left">
	                   		<input id="pakstartDate" name="pakstartDate" class="easyui-validatebox" data-options="required:true"
									onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',onpicked: function(){getDays('pakstartDate','pakendDate',1);} })" 
									value=<fmt:formatDate value="${taskSchedule.pakstartDate}" pattern="yyyy-MM-dd" /> style="width:100%"/>
	                   	</td>
	                    <td  width="10%" align="right" >
	                       	结束时间
	                   	</td>
	                   	<td width="20%" align="left">
	                   		<input id="pakendDate" name="pakendDate" class="easyui-validatebox" data-options="required:true"
									onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',onpicked: function(){getDays('pakstartDate','pakendDate',1);} })" 
									value=<fmt:formatDate value="${taskSchedule.pakendDate}" pattern="yyyy-MM-dd" /> style="width:100%"/>
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
	                   		<input id="svstartDate" name="svstartDate" class="easyui-validatebox" data-options="required:true"
									onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',onpicked: function(){getDays('svstartDate','svendDate',2);} })" 
									value=<fmt:formatDate value="${taskSchedule.svstartDate}" pattern="yyyy-MM-dd" /> style="width:100%"/>
	                   	</td>
	                    <td  width="10%" align="right" >
	                       	结束时间
	                   	</td>
	                   	<td width="20%" align="left">
	                   		<input id="svendDate" name="svendDate" class="easyui-validatebox" data-options="required:true"
									onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',onpicked: function(){getDays('svstartDate','svendDate',2);} })" 
									value=<fmt:formatDate value="${taskSchedule.svendDate}" pattern="yyyy-MM-dd" /> style="width:100%"/>
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
	                   		<input id="teststartDate" name="teststartDate" class="easyui-validatebox" data-options="required:true"
								onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',onpicked: function(){getDays('teststartDate','testendDate',3);} })"
								value=<fmt:formatDate value="${taskSchedule.teststartDate}" pattern="yyyy-MM-dd" /> style="width:100%"/>
	                   	</td>
	                    <td  width="10%" align="right" >
	                       	结束时间
	                   	</td>
	                   	<td width="20%" align="left">
	                   		<input id="testendDate" name="testendDate" class="easyui-validatebox" data-options="required:true"
								onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',onpicked: function(){getDays('teststartDate','testendDate',3);} })"
								value=<fmt:formatDate value="${taskSchedule.testendDate}" pattern="yyyy-MM-dd"/> style="width:100%"/>
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
	                   		<input id="sxstartDate" name="sxstartDate" class="easyui-validatebox" data-options="required:true"
									onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',onpicked: function(){getDays('sxstartDate','sxendDate',4);} })"
									value=<fmt:formatDate value="${taskSchedule.sxstartDate}" pattern="yyyy-MM-dd"/> style="width:100%"/>
	                   	</td>
	                    <td  width="10%" align="right" >
	                       	结束时间
	                   	</td>
	                   	<td width="20%" align="left">
							<input id="sxendDate" name="sxendDate" class="easyui-validatebox" data-options="required:true"
								onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',onpicked: function(){getDays('sxstartDate','sxendDate',4);} })"
								value=<fmt:formatDate value="${taskSchedule.sxendDate}" pattern="yyyy-MM-dd"/> style="width:100%"/>
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
	                   		<input id="jdstartDate" name="jdstartDate" class="easyui-validatebox" data-options="required:true"
								onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',onpicked: function(){getDays('jdstartDate','jdendDate',5);} })"
								value=<fmt:formatDate value="${taskSchedule.jdstartDate}" pattern="yyyy-MM-dd"/> style="width:100%"/>
	                   	</td>
	                    <td  width="10%" align="right" >
	                       	结束时间
	                   	</td>
	                   	<td width="20%" align="left">
	                   		<input id="jdendDate" name="jdendDate" class="easyui-validatebox" data-options="required:true"
								onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',onpicked: function(){getDays('jdstartDate','jdendDate',5);} })"
								value=<fmt:formatDate value="${taskSchedule.jdendDate}" pattern="yyyy-MM-dd"/> style="width:100%"/>
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
		    <div align="center" style="width:780px;margin-left: auto;margin-right: auto;margin-top: 20px;margin-bottom: 20px;">
		    	<a id="price_submit" class="l-btn" href="javascript:void(0);" onclick="javascript:checkPriceSubmit()">
	         		<span class="l-btn-left">
	         			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">保存</span>
	         		</span>
	         	</a>
	         	<a id="price_close" class="l-btn" href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
	         		<span class="l-btn-left">
	         			<span class="l-btn-text icon-cancel l-btn-icon-left" style="padding-left: 20px; ">关闭</span>
	         		</span>
	         	</a>
		    </div>
		</div>
		</form>
	</div>
</div>