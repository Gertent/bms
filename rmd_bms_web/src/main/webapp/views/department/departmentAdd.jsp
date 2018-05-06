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
<title>添加部门</title>
</head>
<body class="easyui-layout">
	<form id="add" method="post">
		<input type="hidden" id="id" name="id" >
		<ul class="inBox">
			<li><label for="">部门名称：<span style="color: red;">*</span></label><input type="text"
				id="deptname" name="deptname"/></li>
			<li><label for="">上级部门：</label><input type="hidden" id="parentId1" name="parentId" />
		<input id="selectUp2" style="width: 288px;height: 28px;line-height: 28px;padding-left: 5px;"/></li>
		</ul>
		<div class="edBox tac">
			<a href="javascript:;" class="btnG btnBl" id="addDepartmentBtn" onclick="addDepartmentBtn()">添加</a>
			<a href="javascript:;" class="btnG btnOg" id="cancelAddBtn" onclick="cancelAddBtn()">取消</a>
		</div>
	</form>
	<script type="text/javascript" src="<%=basePath%>/static/js/department/departmentAdd.js"></script>
</body>
</html>