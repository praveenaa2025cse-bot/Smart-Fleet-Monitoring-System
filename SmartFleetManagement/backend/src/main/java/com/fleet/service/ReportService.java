package com.fleet.service;

import com.fleet.dto.VehicleSummaryResponse;
import com.fleet.model.Trip;
import com.fleet.model.Vehicle;
import com.fleet.model.enums.TripStatus;
import com.fleet.repository.TripRepository;
import com.fleet.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds the "Reports" data: vehicle-wise fuel/maintenance expenses,
 * operating cost, and the overall vehicle performance summary.
 */
@Service
public class ReportService {

    private final VehicleRepository vehicleRepository;
    private final TripRepository tripRepository;
    private final FuelService fuelService;
    private final MaintenanceService maintenanceService;

    @Autowired
    public ReportService(VehicleRepository vehicleRepository, TripRepository tripRepository,
                          FuelService fuelService, MaintenanceService maintenanceService) {
        this.vehicleRepository = vehicleRepository;
        this.tripRepository = tripRepository;
        this.fuelService = fuelService;
        this.maintenanceService = maintenanceService;
    }

    public Map<String, Double> getFuelExpenseReport() {
        return fuelService.getVehicleWiseFuelExpense();
    }

    public Map<String, Double> getMaintenanceExpenseReport() {
        return maintenanceService.getVehicleWiseMaintenanceExpense();
    }

    /** Combines fuel + maintenance expense per vehicle number = operating cost. */
    public Map<String, Double> getOperatingCostReport() {
        Map<String, Double> fuel = getFuelExpenseReport();
        Map<String, Double> maintenance = getMaintenanceExpenseReport();
        Map<String, Double> operatingCost = new HashMap<>(fuel);
        maintenance.forEach((vehicleNumber, cost) -> operatingCost.merge(vehicleNumber, cost, Double::sum));
        return operatingCost;
    }

    public List<VehicleSummaryResponse> getVehicleSummaryReport() {
        List<VehicleSummaryResponse> summaries = new ArrayList<>();

        for (Vehicle vehicle : vehicleRepository.findAll()) {
            List<Trip> trips = tripRepository.findByVehicle(vehicle);
            long totalTrips = trips.size();
            double totalDistance = trips.stream().mapToDouble(t -> t.getDistance() == null ? 0 : t.getDistance()).sum();

            double fuelExpense = fuelService.getTotalFuelExpenseForVehicle(vehicle);
            double maintenanceExpense = maintenanceService.getTotalMaintenanceExpenseForVehicle(vehicle);

            VehicleSummaryResponse summary = new VehicleSummaryResponse(
                    vehicle.getVehicleId(), vehicle.getVehicleNumber(), vehicle.getType(),
                    vehicle.getStatus().name(), totalTrips, totalDistance, fuelExpense, maintenanceExpense);

            summaries.add(summary);
        }

        return summaries;
    }

    /** Count of vehicles grouped by status (Available / On Trip / Under Maintenance). */
    public Map<String, Long> getVehicleStatusSummary() {
        Map<String, Long> statusSummary = new HashMap<>();
        for (Vehicle vehicle : vehicleRepository.findAll()) {
            statusSummary.merge(vehicle.getStatus().name(), 1L, Long::sum);
        }
        return statusSummary;
    }

    public long countOngoingTrips() {
        return tripRepository.findByTripStatus(TripStatus.ONGOING).size();
    }
}
