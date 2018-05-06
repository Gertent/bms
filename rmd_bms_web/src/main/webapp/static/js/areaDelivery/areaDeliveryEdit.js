function save() {
    var areaIds = $("#areaIds").val();
    var deliveryId = "";
    $('input[name="deliveryIds"]:checked').each(function() {
        deliveryId += $(this).val() + ",";
    });
    if (deliveryId.length > 1) {
        deliveryId = deliveryId.substring(0, deliveryId.length-1);
    }
    var params = {
        "areaIds" : areaIds,
        "deliveryId" : deliveryId
    };
    $.ajax({
        type : "POST",
        url : './saveEdit',
        contentType : 'application/json; charset=utf-8',
        data : JSON.stringify(params),
        success : function(data) {
            if (data == "true") {
                $.messager.alert("提示", "操作成功 ", "info");
                $('.editNoticBox').dialog('close');// 关闭弹出窗
                history.go(0);
//                sort();
//                $('#areaDelivery').datagrid('reload');
//                window.location.href="./areaDeliveryPage";
            } else {
                $.messager.alert("提示", "操作失败", "info");
                return;
            }
        }
    });
}

function sort(){
	location.reload();
}
// 取消按钮
function cancel() {
    $("#editWindow").window("close");
}