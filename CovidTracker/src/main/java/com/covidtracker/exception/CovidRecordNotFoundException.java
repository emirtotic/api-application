package com.covidtracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CovidRecordNotFoundException extends ApplicationException {


    public CovidRecordNotFoundException() {
        super(String.format("Record not found"));
    }

    public CovidRecordNotFoundException(String countryCode) {
        super(String.format("Record with country code=[%s] is not found", countryCode));
    }
}