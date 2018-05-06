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
<title>编辑角色</title>

</head>
<body class="easyui-layout">
<form id="edit" method="post">
		<input type="hidden" id="basepath" value="<%=basePath %>"/>
		<input type="hidden" id="menuIds" name="menuIds"/>
		<input type="hidden" id="id" name="id" value="${role.id}" />
		<input type="hidden" id="oldrolename" value="${role.rolename}" />
		<input type="hidden" id="systemId" name="systemId" value="${webApplication.id}"/>
		<ul class="inBox">
			<li><label for="">角色名称：</label><input type="text" id="rolename"
				name="rolename" value="${role.rolename}"/></li>
			<li><label for="">系统：</label>
			<select class="easyui-combobox" disabled="disabled" style="width: 194px; height: 28px; line-height: 28px;" >
				<option value="" selected>${webApplication.appName}</option>
			</select></li>
			
			<li><label for="" style="vertical-align: top;">权限：</label>
			<ul id="editOperationList" name="editOperationList" class="dib easyui-tree" style="padding-top: 12px"></ul> 
		</li>
		</ul>
		<div class="edBox tac">
			<a href="javascript:;" class="btnG btnBl" id="editRoleBtn" onclick="editRoleBtn()">编辑</a>
			<a href="javascript:;" class="btnG btnOg"  onclick="cancelRoleBtn()">取消</a>
		</div>
</form>
<script type="text/javascript" src="<%=basePath%>/static/js/role/roleEdit.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/js/validator.js"></script>
<script type="text/javascript">
	var menuSet=${menuList};
	var menulist = menuSet.menuList;
  	//菜单权限分配与回显
	$('#editOperationList').tree({
		url:"<%=basePath%>/role/getTreeEnumInfo.do?systemId=" + "${webApplication.id}",
		method:'get',   
		animate:true,
		checkbox:true,
		lines:true,
		onLoadSuccess:function(){
			var nodes = $("#editOperationList").tree("getChildren");
			$(nodes).each(function(i,node){
				//只处理叶子节点
				if($("#editOperationList").tree("isLeaf",node.target)){
					//如果是操作
					if(node.attributes.nodeType=='operation'){
					    $(menulist).each(function(j, menu){
					      if(menu.operationId == node.id){
					    	$("#editOperationList").tree('check',node.target);
					    	return false;
				      	  }
					    });
					}
					//如果是菜单
					if(node.attributes.nodeType=='menu'){
					   $(menulist).each(function(i, menu){
				          if(menu.menuId == node.id){
				    	  	$("#editOperationList").tree('check',node.target);
				    	  	return false;
				          }
					   });
					}
				}
			});
		}
	});
</script>
</body>
</html>