var basepath = $("#basepath").val();
$('#selectUp3').combotree({
	url:'./listDepartment', 
	editable:true,
	onSelect:function(row){
		$('#selectUp3').combotree('setValue', row.deptname);
		$("#parentId").val(row.id);
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
function editDepartmentBtn(){
	var isValidatePass = $('#edit').form('validate');
	if (isValidatePass){
		var departmentInfo = $('#edit').serializeJson();
		
		if(departmentInfo.deptname == ''){
			$.messager.alert('提醒', "请填写部门名称");
			return;
		}
		$.ajax({
			type : "POST",
			url : './editDepartment',
			data : departmentInfo,
			success : function(data) {
				if (data == "true") {
					$.messager.alert("提示", "操作成功 ", "info");
					$('.editNoticBox').dialog('close');//关闭弹出窗
					sort();
//					$('.department_list').datagrid('reload');//重新加载table 
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
function cancelDepartmenteBtn(){
	$("#editDepartmentWindow").window("close");
}