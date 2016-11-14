<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理画面</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
	<style type="text/css">
	<!--
	body {
	background-color: pink;
	}
	-->
	</style>
</head>
<body>

<div class="main-contents">
	<a href="signup">ユーザー新規登録</a>
	<a href="home">ホーム</a>

<div class="header">
</div>

<c:if test="${ not empty user }">
	<div class="profile">
		<a href="./?user_id=${user.id}"></a>
			<div class="name"><h2><c:out value="${ users.name }" /></h2></div>
			<div class="account">
				<a href="./?user_id=${users.id }">@<c:out value="${ users.account }" /></a>
			</div>
			<div class="account">
			</div>
	</div>
</c:if>

<table border="8">
	<tr>
		<th>名前</th><th>ログインID</th><th>所属</th><th>役職</th><th>アカウント停止/復活</th><th>編集</th>
	</tr>

	<c:forEach items="${ users }" var="user">
		<tr>
			<td>${ user.name }</td>
			<td>${ user.account }</td>
			<c:forEach items="${ branches }" var="branch">
				<c:if test="${ user.branchId == branch.id }"><td>${ branch.name }</td></c:if>
			</c:forEach>
			<c:forEach items="${ positions }" var="position">
				<c:if test="${ user.positionId == position.id }"><td>${ position.name }</td></c:if>
			</c:forEach>


			<td>
			<!--  -->
			<form action="management" method="post">
				<input type="hidden" name="id" value="${user.id}">
				<c:if test="${ user.operation == true }">
					<input type="hidden" name="operation" value="false" />
					<input type="submit" value="停止" />
				</c:if>
				<c:if test="${ user.operation == false }">
					<input type="hidden" name="operation" value="true" />
					<input type="submit" value="復活" />
				</c:if>

			<!--  -->
			</form>
			</td>

			<td>
				<form action="settings" method="get">
					<input type="hidden" name="userId" value="${ user.id }">
					<input type="submit" value="編集" />
				</form>
			</td>
		</tr>
	</c:forEach>

</table>

<div class="post">
	<div class="account-name">
		<span class="name"><c:out value="${post.name}" /></span>
	</div>
	<div class="subject"><c:out value="${post.subject}" /></div>
	<div class="category"><c:out value="${post.category}" /></div>
	<div class="text"><c:out value="${post.text}" /></div>
</div>
</div>
</body>
</html>