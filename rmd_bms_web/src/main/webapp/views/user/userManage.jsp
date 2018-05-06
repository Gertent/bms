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
<title>员工管理</title>
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
<link rel="stylesheet" href="<%=basePath%>/static/css/other/omsSelect.css"
	type="text/css" />
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

/*add */
.addInBox li>p{
	font-size:14px;
	margin-left: 42px;
    font-weight: bold;
    margin-top: 20px;
}
.addInBox li>ul>li{
	height:30px;
	margin:10px 0;
}
.addInBox li>ul>li label{
	width:25%;
	text-align:right;
	margin-right:10px;
	display: inline-block;
    vertical-align: middle;
}
.addInBox li>ul>li input{
	width:293px !important;
	height:26px;
	line-height:26px;
	padding-left:10px;
}
.addInBox li .mediaBox textarea{
	margin-left:108px;
	width:393px;
	height:100px;
	border: 1px solid #e5e5e5;
}
.fontw{
	font-weight:bold;
	vertical-align:middle !important;
}
.inBox p.fontw{
	margin:15px 0 0 78px;
	font-size:15px;
}
.roleInBox #selectBox{margin-top:10px;}
.roleInBox #selectBox select{border:1px #e5e5e5 solid;}
.gList .textbox{margin-left: -4px!important;width:193px!important;}
.addInBox li>ul>li input[name="loginname"],
.addInBox li>ul>li input[name="password"],
.addInBox li>ul>li input[name="realname"],
.addInBox li>ul>li input#jobNum,
#edit li>ul>li input#position,
.addInBox li>ul>li  .textbox{
	margin-left: -4px!important;
}
#edit li>ul>li  .textbox{margin-left: -7px!important;}
</style>
</head>
<body class="easyui-layout">
	<input type="hidden" id="basepath" value="<%=basePath %>"/>
	<div class="goodsmBox smBox" data-options="region:'center'"
		style="border: 0">
		<div class="easyui-layout" data-options="fit:true">
			<div class="header" id="userListSearch"
				data-options="region:'north'" style="border: 0">
				<p class="navBar mgB16">
					<a class="btnG btnBl" onclick="userSearchBox()">搜索</a> 
					<a class="btnG btnBl" onclick="userReset()">重置</a>
				</p>
				<table class="gList">
					<td><label for="" class="dib w47">账号</label><input
						class="easyui-validatebox combo"  id="loginname" name="loginname"  /></td>
					<td><label for="" class="dib tar">姓名</label><input
						class="easyui-validatebox combo"  id="realname" name="realname" /></td>
					<td><label for="" class="dib tar">工号</label><input
						class="easyui-validatebox combo"  id="jobNum" name="jobNum"  /></td>
					<td><label for="" class="dib w47">部门</label><input type="hidden" id="parentId" name="deptid"/>
			 			<input id="selectUp" class="easyui-combotree" style="width: 184px;height: 28px;line-height: 28px;padding-left: 5px;"/></td>
					<td><label for="" class="dib tar">角色</label>
						<select id="roleId" name="roleId" class="easyui-combobox" style="width: 194px; height: 28px; line-height: 28px;" >
						<option value=""></option>
						<c:forEach items="${roleList}" var="rList">
							<option value="${rList.id}">${rList.appName}&nbsp:&nbsp${rList.rolename}</option>
						</c:forEach>
						</select>
				    </td>
				    <td><label for="" class="dib tar">状态</label>
						<select id="status" name="status" class="easyui-combobox"  style="width: 194px; height: 28px; line-height: 28px;" >
						<option value="2">全部</option>
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
							<a class="btnG btnBl" id="lock" onclick="lockStatus(0)">锁定</a>
							<a class="btnG btnBl" id="unlock" onclick="lockStatus(1)">解锁</a>
							<a class="btnG btnBl" id="deleteUser" onclick="deleteUser()">删除</a>
							<a class="btnG btnBl" id="editUser">编辑</a>
							<a class="btnG btnBl" id="addUser">添加</a>
							<a class="btnG btnBl" id="allocateRoles">分配角色</a>
						</p>
					</div>
					<!-- datagrid列表  -->
					<div data-options="region:'center'"
						style="padding: 1px 30px 20px; border: 0">
						<table id="user_list" width="100%"></table>
					</div>
				</div>
				<!--编辑用户弹窗页面 -->
				<div id="editUserWindow" class="editNoticBox"></div>
				<!--添加用户弹窗页面 -->
				<div id="addUserWindow" class="addNoticBox"></div>
				<!--分配角色弹窗页面 -->
				<div id="allocateRolesWindow" class="allocateNoticBox"></div>
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
		src="<%=basePath%>/static/js/user/userManage.js"></script>
	<script type="text/javascript"
	    src="<%=basePath%>/static/js/common.js"></script>
	<script type="text/javascript"
	    src="<%=basePath%>/static/js/validator.js"></script>
	<script type="text/javascript">
		//主页面表单
		$('.cenList .list li').click(function() {
			var index = $(this).index();
			$(this).addClass("active").siblings().removeClass("active");
		});
		
		//编辑员工弹出窗
	  $('#editUser').click(function(){
	  	var rows = $('#user_list').datagrid('getSelections');
	  	if(rows == ""){
	  		$.messager.alert("提示", "请选择一条数据 ！", "info");   
	  		return;
	  	}
	  	if(rows.length > 1){
	  		$.messager.alert("提示", "请选择一条数据 ！", "info");   
	  		return;
	  	}
	  	$('.editNoticBox').window({
	  			href:"<%=basePath%>/user/userEditPage?id=" + rows[0].id,
				width : 800,
				height : 435,
				modal : true,
				shadow : false,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				minimizable : false,
				maximizable : false,
				title : "编辑员工"

			});
		});
		//添加弹出框
	  $('#addUser').click(function(){
		 	$(".addNoticBox").window({
		  		href:"<%=basePath%>/user/userAddPage",
		  		 width:800,
		         height:435,
		         modal:true,
		         shadow:false,
		         collapsible:false,
		         minimizable:false,
		         maximizable:false,
		         title:"添加员工"
		      }); 
		  });
		//分配角色弹出框
		 $('#allocateRoles').click(function(){
			 var rows = $('#user_list').datagrid('getSelections');
			  	if(rows == ""){
			  		$.messager.alert("提示", "请选择一条数据 ！", "info");   
			  		return;
			  	}
			  	if(rows.length > 1){
			  		$.messager.alert("提示", "请选择一条数据 ！", "info");   
			  		return;
			  	}
		 	$(".allocateNoticBox").window({
		  		href:"<%=basePath%>/user/allocatePage?id=" + rows[0].id,
		  		 width:800,
		         height:530,
		         modal:true,
		         shadow:false,
		         collapsible:false,
		         minimizable:false,
		         maximizable:false,
		         title:"员工分配角色"
		      }); 
		  });
	</script>
</body>
</html>