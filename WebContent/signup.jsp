<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー登録</title>
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
<form action="signup" method="post"><br />
	<label for="account">アカウント名</label>
	<input name="account" id="account"/>（半角英数字で6文字以上20文字以下）<br />

	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/>（記号を含む全ての半角文字で6文字以上255文字以下）<br />

	<label for="name">名前</label>
	<input name="name" id="name"/><br />

	<label for="branch_id">所属</label>
		<div class="branches">
			<select name="branchId">
				<option value="">選択してください</option>
					<c:forEach items="${branches}" var="branch">
						<option value="${branch.id}">${branch.name}</option>
					</c:forEach>
			<option value="">選択してください</option>
				<c:forEach items="${branches}" var="branch">
					<option value="${branch.id}">${branch.name}</option>
				</c:forEach>
			</select>
		</div>

	<label for="position_id">部署・役職</label>
		<div class="positions">
			<select name="positionId">
				<option value="">選択してください</option>
					<c:forEach items="${positions}" var="position">
						<option value="${position.id}">${position.name}</option>
					</c:forEach>

			<option value="">選択してください</option>
				<c:forEach items="${positions}" var="position">
					<option value="${position.id}">${position.name}</option>
				</c:forEach>
			</select>
		</div>

	<input type="submit" value="登録" /> <br />


</form>
<div class="copyright">Copyright(c)Shiho Fujiya</div>
</div>
</body>
</html>