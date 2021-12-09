<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/11/1
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书籍详情</title>
    <%@ include file="/pages/common/head.jsp"%>
    	<Script type="text/javascript" charset="UTF-8">
    		$(function () {
    			// 给加入购物车按钮绑定单击事件
    			$("#addCartBtn").click(function () {
    			    <c:if test="${not empty sessionScope.user}">
                    var bookName = $(this).attr("bookName");
                    var singlePrice=$(this).attr("singlePrice");
                    var bookID=$(this).attr("bookID");
                    // 发ajax请求，添加商品到购物车
                    $.ajax({
                        url:'/cart',
                        type:'post',
                        data:'method=AddCartItem&bookName='+bookName+'&singlePrice='+singlePrice+'&bookID='+bookID,
                        dataType:'text',
                        success:function (data){
                            alert(data);
                        }
                    })
                    </c:if>
                    <c:if test="${empty sessionScope.user}">
                    // var bookID=$(this).attr("bookID");
                    window.location.href="pages/user/login.jsp?bookID="+bookID;
                    </c:if>
    			});
    		});
    	</Script>
</head>
<body>
<%
    String path=request.getRequestURI();
    pageContext.setAttribute("path",path);
%>
<%@include file="/pages/common/clientHeader.jsp"%>

<div class="login_banner" style="height: 550px;margin-top: 20px;">
<%--    <c:if test="${not empty requestScope.errorMsg}">--%>
<%--        <div>--%>
<%--                ${requestScope.errorMsg}--%>
<%--        </div>--%>
<%--    </c:if>--%>
    <div id="l_content" style="text-align: center;margin-top: 8%;margin-left: 13%;">
        <div><img alt="" src="${requestScope.bookByID.img_path}" style="height: 250px;width: 300px;"></div>
        <span class="login_word" style="font-size: 50px">${requestScope.bookByID.name}</span>
    </div>
    <br/>

    <div id="content">
        <div class="login_form" style="height: 440px;margin-top: 15px">
            <div class="login_box">

                <div class="msg_cont">
                    <b></b>
                    <span class="tipMsg">
                            书籍信息:
                    </span>
                </div>
                <div class="form">
                    <form>
                        <input type="hidden" name="method" value="Update">
                        <label>书号：</label>
                        <input class="itxt" type="text"
                               autocomplete="off" tabindex="1" name="bookID" id="bookID"
                               value="${requestScope.bookByID.ID}" readonly/>
                        <br />
                        <br />
                        <label>书名：</label>
                        <input class="itxt" type="text"
                               autocomplete="off" tabindex="1" name="bookName" id="bookName"
                               value="${requestScope.bookByID.name}" readonly/>
                        <br />
                        <br />
                        <label>作者：</label>
                        <input class="itxt" type="text"
                               autocomplete="off" tabindex="1" name="bookAuthor" id="bookAuthor"
                               value="${requestScope.bookByID.author}" readonly/>
                        <br />
                        <br />
                        <label>价格：</label>
                        <input class="itxt" type="text"
                               autocomplete="off" tabindex="1" name="bookPrice" id="bookPrice"
                               value="￥${requestScope.bookByID.price}" readonly/>
                        <br />
                        <br />
                        <label>月销：</label>
                        <input class="itxt" type="text"
                               autocomplete="off" tabindex="1" name="bookSales" id="bookSales"
                               value="${requestScope.bookByID.sales}" readonly/>
                        <br />
                        <br />
                        <label>库存：</label>
                        <input class="itxt" type="text"
                               autocomplete="off" tabindex="1" name="bookStock" id="bookStock"
                               value="${requestScope.bookByID.stock}件" readonly/>
                        <br />
                    </form>
                    <c:if test="${empty sessionScope.user or sessionScope.user.root==0}">
                        <c:if test="${requestScope.bookByID.stock==0}">
                            <div style="text-align: center;color: deepskyblue">
                                对不起，这本书被卖光光了捏。。。
                            </div>
                            <div style="text-align: center;">
                                <a href="index.jsp" style="text-decoration: none;color: red;">去看下其他书吧！</a>
                            </div>
                        </c:if>
                        <c:if test="${requestScope.bookByID.stock>0}">
                            <div class="regisInLogin">
                                <form action="/clientOrder" method="post" style="float: left;margin-left:30px;margin-right: 10px;">
                                    <input type="hidden" name="method" value="createOrderByPurchase">
                                    <input type="hidden" name="bookID" value="${requestScope.bookByID.ID}">
                                    <input type="hidden" name="bookName" value="${requestScope.bookByID.name}">
                                    <input type="hidden" name="singlePrice" value="${requestScope.bookByID.price}">
                                    <input type="submit" value="购买" class="purchase" style="height: 50px;width: 100px;"/>
                                </form>
                                <button bookName="${requestScope.bookByID.name}" singlePrice="${requestScope.bookByID.price}" bookID="${requestScope.bookByID.ID}" id="addCartBtn" class="purchase" style="height: 50px;width: 100px;">加入购物车</button>
                            </div>
                        </c:if>
                    </c:if>
                </div>

            </div>
        </div>
    </div>
</div>
<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp"%>
</body>
</html>

