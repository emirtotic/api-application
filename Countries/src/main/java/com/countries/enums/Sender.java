package com.countries.enums;

public enum Sender {

    COVID_TRACKER_APP("Covid Tracker App");

    private String name;

    private Sender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
