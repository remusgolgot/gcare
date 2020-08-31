package com.gcare.model.dto;

import com.gcare.model.Patient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
public class DocumentDto extends BaseDto {

    private Date creationDate;
    private Patient patient;
    private String documentName;

    public DocumentDto(Date creationDate, Patient patient, String documentName) {
        this.creationDate = creationDate;
        this.patient = patient;
        this.documentName = documentName;
    }
}
