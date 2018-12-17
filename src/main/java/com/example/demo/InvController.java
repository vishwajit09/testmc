package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PropertySource("classpath:application.properties")
@RequestMapping("/invoice")

public class InvController {
	@Value("${access_token_url}")
	String access_token_url;
	@Value("${Username}")
	String user_name;
	@Value("${Password}")
	String password;
	@Value("${client_id}")
	String client_id;
	@Value("${client_secret}")
	String client_secret;
	@Value("${api_url}")
	String api_url;
	
	@GetMapping("/test")
	
	public ResponseEntity<String> getInvo(){
		
		ResourceOwnerPasswordResourceDetails res = new ResourceOwnerPasswordResourceDetails();
		res.setAccessTokenUri(access_token_url);
		res.setUsername("DanskeSupplier01@tix.com");
		res.setPassword("DNSK-user01!");
		res.setClientId("DanskeSupplier");
		res.setGrantType("password");
		res.setClientSecret("Y3eY1LMzX4NPJqdZzGSb+piGnikEq9Wdwv6onjnWeZ8=");
		res.setClientAuthenticationScheme(AuthenticationScheme.form);
		
		System.out.println(access_token_url+password+client_id+client_secret);
		
		System.out.println(res);
		
		AccessTokenRequest  acc = new DefaultAccessTokenRequest();
		OAuth2RestTemplate  temp = new OAuth2RestTemplate(res,new DefaultOAuth2ClientContext(acc));
		
		String ret = temp.getForObject(api_url,String.class);
		return new ResponseEntity<>(ret,HttpStatus.OK);
	}
	
	

}
