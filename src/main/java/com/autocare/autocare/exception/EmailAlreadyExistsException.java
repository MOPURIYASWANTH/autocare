package com.autocare.autocare.exception;
import com.autocare.autocare.exception.EmailAlreadyExistsException;
public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}