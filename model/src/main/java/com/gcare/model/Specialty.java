package com.gcare.model;

public enum Specialty {

    NEUROLOGY("Neurology"),
    NEPHROLOGY("Nephrology"),
    PEDIATRICS("Pediatrics"),
    NUTRITION("Nutrition"),
    GERIATRICS("Geriatrics"),
    HEPATOLOGY("Hepatology"),
    HEMATOLOGY("Hematology"),
    ONCOLOGY("Oncology"),
    CARDIOLOGY("Cardiology"),
    INFECTIOUS_DISEASE("InfectiousDisease"),
    RHEUMATOLOGY("Rheumatology");

    private final String specialtyName;

    Specialty(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }
}
