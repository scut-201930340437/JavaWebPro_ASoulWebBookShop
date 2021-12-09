<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>购物车</title>
	<%-- 静态包含 base标签、css样式、jQuery文件 --%>
	<%@ include file="/pages/common/head.jsp"%>
</head>
<body>
<%--静态包含，登录 成功之后的菜单 --%>
<%@ include file="/pages/common/clientHeader.jsp"%>
<script type="text/javascript">
	$(function () {
		// 给 【删除】绑定单击事件
		$("a.deleteItem").click(function () {
			return confirm("您确定要删除订单号为【" + $(this).parent().parent().find("td:first").text() +"】的订单吗?")
		});
		// 给 【修改】绑定单击事件
		$("a.updateItem").click(function () {
			var root=${sessionScope.user.root};
			if(root==0){
				return confirm("确认收货？（进行此操作前请确保收到商品。）订单号:【" + $(this).parent().parent().find("td:first").text() +"】")
			}else{
				return confirm("确认发货？订单号:【" + $(this).parent().parent().find("td:first").text() +"】")
			}
		});
	});
</script>
<div id="main" style="margin-top: 10px;margin-bottom: 0;">
	<c:if test="${not empty requestScope.errorMsg}">
		<div style="text-align: center;color: red;">
			${requestScope.errorMsg}
		</div>
	</c:if>
	<div class="book_cond" style="margin-top: 10px;margin-bottom: 0;">
		<c:if test="${sessionScope.user.root==0}">
			<form action="/clientOrder" method="post">
				<input type="hidden" name="method" value="QueryByID">
				<div class="search">
					<input type="text" placeholder="搜索订单号" autocomplete="off"  name="orderID" class="search_box" style="height: 30px;width: 400px;"/>
					<input type="submit" value="搜索" class="search_btn"/>
				</div>
			</form>
		</c:if>
		<c:if test="${sessionScope.user.root==1}">
			<form action="/managerOrder" method="post">
				<input type="hidden" name="method" value="QueryByID">
				<div class="search">
					<input type="text" placeholder="搜索订单号" autocomplete="off"  name="orderID" class="search_box" style="height: 30px;width: 400px;"/>
					<input type="submit" value="搜索" class="search_btn"/>
				</div>
			</form>
		</c:if>
	</div>
	<c:if test="${sessionScope.user.root==0}">
		<div style="float: right;margin-right: 70px;margin-top: 0;margin-bottom: 0;">
			<form action="/clientOrder" method="post" style="float: left;margin-right: 5px;">
				<input type="hidden" name="method" value="QueryOrderByStatus">
				<input type="hidden" name="orderStatus" value="0">
				<input type="submit" value="未付款" class="purchase">
			</form>
			<form action="/clientOrder" method="post" style="float: left;margin-right: 5px;">
				<input type="hidden" name="method" value="QueryOrderByStatus">
				<input type="hidden" name="orderStatus" value="1">
				<input type="submit" value="未发货" class="purchase">
			</form>
			<form action="/clientOrder" method="post" style="float: left;margin-right: 5px;">
				<input type="hidden" name="method" value="QueryOrderByStatus">
				<input type="hidden" name="orderStatus" value="2">
				<input type="submit" value="已发货" class="purchase">
			</form>
			<form action="/clientOrder" method="post" style="float: left">
				<input type="hidden" name="method" value="QueryOrderByStatus">
				<input type="hidden" name="orderStatus" value="3">
				<input type="submit" value="已签收" class="purchase">
			</form>
		</div>
	</c:if>
	<c:if test="${sessionScope.user.root==1}">
		<div style="float: right;margin-right: 70px;margin-top: 0;margin-bottom: 0;">
			<form action="/managerOrder" method="post" style="float: left;margin-right: 5px;">
				<input type="hidden" name="method" value="QueryOrderByStatus">
				<input type="hidden" name="orderStatus" value="0">
				<input type="submit" value="未付款" class="purchase">
			</form>
			<form action="/managerOrder" method="post" style="float: left;margin-right: 5px;">
				<input type="hidden" name="method" value="QueryOrderByStatus">
				<input type="hidden" name="orderStatus" value="1">
				<input type="submit" value="未发货" class="purchase">
			</form>
			<form action="/managerOrder" method="post" style="float: left;margin-right: 5px;">
				<input type="hidden" name="method" value="QueryOrderByStatus">
				<input type="hidden" name="orderStatus" value="2">
				<input type="submit" value="已发货" class="purchase">
			</form>
			<form action="/managerOrder" method="post" style="float: left">
				<input type="hidden" name="method" value="QueryOrderByStatus">
				<input type="hidden" name="orderStatus" value="3">
				<input type="submit" value="已签收" class="purchase">
			</form>
		</div>
	</c:if>
	<c:if test="${not empty requestScope.msg}">
		<div style="color: red;text-align: center;">
			${requestScope.msg}
		</div>
	</c:if>
	<table style="margin-top: 50px;">
		<tr>
			<td>订单号</td>
			<td>创建时间</td>
			<td>商品数</td>
			<td>总金额</td>
			<td>用户id</td>
			<td>状态</td>
			<td colspan="2">操作</td>
		</tr>
		<c:if test="${empty requestScope.msg and empty requestScope.orders}">
			<%--如果订单为空的情况--%>
			<c:if test="${sessionScope.user.root==0}">
				<tr>
					<td colspan="7"><a href="index.jsp" style="text-decoration: none">订单空空如也。。。快去浏览商品吧！</a> </td>
				</tr>
			</c:if>
			<c:if test="${sessionScope.user.root==1}">
				<tr>
					<td colspan="7"><a href="index.jsp" style="text-decoration: none">未查找到相关订单</a> </td>
				</tr>
			</c:if>
		</c:if>
		<c:if test="${not empty requestScope.orders}">
			<c:forEach items="${requestScope.orders}" var="order">
				<tr>
					<td>${order.ID}</td>
					<td>${order.createTime}</td>
					<td>${order.totalCount}</td>
					<td>${order.orderPrice}</td>
					<td>${order.ownerID}</td>
					<c:if test="${order.status==0}">
						<td>未付款</td>
					</c:if>
					<c:if test="${order.status==1}">
						<td>未发货</td>
					</c:if>
					<c:if test="${order.status==2}">
						<td>已发货</td>
					</c:if>
                    <c:if test="${order.status==3}">
                        <td>已签收</td>
                    </c:if>
                    <%--管理员--%>
					<c:if test="${sessionScope.user.root==1 and order.status==1}">
						<td><a class="updateItem" href="/managerOrder?method=UpdateOrder&orderID=${order.ID}">发货</a></td>
					</c:if>
						<%--用户--%>
					<c:if test="${sessionScope.user.root==0}">
						<c:if test="${order.status==2}">
							<td><a class="updateItem" href="/clientOrder?method=UpdateOrder&orderID=${order.ID}">确认收货</a></td>
						</c:if>
						<c:if test="${order.status==0 or order.status==3}">
							<td><a class="deleteItem" href="/clientOrder?method=DeleteOrder&orderID=${order.ID}">删除</a></td>
						</c:if>
						<c:if test="${order.status==0}">
							<td><a class="checkItem" href="/clientOrder?method=PayOrder&orderID=${order.ID}">付款</a></td>
						</c:if>
					</c:if>
					<td><a class="detailItem" href="/clientOrder?method=DetailOrder&orderID=${order.ID}">查看详情</a></td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	<%--订单非空才输出的内容--%>
	<c:if test="${not empty requestScope.orders}">
		<div class="cart_info">
			<span class="cart_span">订单数<span class="b_count">${requestScope.orderCount}</span>件</span>
			<span class="cart_span">总金额<span class="b_price">${requestScope.sumPrice}</span>元</span>
		</div>
	</c:if>
	<c:if test="${not empty requestScope.isQ}">
		<c:if test="${sessionScope.user.root==1}">
			<div class="cart_span" style="float: right;margin-right: 400px;"><a href="/managerOrder?method=ListOrder"style="text-decoration: none;">返回</a></div>
		</c:if>
		<c:if test="${sessionScope.user.root==0}">
			<div class="cart_span"style="float: right;margin-right: 400px;"><a href="/clientOrder?method=ListMyOrder" style="text-decoration: none;">返回</a></div>
		</c:if>
	</c:if>

</div>
<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp"%>
</body>
</html>