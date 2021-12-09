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
    <script type="text/javascript">
        $(function (){
            //给修改绑定点击事件
            $("#sub_btn").click(function (){
                // 邮箱验证：xxxxx@xxx.com
                //1 获取邮箱里的内容
                var emailText = $("#newEmail").val();
                //2 创建正则表达式对象
                var emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
                //3 使用test方法验证是否合法
                if (!emailPatt.test(emailText)) {
                    //4 提示用户
                    $("span.errorMsg").text("邮箱格式不合法！");
                    return false;
                }

                //电话验证，长度为11的纯数字
                var TeleText=$("#newTele").val();
                //正则表达式
                var passwordPatt = /^\w{11,11}$/;
                if (!passwordPatt.test(TeleText)) {
                    //4 提示用户结果
                    $("span.errorMsg").text("电话不合法！");
                    return false;
                }
                if(isNaN(TeleText)){
                    //4 提示用户结果
                    $("span.errorMsg").text("电话不合法！");
                    return false;
                }
                // 去掉错误信息
                $("span.errorMsg").text("");
            })
        })
    </script>
</head>
<body>
    <div id="login_header">
        <img alt="" src="static/img/logo.jpg"  />
    </div>
    <div class="login_banner">
        <div id="l_content" style="margin-left: 200px;">
            <span class="login_word">修改个人信息</span>
        </div>
        <br/>
        <div id="content">
            <div class="login_form" style="height: 400px;margin-top: 10px;">
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
                            <label>邮箱：</label>
                            <input class="itxt" type="text"
                                   autocomplete="off" tabindex="1" name="newEmail" id="newEmail"
                                   value="${sessionScope.user.e_mail}"/>
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
