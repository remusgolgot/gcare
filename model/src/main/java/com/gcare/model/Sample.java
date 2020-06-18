package com.gcare.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Entity
@Table(name = "SAMPLE")
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "COLLECTED_DATE", nullable = false)
    private Date collectedDate;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID", referencedColumnName = "id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "PATIENT_ID", referencedColumnName = "id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "MARKER_ID", referencedColumnName = "id", nullable = false)
    private Marker marker;

    @Column(name = "MARKER_VALUE", nullable = false)
    private Double markerValue;
}
