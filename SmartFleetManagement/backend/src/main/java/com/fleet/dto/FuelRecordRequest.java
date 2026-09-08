package com.fleet.dto;

import com.fleet.model.enums.FuelType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/** Incoming payload for creating/updating a FuelRecord. */
public class FuelRecordRequest {

    @NotNull(message = "Vehicle id is required")
    private Long vehicleId;

    @NotNull(message = "Fuel type is required")
    private FuelType fuelType;

    @NotNull(message = "Quantity is required")
    private Double quantity;

    @NotNull(message = "Cost is required")
    private Double cost;

    @NotNull(message = "Date is required")
    private LocalDate recordDate;

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
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
