package com.gcare.dao;

import com.gcare.config.TestConfig;
import com.gcare.dao.GenericDAO;
import com.gcare.model.Gender;
import com.gcare.model.Patient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.sql.Timestamp;
import java.util.List;

import static com.gcare.dao.EntityUtils.createPatientEntity;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PatientDAOTest {

    @Autowired
    private GenericDAO genericDAO;

    @After
    public void cleanup() {
        genericDAO.deleteAll(Patient.class);
        int patientCount = genericDAO.count(Patient.class);
        Assert.assertEquals(0, patientCount);
    }

    @Test
    public void createPatientTest() {
        Patient patient = createPatientEntity();
        Assert.assertNull(patient.getId());
        genericDAO.insert(patient);
        Assert.assertNotNull(patient.getId());
        List<Patient> patientAfterInsert = genericDAO.get(Patient.class);
        Assert.assertEquals(1, patientAfterInsert.size());
        Assert.assertEquals(patient.getFirstName(), patientAfterInsert.get(0).getFirstName());
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



}
