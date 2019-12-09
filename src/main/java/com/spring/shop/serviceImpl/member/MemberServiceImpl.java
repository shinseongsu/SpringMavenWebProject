package com.spring.shop.serviceImpl.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.shop.DAO.member.MemberDAO;
import com.spring.shop.VO.MemberVO;
import com.spring.shop.service.member.MemberService;

@Service("MemberServiceImple")
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO memberDao;
	
	@Override
	public MemberVO read(String userid) {
		return memberDao.Member(userid);
	}

	@Override
	public String idConfirm(String userid) {
		return memberDao.IdConfirm(userid);
	}

	@Override
	public void insertMember(MemberVO vo) {
		memberDao.InsertMember(vo);
	}

}
