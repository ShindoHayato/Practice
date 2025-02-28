package com.example.practice.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.practice.exception.ApplicationException;
import com.example.practice.exception.Error;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({ Exception.class })
    public Error unhandledException(Exception ex) {
        log.error("unhandled Exception.", ex);
        return Error.INTERNAL_SERVER_ERROR;
    }

    @ExceptionHandler({ ApplicationException.class })
    public Error handleApplicationException(ApplicationException ex) {
        log.warn("application exception. " + ex.getError().toString());
        return ex.getError();
    }
}
