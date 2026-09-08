package com.fleet.repository;

import com.fleet.model.MaintenanceRecord;
import com.fleet.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, Long> {

    List<MaintenanceRecord> findByVehicle(Vehicle vehicle);
}
