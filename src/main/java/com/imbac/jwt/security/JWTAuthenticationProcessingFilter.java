package com.imbac.jwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imbac.jwt.pojo.UserCredentials;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

// เป้น class ที่เอา properties ของ abstract class มาใช้งาน
// การทำงานจะอยู่ระหว่างการส่ง request ผ่าน url ซึ่งเราจะใช้มันจับ path /login
// เผื่อเปลี่ยนจาก basic authentication เป็น JWT Authentication แทน
public class JWTAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

	public JWTAuthenticationProcessingFilter(String url, AuthenticationManager manager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(manager);
	}

	@Override // method นี้จะสร้าง UsernamePasswordAuthenticationToken และตรวจสอบดูผล authen
			// ว่าผ่านไหม
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		UserCredentials credentials = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);

		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
				credentials.getUsername(), credentials.getPassword(), Collections.emptyList())
		// เอาไปค้นว่ามี Username Password อยู่รึเปล่า ใน class
		// CustomWebSecurityConfigurerAdapter
		);
	}

	// ทำงานเมื่อผลการ authenticate สำเร็จ
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		TokenAuthenticationService.addAuthentication(response, authResult.getName());
	}
}
