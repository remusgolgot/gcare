import com.gcare.model.*;
import com.gcare.model.dto.PatientDto;
import com.gcare.utils.GsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import httputils.HttpRequestEngine;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
public class PatientLifecycle {

    private static final HttpRequestEngine engine = new HttpRequestEngine();
    private static final String dateFormat = "yyyy-MM-dd";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(dateFormat);

    static PatientDto createPatientEntity() throws Exception {
        PatientDto patient = new PatientDto();
        patient.setAddress("Cluj-Napoca");
        patient.setCity("Cluj-Napoca");
        patient.setCounty("Cluj");
        patient.setCountryCode("RO");
        patient.setUuid("44878745a7c5bbbf");
        patient.setFirstName("Dan");
        patient.setLastName("Suzuki");
        patient.setGender(Gender.M);
        patient.setDateOfBirth(DATE_FORMAT.parse("2019-01-01"));
        return patient;
    }

    @Test
    public void patientCreateUpdateAndGetInfo() throws Exception {

        PatientDto patientToCreate = createPatientEntity();

        String body = GsonUtils.gson.toJson(patientToCreate);
        HttpResponse postResponse = engine.sendHttpPost("http://localhost:8080/patients", body);
        Assert.assertEquals(200, engine.getResponseCode(postResponse));

        String response = engine.sendHttpGet("http://localhost:8080/patients");
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonElement patients = jsonObject.get("patients");
        JsonArray array = patients.getAsJsonArray();
        Assert.assertEquals(1, array.size());
        PatientDto patientAfterGet = GsonUtils.gson.fromJson(array.get(0), PatientDto.class);
        Assert.assertEquals(patientAfterGet, patientToCreate);

        PatientDto patientToUpdate = createPatientEntity();
        patientToUpdate.setCountryCode("RO");
        patientToUpdate.setCity("Vancouver");

        String putBody = GsonUtils.gson.toJson(patientToUpdate);
        HttpResponse putResponse = engine.sendHttpPut("http://localhost:8080/patients/" + patientAfterGet.getId(), putBody);
        Assert.assertEquals(200, engine.getResponseCode(putResponse));
        response = engine.sendHttpGet("http://localhost:8080/patients");
        jsonObject = new JsonParser().parse(response).getAsJsonObject();
        patients = jsonObject.get("patients");
        array = patients.getAsJsonArray();
        Assert.assertEquals(1, array.size());
        PatientDto patientAfterUpdateAndGet = GsonUtils.gson.fromJson(array.get(0), PatientDto.class);
        Assert.assertEquals(patientAfterUpdateAndGet, patientToUpdate);
        Assert.assertNotEquals(patientAfterUpdateAndGet, patientToCreate);

        engine.sendHttpDelete("http://localhost:8080/patients/" + patientAfterGet.getId());
    }

    @Test
    public void createPatientWithValidationErrors() throws Exception {
        PatientDto patientToCreate = createPatientEntity();
        patientToCreate.setCountryCode("CCC");

        String body = GsonUtils.gson.toJson(patientToCreate);
        HttpResponse postResponse = engine.sendHttpPost("http://localhost:8080/patients", body);
        Assert.assertEquals(200, engine.getResponseCode(postResponse));
        String entityAsString = EntityUtils.toString(postResponse.getEntity(), "UTF-8");
        Assert.assertTrue(
                entityAsString.contains("size must be between 2 and 2")
        );
    }

}
