var pageNum = 1;
var pageSize = 20;
var totalPage;
var back;
var param;

function pagecut(pageNum, pageSize, url, param) {
	$.ajax({
		type : 'post',
		async : false,
		url : url,
		data : "pageNo=" + pageNum + "&pageSize=" + pageSize + param,
		dataType : 'json',
		success : function(result) {
			back = result;
		}
	});
	return back;
}

function getLocalTime(date) {
	return new Date(date.time).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
}

function pageSizeChange() {
	var objS = document.getElementById("pageSize");
	pageSize = objS.options[objS.selectedIndex].value;
	pageStyle(pagecut(pageNum, pageSize, url, param));
}