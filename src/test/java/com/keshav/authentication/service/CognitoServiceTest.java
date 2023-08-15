package com.keshav.authentication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.UserType;
import com.keshav.authentication.to.SignInRequest;
import com.keshav.authentication.to.SignUpRequest;

@ExtendWith(MockitoExtension.class)
public class CognitoServiceTest {
	private static final String PASSWORD = "password";
	private static final String EMAIL = "email";
	private static final String USERNAME = "username";
	@InjectMocks
	private CognitoService underTest;
	@Mock
	private AWSCognitoIdentityProvider cognitoClient;
	private String cognitoUserPoolId = "cognitoUserPoolId";
	private String cognitoClientId = "cognitoClientId";

	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(underTest, "cognitoUserPoolId", cognitoUserPoolId);
		ReflectionTestUtils.setField(underTest, "cognitoClientId", cognitoClientId);
	}

	@Test
	public void testCreateUser() {
		SignUpRequest validSignUpRequest = new SignUpRequest(USERNAME, EMAIL, PASSWORD);
		AdminCreateUserRequest mockCreateUserRequest = mockCreateUserRequest(validSignUpRequest);
		AdminCreateUserResult result = mockCreateUserResult(validSignUpRequest);
		when(cognitoClient.adminCreateUser(eq(mockCreateUserRequest))).thenReturn(result );
		underTest.createUser(validSignUpRequest);
		verify(cognitoClient, times(1)).adminCreateUser(mockCreateUserRequest);
	}

	@Test
	public void testInitiateAuthentication() {
		SignInRequest validSignInRequest = new SignInRequest(USERNAME, PASSWORD);
		AdminInitiateAuthResult mockResult = getMockedAdminAuthResult();
		when(cognitoClient.adminInitiateAuth(any(AdminInitiateAuthRequest.class))).thenReturn(mockResult);
		AdminInitiateAuthResult actualResult = underTest.initiateAuthentication(validSignInRequest);
		verify(cognitoClient, times(1)).adminInitiateAuth(any(AdminInitiateAuthRequest.class));
		assertEquals(mockResult, actualResult);
	}

	private AdminCreateUserRequest mockCreateUserRequest(SignUpRequest signUpRequest){
		return new AdminCreateUserRequest().withUserPoolId(cognitoUserPoolId)
				.withUsername(signUpRequest.getUsername())
				.withUserAttributes(new AttributeType().withName(EMAIL).withValue(signUpRequest.getEmail()));
	}

	private AdminCreateUserResult mockCreateUserResult(SignUpRequest validSignUpRequest) {
		AdminCreateUserResult adminCreateUserResult = new AdminCreateUserResult();
		adminCreateUserResult.setUser(new UserType());
		return adminCreateUserResult;
	}

	private AdminInitiateAuthResult getMockedAdminAuthResult() {
		AdminInitiateAuthResult mockResponse = new AdminInitiateAuthResult();
		AuthenticationResultType result = new AuthenticationResultType();
		result.setAccessToken("accessToken");
		result.setIdToken("idToken");
		result.setRefreshToken("refreshToken");
		result.setExpiresIn(100);
		mockResponse.setAuthenticationResult(result );
		return mockResponse;
	}
}
