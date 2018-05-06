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
<title>账户信息</title>
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
#account .inBox{margin-left: 27.5%;}
#account .inBox li>input{width: 260px;}
</style>
</head>
<body class="easyui-layout">
<form id="account" method="post">
		<input type="hidden" id="id" name="id" value="${user.id}" />
		<input type="hidden" id="basepath" value="<%=basePath %>"/>
		<ul class="inBox">
			<li><label for="">账号：</label><input type="text" 
				id="loginname" name="loginname" value="${user.loginname}" class="easyui-validatebox" readonly="readonly" /></li>
			<li><label for="">真实姓名：</label><input type="text"
				id="realname" name="realname" value="${user.realname}" class="easyui-validatebox" readonly="readonly"/></li>
			<li><label for="">所在部门：</label><input type="text"
				id="deptName" name="deptName" value="${user.deptNames}" class="easyui-validatebox" readonly="readonly"/></li>
			<li><label for="">角色：</label><input type="text"
				id=roleName name="roleName" value="${user.roleName}" class="easyui-validatebox" readonly="readonly"/></li>
			<li><label for="">工号：</label><input type="text"
				id="jobNum" name="jobNum" value="${user.jobNum}" class="easyui-validatebox" readonly="readonly"/></li>
			<li><label for="">职务：</label><input type="text"
				id="position" name="position" value="${user.position}" class="easyui-validatebox" readonly="readonly"/></li>
			<li><label for="">电子邮箱：</label><input type="text"
				id="email" name="email" value="${user.email}" class="easyui-validatebox"  validtype="email" invalidMessage="请输入正确的电子邮箱！"/></li>
			<li><label for="">手机号：</label><input type="text"
				id="mobile" name="mobile" value="${user.mobile}" class="easyui-validatebox" validtype="mobile" maxlength="11" invalidMessage="请输入正确的手机号！"/></li>
			<li><label for="">座机号：</label><input type="text"
				id="tel" name="tel" value="${user.tel}" class="easyui-validatebox"/></li>
		</ul>
		<div class="edBox tac">
			<a href="javascript:;" class="btnG btnBl"  onclick="editInformationBtn()">保存</a>
			<a href="javascript:;" class="btnG btnOg"  onclick="cancelEditBtn()">取消</a>
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
		src="<%=basePath%>/static/js/LocalResizeIMG.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/LocalResizeDOC.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/LocalResizeDOC.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/settings/accountInformation.js"></script>
</body>
</html>