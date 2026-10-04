package com.autocare.autocare.controller;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import com.autocare.autocare.entity.AutoService;
import com.autocare.autocare.service.ServiceService;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping
    public AutoService addService(@RequestBody AutoService service) {
        return serviceService.addService(service);
    }
    @GetMapping
    public List<AutoService> getAllServices() {

        return serviceService.getAllServices();
    }
    @GetMapping("/{serviceId}")
    public AutoService getServiceById(
            @PathVariable Long serviceId) {

        return serviceService.getServiceById(serviceId);
    }
    @PutMapping("/{serviceId}")
    public AutoService updateService(
            @PathVariable Long serviceId,
            @RequestBody AutoService request) {

        return serviceService.updateService(serviceId, request);
    }
    @DeleteMapping("/{serviceId}")
    public void deleteService(@PathVariable Long serviceId) {

        serviceService.deleteService(serviceId);
    }
}