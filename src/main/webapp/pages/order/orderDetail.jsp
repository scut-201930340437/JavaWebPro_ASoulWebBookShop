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
    <title>订单详情</title>
    <%-- 静态包含 base标签、css样式、jQuery文件 --%>
    <%@ include file="/pages/common/head.jsp"%>
</head>
<body>
<%--静态包含，登录 成功之后的菜单 --%>
<%@ include file="/pages/common/clientHeader.jsp"%>

<div id="main" style="margin-top: 10px;margin-bottom: 0;">
    <table>
        <tr>
            <td>书名</td>
            <td>数量</td>
            <td>金额</td>
            <td>所属订单号</td>
        </tr>
            <c:forEach items="${requestScope.orderItems}" var="entry">
                <tr>
                    <td><a href="/clientBook?method=searchByID&bookID=${entry.bookID}" style="text-decoration: none;">${entry.name}</a></td>
                    <td>${entry.count}</td>
                    <td>${entry.totalPrice}</td>
                    <td>${entry.orderID}</td>
                </tr>
            </c:forEach>
    </table>

        <div class="cart_info">
            <span class="cart_span">订单中共有<span class="b_count">${requestScope.totalCount}</span>件商品</span>
            <span class="cart_span">总金额<span class="b_price">${requestScope.orderPrice}</span>元</span>
            <span class="cart_span">订单状态:
                <c:if test="${requestScope.orderStatus==0}">
                    未付款
                </c:if>
                <c:if test="${requestScope.orderStatus==1}">
                    未发货
                </c:if>
                <c:if test="${requestScope.orderStatus==2}">
                    已发货
                </c:if>
                <c:if test="${requestScope.orderStatus==3}">
                    已签收
                </c:if>
            </span>
            <%--普通用户页面的内容--%>
            <c:if test="${sessionScope.user.root==0}">
            <span class="cart_span"><a class="return" href="/clientOrder?method=ListMyOrder">返回</a></span>
            </c:if>
            <%--管理员页面的内容--%>
            <c:if test="${sessionScope.user.root==1}">
                <span class="cart_span"><a class="return" href="/managerOrder?method=ListOrder">返回</a></span>
            </c:if>
        </div>
</div>
<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp"%>
</body>
</html>
