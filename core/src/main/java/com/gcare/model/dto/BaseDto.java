package com.gcare.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
public class BaseDto {

    private Integer id;
}
