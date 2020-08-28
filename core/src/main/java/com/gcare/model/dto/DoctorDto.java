package com.gcare.model.dto;

import com.gcare.model.Gender;
import com.gcare.model.Specialty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
public class DoctorDto extends BaseDto {

    private String uuid;
    private Date dateOfBirth;
    private Gender gender;
    private String firstName;
    private String lastName;
    private String middleName;
    private String address;
    private String city;
    private String county;
    private String countryCode;
    private Integer hourlyRate;
    private String description;
    private Specialty primarySpecialty;
}
