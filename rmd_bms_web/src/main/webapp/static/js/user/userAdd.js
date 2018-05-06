var basepath = $("#basepath").val();
$('#selectUp2').combotree({
	url: basepath + '/department/listDepartment',
	editable: false,
	onSelect: function (row) {
			
			$('#selectUp2').combotree('setValue', row.deptname);
			$("#deptId").val(row.id);
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
function addUserBtn(){
	var isValidatePass = $('#add').form('validate');
	if (isValidatePass){
		var userInfo = $('#add').serializeJson();
		$.ajax({
			type : "POST",
			url : './addUser',
			data : $('#add').serialize(),
			dataType:'json',
			success : function(data) {
				if (data == "true") {
					$.messager.alert("提示", "操作成功 ", "info");
					$('.addNoticBox').dialog('close');//关闭弹出窗
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
function cancelUserBtn(){
	$("#addUserWindow").window("close");
}