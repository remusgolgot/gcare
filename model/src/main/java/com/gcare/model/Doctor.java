package com.gcare.model;

import com.gcare.validators.ValidateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Entity
@Table(name = "DOCTOR")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID", nullable = false, length = 16)
    @NotNull
    @Pattern(regexp = "[0-9a-f]{16}", message="UUID is an incorrect format")
    private String uuid;

    @Column(name = "DATE_OF_BIRTH", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    @Past
    private Date dateOfBirth;

    @Column(name = "GENDER", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @NotNull
//    @ValidateEnum(enumClazz = Gender.class) TODO://add enum validation
    private Gender gender;

    @Column(name = "FIRST_NAME", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    @NotNull
    private String lastName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "ADDRESS_CITY", nullable = false)
    @NotNull
    private String addressCity;

    @Column(name = "ADDRESS_COUNTY", nullable = false)
    @NotNull
    private String addressCounty;

    @Column(name = "ADDRESS_COUNTRY", nullable = false)
    @NotNull
    private String addressCountry;

    @Column(name = "HOURLY_RATE", nullable = false)
    @NotNull
    @Positive
    private Integer hourlyRate;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRIMARY_SPECIALTY", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Specialty primarySpecialty;

}
