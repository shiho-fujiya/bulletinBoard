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


<script type="text/javascript">
<!--

function check(){

	// 「OK」時の処理開始 ＋ 確認ダイアログの表示
	if(window.confirm('変更します、よろしいですか？')){
		location.href = "example_confirm.html"; // example_confirm.html へジャンプ
		return true; // 「OK」時は送信を実行
	}
	// 「OK」時の処理終了

	// 「キャンセル」時の処理開始
	else{

		window.alert('キャンセルされました'); // 警告ダイアログを表示
		return false; // 送信を中止
	}
	// 「キャンセル」時の処理終了

}

// -->
</script>

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
<h1>管理画面</h1>
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
	<a href="signup">ユーザー新規登録</a>
	<a href="home">ホーム</a>

<div class="header">
</div>

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
			<form action="management" method="post" onSubmit="return check()">
				<input type="hidden" name="id" value="${user.id}">
				<c:if test="${ user.id != loginUser.id }">
				<c:if test="${ user.operation == true }">
					<input type="hidden" name="operation" value="false" />
					<input type="submit" value="停止" />
				</c:if>
				<c:if test="${ user.operation == false }">
					<input type="hidden" name="operation" value="true" />
					<input type="submit" value="復活" />
				</c:if>
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