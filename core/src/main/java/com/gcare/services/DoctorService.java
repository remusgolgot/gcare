package com.gcare.services;

import com.gcare.dao.DoctorDAO;
import com.gcare.model.Consultation;
import com.gcare.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorDAO doctorDAO;

    public List<Consultation> getConsultationsForDoctor(String doctorUUID) {
        List<Consultation> list = doctorDAO.get(Consultation.class);
        return list.stream().filter(c -> c.getDoctor().getUuid().equals(doctorUUID)).collect(Collectors.toList());
    }

    public Doctor getDoctorByUUID(String doctorUUID) {
        return doctorDAO.getByUUID(doctorUUID);
    }

    public List listDoctors() {
        return doctorDAO.list();
    }

    public Doctor addDoctor(Doctor doctor) {
        return doctorDAO.insert(doctor);
    }

    public void deleteDoctor(String doctorUUID) {
        Doctor doctor = doctorDAO.getByUUID(doctorUUID);
        if (doctor != null) {
            doctorDAO.delete(doctor);
        }
    }
}
