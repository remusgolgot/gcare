package com.gcare.model;

import com.gcare.config.TestConfig;
import com.gcare.dao.GenericDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PatientTest {

    @Autowired
    private GenericDAO genericDAO;

    @Test
    public void createPatientTest() {
        createPatientEntity();
    }

    @Test(expected = PersistenceException.class)
    public void createPatientMissingGender() {
        Patient patient = createPatientEntity();
        patient.setGender(null);
        genericDAO.insert(patient);
    }

    @Test(expected = PersistenceException.class)
    public void createPatientMissingFirstName() {
        Patient patient = createPatientEntity();
        patient.setFirstName(null);
        genericDAO.insert(patient);
    }

    @Test(expected = PersistenceException.class)
    public void createPatientMissingLastName() {
        Patient patient = createPatientEntity();
        patient.setLastName(null);
        genericDAO.insert(patient);
    }

    @Test(expected = PersistenceException.class)
    public void createPatientMissingDateOfBirth() {
        Patient patient = createPatientEntity();
        patient.setDateOfBirth(null);
        genericDAO.insert(patient);
    }

    @Test(expected = PersistenceException.class)
    public void createPatientMissingAddressCity() {
        Patient patient = createPatientEntity();
        patient.setAddressCity(null);
        genericDAO.insert(patient);
    }

    private Patient createPatientEntity() {
        Patient patient = new Patient();
        patient.setAddressCity("Cluj-Napoca");
        patient.setAddressCounty("Cluj");
        patient.setAddressCountry("Romania");
        patient.setFirstName("Dan");
        patient.setLastName("Suzuki");
        patient.setGender(Gender.MALE);
        patient.setDateOfBirth(new Timestamp(5));
        Assert.assertNull(patient.getId());
        genericDAO.insert(patient);
        Assert.assertNotNull(patient.getId());
        return patient;
    }

}
