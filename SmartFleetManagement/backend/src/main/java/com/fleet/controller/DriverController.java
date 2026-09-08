package com.fleet.controller;

import com.fleet.dto.DriverRequest;
import com.fleet.model.Driver;
import com.fleet.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(driverService.searchDrivers(search));
        }
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @PostMapping
    public ResponseEntity<Driver> createDriver(@Valid @RequestBody DriverRequest request) {
        Driver created = driverService.createDriver(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable Long id, @Valid @RequestBody DriverRequest request) {
        return ResponseEntity.ok(driverService.updateDriver(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    /** Assign (or unassign, with body { "vehicleId": null }) a driver to a vehicle. */
    @PutMapping("/{id}/assign-vehicle")
    public ResponseEntity<Driver> assignVehicle(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        Long vehicleId = body.get("vehicleId");
        return ResponseEntity.ok(driverService.assignVehicle(id, vehicleId));
    }
}
