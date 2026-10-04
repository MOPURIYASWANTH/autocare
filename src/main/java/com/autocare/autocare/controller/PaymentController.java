package com.autocare.autocare.controller;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autocare.autocare.dto.PaymentRequest;
import com.autocare.autocare.entity.Payment;
import com.autocare.autocare.service.PaymentService;
import org.springframework.web.bind.annotation.PathVariable;
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment makePayment(@RequestBody PaymentRequest request) {
        return paymentService.makePayment(request);
    }
    @GetMapping
    public List<Payment> getAllPayments() {

        return paymentService.getAllPayments();
    }
    @GetMapping("/invoice/{invoiceId}")
    public Payment getPaymentByInvoice(
            @PathVariable Long invoiceId) {

        return paymentService.getPaymentByInvoice(invoiceId);
    }
    @GetMapping("/{paymentId}")
    public Payment getPaymentById(
            @PathVariable Long paymentId) {

        return paymentService.getPaymentById(paymentId);
    }
}