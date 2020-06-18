package com.gcare.model;

public enum MarkerTargetStatus {

    MET("MET"),
    ON_TRACK("ON_TRACK"),
    OFF_COURSE("OFF_COURSE"),
    PUSHED_BACK("PUSHED_BACK"),
    CANCELLED("CANCELLED"),
    FAILED("FAILED");

    private final String type;

    MarkerTargetStatus(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
