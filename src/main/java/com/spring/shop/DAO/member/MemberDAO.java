package com.spring.shop.DAO.member;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.shop.VO.MemberVO;

@Repository("memberDao")
public class MemberDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public MemberVO Member(String userid) {
		return mybatis.selectOne("Member-Mapping.read", userid);
	}
	
	public String IdConfirm(String userid) {
		return mybatis.selectOne("Member-Mapping.idConfirm", userid);
	}
	
	public void InsertMember(MemberVO vo) {
		mybatis.insert("Member-Mapping.InsertMember", vo);
	}
}
