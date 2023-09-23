package com.snaacker.timeregister.exception;

public class TimeRegisterException extends RuntimeException {
    public TimeRegisterException(Throwable cause) {
        super(cause);
    }

    public TimeRegisterException(String message) {
        super(message);
    }

    public TimeRegisterException() {
        super();
    }
}
