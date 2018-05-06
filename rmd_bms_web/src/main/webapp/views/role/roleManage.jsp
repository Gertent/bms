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
.inBox #menuDiv{margin: -18px 0 0 205px;}
.inBox #system{border: 1px solid #e5e5e5;}
</style>
</head>
<body class="easyui-layout">
	<div class="goodsmBox smBox" data-options="region:'center'"
		style="border: 0">
		<div class="easyui-layout" data-options="fit:true">
			<div class="header" id="roleListSearch"
				data-options="region:'north'" style="border: 0">
				<p class="navBar mgB16">
					<a class="btnG btnBl" onclick="roleSearchBox()">搜索</a> 
					<a class="btnG btnBl" onclick="roleReset()">重置</a>
				</p>
				<table class="gList">
					<td><label for="" class="dib w77">系统名称</label>
					<select id="systemId" name="systemId" class="easyui-combobox"  style="width: 194px; height: 28px; line-height: 28px;" >
						<c:forEach items="${webApplicationList}" var="appList">
							<option value="${appList.id}" <c:if test="${appList.id ==10}">selected</c:if> >${appList.appName}</option>
						</c:forEach>
					</select>
					</td>
					<td><label for="" class="dib tar">角色</label><input
						class="easyui-validatebox combo"  id="rolename" name="rolename" /></td>
					<td><label for="" class="dib tar">状态</label>
						<select id="status" name="status" class="easyui-combobox"  style="width: 194px; height: 28px; line-height: 28px;" >
						<option value="">全部</option>
						<option value="1">正常</option>
						<option value="0">锁定</option>
						</select>
				    </td>
				</table>
			</div>
			<div class="center" data-options="region:'center'" style="border: 0">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'north'"
						style="padding: 20px 30px 12px; border: 0">
						<p class="cenBtn">
							<a class="btnG btnBl" id="updateLockStatusById" onclick="updateLockStatusById(0)">锁定</a>
							<a class="btnG btnBl" id="updateLockStatusById" onclick="updateLockStatusById(1)">解锁</a>
							<a class="btnG btnBl" id="editRole" >编辑</a>
							<a class="btnG btnBl" id="addRole" ">添加</a>
							<a class="btnG btnBl" id="deleteRole" onclick="deleteRole()">删除</a>
						</p>
					</div>
					<!-- datagrid列表  -->
					<div data-options="region:'center'"
						style="padding: 1px 30px 20px; border: 0">
						<table id="role_list" width="100%"></table>
					</div>
				</div>
				<!--编辑角色弹窗页面 -->
				<div id="editRoleWindow" class="editNoticBox"></div>
				<!--添加角色弹窗页面 -->
				<div id="addRoleWindow" class="addNoticBox"></div>
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
		src="<%=basePath%>/static/js/LocalResizeIMG.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/LocalResizeDOC.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/role/roleManage.js"></script>

	<script type="text/javascript">
		//主页面表单
		$('.cenList .list li').click(function() {
			var index = $(this).index();
			$(this).addClass("active").siblings().removeClass("active");
		});
		
		//编辑角色弹出框
	  $('#editRole').click(function(){
	  	var rows = $('#role_list').datagrid('getSelections');
	  	if(rows == ""){
	  		$.messager.alert("提示", "请选择一条数据 ！", "info");   
	  		return;
	  	}
	  	if(rows.length > 1){
	  		$.messager.alert("提示", "请选择一条数据 ！", "info");   
	  		return;
	  	}
	  	$('.editNoticBox').window({
	  			href:"<%=basePath%>/role/roleEditPage?id=" + rows[0].id,
				width : 800,
				height : 435,
				modal : true,
				shadow : false,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				minimizable : false,
				maximizable : false,
				title : "编辑角色"

			});
		});
		//添加角色
		$('#addRole').click(function(){
	  		$('.addNoticBox').window({
	  			href:"<%=basePath%>/role/roleAddPage",
				width : 800,
				height : 435,
				modal : true,
				shadow : false,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				minimizable : false,
				maximizable : false,
				title : "添加角色"
			});
		});
		
	</script>
</body>
</html>