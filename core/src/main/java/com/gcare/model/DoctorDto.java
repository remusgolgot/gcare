package com.gcare.model;

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
    private String addressCity;
    private String addressCounty;
    private String addressCountry;
    private Integer hourlyRate;
    private String description;
    private Specialty primarySpecialty;
}
