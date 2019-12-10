package com.spring.shop.config.email;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private SimpleMailMessage preConfiguredMessage;
	
	@Async
	public void sendMail(String to, String subject, String body) {
		
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper messageHelper =
									new MimeMessageHelper(message, true, "UTF-8");
			
			messageHelper.setFrom("이메일", "흥청망청(개발자: 신성수)");
			messageHelper.setSubject(subject);
			messageHelper.setTo(to);
			messageHelper.setText(body, true);
			
			mailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String EmailForm(String state, String path) {
		
		String filePath = "";

		if(state == "Register") {
			filePath = path;
			filePath = "/Users/mac/git/SpringMavenWebProject/shop/src/main/webapp/WEB-INF/views/email/MailContent.html";
		} else {
			return null;
		}
		
		// 버퍼 생성
		BufferedReader br = null;
		
		//Input 스트림 생성
		InputStreamReader inputstream = null;
		
		// File Input 스트림 생성
		FileInputStream fileinputStream = null;
		
		// File 경로
		File file = new File(filePath);
		
		String temp = "";
		StringBuffer sb = new StringBuffer();
		try{
			
			fileinputStream = new FileInputStream(file);
			
			inputstream = new InputStreamReader(fileinputStream, "UTF-8"); 
			
			br = new BufferedReader(inputstream);
			
			while((temp = br.readLine()) != null) {
				sb.append(temp + "\n");
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				fileinputStream.close();
				inputstream.close();
				br.close();
			} catch(Exception e ) {
				
			}
		}
		System.out.println("== html 내용 ===");
		System.out.println(sb.toString());
		
		return sb.toString();
	}
	
}
