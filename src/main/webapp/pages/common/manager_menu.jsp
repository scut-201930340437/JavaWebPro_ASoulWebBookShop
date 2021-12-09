<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/5
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <form action="/managerBook" method="post" style="float: left;margin-right: 10px;">
        <input type="hidden" name="method" value="pageBooks"/>
        <input type="submit" value="图书管理" class="purchase"/>
    </form>
    <form action="/managerOrder" method="post" style="float: left;margin-right: 10px;">
        <input type="hidden" name="method" value="ListOrder">
        <input type="submit" value="订单管理" class="purchase"/>
    </form>
    <form action="index.jsp" method="post" style="float: left;margin-right: 10px;">
        <input type="submit" value="返回书城" class="purchase"/>
    </form>
    <form action="/download" method="get" style="float: left">
        <input type="submit" value="下载报表" class="purchase"/>
    </form>
</div>