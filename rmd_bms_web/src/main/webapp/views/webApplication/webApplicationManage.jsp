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
<title>系统管理</title>
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
	<input type="hidden" id="basepath" value="<%=basePath %>"/>
	<div class="goodsmBox smBox" data-options="region:'center'"
		style="border: 0">
		<div class="easyui-layout" data-options="fit:true">
			<div class="header" id="webApplicationListSearch"
				data-options="region:'north'" style="border: 0">
				<p class="navBar mgB16">
					<a class="btnG btnBl" onclick="webApplicationSearchBox()">搜索</a> 
					<a class="btnG btnBl" onclick="webApplicationReset()">重置</a>
				</p>
				<table class="gList">
					<td><label for="" class="dib w77">系统名称</label><input
						class="easyui-validatebox combo"  id="appName" name="appName"/></td>
					<td><label for="" class="dib tar">系统标识</label><input
						class="easyui-validatebox combo"  id="identify" name="identify" /></td>
					<td><label for="" class="dib tar">系统编号</label><input
						class="easyui-validatebox combo"  id="appNo" name="appNo"  /></td>
				</table>
			</div>
			<div class="center" data-options="region:'center'" style="border: 0">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'north'"
						style="padding: 20px 30px 12px; border: 0">
						<p class="cenBtn">
							<a class="btnG btnBl" id="categorySort" onclick="categorySort()">排序</a>
							<a class="btnG btnBl" id="deleteWebApplication" onclick="deleteWebApplication()">删除</a>
							<a class="btnG btnBl" id="editWebApplication">编辑</a>
							<a class="btnG btnBl" id="addWebApplication">添加</a>
					</div>
					<!-- datagrid列表  -->
					<div data-options="region:'center'"
						style="padding: 1px 30px 20px; border: 0">
						<table id="webApplication_list" width="100%"></table>
					</div>
				</div>
				<!--编辑用户弹窗页面 -->
				<div id="editWebApplicationWindow" class="editNoticBox"></div>
				<!--添加用户弹窗页面 -->
				<div id="addWebApplicationWindow" class="addNoticBox"></div>
				
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="<%=basePath%>/static/js//jquery-1.8.3.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/easyui-1.4.5/jquery.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/easyui-1.4.5/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/easyui-1.4.5/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/LocalResizeDOC.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/webApplication/webApplicationManage.js"></script>
	<script type="text/javascript">
		//主页面表单
		$('.cenList .list li').click(function() {
			var index = $(this).index();
			$(this).addClass("active").siblings().removeClass("active");
		});
		
		//编辑系统弹出窗
	  $('#editWebApplication').click(function(){
	  	var rows = $('#webApplication_list').datagrid('getSelections');
	  	if(rows == ""){
	  		$.messager.alert("提示", "请选择一条数据 ！", "info");   
	  		return;
	  	}
	  	if(rows.length > 1){
	  		$.messager.alert("提示", "请选择一条数据 ！", "info");   
	  		return;
	  	}
	  	$('.editNoticBox').window({
	  			href:"<%=basePath%>/webApplication/webApplicationEditPage?id=" + rows[0].id,
				width : 800,
				height : 500,
				modal : true,
				shadow : false,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				minimizable : false,
				maximizable : false,
				title : "编辑系统"

			});
		});
	  $('#addWebApplication').click(function(){
		 	$(".addNoticBox").window({
		  		href:"<%=basePath%>/webApplication/webApplicationAddPage",
		  		 width:800,
		         height:500,
		         modal:true,
		         shadow:false,
		         collapsible:false,
		         minimizable:false,
		         maximizable:false,
		         title:"添加系统"
		      }); 
		  });
		
	</script>
</body>
</html>