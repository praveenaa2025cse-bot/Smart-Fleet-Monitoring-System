package com.fleet.repository;

import com.fleet.model.Trip;
import com.fleet.model.Vehicle;
import com.fleet.model.enums.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByTripStatus(TripStatus status);

    List<Trip> findByVehicle(Vehicle vehicle);

    long countByVehicle(Vehicle vehicle);
}
