<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新規投稿</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<h1>新規投稿</h1>

<div class="main-contents">
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

<a href="./">ホーム</a>

<div class="newpost">
<form action="post" method="post"><br />
	<label for="subject">件名</label>
	<input name="subject" id="subject" value="${ post.subject }"/><br>
	(50文字以下で入力してください）<br /><br>


	<%-- テキストエリアにはvalueは使えないので、textareaで囲む --%>
	<label for="text">本文</label>
	<textarea name="text" cols="100" rows="5" class="tweet-box">${ post.text }</textarea>
	(1000文字以下で入力してください)<br /><br>


	<label for="category">カテゴリー</label>
	<input name="category" id="category" value="${ post.category }"/><br>
	(10文字以下で入力してください）<br /><br>

	<input type="submit" value="投稿" /> <br /><br>

</form>
</div>

</div>
</body>
</html>