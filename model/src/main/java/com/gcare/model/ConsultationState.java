package com.gcare.model;

public enum ConsultationState {

    BOOKED("BOOKED"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED");

    private final String type;

    ConsultationState(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
