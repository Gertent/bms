$('#selectUp2').combotree({
	url:'./listDepartment', 
	editable:true,
	onSelect:function(row){
		$('#selectUp2').combotree('setValue', row.deptname);
		$('#parentId1').val(row.id);
	},
	formatter:function(row){
		return '<span class="item-text">'+row.deptname+'</span>';
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
function addDepartmentBtn(){
	var isValidatePass = $('#add').form('validate');
	if (isValidatePass){
		var departmentInfo = $('#add').serializeJson();
		if(departmentInfo.deptname == ''){
			$.messager.alert('提醒', "请填写部门名称!","info");
			return;
		}
		$.ajax({
			type : "POST",
			url : './addDepartment',
			data : departmentInfo,
			success : function(data) {
				if (data == "true") {
					
					$('.addNoticBox').dialog('close');//关闭弹出窗
					$.messager.alert("提示", "操作成功 ", "info");
					sort();
					
//					$('#department_list').datagrid('reload');//重新加载table 
				}
				if(data == "deptnameFalse"){
					$.messager.alert("提示", "此部门已存在!", "info");
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
function sort(){
	location.reload();
}
//取消按钮
function cancelAddBtn(){
	$("#addDepartmentWindow").window("close");
}