package com.autocare.autocare.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import com.autocare.autocare.entity.Mechanic;
import com.autocare.autocare.service.MechanicService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/mechanics")
public class MechanicController {

    private final MechanicService mechanicService;

    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    @PostMapping
    public Mechanic addMechanic(@RequestBody Mechanic mechanic) {
        return mechanicService.addMechanic(mechanic);
    }
    @GetMapping
    public List<Mechanic> getAllMechanics() {

        return mechanicService.getAllMechanics();
    }
    @GetMapping("/{mechanicId}")
    public Mechanic getMechanicById(
            @PathVariable Long mechanicId) {

        return mechanicService.getMechanicById(mechanicId);
    }
    @PutMapping("/{mechanicId}")
    public Mechanic updateMechanic(
            @PathVariable Long mechanicId,
            @RequestBody Mechanic request) {

        return mechanicService.updateMechanic(mechanicId, request);
    }
    @DeleteMapping("/{mechanicId}")
    public void deleteMechanic(@PathVariable Long mechanicId) {

        mechanicService.deleteMechanic(mechanicId);
    }
}
