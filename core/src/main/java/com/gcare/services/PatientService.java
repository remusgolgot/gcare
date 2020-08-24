package com.gcare.services;

import com.gcare.dao.PatientDAO;
import com.gcare.model.Doctor;
import com.gcare.model.DoctorDto;
import com.gcare.model.Patient;
import com.gcare.model.PatientDto;
import com.gcare.utils.ClassUtils;
import com.gcare.utils.ConstraintViolationsErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientDAO patientDAO;

    public Patient getPatientByUUID(String patientUUID) {
        return patientDAO.getByUUID(patientUUID);
    }

    public Patient getPatientByID(int patientID) {
        return patientDAO.getByID(patientID);
    }

    public Patient addPatient(PatientDto patientDto) throws Exception {
        return patientDAO.insert((Patient) ClassUtils.copyPropertiesFromDTO(Patient.class, patientDto));
    }

    public void deletePatient(Integer patientID) {
        Patient patient = patientDAO.getByID(patientID);
        if (patient != null) {
            patientDAO.delete(patient);
        }
    }

    public Patient getPatientById(Integer id) {
        return patientDAO.getByID(id);
    }

    public List<Patient> listPatients() {
        return patientDAO.list();
    }

    public Patient updatePatient(PatientDto patientDto) throws Exception {
        Patient entity = (Patient) ClassUtils.copyPropertiesFromDTO(Patient.class, patientDto);
        try {
            return patientDAO.update(entity);
        } catch (ConstraintViolationException e) {
            throw new Exception(ConstraintViolationsErrorBuilder.buildErrorMessageFromException(e));
        } catch (Exception e) {
            throw e;
        }
    }
}
