package com.spring.shop.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.shop.VO.MainVO;
import com.spring.shop.VO.MemberVO;
import com.spring.shop.VO.SecurityUser;
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
	public ModelAndView home(Locale locale, ModelAndView model, HttpServletRequest request, HttpServletResponse response, Principal principal, HttpSession session) {
		
		List<MainVO> homeList = new ArrayList<MainVO>();
		
		try {
			if(principal.getName() != null) {
				System.out.println("스프링 시큐리티 아이디 : " + principal.getName());
				model.addObject("user_id", principal.getName());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
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
