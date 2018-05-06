 var basepath = $("#basepath").val();
 var newurl='./listDepartment';
$(function (){
	init(newurl);
})
function init(url){
	var queryParams = {};
//	$('#DEPARTMENTLISTSEARCH').FIND('*').EACH(FUNCTION() {
//		QUERYPARAMS[$(THIS).ATTR('NAME')] = $(THIS).VAL();
//	});
	var pageNo = 1;
	var rows = 10;
	if (queryParams.pageNo && queryParams.pageNo != '') {
		pageNo = queryParams.pageNo;
		rows = queryParams.rows;
	}
	$('#department_list').treegrid({
		width: 'auto',
		height: 'auto',
		url : './listDepartment',// True 可折叠
		singleSelect: false, // True 只能选择一行
		idField:'id',
		treeField:'deptname',
		columns : [ [ {
			field : 'id',
			titel : 'id',
			hidden : true
		},{
			field : 'parentid',
			titel : '上级id',
			hidden : true 
		},{
			field: 'sortNum', 
			title: '排序', 
			editor:'numberbox',
			width: '7%', 
			align: 'center',
			formatter: function (value, row, index) {
			return '<input type="text" class="bE5 pdL5" style="width: 25px;" value="'+value+'" onblur="sortNum(this.value,'+row.id+')"/>';
			}
		 },{
			field : 'deptname',
			state:'closed',
			title : '部门名称',
			width : '48%',
			align : 'left'
		},{
			field : 'userNum',
			title : '员工数量',
			width : '44%',
			align : 'center'
		}] ],
		pagination : true,// 显示分页栏。
		rownumbers : true,// 显示行号的列。
		pageSize : rows,
		pageNumber : pageNo,
		queryParams : queryParams,
		pageList : [ 10, 20, 30, 40 ],
	});
}
//动态搜索方法
function departmentSearchBox() {
	var deptname = $("#deptname").val();
    var params = {
        "deptname" : deptname,
    };
    if (deptname != "") {
        $('#department_list').treegrid('options').url = "./searchDepartment";
    } else {
        $('#department_list').treegrid('options').url = "./listDepartment";
    }
    $('#department_list').treegrid("reload", params);
}

//删除部门
function deleteDepartment(){
	var rows = $('#department_list').datagrid('getSelections');
	if (rows.length < 1) {
		$.messager.alert("提示", "请选择数据！", "info");
		return;
	}
	$.messager.confirm("确认消息", "是否删除此数据?", function(r) {
		if(r){
			var strIds = "";// id
			for (var i = 0; i < rows.length; i++) {
				strIds += rows[i].id + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			$.post(
				"./deleteDepartmentById",
				{ids:strIds},
				function(data){
					if(data =="true"){
						$.messager.alert("提示", "操作成功", "info");
//			        	$('#department_list').datagrid('reload');//重新加载table 
						sort();
					}
					if(data =="deleteDeptFalse"){
						$.messager.alert("提示", "所删部门中存在子部门,不能被删除!", "info");
						return;
					}
					if(data == "false"){
						$.messager.alert("提示", "操作失败!", "info");
						return;
					}
			})
		}
	})
}

//修改排序号
function sortNum(val,id){
	if(isNaN(val)){
		$.messager.alert('提醒', '请输入数字!', 'info');
		return false;
	}
	$.ajax({
		type : "POST",
		url : './categorySort',
		data :{'index':val,'id':id},
		dataType : "json",
		success : function(data) {
			if(data != null){
				
			}else {
				$.messager.alert('提醒', '数据传输失败!');
			}
		}
	});
}
//排序
function categorySort(){
	location.reload();
}

// 时间格式化
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};
function formatDatebox(value) {
	if (value == null || value == '') {
		return '';
	}
	var dt;
	if (value instanceof Date) {
		dt = value;
	} else {
		dt = new Date(value);
	}
	return dt.format("yyyy-MM-dd"); // 扩展的Date的format方法(上述插件实现)
};

function sort(){
	location.reload();
}

function departmentReset() {
	location.reload();
}