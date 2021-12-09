<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/11/1
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改信息</title>
    <%@ include file="/pages/common/head.jsp"%>
</head>
<body>
    <div id="login_header">
        <img alt="" src="static/img/logo.jpg"  />
    </div>
    <div class="login_banner">

        <div id="l_content">
            <span class="login_word">修改个人信息</span>
        </div>
        <br/>

        <div id="content">
            <div class="login_form" style="height: 350px;">
                <div class="login_box">

                    <div class="msg_cont">
                        <b></b>
                        <span class="tipMsg">
                            用户信息:
                        </span>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <span class="errorMsg">
                            ${requestScope.msg}
                        </span>
                    </div>
                    <div class="form">
                        <form action="/user" method="post">
                            <input type="hidden" name="method" value="Update">
                            <label>ID：</label>&nbsp;&nbsp;&nbsp;
                            <input class="itxt" type="text"
                                   autocomplete="off" tabindex="1" name="id" id="ID"
                                   value="${sessionScope.user.ID}" readonly/>
                            <br />
                            <br />
                            <label>账号：</label>
                            <input class="itxt" type="text"
                                   autocomplete="off" tabindex="1" name="account" id="account"
                                   value="${sessionScope.user.account}" readonly/>
                            <br />
                            <br />
                            <label>昵称：</label>
                            <input class="itxt" type="text"
                                   autocomplete="off" tabindex="1" name="newName" id="newName"
                                   value="${sessionScope.user.name}"/>
                            <br />
                            <br />
                            <label>电话：</label>
                            <input class="itxt" type="text"
                                   autocomplete="off" tabindex="1" name="newTele" id="newTele"
                                   value="${sessionScope.user.telephone}"/>
                            <br />
                            <br />
                            <input type="submit" value="修改" id="sub_btn" />
                            <br/>
                            <div class="regisInLogin">
                                <a href="../../index.jsp" id="registHref">返回</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
