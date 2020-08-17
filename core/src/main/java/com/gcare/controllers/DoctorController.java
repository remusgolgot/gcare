package com.gcare.controllers;

import com.gcare.model.Doctor;
import com.gcare.services.DoctorService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping(value = "/{doctorUUID}/consultations")
    public ResponseEntity listConsultations(@PathVariable(value = "doctorUUID") String doctorUUID) {
        List results = doctorService.getConsultationsForDoctor(doctorUUID);
        HttpStatus status = HttpStatus.OK;
        String response = results.toString();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(value = "/{doctorUUID}")
    public ResponseEntity getDoctorByUUID(@PathVariable(value = "doctorUUID") String doctorUUID) {
        List results = doctorService.getDoctorByUUID(doctorUUID);
        HttpStatus status = HttpStatus.OK;
        String response = results.toString();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping
    public ResponseEntity listDoctors() {
        List<Doctor> results = doctorService.listDoctors();
        HttpStatus status = HttpStatus.OK;
        String response = new Gson().toJson(results);
        return ResponseEntity.status(status).body(response);
    }

}
