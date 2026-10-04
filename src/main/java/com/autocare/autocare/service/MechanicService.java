package com.autocare.autocare.service;

import com.autocare.autocare.exception.MechanicNotFoundException;
import org.springframework.stereotype.Service;

import com.autocare.autocare.entity.Mechanic;
import com.autocare.autocare.repository.MechanicRepository;

@Service
public class MechanicService {

	private final MechanicRepository mechanicRepository;

	public MechanicService(MechanicRepository mechanicRepository) {
		this.mechanicRepository = mechanicRepository;
	}

	public Mechanic addMechanic(Mechanic mechanic) {
		return mechanicRepository.save(mechanic);
	}

	public java.util.List<Mechanic> getAllMechanics() {

		return mechanicRepository.findAll();
	}

	public Mechanic getMechanicById(Long mechanicId) {

		return mechanicRepository.findById(mechanicId)
				.orElseThrow(() -> new MechanicNotFoundException("Mechanic not found with id: " + mechanicId));
	}

	public Mechanic updateMechanic(Long mechanicId, Mechanic request) {

		Mechanic mechanic = mechanicRepository.findById(mechanicId)
				.orElseThrow(() -> new MechanicNotFoundException("Mechanic not found with id: " + mechanicId));

		mechanic.setName(request.getName());
		mechanic.setPhone(request.getPhone());
		mechanic.setSpecialization(request.getSpecialization());

		return mechanicRepository.save(mechanic);
	}

	public void deleteMechanic(Long mechanicId) {

		Mechanic mechanic = mechanicRepository.findById(mechanicId)
				.orElseThrow(() -> new MechanicNotFoundException("Mechanic not found with id: " + mechanicId));

		mechanicRepository.delete(mechanic);
	}
}