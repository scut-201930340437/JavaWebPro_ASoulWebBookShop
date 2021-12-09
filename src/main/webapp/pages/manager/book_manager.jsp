<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>图书管理</title>
	<%-- 静态包含 base标签、css样式、jQuery文件 --%>
	<%@ include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function () {
			// 给删除的a标签绑定单击事件，用于删除的确认提示操作
			$(".deleteBtn").click(function () {
				// 在事件的function函数中，有一个this对象。这个this对象，是当前正在响应事件的dom对象。
				return confirm("你确定要删除《" + $(this).parent().parent().parent().find("td:first").text() + "》?");
				// return false// 阻止元素的默认行为，不提交请求
			});
		});
	</script>
</head>
<body>
	<div id="header">
			<img class="logo_img" alt="" src="../../static/img/logo.jpg" >
			<span class="wel_word">书籍管理系统</span>
		<%-- 静态包含 manager管理模块的菜单  --%>
		<%@include file="/pages/common/manager_menu.jsp"%>
	</div>
	
	<div id="main" style="height: 570px;">
		<div class="book_cond" style="height: 5%;">
            		<form action="/clientBook" method="post">
                		<input type="hidden" name="method" value="searchByName">
				<input type="hidden" name="isAdmin" value="1">
                		<div class="search">
                    			<input type="text" placeholder="搜索书名" autocomplete="off"  name="bookName" value="${requestScope.bookName}" class="search_box" style="height: 30px;width: 400px;"/>
                    			<input type="submit" value="搜索" class="search_btn"/>
                		</div>
            		</form>
        	</div>
		<table>
			<tr>
				<td>名称</td>
				<td>价格</td>
				<td>作者</td>
				<td>销量</td>
				<td>库存</td>
				<td>浏览量</td>
				<td colspan="2">操作</td>
			</tr>
			${requestScope.page.pageSize}
			<c:forEach items="${requestScope.page.pageBooks}" var="book">
				<tr>
					<td>${book.name}</td>
					<td>${book.price}</td>
					<td>${book.author}</td>
					<td>${book.sales}</td>
					<td>${book.stock}</td>
					<td>${book.visits}</td>
					<td>
						<form action="/managerBook" method="post" style="float: none">
							<input type="hidden" name="method" value="getBook"/>
							<input type="hidden" name="bookID" value="${book.ID}"/>
							<input type="hidden" name="pageNo" value="${requestScope.page.pageNo}">
							<input type="submit" value="修改">
					    </form>
					</td>
					<td>
						<form action="/managerBook" method="post" style="float: left">
							<input type="hidden" name="method" value="DeleteBook"/>
							<input type="hidden" name="bookID" value="${book.ID}"/>
							<input type="hidden" name="pageNo" value="${requestScope.page.pageNo}">
							<input type="submit" value="删除" class="deleteBtn"/>
						</form>
					</td>
				</tr>
			</c:forEach>
			<!--添加图书-->
			<tr>
				<!--将添加书籍按键移至靠右的位置-->
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>
					<form action="pages/manager/book_edit.jsp" method="post" style="float: left">
						<input type="hidden" name="pageNo" value="${requestScope.page.pageTotal}">
						<input type="submit" value="+添加书籍">
					</form>
				</td>
			</tr>	
		</table>
		<%--静态包含分页条--%>
		<%@include file="/pages/common/page_nav.jsp"%>
	</div>
	<%--静态包含页脚内容--%>
	<%@include file="/pages/common/footer.jsp"%>
</body>
</html>
