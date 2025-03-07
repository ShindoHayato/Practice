package com.example.practice.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final Error error;

    public ApplicationException(Error error) {
        this.error = error;
    }

    public ApplicationException(Error error, Throwable e) {
        super(e);
        this.error = error;
    }
}
