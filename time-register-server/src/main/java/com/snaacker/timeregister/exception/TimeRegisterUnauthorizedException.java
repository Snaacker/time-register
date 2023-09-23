package com.snaacker.timeregister.exception;

public class TimeRegisterUnauthorizedException extends TimeRegisterException {
    public TimeRegisterUnauthorizedException(String message) {
        super(message);
    }

    public TimeRegisterUnauthorizedException() {
        super();
    }
}
