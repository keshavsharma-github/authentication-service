package com.keshav.authentication.exception;

public class InvalidInputValueException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final ErrorResponse errorResponse;

	public InvalidInputValueException(final ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public InvalidInputValueException(final ErrorResponse errorResponse, final Throwable exp) {
		super(errorResponse.getMessage(), exp);
		this.errorResponse = errorResponse;
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}
}