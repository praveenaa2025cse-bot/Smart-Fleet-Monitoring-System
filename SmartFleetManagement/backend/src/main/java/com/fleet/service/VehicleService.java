package com.fleet.service;

import com.fleet.dto.VehicleRequest;
import com.fleet.exception.InvalidRequestException;
import com.fleet.exception.VehicleNotFoundException;
import com.fleet.model.Car;
import com.fleet.model.Truck;
import com.fleet.model.Van;
import com.fleet.model.Vehicle;
import com.fleet.model.enums.VehicleStatus;
import com.fleet.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Vehicle operations.
 *
 * Demonstrates POLYMORPHISM: depending on the requested VehicleType,
 * a different concrete subclass (Car/Truck/Van) is instantiated and
 * assigned to a Vehicle reference - "Vehicle vehicle = new Car();" etc.
 * From here on, every other part of the application only deals with
 * the abstract Vehicle type, calling overridden methods like getType()
 * and calculateMaintenanceFactor() without knowing the concrete class.
 */
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
    }

    public List<Vehicle> searchVehicles(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllVehicles();
        }
        return vehicleRepository.findByVehicleNumberContainingIgnoreCaseOrModelContainingIgnoreCase(keyword, keyword);
    }

    public Vehicle createVehicle(VehicleRequest request) {
        vehicleRepository.findByVehicleNumberIgnoreCase(request.getVehicleNumber())
                .ifPresent(v -> {
                    throw new InvalidRequestException("A vehicle with number " + request.getVehicleNumber() + " already exists");
                });

        // POLYMORPHISM in action: same variable type (Vehicle), different
        // concrete object depending on the requested type.
        Vehicle vehicle = createVehicleInstance(request);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Long id, VehicleRequest request) {
        Vehicle existing = getVehicleById(id);
        existing.setVehicleNumber(request.getVehicleNumber());
        existing.setModel(request.getModel());
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
        }
        // Note: changing the vehicle "type" (Car -> Truck) is intentionally
        // not supported here since it would require replacing the entity's
        // discriminator, which JPA does not allow in place.
        return vehicleRepository.save(existing);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = getVehicleById(id);
        vehicleRepository.delete(vehicle);
    }

    public Vehicle updateStatus(Long id, VehicleStatus status) {
        Vehicle vehicle = getVehicleById(id);
        vehicle.setStatus(status);
        return vehicleRepository.save(vehicle);
    }

    /** Factory-style helper that builds the right subclass based on the requested type. */
    private Vehicle createVehicleInstance(VehicleRequest request) {
        VehicleStatus status = request.getStatus() == null ? VehicleStatus.AVAILABLE : request.getStatus();
        switch (request.getType()) {
            case CAR:
                return new Car(request.getVehicleNumber(), request.getModel(), status);
            case TRUCK:
                return new Truck(request.getVehicleNumber(), request.getModel(), status);
            case VAN:
                return new Van(request.getVehicleNumber(), request.getModel(), status);
            default:
                throw new InvalidRequestException("Unsupported vehicle type: " + request.getType());
        }
    }
}
