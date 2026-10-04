package com.autocare.autocare.service;
import com.autocare.autocare.exception.InvoiceNotFoundException;
import com.autocare.autocare.exception.PaymentAlreadyExistsException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.autocare.autocare.exception.InvalidPaymentAmountException;
import com.autocare.autocare.dto.PaymentRequest;
import com.autocare.autocare.entity.Invoice;
import com.autocare.autocare.entity.Payment;
import com.autocare.autocare.repository.InvoiceRepository;
import com.autocare.autocare.repository.PaymentRepository;
import com.autocare.autocare.exception.PaymentNotFoundException;

@Service
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final InvoiceRepository invoiceRepository;

	public PaymentService(PaymentRepository paymentRepository, InvoiceRepository invoiceRepository) {

		this.paymentRepository = paymentRepository;
		this.invoiceRepository = invoiceRepository;
	}

	public Payment makePayment(PaymentRequest request) {

	    Invoice invoice = invoiceRepository
	            .findById(request.getInvoiceId())
	            .orElseThrow(() ->
	                    new InvoiceNotFoundException(
	                            "Invoice not found with id: "
	                            + request.getInvoiceId()));

	    if (paymentRepository
	            .findByInvoiceId(request.getInvoiceId())
	            .isPresent()) {

	        throw new PaymentAlreadyExistsException(
	                "Payment already exists for invoice id: "
	                + request.getInvoiceId());
	    }
	    if (request.getAmount() != invoice.getTotalAmount()) {

	        throw new InvalidPaymentAmountException(
	                "Payment amount must be equal to invoice total amount: "
	                + invoice.getTotalAmount());
	    }

	    // remaining payment creation code...

		Payment payment = new Payment();

		payment.setAmount(request.getAmount());
		payment.setPaymentDate(request.getPaymentDate());
		payment.setPaymentMethod(request.getPaymentMethod());
		payment.setStatus(request.getStatus());

		payment.setInvoice(invoice);

		return paymentRepository.save(payment);
	}

	public List<Payment> getAllPayments() {

		return paymentRepository.findAll();
	}

	public Payment getPaymentByInvoice(Long invoiceId) {

		return paymentRepository.findByInvoiceId(invoiceId)
				.orElseThrow(() -> new PaymentNotFoundException("Payment not found for invoice id: " + invoiceId));
	}

	public Payment getPaymentById(Long paymentId) {

		return paymentRepository.findById(paymentId)
				.orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + paymentId));
	}
}