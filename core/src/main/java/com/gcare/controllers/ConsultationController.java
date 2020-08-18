package com.gcare.controllers;

import com.gcare.messages.Responses;
import com.gcare.services.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @GetMapping(value = "/{consultationID}", produces = "application/json")
    public HashMap<String, Object> getConsultationByID(@Valid @PathVariable(value = "consultationID") Integer consultationID) {
        List consultations = consultationService.getConsultationDetails(consultationID);
        HashMap<String, Object> map = new HashMap<>();
        if (consultations.size() == 0) {
            map.put("error", Responses.CONSULTATION_NOT_FOUND);
        } else {
            map.put("consultation", consultations);
        }
        return map;
    }
}
