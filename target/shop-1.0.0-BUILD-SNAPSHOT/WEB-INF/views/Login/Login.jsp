<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>로그인 화면</title>
	<link rel="stylesheet" href="<c:url value="/css/login/login.css" />" />
</head>
<body>
	<div>
		<form action="/LoginAction" method="post"> 
			<input type="text" id="id" name="id" placeholder="아이디(이메일)">
			<input type="password" id="password" name="password">
			<hr>
			<input type="submit" value="로그인">
		</form>
		
	</div>
</body>
</html>