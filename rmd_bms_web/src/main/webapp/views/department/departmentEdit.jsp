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
<title>编辑部门</title>
</head>
<body class="easyui-layout">
<!-- 	<div class="tabContentBox" style="display: none;" > -->
	<form id="edit" method="post">
		<input type="hidden" id="id" name="id" value="${departMent.id}">
		<input type="hidden" id="parentId" name="parentId" value="${departMent.parentId}">
		<input type="hidden" id="basepath" value="<%=basePath %>"/>
		<ul class="inBox">
			<li><label for="">部门名称：</label><input type="text"
				id="deptname" name="deptname" value="${departMent.deptname}"/></li>
		</ul>
		
		<div class="edBox tac">
			<a href="javascript:;" class="btnG btnBl" id="editDepartmentBtn" onclick="editDepartmentBtn()">保存</a>
			<a href="javascript:;" class="btnG btnOg" id="cancelDepartmentBtn" onclick="cancelDepartmenteBtn()">取消</a>
		</div>
	</form>
<script type="text/javascript" src="<%=basePath%>/static/js/department/departmentEdit.js"></script>
<!-- 	</div> -->
</body>
</html>