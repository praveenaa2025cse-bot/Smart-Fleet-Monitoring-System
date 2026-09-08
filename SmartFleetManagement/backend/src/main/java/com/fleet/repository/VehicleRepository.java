package com.fleet.repository;

import com.fleet.model.Vehicle;
import com.fleet.model.enums.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByVehicleNumberIgnoreCase(String vehicleNumber);

    List<Vehicle> findByStatus(VehicleStatus status);

    List<Vehicle> findByVehicleNumberContainingIgnoreCaseOrModelContainingIgnoreCase(String vehicleNumber, String model);

    long countByStatus(VehicleStatus status);
}
