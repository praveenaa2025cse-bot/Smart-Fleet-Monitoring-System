package com.fleet.dto;

import java.util.List;
import java.util.Map;

/** Aggregated data shown on the Dashboard page. */
public class DashboardResponse {

    private long totalVehicles;
    private long availableVehicles;
    private long onTripVehicles;
    private long underMaintenanceVehicles;
    private long totalDrivers;
    private long totalTrips;
    private double totalFuelExpenses;
    private double totalMaintenanceExpenses;

    private List<Map<String, Object>> maintenanceDueAlerts;
    private List<Map<String, Object>> upcomingMaintenanceAlerts;
    private List<Map<String, Object>> unavailableVehicles;

    public long getTotalVehicles() {
        return totalVehicles;
    }

    public void setTotalVehicles(long totalVehicles) {
        this.totalVehicles = totalVehicles;
    }

    public long getAvailableVehicles() {
        return availableVehicles;
    }

    public void setAvailableVehicles(long availableVehicles) {
        this.availableVehicles = availableVehicles;
    }

    public long getOnTripVehicles() {
        return onTripVehicles;
    }

    public void setOnTripVehicles(long onTripVehicles) {
        this.onTripVehicles = onTripVehicles;
    }

    public long getUnderMaintenanceVehicles() {
        return underMaintenanceVehicles;
    }

    public void setUnderMaintenanceVehicles(long underMaintenanceVehicles) {
        this.underMaintenanceVehicles = underMaintenanceVehicles;
    }

    public long getTotalDrivers() {
        return totalDrivers;
    }

    public void setTotalDrivers(long totalDrivers) {
        this.totalDrivers = totalDrivers;
    }

    public long getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(long totalTrips) {
        this.totalTrips = totalTrips;
    }

    public double getTotalFuelExpenses() {
        return totalFuelExpenses;
    }

    public void setTotalFuelExpenses(double totalFuelExpenses) {
        this.totalFuelExpenses = totalFuelExpenses;
    }

    public double getTotalMaintenanceExpenses() {
        return totalMaintenanceExpenses;
    }

    public void setTotalMaintenanceExpenses(double totalMaintenanceExpenses) {
        this.totalMaintenanceExpenses = totalMaintenanceExpenses;
    }

    public List<Map<String, Object>> getMaintenanceDueAlerts() {
        return maintenanceDueAlerts;
    }

    public void setMaintenanceDueAlerts(List<Map<String, Object>> maintenanceDueAlerts) {
        this.maintenanceDueAlerts = maintenanceDueAlerts;
    }

    public List<Map<String, Object>> getUpcomingMaintenanceAlerts() {
        return upcomingMaintenanceAlerts;
    }

    public void setUpcomingMaintenanceAlerts(List<Map<String, Object>> upcomingMaintenanceAlerts) {
        this.upcomingMaintenanceAlerts = upcomingMaintenanceAlerts;
    }

    public List<Map<String, Object>> getUnavailableVehicles() {
        return unavailableVehicles;
    }

    public void setUnavailableVehicles(List<Map<String, Object>> unavailableVehicles) {
        this.unavailableVehicles = unavailableVehicles;
    }
}
