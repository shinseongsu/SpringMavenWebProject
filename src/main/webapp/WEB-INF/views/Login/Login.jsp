<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta charset="UTF-8">
	<title>로그인 화면 </title>
	
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/Login/Login.css" />" />
	<link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/material-design-iconic-font.min.css" />" />
	
	<script type="text/javascript" src="<c:url value="/js/jquery-2.1.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/Login/Login.js" />"></script>
</head>
<body>
	
	<!-- <h2><c:out value="${error}" /></h2>
	<h2><c:out value="${logout}" /></h2> -->
	
	<div class="limiter">
		<div class="login-container">
			<div class="login-wrap">
				<form class="login-form form-validate" method="post" action="/login">
					<span class="login-form-title">
						Login
					</span>
					
					<div class="input-wrap input-validate" style="margin-bottom: 24px;" data-validate="Username is required">
						<span class="input-label">Username</span>
						<input type="text" name="username" class="input" placeholder="Enter your username">
						<span class="input-focus" data-symbol="&#xf206;"></span>
					</div>
					
					<div class="input-wrap input-validate" data-validate="password is required">
						<span class="input-label">Password</span>
						<input type="password" name="password" class="input" placeholder="Enter your password">
						<span class="input-focus" data-symbol="&#xf190;" ></span>
					</div>
					
					<div class="text">
						<input type="checkbox" name="remember-me"> Remember Me
							<a href="/Idfinder" class="text-text">
								패스워드 찾기
							</a>
							<a href="/Register" class="text-text">
								회원가입
							</a>
					</div>
					
					<div class="login-container-form-btn">
						<div class="login-wrap-form-btn">
							<div class="login-form-bgbtn"></div>
							<input type="submit" class="login-form-btn" value="Login">
						</div>
					</div>
					
					<div class="login-social">
						<a href="${facebook_url}" class="login-social-item bg1">
							<i class="fa fa-facebook"></i>
						</a>
						
						<a href="${google_url}" class="login-social-item bg2">
							<i class="fa fa-twitter"></i>
						</a>
						
						<a href="${twitter_url}" class="login-social-item bg3">
							<i class="fa fa-google"></i>
						</a>
					</div>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>
			</div>
		</div>
	</div>
	
</body>
</html>