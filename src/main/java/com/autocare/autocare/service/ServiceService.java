package com.autocare.autocare.service;

import com.autocare.autocare.exception.ServiceNotFoundException;
import org.springframework.stereotype.Service;

import com.autocare.autocare.entity.AutoService;
import com.autocare.autocare.repository.ServiceRepository;

@Service
public class ServiceService {

	private final ServiceRepository serviceRepository;

	public ServiceService(ServiceRepository serviceRepository) {
		this.serviceRepository = serviceRepository;
	}

	public AutoService addService(AutoService service) {
		return serviceRepository.save(service);
	}

	public java.util.List<AutoService> getAllServices() {

		return serviceRepository.findAll();
	}

	public AutoService getServiceById(Long serviceId) {

		return serviceRepository.findById(serviceId)
				.orElseThrow(() -> new ServiceNotFoundException("Service not found with id: " + serviceId));
	}

	public AutoService updateService(Long serviceId, AutoService request) {

		AutoService service = serviceRepository.findById(serviceId)
				.orElseThrow(() -> new ServiceNotFoundException("Service not found with id: " + serviceId));

		service.setName(request.getName());
		service.setDescription(request.getDescription());
		service.setPrice(request.getPrice());

		return serviceRepository.save(service);
	}

	public void deleteService(Long serviceId) {

	    AutoService service = serviceRepository
	            .findById(serviceId)
	            .orElseThrow(() ->
	                    new ServiceNotFoundException(
	                            "Service not found with id: "
	                            + serviceId));

	    serviceRepository.delete(service);
	}}