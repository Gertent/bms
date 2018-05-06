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
<title>添加员工</title>
</head>
<body class="easyui-layout">
<form id="edit" method="post">
		<ul class="inBox addInBox">
		<input type="hidden" id="id" name="id" value="${user.id}">
		<input type="hidden" id="basepath" value="<%=basePath %>"/>
			<li><p>账号信息</p><li>
				<ul>
					<li><label for="">账号：<span style="color: red;">*</span></label><input type="text" id="loginname" name="loginname" class="easyui-validatebox"  value="${user.loginname}" readonly="readonly"/><span style="color: #AAAAAA">(不可编辑)</span></li>
					<li><label for="">登录密码：</label><input type="text" id="password" name="password" class="easyui-validatebox" onblur="onblurPass(this)" validtype="newPassword" maxlength="20" placeholder="空为不修改密码"/></li>
				</ul>
		
			<li><p>个人信息</p>	
				<ul>
					<li><label for="">部门：<span style="color: red;">*</span></label><input type="hidden" id="deptid" name="deptid" value="${user.deptid}"/>
			 			<input id="selectUp3" class="easyui-combotree" value="${user.deptName}" required="true" style="width: 184px;height: 28px;line-height: 28px;padding-left: 5px;"/></li>
					<li><label for="">姓名：<span style="color: red;">*</span></label><input type="text" id="realname" name="realname" class="easyui-validatebox"  required="true" value="${user.realname}"/></li>
					<li><label for="">工号：</label><input type="text" id="jobNum" name="jobNum" value="${user.jobNum}" readonly="readonly"/><span style="color: #AAAAAA">(不可编辑)</span></li>
					<li><label for="">职务：</label><input type="text" id="position" name="position" class="easyui-validatebox"  value="${user.position}"/></li>
				</ul>
			</li>
			<li><p>联系信息</p>
				<ul class="radioBox">
					<li><label for="">邮箱：</label><input id="email" name="email" value="${user.email}" class="easyui-validatebox"  validtype="email" invalidMessage="请输入正确的电子邮箱！"/></li>
					<li><label for="">手机号：</label><input id="mobile" name="mobile" value="${user.mobile}" class="easyui-validatebox"  validtype="mobile" maxlength="11"/></li>
					<li><label for="">座机号：</label><input id="tel" name="tel" value="${user.tel}" class="easyui-validatebox"/></li>
				</ul>
			</li>
			<li><p>备注</p>
				<div class="mediaBox">
					<textarea id="note" name="note" cols="50" rows="30"></textarea>
					<div class="errorDiv clearD" id="fuContenterr"></div>
				</div>
			</li>
		</ul>
		<div class="edBox tac">
			<a href="javascript:;" class="btnG btnBl" id="editUserBtn" onclick="editUserBtn()">编辑</a>
			<a href="javascript:;" class="btnG btnOg"  onclick="cancelEditBtn()">取消</a>
		</div>
</form>
<script type="text/javascript" src="<%=basePath%>/static/js/user/userEdit.js"></script>
<script type="text/javascript">
	$(function (){
		var note='${user.note}';
		$("#note").val(note);
	})
</script>
</body>
</html>