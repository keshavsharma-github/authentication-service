package com.keshav.authentication.to;

import static com.amazonaws.util.StringUtils.isNullOrEmpty;

public class SignInRequest {
	private String username;
	private String password;

	public SignInRequest() {
		super();
	}

	public SignInRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

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

	public boolean isValid() {
		return !isNullOrEmpty(password) && !isNullOrEmpty(username);
	}

	@Override
	public String toString() {
		return "SignInRequest [username=" + username + ", password=" + password + "]";
	}
}