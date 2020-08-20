package com.gcare.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
public class ConsultationDto extends BaseDto {

    private ConsultationType consultationType;
    private ConsultationState consultationState;
    private String notes;
    private Date date;
    private Integer patientID;
    private Integer doctorID;

}
