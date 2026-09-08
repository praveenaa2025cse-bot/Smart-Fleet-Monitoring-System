package com.fleet.service;

import com.fleet.dto.TripRequest;
import com.fleet.exception.InvalidRequestException;
import com.fleet.exception.TripNotFoundException;
import com.fleet.model.Driver;
import com.fleet.model.Trip;
import com.fleet.model.Vehicle;
import com.fleet.model.enums.TripStatus;
import com.fleet.model.enums.VehicleStatus;
import com.fleet.repository.DriverRepository;
import com.fleet.repository.TripRepository;
import com.fleet.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Trip operations.
 *
 * Business rule implemented here:
 *  - When a trip's status becomes ONGOING, its vehicle becomes ON_TRIP.
 *  - When a trip's status becomes COMPLETED, its vehicle becomes AVAILABLE
 *    (unless the vehicle has since been put under maintenance).
 */
@Service
public class TripService {

    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    @Autowired
    public TripService(TripRepository tripRepository, VehicleRepository vehicleRepository,
                        DriverRepository driverRepository) {
        this.tripRepository = tripRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip getTripById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException(id));
    }

    public List<Trip> filterTrips(TripStatus status) {
        if (status == null) {
            return getAllTrips();
        }
        return tripRepository.findByTripStatus(status);
    }

    public Trip createTrip(TripRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new InvalidRequestException("Vehicle not found with id: " + request.getVehicleId()));
        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new InvalidRequestException("Driver not found with id: " + request.getDriverId()));

        if (vehicle.getStatus() == VehicleStatus.UNDER_MAINTENANCE) {
            throw new InvalidRequestException("Cannot create a trip for a vehicle under maintenance");
        }

        TripStatus status = request.getTripStatus() == null ? TripStatus.PENDING : request.getTripStatus();

        Trip trip = new Trip(vehicle, driver, request.getSource(), request.getDestination(),
                request.getDistance(), request.getTripDate(), status);

        applyVehicleStatusForTrip(vehicle, status);
        vehicleRepository.save(vehicle);

        return tripRepository.save(trip);
    }

    public Trip updateTrip(Long id, TripRequest request) {
        Trip trip = getTripById(id);

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new InvalidRequestException("Vehicle not found with id: " + request.getVehicleId()));
        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new InvalidRequestException("Driver not found with id: " + request.getDriverId()));

        trip.setVehicle(vehicle);
        trip.setDriver(driver);
        trip.setSource(request.getSource());
        trip.setDestination(request.getDestination());
        trip.setDistance(request.getDistance());
        trip.setTripDate(request.getTripDate());

        TripStatus newStatus = request.getTripStatus() == null ? trip.getTripStatus() : request.getTripStatus();
        trip.setTripStatus(newStatus);

        applyVehicleStatusForTrip(vehicle, newStatus);
        vehicleRepository.save(vehicle);

        return tripRepository.save(trip);
    }

    public void deleteTrip(Long id) {
        Trip trip = getTripById(id);
        tripRepository.delete(trip);
    }

    /** Applies the "trip status drives vehicle status" business rule. */
    private void applyVehicleStatusForTrip(Vehicle vehicle, TripStatus tripStatus) {
        if (tripStatus == TripStatus.ONGOING) {
            vehicle.setStatus(VehicleStatus.ON_TRIP);
        } else if (tripStatus == TripStatus.COMPLETED) {
            if (vehicle.getStatus() != VehicleStatus.UNDER_MAINTENANCE) {
                vehicle.setStatus(VehicleStatus.AVAILABLE);
            }
        }
        // PENDING trips do not change vehicle status.
    }
}
