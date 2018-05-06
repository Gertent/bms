var basepath = $("#basepath").val();
$(function () {

	var queryParams = {};
	$('#roleListSearch').find('*').each(function () {
		queryParams[$(this).attr('name')] = $(this).val();
	});
	var pageNo = 1;
	var rows = 20;
	if (queryParams.pageNo && queryParams.pageNo != '') {
		pageNo = queryParams.pageNo;
		rows = queryParams.rows;
		status = queryParams.status;
	}

	$('#role_list').datagrid({
		width: 'auto',
		height: 'auto',
		border: true,
		nowrap: false, // True 数据将在一行显示
		striped: true, // True 就把行条纹化
		collapsible: false, // True 可折叠
		url: './listRole',
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
		columns : [ [ {
			field : 'id',
			titel : 'id',
			width : 100,
			hidden : true
		},{
			field : 'rolename',
			title : '角色名称',
			width : 100,
			align : 'center'
		},{
			field: 'status', 
			title: '状态', 
			width: 100, 
			align: 'center',
			formatter: function (val, row, index) {
				if (val == 0) {
					return '<span style="color:red">锁定</span>';
				} else if (val = 1) {
					return '正常';
				}
			}
			
		 },{
			field : 'appName',
			title : '系统名称',
			width : 100,
			align : 'center'
		},{
			field : 'userCount',
			title : '员工数',
			width : 100,
			align : 'center'
		}] ],
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
function roleSearchBox(pageNo) {
	var opts = $("#role_list").datagrid('options');
	var pager = $("#role_list").datagrid('getPager');
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
	$('#roleListSearch').find('*').each(function () {
		queryParams[$(this).attr('name')] = $(this).val();
	});
	$("#all").addClass("active").siblings().removeClass("active");
	goods(queryParams);
}
function goods(params) {
	var dgId = "role_list";
	if (params.page) {
		var opts = $('#' + dgId).datagrid('options');
		opts.pageNumber = params.page;
		opts.status = params.status;
	}
	$('#' + dgId).datagrid("reload", params);
}

//重置按钮

function roleReset(){
	window.location.href="./rolePage";
}
//锁定解锁
function updateLockStatusById(status){
	var rows = $('#role_list').datagrid('getSelections');
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
		$.messager.confirm('提醒', '确定锁定？',function (d){
			if(d){
				$.ajax({
					type:'post',
                    async: false,
					url:'./updateLockStatusById',
					data:{'ids':strIds,'status':status},
					dataType:'json',
					success:function(data){
						if(data.code == '010000'){
							$.messager.alert("提示", data.desc, "info");
				        	$("#role_list").datagrid('reload');//重新加载table 
						}else{
							$.messager.alert("提示", data.desc, "info");
						}	
					}
				})
			}
		})
	}else{
		$.messager.confirm('提醒', '确定解锁？',function (d){
			if(d){
				$.ajax({
					type:'post',
                    async: false,
					url:'./updateLockStatusById',
					data:{'ids':strIds,'status':status},
					dataType:'json',
					success:function(data){
						if(data.code == '010000'){
							$.messager.alert("提示", data.desc, "info");
				        	$("#role_list").datagrid('reload');//重新加载table 
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
function deleteRole(){
	var rows = $('#role_list').datagrid('getSelections');
	if(rows.length == 0){
		$.messager.alert('提醒', '请选择数据 !');
		return;
	}
	var strIds = "";
	for (var i = 0; i < rows.length; i++) {
		if(rows[i].userCount >0){
			$.messager.alert('提醒', '此角色下还有用户 不能删除 !');
			return;
		}
		strIds += rows[i].id + ",";
	}
	strIds = strIds.substr(0, strIds.length - 1);
	$.messager.confirm('提醒', '确定删除此角色吗？',function (d){
		if(d){
			$.ajax({
				type:'post',
				url:'./deleteroleById',
				data:{'ids':strIds},
				dataType:'json',
				success:function(data){
					if(data == "true"){
						$.messager.alert("提示", "操作成功", "info");
			        	$("#role_list").datagrid('reload');//重新加载table 
					}else{
						$.messager.alert("提示", "操作失败", "info");
					}	
				}
			})
		}
	});
}
function sort() {
	location.reload();
}