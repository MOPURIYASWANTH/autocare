package com.autocare.autocare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autocare.autocare.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmailAndPassword(
            String email,
            String password
    );

    boolean existsByEmail(String email);
}