package com.keshav.authentication.controller;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keshav.authentication.service.AuthorizationService;
import com.keshav.authentication.to.SignInRequest;
import com.keshav.authentication.to.SignUpRequest;
import com.keshav.authentication.to.TokenInfoTo;

@RestController
@RequestMapping("/v1/auth")
public class AuthorizationController {
	private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);
	@Autowired
	public AuthorizationService authorizationService;

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
		logger.info("signUp");
		authorizationService.signUp(signUpRequest);
		return ResponseEntity.status(HttpStatus.SC_CREATED).body("User registered successfully!");
	}

	@PostMapping("/signin")
	public ResponseEntity<TokenInfoTo> signIn(@RequestBody SignInRequest signInRequest) {
		logger.info("signIn");
		TokenInfoTo tokenInfo = authorizationService.signIn(signInRequest);
		return ResponseEntity.ok(tokenInfo);
	}
}
