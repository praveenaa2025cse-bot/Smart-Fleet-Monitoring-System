package com.fleet.controller;

import com.fleet.dto.FuelRecordRequest;
import com.fleet.model.FuelRecord;
import com.fleet.service.FuelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fuel")
public class FuelController {

    private final FuelService fuelService;

    @Autowired
    public FuelController(FuelService fuelService) {
        this.fuelService = fuelService;
    }

    @GetMapping
    public ResponseEntity<List<FuelRecord>> getAllFuelRecords() {
        return ResponseEntity.ok(fuelService.getAllFuelRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelRecord> getFuelRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(fuelService.getFuelRecordById(id));
    }

    @PostMapping
    public ResponseEntity<FuelRecord> createFuelRecord(@Valid @RequestBody FuelRecordRequest request) {
        FuelRecord created = fuelService.createFuelRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuelRecord> updateFuelRecord(@PathVariable Long id, @Valid @RequestBody FuelRecordRequest request) {
        return ResponseEntity.ok(fuelService.updateFuelRecord(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelRecord(@PathVariable Long id) {
        fuelService.deleteFuelRecord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total-expense")
    public ResponseEntity<Map<String, Double>> getTotalExpense() {
        return ResponseEntity.ok(Map.of("totalFuelExpense", fuelService.getTotalFuelExpense()));
    }
}
