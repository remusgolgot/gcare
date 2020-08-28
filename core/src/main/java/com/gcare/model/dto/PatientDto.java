package com.gcare.model.dto;

import com.gcare.model.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
public class PatientDto extends BaseDto {

    private Date dateOfBirth;
    private Gender gender;
    private String firstName;
    private String lastName;
    private String middleName;
    private String address;
    private String city;
    private String county;
    private String countryCode;
    private String uuid;

}
