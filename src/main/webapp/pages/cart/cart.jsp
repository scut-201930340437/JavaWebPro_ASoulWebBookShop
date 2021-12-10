<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
	<%-- 静态包含 base标签、css样式、jQuery文件 --%>
	<%@ include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function () {
			// 给 【删除】绑定单击事件
			$("a.deleteItem").click(function () {
				return confirm("你确定要删除【" + $(this).parent().parent().find("td:first").text() +"】吗?")
			});
			// 给清空购物车绑定单击事件
			$("#clearCart").click(function () {
				return confirm("你确定要清空购物车吗?");
			})
			// 给输入框绑定 onchange内容发生改变事件
			$(".updateCount").change(function () {
				// 获取商品名称
				var name = $(this).parent().parent().find("td:first").text();
				var id = $(this).attr('ID');
				// 获取商品数量
				var countR = /^\+?[1-9][0-9]*$/;
				var flag=countR.test(this.value);
				// var count = this.value;
				if(flag){
					if ( confirm("你确定要将【" + name + "】商品修改数量为:" + this.value + " 件吗?") ) {
						//发起请求。给服务器保存修改
						var ip=$(this).attr('basePath');
						location.href = ip+"cart?method=UpdateCount&count="+this.value+"&ID="+id;
					} else {
						// defaultValue属性是表单项Dom对象的属性。它表示默认的value属性值。
						this.value = this.defaultValue;
					}
				} else {
					alert('商品数量至少为1件且必须为整数!');
					this.value = this.defaultValue;
				}
			});
		});
	</script>
</head>
<body>
<%--静态包含，登录 成功之后的菜单 --%>
<%@ include file="/pages/common/clientHeader.jsp"%>
	<div id="main" style="margin-top: 10px;margin-bottom: 0;">
		<table>
			<tr>
				<td>书名</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>		
			<c:if test="${empty sessionScope.cart.items}">
				<%--如果购物车空的情况--%>
				<tr>
					<td colspan="5"><a href="index.jsp" style="text-decoration: none">购物车里还什么都没有呢，快去浏览商品吧！</a> </td>
				</tr>
			</c:if>
			<c:if test="${not empty sessionScope.cart.items}">
				<%--如果购物车非空的情况--%>
				<c:forEach items="${sessionScope.cart.items}" var="entry">
					<tr>
						<!--点击书名可以跳转至详情页面-->
						<td><a href="/clientBook?method=searchByID&bookID=${entry.value.bookID}" style="text-decoration: none;">${entry.value.name}</a></td>
						<td>
							<input class="updateCount" style="width: 80px;"
								   ID="${entry.value.ID}"
								   type="text" value="${entry.value.count}" basePath="${pageScope.basePath}"/>
						</td>
						<td>${entry.value.singlePrice}</td>
						<td>${entry.value.totalPrice}</td>
						<td><a class="deleteItem" href="/cart?method=DeleteItem&ID=${entry.value.ID}">删除</a></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		<%--如果购物车非空才输出页面的内容--%>
		<c:if test="${not empty sessionScope.cart.items}">
			<div class="cart_info">
				<span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
				<span class="cart_span">总金额<span class="b_price">${sessionScope.cart.allItemPrice}</span>元</span>
				<span class="cart_span"><a id="clearCart" href="/cart?method=ClearCart" style="text-decoration: none">清空购物车</a></span>
				<span class="cart_span"><a id="payCart" href="/clientOrder?method=createOrder" style="text-decoration: none">结算</a></span>
			</div>
		</c:if>
	
	</div>


	<%--静态包含页脚内容--%>
	<%@include file="/pages/common/footer.jsp"%>


</body>
</html>