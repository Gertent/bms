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
<form id="add" method="post">
		<ul class="inBox addInBox">
		<input type="hidden" id="basepath" value="<%=basePath %>"/>
			<li><p>账号信息</p>
				<ul>
					<li>
						<label for="">账号：<span style="color: red;">*</span></label>
						<input type="text" id="loginname" name="loginname" class="easyui-validatebox" required="true" maxlength="20" validtype="loginname"/>
					</li>
					<li>
						<label for="">登录密码：<span style="color: red;">*</span></label>
						<input type="text" id="password" name="password" class="easyui-validatebox" required="true" maxlength="20" validtype="newPassword"/>
					</li>
				</ul>
			</li>
			<li><p>个人信息</p>	
				<ul>
					<li>
						<label for="">部门：<span style="color: red;">*</span></label>
						<input type="hidden" id="deptId" name="deptid" />
			 			<input id="selectUp2" class="easyui-combotree" required="true" style="width: 184px;height: 28px;line-height: 28px;padding-left: 5px;"/>
					</li>
					<li>
						<label for="">姓名：<span style="color: red;">*</span></label>
						<input type="text" id="realname" name="realname" required="true" class="easyui-validatebox"/>
					</li>
					<li>
						<label for="">工号：</label>
						<input type="text" id="jobNum" readonly="readonly"  placeholder="不可编辑 默认生成"/>
					</li>
					<li><label for="">职务：</label><input type="text" id="position" name="position" class="easyui-validatebox"/></li>
				</ul>
			</li>
			<li><p>联系信息</p>
				<ul class="radioBox">
					<li><label for="">邮箱：</label><input id="email" name="email" class="easyui-validatebox" validtype="email" invalidMessage="请输入正确的电子邮箱！"/>
					<li><label for="">手机号：</label><input id="mobile" name="mobile" class="easyui-validatebox"  validtype="mobile" maxlength="11"/>
					<li><label for="">座机号：</label><input id="tel" name="tel" class="easyui-validatebox"/>
				</ul>
			</li>
			<li><p>备注</p>
				<div class="mediaBox ">
					<textarea name="note" id="note" cols="20" rows="10"></textarea>
					<div class="errorDiv clearD" id="fuContenterr"></div>
				</div>
			</li>
		</ul>
		<div class="edBox tac">
			<a href="javascript:;" class="btnG btnBl" id="addUserBtn" onclick="addUserBtn()">添加</a>
			<a href="javascript:;" class="btnG btnOg"  onclick="cancelUserBtn()">取消</a>
		</div>
</form>
<script type="text/javascript" src="<%=basePath%>/static/js/user/userAdd.js"></script>

</body>
</html>