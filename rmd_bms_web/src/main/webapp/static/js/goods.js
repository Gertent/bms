	  
$(function() {
	$("#first").bind("click", function() {
		if (pageNum == 1) {alert("这是首页");return;}
		pageNum = 1;
		getParamAndSendReq();
	});
	$("#up").bind("click", function() {
		if (pageNum == 1) {alert("这是第一页");return;}
		pageNum -= 1;
		getParamAndSendReq();
	});
	$("#down").bind("click", function() {
		if (totalPage != null && pageNum == totalPage) {alert("这是最后一页");return;}
		pageNum += 1;
		getParamAndSendReq();
	});
	$("#last").bind("click", function() {
		if (pageNum == totalPage) {alert("这是末页");return;}
		pageNum = totalPage;
		getParamAndSendReq();
	});
	$("#search").bind("click", function() {
		pageNum = 1;
		getParamAndSendReq();
	});
	$("#checkAll").change(function() {
		if ($("#checkAll").attr("checked") == "checked") {
			$(":checkbox").attr("checked", true);
		} else {
			$(":checkbox").attr("checked", false);
		}
	});
	getParamAndSendReq();
});

function pageStyle(result){
	$("#box tr:gt(1)").remove();
	$("#ttr").show();
	var trow=$("#box tr").eq(1).clone();
	
	var rs = eval("("+result+")");
	
	for (var int = 0; int < rs.list.length; int++)  {
		trow=$("#box tr").eq(1).clone();
		trow.id="";
		initdata(trow,rs.list[int]);
		$("#box").append(trow);
	}
	$("#ttr").hide();
	$("#pageNum").html(rs.pageBean.pageNum);
	$("#totalPage").html(rs.pageBean.pages);
	totalPage=rs.pageBean.pages;
}