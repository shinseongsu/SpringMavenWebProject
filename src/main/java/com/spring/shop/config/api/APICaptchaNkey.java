package com.spring.shop.config.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.shop.VO.APIKeyVO;

public class APICaptchaNkey {
	
	@Autowired
	private static APIKeyVO apikey;
	
	private static ApplicationContext context = new ClassPathXmlApplicationContext("social-context.xml");
	
	// 키 발급
	public static String apiKEY() {
		
		apikey = (APIKeyVO) context.getBean("NaverAPI");
		
		String key = null;
		try {
			String code = "0";
			String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code;
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", apikey.getClientId());
			con.setRequestProperty("X-Naver-Client-Secret", apikey.getClient_Secret());
			
			int responseCode = con.getResponseCode();
			
			BufferedReader br;
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			String inputLine;
			
			StringBuffer response = new StringBuffer();
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			System.out.println(response.toString());
			key = response.toString();
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(key);
			key = (String) object.get("key");

		}catch (Exception e ) {
			System.out.println(e);
			apikey.setCAPTCHA_KEY("");
		}
		return key;
	}
	
	// 캡차 이미지
	public static String APICaptchaImage(String CAPTCHA_KEY) {
	//	String dirPath = request.getServletContext().getRealPath("captchaImage");
		String dirPath = null;
		try {
			
			String key = CAPTCHA_KEY;
			String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", apikey.getClientId());
            con.setRequestProperty("X-Naver-Client-Secret", apikey.getClient_Secret());
            
            int responseCode = con.getResponseCode();
            
            BufferedReader br;
            
            if(responseCode==200) { // 정상 호출
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                // 랜덤한 이름으로 파일 생성
                //String tempname = Long.valueOf(new Date().getTime()).toString();
                //File f = new File("/Users/mac/Documents/shop/src/main/webapp/resources/images/Capcha/" + tempname + ".jpg");
                //f.createNewFile();
                //OutputStream outputStream = new FileOutputStream(f);
                //while ((read =is.read(bytes)) != -1) {
                //    outputStream.write(bytes, 0, read);
                //}
                //is.close();
                //
                //dirPath = tempname + ".jpg";
                //apikey.setImagedir(dirPath);
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            }
			
		} catch(Exception e) {
			System.out.println(e);
		}
		return dirPath;
	}
	
	// 캡차 입력값 비교
	public static boolean APICaptchaResult( String Capcha_Key,
											String InputText) {
		boolean result = false;
		
		try {
			String code = "1";
			String key = Capcha_Key;
			String value = InputText;
			String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code +"&key="+ key + "&value="+ value;
			
			URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", apikey.getClientId());
            con.setRequestProperty("X-Naver-Client-Secret", apikey.getClient_Secret());
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            
            String responseString = response.toString();
            
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(responseString);
            result = (boolean) object.get("result");
            
            br.close();
            System.out.println(response.toString());
		} catch ( Exception e ) {
			System.out.println(e);
		}
		return result;
	}
	
}
