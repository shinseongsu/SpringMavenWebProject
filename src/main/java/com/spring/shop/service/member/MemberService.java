package com.spring.shop.service.member;

import com.spring.shop.VO.MemberVO;

public interface MemberService {
	
	public MemberVO read(String userid);
	
	public String idConfirm(String userid);
	
	public void insertMember(MemberVO vo);
}
