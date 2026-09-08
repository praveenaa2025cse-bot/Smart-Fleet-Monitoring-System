package com.fleet.service;

import com.fleet.dto.FuelRecordRequest;
import com.fleet.exception.FuelRecordNotFoundException;
import com.fleet.exception.InvalidRequestException;
import com.fleet.model.FuelRecord;
import com.fleet.model.Vehicle;
import com.fleet.repository.FuelRecordRepository;
import com.fleet.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Service layer for Fuel record operations and fuel-expense aggregation. */
@Service
public class FuelService {

    private final FuelRecordRepository fuelRecordRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public FuelService(FuelRecordRepository fuelRecordRepository, VehicleRepository vehicleRepository) {
        this.fuelRecordRepository = fuelRecordRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<FuelRecord> getAllFuelRecords() {
        return fuelRecordRepository.findAll();
    }

    public FuelRecord getFuelRecordById(Long id) {
        return fuelRecordRepository.findById(id)
                .orElseThrow(() -> new FuelRecordNotFoundException(id));
    }

    public FuelRecord createFuelRecord(FuelRecordRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new InvalidRequestException("Vehicle not found with id: " + request.getVehicleId()));
        FuelRecord record = new FuelRecord(vehicle, request.getFuelType(), request.getQuantity(),
                request.getCost(), request.getRecordDate());
        return fuelRecordRepository.save(record);
    }

    public FuelRecord updateFuelRecord(Long id, FuelRecordRequest request) {
        FuelRecord record = getFuelRecordById(id);
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new InvalidRequestException("Vehicle not found with id: " + request.getVehicleId()));
        record.setVehicle(vehicle);
        record.setFuelType(request.getFuelType());
        record.setQuantity(request.getQuantity());
        record.setCost(request.getCost());
        record.setRecordDate(request.getRecordDate());
        return fuelRecordRepository.save(record);
    }

    public void deleteFuelRecord(Long id) {
        FuelRecord record = getFuelRecordById(id);
        fuelRecordRepository.delete(record);
    }

    public double getTotalFuelExpense() {
        return fuelRecordRepository.findAll().stream()
                .mapToDouble(FuelRecord::getCost)
                .sum();
    }

    public double getTotalFuelExpenseForVehicle(Vehicle vehicle) {
        return fuelRecordRepository.findByVehicle(vehicle).stream()
                .mapToDouble(FuelRecord::getCost)
                .sum();
    }

    /** Vehicle-wise fuel expense report: { vehicleNumber -> totalCost }. */
    public Map<String, Double> getVehicleWiseFuelExpense() {
        Map<String, Double> result = new HashMap<>();
        for (FuelRecord record : fuelRecordRepository.findAll()) {
            String number = record.getVehicle().getVehicleNumber();
            result.merge(number, record.getCost(), Double::sum);
        }
        return result;
    }
}
