package com.fleet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

/** Maintenance/service record for a specific vehicle. */
@Entity
@Table(name = "maintenance_records")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintenance_id")
    private Long maintenanceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "service_type", nullable = false, length = 100)
    private String serviceType;

    @Column(name = "service_date", nullable = false)
    private LocalDate serviceDate;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @Column(name = "remarks", length = 255)
    private String remarks;

    /** Next due date for service - used to power the "maintenance due" smart alert. */
    @Column(name = "next_due_date")
    private LocalDate nextDueDate;

    public MaintenanceRecord() {
    }

    public MaintenanceRecord(Vehicle vehicle, String serviceType, LocalDate serviceDate,
                              Double cost, String remarks, LocalDate nextDueDate) {
        this.vehicle = vehicle;
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.cost = cost;
        this.remarks = remarks;
        this.nextDueDate = nextDueDate;
    }

    public Long getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(Long maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDate nextDueDate) {
        this.nextDueDate = nextDueDate;
    }
}
