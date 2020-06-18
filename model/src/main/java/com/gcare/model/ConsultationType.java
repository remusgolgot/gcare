package com.gcare.model;

public enum ConsultationType {

    ONLINE("ONLINE"),
    IN_PERSON("IN_PERSON");

    private final String type;

    ConsultationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
