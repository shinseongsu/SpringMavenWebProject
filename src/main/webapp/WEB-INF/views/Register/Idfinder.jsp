<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 비밀번호 찾기</title>
</head>
<body>
	<div class="limiter">
		<header>
			<nav>
				<ul>
					<li>아이디 찾기</li>
					<li>비밀번호 찾기</li>
				</ul>
			</nav>
		</header>
		<section>
			<article>
				<h4>아이디찾기</h4>
				<hr />
				<p>아이디 찾는 방법을 선택해 주세요.</p>
				<ul>
					<li>회원정보에 등록한 휴대전화로 인증
						<div class="submenu">
							<p>회원정보에 등록한 휴대전화 번호와 입력한 휴대전화 번호가 같아야, 인증번호를 받을 수 있습니다.</p>
							<table>
								<tr>
									<td><label>이름</label></td>
									<td><input type="text" id="username" class="username"></td>
									<td></td>
								</tr>
								<tr>
									<td><label>휴대전화</label></td>
									<td>
										<select>
											<option>대한민국 +82</option>
										</select>
										<input type="text" id="tel" class="tel" placeholder="휴대전화 번호">
									</td>
									<td>
										<input type="button" id="sms" class="sms" value="인증번호 받기">
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
									  <input type="text" id="sms_auth" class="sms_auth" placeholder="인증번호 6자리 숫자 입력">
									</td>
									<td></td>
								</tr>
							</table>
						</div>
					</li>
					<li>본인확인 이메일로 인증
						<div class="submenu">
							<p>본인확인 이메일 주소와 입력한 이메일 주소가 같아야, 인증번호를 받을 수 있습니다.</p>
							<table>
								<tr>
									<td><label>이름</label></td>
									<td><input type="text" id="username" class="username">
									<td></td>
								</tr>
								<tr>
									<td><label>이메일 주소</label></td>
									<td><input type="text" id="email" class="email"></td>
									<td><input type="button" id="email_sms" class="email_sms" value="인증번호 받기"></td>
								</tr>
								<tr>
									<td></td>
									<td><input type="text" id="email_sms_auth" class="email_sms_auth" placeholder="인증번호 6자리 숫자 입력"></td>
									<td></td>
								</tr>
							</table>
						</div>
					</li>
				</ul>
			</article>
			<article>
				<h4>비밀번호 찾기</h4>
				<hr>
				<div id="first">
					<p>비밀번호를 찾고자 하는 아이디를 입력해 주세요.</p>
					<div>
						<input type="text" id="id" class="id" placeholder="아이디를 입력해주세요.">
					</div>
					<input type="button" id="next" class="next" value="다음">
				</div>
				<div id="second">
					<ul>
						<li>회원정보에 등록한 휴대전화로 인증
							<div class="submenu">
								<p>회원정보에 등록한 휴대전화 번호와 입력한 휴대전화 번호가 같아야, 인증번호를 받을 수 있습니다.</p>
								<table>
									<tr>
										<td><label>이름</label></td>
										<td><input type="text" id="username" class="username"></td>
										<td></td>
									</tr>
									<tr>
										<td><label>휴대전화</label></td>
										<td>
											<select>
												<option>대한민국 +82</option>
											</select>
											<input type="text" id="tel" class="tel" placeholder="휴대전화 번호">
										</td>
										<td>
											<input type="button" id="sms" class="sms" value="인증번호 받기">
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
										  <input type="text" id="sms_auth" class="sms_auth" placeholder="인증번호 6자리 숫자 입력">
										</td>
										<td></td>
									</tr>
								</table>
							</div>
						</li>
					</ul>
				</div>
			</article>
		</section>
	</div>
</body>
</html>