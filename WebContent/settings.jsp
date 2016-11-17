<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${user.name}の設定</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
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

<form action="settings" method="post"><br />
	<label for="name">名前</label>
	<input name="name" value="${user.name}" id="name"/><br />

	<label for="account">アカウント名</label>
	<input name="account" value="${user.account}" id="account" /><br />

	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/> <br />

	<label for="confirmation">確認用パスワード</label>
	<input name="confirmation" type="password" id="confirmation"/> <br />

	<label for="branch_id">所属</label>
		<div class="branches">
			<select name="branchId">
				<option value="">選択してください</option>
					<c:forEach items="${branches}" var="branch">
						<c:if test="${ user.branchId == branch.id }" >
							<option value="${ user.branchId }" selected >${ branch.name }</option>
						</c:if>
						<c:if test="${ user.branchId != branch.id }" >
							<option value="${ branch.id }" >${ branch.name }</option>
						</c:if>
					</c:forEach>
			</select>
		</div>

	<label for="position_id">部署・役職</label>
		<div class="positions">
			<select name="positionId">
				<option value="">選択してください</option>
					<c:forEach items="${positions}" var="position">
						<c:if test="${ user.positionId == position.id }" >
							<option value="${ user.positionId }" selected >${ position.name }</option>
						</c:if>
						<c:if test="${ user.positionId != position.id }" >
							<option value="${ position.id }" >${ position.name }</option>
						</c:if>
						<option value="${position.id}">${position.name}</option>
					</c:forEach>
			</select>
		</div>

	<input type="submit" value="登録" /> <br />

	<a href="./">戻る</a>
</form>
</div>
</body>
</html>