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
<title>添加系统</title>

</head>
<body class="easyui-layout">
<form id="edit" method="post" enctype="multipart/form-data">
		<input type="hidden" id="basepath" value="<%=basePath %>"/>
		<ul class="inBox">
			<input type="hidden" id="id" name="id" value="${webApplication.id}">
			<li><label for="">系统名称：<span style="color: red;">*</span></label><input type="text" id="appName"
				name="appName" value="${webApplication.appName}" readonly="readonly"/><span style="color: #AAAAAA">(不可编辑)</span></li>
			<li><label for="">系统标识：<span style="color: red;">*</span></label><input type="text"
				id="identify" name="identify" value="${webApplication.identify}" readonly="readonly"/><span style="color: #AAAAAA">(不可编辑)</span></li>
			<li><label for="">系统编号：<span style="color: red;">*</span></label><input type="text"
				id="appNo" name="appNo" value="${webApplication.appNo}" readonly="readonly"/><span style="color: #AAAAAA">(不可编辑)</span></li>
			<li><label for="">系统网址：<span style="color: red;">*</span></label><input type="text"
				id="appUrl" name="appUrl" value="${webApplication.appUrl}"/></li>
			<li><label for="">系统图标：</label>
				<div class="dib imgBox bE5" style="margin-left: -3px;">
					<div class="fileBox">
						<input type="file" id="thumbnaila" class="file" hidden=true/>
						<img id="imgthumbnaila" onclick="$('input[id=thumbnaila]').click();" src="${webApplication.applicationImg}"/>
						<input type="hidden" id="application_imga" name="applicationImg" value="${webApplication.applicationImg}"/>
					<p>
						<a href="javascript:delthumbnail('imgthumbnail')">删除</a>
					</p>
					</div>
					<span style="color:#AAAAAA">(要求：640x640像素，仅支持PNG格式)</span>
				</div>
				
			</li>
		</ul>
		<div class="edBox tac">
			<a href="javascript:;" class="btnG btnBl" id="editWebApplicationBtn" onclick="editWebApplicationBtn()">保存</a>
			<a href="javascript:;" class="btnG btnOg"  onclick="cancelWebApplicationBtn()">取消</a>
		</div>
</form>
<script type="text/javascript" src="<%=basePath%>/static/js/webApplication/webApplicationEdit.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/LocalResizeIMGG.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/images.js"></script>
</body>
</html>