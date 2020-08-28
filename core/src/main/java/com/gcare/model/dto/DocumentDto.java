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

    private String type;
    private Date dateOfCollection;
    private Patient patient;
}
