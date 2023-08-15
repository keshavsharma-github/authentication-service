package com.keshav.authentication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keshav.authentication.exception.ApiException;
import com.keshav.authentication.exception.ErrorResponse;
import com.keshav.authentication.exception.InvalidInputValueException;
import com.keshav.authentication.to.SignInRequest;
import com.keshav.authentication.to.SignUpRequest;
import com.keshav.authentication.to.TokenInfoTo;

@Service
public class AuthorizationService {

	public static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

	@Autowired
	private CognitoService cognitoService;

	public void signUp(SignUpRequest signUpRequest) {
		if (signUpRequest == null || !signUpRequest.isValid()) {
			logger.error("Invalid SignUpRequest");
			ErrorResponse errorResponse = new ErrorResponse("Invalid SignUpRequest");
			throw new InvalidInputValueException(errorResponse);
		}
		try {
			cognitoService.createUser(signUpRequest);
		} catch (Exception e) {
			logger.error("Unable to create User", e);
			ErrorResponse errorResponse = new ErrorResponse("Unable to create User");
			throw new ApiException(errorResponse, e);
		}
	}

	public TokenInfoTo signIn(SignInRequest signInRequest) {
		if (signInRequest == null || !signInRequest.isValid()) {
			logger.error("Invalid SignInRequest");
			ErrorResponse errorResponse = new ErrorResponse("Invalid SignInRequest");
			throw new InvalidInputValueException(errorResponse);
		}
		TokenInfoTo tokenInfoTo = null;
		try {
			tokenInfoTo = cognitoService.initiateAuthentication(signInRequest);
		} catch (Exception e) {
			logger.error("Unable to signIn User", e);
			ErrorResponse errorResponse = new ErrorResponse("Unable to signIn User");
			throw new ApiException(errorResponse, e);
		}
		return tokenInfoTo;
	}
}