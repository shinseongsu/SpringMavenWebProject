package com.spring.shop.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.shop.DAO.MainDAO;
import com.spring.shop.VO.MainVO;
import com.spring.shop.service.MainService;

@Service("MainServiceImpl")
public class MainServiceImpl implements MainService{

	@Autowired	
	private MainDAO mainDao;
	
	@Override
	public List<MainVO> MainList() {
		return mainDao.MainList();
	}
	
}
