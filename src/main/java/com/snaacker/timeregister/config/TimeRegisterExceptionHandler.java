package com.snaacker.timeregister.config;

import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.exception.TimeRegisterUnauthorizedException;
import com.snaacker.timeregister.exception.TimeRegisterUserNotAllowException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class TimeRegisterExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {TimeRegisterObjectNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Object not found\n" + ex.getMessage();
        return handleExceptionInternal(
                ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {TimeRegisterBadRequestException.class})
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Bad request\n" + ex.getMessage();
        return handleExceptionInternal(
                ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {TimeRegisterUnauthorizedException.class})
    protected ResponseEntity<Object> handleUnAuthorized(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Bad request\n" + ex.getMessage();
        return handleExceptionInternal(
                ex, bodyOfResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {TimeRegisterUserNotAllowException.class})
    protected ResponseEntity<Object> handleUserNotAllow(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "User not allow\n" + ex.getMessage();
        return handleExceptionInternal(
                ex, bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }
}
