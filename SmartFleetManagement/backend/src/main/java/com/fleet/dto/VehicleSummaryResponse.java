package com.fleet.dto;

/** One row of the "Vehicle Performance / Expense Summary" report. */
public class VehicleSummaryResponse {

    private Long vehicleId;
    private String vehicleNumber;
    private String vehicleType;
    private String status;
    private long totalTrips;
    private double totalDistance;
    private double fuelExpense;
    private double maintenanceExpense;
    private double totalOperatingCost;

    public VehicleSummaryResponse() {
    }

    public VehicleSummaryResponse(Long vehicleId, String vehicleNumber, String vehicleType, String status,
                                   long totalTrips, double totalDistance, double fuelExpense,
                                   double maintenanceExpense) {
        this.vehicleId = vehicleId;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.status = status;
        this.totalTrips = totalTrips;
        this.totalDistance = totalDistance;
        this.fuelExpense = fuelExpense;
        this.maintenanceExpense = maintenanceExpense;
        this.totalOperatingCost = fuelExpense + maintenanceExpense;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(long totalTrips) {
        this.totalTrips = totalTrips;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getFuelExpense() {
        return fuelExpense;
    }

    public void setFuelExpense(double fuelExpense) {
        this.fuelExpense = fuelExpense;
        this.totalOperatingCost = this.fuelExpense + this.maintenanceExpense;
    }

    public double getMaintenanceExpense() {
        return maintenanceExpense;
    }

    public void setMaintenanceExpense(double maintenanceExpense) {
        this.maintenanceExpense = maintenanceExpense;
        this.totalOperatingCost = this.fuelExpense + this.maintenanceExpense;
    }

    public double getTotalOperatingCost() {
        return totalOperatingCost;
    }

    public void setTotalOperatingCost(double totalOperatingCost) {
        this.totalOperatingCost = totalOperatingCost;
    }
}
