package com.gcare.model;

import com.gcare.config.TestConfig;
import com.gcare.dao.GenericDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DoctorTest {

    @Autowired
    private GenericDAO genericDAO;

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
        List<Doctor> doctorAfterInsert = genericDAO.get(Doctor.class);
        Assert.assertEquals(1, doctorAfterInsert.size());
        Assert.assertEquals(doctor.getFirstName(), doctorAfterInsert.get(0).getFirstName());
    }

    @Test(expected = PersistenceException.class)
    public void createPatientMissingGender() {
        Doctor doctor = createDoctorEntity();
        doctor.setGender(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = PersistenceException.class)
    public void createPatientMissingFirstName() {
        Doctor doctor = createDoctorEntity();
        doctor.setFirstName(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = PersistenceException.class)
    public void createPatientMissingLastName() {
        Doctor doctor = createDoctorEntity();
        doctor.setLastName(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = PersistenceException.class)
    public void createPatientMissingDateOfBirth() {
        Doctor doctor = createDoctorEntity();
        doctor.setDateOfBirth(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = PersistenceException.class)
    public void createDoctorMissingAddressCity() {
        Doctor doctor = createDoctorEntity();
        doctor.setAddressCity(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = PersistenceException.class)
    public void createDoctorMissingAddressCounty() {
        Doctor doctor = createDoctorEntity();
        doctor.setAddressCounty(null);
        genericDAO.insert(doctor);
    }


    @Test(expected = PersistenceException.class)
    public void createDoctorMissingAddressCountry() {
        Doctor doctor = createDoctorEntity();
        doctor.setAddressCountry(null);
        genericDAO.insert(doctor);
    }


    @Test(expected = PersistenceException.class)
    public void createDoctorMissingSpecialty() {
        Doctor doctor = createDoctorEntity();
        doctor.setPrimarySpecialty(null);
        genericDAO.insert(doctor);
    }

    @Test(expected = PersistenceException.class)
    public void createDoctorMissingHourlyRate() {
        Doctor doctor = createDoctorEntity();
        doctor.setHourlyRate(null);
        genericDAO.insert(doctor);
    }

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
        doctor.setDateOfBirth(Date.from(Instant.now()));
        doctor.setUuid("4487-8745-a7c5-bbbf");
        return doctor;
    }

}
