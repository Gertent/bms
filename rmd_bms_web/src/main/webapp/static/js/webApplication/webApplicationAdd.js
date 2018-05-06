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
function addWebApplicationBtn(){
	var isValidatePass = $('#add').form('validate');
	if (isValidatePass){
		var webApplicationInfo = $('#add').serializeJson();
		if(webApplicationInfo.appName==''){
			$.messager.alert('提醒', "请填写系统名称");
			return;
		}
		if(webApplicationInfo.identify == ''){
			$.messager.alert('提醒', "请填写系统标识");
			return;
		}
		if(webApplicationInfo.appNo == ''){
			$.messager.alert('提醒', "请填写系统编号");
			return;
		}
		if(webApplicationInfo.appUrl == ''){
			$.messager.alert('提醒', "请填写系统网址！");
			return;
		}
		var filename=webApplicationInfo.applicationImg; 
		if(filename !=null && filename !=''){
			var index1=filename.lastIndexOf(".");  
			var index2=filename.length; 
			var postf=filename.substring(index1,index2);//后缀名  
			if(postf !=".png"){
				$.messager.alert("提示", "请选择以.png为后缀的图片!", "info");
				return;
			}
		}
		$.ajax({
			type : "POST",
			url : './webApplicationAdd',
			data : webApplicationInfo,
			success : function(data) {
				if (data == "true") {
					$.messager.alert("提示", "操作成功 ", "info");
					$('.addNoticBox').dialog('close');//关闭弹出窗
					$("#webApplication_list").datagrid('reload');//重新加载table 
				}
				if(data == "appNameFalse"){
					$.messager.alert("提示", "此系统名称已存在!", "info");
					return;
				}
				if(data == "IdentifyFalse"){
					$.messager.alert("提示", "此系统标识已存在!", "info");
					return;
				}
				if(data == "appNoFalse"){
					$.messager.alert("提示", "此系统编号已存在!", "info");
					return;
				}
				if(data == "appUrlFalse"){
					$.messager.alert("提示", "此系统网址已存在!", "info");
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
function cancelWebApplicationBtn(){
	$("#addWebApplicationWindow").window("close");
}