//@ sourceURL=image.js
var basepath = $("#basepath").val();
//上传缩略图
$('input[id=thumbnail]').localResizeIMG({
	width: 500,
	quality: 0.8,
	success: function(result) {
		var img = new Image();
		img.src = result.base64;
		$("#imgthumbnail").attr("src",img.src);
		var fileData = result.blob;
		var form=document.forms[0];
		var formData = new FormData(form);   //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数
		formData.append("file",fileData);  //append函数的第一个参数是后台获取数据的参数名,和html标签的input的name属性功能相同
		//ajax 提交form
		$.ajax({
			url : basepath+"/base/upload/uploadPic",
			type : "POST",
			data : formData,
			dataType:"json",
			processData : false,		 // 告诉jQuery不要去处理发送的数据
			contentType : false,		// 告诉jQuery不要去设置Content-Type请求头
			success:function(data){
				$("#application_img").val(data.url);
			},
		});
	}
});
function delthumbnail(){
	$("#imgthumbnail").attr("src","");
	$("#application_img").val('');
}


