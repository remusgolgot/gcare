package com.gcare.controllers;

import com.gcare.messages.Responses;
import com.gcare.model.Doctor;
import com.gcare.services.DoctorService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    //TODO : add HashMap as result
    @GetMapping(value = "/{doctorUUID}/consultations")
    public ResponseEntity listConsultations(@PathVariable(value = "doctorUUID") String doctorUUID) {
        List results = doctorService.getConsultationsForDoctor(doctorUUID);
        HttpStatus status = HttpStatus.OK;
        String response = results.toString();
        return ResponseEntity.status(status).body(response);
    }

    //TODO : add HashMap as result
    @GetMapping(value = "/{doctorUUID}")
    public ResponseEntity getDoctorByUUID(@PathVariable(value = "doctorUUID") String doctorUUID) {
        List results = doctorService.getDoctorByUUID(doctorUUID);
        HttpStatus status = HttpStatus.OK;
        String response = results.toString();
        return ResponseEntity.status(status).body(response);
    }

    //TODO : add HashMap as result
    @GetMapping
    public ResponseEntity listDoctors() {
        List<Doctor> results = doctorService.listDoctors();
        HttpStatus status = HttpStatus.OK;
        String response = new Gson().toJson(results);
        return ResponseEntity.status(status).body(response);
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
