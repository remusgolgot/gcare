package com.gcare.model;

import com.gcare.config.TestConfig;
import com.gcare.dao.GenericDAO;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DoctorTest {

    @Autowired
    private GenericDAO genericDAO;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @After
    public void cleanup() {
        genericDAO.deleteAll(Doctor.class);
        int patientCount = genericDAO.count(Doctor.class);
        Assert.assertEquals(0, patientCount);
    }

    @Test
    public void createDoctorTest() {
        Doctor doctor = createDoctorEntity();
        Assert.assertNull(doctor.getId());
        genericDAO.insert(doctor);
        Assert.assertNotNull(doctor.getId());
        List<Doctor> doctorAfterInsertList = genericDAO.get(Doctor.class);
        Assert.assertEquals(1, doctorAfterInsertList.size());
        Assert.assertEquals(doctor.getFirstName(), doctorAfterInsertList.get(0).getFirstName());
    }

    @Test(expected = ConstraintViolationException.class)
    public void createPatientMissingGender() {
        Doctor doctor = createDoctorEntity();
        doctor.setGender(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createPatientMissingFirstName() {
        Doctor doctor = createDoctorEntity();
        doctor.setFirstName(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createPatientMissingLastName() {
        Doctor doctor = createDoctorEntity();
        doctor.setLastName(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createPatientMissingDateOfBirth() {
        Doctor doctor = createDoctorEntity();
        doctor.setDateOfBirth(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createDoctorMissingAddressCity() {
        Doctor doctor = createDoctorEntity();
        doctor.setAddressCity(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createDoctorMissingAddressCounty() {
        Doctor doctor = createDoctorEntity();
        doctor.setAddressCounty(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createDoctorMissingAddressCountry() {
        Doctor doctor = createDoctorEntity();
        doctor.setAddressCountry(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createDoctorMissingSpecialty() {
        Doctor doctor = createDoctorEntity();
        doctor.setPrimarySpecialty(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createDoctorMissingHourlyRate() {
        Doctor doctor = createDoctorEntity();
        doctor.setHourlyRate(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createDoctorNegativeHourlyRate() {
        Doctor doctor = createDoctorEntity();
        doctor.setHourlyRate(-1);
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createDoctorZeroHourlyRate() {
        Doctor doctor = createDoctorEntity();
        doctor.setHourlyRate(0);
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createDoctorMissingUUID() {
        Doctor doctor = createDoctorEntity();
        doctor.setUuid(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createDoctorUUIDIncorrectLength() {
        Doctor doctor = createDoctorEntity();
        doctor.setUuid("40878745a7c5bbf");
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createDoctorUUIDIncorrectChars() {
        Doctor doctor = createDoctorEntity();
        doctor.setUuid("40{.?745a7c5b*fZ");
        genericDAO.insert(doctor);
    }

    @Test(expected = ConstraintViolationException.class)
    @SneakyThrows
    public void createDoctorDateOfBirthInTheFuture() {
        Doctor doctor = createDoctorEntity();
        doctor.setDateOfBirth(format.parse("3018-01-01"));
        genericDAO.insert(doctor);
    }

    @SneakyThrows
    private Doctor createDoctorEntity() {

        Doctor doctor = new Doctor();
        doctor.setAddressCity("New York");
        doctor.setAddressCounty("NY");
        doctor.setAddressCountry("USA");
        doctor.setFirstName("Dan");
        doctor.setLastName("Ives");
        doctor.setGender(Gender.F);
        doctor.setHourlyRate(12);
        doctor.setPrimarySpecialty(Specialty.CARDIOLOGY);
        doctor.setDateOfBirth(format.parse("2019-01-01"));
        doctor.setUuid("44878745a7c5bbbf");
        return doctor;
    }

}
