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
function editPasswordBtn(){
	var isValidatePass = $('#change').form('validate');
	if (isValidatePass){
		var passwordInfo = $('#change').serializeJson();
		if( passwordInfo.password==''){
			$.messager.alert('提醒', "请填写原密码!");
			return;
		}
		if( passwordInfo.newPassword==''){
			$.messager.alert('提醒', "请填写新密码!");
			return;
		}
		if( passwordInfo.confirmPassword==''){
			$.messager.alert('提醒', "请填写确认密码 并与新密码相同!");
			return;
		}
		$.ajax({
			type : "POST",
			url : './updatePass',
			data : passwordInfo,
			success : function(data) {
				if (data == "true") {
					$.messager.alert("提示", "操作成功 ", "info");
					$("#password").val('');
					$("#newPassword").val('');
					$("#confirmPassword").val('');
				}
				if(data == "confiremFalse"){
					$.messager.alert("提示", "确认密码与新密码不符，请重新输入!", "info");
					$("#newPassword").val('');
					$("#confirmPassword").val('');
					return;
				}
				if(data == "passwordFalse"){
					$.messager.alert("提示", "原密码输入有误!", "info");
					$("#password").val('');
				}
				if(data == "false"){
					$.messager.alert("提示", "操作失败", "info");
					return;
				}
			}
		});
	}
}
