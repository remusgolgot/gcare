package com.gcare.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Entity
@Table(name = "PATIENT")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private Timestamp dateOfBirth;

    @Column(name = "GENDER", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "ADDRESS_CITY", nullable = false)
    private String addressCity;

    @Column(name = "ADDRESS_COUNTY", nullable = false)
    private String addressCounty;

    @Column(name = "ADDRESS_COUNTRY", nullable = false)
    private String addressCountry;
}
