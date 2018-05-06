$(function () {

	var queryParams = {};
	$('#operationSearch').find('*').each(function () {
		queryParams[$(this).attr('name')] = $(this).val();
	});
	var pageNo = 1;
	var rows = 20;
	if (queryParams.pageNo && queryParams.pageNo != '') {
		pageNo = queryParams.pageNo;
		rows = queryParams.rows;
		status = queryParams.status;
	}

	$('#operation_list').datagrid({
		width: 'auto',
		height: 'auto',
		border: true,
		nowrap: false, // True 数据将在一行显示
		striped: true, // True 就把行条纹化
		collapsible: false, // True 可折叠
		url: './operationLog',
		remoteSort: false, // 定义是否从服务器给数据排序
		singleSelect: false, // True 只能选择一行
		fit: true,
		toolbar: '#box',
		fitColumns: true,
		columns: [
			[{
				field: 'userName', 
				title: '账号', 
				width: '7%'
			},{
				field: 'reqIp',
				title: 'IP地址',
				width: 30,
			},{
				field: 'appName',
				title: '系统名称',
				width: 50,
			}, {
				field: 'reqUrl',
				title: '操作内容',
				align:'center',
				width: 100
				
			}, {
				field: 'reqBrowser',
				title: '客户端标识',
				align:'center',
				width: 100
			}, {
				field: 'reqDate',
				title: '操作时间',
				align:'center',
				width: 100,
				sortable:true,
				sorter:function(a,b){
					a = a.split('/');
					b = b.split('/');
					if (a[2] == b[2]){
						if (a[0] == b[0]){
							return (a[1]>b[1]?1:-1);
						} else {
							return (a[0]>b[0]?1:-1);
						}
					} else {
						return (a[2]>b[2]?1:-1);
					}
			},
				formatter:function (value, row, index) {
	                return '<a href="#" style="color:black">' +  formatterdate(value,null) + '</a>';
	            }
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
function operationSearchBox(pageNo) {
	var opts = $("#operation_list").datagrid('options');
	var pager = $("#operation_list").datagrid('getPager');
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
	$('#operationSearch').find('*').each(function () {
		queryParams[$(this).attr('name')] = $(this).val();
	});
	$("#all").addClass("active").siblings().removeClass("active");
	goods(queryParams);
}

function goods(params) {
	var dgId = "operation_list";
	if (params.page) {
		var opts = $('#' + dgId).datagrid('options');
		opts.pageNumber = params.page;
		opts.status = params.status;
	}
	$('#' + dgId).datagrid("reload", params);
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
//重置按钮

function operationReset(){
	location.reload();
}
