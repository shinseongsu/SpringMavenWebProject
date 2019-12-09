package com.spring.shop.DAO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.shop.VO.MainVO;

@Repository("mainDao")
public class MainDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<MainVO> MainList() {
		return mybatis.selectList("Main-Mapping.MainList");
	}
}
