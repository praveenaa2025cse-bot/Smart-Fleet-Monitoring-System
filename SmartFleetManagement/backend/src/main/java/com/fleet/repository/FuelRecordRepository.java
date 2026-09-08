package com.fleet.repository;

import com.fleet.model.FuelRecord;
import com.fleet.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuelRecordRepository extends JpaRepository<FuelRecord, Long> {

    List<FuelRecord> findByVehicle(Vehicle vehicle);
}
