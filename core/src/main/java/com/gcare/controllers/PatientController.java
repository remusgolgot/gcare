package com.gcare.controllers;

import com.gcare.messages.Responses;
import com.gcare.model.Consultation;
import com.gcare.model.Doctor;
import com.gcare.model.Patient;
import com.gcare.model.PatientDto;
import com.gcare.services.DoctorService;
import com.gcare.services.PatientService;
import com.gcare.utils.GsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity listDoctors() {
        List<Patient> resultList = patientService.listPatients();

        String data = GsonUtils.gson.toJson(resultList);
        JsonArray jsonArray = new JsonParser().parse(data).getAsJsonArray();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.add("patients", jsonArray);
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @GetMapping(value = "/{patientID}")
    public ResponseEntity getPatientByID(@PathVariable(value = "patientID") Integer patientID) {
        JsonObject jsonResponse = new JsonObject();
        Patient patient = patientService.getPatientByID(patientID);

        if (patient == null) {
            jsonResponse.addProperty("error", Responses.PATIENT_NOT_FOUND_FOR_ID);
        } else {
            jsonResponse.addProperty("patient", patient.toString());
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity createPatient(@Valid @RequestBody PatientDto patientDto) {
        String errorString = null;
        Patient patientToAdd;
        JsonObject jsonResponse = new JsonObject();
        try {
            patientToAdd = patientService.addPatient(patientDto);
            if (patientToAdd != null) {
                jsonResponse.addProperty("patient", patientToAdd.toString());
            }
            jsonResponse.addProperty("response", Responses.SUCCESSFULLY_ADDED_PATIENT);
        } catch (Exception e) {
            errorString = e.getMessage();
        } finally {
            if (errorString != null) {
                jsonResponse.addProperty("error", Responses.FAILED_TO_CREATE_PATIENT + " : " + errorString);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @PutMapping(value = "/{patientID}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updatePatient(@PathVariable(value = "patientID") Integer patientID,
                                        @Valid @RequestBody PatientDto patientDto) {
        String errorString = null;
        Patient patientToUpdate;
        JsonObject jsonResponse = new JsonObject();
        try {
            patientToUpdate = patientService.getPatientByID(patientID);
            if (patientToUpdate != null) {
                patientDto.setId(patientToUpdate.getId());
                patientService.updatePatient(patientDto);
                jsonResponse.addProperty("response", Responses.SUCCESSFULLY_UPDATED_DOCTOR);
            } else {
                errorString = Responses.DOCTOR_NOT_FOUND_FOR_ID;
            }
        } catch (Exception e) {
            errorString = e.getMessage();
        } finally {
            if (errorString != null) {
                jsonResponse.addProperty("error", Responses.FAILED_TO_UPDATE_DOCTOR + " : " + errorString);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @DeleteMapping(value = "/{patientID}")
    public ResponseEntity deletePatientByID(@PathVariable(value = "patientID") Integer patientID) {
        String errorString = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            Patient patient = patientService.getPatientByID(patientID);
            if (patient == null) {
                errorString = Responses.FAILED_TO_CREATE_PATIENT + " : " + Responses.PATIENT_NOT_FOUND_FOR_ID;
            } else {
                patientService.deletePatient(patientID);
                jsonResponse.addProperty("response", Responses.SUCCESSFULLY_DELETED_PATIENT);
            }
        } catch (Exception e) {
            errorString = Responses.FAILED_TO_DELETE_PATIENT + " : " + e.getMessage();
        } finally {
            if (errorString != null) {
                jsonResponse.addProperty("error", errorString);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }
}
