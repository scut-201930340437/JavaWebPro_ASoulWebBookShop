<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/11/19
  Time: 9:39
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>浏览记录</title>
    <%-- 静态包含 base标签、css样式、jQuery文件 --%>
    <%@ include file="/pages/common/head.jsp"%>
</head>
<body>
<%--静态包含，登录 成功之后的菜单 --%>
<%@ include file="/pages/common/clientHeader.jsp"%>

<div id="main" style="margin-top: 10px;margin-bottom: 0;height: 80%;">

    <c:if test="${empty requestScope.scanRecords}">
        <div style="text-align: center;color: #095bd7;font-size: 20px;">
            还没有浏览记录哦。。。
        </div>
    </c:if>
    <c:if test="${not empty requestScope.scanRecords}">
        <table>
            <tr>
                <td>时间</td>
                <td>书号</td>
                <td>书名</td>
            </tr>
            <c:forEach items="${requestScope.scanRecords}" var="entry">
                <tr>
                    <td>${entry.createTime}</td>
                    <td>${entry.bookID}</td>
                    <td>${entry.bookName}</td>
                    <td>
                        <form action="/scan" method="post" style="float: right;margin-right: 10px;">
                            <input type="hidden" name="method" value="deleteScan">
                            <input type="hidden" name="bookID" value="${entry.bookID}">
                            <input type="submit" value="删除" class="purchase">
                        </form>
                    </td>
                    <td>
                        <form action="/clientBook" method="post" style="float: none">
                            <input type="hidden" name="method" value="searchByID">
                            <input type="hidden" name="bookID" value="${entry.bookID}">
                            <input type="submit" value="查看详情" class="purchase">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <div class="cart_info">
            <span class="cart_span">共有<span class="b_count">${requestScope.scanCount}</span>条浏览记录</span>
            <span>
                <form action="/scan" method="post">
                    <input type="hidden" name="method" value="clearScan">
                    <input type="submit" value="清空记录" class="purchase">
                </form>
            </span>
        </div>
    </c:if>
</div>


<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp"%>
</body>
</html>
