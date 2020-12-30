package com.imbac.jwt.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

public class TokenAuthenticationService {

	static final long EXPIRATION_TIME = 1000 * 60; // กำหนดอายุการใช้งานของ token
	static final String HEADER_STRING = "Authorization";
	static final String TOKEN_PREFIX = "Bearer";
	static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	// ทำหน้าที่สร้าง jwt หรือ token โดยจะใช้
	// Keys.secretKeyFor(SignatureAlgorithm.HS512) มาสร้าง secret key
	// มากขาก library jjwt มาสุ่มสร้าง key เพื่อความปลอดภัย
	static void addAuthentication(HttpServletResponse res, String username) {
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SECRET_KEY)
				.compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
//		System.out.println("Date is " + new Date(System.currentTimeMillis() + EXPIRATION_TIME));
	}

	// ทำหน้าที่เปรียบเทียบ signature ว่าตรงกับของเดิมไหม
	static Authentication getAuthentication(HttpServletRequest req) {
		String token = req.getHeader(HEADER_STRING);
		if(token == null) {
			return null;
		}

		String username = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
				.getBody()
				.getSubject();

		return username != null ?
				new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList())
				: null;
	}
}
