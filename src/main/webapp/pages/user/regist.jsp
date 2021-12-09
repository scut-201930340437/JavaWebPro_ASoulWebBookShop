<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>一个魂儿会员注册页面</title>
		<%-- 静态包含 base标签、css样式、jQuery文件 --%>
		<%@ include file="/pages/common/head.jsp"%>

		<script type="text/javascript">
			// 页面加载完成之后
			$(function () {
				var userExist;
				$("#account").blur(function () {
					var account = this.value;
					//2 创建正则表达式对象
					var accountPatt = /^\w{5,12}$/;
					//3 使用test方法验证
					if (!accountPatt.test(account)) {
						//4 提示用户结果
						$("span.errorMsg").text("用户名不合法！");
						return false;
					}
					//ajax请求，查询数据库中是否已存在该用户名
					$.ajax({
						url:'/user',
						type:'post',
						data:'method=AjaxExistsAccount&Account='+account,
						dataType:'text',
						success:function (data){
							if (data=='1') {
								$("span.errorMsg").text("用户名已存在！");
								userExist = true;
							} else {
								$("span.errorMsg").text("用户名可用！");
								userExist = false;
							}
						}
					})
				});

				// 给注册绑定单击事件
				$("#sub_btn").click(function () {
					//如果用户名已存在，不发送请求
					if(userExist){
						return false;
					}
					// 验证用户名：必须由字母，数字下划线组成，并且长度为5到12位
					//1 获取用户名输入框里的内容
					var accountText = $("#account").val();
					//2 创建正则表达式对象
					var accountPatt = /^\w{5,12}$/;
					//3 使用test方法验证
					if (!accountPatt.test(accountText)) {
						//4 提示用户结果
						$("span.errorMsg").text("用户名不合法！");
						return false;
					}
					// 验证密码：必须由字母，数字下划线组成，并且长度为5到12位
					//1 获取用户名输入框里的内容
					var passwordText = $("#password").val();
					//2 创建正则表达式对象
					var passwordPatt = /^\w{5,12}$/;
					//3 使用test方法验证
					if (!passwordPatt.test(passwordText)) {
						//4 提示用户结果
						$("span.errorMsg").text("密码不合法！");
						return false;
					}
					// 验证确认密码：和密码相同
					//1 获取确认密码内容
					var repwdText = $("#repwd").val();
					//2 和密码相比较
					if (repwdText != passwordText) {
						//3 提示用户
						$("span.errorMsg").text("确认密码和密码不一致！");
						return false;
					}
					// 邮箱验证：xxxxx@xxx.com
					//1 获取邮箱里的内容
					var emailText = $("#email").val();
					//2 创建正则表达式对象
					var emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
					//3 使用test方法验证是否合法
					if (!emailPatt.test(emailText)) {
						//4 提示用户
						$("span.errorMsg").text("邮箱格式不合法！");
						return false;
					}
					// 去掉错误信息
					$("span.errorMsg").text("");
				});
			});
		</script>
	</head>
	<body>
	<%--静态包含，登录 成功之后的菜单 --%>
	<%@ include file="/pages/common/clientHeader.jsp"%>
			<div class="login_banner">
				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>
				<div id="content">
					<div class="regist_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册一个魂儿会员</h1>
								<span class="errorMsg">
									${ requestScope.msg }
								</span>
							</div>
							<div class="form">
								<form action="/user" method="post">
									<input type="hidden" name="method" value="Register">
									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名"
										   value="${requestScope.account}"
										   autocomplete="off" tabindex="1" name="Account" id="account" />
									<div style="font-size: 17px;color: crimson;">用户名要求:长度为5-12个字符。</div>

									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码"
										   autocomplete="off" tabindex="1" name="Password" id="password" />
									<div style="font-size: 17px;color: crimson;">密码要求:长度为5-12个字符。</div>

									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码"
										   autocomplete="off" tabindex="1" name="repwd" id="repwd" />

									<div style="margin-bottom: 10px;margin-top: 0;"></div>
									<label>电子邮箱：</label>
									<input class="itxt" type="text" placeholder="请输入邮箱地址"
										   value="${requestScope.e_mail}"
										   autocomplete="off" tabindex="1" name="E_mail" id="email" />
									<div style="margin-bottom: 10px;margin-top: 0;"></div>
									<input type="submit" value="注册" id="sub_btn" />
								</form>
							</div>

						</div>
					</div>
				</div>
			</div>

		<%--静态包含页脚内容--%>
		<%@include file="/pages/common/footer.jsp"%>
	</body>
</html>