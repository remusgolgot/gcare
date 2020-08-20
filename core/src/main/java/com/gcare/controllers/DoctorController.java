package com.gcare.controllers;

import com.gcare.messages.Responses;
import com.gcare.model.Consultation;
import com.gcare.model.Doctor;
import com.gcare.model.DoctorDto;
import com.gcare.services.DoctorService;
import com.gcare.utils.GsonUtils;
import com.google.gson.*;
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

    @GetMapping(value = "/{doctorID}/consultations")
    public ResponseEntity listConsultations(@PathVariable(value = "doctorID") Integer doctorID) {
        JsonObject jsonResponse = new JsonObject();
        Doctor doctor = doctorService.getDoctorById(doctorID);
        if (doctor == null) {
            jsonResponse.addProperty("error", Responses.DOCTOR_NOT_FOUND_FOR_ID);
        } else {
            List<Consultation> consultations = doctorService.getConsultationsForDoctor(doctorID);
            jsonResponse.addProperty("consultations", GsonUtils.gson.toJson(consultations));
            jsonResponse.addProperty("nrOfConsultations", consultations.size());
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @GetMapping(value = "/{doctorID}")
    public ResponseEntity getDoctorByID(@PathVariable(value = "doctorID") Integer doctorID) {
        JsonObject jsonResponse = new JsonObject();
        Doctor doctor = doctorService.getDoctorById(doctorID);

        if (doctor == null) {
            jsonResponse.addProperty("error", Responses.DOCTOR_NOT_FOUND_FOR_ID);
        } else {
            jsonResponse.addProperty("doctor", doctor.toString());
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @GetMapping
    public ResponseEntity listDoctors() {
        List<Doctor> resultList = doctorService.listDoctors();

        String data = GsonUtils.gson.toJson(resultList);
        JsonArray jsonArray = new JsonParser().parse(data).getAsJsonArray();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.add("doctors", jsonArray);
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity createDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        String errorString = null;
        Doctor doctorToAdd;
        JsonObject jsonResponse = new JsonObject();
        try {
            doctorToAdd = doctorService.addDoctor(doctorDto);
            if (doctorToAdd != null) {
                jsonResponse.addProperty("doctor", doctorToAdd.toString());
            }
            jsonResponse.addProperty("response", Responses.SUCCESSFULLY_ADDED_DOCTOR);
        } catch (Exception e) {
            errorString = e.getMessage();
        } finally {
            if (errorString != null) {
                jsonResponse.addProperty("error", Responses.FAILED_TO_CREATE_DOCTOR + " " + errorString);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @DeleteMapping(value = "/{doctorID}")
    public ResponseEntity deleteDoctorByID(@PathVariable(value = "doctorID") Integer doctorID) {
        String errorString = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            Doctor doctor = doctorService.getDoctorById(doctorID);
            if (doctor == null) {
                errorString = Responses.FAILED_TO_DELETE_DOCTOR + " : " + Responses.DOCTOR_NOT_FOUND_FOR_ID;
            } else {
                doctorService.deleteDoctor(doctorID);
                jsonResponse.addProperty("response", Responses.SUCCESSFULLY_DELETED_DOCTOR);
            }
        } catch (Exception e) {
            errorString = Responses.FAILED_TO_DELETE_DOCTOR + " : " + e.getMessage();
        } finally {
            if (errorString != null) {
                jsonResponse.addProperty("error", errorString);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }
}
