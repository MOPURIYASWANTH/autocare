package com.autocare.autocare.service;

import com.autocare.autocare.exception.VehicleOwnershipException;
import com.autocare.autocare.exception.InvalidBookingStatusException;
import com.autocare.autocare.exception.BookingNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

import com.autocare.autocare.dto.BookingRequest;
import com.autocare.autocare.entity.AutoService;
import com.autocare.autocare.entity.Booking;
import com.autocare.autocare.entity.Customer;
import com.autocare.autocare.entity.Mechanic;
import com.autocare.autocare.entity.Vehicle;
import com.autocare.autocare.repository.BookingRepository;
import com.autocare.autocare.repository.CustomerRepository;
import com.autocare.autocare.repository.MechanicRepository;
import com.autocare.autocare.repository.ServiceRepository;
import com.autocare.autocare.repository.VehicleRepository;
import com.autocare.autocare.exception.CustomerNotFoundException;
import com.autocare.autocare.exception.VehicleNotFoundException;
import com.autocare.autocare.exception.ServiceNotFoundException;
import com.autocare.autocare.exception.MechanicNotFoundException;

@Service
public class BookingService {

	private final BookingRepository bookingRepository;
	private final CustomerRepository customerRepository;
	private final VehicleRepository vehicleRepository;
	private final ServiceRepository serviceRepository;
	private final MechanicRepository mechanicRepository;

	public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository,
			VehicleRepository vehicleRepository, ServiceRepository serviceRepository,
			MechanicRepository mechanicRepository) {

		this.bookingRepository = bookingRepository;
		this.customerRepository = customerRepository;
		this.vehicleRepository = vehicleRepository;
		this.serviceRepository = serviceRepository;
		this.mechanicRepository = mechanicRepository;
	}

	public Booking addBooking(BookingRequest request) {

		Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
				() -> new CustomerNotFoundException("Customer not found with id: " + request.getCustomerId()));

		Vehicle vehicle = vehicleRepository.findById(request.getVehicleId()).orElseThrow(
				() -> new VehicleNotFoundException("Vehicle not found with id: " + request.getVehicleId()));

		AutoService service = serviceRepository.findById(request.getServiceId()).orElseThrow(
				() -> new ServiceNotFoundException("Service not found with id: " + request.getServiceId()));

		Mechanic mechanic = mechanicRepository.findById(request.getMechanicId()).orElseThrow(
				() -> new MechanicNotFoundException("Mechanic not found with id: " + request.getMechanicId()));
		if (!vehicle.getCustomer().getId().equals(customer.getId())) {

			throw new VehicleOwnershipException("Vehicle does not belong to the customer");
		}

		Booking booking = new Booking();

		booking.setBookingDate(request.getBookingDate());
		booking.setBookingTime(request.getBookingTime());
		booking.setStatus(request.getStatus());

		booking.setCustomer(customer);
		booking.setVehicle(vehicle);
		booking.setService(service);
		booking.setMechanic(mechanic);

		return bookingRepository.save(booking);
	}

	public Booking updateStatus(Long bookingId, String status) {

		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));

		if (!status.equals("PENDING") && !status.equals("CONFIRMED") && !status.equals("IN_PROGRESS")
				&& !status.equals("COMPLETED") && !status.equals("CANCELLED")) {

			throw new InvalidBookingStatusException("Invalid booking status: " + status);
		}

		booking.setStatus(status);

		return bookingRepository.save(booking);
	}

	public java.util.List<Booking> getAllBookings() {

		return bookingRepository.findAll();
	}

	public Booking getBookingById(Long bookingId) {

		return bookingRepository.findById(bookingId)
				.orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));
	}

	public List<Booking> getBookingsByCustomer(Long customerId) {

		return bookingRepository.findByCustomerId(customerId);
	}

	public List<Booking> getBookingsByMechanic(Long mechanicId) {
		return bookingRepository.findByMechanicId(mechanicId);
	}

	public List<Booking> getBookingsByVehicle(Long vehicleId) {
		return bookingRepository.findByVehicleId(vehicleId);
	}
}