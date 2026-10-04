package com.autocare.autocare.service;

import com.autocare.autocare.exception.InvalidInvoiceAmountException;
import com.autocare.autocare.exception.InvoiceAlreadyExistsException;
import com.autocare.autocare.exception.InvoiceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

import com.autocare.autocare.dto.InvoiceRequest;
import com.autocare.autocare.entity.Booking;
import com.autocare.autocare.entity.Invoice;
import com.autocare.autocare.repository.BookingRepository;
import com.autocare.autocare.repository.InvoiceRepository;
import com.autocare.autocare.exception.BookingNotFoundException;

@Service
public class InvoiceService {

	private final InvoiceRepository invoiceRepository;
	private final BookingRepository bookingRepository;

	public InvoiceService(InvoiceRepository invoiceRepository, BookingRepository bookingRepository) {

		this.invoiceRepository = invoiceRepository;
		this.bookingRepository = bookingRepository;
	}

	public Invoice createInvoice(InvoiceRequest request) {

		Booking booking = bookingRepository.findById(request.getBookingId()).orElseThrow(
				() -> new BookingNotFoundException("Booking not found with id: " + request.getBookingId()));
		if (invoiceRepository.findByBookingId(request.getBookingId()).isPresent()) {

			throw new InvoiceAlreadyExistsException("Invoice already exists for booking id: " + request.getBookingId());
		}

		if (request.getServiceCharge() < 0 || request.getPartsCharge() < 0 || request.getTax() < 0) {

			throw new InvalidInvoiceAmountException("Invoice charges cannot be negative");
		}

		Invoice invoice = new Invoice();

		invoice.setInvoiceNumber(request.getInvoiceNumber());
		invoice.setServiceCharge(request.getServiceCharge());
		invoice.setPartsCharge(request.getPartsCharge());
		invoice.setTax(request.getTax());

		double totalAmount = request.getServiceCharge() + request.getPartsCharge() + request.getTax();

		invoice.setTotalAmount(totalAmount);

		invoice.setBooking(booking);

		return invoiceRepository.save(invoice);
	}

	public List<Invoice> getAllInvoices() {

		return invoiceRepository.findAll();
	}

	public Invoice getInvoiceByBooking(Long bookingId) {

		return invoiceRepository.findByBookingId(bookingId)
				.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found for booking id: " + bookingId));
	}

	public Invoice getInvoiceById(Long invoiceId) {

		return invoiceRepository.findById(invoiceId)
				.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found with id: " + invoiceId));
	}
}