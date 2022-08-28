package com.snaacker.timeregister.exception;

public class TimeRegisterUserNotAllowException extends TimeRegisterException{

    public TimeRegisterUserNotAllowException(Throwable cause){
        super(cause);
    }
    public TimeRegisterUserNotAllowException(String message) {
        super(message);
    }
    public TimeRegisterUserNotAllowException() {
        super();
    }
}
