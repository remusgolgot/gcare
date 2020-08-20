package com.gcare.services;

import com.gcare.dao.DoctorDAO;
import com.gcare.model.Consultation;
import com.gcare.model.Doctor;
import com.gcare.model.DoctorDto;
import com.gcare.utils.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorDAO doctorDAO;

    public List<Consultation> getConsultationsForDoctor(Integer doctorID) {
        List<Consultation> list = doctorDAO.get(Consultation.class);
        return list.stream().filter(c -> c.getDoctor().getUuid().equals(doctorID)).collect(Collectors.toList());
    }

    public Doctor getDoctorByUUID(String doctorUUID) {
        return doctorDAO.getByUUID(doctorUUID);
    }

    public List listDoctors() {
        return doctorDAO.list();
    }

    public Doctor addDoctor(DoctorDto doctorDto) throws Exception {
        Doctor entity = (Doctor) ClassUtils.copyPropertiesFromDTO(Doctor.class, doctorDto);
        return doctorDAO.insert(entity);
    }

    public void deleteDoctor(Integer doctorID) {
        Doctor doctor = doctorDAO.getByID(doctorID);
        if (doctor != null) {
            doctorDAO.delete(doctor);
        }
    }

    public Doctor getDoctorById(Integer id) {
        return doctorDAO.getByID(id);
    }
}
