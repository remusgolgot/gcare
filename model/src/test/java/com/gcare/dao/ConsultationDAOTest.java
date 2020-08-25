package com.gcare.dao;

import com.gcare.config.TestConfig;
import com.gcare.model.Consultation;
import com.gcare.model.Doctor;
import com.gcare.model.Patient;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.List;

import static com.gcare.dao.EntityUtils.*;
import static com.gcare.utils.DateFormatUtils.DATE_FORMAT;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ConsultationDAOTest {

    @Autowired
    private GenericDAO genericDAO;

    @After
    public void cleanup() {
        genericDAO.deleteAll(Consultation.class);
        genericDAO.deleteAll(Patient.class);
        genericDAO.deleteAll(Doctor.class);
        int consultationCount = genericDAO.count(Consultation.class);
        Assert.assertEquals(0, consultationCount);
    }

    @Test
    public void createConsultationTest() {
        Doctor doctor = createDoctorEntity();
        Assert.assertNull(doctor.getId());
        genericDAO.insert(doctor);

        Patient patient = createPatientEntity();
        Assert.assertNull(patient.getId());
        genericDAO.insert(patient);

        Consultation consultation = createConsultationEntity();
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);

        Assert.assertNull(consultation.getId());
        genericDAO.insert(consultation);
        Assert.assertNotNull(consultation.getId());
        List<Consultation> consultationAfterInsertList = genericDAO.get(Consultation.class);
        Assert.assertEquals(1, consultationAfterInsertList.size());
        Assert.assertEquals(consultation.getNotes(), consultationAfterInsertList.get(0).getNotes());
        Assert.assertEquals(consultation.getConsultationState(), consultationAfterInsertList.get(0).getConsultationState());
    }

    @Test(expected = ConstraintViolationException.class)
    public void createConsultationMissingDate() {
        Consultation consultation = createConsultationEntity();
        consultation.setDate(null);
        genericDAO.insert(consultation);
    }

    @Test(expected = ConstraintViolationException.class)
    @SneakyThrows
    public void createDoctorDateInThePast() {
        Consultation consultation = createConsultationEntity();
        consultation.setDate(DATE_FORMAT.parse("2018-01-01"));
        genericDAO.insert(consultation);
    }

    @Test
    public void updateConsultationTest() {
        Doctor doctor = createDoctorEntity();
        Assert.assertNull(doctor.getId());
        genericDAO.insert(doctor);

        Patient patient = createPatientEntity();
        Assert.assertNull(patient.getId());
        genericDAO.insert(patient);

        Consultation consultation = createConsultationEntity();
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);

        Assert.assertNull(consultation.getId());
        genericDAO.insert(consultation);
        Assert.assertNotNull(consultation.getId());

        List<Consultation> consultationsAfterInsertList = genericDAO.get(Consultation.class);
        Assert.assertEquals(1, consultationsAfterInsertList.size());
        Assert.assertEquals(consultation.getNotes(), consultationsAfterInsertList.get(0).getNotes());
        Timestamp creationTimestamp = consultationsAfterInsertList.get(0).getLastUpdateTimestamp();

        String newNotes = consultation.getNotes() + " etc.";
        consultation.setNotes(newNotes);
        genericDAO.update(consultation);
        List<Consultation> consultationAfterUpdateList = genericDAO.get(Consultation.class);
        Assert.assertEquals(1, consultationAfterUpdateList.size());
        Assert.assertEquals(newNotes, consultationAfterUpdateList.get(0).getNotes());
        Timestamp updateTimestamp = consultationAfterUpdateList.get(0).getLastUpdateTimestamp();

        Assert.assertTrue(creationTimestamp.getTime() < updateTimestamp.getTime());
    }


}
