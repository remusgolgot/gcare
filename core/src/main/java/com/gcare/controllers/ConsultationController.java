package com.gcare.controllers;

import com.gcare.messages.Responses;
import com.gcare.model.Consultation;
import com.gcare.model.dto.ConsultationDto;
import com.gcare.model.Doctor;
import com.gcare.model.Patient;
import com.gcare.services.ConsultationService;
import com.gcare.services.DoctorService;
import com.gcare.services.PatientService;
import com.gcare.utils.ClassUtils;
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
@RequestMapping("/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity listDoctors() {
        List<Consultation> resultList = consultationService.listConsultations();

        String data = GsonUtils.gson.toJson(resultList);
        JsonArray jsonArray = new JsonParser().parse(data).getAsJsonArray();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.add("consultations", jsonArray);
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @GetMapping(value = "/{consultationID}", produces = "application/json")
    public ResponseEntity getConsultationByID(@Valid @PathVariable(value = "consultationID") Integer consultationID) {
        Consultation consultation = consultationService.getConsultationByID(consultationID);
        JsonObject jsonResponse = new JsonObject();
        if (consultation == null) {
            jsonResponse.addProperty("error", Responses.CONSULTATION_NOT_FOUND);
        } else {
            jsonResponse.add("consultation", new JsonParser().parse(GsonUtils.gson.toJson(consultation)).getAsJsonArray());
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @PutMapping(value = "/{consultationID}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateConsultation(@PathVariable(value = "consultationID") Integer consultationID,
                                       @Valid @RequestBody ConsultationDto consultationDto) {
        String errorString = null;
        Consultation consultationToUpdate;
        JsonObject jsonResponse = new JsonObject();
        try {
            consultationToUpdate = consultationService.getConsultationByID(consultationID);
            if (consultationToUpdate != null) {
                consultationDto.setId(consultationToUpdate.getId());
                consultationService.updateConsultation(consultationDto);
                jsonResponse.addProperty("response", Responses.SUCCESSFULLY_UPDATED_CONSULTATION);
            } else {
                errorString = Responses.CONSULTATION_NOT_FOUND;
            }
        } catch (Exception e) {
            errorString = e.getMessage();
        } finally {
            if (errorString != null) {
                jsonResponse.addProperty("error", Responses.FAILED_TO_UPDATE_CONSULTATION + " : " + errorString);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity createConsultation(@Valid @RequestBody ConsultationDto consultationDTO) {
        String errorString = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            Doctor doctor = doctorService.getDoctorById(consultationDTO.getDoctorID());
            Patient patient = patientService.getPatientById(consultationDTO.getPatientID());
            if (doctor == null) {
                errorString = Responses.DOCTOR_NOT_FOUND_FOR_ID;
            }
            if (patient == null) {
                errorString = Responses.PATIENT_NOT_FOUND_FOR_ID;
            }
           if (doctor != null && patient != null) {
               Consultation consultationAdded = (Consultation) ClassUtils.copyPropertiesFromDTO(Consultation.class, consultationDTO);
               consultationAdded.setDoctor(doctor);
               consultationAdded.setPatient(patient);
                consultationAdded = consultationService.addConsultation(consultationAdded);
                if (consultationAdded != null) {
                    jsonResponse.addProperty("consultation", consultationAdded.toString());
                }
                jsonResponse.addProperty("response", Responses.SUCCESSFULLY_ADDED_CONSULTATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorString = e.getMessage();
        } finally {
            if (errorString != null) {
                jsonResponse.addProperty("error", Responses.FAILED_TO_CREATE_CONSULTATION + " : " + errorString);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    @DeleteMapping(value = "/{consultationID}")
    public ResponseEntity deleteConsultationByID(@PathVariable(value = "consultationID") Integer consultationID) {
        String errorString = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            Consultation consultation = consultationService.getConsultationByID(consultationID);
            if (consultation == null) {
                errorString = Responses.FAILED_TO_DELETE_CONSULTATION + " : " + Responses.CONSULTATION_NOT_FOUND;
            } else {
                consultationService.deleteConsultationByID(consultationID);
                jsonResponse.addProperty("response", Responses.SUCCESSFULLY_DELETED_CONSULTATION);
            }
        } catch (Exception e) {
            errorString = Responses.FAILED_TO_DELETE_CONSULTATION + " : " + e.getMessage();
        } finally {
            if (errorString != null) {
                jsonResponse.addProperty("error", errorString);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }


}
