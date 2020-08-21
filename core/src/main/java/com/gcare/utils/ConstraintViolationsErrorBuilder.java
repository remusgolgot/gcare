package com.gcare.utils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

public class ConstraintViolationsErrorBuilder {

    public static String buildErrorMessageFromException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        StringBuilder errorsBuilder = new StringBuilder("Validation Errors : [");
        String errors = set.stream().map(o -> o.getPropertyPath() + " : " + o.getMessage()).collect(Collectors.joining(", "));
        errorsBuilder.append(errors).append("]");
        return errorsBuilder.toString();
    }
}
