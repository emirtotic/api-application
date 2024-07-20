package com.countries.enums;

public enum LoggerStatus {

    OK("OK"),
    FAILED("FAILED");

    private String status;

    private LoggerStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
