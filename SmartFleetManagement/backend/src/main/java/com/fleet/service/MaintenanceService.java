package com.fleet.service;

import com.fleet.dto.MaintenanceRecordRequest;
import com.fleet.exception.InvalidRequestException;
import com.fleet.exception.MaintenanceRecordNotFoundException;
import com.fleet.model.MaintenanceRecord;
import com.fleet.model.Vehicle;
import com.fleet.model.enums.VehicleStatus;
import com.fleet.repository.MaintenanceRecordRepository;
import com.fleet.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service layer for Maintenance record operations.
 *
 * Business rule: whenever a maintenance record is logged for "today or in
 * the future" (i.e. the vehicle is actively being serviced), the vehicle's
 * status is automatically switched to UNDER_MAINTENANCE. This also powers
 * the "Smart Alerts" on the dashboard (maintenance due / upcoming).
 */
@Service
public class MaintenanceService {

    /** Number of days ahead considered "upcoming" maintenance. */
    private static final long UPCOMING_WINDOW_DAYS = 7;

    private final MaintenanceRecordRepository maintenanceRecordRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public MaintenanceService(MaintenanceRecordRepository maintenanceRecordRepository,
                               VehicleRepository vehicleRepository) {
        this.maintenanceRecordRepository = maintenanceRecordRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<MaintenanceRecord> getAllMaintenanceRecords() {
        return maintenanceRecordRepository.findAll();
    }

    public MaintenanceRecord getMaintenanceRecordById(Long id) {
        return maintenanceRecordRepository.findById(id)
                .orElseThrow(() -> new MaintenanceRecordNotFoundException(id));
    }

    public MaintenanceRecord createMaintenanceRecord(MaintenanceRecordRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new InvalidRequestException("Vehicle not found with id: " + request.getVehicleId()));

        MaintenanceRecord record = new MaintenanceRecord(vehicle, request.getServiceType(), request.getServiceDate(),
                request.getCost(), request.getRemarks(), request.getNextDueDate());

        // If the service date is today or earlier (vehicle is currently being serviced), mark it.
        if (!request.getServiceDate().isAfter(LocalDate.now())) {
            vehicle.setStatus(VehicleStatus.UNDER_MAINTENANCE);
            vehicleRepository.save(vehicle);
        }

        return maintenanceRecordRepository.save(record);
    }

    public MaintenanceRecord updateMaintenanceRecord(Long id, MaintenanceRecordRequest request) {
        MaintenanceRecord record = getMaintenanceRecordById(id);
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new InvalidRequestException("Vehicle not found with id: " + request.getVehicleId()));

        record.setVehicle(vehicle);
        record.setServiceType(request.getServiceType());
        record.setServiceDate(request.getServiceDate());
        record.setCost(request.getCost());
        record.setRemarks(request.getRemarks());
        record.setNextDueDate(request.getNextDueDate());

        return maintenanceRecordRepository.save(record);
    }

    public void deleteMaintenanceRecord(Long id) {
        MaintenanceRecord record = getMaintenanceRecordById(id);
        maintenanceRecordRepository.delete(record);
    }

    /** Marks the vehicle as back in service (AVAILABLE) once maintenance is complete. */
    public Vehicle completeMaintenance(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new InvalidRequestException("Vehicle not found with id: " + vehicleId));
        vehicle.setStatus(VehicleStatus.AVAILABLE);
        return vehicleRepository.save(vehicle);
    }

    public double getTotalMaintenanceExpense() {
        return maintenanceRecordRepository.findAll().stream()
                .mapToDouble(MaintenanceRecord::getCost)
                .sum();
    }

    public double getTotalMaintenanceExpenseForVehicle(Vehicle vehicle) {
        return maintenanceRecordRepository.findByVehicle(vehicle).stream()
                .mapToDouble(MaintenanceRecord::getCost)
                .sum();
    }

    /** Vehicle-wise maintenance expense report: { vehicleNumber -> totalCost }. */
    public Map<String, Double> getVehicleWiseMaintenanceExpense() {
        Map<String, Double> result = new HashMap<>();
        for (MaintenanceRecord record : maintenanceRecordRepository.findAll()) {
            String number = record.getVehicle().getVehicleNumber();
            result.merge(number, record.getCost(), Double::sum);
        }
        return result;
    }

    /** Records whose nextDueDate is today or already passed. */
    public List<MaintenanceRecord> getOverdueMaintenance() {
        LocalDate today = LocalDate.now();
        List<MaintenanceRecord> overdue = new ArrayList<>();
        for (MaintenanceRecord record : maintenanceRecordRepository.findAll()) {
            if (record.getNextDueDate() != null && !record.getNextDueDate().isAfter(today)) {
                overdue.add(record);
            }
        }
        return overdue;
    }

    /** Records whose nextDueDate falls within the next UPCOMING_WINDOW_DAYS days. */
    public List<MaintenanceRecord> getUpcomingMaintenance() {
        LocalDate today = LocalDate.now();
        LocalDate windowEnd = today.plusDays(UPCOMING_WINDOW_DAYS);
        List<MaintenanceRecord> upcoming = new ArrayList<>();
        for (MaintenanceRecord record : maintenanceRecordRepository.findAll()) {
            if (record.getNextDueDate() != null
                    && record.getNextDueDate().isAfter(today)
                    && !record.getNextDueDate().isAfter(windowEnd)) {
                upcoming.add(record);
            }
        }
        return upcoming;
    }
}
