<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	function modifyPwd() {
		$("#basicInfo_dialog").editDialog({
			width : 520,
			height : 250,
	        url : '${pageContext.request.contextPath}/admin/modifyPwd.jsp',
	        title :"修改密码"
	    });
	}
	function pt_rwdcl_link(){
		layout_center_addTabFun({
			title : '待处理查询',
			closable : true,
			href : '${pageContext.request.contextPath}/taskmanage/taskSusList.jsp'
		});
	}
	function es_rwdcl_link(){
		layout_center_addTabFun({
			title : '二筛任务待处理',
			closable : true,
			href : '${pageContext.request.contextPath}/taskmanage/erSaiTaskSusList.jsp'
		});
	}
	function ys_rwdcl_link(){
		layout_center_addTabFun({
			title : '验收任务待处理',
			closable : true,
			href : '${pageContext.request.contextPath}/yans/yansTaskSusList.jsp'
		});
	}
</script>
<div style="width:80%;margin-left: auto;margin-right: auto;">
	<div style="margin-top: 20px;">
		<div id="xinxi"  title="个人基本信息" class="easyui-panel">
			<table class="tableForm"  width="100%">
				<tr>
		            <th width="10%" class="textinfo_title" align="right" >
		                	用户名
		            </th>
		            <td width="20%" class="" align="left">
		            		${memberInfo.loginName}
		            </td>
		            <th width="12%" class="textinfo_title" align="right">
		                	真实姓名
		            </th>
		            <td width="20%" class="" align="left">
		            		${memberInfo.realName}
		            </td>
		            <th width="10%" class="textinfo_title" align="right" >
		                	工号
		            </th>
		            <td width="28%" class="" align="left">
		            		${memberInfo.memberId}
		            </td>
		        </tr>
		    	<tr>
		            <th width="10%" class="textinfo_title" align="right" >
		                	所属部门
		            </th>
		            <td width="20%" class="" align="left">
		            		${memberInfo.departmentName}
		            </td>
		            <th width="10%" class="textinfo_title" align="right">
		                	所属角色
		            </th>
		            <td width="20%" class="" align="left">
		            		${memberInfo.roleNames}
		            </td>
		            <th width="10%" class="textinfo_title" align="right" >
		                	邮箱地址
		            </th>
		            <td width="30%" class="" align="left">
		            		${memberInfo.mailAddress}
		            </td>
		        </tr>
		        <tr>
		            <th width="10%" class="textinfo_title" align="right" >
		                	注册时间
		            </th>
		            <td width="20%" class="" align="left">
		            		<fmt:formatDate value="${memberInfo.registerDate}" pattern="yyyy-MM-dd HH:mm:ss" />
		            </td>
		            <th width="10%" class="textinfo_title" align="right">
		                	上次登录时间
		            </th>
		            <td width="20%" class="" align="left">
		            		<fmt:formatDate value="${memberInfo.lastLoginDate}" pattern="yyyy-MM-dd HH:mm:ss" />
		            </td>
		            <th width="10%" class="textinfo_title" align="right" >
		                	状态
		            </th>
		            <td width="30%" class="" align="left">
		            		${memberInfo.statusName}
		            </td>
		        </tr>
		        <tr>
		           	<td colspan="6">
		           		<a class="easyui-linkbutton" id="zxcl_outcall" href="javascript:modifyPwd()" style="float:right">修改密码</a>
		            </td>
		        </tr>
			</table>
		</div>
	</div>
	<div style="margin-top: 20px;">
		<div title="待处理信息" class="easyui-panel">
			<table class="tableForm" width="90%">
		    	<tr>
		            <th width="15%" class="textinfo_title" align="right" >
						普通任务待处理
		            </th>
		            <td width="85%"  align="left">
		            	<c:if test="${taskInfo == 0}">
		            		无
		            	</c:if>
		            	<c:if test="${taskInfo != 0}">
		            		尚有<a style="color:red;font-weight:bold" href="javascript:pt_rwdcl_link()">${taskInfo}</a>条待处理任务单。
		            		<a style="color:blue;font-weight:bold" href="javascript:pt_rwdcl_link()">【立即处理】</a>
		            	</c:if>
		            </td>
		       	</tr>
		       	<tr>
		            <th width="15%" class="textinfo_title" align="right" >
						二筛任务待处理
		            </th>
		            <td width="85%"  align="left">
		            	<c:if test="${ersaiTaskInfo == 0}">
		            		无
		            	</c:if>
		            	<c:if test="${ersaiTaskInfo != 0}">
		            		尚有<a style="color:red;font-weight:bold" href="javascript:es_rwdcl_link()">${ersaiTaskInfo}</a>条待处理任务单。
		            		<a style="color:blue;font-weight:bold" href="javascript:es_rwdcl_link()">【立即处理】</a>
		            	</c:if>
		            </td>
		       	</tr>
		       	<tr>
		            <th width="15%" class="textinfo_title" align="right" >
						验收任务待处理
		            </th>
		            <td width="85%"  align="left">
		            	<c:if test="${yansTaskInfo == 0}">
		            		无
		            	</c:if>
		            	<c:if test="${yansTaskInfo != 0}">
		            		尚有<a style="color:red;font-weight:bold" href="javascript:ys_rwdcl_link()">${yansTaskInfo}</a>条待处理任务单。
		            		<a style="color:blue;font-weight:bold" href="javascript:ys_rwdcl_link()">【立即处理】</a>
		            	</c:if>
		            </td>
		       	</tr>
			</table>
		</div>
	</div>
</div>
<div id="basicInfo_dialog"></div>