package com.gcare.services;

import com.gcare.dao.ConsultationDAO;
import com.gcare.model.Consultation;
import com.gcare.model.ConsultationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationDAO consultationDAO;


    public Consultation getConsultationByID(Integer consultationID) {
        return consultationDAO.getByID(consultationID);
    }

    public Consultation addConsultation(Consultation consultation) {
        consultation.setConsultationState(ConsultationState.BOOKED);
        return consultationDAO.insert(consultation);
    }

    public void deleteConsultationByID(Integer consultationID) {
        Consultation consultation = consultationDAO.getByID(consultationID);
        if (consultation != null) {
            consultationDAO.delete(consultation);
        }
    }

    public List<Consultation> listConsultations() {
        return consultationDAO.list();
    }
}
