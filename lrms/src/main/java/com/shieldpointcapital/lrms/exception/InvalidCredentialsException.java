package com.shieldpointcapital.lrms.exception;

public class InvalidCredentialsException extends RuntimeException {
    
    public InvalidCredentialsException (String message) {
        super(message);
    }
}
