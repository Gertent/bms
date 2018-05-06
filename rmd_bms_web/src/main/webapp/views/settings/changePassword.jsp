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
<head lang="en">
<meta charset="UTF-8">
<title>修改密码</title>
<link rel="stylesheet" href="<%=basePath%>/static/css/common/reset.css"
	type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/static/css/goodsManage.css"
	type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/static/css/changeNoInfo.css"
	type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/static/css/addGoods.css"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/static/js/easyui-1.4.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/static/js/easyui-1.4.5/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/static/js/easyui-1.4.5/demo.css">
<style>
.datagrid-btable .datagrid-cell {
	padding: 6px 4px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.tableCon {
	border: 1px #dcdcdc solid;
}

.tableCon tr {
	border-bottom: 1px #dcdcdc solid;
}

.tableCon td,.tableCon th {
	text-align: center;
	border-right: 1px #dcdcdc solid;
}
#change .inBox{margin-left: 27.5%;}
#change .inBox li>input{width: 260px;}
</style>
</head>
<body class="easyui-layout">
<form id="change" method="post">
		<input type="hidden" id="basepath" value="<%=basePath %>"/>
		<input type="hidden" name="id" value="${user.id}"/>
		<ul class="inBox">
			<li><label for="">原密码：<span style="color: red;">*</span></label><input type="password"
			 id="password" name="password" class="easyui-validatebox" required="true" maxlength="20"/></li>
			<li><label for="">新密码：<span style="color: red;">*</span></label><input type="password"
				id="newPassword" name="newPassword" maxlength="20" class="easyui-validatebox"
			     required="true" validtype="newPassword"/></li>
			
			<li><label for="">确认密码：<span style="color: red;">*</span></label><input type="password"
				id="confirmPassword" name="confirmPassword"
				class="easyui-validatebox" maxlength="20" validType="equalTo['#newPassword']" required="true" invalidMessage="两次输入的密码不一致"/></li>
		</ul>
		<div class="edBox tac">
			<a href="javascript:;" class="btnG btnBl" onclick="editPasswordBtn()">保存</a>
		</div>
</form>
<script type="text/javascript"
		src="<%=basePath%>/static/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/easyui-1.4.5/jquery.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/easyui-1.4.5/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/easyui-1.4.5/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript"
	    src="<%=basePath %>/static/js/validator.js"></script>
	<script type="text/javascript"
	    src="<%=basePath %>/static/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/settings/changePassword.js"></script>
</body>
</html>