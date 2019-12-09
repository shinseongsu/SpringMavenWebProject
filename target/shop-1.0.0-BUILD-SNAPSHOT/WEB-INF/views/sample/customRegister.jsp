<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원가입</title>
	
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/Login/Register.css" />" />
	<link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<script type="text/javascript" src="<c:url value="/js/jquery-2.1.1.min.js" />"></script>
	
</head>
<body>
	
	<div class="limiter">
		<div class="register-container">
			<div class="register-wrap">
				<form class="login-form-title" method="post" action="/register">
					<span class="register-form-title">
						Register
					</span>
				
					<div class="input_username">
						<input type="text" name="username" class="username" id="username" />
						<label for="username"><i class="fa fa-user"></i>username</label>
						<div class="after"></div>
						<input type="button" id="IdConfirm" class="IdConfirm" value="아이디 중복확인" />
					</div>
					
					<div class="input_password">
						<input type="password" name="password" class="password" id="password" />
						<label for="password"><i class="fa fa-lock"></i>password</label>
						<div class="after"></div>
					</div>
					
					<div class="input_passwordConfirm">
						<input type="password" name="passwordConfirm" class="passwordConfirm" id="passwordConfirm" />
						<label for="passwordConfirm"><i class="fa fa-lock"></i>password</label>
						<div class="after"></div>
					</div>
					
					<div class="input_email">
						<input type="email" name="email" class="email" id="email" />
						<label for="email"><i class="fa fa-at"></i>email</label>
						<div class="after"></div>
					</div>
					
					<div class="input_tel">
						<input type="text" name="tel" class="tel" id="tel" />
						<label for="tel"><i class="fa fa-mobile"></i>tel</label>
						<div class="after"></div>
					</div>
					
					<div class="input_address">
						<input type="text" name="address" class="address" id="address" />
						<label for="address"><i class="fa fa-map-marker"></i>address</label>
						<div class="after"></div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	
</body>
</html>