<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>发送成功</title>

    <%-- 静态包含 base标签、css样式、jQuery文件 --%>
    <%@ include file="/pages/common/head.jsp"%>


    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }

    </style>
</head>
<body>
<%--静态包含，登录 成功之后的菜单 --%>
<%@ include file="/pages/common/clientHeader.jsp"%>

<div style="margin-bottom: 355px;">
    <h1 style="font-size: 50px;color: black;">发送成功! <a href="../../index.jsp" style="color: blue;text-decoration: none;text-align: center;font-size: 50px;">转到主页</a></h1>
</div>

<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp"%>

</body>
</html>
