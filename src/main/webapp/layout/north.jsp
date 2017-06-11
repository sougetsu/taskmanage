<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	/**
	 * @author 孙宇
	 * 
	 * @requires jQuery,EasyUI,jQuery cookie plugin
	 * 
	 * 更换EasyUI主题的方法
	 * 
	 * @param themeName
	 *            主题名称
	 */
	function changeThemeFun(themeName) {
		if ($.cookie('easyuiThemeName')) {
			$('#layout_north_pfMenu').menu('setIcon', {
				target : $('#layout_north_pfMenu div[title=' + $.cookie('easyuiThemeName') + ']')[0],
				iconCls : 'emptyIcon'
			});
		}
		$('#layout_north_pfMenu').menu('setIcon', {
			target : $('#layout_north_pfMenu div[title=' + themeName + ']')[0],
			iconCls : 'tick'
		});

		var $easyuiTheme = $('#easyuiTheme');
		var url = $easyuiTheme.attr('href');
		var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
		$easyuiTheme.attr('href', href);

		var $iframe = $('iframe');
		if ($iframe.length > 0) {
			for ( var i = 0; i < $iframe.length; i++) {
				var ifr = $iframe[i];
				try {
					$(ifr).contents().find('#easyuiTheme').attr('href', href);
				} catch (e) {
					try {
						ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
					} catch (e) {
					}
				}
			}
		}

		$.cookie('easyuiThemeName', themeName, {
			expires : 7
		});

	};

	function logoutFun(b) {
		$.messager.confirm('确认','您确认退出系统？',function(r) {
			if (r) {
				$.getJSON('${pageContext.request.contextPath}/member/logout',function(result) {
					$.messager.show({title : '提示',msg : "成功退出"});
					window.location.href = '${pageContext.request.contextPath}/index.jsp';
				});
			}
		});
	}

	function editCurrentUserPwd() {
		$("#basicInfo_dialog").editDialog({
			width : 520,
			height : 250,
	        url : '${pageContext.request.contextPath}/admin/modifyPwd.jsp',
	        title :"修改密码"
	    });
	}
	function currentUserRole() {
		parent.$.modalDialog({
			title : '我的角色',
			width : 300,
			height : 250,
			href : '${pageContext.request.contextPath}/userController/currentUserRolePage'
		});
	}
	function currentUserResource() {
		parent.$.modalDialog({
			title : '我可以访问的资源',
			width : 300,
			height : 250,
			href : '${pageContext.request.contextPath}/userController/currentUserResourcePage'
		});
	}
</script>
<div id="logo" style ="BACKGROUND-IMAGE: url(../style/images/banner_back_tu_03.jpg);height:100px">
<div id="logo_banner"style ="BACKGROUND-IMAGE: url(../style/images/logo_tu_01.jpg);height:100px;width:427px"></div>
<div id="sessionInfoDiv" style="position: absolute; right: 10px; top: 50px;">
	<c:if test="${sessionInfo.userId != null}">[<strong>${sessionInfo.loginName}</strong>]，欢迎您！</c:if>
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'cog'">更换皮肤</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'cog'">控制面板</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'cog'">注销</a>
</div>
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="changeThemeFun('default');" title="default">default</div>
	<div onclick="changeThemeFun('gray');" title="gray">gray</div>
	<div onclick="changeThemeFun('metro');" title="metro">metro</div>
	<div onclick="changeThemeFun('bootstrap');" title="bootstrap">bootstrap</div>
	<div onclick="changeThemeFun('black');" title="black">black</div>
	<div class="menu-sep"></div>
	<div onclick="changeThemeFun('cupertino');" title="cupertino">cupertino</div>
	<div onclick="changeThemeFun('dark-hive');" title="dark-hive">dark-hive</div>
	<div onclick="changeThemeFun('pepper-grinder');" title="pepper-grinder">pepper-grinder</div>
	<div onclick="changeThemeFun('sunny');" title="sunny">sunny</div>
	<div class="menu-sep"></div>
	<div onclick="changeThemeFun('metro-blue');" title="metro-blue">metro-blue</div>
	<div onclick="changeThemeFun('metro-gray');" title="metro-gray">metro-gray</div>
	<div onclick="changeThemeFun('metro-green');" title="metro-green">metro-green</div>
	<div onclick="changeThemeFun('metro-orange');" title="metro-orange">metro-orange</div>
	<div onclick="changeThemeFun('metro-red');" title="metro-red">metro-red</div>
</div>
<div id="layout_north_kzmbMenu" style="width: 80px; display: none;">
	<div onclick="editCurrentUserPwd();">修改密码</div>
	<div class="menu-sep"></div>
	<%--
	<div onclick="currentUserRole();">我的角色</div>
	<div class="menu-sep"></div>
	<div onclick="currentUserResource();">我的权限</div>--%>
</div>
<div id="layout_north_zxMenu" style="width: 80px; display: none;">
	<div onclick="logoutFun();">锁定窗口</div>
	<div class="menu-sep"></div>
	<div onclick="logoutFun();">重新登录</div>
	<div onclick="logoutFun(true);">退出系统</div>
</div>
</div>
<div id="basicInfo_dialog"></div>