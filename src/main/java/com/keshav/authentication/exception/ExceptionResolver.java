package com.keshav.authentication.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionResolver extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(final ApiException ex, final WebRequest request) {
        String errorMessage = "Internal server error occurred";
        ErrorResponse response = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), errorMessage, ex);

        logger.error("{} : {}", errorMessage, response.toString(), ex);

        return new ResponseEntity<>(response, getHeaders(), INTERNAL_SERVER_ERROR);

    }
    
    @ExceptionHandler(InvalidInputValueException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputValueException(final InvalidInputValueException ex, final WebRequest request) {
        String errorMessage = "Internal server error occurred";
        ErrorResponse response = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), errorMessage, ex);

        logger.error("{} : {}", errorMessage, response.toString(), ex);

        return new ResponseEntity<>(response, getHeaders(), INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> genericException(final ApiException ex, final WebRequest request) {
        String errorMessage = "Unexpected error occurred";
        ErrorResponse response = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), errorMessage, ex);
        logger.error("{} : {}", errorMessage, response.toString(), ex);

        return new ResponseEntity<>(response, getHeaders(), INTERNAL_SERVER_ERROR);

    }

    private HttpHeaders getHeaders() {
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return responseHeaders;
    }
}