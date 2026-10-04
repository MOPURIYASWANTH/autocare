package com.autocare.autocare.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.autocare.autocare.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByCustomerId(Long customerId);
	List<Booking> findByMechanicId(Long mechanicId);
	List<Booking> findByVehicleId(Long vehicleId);

}