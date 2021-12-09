<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/11/17
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //获取当前页面的路径
    String curPath=request.getRequestURI();
    pageContext.setAttribute("curPath",curPath);
%>
<div id="header" style="margin-top: 0;margin-bottom: 0;height: 80px;">
    <div style="margin-top: 5px;margin-bottom: 0;float: left;">
        <img class="logo_img" alt="" src="static/img/logo.jpg" style="height: 80px;width: 180px;">
    </div>
    <div style="margin-top: 0;margin-bottom: 0;">
        <div style="margin-top: 0;margin-bottom: 0;height: 50px;">
            <span class="wel_word">一个魂儿书城</span>
        </div>

        <div style="margin-bottom: 0;float: revert;">
            <%--如果用户还没有登录，显示     【登录 和注册的菜单】 --%>
            <c:if test="${empty sessionScope.user and pageScope.curPath!='/pages/user/login.jsp' and pageScope.curPath!='/pages/user/regist.jsp'}">
                <form action="pages/user/login.jsp" method="post" style="float: left;margin-right: 10px;">
                    <input type="hidden" name="bookID" value="${requestScope.bookByID.ID}">
                    <input type="submit" value="登录" class="purchase"/>
                </form>
                <form action="pages/user/regist.jsp" method="post" style="float: left;margin-right: 10px;">
                    <input type="submit" value="注册" class="purchase"/>
                </form>
                <form action="pages/manager/manager.jsp" method="post" style="float: left;margin-right: 10px;">
                    <input type="submit" value="后台管理" class="purchase"/>
                </form>
            </c:if>
            <%--如果已经登录，则显示 登录 成功之后的用户信息。--%>
            <c:if test="${not empty sessionScope.user}">
                <span style="float: left;margin-top: 0;margin-bottom: 0;">欢迎<span class="um_span"><a href="pages/user/UpdateUser.jsp">${sessionScope.user.account}</a></span>光临一个魂儿书城</span>
                <!--普通用户-->
                <c:if test="${sessionScope.user.root==0}">
                    <c:if test="${pageScope.curPath!='/pages/order/order.jsp'}">
                        <form action="/clientOrder" method="post" style="float: left;margin-right: 10px;">
                            <input type="hidden" name="method" value="ListMyOrder"/>
                            <input type="submit" value="我的订单" class="purchase"/>
                        </form>
                    </c:if>
                    <c:if test="${pageScope.curPath!='/pages/cart/cart.jsp'}">
                        <form action="pages/cart/cart.jsp" method="post" style="float: left;margin-right: 10px;">
                            <input type="submit" value="购物车" class="purchase"/>
                        </form>
                    </c:if>
                    <c:if test="${pageScope.curPath!='/pages/scan/scanRecord.jsp'}">
                        <form action="/scan" method="post" style="float: left;margin-right: 10px;">
                            <input type="hidden" name="method" value="ListMyScan"/>
                            <input type="submit" value="浏览记录" class="purchase"/>
                        </form>
                    </c:if>
                </c:if>
                <!--管理员-->
                <c:if test="${sessionScope.user.root==1}">
                    <form action="pages/manager/manager.jsp" method="post" style="float: left;margin-right: 10px;">
                        <input type="submit" value="后台管理" class="purchase"/>
                    </form>
                </c:if>
                <form action="/logout" method="post" style="float: left;margin-right: 10px;">
                    <input type="submit" value="退出登录" class="purchase"/>
                </form>
            </c:if>
            <c:if test="${pageScope.path!='/pages/client/index.jsp'}">
                <form action="index.jsp" method="post" style="float: left;margin-right: 10px;">
                    <input type="submit" value="返回书城" class="purchase"/>
                </form>
            </c:if>
        </div>
    </div>
</div>
