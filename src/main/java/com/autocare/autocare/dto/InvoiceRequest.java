package com.autocare.autocare.dto;

public class InvoiceRequest {

    private String invoiceNumber;
    private double serviceCharge;
    private double partsCharge;
    private double tax;
    private Long bookingId;

    public InvoiceRequest() {
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public double getPartsCharge() {
        return partsCharge;
    }

    public void setPartsCharge(double partsCharge) {
        this.partsCharge = partsCharge;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}