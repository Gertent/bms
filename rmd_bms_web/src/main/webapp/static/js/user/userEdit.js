var basepath = $("#basepath").val();
$('#selectUp3').combotree({
	url: basepath + '/department/listDepartment',
	editable: false,
	onSelect: function (row) {
			$('#selectUp3').combotree('setValue', row.deptname);
			$("#deptid").val(row.id);
		},
		formatter: function (row) {
			return '<span class="item-text">' + row.deptname + '</span>';
		}
});
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
function editUserBtn(){
	var isValidatePass = $('#edit').form('validate');
	if (isValidatePass){
		var userInfo = $('#edit').serializeJson();
		if(userInfo.deptId == 0){
			$.messager.alert('提醒', "请选择部门");
			return;
		}
		if(userInfo.realname == ''){
			$.messager.alert('提醒', "请填写姓名");
			return;
		}
		
		$.ajax({
			type : "POST",
			url : './editUser',
			data : userInfo,
			dataType:'json',
			success : function(data) {
				if (data == "true") {
					$.messager.alert("提示", "操作成功 ", "info");
					$('.editNoticBox').dialog('close');//关闭弹出窗
					$("#user_list").datagrid('reload');//重新加载table 
				}
				if(data == "loginnameFalse"){
					$.messager.alert("提示", "此账号已存在!", "info");
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
function cancelEditBtn(){
//	alert("a");
	$(".editNoticBox").window("close");
}