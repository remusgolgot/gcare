package com.gcare.services;

import com.gcare.dao.DoctorDAO;
import com.gcare.dao.PatientDAO;
import com.gcare.model.Consultation;
import com.gcare.model.Doctor;
import com.gcare.model.Patient;
import com.gcare.model.PatientDto;
import com.gcare.utils.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
}
