package com.keshav.authentication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.keshav.authentication.exception.InvalidInputValueException;
import com.keshav.authentication.to.SignInRequest;
import com.keshav.authentication.to.SignUpRequest;
import com.keshav.authentication.to.TokenInfoTo;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest {
	private static final String PASSWORD = "password";
	private static final String EMAIL = "email";
	private static final String USERNAME = "username";

	@InjectMocks
	private AuthorizationService underTest;

	@Mock
	private CognitoService cognitoService;

	@Test
	public void testSignUp_ValidRequest() throws Exception {
		SignUpRequest validSignUpRequest = new SignUpRequest(USERNAME, EMAIL, PASSWORD);
		doReturn(null).when(cognitoService).createUser(eq(validSignUpRequest));

		underTest.signUp(validSignUpRequest);
		verify(cognitoService, times(1)).createUser(eq(validSignUpRequest));
	}

	@Test
	public void testSignUp_InvalidRequest() {
		SignUpRequest invalidSignUpRequest = new SignUpRequest();
		assertThrows(InvalidInputValueException.class, () -> {
			underTest.signUp(invalidSignUpRequest);
		});
	}

	@Test
	public void testSignIn_ValidRequest() throws Exception {
		SignInRequest validSignInRequest = new SignInRequest(USERNAME, PASSWORD);
		AdminInitiateAuthResult mockResponse = getMockedAdminAuthResult();
		when(cognitoService.initiateAuthentication(eq(validSignInRequest))).thenReturn(mockResponse );

		TokenInfoTo resultTokenInfo = underTest.signIn(validSignInRequest);

		assertEquals(mockResponse.getAuthenticationResult().getIdToken(), resultTokenInfo.getIdToken());
		assertEquals(mockResponse.getAuthenticationResult().getAccessToken(), resultTokenInfo.getAccessToken());
		assertEquals(mockResponse.getAuthenticationResult().getRefreshToken(), resultTokenInfo.getRefreshToken());
		assertEquals(Long.valueOf(mockResponse.getAuthenticationResult().getExpiresIn()), resultTokenInfo.getExpiresIn());
		verify(cognitoService, times(1)).initiateAuthentication(eq(validSignInRequest));
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

	@Test
	public void testSignIn_InvalidRequest() {
		SignInRequest invalidSignInRequest = new SignInRequest(null, null);
		assertThrows(InvalidInputValueException.class, () -> {
			underTest.signIn(invalidSignInRequest);
		});
	}
}