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
function editInformationBtn(){
	var isValidatePass = $('#account').form('validate');
	if (isValidatePass){
		var accountInfo = $('#account').serializeJson();
		var id=$("#id").val();
		var mobile = accountInfo.mobile;
		var email = accountInfo.email;
		var tel = accountInfo.tel;
		$.ajax({
			type : "POST",
			url : './updateInformation',
			data : {
					'id':id,
					'mobile':mobile,
					'email':email,
					'tel' : tel
					},
			success : function(data) {
				if(data.code == '010000'){
					$.messager.alert("提示", data.desc, "info");
				}else{
					$.messager.alert("提示", data.desc, "info");
				}	
			}
		});
	}
}
function cancelEditBtn(){
	window.location.href="./informationsize";
}