package com.gcare.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "LAST_UPDATE_TS", nullable = false, columnDefinition = "DATETIME(3)")
    @UpdateTimestamp
    private Timestamp lastUpdateTimestamp;
}
