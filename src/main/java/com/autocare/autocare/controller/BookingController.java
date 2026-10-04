package com.autocare.autocare.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.autocare.autocare.dto.BookingRequest;
import com.autocare.autocare.entity.Booking;
import com.autocare.autocare.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@PostMapping
	public Booking addBooking(@RequestBody BookingRequest request) {
		return bookingService.addBooking(request);
	}

	@PutMapping("/{bookingId}/status")
	public Booking updateStatus(@PathVariable Long bookingId, @RequestParam String status) {

		return bookingService.updateStatus(bookingId, status);
	}

	@GetMapping
	public java.util.List<Booking> getAllBookings() {

		return bookingService.getAllBookings();
	}

	@GetMapping("/{bookingId}")
	public Booking getBookingById(@PathVariable Long bookingId) {

		return bookingService.getBookingById(bookingId);
	}

	@GetMapping("/customer/{customerId}")
	public List<Booking> getBookingsByCustomer(@PathVariable Long customerId) {

		return bookingService.getBookingsByCustomer(customerId);
	}

	@GetMapping("/mechanic/{mechanicId}")
	public List<Booking> getBookingsByMechanic(@PathVariable Long mechanicId) {

		return bookingService.getBookingsByMechanic(mechanicId);
	}
	@GetMapping("/vehicle/{vehicleId}")
	public List<Booking> getBookingsByVehicle(
	        @PathVariable Long vehicleId) {

	    return bookingService.getBookingsByVehicle(vehicleId);
	}
}