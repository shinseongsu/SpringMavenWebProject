package com.spring.shop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.shop.VO.MainVO;
import com.spring.shop.serviceImpl.MainServiceImpl;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	@Autowired
	private MainServiceImpl homeServiceImpl;
	
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, ModelAndView model) {
		
		List<MainVO> homeList = new ArrayList<MainVO>();
		
		try {
			homeList = homeServiceImpl.MainList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		model.addObject("MainList", homeList);
		model.setViewName("Main");
		System.out.println("Controller 타고 시작 ");
		
		return model;
	}
	
}
