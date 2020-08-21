
import com.gcare.model.DoctorDto;
import com.gcare.model.Gender;
import com.gcare.model.Specialty;

import com.google.gson.*;
import httputils.HttpRequestEngine;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
public class DoctorLifecycle {

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

    @Test
    public void createDoctorAndGetInfo() throws Exception {

        DoctorDto doctorToCreate = createDoctorEntity();
        Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();

        String body = gson.toJson(doctorToCreate);
        HttpResponse postResponse = engine.sendHttpPost("http://localhost:8080/doctors", body);
        Assert.assertEquals(200, engine.getResponseCode(postResponse));

        String response = engine.sendHttpGet("http://localhost:8080/doctors");
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonElement doctors = jsonObject.get("doctors");
        JsonArray array = doctors.getAsJsonArray();
        Assert.assertEquals(1, array.size());
        DoctorDto doctorAfterGet = gson.fromJson(array.get(0), DoctorDto.class);
        Assert.assertEquals(doctorAfterGet, doctorToCreate);

        engine.sendHttpDelete("http://localhost:8080/doctors/" + doctorAfterGet.getId());
    }

}
