<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>结算页面</title>

	<%-- 静态包含 base标签、css样式、jQuery文件 --%>
	<%@ include file="/pages/common/head.jsp"%>


	<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
</style>
</head>
<body>
<%--静态包含，登录 成功之后的菜单 --%>
<%@ include file="/pages/common/clientHeader.jsp"%>

	<div id="main" style="margin-bottom: 0;margin-top: 10px;">
		<h1>您的订单已结算，订单号为: ${param.orderID}</h1>
		<div style="display: flex;justify-content: center;">
			<form action="/mail" method="post" style="margin-right: 20px;">
			<input type="hidden" name="method" value="SendMail"/>
			<input type="hidden" name="orderID" value="${param.orderID}"/>
			<input type="submit" value="发送订单邮件到您的邮箱" style="color: red;"/>
			</form>
			<form action="index.jsp" method="post">
				<input type="submit" value="不用了" style="color: blue;"/>
			</form>
		</div>

	</div>


	<%--静态包含页脚内容--%>
	<%@include file="/pages/common/footer.jsp"%>


</body>
</html>