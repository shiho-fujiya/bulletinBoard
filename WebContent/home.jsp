<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>掲示板システム</title>
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
		<a href="post">新規投稿</a>
		<a href="management">ユーザー管理</a>
		<a href="logout">ログアウト</a>

<div class="header">
</div>

<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>

<c:if test="${ not empty user }">
	<div class="profile">
		<a href="./?user_id=${user.id}"></a>
			<div class="name"><h2><c:out value="${users.name }" /></h2></div>
			<div class="account">
				<a href="./?user_id=${users.id }">@<c:out value="${ users.account }" /></a>
			</div>
			<div class="account">
		</div>
	</div>
</c:if>

<div class="posts">
	<c:forEach items="${posts}" var="post">
		<div class="post">
			<div class="account-name">
				<span class="name"><c:out value="${post.name}" /></span>
			</div>
			<div class="subject"><c:out value="${post.subject}" /></div>
			<div class="category"><c:out value="${post.category}" /></div>
			<div class="text"><c:out value="${post.text}" /></div>
			<div class="date"><fmt:formatDate value="${post.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
			<form action="home" method="post">
				<input type="hidden" name="postId" value="${ post.id }">
				<input type="submit" value="この投稿を削除" />
			</form>

			<c:forEach items="${comments}" var="comment">

				<c:if test="${post.id == comment.postId}">
					<div class="name"><c:out value="${comment.name}" /></div>
					<div class="text"><c:out value="${comment.text}" /></div>
					<div class="date"><fmt:formatDate value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
					<form action="home" method="post">
						<input type="hidden" name="commentId" value="${ commentId }">
						<input type="submit" value="このコメントを削除" />
					</form>
				</c:if>

			</c:forEach>
		<div class="comment"></div>

			<form action="comment" method="post"><br />
				<input type="hidden" name="postId" value="${post.id}">
				<label for="text">本文</label>
				<textarea name="text" cols="100" rows="5" class="tweet-box"></textarea>
				(500文字以下で入力してください)<br />
				<input type="submit" value="コメント" /> <br />
			</form>
		</div>
	</c:forEach>
</div>


</div>
</body>
</html>