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
@Table(name = "PATIENT_CONDITION")
public class PatientCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PATIENT_ID", referencedColumnName = "id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "MEDICAL_CONDITION_ID", referencedColumnName = "id", nullable = false)
    private MedicalCondition medicalCondition;

    @Column(name = "DETECTED_DATE", nullable = false)
    private Date detectedDate;

    @Column(name = "LAST_UPDATE", nullable = false)
    private Date lastUpdate;

    @Column(name = "STATUS", nullable = false)
    private MedicalConditionStatus medicalConditionStatus;

}
