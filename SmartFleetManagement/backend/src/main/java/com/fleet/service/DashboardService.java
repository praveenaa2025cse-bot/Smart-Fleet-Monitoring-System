package com.fleet.service;

import com.fleet.dto.DashboardResponse;
import com.fleet.model.MaintenanceRecord;
import com.fleet.model.Vehicle;
import com.fleet.model.enums.VehicleStatus;
import com.fleet.repository.DriverRepository;
import com.fleet.repository.TripRepository;
import com.fleet.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Builds the aggregated payload consumed by dashboard.html. */
@Service
public class DashboardService {

    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final TripRepository tripRepository;
    private final FuelService fuelService;
    private final MaintenanceService maintenanceService;

    @Autowired
    public DashboardService(VehicleRepository vehicleRepository, DriverRepository driverRepository,
                             TripRepository tripRepository, FuelService fuelService,
                             MaintenanceService maintenanceService) {
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.tripRepository = tripRepository;
        this.fuelService = fuelService;
        this.maintenanceService = maintenanceService;
    }

    public DashboardResponse getDashboardData() {
        DashboardResponse response = new DashboardResponse();

        List<Vehicle> allVehicles = vehicleRepository.findAll();
        response.setTotalVehicles(allVehicles.size());
        response.setAvailableVehicles(vehicleRepository.countByStatus(VehicleStatus.AVAILABLE));
        response.setOnTripVehicles(vehicleRepository.countByStatus(VehicleStatus.ON_TRIP));
        response.setUnderMaintenanceVehicles(vehicleRepository.countByStatus(VehicleStatus.UNDER_MAINTENANCE));

        response.setTotalDrivers(driverRepository.count());
        response.setTotalTrips(tripRepository.count());

        response.setTotalFuelExpenses(fuelService.getTotalFuelExpense());
        response.setTotalMaintenanceExpenses(maintenanceService.getTotalMaintenanceExpense());

        // ---------------- Smart Alerts ----------------
        List<Map<String, Object>> maintenanceDue = new ArrayList<>();
        for (MaintenanceRecord record : maintenanceService.getOverdueMaintenance()) {
            Map<String, Object> alert = new HashMap<>();
            alert.put("vehicleNumber", record.getVehicle().getVehicleNumber());
            alert.put("dueDate", record.getNextDueDate());
            alert.put("message", "Vehicle " + record.getVehicle().getVehicleNumber() + " maintenance is due soon.");
            maintenanceDue.add(alert);
        }
        response.setMaintenanceDueAlerts(maintenanceDue);

        List<Map<String, Object>> upcoming = new ArrayList<>();
        for (MaintenanceRecord record : maintenanceService.getUpcomingMaintenance()) {
            Map<String, Object> alert = new HashMap<>();
            alert.put("vehicleNumber", record.getVehicle().getVehicleNumber());
            alert.put("dueDate", record.getNextDueDate());
            alert.put("message", "Vehicle " + record.getVehicle().getVehicleNumber()
                    + " has upcoming maintenance on " + record.getNextDueDate() + ".");
            upcoming.add(alert);
        }
        response.setUpcomingMaintenanceAlerts(upcoming);

        List<Map<String, Object>> unavailable = new ArrayList<>();
        for (Vehicle vehicle : allVehicles) {
            if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
                Map<String, Object> item = new HashMap<>();
                item.put("vehicleNumber", vehicle.getVehicleNumber());
                item.put("type", vehicle.getType());
                item.put("status", vehicle.getStatus());
                unavailable.add(item);
            }
        }
        response.setUnavailableVehicles(unavailable);

        return response;
    }
}
