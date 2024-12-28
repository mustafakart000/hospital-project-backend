package com.hospital.exception;

import java.util.List;

public class UserValidationException extends RuntimeException {
    private List<String> errors;

    public UserValidationException(List<String> errors) {
        super(errors.toString());
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
} 