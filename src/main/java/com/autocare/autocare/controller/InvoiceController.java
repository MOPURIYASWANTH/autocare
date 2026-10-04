package com.autocare.autocare.controller;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autocare.autocare.dto.InvoiceRequest;
import com.autocare.autocare.entity.Invoice;
import com.autocare.autocare.service.InvoiceService;
import org.springframework.web.bind.annotation.PathVariable;
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public Invoice createInvoice(@RequestBody InvoiceRequest request) {
        return invoiceService.createInvoice(request);
    }
    @GetMapping
    public List<Invoice> getAllInvoices() {

        return invoiceService.getAllInvoices();
    }
    @GetMapping("/booking/{bookingId}")
    public Invoice getInvoiceByBooking(
            @PathVariable Long bookingId) {

        return invoiceService.getInvoiceByBooking(bookingId);
    }
    @GetMapping("/{invoiceId}")
    public Invoice getInvoiceById(
            @PathVariable Long invoiceId) {

        return invoiceService.getInvoiceById(invoiceId);
    }
}