package com.restful.api.exception;

public class CCommunicationException extends RuntimeException{

    public CCommunicationException() {
    }

    public CCommunicationException(String message) {
        super(message);
    }

    public CCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
