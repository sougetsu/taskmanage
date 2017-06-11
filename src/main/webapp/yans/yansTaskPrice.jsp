<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@page import ="java.util.*,java.text.*"%>
<script type="text/javascript">
	$(function(){
		parent.$.messager.progress('close');
		var lastIndex;
		var editable=false;
		$('#yans_checkPrice_datagrid').datagrid({
		    onClickRow:function(rowIndex){  
				if(editable==false){
					return false;
				}
		        if (lastIndex != rowIndex){  
		            $('#yans_checkPrice_datagrid').datagrid('endEdit', lastIndex);  
		            $('#yans_checkPrice_datagrid').datagrid('beginEdit', rowIndex);  
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
	    var editors = $('#yans_checkPrice_datagrid').datagrid('getEditors', rowIndex);  
	    var rows = $('#yans_checkPrice_datagrid').datagrid('getRows'); 
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
	        var content1 = parseInt($("#yans_sumPrice").html());
	        var sumP = content1+cost;
	        $("#yans_sumPrice").text(sumP);
	    }
	}
	function endEdit(){
		var sumPrice=0;
        var rows = $('#yans_checkPrice_datagrid').datagrid('getRows');
        for ( var i = 0; i < rows.length; i++) {
        	var price = rows[i].unitcost;
        	if (typeof(price)!="undefined" && price !="" )
        	{
        		sumPrice = sumPrice + price*1;
        	}
        	$('#yans_checkPrice_datagrid').datagrid('endEdit', i); 
        }
        $("#yans_sumPrice").text(sumPrice);
    }
	function checkPriceSubmit(){
		var pakstartDate = $("#yans_pakstartDate").val();
		var pakendDate = $("#yans_pakendDate").val();
		if(!comparetime(pakstartDate,pakendDate)){
			alert("封装开始时间大于结束时间,请重新选择！");
			return;
		}
		 if ($('#yans_checkPrice_datagrid').datagrid('getChanges').length) {
	            var updated = $('#yans_checkPrice_datagrid').datagrid('getChanges', "updated");
	            var effectRow = new Object();
	            if (updated.length) {
	           	 effectRow = obj2str(updated);
	           	 $("#yans_priceItems").val(effectRow);
	            }
	            var submitForm = $('#yans_taskOrder_pricecheck_form');
	            $.ajax({
	           	type: "POST",
				url : '${pageContext.request.contextPath}/yansTaskManage/checkPrice',
				data:submitForm.serialize(),
	            dataType : 'json',
				success : function(result) {
					if (result.success) {
						var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
						$('#layout_center_tabs').tabs('close', index);
						$('#yans_taskorder_list_datagrid').datagrid('reload');	
						$('#yans_taskorder_suslist_datagrid').datagrid('reload');	
					}
					$.messager.show({
						title : '提示',
						msg : result.msg
					});
				}
			});
	        }else{
	       	 alert("未进行核价");
	        } 
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
		   		$("#yans_day1").text(workingDays+"天");
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
		<form id="yans_taskOrder_pricecheck_form" method="post">
		<div align="center" style="width:780px;margin-left: auto;margin-right: auto;">
			<input type="hidden"  name="orderId" value="${taskOrder.orderId}"/>
			<input type="hidden" id="yans_priceItems" name="priceItems"/>
			<table id="yans_checkPrice_datagrid" style="width:auto;height:auto"
				title="封测中心内部结算"
				singleSelect="true" idField="itemId" url="${pageContext.request.contextPath}/yansTaskManage/listPrice">
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
						<th field="remark"hidden = true width="150">说明</th>
					</tr>
				</thead>
			</table>
			<table>
				<tr>
					<th width="440" align="right">总合计</th>
					<td width="80" align="right" id ="yans_sumPrice">0</td>
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
			   	<table id="yans_tt_confirm" style="width:90%;height:auto" title="确认消息" cellSpacing=0 cellPadding=5>
			    	<tr>
			    		<td width="10%" align="right">
	                       	开始时间
	                   	</td>
	                   	<td width="20%" align="left">
	                   		<input id="yans_pakstartDate" name="pakstartDate" class="easyui-validatebox" data-options="required:true"
									onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',onpicked: function(){getDays('yans_pakstartDate','yans_pakendDate',1);} })" 
									value=<%=sdf.format(date)%> style="width:100%"/>
	                   	</td>
	                    <td  width="10%" align="right" >
	                       	结束时间
	                   	</td>
	                   	<td width="20%" align="left">
	                   		<input id="yans_pakendDate" name="pakendDate" class="easyui-validatebox" data-options="required:true"
									onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',onpicked: function(){getDays('yans_pakstartDate','yans_pakendDate',1);} })" 
									value=<%=sdf.format(date)%> style="width:100%"/>
	                   	</td>
	                   	<td  width="10%" align="right" >
	                       	工期
	                   	</td>
	                   	<td width="20%" id="yans_day1" align="left">
	                   		0天
	                   	</td>
				    </tr>
			    </table>
		    </div>
		    <div align="center" style="width:780px;margin-left: auto;margin-right: auto;margin-top: 20px;margin-bottom: 20px;">
		    	<a id="yans_price_submit" class="l-btn" href="javascript:void(0);" onclick="javascript:checkPriceSubmit()">
	         		<span class="l-btn-left">
	         			<span class="l-btn-text icon-save l-btn-icon-left" style="padding-left: 20px; ">保存</span>
	         		</span>
	         	</a>
	         	<a id="yans_price_close" class="l-btn" href="javascript:void(0);" onclick="javascript:closeCurrentTab()">
	         		<span class="l-btn-left">
	         			<span class="l-btn-text icon-cancel l-btn-icon-left" style="padding-left: 20px; ">关闭</span>
	         		</span>
	         	</a>
		    </div>
		</div>
		</form>
	</div>
</div>