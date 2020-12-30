package com.imbac.jwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// api ในการทดสอบ
@RestController
public class UserController {

	// ไม่ต้องใช้ token ในการเข้าถึง
	@RequestMapping("/")
	String index() {
		System.out.println("\nin index()");
		return "Welcome to \"/\" path";
	}

	// ใช้ token ในการเข้าถึง
	@RequestMapping("/users")
	String users() {
		System.out.println("\nin users()");
		return "{\"users\":[{\"id\":\"1\",\"firstName\":\"Tom\",\"lastName\":\"Cruise\",\"photo\":\"https://pbs.twimg.com/profile_images/735509975649378305/B81JwLT7.jpg\"},{\"id\":\"2\",\"firstName\":\"Maria\",\"lastName\":\"Sharapova\",\"photo\":\"https://pbs.twimg.com/profile_images/3424509849/bfa1b9121afc39d1dcdb53cfc423bf12.jpeg\"},{\"id\":\"3\",\"firstName\":\"James\",\"lastName\":\"Bond\",\"photo\":\"https://pbs.twimg.com/profile_images/664886718559076352/M00cOLrh.jpg\"}]}";
	}
}
