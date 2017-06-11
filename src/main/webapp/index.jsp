<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
   	<title>任务单管理系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
	<style type="text/css">
		body {
			background-image: url(style/images/houtai_delu_tu_01.jpg);
			margin-top: 0px;
		}
		.login_banner {
			width: 1005px;
			height:227px;
			margin: 0 auto;
		}
		.login_banner img {
			margin-top: 2px;
		}
		.login_center {
			width: 1005px;
			height:202px;
			margin: 0 auto;
			background-image: url(style/images/houtai_delu_tu_03_1.jpg);
			background-repeat: no-repeat;
		}
		.login_center_form{
			width:502px;
			height:195px;
			float:right;
		}
		#user_login_loginInputForm{
			margin-top: 61px;
		}
		.item{
			padding-top: 5px;
			height: 40px;
			line-height: 26px;
			overflow: hidden;
		}
		.label{
			float:left;
			width:80px;
			text-align:center;
			font-size:16px;
			font-weight:bold;
			color:white;
		}
		.input_form{
			float: left;
			text-align: left;
		}
		.login_button{
			padding-left: 80px;
			height:40px;
			text-align: left;
		}
		.login_button .login_btn{
			padding-left: 15px;
			float:left;
		}
		.btn-login {
			cursor: pointer;
			width: 68px;
			height: 30px;
			background:url(style/images/oicq_tu_01.png) no-repeat;
		}
		.input_text{
			width:185px;
		}
		.login_bottom {
			width: 1005px;
			margin: 0 auto;
			TEXT-ALIGN: center;
		}
		.login_bottom_text{
			margin-top: 10px;
			font-size: 10pt;
		}
		ul, li {
			list-style: none;
		}
		#footer ul {
			padding: 10px 0px;
		}
		#footer ul li {
			color: #333;
			text-align: center;
			padding:5px;
		}
		a {
			color: #333;
			text-decoration: none;
		}
	</style>
	<!-- EasyUI控件 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.3/themes/default/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.3/themes/icon.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-1.8.3.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.3/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript">
		$(function() {
		  	$('#user_login_loginInputForm').form({
				url : '${pageContext.request.contextPath}/member/login',
				success : function(result) {
					var r = $.parseJSON(result);
					if (r.success) {
						window.location.href='${pageContext.request.contextPath}/member/forward';
					}
					$.messager.show({
						title : '提示',
						msg : r.msg
					});
				}
			});
		  	document.onkeydown = function(e){
	       	    var ev = document.all ? window.event : e;
	       	    if(ev.keyCode==13) {
	       	    	login();
	       	     }
	       	};
	  	});
		function login(){
			if($('#user_login_loginInputForm').form('validate')){
				$('#user_login_loginInputForm').submit();
				$.messager.show({  
	                title:'提示',  
	                msg:'正在登录验证，请稍候.',  
	                timeout:0,  
	                showType:'fade'  
	            }); 
	        }
		}
		function cancel(){
			$('#user_login_loginInputForm input').val('');
		}
	</script>
</head>
<body>
	<div class="login_banner" >
		<img src="style/images/banner_tu_01.jpg" width="973" height="225">
	</div>
	<div class="login_center">
		<div class="login_center_form">
			<form method="post" id="user_login_loginInputForm">
				<div class="item">
					 <span class="label">用户名:</span>
					 <div class="input_form">
					 	<input name="loginName" class="easyui-validatebox input_text" data-options="required:true,missingMessage:'请输入用户名',validType:'length[3,20]'" value="" />
					 </div>
				</div>
				<div class="item">
					 <span class="label">密&nbsp;&nbsp;码:</span>
					 <div class="input_form">
					 	<input name="savedPassword" type="password" class="easyui-validatebox input_text" data-options="required:true,missingMessage:'请输入密码',validType:'length[6,16]'" value="" />
					 </div>
				</div>
				<div class="login_button">
					<div class="login_btn">
						<a href="#" id="loginsubmit"  onclick=login()>
							<img src="style/images/oicq_tu_01.png"  onmouseover="this.src='style/images/oicq_tu_01_on.png'"
								onmouseout="this.src='style/images/oicq_tu_01.png'" width="64" height="29" border="0" id="Image1" />
						</a>
					</div>
					<div class="login_btn">
						<a href="#" id="logincancel"  onclick=cancel()>
							<img src="style/images/oicq_tu_02.png"  onmouseover="this.src='style/images/oicq_tu_02_on.png'"
								onmouseout="this.src='style/images/oicq_tu_02.png'" width="64" height="29" border="0" id="Image2" />
						</a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="login_bottom">
		<div id="footer" class="login_bottom_text">
			<ul>
				<li>
					建议使用IE8.0 1024*768以上分辨率浏览本站
				</li>
			</ul>
		</div>
	</div>
</body>
</html>
