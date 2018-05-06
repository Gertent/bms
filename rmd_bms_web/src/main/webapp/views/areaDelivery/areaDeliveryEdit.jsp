<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>设置配送方式</title>
</head>
<body class="easyui-layout">
    <form id="edit" method="post">
        <input type="hidden" id="areaIds" name="areaIds" value="${areaIds}">
        <ul class="inBox">
            <li class="clearfix"><label for="" class="fl">配送方式：</label>
                <div class="fl checkboxBox" style="margin-top: 13px;">
                    <c:forEach items="${deliveryTypeList }" var="deliveryType">
                        <input type="checkbox" name="deliveryIds" value="${deliveryType.id}" />
                        <label>${deliveryType.type }</label>
                    </c:forEach>
                </div></li>
        </ul>

        <div class="edBox tac">
            <a href="javascript:;" class="btnG btnBl" onclick="save()">保存</a>
            <a href="javascript:;" class="btnG btnOg" onclick="cancel()">取消</a>
        </div>
    </form>
    <!-- <script type="text/javascript">
        $(function() {
            var deliveryId = $("#deliveryId").val();
            if (deliveryId != null && deliveryId.length > 0) {
                var deliveryIds = deliveryId.split(",");
                for (var i = 0; i < deliveryIds.length; i++) {
                    $("#" + deliveryIds[i]).attr("checked", true);
                }
            }
        });
    </script> -->
</body>
</html>