package com.spring.shop.controller.Email;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.shop.config.email.MailService;

@Controller
@EnableAsync
public class MailController {
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping(value ="/sendMail", method = RequestMethod.GET)
	public void sendSimpleMail(HttpServletRequest request,
								HttpServletResponse response) throws Exception {
		
		
	}
}
