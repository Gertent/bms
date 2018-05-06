var basepath = $("#basepath").val();
$(function () {

	var queryParams = {};
	$('#webApplicationListSearch').find('*').each(function () {
		queryParams[$(this).attr('name')] = $(this).val();
	});
	var pageNo = 1;
	var rows = 20;
	if (queryParams.pageNo && queryParams.pageNo != '') {
		pageNo = queryParams.pageNo;
		rows = queryParams.rows;
		status = queryParams.status;
	}

	$('#webApplication_list').datagrid({
		width: 'auto',
		height: 'auto',
		border: true,
		nowrap: false, // True 数据将在一行显示
		striped: true, // True 就把行条纹化
		collapsible: false, // True 可折叠
		url: './getWebApplicationList',
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
				field: 'sortNum', 
				title: '排序', 
				editor:'numberbox',
				width: '7%', 
				align: 'center',
				formatter: function (value, row, index) {
				return '<input type="text" class="bE5 pdL5" style="width: 25px;" value="'+value+'" onblur="sortNum(this.value,'+row.id+')"/>';
				}
			},{
				field: 'appName',
				title: '系统名称',
				width: 100,
			}, {
				field: 'identify',
				title: '系统标识',
				align:'center',
				width: 100
				
			}, {
				field: 'appNo',
				title: '系统编号',
				align:'center',
				width: 100
			}, {
				field: 'appUrl',
				title: '系统网址',
				align:'center',
				width: 100
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
function webApplicationSearchBox(pageNo) {
	var opts = $("#webApplication_list").datagrid('options');
	var pager = $("#webApplication_list").datagrid('getPager');
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
	$('#webApplicationListSearch').find('*').each(function () {
		queryParams[$(this).attr('name')] = $(this).val();
	});
	$("#all").addClass("active").siblings().removeClass("active");
	goods(queryParams);
}
function goods(params) {
	var dgId = "webApplication_list";
	if (params.page) {
		var opts = $('#' + dgId).datagrid('options');
		opts.pageNumber = params.page;
		opts.status = params.status;
	}
	$('#' + dgId).datagrid("reload", params);
}

//修改排序号
function sortNum(val,id){
	if(isNaN(val)){
		$.messager.alert('提醒', '请输入数字!','info');
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
//删除系统
function deleteWebApplication(){
	var rows = $('#webApplication_list').datagrid('getSelections');
	if(rows.length == 0){
		$.messager.alert('提醒', '请选择数据 !');
		return;
	}
	var strIds = "";
	for (var i = 0; i < rows.length; i++) {
		strIds += rows[i].id + ",";
	}
	strIds = strIds.substr(0, strIds.length - 1);
	$.messager.confirm("提示","是否删除此数据？",function(d){
		if(d){
			$.ajax({
				type:'post',
				url:'./deleteWebApplicationById',
				data:{'ids':strIds,status:status},
				dataType:'json',
				success:function(data){
					if(data == "true"){
						$.messager.alert("提示", "操作成功", "info");
			        	$('#webApplication_list').datagrid('reload');//重新加载table 
					}
					if(data == "userCountFalse"){
						$.messager.alert("提示", "此系统下已有员工不能删除!", "info");
						return;
					}	
					if(data == "false"){
						$.messager.alert("提示", "操作失败", "info");
						return;
					}
				}
			})
		}
	})
}

//重置按钮

function webApplicationReset(){
	window.location.href="./webApplicationPage";
}

function sort() {
	location.reload();
}