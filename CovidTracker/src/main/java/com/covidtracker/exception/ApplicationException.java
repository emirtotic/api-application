package com.covidtracker.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ApplicationException extends RuntimeException {

    private final String message;

    public ApplicationException(String message) {
        this.message = message;
    }
}
