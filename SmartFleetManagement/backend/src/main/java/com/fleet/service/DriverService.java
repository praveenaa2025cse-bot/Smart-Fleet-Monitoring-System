package com.fleet.service;

import com.fleet.dto.DriverRequest;
import com.fleet.exception.DriverNotFoundException;
import com.fleet.exception.InvalidRequestException;
import com.fleet.model.Driver;
import com.fleet.model.Vehicle;
import com.fleet.model.enums.VehicleStatus;
import com.fleet.repository.DriverRepository;
import com.fleet.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** Service layer for Driver operations, including assigning a driver to a vehicle. */
@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository, VehicleRepository vehicleRepository) {
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(id));
    }

    public List<Driver> searchDrivers(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllDrivers();
        }
        return driverRepository.findByNameContainingIgnoreCaseOrLicenseNumberContainingIgnoreCase(keyword, keyword);
    }

    public Driver createDriver(DriverRequest request) {
        Driver driver = new Driver();
        driver.setName(request.getName());
        driver.setPhoneNumber(request.getPhoneNumber());
        driver.setLicenseNumber(request.getLicenseNumber());
        applyVehicleAssignment(driver, request.getAssignedVehicleId());
        return driverRepository.save(driver);
    }

    public Driver updateDriver(Long id, DriverRequest request) {
        Driver driver = getDriverById(id);
        driver.setName(request.getName());
        driver.setPhoneNumber(request.getPhoneNumber());
        driver.setLicenseNumber(request.getLicenseNumber());
        applyVehicleAssignment(driver, request.getAssignedVehicleId());
        return driverRepository.save(driver);
    }

    public void deleteDriver(Long id) {
        Driver driver = getDriverById(id);
        driverRepository.delete(driver);
    }

    /** Assigns a driver to an available vehicle (or clears the assignment if id is null). */
    public Driver assignVehicle(Long driverId, Long vehicleId) {
        Driver driver = getDriverById(driverId);
        applyVehicleAssignment(driver, vehicleId);
        return driverRepository.save(driver);
    }

    private void applyVehicleAssignment(Driver driver, Long vehicleId) {
        if (vehicleId == null) {
            driver.setAssignedVehicle(null);
            return;
        }
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new InvalidRequestException("Vehicle not found with id: " + vehicleId));

        if (vehicle.getStatus() == VehicleStatus.UNDER_MAINTENANCE) {
            throw new InvalidRequestException("Cannot assign a vehicle that is under maintenance");
        }
        driver.setAssignedVehicle(vehicle);
    }
}
