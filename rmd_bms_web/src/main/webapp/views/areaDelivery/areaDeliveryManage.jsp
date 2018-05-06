<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区划管理</title>
<link rel="stylesheet" href="<%=basePath%>/static/css/common/reset.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/static/css/goodsManage.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/static/css/changeNoInfo.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/static/css/addGoods.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/js/easyui-1.4.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/js/easyui-1.4.5/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/js/easyui-1.4.5/demo.css">
</head>
<body class="easyui-layout">
    <div class="goodsmBox smBox" data-options="region:'center'" style="border: 0">
        <div class="easyui-layout" data-options="fit:true">
            <div class="header" id="departmentListSearch" data-options="region:'north'" style="border: 0">
                <p class="navBar mgB16">
                    <a class="btnG btnBl" onclick="searchArea()">搜索</a> <a class="btnG btnBl" onclick="cancleArea()">重置</a>
                </p>
                <table class="gList">
                    <tr>
                        <td>
                            <label for="areaName" class="dib w77">区划名称：</label>
                            <input id="areaName" class="easyui-textbox" type="text" style="width: 184px; height: 28px; line-height: 28px; padding-left: 5px;" />
                        </td>
                        <td>
                            <label for="" class="dib tar">配送方式：</label>
                            <c:forEach items="${deliveryTypeList }" var="deliveryType">
                                <input type="checkbox" name="deliveryId" value="${deliveryType.id}"/>
                                <label>${deliveryType.type }</label>
                            </c:forEach>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="center" data-options="region:'center'" style="border: 0">
                <div class="easyui-layout" data-options="fit:true">
                    <div data-options="region:'north'" style="padding: 20px 30px 12px; border: 0">
                        <p class="cenBtn">
                            <a class="btnG btnBl" onclick="edit()">设置配送方式</a>
					    <a class="btnG btnBl" onclick="refreshCache()">刷新缓存</a> 
                        </p>
                    </div>
                    <!-- datagrid列表  -->
                    <div data-options="region:'center'">
                        <table id="areaDelivery"  class="easyui-treegrid areaDelivery"></table>
                    </div>
                </div>
                <!--编辑弹窗页面 -->
                <div id="editWindow" class="editNoticBox"></div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="<%=basePath%>/static/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/easyui-1.4.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/easyui-1.4.5/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/areaDelivery/areaDeliveryManage.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/areaDelivery/areaDeliveryEdit.js"></script>
    <script type="text/javascript">
    function refreshCache(){
    	<%--window.location.href="<%=basePath%>/area/cacheData";--%>
    	$.ajax({url:"<%=basePath%>/area/cacheData",async:false,success:function (data) {
            $.messager.alert("提示", data, "info");
        }});
    }
    </script>
</body>
</html>