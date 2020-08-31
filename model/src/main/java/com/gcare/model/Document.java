package com.gcare.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Entity
@Table(name = "DOCUMENT")
public class Document extends BaseEntity {

    @Column(name = "DATE_OF_COLLECTION", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    @Past
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "PATIENT_ID", referencedColumnName = "id")
    private Patient patient;

    @Column(name = "DOCUMENT_NAME")
    private String documentName;
}
