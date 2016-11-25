<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${user.name}の設定</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">

</head>
<body>
<h1>ユーザー編集</h1>
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

<a href="management">ユーザー管理</a>

<form action="settings" method="post"><br />
	<input type="hidden" name="userId" value="${user.id}">

	<label for="name">名前</label>
	<input name="name" value="${editUser.name}" id="name"/><br>（10文字以下）<br /><br>

	<label for="account">アカウント名</label>
	<input name="account" value="${editUser.account}" id="account" /><br>（半角英数字で6文字以上20文字以下）<br /><br>

	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/><br>（記号を含む全ての半角文字で6文字以上255文字以下)<br /><br>

	<label for="confirmation">確認用パスワード</label>
	<input name="confirmation" type="password" id="confirmation"/><br>（再度入力してください） <br /><br>

	<c:if test="${ loginUser.id != editUser.id }">
		<label for="branch_id">所属</label>
			<div class="branches">
				<select name="branchId">
					<c:forEach items="${branches}" var="branch">
						<c:if test="${ branch.id == editUser.branchId }" >
							<option value="${ branch.id}" selected >${ branch.name }</option>
						</c:if>
						<c:if test="${ branch.id != editUser.branchId }">
							<option value="${ branch.id }">${ branch.name }</option>
						</c:if>
					</c:forEach>
				</select><br>
				<c:out value="（変更がある場合は選択して下さい）"/>
			</div><br>

		<label for="position_id">部署・役職</label>
			<div class="positions">
				<select name="positionId">
					<c:forEach items="${positions}" var="position">
						<c:if test="${ position.id == editUser.positionId }" >
							<option value="${ position.id}" selected >${ position.name }</option>
						</c:if>
						<c:if test="${ position.id != editUser.positionId }">
							<option value="${ position.id }">${ position.name }</option>
						</c:if>
					</c:forEach>
				</select><br>
				<c:out value="（変更がある場合は選択して下さい）"/>
			</div><br>
	</c:if>

	<c:if test="${ loginUser.id == editUser.id }">
		<input type="hidden" name="branchId" value="1">
		<input type="hidden" name="positionId" value="1">
	</c:if>

	<input type="submit" value="登録" /> <br /><br>

</form>

</div>
</body>
</html>