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
<title>添加角色</title>

</head>
<body class="easyui-layout">
<!-- 	<div class="tabContentBox" style="display: none;" -->
<!-- 		id="addPurchaseBiLLInfo"> -->
<form id="add" method="post">
		<input type="hidden" id="basepath" value="<%=basePath %>"/>
		<input type="hidden" id="menuIds" name="menuIds" value="" />
		<ul class="inBox">
			<li><label for="">角色名称：</label><input type="text" id="rolename"
				name="rolename" /></li>
			<li><label for="">系统：</label>
			<select id="system" name="systemId" onchange="change()" style="width: 194px; height: 28px; line-height: 28px;" >
						<option value="">--请选择系统--</option>
						<c:forEach items="${webApplicationList}" var="appList">
							<option value="${appList.id}">${appList.appName}</option>
						</c:forEach>
			</select></li>
			<li><label for="">权限：</label>
			<div id="menuDiv">
			 <ul id="operationList" class="dib easyui-tree" style="padding-top: 12px"></ul> 
			</div>
		</li>
		</ul>
		<div class="edBox tac">
			<a href="javascript:;" class="btnG btnBl" id="addRoleBtn" onclick="addRoleBtn()">添加</a>
			<a href="javascript:;" class="btnG btnOg"  onclick="cancelRoleBtn()">取消</a>
		</div>
</form>
<script type="text/javascript" src="<%=basePath%>/static/js/role/roleAdd.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/js/validator.js"></script>
<script type="text/javascript">
	function change(){
		var sys_id = $("#system").val();
		$("#operationList").tree({
			url:"<%=basePath%>/role/getTreeEnumInfo.do?systemId=" + sys_id,
			method:"get",
			animate:true,
			checkbox:true,
			lines:true
		});
	}
</script>
</body>
</html>