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
<form action="post" method="post"><br />
	<label for="subject">件名</label>
	<input name="subject" id="subject"/>（50文字以下で入力してください）<br />


	<label for="text">本文</label>
	<textarea name="text" cols="100" rows="5" class="tweet-box"></textarea>
	(1000文字以下で入力してください)<br />


	<label for="category">カテゴリー</label>
	<input name="category" id="category"/>（10文字以下で入力してください）<br />

	<input type="submit" value="投稿" /> <br />

</form>

<a href="./">戻る</a>
</div>
</body>
</html>