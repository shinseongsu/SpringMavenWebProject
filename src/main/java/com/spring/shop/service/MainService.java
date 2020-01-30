package com.spring.shop.service;

import java.util.List;
import java.util.Map;

import com.spring.shop.VO.MainVO;

public interface MainService {
	public List<MainVO> MainList();
	
	public List<Map<String, String>> Categories();
}
