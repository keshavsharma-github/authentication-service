package com.keshav.authentication.exception;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    private ErrorResponse() {
        timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String message) {
        this();
        this.message = message;
    }
   

    public ErrorResponse(int status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
    }

    public ErrorResponse(int status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorResponse [status=" + status + ", timestamp=" + timestamp + ", message=" + message + "]";
	}
}