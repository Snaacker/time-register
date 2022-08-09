package com.snaacker.timeregister.exception;

public class TimeRegisterBadRequestException extends TimeRegisterException{

    public TimeRegisterBadRequestException(Throwable cause){
        super(cause);
    }
    public TimeRegisterBadRequestException(String message) {
        super(message);
    }
    public TimeRegisterBadRequestException() {
        super();
    }
}
