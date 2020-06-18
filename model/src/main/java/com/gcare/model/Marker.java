package com.gcare.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Entity
@Table(name = "MARKER")
public class Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "MARKER_NAME", nullable = false)
    private String markerName;

    @Column(name = "MEASURE_UNIT", nullable = false)
    private String measureUnit;
}
