package com.gcare.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Entity
@Table(name = "TREATMENT")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DRUG_NAME", nullable = false)
    private String drugName;

    @Column(name = "DRUG_DOSE", nullable = false)
    private String drugDose;

    @Column(name = "HOURLY_PERIODICITY")
    private Integer hourlyPeriodicity;
}
