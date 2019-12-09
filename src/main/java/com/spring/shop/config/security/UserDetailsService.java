package com.spring.shop.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.spring.shop.VO.MemberVO;
import com.spring.shop.VO.SecurityUser;
import com.spring.shop.serviceImpl.member.MemberServiceImpl;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{
	
	@Autowired
	private MemberServiceImpl memberServiceImple;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println(username);
		
		MemberVO vo = memberServiceImple.read(username);
		
		return vo == null ? null : new SecurityUser(vo);
	}
	
	
	
}
