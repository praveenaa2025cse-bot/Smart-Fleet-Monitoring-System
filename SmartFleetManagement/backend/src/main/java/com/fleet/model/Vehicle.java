package com.fleet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fleet.model.enums.VehicleStatus;
import jakarta.persistence.*;

/**
 * Abstract base class representing a generic Vehicle in the fleet.
 *
 * OOP CONCEPTS DEMONSTRATED HERE:
 *  - Abstraction: Vehicle cannot be instantiated directly; only concrete
 *    subclasses (Car, Truck, Van) can be created.
 *  - Encapsulation: all fields are private with public getters/setters.
 *  - Inheritance: Car, Truck and Van all "extends Vehicle".
 *  - Polymorphism: getType() and calculateMaintenanceFactor() are abstract
 *    here and overridden differently in every subclass.
 *
 * Table strategy: SINGLE_TABLE with a discriminator column "vehicle_category"
 * so Car/Truck/Van rows all live in one "vehicles" table, matching the
 * simple schema.sql provided with this project.
 */
@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_category", discriminatorType = DiscriminatorType.STRING)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Column(name = "vehicle_number", nullable = false, unique = true, length = 20)
    private String vehicleNumber;

    @Column(name = "model", nullable = false, length = 60)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private VehicleStatus status = VehicleStatus.AVAILABLE;

    /** No-args constructor required by JPA/Hibernate. */
    protected Vehicle() {
    }

    /** Convenience constructor used by subclasses. */
    protected Vehicle(String vehicleNumber, String model, VehicleStatus status) {
        this.vehicleNumber = vehicleNumber;
        this.model = model;
        this.status = status == null ? VehicleStatus.AVAILABLE : status;
    }

    // ----------------------------------------------------------------
    // ABSTRACT METHODS - each subclass MUST provide its own
    // implementation. This is where POLYMORPHISM is demonstrated:
    // the same method call behaves differently depending on the
    // actual runtime type (Car / Truck / Van).
    // ----------------------------------------------------------------

    /** Human readable vehicle category, e.g. "Car", "Truck", "Van". */
    public abstract String getType();

    /**
     * A multiplier used when estimating how often a vehicle needs
     * maintenance. Heavier / higher-usage vehicle types have a higher
     * factor. Different subclasses return different values -
     * classic method overriding.
     */
    public abstract double calculateMaintenanceFactor();

    // ----------------------------------------------------------------
    // ENCAPSULATION - private fields exposed only via getters/setters
    // ----------------------------------------------------------------

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getType() + "{id=" + vehicleId + ", number='" + vehicleNumber
                + "', model='" + model + "', status=" + status + '}';
    }
}
