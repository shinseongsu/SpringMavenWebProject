package com.spring.shop.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class LoginAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		System.out.println("request ::: " + request);
		System.out.println("response ::: "+ response);
		System.out.println("에러 원인 ::: " + accessDeniedException);
		System.out.println("에러~~~");
		
		response.sendRedirect("/accessError");
		
	}
	
	
}
