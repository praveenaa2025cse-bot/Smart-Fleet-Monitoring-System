package com.fleet.controller;

import com.fleet.dto.VehicleSummaryResponse;
import com.fleet.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/fuel")
    public ResponseEntity<Map<String, Double>> getFuelReport() {
        return ResponseEntity.ok(reportService.getFuelExpenseReport());
    }

    @GetMapping("/maintenance")
    public ResponseEntity<Map<String, Double>> getMaintenanceReport() {
        return ResponseEntity.ok(reportService.getMaintenanceExpenseReport());
    }

    @GetMapping("/operating-cost")
    public ResponseEntity<Map<String, Double>> getOperatingCostReport() {
        return ResponseEntity.ok(reportService.getOperatingCostReport());
    }

    @GetMapping("/vehicle-summary")
    public ResponseEntity<List<VehicleSummaryResponse>> getVehicleSummary() {
        return ResponseEntity.ok(reportService.getVehicleSummaryReport());
    }

    @GetMapping("/vehicle-status-summary")
    public ResponseEntity<Map<String, Long>> getVehicleStatusSummary() {
        return ResponseEntity.ok(reportService.getVehicleStatusSummary());
    }
}
