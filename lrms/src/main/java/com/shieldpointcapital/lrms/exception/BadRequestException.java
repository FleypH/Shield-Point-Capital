package com.shieldpointcapital.lrms.exception;

public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message){
        super(message);
    }
}
