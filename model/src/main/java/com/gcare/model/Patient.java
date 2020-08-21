package com.gcare.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)
@Entity
@Table(name = "PATIENT")
public class Patient extends BaseEntity {

    @Column(name = "DATE_OF_BIRTH", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    @Past
    private Date dateOfBirth;

    @Column(name = "GENDER", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "ADDRESS", nullable = false)
    @NotNull
    private String address;

    @Column(name = "CITY", nullable = false)
    @NotNull
    private String city;

    @Column(name = "COUNTY", nullable = false)
    @NotNull
    private String county;

    @Column(name = "COUNTRY_CODE", nullable = false)
    @NotNull
    private String countryCode;

    @Column(name = "UUID", nullable = false, length = 16)
    @NotNull
    @Pattern(regexp = "[0-9a-f]{16}", message="UUID is an incorrect format")
    private String uuid;

}
