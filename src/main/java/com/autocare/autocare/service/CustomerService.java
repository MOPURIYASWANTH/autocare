package com.autocare.autocare.service;

import com.autocare.autocare.dto.LoginRequest;
import org.springframework.stereotype.Service;
import com.autocare.autocare.dto.CustomerResponse;
import com.autocare.autocare.entity.Customer;
import com.autocare.autocare.repository.CustomerRepository;
import com.autocare.autocare.exception.EmailAlreadyExistsException;
import com.autocare.autocare.exception.CustomerNotFoundException;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Customer registerCustomer(Customer customer) {

		if (customerRepository.existsByEmail(customer.getEmail())) {
			throw new EmailAlreadyExistsException("Email already registered");
		}

		return customerRepository.save(customer);
	}

	public java.util.List<Customer> getAllCustomers() {

		return customerRepository.findAll();
	}

	public CustomerResponse loginCustomer(LoginRequest request) {

		Customer customer = customerRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
				.orElseThrow(() -> new RuntimeException("Invalid email or password"));
		return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone());
	}

	public CustomerResponse getCustomerById(Long customerId) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));

		return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone());
	}

	public CustomerResponse updateCustomer(Long customerId, Customer request) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
		if (!customer.getEmail().equals(request.getEmail()) && customerRepository.existsByEmail(request.getEmail())) {

			throw new EmailAlreadyExistsException("Email already registered");
		}

		customer.setName(request.getName());
		customer.setEmail(request.getEmail());
		customer.setPhone(request.getPhone());

		Customer updatedCustomer = customerRepository.save(customer);

		return new CustomerResponse(updatedCustomer.getId(), updatedCustomer.getName(), updatedCustomer.getEmail(),
				updatedCustomer.getPhone());
	}

	public void deleteCustomer(Long customerId) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));

		customerRepository.delete(customer);
	}
}
