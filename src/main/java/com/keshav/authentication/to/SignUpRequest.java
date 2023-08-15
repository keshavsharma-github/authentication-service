package com.keshav.authentication.to;

import static com.amazonaws.util.StringUtils.isNullOrEmpty;

public class SignUpRequest {
	private String username;
	private String email;
	private String password;

	public SignUpRequest() {
		super();
	}

	public SignUpRequest(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValid() {
		return !isNullOrEmpty(email) && !isNullOrEmpty(password) && !isNullOrEmpty(username);
	}

	@Override
	public String toString() {
		return "SignUpRequest [username=" + username + ", email=" + email + ", password=" + password + "]";
	}
}