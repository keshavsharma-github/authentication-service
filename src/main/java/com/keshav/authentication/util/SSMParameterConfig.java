package com.keshav.authentication.util;

public class SSMParameterConfig {
	private String cognitoUserPoolId;
	private String cognitoClientId;

	public String getCognitoUserPoolId() {
		return cognitoUserPoolId;
	}
	public void setCognitoUserPoolId(String cognitoUserPoolId) {
		this.cognitoUserPoolId = cognitoUserPoolId;
	}
	public String getCognitoClientId() {
		return cognitoClientId;
	}
	public void setCognitoClientId(String cognitoClientId) {
		this.cognitoClientId = cognitoClientId;
	}
}