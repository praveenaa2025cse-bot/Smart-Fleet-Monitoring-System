package com.fleet.model;

import com.fleet.model.enums.VehicleStatus;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Car - concrete subclass of the abstract Vehicle class.
 * Demonstrates INHERITANCE ("extends Vehicle") and
 * POLYMORPHISM (overrides getType() and calculateMaintenanceFactor()).
 */
@Entity
@DiscriminatorValue("CAR")
public class Car extends Vehicle {

    public Car() {
        super();
    }

    public Car(String vehicleNumber, String model, VehicleStatus status) {
        super(vehicleNumber, model, status);
    }

    @Override
    public String getType() {
        return "Car";
    }

    @Override
    public double calculateMaintenanceFactor() {
        // Cars are light-duty; lowest maintenance factor
        return 1.0;
    }
}
