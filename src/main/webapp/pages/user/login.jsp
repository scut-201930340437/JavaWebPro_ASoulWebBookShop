<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>一个魂儿会员登录页面</title>

	<%-- 静态包含 base标签、css样式、jQuery文件 --%>
	<%@ include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function (){
			$("#account").click(function (){
				$("span.errorMsg").text("")
			});
			$("#password").click(function (){
				$("span.errorMsg").text("")
			});
		});
	</script>
</head>
<body>
<%--静态包含，登录 成功之后的菜单 --%>
<%@ include file="/pages/common/clientHeader.jsp"%>
		
			<div class="login_banner" style="height: 80%;">
			
				<div id="l_content">
					<span class="login_word">欢迎登录</span>
				</div>
				<br/>

				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>一个魂儿会员</h1>
							</div>
							<div class="msg_cont">
								<b></b>
								<span class="TipMsg">
									请输入用户名和密码
								</span>
								&nbsp;&nbsp;&nbsp;
								<span class="errorMsg">
									${requestScope.msg}
								</span>
							</div>
							<div class="form">
								<form action="/login" method="post">
	                                <input type="hidden" name="bookID" value="${param.bookID}"/>
									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名"
										   autocomplete="off" tabindex="1" name="account" id="account"
										   value="${requestScope.account}" />
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码"
										   autocomplete="off" tabindex="1" name="password" id="password"
									       value="${requestScope.password}"/>
									<br />
									<br />
									<input type="submit" value="登录" id="sub_btn" />
									<br/>
								</form>
								<div class="regisInLogin">
									<a href="pages/user/regist.jsp" id="registHref">首次使用？点我注册!</a>
								</div>
							</div>
							
						</div>
					</div>
				</div>
			</div>

		<%--静态包含页脚内容--%>
		<%@include file="/pages/common/footer.jsp"%>
</body>
</html>