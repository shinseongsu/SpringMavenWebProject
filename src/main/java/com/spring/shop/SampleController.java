package com.spring.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sample/*")
@Controller
public class SampleController {

	@GetMapping("/all")
	public String doAll() {
		System.out.println("do all can access everybody");
		
		return "sample/all";
	}
	
	@GetMapping("/member")
	public String doMember() {
		System.out.println("logined member");
	
		return "sample/member";
	}
	
	@GetMapping("/admin")
	public String doAdmin() {
		System.out.println("admin only");
		
		return "sample/admin";
	}
	
	@GetMapping("/test")
	public String test(){
		return "email/MailContent.html";
	}
	
}
