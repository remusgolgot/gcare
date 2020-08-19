package com.gcare.controllers;

import com.gcare.messages.Responses;
import com.gcare.model.Consultation;
import com.gcare.model.Doctor;
import com.gcare.services.DoctorService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

import static com.gcare.messages.Responses.SUCCESSFULLY_DELETED_DOCTOR;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    private static final Gson gson = new GsonBuilder().create();

    @GetMapping(value = "/{doctorUUID}/consultations")
    public ResponseEntity listConsultations(@PathVariable(value = "doctorUUID") String doctorUUID) {
        JsonObject jsonResponse = new JsonObject();
        Doctor doctor = doctorService.getDoctorByUUID(doctorUUID);
        if (doctor == null) {
            jsonResponse.addProperty("error", Responses.DOCTOR_NOT_FOUND_FOR_UUID);
        } else {
            List<Consultation> consultations = doctorService.getConsultationsForDoctor(doctorUUID);
            jsonResponse.addProperty("consultations", gson.toJson(consultations));
            jsonResponse.addProperty("nrOfConsultations", consultations.size());
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @GetMapping(value = "/{doctorUUID}")
    public ResponseEntity getDoctorByUUID(@PathVariable(value = "doctorUUID") String doctorUUID) {
        JsonObject jsonResponse = new JsonObject();
        Doctor doctor = doctorService.getDoctorByUUID(doctorUUID);

        if (doctor == null) {
            jsonResponse.addProperty("error", Responses.DOCTOR_NOT_FOUND_FOR_UUID);
        } else {
            jsonResponse.addProperty("doctor", doctor.toString());
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @GetMapping
    public ResponseEntity listDoctors() {
        List<Doctor> resultList = doctorService.listDoctors();
        String data = gson.toJson(resultList);
        JsonArray jsonArray = new JsonParser().parse(data).getAsJsonArray();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.add("doctors", jsonArray);
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity createDoctor(@Valid @RequestBody Doctor doctor) {
        String errorString = null;
        Doctor doctorAdded;
        JsonObject jsonResponse = new JsonObject();
        try {
            doctorAdded = doctorService.addDoctor(doctor);
            if (doctorAdded != null) {
                jsonResponse.addProperty("doctor", doctorAdded.toString());
            }
            jsonResponse.addProperty("response", Responses.SUCCESSFULLY_ADDED_DOCTOR);
        } catch (Exception e) {
            errorString = Responses.FAILED_TO_CREATE_DOCTOR + e.getMessage();
        } finally {
            if (errorString != null) {
                jsonResponse.addProperty("error", errorString);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @DeleteMapping(value = "/{doctorUUID}")
    public ResponseEntity deleteDoctorByUUID(@PathVariable(value = "doctorUUID") String doctorUUID) {
        String errorString = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            Doctor doctor = doctorService.getDoctorByUUID(doctorUUID);
            if (doctor == null) {
                errorString = Responses.FAILED_TO_DELETE_DOCTOR + " : " + Responses.DOCTOR_NOT_FOUND_FOR_UUID;
            } else {
                doctorService.deleteDoctor(doctorUUID);
                jsonResponse.addProperty("response", SUCCESSFULLY_DELETED_DOCTOR);
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
