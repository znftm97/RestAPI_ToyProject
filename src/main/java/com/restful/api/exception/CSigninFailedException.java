package com.restful.api.exception;

public class CSigninFailedException extends RuntimeException{
    public CSigninFailedException() {
    }

    public CSigninFailedException(String message) {
        super(message);
    }

    public CSigninFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
