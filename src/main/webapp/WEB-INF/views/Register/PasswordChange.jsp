<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>패스워드 변경</title>
</head>
<body>
	<div class="limiter">
		<div class="register-container">
			<div class="register-wrap">
				<form class="login-form-title" method="post" action="/passwordChange">
					<span class="register-form-title">
						Password Change
					</span>
					
					<div class="input_username">
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
					
					<input type="hidden" id="token" name="${_csrf.parameterName}" value="${_csrf.token}" />
					
					<div class="register-container-form-btn">
						<div class="register-wrap-form-btn">
							<div class="register-form-bgbtn"></div>
							<input type="submit" class="register-form-btn" value="Register">
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>