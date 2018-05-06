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
<title>分配角色</title>
</head>
<body class="">
<form id="allocat" method="post">
		<input type="hidden" id="id" name="id" value="${user.id}">
		<input type="hidden" id="basepath" value="<%=basePath %>"/>
		<input type="hidden" id="change">
		<ul class="inBox roleInBox">
			<li><label for="" class="fontw">已选员工：</label><input type="text"
				id="realname" name="loginname" value="${user.loginname}" readonly="readonly" /><span style="color: #AAAAAA">(不可编辑)</span></li>
			
			<li><p class="fontw">分配角色</p></li>
			<li class="clearfix"><label for="" class="fl">系统：</label>
				<div id="selectBox" class="fl">
				<select id="systemId" name="systemId" onchange="chan()"  style="width: 194px; height: 28px; line-height: 28px;" >
					<c:forEach items="${webApplicationList}" var="wList">
						<option value="${wList.id}" <c:if test="${10 != wList.id}">selected</c:if>>${wList.appName}</option>
					</c:forEach>
				</select>
				</div>
			</li>
			<li><label for="">角色：</label></li>
			<div class="selectBox clearfix">
			<div class="checkboxList fl">
				<ul id="roleDiv">
				<c:forEach items="${roleList}" var="role">
					<li>
						<input type="checkbox" class="roleBox" name="rolename" id="${role.id }" data-id="${role.id }"/>
						<label for="${role.id }">${role.rolename}</label>
					</li>
				</c:forEach>
				
				</ul>
			</div>
			<div class="selectedList fl">
				<ul></ul>
			</div>
			<input type="hidden" name="roleIds" id="roleIds"/>
		</div>
		</ul>
		<div class="edBox tac">
			<a href="javascript:;" class="btnG btnBl" id="addAllocatBtn" onclick="addAllocatBtn()">保存</a>
			<a href="javascript:;" class="btnG btnOg"  onclick="cancelAllocatBtn()">取消</a>
		</div>
</form>
<script type="text/javascript" src="<%=basePath%>/static/js/user/userAllocatRoles.js"></script>
<script type="text/javascript">
	$(function (){
		chan();
	});
</script>
</body>
</html>