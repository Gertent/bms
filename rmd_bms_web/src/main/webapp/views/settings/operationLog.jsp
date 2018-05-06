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
<title>角色管理</title>
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
</style>
</head>
<body class="easyui-layout">
	<div class="goodsmBox smBox" data-options="region:'center'"
		style="border: 0">
		<div class="easyui-layout" data-options="fit:true">
			<div class="header" id="operationSearch"
				data-options="region:'north'" style="border: 0">
				<p class="navBar mgB16">
					<a class="btnG btnBl" onclick="operationSearchBox()">搜索</a> 
					<a class="btnG btnBl" onclick="operationReset()">重置</a>
				</p>
				<table class="gList">
				    <td>
				    	<label for="" class="dib tar">系统</label>
				    	<select id="systemId" name="systemId" class="easyui-combobox"  style="width: 194px; height: 28px; line-height: 28px;" >
							<option value="">选择系统</option>
							<c:forEach items="${webApplicationList}" var="appList">
								<option value="${appList.appNo}">${appList.appName}</option>
							</c:forEach>
						</select>
				    </td>
					<td><label for="" class="dib tar">账号</label><input
						class="easyui-validatebox combo"  id="loginname" name="loginname" /></td>
					<td><label for="" class="dib tar">操作内容</label><input
						class="easyui-validatebox combo"  id="operation" name="operation" /></td>
					<td style="width: 400px;">
						<label for="" class="dib tar">操作时间</label>
						<input class="easyui-datetimebox easyui-validatebox" id="starttime" name="starttime" style="width: 120px; height: 28px; line-height: 28px;" />
						--
						<input class="easyui-datetimebox easyui-validatebox" id="endtime" name="endtime" style="width: 120px; height: 28px; line-height: 28px;"/>
					</td>
				</table>
			</div>
			<div class="center" data-options="region:'center'" style="border: 0">
				<div class="easyui-layout" data-options="fit:true">
					<!-- datagrid列表  -->
					<div data-options="region:'center'"
						style="padding: 1px 30px 20px; border: 0">
						<table id="operation_list" width="100%"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
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
		src="<%=basePath%>/static/js/settings/operationLog.js"></script>

	<script type="text/javascript">
		//主页面表单
		$('.cenList .list li').click(function() {
			var index = $(this).index();
			$(this).addClass("active").siblings().removeClass("active");
		});
	</script>
</body>
</html>