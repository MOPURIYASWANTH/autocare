package com.autocare.autocare.exception;

public class InvalidInvoiceAmountException extends RuntimeException {

    public InvalidInvoiceAmountException(String message) {
        super(message);
    }
}