package com.spring.shop.config.security;

import java.io.IOException;
import java.net.InetAddress;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.spring.shop.log.Dao.LogDao;

public class LoginSuccessHandler implements AuthenticationSuccessHandler{
	
	@Autowired
	private LogDao log;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		List<String> roleNames = new ArrayList<>();
		
		authentication.getAuthorities().forEach(authority -> {
			roleNames.add(authority.getAuthority());
		});
		
		InetAddress local = InetAddress.getLocalHost();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		
		String username = (String) auth.getName();
		String ip = local.getHostAddress();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("ID", username);
		map.put("IP_ADDRESS", ip);
		map.put("LOG_STATUS", "SUCCESS");
		
		log.Login_log(map);
		 
		System.out.println("rolelist 불러오기 ");
		for(int i = 0 ; i < roleNames.size() ; i++) {
			System.out.println(roleNames.get(i));
		}
		
		// 관리자 권한이 있으면 관리자 페이지로..
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/sample/admin");
		}
		
		// member권한 일 경우 첫 메인 페이지로..
		if(roleNames.contains("ROLE_MEMBER")) {
			response.sendRedirect("/sample/member");
		}
		
		
		response.sendRedirect("/");
	}

}
