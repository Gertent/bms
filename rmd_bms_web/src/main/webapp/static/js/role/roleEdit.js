(function($) {
	$.fn.serializeJson = function() {
		var serializeObj = new Object();
		var array = this.serializeArray();
		$(array).each(function() {
			if (serializeObj[this.name]) {
				if ($.isArray(serializeObj[this.name])) {
					serializeObj[this.name].push(this.value);
				} else {
					serializeObj[this.name] = [serializeObj[this.name], this.value];
				}
			} else {
				serializeObj[this.name] = $.trim(this.value);
			}
		});
		return serializeObj;
	};
})(jQuery);
function editRoleBtn(){
	var isValidatePass = $('#edit').form('validate');
	if (isValidatePass){
		var roleInfo = $('#edit').serializeJson();
		if(roleInfo.rolename==''){
			$.messager.alert('提醒', "请填写角色名称");
			return;
		}
		var ids ="";
		var treeNodes = $('#editOperationList').tree('getChecked',['checked','indeterminate']);
		for(var i=0; i<treeNodes.length; i++){
			if(treeNodes[i].attributes.nodeType=="system"){
				continue;
			}
			var menu = "";
			if(treeNodes[i].attributes.nodeType=="operation"){
				menu= $('#editOperationList').tree('getParent',treeNodes[i].target).id + ":" + treeNodes[i].id;
			}else{
				menu= treeNodes[i].id + ":";
			}
			if (ids != '') ids += ',';
			ids += menu;
		}
		$("#menuIds").val(ids);
		var rolename = roleInfo.rolename;
		$.ajax({
			type : "POST",
			url : './roleEdit',
//			data : $("#edit").serialize(),
			data : {
					id:$("#id").val(),
					rolename:rolename,
					menuIds:$("#menuIds").val(),
                	systemId:$("#systemId").val(),
					oldrolename:$("#oldrolename").val()
				},
			dataType:'json',
			success : function(data) {
				if (data == "true") {
					$.messager.alert("提示", "操作成功 ", "info");
					$('.editNoticBox').dialog('close');//关闭弹出窗
					$("#role_list").datagrid('reload');//重新加载table 
				}
				if(data == "rolenameFalse"){
					$.messager.alert("提示", "此角色名称已存在!", "info");
					return;
				}
				if(data == "false"){
					$.messager.alert("提示", "操作失败", "info");
					return;
				}
			}
		});
	}
}
//取消按钮
function cancelRoleBtn(){
	$("#editRoleWindow").window("close");
}