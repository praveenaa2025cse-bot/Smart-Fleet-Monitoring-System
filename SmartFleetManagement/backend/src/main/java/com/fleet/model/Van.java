package com.fleet.model;

import com.fleet.model.enums.VehicleStatus;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Van - concrete subclass of the abstract Vehicle class.
 * Demonstrates INHERITANCE and POLYMORPHISM via method overriding.
 */
@Entity
@DiscriminatorValue("VAN")
public class Van extends Vehicle {

    public Van() {
        super();
    }

    public Van(String vehicleNumber, String model, VehicleStatus status) {
        super(vehicleNumber, model, status);
    }

    @Override
    public String getType() {
        return "Van";
    }

    @Override
    public double calculateMaintenanceFactor() {
        // Vans are medium-duty; moderate maintenance factor
        return 1.3;
    }
}
