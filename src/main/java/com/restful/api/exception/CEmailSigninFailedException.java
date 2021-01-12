package com.restful.api.exception;

public class CEmailSigninFailedException extends RuntimeException{
    public CEmailSigninFailedException() {
    }

    public CEmailSigninFailedException(String message) {
        super(message);
    }

    public CEmailSigninFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
