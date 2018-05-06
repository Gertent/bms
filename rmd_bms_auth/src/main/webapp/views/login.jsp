<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<title>统一系统登录平台</title>
<link rel="stylesheet" href="<%=basePath%>/static/css/common/reset.css" />
<link rel="stylesheet" href="<%=basePath%>/static/css/login.css" />
<link rel="stylesheet" href="<%=basePath%>/static/js/easyui-1.4.5/themes/default/easyui.css"/>
<link rel="stylesheet" href="<%=basePath%>/static/js/easyui-1.4.5/themes/icon.css"/>
<link rel="stylesheet" href="<%=basePath%>/static/js/easyui-1.4.5/demo.css"/>
<script type="text/javascript" src="<%=basePath%>/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/easyui-1.4.5/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">

	if(window!=top){
	    top.location.href=location.href;
	}

	function chgUrl(url) {
		var timestamp = (new Date()).valueOf();
		url = url + "?timestamp=" + timestamp;
		return url;
	}
	
	$(document).ready(function(){
		$("#limage").attr("src",chgUrl('<%=basePath%>/getcheckcode'));
		document.onkeydown=function(event){
			var e = event || window.event || arguments.callee.caller.arguments[0];
			if(e && e.keyCode==13){ // enter 键
				checkLogin();
			}
		}; 
	});
	
	function checkLogin(){
		$("form").submit();
	}
</script>
</head>

<body>
		<div class="loginbg"><img src="<%=basePath%>/static/image/bgpic.png" width="100%" height="100%" alt="logo"/></div>
		<div class="loginForm">
		    <div class="nav">
		        <img src="<%=basePath%>/static/image/logoIcon.png" width="50%" height="50%" alt="logo"/>
		        <h1>统一系统登录平台</h1>
		    </div>
		    <form id="form" autocomplete="off" action="" method="post">
		    	<input type="hidden" name="userName" id="userName" />
		        <div class="section">
		            <ul class="formIn">
		                <li><img src="<%=basePath%>/static/image/userIcon.png" alt=""/><input type="text" class="txt" onchange="$('#userName').val($(this).val())" /></li>
		                <li><img src="<%=basePath%>/static/image/passIcon.png" alt=""/><input type="password" name="pwd" id="pwd" class="txt" /></li>
		            </ul>
		            <div class="validation clearfix">
		                <div class="dateBox">
		                    <input id="checkcode" type="text" name="checkcode" class="code" placeholder="验证码" />
		                </div>
		                <a href="#" class="moveAnother" onclick="javascript:var aaa=document.getElementById('limage');aaa.src=chgUrl('<%=basePath%>/getcheckcode');"></a>
		                <div class="dateImg">
		     				<input id="limage" type="image" name="checkcode" style="width: 100px; height: 40px;"/> 
		                </div>
		            </div>
		            <label id="errormsg" style="font-size: 10px; color: red;"> 
						<c:if test="${error_message!=null}">
							${error_message}
						</c:if>
					</label> 
		        </div>
		        <input type="button" value="立即登录" class="loBtn btnG btnOg" onclick="checkLogin()" />
		    </form>
		    	<a href="http://rj.baidu.com/soft/detail/14744.html" style="font-size: 15px">为了更好的体验，建议您使用Chrome访问</a>
		</div>
</body>
<script type="text/javascript">
	
</script>
</html>