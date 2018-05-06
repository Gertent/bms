var basepath = $("#basepath").val();
$(function () {

	var queryParams = {};
	$('#userListSearch').find('*').each(function () {
		queryParams[$(this).attr('name')] = $(this).val();
	});
	var pageNo = 1;
	var rows = 20;
	if (queryParams.pageNo && queryParams.pageNo != '') {
		pageNo = queryParams.pageNo;
		rows = queryParams.rows;
		status = queryParams.status;
	}

	$('#user_list').datagrid({
		width: 'auto',
		height: 'auto',
		border: true,
		nowrap: true, // True 数据将在一行显示
		striped: true, // True 就把行条纹化
		collapsible: false, // True 可折叠
		url: './getUserList',
		remoteSort: false, // 定义是否从服务器给数据排序
		singleSelect: false, // True 只能选择一行
		fit: true,
		toolbar: '#box',
		fitColumns: true,
		frozenColumns: [
			[{
				field: 'ck',
				checkbox: true
			}, ]
		],
		columns: [
			[{
				field: 'id',
				titel: 'id',
				hidden: true
			}, {
				field: 'loginname',
				title: '账号',
				width: 100,
			}, {
				field: 'jobNum',
				title: '工号',
				align:'center',
				width: 100
				
			}, {
				field: 'realname',
				title: '姓名	',
				align:'center',
				width: 100
				
			}, {
				field: 'status',
				title: '状态',
				align:'center',
				width: 100,
				formatter: function (val, row, index) {
					if (val == "0") {
						return '<span style="color:red">锁定</span>';
					} else if (val = '1') {
						return "正常";
					}
				}
			}, {
				field: 'deptNames',
				title: '部门',
				align:'center',
				width: 100
			}, {
				field: 'roleName',
				title: '角色',
				align:'center',
				width: 100
			},{
				field: 'email',
				title: '邮箱',
				align:'center',
				width: 100
			}, {
				field: 'mobile',
				title: '手机号',
				width: 360
			}, {
				field: 'note',
				title: '备注',
				width: 360
			}, 
		] ],
		pagination: true, // 显示分页栏。
		rownumbers: true, // 显示行号的列。
		pageNumber: pageNo,
		pageSize: rows,
		mark:queryParams,
		queryParams: queryParams
	});
	$('.cenList .list li').click(function () {
		var index = $(this).index();
		$(this).addClass("active").siblings().removeClass("active");
	});
});


//模糊查询
function userSearchBox(pageNo) {
	var opts = $("#user_list").datagrid('options');
	var pager = $("#user_list").datagrid('getPager');
	opts.pageNumber = 1;
	opts.pageSize = opts.pageSize;
	pager.pagination('refresh', {
		pageNumber: 1,
		pageSize: opts.pageSize
	});

	var queryParams = {};
	if (pageNo) {
		queryParams.page = pageNo;
	}
	$('#userListSearch').find('*').each(function () {
		queryParams[$(this).attr('name')] = $(this).val();
	});
	$("#all").addClass("active").siblings().removeClass("active");
	goods(queryParams);
}

function goods(params) {
	var dgId = "user_list";
	if (params.page) {
		var opts = $('#' + dgId).datagrid('options');
		opts.pageNumber = params.page;
		opts.status = params.status;
	}
	$('#' + dgId).datagrid("reload", params);
}

//重置按钮

function userReset(){
	window.location.href="./userPage";
}
//锁定解锁
function lockStatus(status){
	var rows = $('#user_list').datagrid('getSelections');
	if(rows.length == 0){
		$.messager.alert('提醒', '请选择数据 !');
		return;
	}
	var strIds = "";
	for (var i = 0; i < rows.length; i++) {
		strIds += rows[i].id + ",";
	}
	strIds = strIds.substr(0, strIds.length - 1);
	if(status == 0){
		$.messager.confirm("系统提示", "确定锁定？", function(d){
			if(d){
				$.ajax({
					type:'post',
					url:'./updateLockStatusById',
					data:{'ids':strIds,status:status},
					dataType:'json',
					success:function(data){
						if(data.code == '010000'){
							$.messager.alert("提示", data.desc, "info");
				        	$("#user_list").datagrid('reload');//重新加载table 
						}else{
							$.messager.alert("提示", data.desc, "info");
						}	
					}
				})
			}
		})
	}else{
		$.messager.confirm("系统提示", "确定解锁？", function(d){
			if(d){
				$.ajax({
					type:'post',
					url:'./updateLockStatusById',
					data:{'ids':strIds,status:status},
					dataType:'json',
					success:function(data){
						if(data.code == '010000'){
							$.messager.alert("提示", data.desc, "info");
				        	$("#user_list").datagrid('reload');//重新加载table 
						}else{
							$.messager.alert("提示", data.desc, "info");
						}	
					}
				})
			}
		})
	}
	
}
//删除
function deleteUser(){
	var rows = $('#user_list').datagrid('getSelections');
	if(rows.length == 0){
		$.messager.alert('提醒', '请选择数据 !');
		return;
	}
	var strIds = "";
	for (var i = 0; i < rows.length; i++) {
		strIds += rows[i].id + ",";
	}
	strIds = strIds.substr(0, strIds.length - 1);
	$.messager.confirm("系统提示", "删除后不可恢复，确认删除？", function(d){
		if(d){
			$.ajax({
				type:'post',
				url:'./deleteUserById',
				data:{'ids':strIds,status:status},
				dataType:'json',
				success:function(data){
					if(data.code == '010000'){
						$.messager.alert("提示", data.desc, "info");
			        	$("#user_list").datagrid('reload');//重新加载table 
					}else{
						$.messager.alert("提示", data.desc, "info");
					}	
				}
			})
		}
	})
	
}
function formatterdate(val, row) {
 	if (val != null) {
 		 var date = new Date(val);
 		 var year = date.getFullYear().toString();
 		 var month = (date.getMonth() + 1);
 		 var day = date.getDate().toString();
 		 var hour = date.getHours().toString();
 		 var minutes = date.getMinutes().toString();
 		 var seconds = date.getSeconds().toString();
 		 if (month < 10) {
 	       month = "0" + month;
 		 }
 		 if (day < 10) {
 		   day = "0" + day;
 		 }
 		 if (hour < 10) {
 		   hour = "0" + hour;
 		  }
 		 if (minutes < 10) {
 		   minutes = "0" + minutes;
 		 }
 		 if (seconds < 10) {
 		 seconds = "0" + seconds;
 		 }
 		return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
 	} 
 } 

$('#selectUp').combotree({
	
	url: basepath + '/department/listDepartment',
	editable: false,
	onSelect: function (row) {
		console.log(row);
			$('#selectUp').combotree('setValue', row.deptname);
			$("#parentId").val(row.id);
		},
		formatter: function (row) {
			return '<span class="item-text">' + row.deptname + '</span>';
		}
});
function sort() {
	location.reload();
}