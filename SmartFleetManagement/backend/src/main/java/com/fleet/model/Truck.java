package com.fleet.model;

import com.fleet.model.enums.VehicleStatus;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Truck - concrete subclass of the abstract Vehicle class.
 * Demonstrates INHERITANCE and POLYMORPHISM via method overriding.
 */
@Entity
@DiscriminatorValue("TRUCK")
public class Truck extends Vehicle {

    public Truck() {
        super();
    }

    public Truck(String vehicleNumber, String model, VehicleStatus status) {
        super(vehicleNumber, model, status);
    }

    @Override
    public String getType() {
        return "Truck";
    }

    @Override
    public double calculateMaintenanceFactor() {
        // Trucks carry heavy loads; highest maintenance factor
        return 1.8;
    }
}
