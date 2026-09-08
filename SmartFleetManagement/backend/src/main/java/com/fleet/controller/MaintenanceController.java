package com.fleet.controller;

import com.fleet.dto.MaintenanceRecordRequest;
import com.fleet.model.MaintenanceRecord;
import com.fleet.model.Vehicle;
import com.fleet.service.MaintenanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @Autowired
    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceRecord>> getAllMaintenanceRecords() {
        return ResponseEntity.ok(maintenanceService.getAllMaintenanceRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceRecord> getMaintenanceRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceService.getMaintenanceRecordById(id));
    }

    @PostMapping
    public ResponseEntity<MaintenanceRecord> createMaintenanceRecord(@Valid @RequestBody MaintenanceRecordRequest request) {
        MaintenanceRecord created = maintenanceService.createMaintenanceRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceRecord> updateMaintenanceRecord(@PathVariable Long id,
                                                                      @Valid @RequestBody MaintenanceRecordRequest request) {
        return ResponseEntity.ok(maintenanceService.updateMaintenanceRecord(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenanceRecord(@PathVariable Long id) {
        maintenanceService.deleteMaintenanceRecord(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/vehicle/{vehicleId}/complete")
    public ResponseEntity<Vehicle> completeMaintenance(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(maintenanceService.completeMaintenance(vehicleId));
    }

    @GetMapping("/total-expense")
    public ResponseEntity<Map<String, Double>> getTotalExpense() {
        return ResponseEntity.ok(Map.of("totalMaintenanceExpense", maintenanceService.getTotalMaintenanceExpense()));
    }

    @GetMapping("/due")
    public ResponseEntity<List<MaintenanceRecord>> getOverdue() {
        return ResponseEntity.ok(maintenanceService.getOverdueMaintenance());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MaintenanceRecord>> getUpcoming() {
        return ResponseEntity.ok(maintenanceService.getUpcomingMaintenance());
    }
}
