package com.gcare.dao;

import com.gcare.model.Doctor;
import com.gcare.model.Gender;
import com.gcare.model.Patient;
import com.gcare.model.Specialty;
import lombok.SneakyThrows;
import java.sql.Timestamp;

import static com.gcare.utils.DateFormatUtils.DATE_FORMAT;

public class EntityUtils {

    static Patient createPatientEntity() {
        Patient patient = new Patient();
        patient.setAddressCity("Cluj-Napoca");
        patient.setAddressCounty("Cluj");
        patient.setAddressCountry("Romania");
        patient.setFirstName("Dan");
        patient.setLastName("Suzuki");
        patient.setGender(Gender.M);
        patient.setDateOfBirth(new Timestamp(5));
        return patient;
    }

    @SneakyThrows
    static Doctor createDoctorEntity() {

        Doctor doctor = new Doctor();
        doctor.setAddressCity("New York");
        doctor.setAddressCounty("NY");
        doctor.setAddressCountry("USA");
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
