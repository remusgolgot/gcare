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
@Table(name = "CONSULTATION")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CONSULTATION_TYPE")
    private ConsultationType consultationType;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "PATIENT_ID", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID", referencedColumnName = "id")
    private Doctor doctor;

}
