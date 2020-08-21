package com.gcare.dao;

import com.gcare.model.Doctor;
import com.gcare.model.Gender;
import com.gcare.model.Patient;
import com.gcare.model.Specialty;
import lombok.SneakyThrows;


import static com.gcare.utils.DateFormatUtils.DATE_FORMAT;

public class EntityUtils {

    @SneakyThrows
    static Patient createPatientEntity() {
        Patient patient = new Patient();
        patient.setAddress("Cluj-Napoca");
        patient.setCity("Cluj-Napoca");
        patient.setCounty("Cluj");
        patient.setCountryCode("RO");
        patient.setUuid("44878745a7c5bbbf");
        patient.setFirstName("Dan");
        patient.setLastName("Suzuki");
        patient.setGender(Gender.M);
        patient.setDateOfBirth(DATE_FORMAT.parse("2019-01-01"));
        return patient;
    }

    @SneakyThrows
    static Doctor createDoctorEntity() {

        Doctor doctor = new Doctor();
        doctor.setAddress("5th Avenue, nr. 65");
        doctor.setCity("New York");
        doctor.setCounty("NY");
        doctor.setCountryCode("US");
        doctor.setFirstName("Dan");
        doctor.setLastName("Ives");
        doctor.setGender(Gender.F);
        doctor.setHourlyRate(12);
        doctor.setPrimarySpecialty(Specialty.CARDIOLOGY);
        doctor.setDateOfBirth(DATE_FORMAT.parse("2019-01-01"));
        doctor.setUuid("44878745a7c5bbbf");
        return doctor;
    }
}
