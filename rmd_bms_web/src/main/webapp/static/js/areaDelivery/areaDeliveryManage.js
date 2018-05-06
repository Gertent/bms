$(function() {
    $('#areaDelivery').treegrid({
        width : 'auto',
        height : 'auto',
        url : './listAreaDelivery',
        method : 'post',
        idField : 'areaId',
        treeField : 'areaName',
        nowrap : false,// True 数据将在一行显示
        striped : true,// True 就把行条纹化
        collapsible : true,// True 可折叠
        remoteSort : false,// 定义是否从服务器给数据排序
        singleSelect : false,// True 只能选择一行
        rownumbers : false,
        columns : [ [ {
            field : 'areaId',
            titel : '区域id',
            width : "6%",
            align : "center",
            checkbox : true
        }, {
            field : 'areaCode',
            titel : '区域编码',
            hidden : true
        }, {
            field : 'parentCode',
            titel : '父级区域编码',
            hidden : true
        }, {
            title : '区划名称',
            field : 'areaName',
            width : "50%",
            align : "left",
            formatter : function(value, row, index) {
                return '<a href="#" onclick="scanChildren(' + row + ')">' + value + '</a>';
            }
        }, {
            title : '配送方式',
            field : 'type',
            width : "44%",
            align : "center",
            formatter : function(value, row, index) {
                var types = row.type;
                if (types != null && types.indexOf(",") > 0) {
                    types = types.replace(new RegExp(/,/g), "/");
                }
                return types;
            }
        }] ],
        onBeforeExpand:scanChildren
    });
});
function scanChildren(row) {
    var queryParams = {"parentCode":row.areaCode};
    $('#areaDelivery').treegrid("options").queryParams = queryParams;
}

function edit() {
    var rows = $('#areaDelivery').datagrid('getSelections');
    if (rows == "") {
        $.messager.alert("提示", "请选择数据", "info");
        return;
    }
    var areaIds = "";
    for (var i=0; i<rows.length; i++) {
        if (areaIds.length > 0) {
            areaIds += ",";
        }
        areaIds += rows[i].areaId;
    }
    $('.editNoticBox').window({
        href : "./areaDeliveryEditPage?areaIds=" + areaIds,
        width : 400,
        height : 235,
        modal : true,
        shadow : false,
        collapsible : false,
        minimizable : false,
        maximizable : false,
        minimizable : false,
        maximizable : false,
        title : "设置配送方式"
    });
}

function cancleArea() {
//    $("#AREANAME").VAL("");
//    $('INPUT[NAME="DELIVERYID"]:CHECKED').EACH(FUNCTION() {
//        $(THIS).ATTR("CHECKED", TRUE);
//    });
	alert("a");
//	location.reload();
}

function searchArea() {
    var areaName = $("#areaName").val();
    var deliveryId = "";
    $('input[name="deliveryId"]:checked').each(function() {
        deliveryId += $(this).val() + ",";
    });
    if (deliveryId.length > 1) {
        deliveryId = deliveryId.substring(0, deliveryId.length-1);
    }
    var params = {
        "areaName" : areaName,
        "deliveryId" : deliveryId
    };
    if (areaName == "" && deliveryId == "") {
        $('#areaDelivery').treegrid("options").url = "./listAreaDelivery";
    } else {
        $('#areaDelivery').treegrid("options").url = "./searchAreaDelivery";
    }
    $('#areaDelivery').treegrid("reload", params);
}

function search(areaName) {
    var searchedRow = null;
    var data = $('#areaDelivery').treegrid("getData");
    $.easyui.forEach(data, true, function(row) {
        if (row.areaName == areaName) {
            searchedRow = row;
            return false;
        }
    });
    return searchedRow;
}
