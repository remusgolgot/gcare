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
@Table(name = "MARKER")
public class MarkerTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "MARKER_ID", referencedColumnName = "id", nullable = false)
    private Marker marker;

    @Column(name = "MARKER_VALUE", nullable = false)
    private Double markerValue;

    @ManyToOne
    @JoinColumn(name = "PATIENT_ID", referencedColumnName = "id", nullable = false)
    private Patient patient;

    @Column(name = "TARGET_DEADLINE")
    private Date targetDeadline;

    @Column(name = "MARKER_TARGET_STATUS")
    @Enumerated(EnumType.STRING)
    private MarkerTargetStatus markerTargetStatus;
}
