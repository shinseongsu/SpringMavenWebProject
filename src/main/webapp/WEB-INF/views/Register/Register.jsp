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
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/loading.css" />" />
	<link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<script type="text/javascript" src="<c:url value="/js/jquery-2.1.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/jquery.loading.min.js" />"></script>
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	
	<script>
		$(document).ready(function() {
			// 아이디 중복 체크(변경 될때마다 ajax)
			$("#username").on("keyup", function() {
				$(".input_username").removeClass("alert-validate");
				$(".input_username").removeClass("alert-check");
				$(".input_username").removeClass("alert-fail");
				
				if($("#username").val() == "") {
					$(".input_username").addClass("alert-validate");
					return;
				}
				
				$.ajax({
					url: "/AjaxOverlapped",
					type: "POST",
					data : { id : $("#username").val() },
					beforeSend: function(data) {
						data.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success: function(data){
						if(data == "false") {
							$(".input_username").addClass("alert-validate");
						} else {
							$(".input_username").addClass("alert-check");
						}
					},
					error: function(data) {
						console.log(data);
					}
				});
			});
			
			$("#passwordConfirm").on("keyup", function() {
				$(".input_password").removeClass("alert-validate");
				$(".input_password").removeClass("alert-check");
				$(".input_password").removeClass("alert-fail");
				$(".input_passwordConfirm").removeClass("alert-validate");
				$(".input_passwordConfirm").removeClass("alert-check");
				$(".input_passwordConfirm").removeClass("alert-fail");
				
				var password = $("#password").val();
				var passwordConfirm = $("#passwordConfirm").val();
				
				if(password == passwordConfirm) {
					$(".input_password").addClass("alert-check");
					$(".input_passwordConfirm").addClass("alert-check");
				} else {
					$(".input_passwordConfirm").addClass("alert-validate");
				}
			});
			
			$("#email").on("keyup", function() {
				 $(".input_email").removeClass("alert-check");
				 $(".input_email").removeClass("alert-validate"); 
				 $(".input_email").removeClass("alert-fail");
				 
				 var regExp = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
				 var email = $("#email").val();
				 
				 if(regExp.test(email)){
					 $(".input_email").addClass("alert-check");
				 } else {
					 $(".input_email").addClass("alert-validate"); 
				 }
			});
			
			$("#tel").on("keyup", function() {
				$(".input_tel").removeClass("alert-check");
				$(".input_tel").removeClass("alert-validate"); 
				$(".input_tel").removeClass("alert-fail");
				
				var regExp = /^\d{3}-\d{3,4}-\d{4}$/;
				var tel = $("#tel").val();
				
				if(regExp.test(tel)) {
					$(".input_tel").addClass("alert-check");
				} else {
					$(".input_tel").addClass("alert-validate");
				}
			});
			
			$("#address").click(function() {
				KakaoAPI();
			});
			
			$("#address").keyup(function() {
				KakaoAPI();
			});
			
			function KakaoAPI() {
				if($("#address").val() != "") {
					var checked = confirm("주소를 다시 입력하시겠습니까?");
					if(checked == false) {
						return;
					}
				}
				
				$("#address").val('');
				
				new daum.Postcode({
					oncomplete: function(data){
						var address = data.roadAddress;
						console.log(address);
						$("#address").val(address);
						$(".input_address").addClass("alert-check");
					}
				}).open();
			}
			
			$(".register-form-btn").click(function(e) {
				e.preventDefault();
				
				if(!$(".input_username").hasClass("alert-check")) {
					$("#username").focus();
					$(".alert-validate").addClass("alert-fail");
					return;
				}
				if(!$(".input_passwordConfirm").hasClass("alert-check")) {
					$("#passwordConfirm").focus();
					$(".alert-validate").addClass("alert-fail");
					return;
				}
				if(!$(".input_email").hasClass("alert-check")) {
					$("#email").focus();
					$(".alert-validate").addClass("alert-fail");
					return;
				}
				if(!$(".input_tel").hasClass("alert-check")) {
					$("#tel").focus();
					$(".alert-validate").addClass("alert-fail");
					return;
				}
				if(!$(".input_address").hasClass("alert-check")) {
					$("#address").focus();
					$(".alert-validate").addClass("alert-fail");
					return;
				}
				
				NaverCapchaAPI();
				
				$(".modal-div").show();
				
				//$(".login-form-title").submit();
			});
			
			function NaverCapchaAPI() {
				$.ajax({
					url: "/AjaxAPICapcha",
					type: "GET",
					data: { "${_csrf.headerName}" : "${_csrf.token}" },
					dataType: "JSON",
					beforeSend: function() {
						$(".modal-div").loading({
							  overlay: $("#custom-overlay")
						});
					},
					success: function(data){
						console.log(data);
						console.log(data.key);
						console.log(data.Imagedir);
						$(".CapchaKey").val(data.key);
						$(".CapchaImage").attr("src", "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + data.key );
						$(".modal-div").loading("stop");
					},
					error: function(data) {
						console.log(data);
					}
				});
			}
			
			$(".fa-close").click(function() {
				$(".modal-div").hide();
				$(".CapchaImage").attr("src", "");
			});
			
			$(".CapchaReply").click(function() {
				NaverCapchaAPI();
			});
			
			$(".CapchaButton").click(function() {
				if($(".CapchaText").val() == "" ) {
					alert("보이는 대로 입력해주세요");
					return;
				}
				
				$.ajax({
					url: "/AjaxAPICapchaCheck",
					type: "POST",
					data: { "CapchaText" : $(".CapchaText").val(),
							 "CapchaKey" : $(".CapchaKey").val() 
						   },
					dataType: "JSON",
					beforeSend: function(data) {
						data.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success: function(data){
						if(data.toString() == "false") {
							alert("보안문자를 다시 확인해주세요.");
						} else {
							$(".login-form-title").submit();
						}
					},
					error: function(data) {
						alert("에러가 발생하였습니다.");
					}
				});
				
			});
			
		});
	</script>
	
</head>
<body>
	
	<div class="limiter">
		<div class="register-container">
			<div class="register-wrap">
				<form class="login-form-title" method="post" action="/register">
					<span class="register-form-title">
						Register
					</span>
					
					<div class="input_username" data-check="이미 있는 아이디입니다." data-validate="빈칸을 채워주세요.">
						<input type="text" name="username" class="username" id="username" required>
						<label for="username"><i class="fa fa-user"></i>username</label>
						<div class="after"></div>
					</div>
					
				 	<div class="input_password">
						<input type="password" name="password" class="password" id="password" required>
						<label for="password"><i class="fa fa-lock"></i>password</label>
						<div class="after"></div>
					</div>
					
					<div class="input_passwordConfirm" data-check="비밀번호가 다릅니다.">
						<input type="password" name="passwordConfirm" class="passwordConfirm" id="passwordConfirm" required>
						<label for="passwordConfirm"><i class="fa fa-lock"></i>password Confirm</label>
						<div class="after"></div>
					</div>
					
					<div class="input_email" data-check="이메일을 확인해주세요.">
						<input type="text" name="email" class="email" id="email" required>
						<label for="email"><i class="fa fa-at"></i>email</label>
						<div class="after"></div>
					</div>
					
					<div class="input_tel" data-check="전화번호를 확인해주세요.">
						<input type="text" name="tel" class="tel" id="tel" required>
						<label for="tel"><i class="fa fa-mobile"></i>tel</label>
						<div class="after"></div>
					</div>
					
					<div class="input_address">
						<input type="text" name="address" class="address" id="address" required>
						<label for="address"><i class="fa fa-map-marker"></i>address</label>
						<div class="after"></div>
					</div>  
					
					<div class="register-container-form-btn">
						<div class="register-wrap-form-btn">
							<div class="register-form-bgbtn"></div>
							<input type="submit" class="register-form-btn" value="Register">
						</div>
					</div>
					<input type="hidden" id="token" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>
			</div>
		</div>
		
	</div>
	<div class="clear"></div>
	<div class="modal-div">
		<div class="modal-container">
			<div class="modal-header">
				<h5 class="modal-title">아래의 이미지를 보이는 대로 입력해주세요.</h5>
				<i class="fa fa-close"></i>
			</div>
			<div class="modal-body">
				<img class="CapchaImage" />
				<input type="text" class="CapchaText" name="CapchaText" autocomplete="off">
				<button class="CapchaButton">등록</button>
				<button class="CapchaReply"><i class="fa fa-refresh"></i></button>
				<img class="navercopyright" src="<c:url value="/images/Capcha/Naver.png" />" />
				<input type="hidden" class="CapchaKey" value="">
			</div>
		</div>
	</div>
	
	<div id="custom-overlay">
        <div class="loading-spinner">
          Loading (custom)...
        </div>
    </div>
</body>
</html>