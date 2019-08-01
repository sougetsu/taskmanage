<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
	
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$.ajax({
            type: "POST",
            url:'${pageContext.request.contextPath}/taskManageRadiation/getSumResult',
            dataType : 'json',
			success : function(result) {
				parent.$.messager.progress('close');
				if (result.success) {
					 //定义一个数组
                    var browsers = new Array();
                  	//迭代，把异步获取的数据放到数组中
                    $.each(result.obj,function(i,d){
                    	var sfr=[d.name,parseInt(d.rate)];
                        browsers.push(sfr);
                    });
					$('#container1').highcharts({
						credits : {
							enabled : false
						},
				        chart: {
				            plotBackgroundColor: null,
				            plotBorderWidth: null,
				            plotShadow: false
				        },
				        title: {
				            text: '鉴定类辐射任务单状态占比'
				        },
				        tooltip: {
				            headerFormat: '{series.name}<br>',
				            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
				        },
				        plotOptions: {
				            pie: {
				                allowPointSelect: true,
				                cursor: 'pointer',
				                dataLabels: {
				                    enabled: false
				                },
				                showInLegend: true
				            }
				        },
				        series: [{
				            type: 'pie',
				            name: '任务单完成占比',
				            data:browsers
				        }]
				    });
				}
			}
        });
		$.ajax({
            type: "POST",
            url:'${pageContext.request.contextPath}/taskManagemdRadiation/getSumResult',
            dataType : 'json',
			success : function(result) {
				parent.$.messager.progress('close');
				if (result.success) {
					 //定义一个数组
                    var browsers = new Array();
                  	//迭代，把异步获取的数据放到数组中
                    $.each(result.obj,function(i,d){
                    	var sfr=[d.name,parseInt(d.rate)];
                        browsers.push(sfr);
                    });
					$('#container2').highcharts({
						credits : {
							enabled : false
						},
				        chart: {
				            plotBackgroundColor: null,
				            plotBorderWidth: null,
				            plotShadow: false
				        },
				        title: {
				            text: '摸底类辐射任务单状态占比'
				        },
				        tooltip: {
				            headerFormat: '{series.name}<br>',
				            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
				        },
				        plotOptions: {
				            pie: {
				                allowPointSelect: true,
				                cursor: 'pointer',
				                dataLabels: {
				                    enabled: false
				                },
				                showInLegend: true
				            }
				        },
				        series: [{
				            type: 'pie',
				            name: '任务单完成占比',
				            data:browsers
				        }]
				    });
				}
			}
        });
		$.ajax({
            type: "POST",
            url:'${pageContext.request.contextPath}/taskManageRadiation/getSumByMonth',
            dataType : 'json',
			success : function(result) {
				parent.$.messager.progress('close');
				if (result.success) {
					 //定义一个数组
                    var browsers = new Array();
                  	//迭代，把异步获取的数据放到数组中
                    $.each(result.obj,function(i,d){
                    	var sfr=[d.name,d.count];
                        browsers.push(sfr);
                    });
                  	alert(browsers);
					$('#container3').highcharts({
						credits : {
							enabled : false
						},
						chart: {
					        type: 'line'
					    },
					    title: {
					        text: '任务单每月创建数'
					    },
					    xAxis: {
					        categories: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
					    },
					    yAxis: {
					        title: {
					            text: '数量 (条)'
					        }
					    },
					    plotOptions: {
					        line: {
					            dataLabels: {
					                enabled: true          // 开启数据标签
					            },
					            enableMouseTracking: false // 关闭鼠标跟踪，对应的提示框、点击事件会失效
					        }
					    },
					    series:result.obj
				    });
				}
			}
        });
	});
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false">
		<div id="container1" style="float:left ;  width:50%;  height:100%;"></div>
		<div id="container2" style="float:left ;  width:50%;  height:100%;"></div>
		<div id="container3" style="width:100%;  height:100%;"></div>
	</div>
</div>