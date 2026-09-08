package com.fleet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fleet.model.enums.FuelType;
import jakarta.persistence.*;

import java.time.LocalDate;

/** Fuel purchase/consumption record for a specific vehicle. */
@Entity
@Table(name = "fuel_records")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FuelRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuel_id")
    private Long fuelId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false, length = 20)
    private FuelType fuelType;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    public FuelRecord() {
    }

    public FuelRecord(Vehicle vehicle, FuelType fuelType, Double quantity, Double cost, LocalDate recordDate) {
        this.vehicle = vehicle;
        this.fuelType = fuelType;
        this.quantity = quantity;
        this.cost = cost;
        this.recordDate = recordDate;
    }

    public Long getFuelId() {
        return fuelId;
    }

    public void setFuelId(Long fuelId) {
        this.fuelId = fuelId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }
}
