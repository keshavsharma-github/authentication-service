package com.keshav.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.keshav.authentication.to.SignInRequest;
import com.keshav.authentication.to.SignUpRequest;
import com.keshav.authentication.to.TokenInfoTo;
import com.keshav.authentication.util.ParameterConfig;

@Service
public class CognitoService {
	private static final String PASSWORD = "PASSWORD";
	private static final String USERNAME = "USERNAME";
	private static final String EMAIL = "email";

	@Autowired
	private AWSCognitoIdentityProvider cognitoClient;
	// ParameterConfig can be used to fetch secrets from AWS Systems Manager Parameter Store
	@Autowired
	private ParameterConfig parameterConfig;

	@Value("cognito.poolId")
	private String cognitoUserPoolId;
	@Value("cognito.clientId")
	private String cognitoClientId;
	
	public AdminCreateUserResult createUser(SignUpRequest signUpRequest) {
		// Create a new user in Cognito User Pool
		AdminCreateUserRequest request = new AdminCreateUserRequest().withUserPoolId(cognitoUserPoolId)
				.withUsername(signUpRequest.getUsername())
				.withUserAttributes(new AttributeType().withName(EMAIL).withValue(signUpRequest.getEmail()));
		return cognitoClient.adminCreateUser(request);
	}

	public AdminInitiateAuthResult initiateAuthentication(SignInRequest signInRequest) {
		 // Authenticate user using Cognito
        AdminInitiateAuthRequest request = new AdminInitiateAuthRequest()
                .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .withUserPoolId(cognitoUserPoolId)
                .withClientId(cognitoClientId)
                .addAuthParametersEntry(USERNAME, signInRequest.getUsername())
                .addAuthParametersEntry(PASSWORD, signInRequest.getPassword());

        return cognitoClient.adminInitiateAuth(request);
	}
}