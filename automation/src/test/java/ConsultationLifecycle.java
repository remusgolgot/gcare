import com.gcare.model.*;
import com.gcare.model.dto.ConsultationDto;
import com.gcare.model.dto.DoctorDto;
import com.gcare.model.dto.PatientDto;
import com.gcare.utils.GsonUtils;
import com.google.gson.*;
import httputils.HttpRequestEngine;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
public class ConsultationLifecycle {

    private static final HttpRequestEngine engine = new HttpRequestEngine();
    private static final String dateFormat = "yyyy-MM-dd";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(dateFormat);

    private static DoctorDto createDoctorEntity() throws Exception {

        DoctorDto doctor = new DoctorDto();
        doctor.setAddress("5th Avenue, nr. 65");
        doctor.setCity("New York");
        doctor.setCounty("NY");
        doctor.setCountryCode("US");
        doctor.setFirstName("Dan");
        doctor.setLastName("Ives");
        doctor.setGender(Gender.F);
        doctor.setHourlyRate(12);
        doctor.setPrimarySpecialty(Specialty.CARDIOLOGY);
        doctor.setDateOfBirth(DATE_FORMAT.parse("2019-01-01"));
        doctor.setUuid("44878745a7c5bbbf");
        return doctor;
    }

    private static PatientDto createPatientEntity() throws Exception {

        PatientDto patient = new PatientDto();
        patient.setAddress("5th Avenue, nr. 65");
        patient.setCity("New York");
        patient.setCounty("NY");
        patient.setCountryCode("US");
        patient.setFirstName("Dan");
        patient.setLastName("Ives");
        patient.setGender(Gender.F);
        patient.setDateOfBirth(DATE_FORMAT.parse("2019-01-01"));
        patient.setUuid("44878745a7c5bbbf");
        return patient;
    }

    private static ConsultationDto createConsultationEntity() throws Exception {
        ConsultationDto consultation = new ConsultationDto();
        consultation.setDate(DATE_FORMAT.parse("2020-12-12"));
        consultation.setConsultationType(ConsultationType.IN_PERSON);
        return consultation;
    }

    @Test
    public void createAndUpdateConsultation() throws Exception {

        DoctorDto doctorToCreate = createDoctorEntity();

        String body = GsonUtils.gson.toJson(doctorToCreate);
        HttpResponse postResponse = engine.sendHttpPost("http://localhost:8080/doctors", body);
        Assert.assertEquals(200, engine.getResponseCode(postResponse));

        PatientDto patientToCreate = createPatientEntity();

        body = GsonUtils.gson.toJson(patientToCreate);
        postResponse = engine.sendHttpPost("http://localhost:8080/patients", body);
        Assert.assertEquals(200, engine.getResponseCode(postResponse));

        String response = engine.sendHttpGet("http://localhost:8080/doctors");
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonElement doctors = jsonObject.get("doctors");
        JsonArray array = doctors.getAsJsonArray();
        Assert.assertEquals(1, array.size());
        DoctorDto doctorAfterGet = GsonUtils.gson.fromJson(array.get(0), DoctorDto.class);
        Assert.assertEquals(doctorAfterGet, doctorToCreate);

        response = engine.sendHttpGet("http://localhost:8080/patients");
        jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonElement patients = jsonObject.get("patients");
        array = patients.getAsJsonArray();
        Assert.assertEquals(1, array.size());
        PatientDto patientAfterGet = GsonUtils.gson.fromJson(array.get(0), PatientDto.class);
        Assert.assertEquals(patientAfterGet, patientToCreate);

        ConsultationDto consultationToCreate = createConsultationEntity();
        consultationToCreate.setDoctorID(doctorAfterGet.getId());
        consultationToCreate.setPatientID(patientAfterGet.getId());

        body = GsonUtils.gson.toJson(consultationToCreate);
        postResponse = engine.sendHttpPost("http://localhost:8080/consultations", body);
        Assert.assertEquals(200, engine.getResponseCode(postResponse));

        response = engine.sendHttpGet("http://localhost:8080/consultations");
        jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonElement consultations = jsonObject.get("consultations");
        array = consultations.getAsJsonArray();
        Assert.assertEquals(1, array.size());
        Consultation consultationAfterGet = GsonUtils.gson.fromJson(array.get(0), Consultation.class);
        Assert.assertEquals(ConsultationState.BOOKED, consultationAfterGet.getConsultationState());
        Assert.assertEquals(consultationToCreate.getDate(), consultationAfterGet.getDate());
        Assert.assertEquals(consultationAfterGet.getDoctor().getId(), doctorAfterGet.getId());
        Assert.assertEquals(consultationAfterGet.getPatient().getId(), patientAfterGet.getId());

        ConsultationDto consultationToUpdate = createConsultationEntity();
        consultationToUpdate.setConsultationState(ConsultationState.COMPLETED);
        consultationToUpdate.setNotes("He gone");
        consultationToUpdate.setDoctorID(doctorAfterGet.getId());
        consultationToUpdate.setPatientID(patientAfterGet.getId());

        String putBody = GsonUtils.gson.toJson(consultationToUpdate);
        HttpResponse putResponse = engine.sendHttpPut("http://localhost:8080/consultations/" + consultationAfterGet.getId(), putBody);
        Assert.assertEquals(200, engine.getResponseCode(putResponse));
        response = engine.sendHttpGet("http://localhost:8080/consultations");
        jsonObject = new JsonParser().parse(response).getAsJsonObject();
        consultations = jsonObject.get("consultations");
        array = consultations.getAsJsonArray();
        Assert.assertEquals(1, array.size());
        ConsultationDto consultationAfterUpdateAndGet = GsonUtils.gson.fromJson(array.get(0), ConsultationDto.class);
        Assert.assertEquals(consultationAfterUpdateAndGet.getConsultationState(), consultationToUpdate.getConsultationState());
        Assert.assertNotEquals(consultationAfterUpdateAndGet.getConsultationState(), consultationToCreate.getConsultationState());

        engine.sendHttpDelete("http://localhost:8080/consultations/" + consultationAfterGet.getId());
        engine.sendHttpDelete("http://localhost:8080/doctors/" + doctorAfterGet.getId());
        engine.sendHttpDelete("http://localhost:8080/patients/" + patientAfterGet.getId());
    }

}
