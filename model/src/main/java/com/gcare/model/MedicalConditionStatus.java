package com.gcare.model;

public enum MedicalConditionStatus {

    HEALED("HEALED"),
    MILD("MILD"),
    SEVERE("SEVERE"),
    CRITICAL("CRITICAL");

    private final String status;

    MedicalConditionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
