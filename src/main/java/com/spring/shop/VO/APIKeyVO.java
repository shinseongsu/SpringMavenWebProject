package com.spring.shop.VO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class APIKeyVO {
	private String clientId;
	private String client_Secret;
	private String CAPTCHA_KEY;
	private String Imagedir;
	
	private String appId;
	private String appSecret;
	private String accessToken;
	private String accessSecretToken;
}
