package com.autocare.autocare.controller;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;	

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autocare.autocare.dto.VehicleRequest;
import com.autocare.autocare.entity.Vehicle;
import com.autocare.autocare.service.VehicleService;
import org.springframework.web.bind.annotation.PutMapping;
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping 
    public Vehicle addVehicle(@RequestBody VehicleRequest request) {
        return vehicleService.addVehicle(request);
    }
    @GetMapping("/customer/{customerId}")
    public List<Vehicle> getVehiclesByCustomer(
            @PathVariable Long customerId) {

        return vehicleService.getVehiclesByCustomer(customerId);
    }
    @GetMapping
    public List<Vehicle> getAllVehicles() {

        return vehicleService.getAllVehicles();
    }
    @GetMapping("/{vehicleId}")
    public Vehicle getVehicleById(
            @PathVariable Long vehicleId) {

        return vehicleService.getVehicleById(vehicleId);
    }
    @PutMapping("/{vehicleId}")
    public Vehicle updateVehicle(
            @PathVariable Long vehicleId,
            @RequestBody VehicleRequest request) {

        return vehicleService.updateVehicle(vehicleId, request);
    }
    @DeleteMapping("/{vehicleId}")
    public void deleteVehicle(@PathVariable Long vehicleId) {

        vehicleService.deleteVehicle(vehicleId);
    }
}