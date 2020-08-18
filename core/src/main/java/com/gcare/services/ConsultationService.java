package com.gcare.services;

import com.gcare.dao.ConsultationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationDAO consultationDAO;


    public List getConsultationDetails(Integer consultationID) {
        return consultationDAO.getDetails(consultationID);
    }
}
