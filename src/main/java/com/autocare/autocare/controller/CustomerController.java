package com.autocare.autocare.controller;
import java.util.List;
import com.autocare.autocare.dto.LoginRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import com.autocare.autocare.dto.CustomerResponse;
import com.autocare.autocare.entity.Customer;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.autocare.autocare.service.CustomerService;
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public Customer register(@RequestBody Customer customer) {
        return customerService.registerCustomer(customer);
    }
    @GetMapping
    public List<Customer> getAllCustomers() {

        return customerService.getAllCustomers();
    }
    @PostMapping("/login")
    public CustomerResponse login(@RequestBody LoginRequest request) {

        return customerService.loginCustomer(request);
    }
    @GetMapping("/{customerId}")
    public CustomerResponse getCustomerById(
            @PathVariable Long customerId) {

        return customerService.getCustomerById(customerId);
    }
    @PutMapping("/{customerId}")
    public CustomerResponse updateCustomer(
            @PathVariable Long customerId,
            @RequestBody Customer request) {

        return customerService.updateCustomer(customerId, request);
    }
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(
            @PathVariable Long customerId) {

        customerService.deleteCustomer(customerId);
    }
}
