package com.autocare.autocare.exception;

public class MechanicNotFoundException extends RuntimeException {

    public MechanicNotFoundException(String message) {
        super(message);
    }
}