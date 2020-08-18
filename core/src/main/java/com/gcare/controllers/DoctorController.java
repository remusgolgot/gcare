package com.gcare.controllers;

import com.gcare.messages.Responses;
import com.gcare.model.Consultation;
import com.gcare.model.Doctor;
import com.gcare.services.DoctorService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping(value = "/{doctorUUID}/consultations")
    public HashMap<String, Object> listConsultations(@PathVariable(value = "doctorUUID") String doctorUUID) {
        HashMap<String, Object> map = new HashMap<>();
        Doctor doctor = doctorService.getDoctorByUUID(doctorUUID);
        if (doctor == null) {
            map.put("error", Responses.DOCTOR_NOT_FOUND_FOR_UUID);
        } else {
            List<Consultation> consultations = doctorService.getConsultationsForDoctor(doctorUUID);
            map.put("consultations", consultations);
            map.put("nrOfConsultations", consultations.size());
        }
        return map;
    }

    @GetMapping(value = "/{doctorUUID}")
    public HashMap<String, Object> getDoctorByUUID(@PathVariable(value = "doctorUUID") String doctorUUID) {
        Doctor doctor = doctorService.getDoctorByUUID(doctorUUID);
        HashMap<String, Object> map = new HashMap<>();
        if (doctor == null) {
            map.put("error", Responses.DOCTOR_NOT_FOUND_FOR_UUID);
        } else {
            map.put("doctor", doctor);
        }
        return map;
    }

    @GetMapping
    public HashMap<String, Object> listDoctors() {
        List<Doctor> resultList = doctorService.listDoctors();
        HashMap<String, Object> map = new HashMap<>();
        map.put("doctors", resultList);
        return map;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity createDoctor(@Valid @RequestBody Doctor doctor) {
        HttpStatus status = HttpStatus.OK;
        ResponseEntity responseEntity;
        String responseString = "";
        try {
            doctorService.addDoctor(doctor);
            responseString = Responses.SUCCESSFULLY_ADDED_DOCTOR;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseString = Responses.FAILED_TO_CREATE_DOCTOR + e.getMessage();
        } finally {
            String response = new Gson().toJson(responseString);
            responseEntity = ResponseEntity.status(status).body(response);
        }
        return responseEntity;

    }
}
