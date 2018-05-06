function appen(){
	var _value = "";
	var arr = [];
	var len =$("#roleDiv li .roleBox").size();
	for(var index = 0; index < len; index++){//创建一个数字数组
	arr[index] = index;
	}
	$.each(arr, function(i){//循环得到不同的id的值
		var idValue = $("#roleDiv li .roleBox").eq(i).attr("id");
		_value += idValue + ",";
	});
	_value = _value.substring(0,_value.length-1);
	var _text = $("#change").val();
	_text = _text.substring(0,_text.length-1);
	
	_value=_value.split(",");
	_text=_text.split(",");
	for(var i=0; i<_value.length; i++){
		for(var j =0; j<_text.length; j++){
			if(_value[i] == _text[j]){
				$("#"+_value[i]).attr("checked",true);
			}
		}
	}
}

function deleFUN(){
	$(".selectedList ul li").on("click","span",function(){
		$(this).parent().remove();
		var Rid = $(this).attr('value');
		$(".checkboxList ul li input").each(function(index){
			if($(this).attr('data-id') == Rid){
				$(this).attr("checked",false);
				$(this).parent().removeClass("active");
			}
		})
	})
}

function operationFun(){
	$(".checkboxList #roleDiv li").on({
		click:function(){
			var _this=this;
			var istrue = true;
			if($(_this).parent('li').hasClass("active")){
				return false;
			}else{
				$(_this).parent().addClass("active");
				$(_this).attr("checked",true);

				var data_id=$(_this).data("id");
				var labelCon=$(_this).next("label").text();
				$(".selectedList ul li").each(function(index){
					if($(this).find("span").attr('value') == data_id){
						istrue = false;
					}
				})
				if(istrue){
					var liTxt='<li><p>'+labelCon+'</p><span value='+data_id+'>X</span><input type="hidden" value="'+data_id+'" name="roleId" id="roleId"></li>'
					$(".selectedList ul").append(liTxt);	
				}
				deleFUN();
			}
		}
	},"input");
}

var basepath = $("#basepath").val();
function chan(){
	$(".selectedList ul").html('');
	var systemId = $("#systemId").val();
	var id = $("#id").val();
	$.ajax({
		type:'post',
		url:basepath+'/role/getRoleListBySystemId',
		data:{'systemId':systemId,'userid':id},
		dataType:'json',
		success:function(data){
			/*根据系统查询角色返回的集合*/
			var roleBySysIdList = data.roleBySysIdList;
			/*根据系统和用户查询角色集合返回的集合*/
			var roleBySysIdandUserList = data.roleBySysIdandUserList;
			$("#roleDiv").html('');
			
			var list ='';
			var list2 ='';
			var _arr = "";
			for(var i in roleBySysIdList){
				list+='<li>';
				list+='<input type="checkbox" class="roleBox" name="rolename" id="'+roleBySysIdList[i].id+'" data-id="'+roleBySysIdList[i].id+'"/>';
				list+='<label for="'+roleBySysIdList[i].id+'">'+roleBySysIdList[i].rolename+'</label>';
				list+='</li>';
				for(var j in roleBySysIdandUserList){
					
					if(roleBySysIdandUserList[j].id==roleBySysIdList[i].id){
						
						_arr += roleBySysIdandUserList[j].id+",";
						$("#change").val(_arr);
						list2+='<li><p>'+roleBySysIdandUserList[j].rolename+'</p><span value='+roleBySysIdandUserList[j].id+'>X</span><input type="hidden" value="'+roleBySysIdandUserList[j].id+'" name="roleId" class="roleBox"></li>'	
					}
					
				}
			}
			$(".selectedList ul").append(list2);
			$("#roleDiv").append(list);
			operationFun();
			deleFUN();
			appen();
		}
	})
}

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
function addAllocatBtn(){
	//判断是否有角色
	if(!$("#roleDiv li .roleBox").size()>0){
		$.messager.alert("提示", "请先添加角色！", "info");
		return;
	}
//	if(!$(".selectedList ul li").size()>0){
//		$.messager.alert("提示", "请选择角色！", "info");
//		return;
//	}
	
	var arr ="";
	$(".selectedList ul li input").each(function(){
		var _value= $(this).val();
		arr +=_value+",";
	})
	arr=arr.substring(0,arr.length-1);
	$("#roleIds").val(arr);
	var isValidatePass = $('#allocat').form('validate');
	if (isValidatePass){
		var userInfo = $('#allocat').serializeJson();
		$.ajax({
			type : "POST",
			url : './allocationRole',
			data : {
				'id':userInfo.id,
				'systemId':userInfo.systemId,
				'roleIds' :userInfo.roleIds,
			},
			success : function(data) {
				if(data.code == '010000'){
					$.messager.alert("提示", data.desc, "info");
					$('.allocateNoticBox').dialog('close');//关闭弹出窗
					$("#user_list").datagrid('reload');//重新加载table 
				}else{
					$.messager.alert("提示", data.desc, "info");
				}
			}
		});
	}
}
//取消按钮
function cancelAllocatBtn(){
	$("#allocateRolesWindow").window("close");
}