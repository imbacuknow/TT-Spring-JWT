package com.imbac.jwt.pojo;

// เป็น claas pojo เพื่อใช้
// แปลง JSON Parser -> POJO (กรณีรับ json มากจาก api)
// หรือ
// แปลง POJO -> JSON  (กรณีจะส่ง json ไปกับ api)
public class UserCredentials {
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
