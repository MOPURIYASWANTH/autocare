package com.autocare.autocare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.autocare.autocare.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleRuntimeException(
            RuntimeException exception) {

        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ErrorResponse handleEmailAlreadyExists(
            EmailAlreadyExistsException exception) {

        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public ErrorResponse handleCustomerNotFound(
            CustomerNotFoundException exception) {

        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(VehicleNotFoundException.class)
    public ErrorResponse handleVehicleNotFound(
            VehicleNotFoundException exception) {

        return new ErrorResponse(exception.getMessage());
    }
    @ExceptionHandler(ServiceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleServiceNotFound(
            ServiceNotFoundException exception) {

        return new ErrorResponse(exception.getMessage());
    }
    @ExceptionHandler(MechanicNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMechanicNotFound(
            MechanicNotFoundException exception) {

        return new ErrorResponse(exception.getMessage());
    }
    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleBookingNotFound(
            BookingNotFoundException exception) {

        return new ErrorResponse(exception.getMessage());
    }
    @ExceptionHandler(InvoiceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleInvoiceNotFound(
            InvoiceNotFoundException exception) {

        return new ErrorResponse(exception.getMessage());
    }
    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePaymentNotFound(
            PaymentNotFoundException exception) {

        return new ErrorResponse(exception.getMessage());
    }
    @ExceptionHandler(InvoiceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleInvoiceAlreadyExists(
            InvoiceAlreadyExistsException exception) {

        return new ErrorResponse(exception.getMessage());
    }
    @ExceptionHandler(PaymentAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlePaymentAlreadyExists(
            PaymentAlreadyExistsException exception) {

        return new ErrorResponse(exception.getMessage());
    }
    @ExceptionHandler(InvalidBookingStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidBookingStatus(
            InvalidBookingStatusException exception) {

        return new ErrorResponse(exception.getMessage());
    }
    @ExceptionHandler(InvalidPaymentAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidPaymentAmount(
            InvalidPaymentAmountException exception) {

        return new ErrorResponse(exception.getMessage());
    }
    @ExceptionHandler(InvalidInvoiceAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidInvoiceAmount(
            InvalidInvoiceAmountException exception) {

        return new ErrorResponse(exception.getMessage());
    }
    @ExceptionHandler(VehicleOwnershipException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleVehicleOwnership(
            VehicleOwnershipException exception) {

        return new ErrorResponse(exception.getMessage());
    }
}