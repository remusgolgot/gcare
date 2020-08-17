package com.gcare.services;

import com.gcare.dao.DoctorDAO;
import com.gcare.model.Consultation;
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

    public List getDoctorByUUID(String doctorUUID) {
       return doctorDAO.getByUUID(doctorUUID);
    }

    public List listDoctors() {
            return doctorDAO.list();
    }
}
