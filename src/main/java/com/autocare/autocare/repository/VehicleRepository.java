package com.autocare.autocare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autocare.autocare.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByCustomerId(Long customerId);

}