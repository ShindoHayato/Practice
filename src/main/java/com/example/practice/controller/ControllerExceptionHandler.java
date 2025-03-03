package com.example.practice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.practice.exception.ApplicationException;
import com.example.practice.exception.Error;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Error> unhandledException(Exception ex) {
        log.error("unhandled Exception.", ex);
        return new ResponseEntity<>(Error.INTERNAL_SERVER_ERROR, Error.INTERNAL_SERVER_ERROR.getStatus());
    }

    @ExceptionHandler({ ApplicationException.class })
    public ResponseEntity<Error> handleApplicationException(ApplicationException ex) {
        log.warn("application exception. " + ex.getError().toString());
        return new ResponseEntity<>(ex.getError(), ex.getError().getStatus());
    }
}
