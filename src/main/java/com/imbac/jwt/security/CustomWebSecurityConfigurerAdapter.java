package com.imbac.jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// start here! //
// เป้น class ที่เอา properties ของ abstract class มาใช้งาน
// และเพื่อเปลี่ยนแปลงการทำงานเดิม เราจะใช้ @Override ในการแก้ไข
@Configuration //ให้spring security มาอ่าน config
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("imbac")
				.password(passwordEncoder().encode("1234"))
				.authorities("ADMIN");
		// กำหนด username=imbac and password=1234
	}

	// เป้น overload medthod ที่รับค่า HttpSecurity
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/").permitAll()
				// ให้ path / เข้าถึงได้เลย
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				// ให้ path /login เข้าถึงแบบ POST และ path /xxxx อื่นๆ
				// จะถูกป้องกันโดย http method 403 forbidden
				.anyRequest().authenticated()
				// path อื่นทั้งหมดต่อง authenticate
				.and()
				.addFilterBefore(
						// path /login ต่องถูกรับรองสิทธิ์ก่อนเพื่อสร้าง UsernamePasswordAuthenticationToken หรือ null
						// โดยคลาส JWTAuthenticationProcessingFilter
						// ทำการแทรกแซง (filter chain) request ของ path /login ให้ทำบางแย่างก่อน
						new JWTAuthenticationProcessingFilter(
								"/login",
								authenticationManager()
						),
						UsernamePasswordAuthenticationFilter.class
				)
				.addFilterBefore(
						new JWTGenericFilterBean(),
						UsernamePasswordAuthenticationFilter.class
				);
	}
}
