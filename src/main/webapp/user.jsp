<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>任务单管理系统</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="shortcut icon" href="../favicon.ico" type="image/x-icon" />
<jsp:include page="inc.jsp"></jsp:include>
<script type="text/javascript">
	var defaultStatus = 0;
	function defaultSelect(){
		if(defaultStatus == 0 ){
			layout_center_addTabFun({
				title : '个人首页',
				closable : false,
				href : '${pageContext.request.contextPath}/member/userInfoPage'
			});
			defaultStatus = 1;
		}
	}
	
	function banBackSpace(e){
	    var ev = e || window.event;//获取event对象
	    var obj = ev.target || ev.srcElement;//获取事件源
	    var t = obj.type || obj.getAttribute('type');//获取事件源类型
	    //获取作为判断条件的事件类型
	    var vReadOnly = obj.readOnly;
	    var vDisabled = obj.disabled;
	    //处理undefined值情况
	    vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
	    vDisabled = (vDisabled == undefined) ? true : vDisabled;
	    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
	    //并且readOnly属性为true或disabled属性为true的，则退格键失效
	    var flag1= ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea")&& (vReadOnly==true || vDisabled==true);
	    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
	    var flag2= ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea" ;
	    //判断
	    if(flag2 || flag1)return false;
	}
	//禁止退格键 作用于Firefox、Opera
	document.onkeypress=banBackSpace;
	//禁止退格键 作用于IE、Chrome
	document.onkeydown=banBackSpace;
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',href:'${pageContext.request.contextPath}/layout/north.jsp'" style="height: 100px;overflow: hidden;" class="logo"></div>
	<div data-options="region:'west',split:true,href:'${pageContext.request.contextPath}/layout/west.jsp'" title="功能菜单" style="width: 200px;overflow: hidden;"></div>
	<div data-options="region:'center',href:'${pageContext.request.contextPath}/layout/center.jsp'" style="overflow: hidden;"></div>
	<div data-options="region:'south',href:'${pageContext.request.contextPath}/layout/south.jsp'" style="height: 27px;overflow: hidden;"></div>
</body>
</html>