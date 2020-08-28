package com.gcare.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Entity
@Table(name = "CONSULTATION")
public class Consultation extends BaseEntity {

    @Column(name = "CONSULTATION_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ConsultationType consultationType;

    @Column(name = "CONSULTATION_STATE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ConsultationState consultationState;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    @Future
    private Date date;

    @ManyToOne
    @JoinColumn(name = "PATIENT_ID", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID", referencedColumnName = "id")
    private Doctor doctor;

}
