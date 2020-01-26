package com.spring.shop.controller.Login;

import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleOAuth2Template;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.shop.VO.APIKeyVO;
import com.spring.shop.VO.AuthInfo;
import com.spring.shop.VO.AuthVO;
import com.spring.shop.VO.MemberVO;
import com.spring.shop.VO.SecurityUser;

@Controller
public class LoginController {
	@Inject
	private AuthInfo authInfo;
	
	@Autowired
	private FacebookConnectionFactory connectionFactory;
	@Autowired
	private OAuth2Parameters oAuth2Parameters;
	@Autowired
	private GoogleOAuth2Template googleOAuth2Template;
	@Autowired
	private TwitterConnectionFactory connectionTwitter;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	ApplicationContext context = new ClassPathXmlApplicationContext("social-context.xml");
	
	@RequestMapping(value = "/accessError", method = { RequestMethod.GET })
	public ModelAndView accessDenied(Authentication auth, ModelAndView model) {
		
		
		model.addObject("msg", "Access Denied");
		model.setViewName("common/accessError");
		
		return model;
	}
	
	@RequestMapping(value = "/Register", method = { RequestMethod.GET })
	public String Resgister() {
		System.out.println("회원가입 들어가기~ ");
		
		return "Register/Register";
	}
	
	@RequestMapping(value = "/Login", method = { RequestMethod.GET })
	public String customLogin(HttpServletResponse response, Model model) {
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth1Operations oauth1Operations = connectionTwitter.getOAuthOperations();
		
		oAuth2Parameters = (OAuth2Parameters) context.getBean("oAuth2Parameters");
		String facebook_url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
	    
		oAuth2Parameters = (OAuth2Parameters) context.getBean("googleOAuth2Parameters");
		String google_url = googleOAuth2Template.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
		
		OAuthToken requestToken = oauth1Operations.fetchRequestToken("http://127.0.0.1:7000/twitterSignInCallback", null);
		String twitter_url = oauth1Operations.buildAuthenticateUrl(requestToken.getValue(), OAuth1Parameters.NONE );
		
		model.addAttribute("twitter_url", twitter_url);
		model.addAttribute("google_url", google_url);
        model.addAttribute("facebook_url", facebook_url);
        
        System.out.println("/twitter_url" + twitter_url);
        System.out.println("/google"+ google_url);
        System.out.println("/facebook" + facebook_url);
		
		System.out.println("custom Login");
		
		return "Login/Login";
	}
	
	@RequestMapping(value = "/facebookSignInCallback", method = { RequestMethod.GET, RequestMethod.POST })
	public String facebookSignInCallback(@RequestParam String code, HttpSession session, HttpServletRequest request) throws Exception {
		try {
			oAuth2Parameters = (OAuth2Parameters) context.getBean("oAuth2Parameters");
			String redirectUri = oAuth2Parameters.getRedirectUri();
			System.out.println("Redirect URI: " + redirectUri);
			System.out.println("Code: " + code);
			
			OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
			AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, redirectUri, null);
			String accessToken = accessGrant.getAccessToken();
			System.out.println("AccessToken: " + accessToken);
			Long expireTime = accessGrant.getExpireTime();
			
			if(expireTime != null && expireTime < System.currentTimeMillis()) {
				accessToken = accessGrant.getRefreshToken();
				logger.info("accessToken is expired. refresh token = {}", accessToken);
			}
			
			Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
			Facebook facebook = connection == null ? new FacebookTemplate(accessToken) : connection.getApi();
			UserOperations userOperations = facebook.userOperations();
			
			try {
				String[] fields = {"id", "email", "name"};
				User userProfile = facebook.fetchObject("me", User.class, fields);
				System.out.println("유저이메일 : " + userProfile.getEmail());
				System.out.println("유저 id : " + userProfile.getId());
				System.out.println("유저 name : " + userProfile.getName());
				
				MemberVO vo = new MemberVO();
				AuthVO authvo = new AuthVO();
				
				authvo.setAuth("ROLE_SOCIAL");
				authvo.setUserid(userProfile.getId());
				
				List<AuthVO> list = Arrays.asList(authvo);
				
				//vo.setUserid(userProfile.getId());
				vo.setUserName(userProfile.getName());
				vo.setUserid(userProfile.getName());
				vo.setAuthList(list);
				vo.setUserpw("unknown");
				vo.setEmail(userProfile.getEmail());
				vo.setEnabled(true);
				
				session.setAttribute("user_id", vo.getUserName());
				
				UserDetails user = new SecurityUser(vo);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); 
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			} catch(MissingAuthorizationException e ) { 
				e.printStackTrace();
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/googleSignInCallback", method = { RequestMethod.GET, RequestMethod.POST } )
	public String googleSignInCallback(HttpServletRequest request, HttpSession session) throws Exception {
		oAuth2Parameters = (OAuth2Parameters) context.getBean("googleOAuth2Parameters");
		String code = request.getParameter("code");
		
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("code", code);
		parameters.add("client_id", authInfo.getClientId());
		parameters.add("client_secret", authInfo.getClientSecret());
		parameters.add("redirect_uri", oAuth2Parameters.getRedirectUri());
		parameters.add("grant_type", "authorization_code");
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
	    ResponseEntity<Map> responseEntity = restTemplate.exchange("https://www.googleapis.com/oauth2/v4/token", HttpMethod.POST, requestEntity, Map.class);
	    Map<String, Object> responseMap = responseEntity.getBody();
		
	    String[] tokens = ((String)responseMap.get("id_token")).split("\\.");
	    Base64 base64 = new Base64(true);
	    String body = new String(base64.decode(tokens[1]));
	        
	    System.out.println(tokens.length);
	    System.out.println(new String(Base64.decodeBase64(tokens[0]), "utf-8"));
	    System.out.println(new String(Base64.decodeBase64(tokens[1]), "utf-8"));
	     
	    String resultString = new String(Base64.decodeBase64(tokens[1]), "utf-8");
	     
	    JSONParser json = new JSONParser();
	    Object obj = json.parse(resultString);
	    JSONObject jsonobject = (JSONObject) obj;
	     
	    System.out.println("name:" + (String) jsonobject.get("name"));
	    System.out.println("picture:" + (String) jsonobject.get("picture"));
	    System.out.println("given_name:" + (String) jsonobject.get("given_name"));
	    System.out.println("family_name:" + (String) jsonobject.get("family_name"));
	    System.out.println("locale:" + (String) jsonobject.get("locale"));
	 
	    ObjectMapper mapper = new ObjectMapper();
	    Map<String, String> result = mapper.readValue(body, Map.class);
	    System.out.println(result.get(""));
	     
	    MemberVO vo = new MemberVO();
	    AuthVO authvo = new AuthVO();
	     
	    authvo.setAuth("ROLE_SOCIAL");
		authvo.setUserid((String) jsonobject.get("name"));

		List<AuthVO> list = Arrays.asList(authvo);
			
		vo.setUserName((String) jsonobject.get("name"));
		vo.setUserid((String) jsonobject.get("name"));
		vo.setAuthList(list);
		vo.setUserpw("unknown");
		vo.setEmail("");
		vo.setEnabled(true);
			
		session.setAttribute("user_id", vo.getUserName());
			
		UserDetails user = new SecurityUser(vo);
		 
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); 
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
		SecurityContextHolder.getContext().setAuthentication(authentication);
		 
	    return "redirect:/";
	}
	
	@RequestMapping(value = "/twitterSignInCallback", method = { RequestMethod.GET, RequestMethod.POST })
	public String twitterCallback(HttpServletRequest request, HttpSession session) {
		
		System.out.println("!!!!!!!1");
		
		APIKeyVO vo = (APIKeyVO) context.getBean("twitterAPI");
		
		System.out.println("==============");
		System.out.println(vo.getAppId());
		System.out.println(vo.getAppSecret());
		System.out.println(vo.getAccessToken());
		System.out.println(vo.getAccessSecretToken());
		System.out.println("==============");
		
		Twitter twitter = new TwitterTemplate(vo.getAppId(), vo.getAppSecret(), vo.getAccessToken(), vo.getAccessSecretToken());
		
		TwitterProfile profile = twitter.userOperations().getUserProfile();
		
		String profileId = twitter.userOperations().getScreenName();
		long profileName = twitter.userOperations().getProfileId();
		
		System.out.println(profile);
		System.out.println(profileName);
		System.out.println(profileId + "!!!!!!!!!!!!!!");
		
		MemberVO membervo = new MemberVO();
	    AuthVO authvo = new AuthVO();
	     
	    authvo.setAuth("ROLE_SOCIAL");
		authvo.setUserid(profileId);

		List<AuthVO> list = Arrays.asList(authvo);
			
		membervo.setUserName(profileId);
		membervo.setUserid(profileId);
		membervo.setAuthList(list);
		membervo.setUserpw("unknown");
		membervo.setEmail("");
		membervo.setEnabled(true);
			
		session.setAttribute("user_id", membervo.getUserName());
			
		UserDetails user = new SecurityUser(membervo);
		 
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); 
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
		SecurityContextHolder.getContext().setAuthentication(authentication);
		 
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/Logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String customLogout() {
		System.out.println("custom logout");
		return "redirect:/";
	}
	
}
