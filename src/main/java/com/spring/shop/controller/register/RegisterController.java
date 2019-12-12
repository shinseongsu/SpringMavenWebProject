package com.spring.shop.controller.register;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.shop.VO.MemberVO;
import com.spring.shop.config.api.APICaptchaNkey;
import com.spring.shop.config.email.MailService;
import com.spring.shop.serviceImpl.member.MemberServiceImpl;

@Controller
public class RegisterController {
	
	@Autowired
	public MemberServiceImpl memberServiceImple;
	
	@Autowired
	public PasswordEncoder pwencoder;
	
	@Autowired
	private MailService mailService;
	
	APICaptchaNkey api = new APICaptchaNkey();
	
	@RequestMapping(value="/AjaxOverlapped", method = RequestMethod.POST)
	@ResponseBody
	public String overlapper(@RequestParam("id") String id,
									 HttpServletRequest request,
									 HttpServletResponse response) throws Exception {

		String checkId = memberServiceImple.idConfirm(id);
		
		if(checkId == null) {
			checkId = "true";
		} else {
			checkId = "false";
		}
		return checkId;
	}
	
	@RequestMapping(value="/AjaxAPICapcha", method = RequestMethod.GET)
	@ResponseBody
	public Map AjaxAPICapcha() {
		Map<String, String> map = new HashMap<String, String>();
		
		String key = APICaptchaNkey.apiKEY();
		String imagedir = APICaptchaNkey.APICaptchaImage(key);
		
		
		map.put("key", key);
		map.put("Imagedir", imagedir);
		
		return map;
	}
	
	@RequestMapping(value="/AjaxAPICapchaCheck", method = RequestMethod.POST)
	@ResponseBody
	public boolean AjaxAPICapchaCheck(@RequestParam("CapchaText") String CapchaText,
									  @RequestParam("CapchaKey") String CapchaKey) {
		boolean result = false;
		
		result = api.APICaptchaResult(CapchaKey, CapchaText);
		
		return result;
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String insertMember(@RequestParam("username") String username,
						 			 @RequestParam("password") String password,
						 			 @RequestParam("email") String email,
						 			 @RequestParam("tel") String tel,
						 			 @RequestParam("address") String address,
						 			 HttpServletResponse response,
						 			 HttpServletRequest request) throws Exception{
		
		String path = request.getSession().getServletContext().getRealPath("src/main/webapp/WEB-INF/views/email/MailContent.html");
		
		response.setContentType("text/html; charset=UTF-8");
		
		MemberVO member = new MemberVO();

		member.setUserid(username);
		member.setUserpw(pwencoder.encode(password));
		member.setEmail(email);
		member.setTel(tel);
		member.setAddress(address);
		
		String result = "false";
		
		try {
			memberServiceImple.insertMember(member);
			result = "true";
		} catch (Exception e) {
			result = "false";
		}
		
		PrintWriter out = response.getWriter();
		
		if(result == "true") {
			String message = mailService.EmailForm("Register", path);
			message.replace("_USERID_", username);
			mailService.sendMail(email, "회원가입을 환영합니다", message);
			
			out.println("<script>");
			out.println("alert('회원가입에 성공하였습니다.');");
			out.println("location.href='/';");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('회원가입에 실패하였습니다.');");
			out.println("history.back();");
			out.println("<script>");
		}
		
		return null;
	}
	
	@RequestMapping(value="/Idfinder", method = RequestMethod.POST)
	public ModelAndView Idfinder (ModelAndView model) {
		model.setViewName("Register/Idfinder");
		
		return model;
	}
	
}
