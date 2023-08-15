package com.keshav.authentication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.keshav.authentication.service.AuthorizationService;
import com.keshav.authentication.to.SignInRequest;
import com.keshav.authentication.to.SignUpRequest;
import com.keshav.authentication.to.TokenInfoTo;

@ExtendWith(MockitoExtension.class)
public class AuthorizationControllerTest {
    private static final String PASSWORD = "password";
	private static final String EMAIL = "email";
	private static final String USERNAME = "username";

	@InjectMocks
    private AuthorizationController underTest;

    @Mock
    private AuthorizationService authorizationService;

    @Test
    public void testSignUp() {
    	SignUpRequest signUpRequest = new SignUpRequest(USERNAME, EMAIL, PASSWORD);
    	doNothing().when(authorizationService).signUp(eq(signUpRequest));
		ResponseEntity<String> actualResponse = underTest.signUp(signUpRequest);

		verify(authorizationService, times(1)).signUp(eq(signUpRequest));
        assertNotNull(actualResponse);
        assertEquals(HttpStatus.SC_CREATED, actualResponse.getStatusCode().value());
        assertTrue("User registered successfully!".equals(actualResponse.getBody()));
    }

    @Test
    public void testSignIn() {
        TokenInfoTo tokenInfo = new TokenInfoTo();
        SignInRequest signInRequest = new SignInRequest(USERNAME, PASSWORD);
        when(authorizationService.signIn(eq(signInRequest))).thenReturn(tokenInfo);
        ResponseEntity<TokenInfoTo> actualResponse = underTest.signIn(signInRequest);
        
        verify(authorizationService, times(1)).signIn(eq(signInRequest));
        assertEquals(tokenInfo, actualResponse.getBody());
    }
}