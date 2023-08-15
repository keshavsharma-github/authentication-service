package com.keshav.authentication.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParameterConfig {

	private static SSMParameterConfig config = null;

	@Autowired
	private SSMExtractorUtil ssmExtractorUtil;

	public SSMParameterConfig getConfig() {
		if (config == null) {
			config = new SSMParameterConfig();
			config.setCognitoUserPoolId(ssmExtractorUtil.getParameterValue("COGNITO_USER_POOL_ID"));
			config.setCognitoClientId(ssmExtractorUtil.getParameterValue("COGNITO_CLIENT_ID", true));
		}
		return config;
	}
}