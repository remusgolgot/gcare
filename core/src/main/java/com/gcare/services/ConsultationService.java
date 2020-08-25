package com.gcare.services;

import com.gcare.dao.ConsultationDAO;
import com.gcare.model.Consultation;
import com.gcare.model.ConsultationDto;
import com.gcare.model.ConsultationState;
import com.gcare.utils.ClassUtils;
import com.gcare.utils.ConstraintViolationsErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
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

    public Consultation updateConsultation(ConsultationDto consultationDto) throws Exception {
        Consultation entity = (Consultation) ClassUtils.copyPropertiesFromDTO(Consultation.class, consultationDto);
        try {
            System.out.println("HERE");
            System.out.println(entity.toString());
            return consultationDAO.update(entity);
        } catch (ConstraintViolationException e) {
            throw new Exception(ConstraintViolationsErrorBuilder.buildErrorMessageFromException(e));
        } catch (Exception e) {
            throw e;
        }
    }
}
