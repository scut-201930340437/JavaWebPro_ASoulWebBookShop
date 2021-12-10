<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/12/10
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>浏览日志</title>
    <%-- 静态包含 base标签、css样式、jQuery文件 --%>
    <%@ include file="/pages/common/head.jsp"%>
</head>
<body>
<div id="header">
    <img class="logo_img" alt="" src="../../static/img/logo.jpg" >
    <span class="wel_word">书籍管理系统</span>
    <%-- 静态包含 manager管理模块的菜单  --%>
    <%@include file="/pages/common/manager_menu.jsp"%>
</div>
<div id="main" style="margin-top: 10px;margin-bottom: 0;">
    <table>
        <tr>
            <td>用户id</td>
            <td>书籍id</td>
            <td>书名</td>
            <td>创建时间</td>
        </tr>
        <c:if test="${empty requestScope.scans}">
            <%--如果浏览记录空的情况--%>
            <tr>
                <td colspan="4"><a href="/pages/manager/manager.jsp" style="text-decoration: none">目前没有浏览记录。</a> </td>
            </tr>
        </c:if>
        <c:if test="${not empty requestScope.scans}">
            <%--如果浏览记录非空的情况--%>
            <c:forEach items="${requestScope.scans}" var="entry">
                <tr>
                    <td>${entry.userID}</td>
                    <td>${entry.bookID}</td>
                    <td><a href="/clientBook?method=searchByID&bookID=${entry.bookID}" style="text-decoration: none;">${entry.bookName}</a></td>
                    <td>${entry.createTime}</td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
    <%--如果购物车非空才输出页面的内容--%>
    <c:if test="${not empty requestScope.scans}">
        <div class="cart_info">
            <span class="cart_span">共有<span class="b_count">${requestScope.scanCount}</span>条浏览记录</span>
        </div>
    </c:if>

</div>
</body>
</html>
