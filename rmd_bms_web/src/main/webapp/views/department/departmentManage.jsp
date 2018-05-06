<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
<title>部门管理</title>
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
#add .textbox{margin-left: -4px!important;}
#edit .inBox>li input{
	width:280px;
}
</style>
</head>
<body class="easyui-layout">

	<div class="goodsmBox smBox" data-options="region:'center'"
		style="border: 0">
		<div class="easyui-layout" data-options="fit:true">
			<div class="header" id="departmentListSearch"
				data-options="region:'north'" style="border: 0">
				<p class="navBar mgB16">
					<a class="btnG btnBl" onclick="departmentSearchBox()">搜索</a> 
					<a class="btnG btnBl" onclick="departmentReset()">重置</a>
				</p>
				<table class="gList">
					<td>
                        <label for="" class="dib w77">部门名称</label>
                        <!-- <input type="hidden" id="parentId" name="parentid"/>
			 			<input id="selectUp" class="easyui-combobox" style="width: 184px;height: 28px;line-height: 28px;padding-left: 5px;"/> -->
                        <input id="deptname" class="easyui-textbox" type="text" style="width: 184px; height: 28px; line-height: 28px; padding-left: 5px;" />
			 		</td>
				</table>
			</div>
			<div class="center" data-options="region:'center'" style="border: 0">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'north'"
						style="padding: 20px 30px 12px; border: 0">
						<p class="cenBtn">
       						<a class="btnG btnBl" id="categorySort" onclick="categorySort()">排序</a>
							<a class="btnG btnBl" id="deleteDepartment" onclick="deleteDepartment()">删除</a>
							<a class="btnG btnBl" id="addDepartment" >添加</a>
							<a class="btnG btnBl" id="editDepartment">编辑</a>
						</p>
					</div>
					<!-- datagrid列表  -->
					<div data-options="region:'center'"
						style="padding: 1px 30px 20px; border: 0">
						<table id="department_list" class="easyui-treegrid department_list" width="100%"></table>
					</div>
				</div>
				<!--编辑部门弹窗页面 -->
				<div id="editDepartmentWindow" class="editNoticBox"></div>
				<!--添加部门弹窗页面 -->
				<div id="addDepartmentWindow" class="addNoticBox"></div>
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
		src="<%=basePath%>/static/js/LocalResizeDOC.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/department/departmentManage.js"></script>
	<script type="text/javascript" src="<%=basePath%>/static/js/department/departmentAdd.js"></script>
	<script type="text/javascript">
		//主页面表单
		$('.cenList .list li').click(function() {
			var index = $(this).index();
			$(this).addClass("active").siblings().removeClass("active");
		});
		//编辑仓库
		//修改弹窗
	  $('#editDepartment').click(function(){
	  	var rows = $('#department_list').datagrid('getSelections');
	  	if(rows == ""){
	  		$.messager.alert("提示", "请选择一条数据 ！", "info");   
	  		return;
	  	}
	  	if(rows.length > 1){
	  		$.messager.alert("提示", "请选择一条数据 ！", "info");   
	  		return;
	  	}
	  	$('.editNoticBox').window({
	  			href:"<%=basePath%>/department/departmentEditPage?id=" + rows[0].id,
				width : 600,
				height : 260,
				modal : true,
				shadow : false,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				minimizable : false,
				maximizable : false,
				title : "编辑部门"

			});
		});
		//添加
	    $('#addDepartment').click(function(){
		  	$('.addNoticBox').window({
		  			href:"<%=basePath%>/department/departmentAddPage",
					width :600,
					height : 435,
					modal : true,
					shadow : false,
					collapsible : false,
					minimizable : false,
					maximizable : false,
					minimizable : false,
					maximizable : false,
					title : "添加部门"

				});
			});
		
	</script>
</body>
</html>