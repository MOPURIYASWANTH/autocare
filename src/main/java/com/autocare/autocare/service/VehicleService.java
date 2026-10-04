package com.autocare.autocare.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.autocare.autocare.dto.VehicleRequest;
import com.autocare.autocare.entity.Customer;
import com.autocare.autocare.entity.Vehicle;
import com.autocare.autocare.repository.CustomerRepository;
import com.autocare.autocare.repository.VehicleRepository;
import com.autocare.autocare.exception.VehicleNotFoundException;
import com.autocare.autocare.exception.CustomerNotFoundException;
@Service
public class VehicleService {

	private final VehicleRepository vehicleRepository;
	private final CustomerRepository customerRepository;

	public VehicleService(VehicleRepository vehicleRepository, CustomerRepository customerRepository) {

		this.vehicleRepository = vehicleRepository;
		this.customerRepository = customerRepository;
	}

	public Vehicle addVehicle(VehicleRequest request) {

		Customer customer = customerRepository
		        .findById(request.getCustomerId())
		        .orElseThrow(() ->
		                new CustomerNotFoundException(
		                        "Customer not found with id: "
		                        + request.getCustomerId()));

		Vehicle vehicle = new Vehicle();

		vehicle.setVehicleNumber(request.getVehicleNumber());
		vehicle.setBrand(request.getBrand());
		vehicle.setModel(request.getModel());
		vehicle.setVehicleType(request.getVehicleType());

		vehicle.setCustomer(customer);

		return vehicleRepository.save(vehicle);
	}

	public java.util.List<Vehicle> getVehiclesByCustomer(Long customerId) {

		return vehicleRepository.findByCustomerId(customerId);
	}

	public List<Vehicle> getAllVehicles() {

		return vehicleRepository.findAll();
	}

	public Vehicle getVehicleById(Long vehicleId) {

		return vehicleRepository.findById(vehicleId)
				.orElseThrow(() -> new VehicleNotFoundException("Vehicle not found with id: " + vehicleId));
	}

	public Vehicle updateVehicle(Long vehicleId, VehicleRequest request) {

		Vehicle vehicle = vehicleRepository.findById(vehicleId)
				.orElseThrow(() -> new VehicleNotFoundException("Vehicle not found with id: " + vehicleId));

		vehicle.setVehicleNumber(request.getVehicleNumber());
		vehicle.setBrand(request.getBrand());
		vehicle.setModel(request.getModel());
		vehicle.setVehicleType(request.getVehicleType());

		return vehicleRepository.save(vehicle);
	}

	public void deleteVehicle(Long vehicleId) {

	    Vehicle vehicle = vehicleRepository
	            .findById(vehicleId)
	            .orElseThrow(() ->
	                    new VehicleNotFoundException(
	                            "Vehicle not found with id: "
	                            + vehicleId));

	    vehicleRepository.delete(vehicle);
	}
}