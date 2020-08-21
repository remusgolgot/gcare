package com.gcare.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Objects;


@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)
@Entity
@Table(name = "DOCTOR")
public class Doctor extends BaseEntity {

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
    @Enumerated(EnumType.STRING)
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
    @NotNull(message="countryCode is a required field")
    @Size(min = 2, max = 2)
    private String countryCode;

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

    @Override
    public String toString() {
        return "{" +
                "id=" + getId() +
                ", uuid='" + uuid + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", description='" + description + '\'' +
                ", primarySpecialty=" + primarySpecialty +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(uuid, doctor.uuid) &&
                Objects.equals(dateOfBirth, doctor.dateOfBirth) &&
                gender == doctor.gender &&
                Objects.equals(firstName, doctor.firstName) &&
                Objects.equals(lastName, doctor.lastName) &&
                Objects.equals(middleName, doctor.middleName) &&
                Objects.equals(address, doctor.address) &&
                Objects.equals(city, doctor.city) &&
                Objects.equals(county, doctor.county) &&
                Objects.equals(countryCode, doctor.countryCode) &&
                Objects.equals(hourlyRate, doctor.hourlyRate) &&
                Objects.equals(description, doctor.description) &&
                primarySpecialty == doctor.primarySpecialty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dateOfBirth, gender, firstName, lastName, middleName, address, city, county, countryCode, hourlyRate, description, primarySpecialty);
    }
}
