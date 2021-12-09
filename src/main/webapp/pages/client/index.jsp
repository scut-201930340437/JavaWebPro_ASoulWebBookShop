<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>书城首页</title>
	<%-- 静态包含 base标签、css样式、jQuery文件 --%>
	<%@ include file="/pages/common/head.jsp"%>
</head>
<body>

<%@include file="/pages/common/clientHeader.jsp"%>

<div id="main" style="margin-bottom: 0;margin-top: 0;height: 90%;">
	<div id="book" style="height: 85%;">
		<div class="book_cond" style="height: 5%;">
			<form action="/clientBook" method="post">
				<input type="hidden" name="method" value="searchByName">
				<div class="search">
					<input type="text" placeholder="搜索书名" autocomplete="off"  name="bookName" value="${requestScope.bookName}" class="search_box" style="height: 30px;width: 400px;"/>
					<input type="submit" value="搜索" class="search_btn"/>
				</div>
			</form>
		</div>
		<div style="float: left;margin-bottom: 0; height: 95%;">
			<c:if test="${empty requestScope.page.pageBooks}">
				很抱歉，没有查找到书籍。。。
			</c:if>
			<c:if test="${not empty requestScope.page.pageBooks}">
				<c:forEach items="${requestScope.page.pageBooks}" var="book">
					<div class="b_list" style="height: 50%;margin-bottom: 5px;">
						<div class="img_div">
							<a href="/clientBook?method=searchByID&bookID=${book.ID}"><img class="book_img" alt="" src="${book.img_path}" /></a>
						</div>
						<div class="book_info">
							<div class="book_name" style="margin-bottom: 5px;">
								<span class="sp1">书名:</span>
								<span class="sp2">${book.name}</span>
							</div>
							<div class="book_author" style="margin-bottom: 5px;">
								<span class="sp1">作者:</span>
								<span class="sp2">${book.author}</span>
							</div>
							<div class="book_price" style="margin-bottom: 5px;">
								<span class="sp1">价格:</span>
								<span class="sp2">￥${book.price}</span>
							</div>
							<div class="book_sales">
								<span class="sp1">月销量:</span>
								<span class="sp2">${book.sales}</span>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>
	<%--静态包含分页条--%>
	<%@include file="/pages/common/page_nav.jsp"%>
	<%--静态包含页脚内容--%>
	<%@include file="/pages/common/footer.jsp"%>
</div>
</body>
</html>