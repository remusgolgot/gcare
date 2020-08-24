package com.gcare.dao;

import com.gcare.config.TestConfig;
import com.gcare.model.Patient;
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

    @Test(expected = ConstraintViolationException.class)
    public void createPatientMissingDateOfBirth() {
        Patient patient = createPatientEntity();
        patient.setDateOfBirth(null);
        genericDAO.insert(patient);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createPatientMissingUUID() {
        Patient patient = createPatientEntity();
        patient.setUuid(null);
        genericDAO.insert(patient);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createPatientMissingAddressCity() {
        Patient patient = createPatientEntity();
        patient.setCity(null);
        genericDAO.insert(patient);
    }

    @Test
    public void updatePatientTest() {
        Patient patient = createPatientEntity();
        Assert.assertNull(patient.getId());
        genericDAO.insert(patient);
        Assert.assertNotNull(patient.getId());
        List<Patient> patientAfterInsertList = genericDAO.get(Patient.class);
        Assert.assertEquals(1, patientAfterInsertList.size());
        Assert.assertEquals(patient.getCity(), patientAfterInsertList.get(0).getCity());
        Timestamp creationTimestamp = patientAfterInsertList.get(0).getLastUpdateTimestamp();

        String newCity = "Linz";
        patient.setCity(newCity);
        genericDAO.update(patient);
        List<Patient> patientAfterUpdateList = genericDAO.get(Patient.class);
        Assert.assertEquals(1, patientAfterUpdateList.size());
        Assert.assertEquals(newCity, patientAfterUpdateList.get(0).getCity());
        Timestamp updateTimestamp = patientAfterUpdateList.get(0).getLastUpdateTimestamp();

        Assert.assertTrue(creationTimestamp.getTime() < updateTimestamp.getTime());
    }


}
