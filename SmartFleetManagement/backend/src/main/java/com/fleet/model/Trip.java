package com.fleet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fleet.model.enums.TripStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Trip entity - links a Vehicle and a Driver for a journey between
 * a source and destination on a given date.
 */
@Entity
@Table(name = "trips")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long tripId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(name = "source", nullable = false, length = 100)
    private String source;

    @Column(name = "destination", nullable = false, length = 100)
    private String destination;

    @Column(name = "distance", nullable = false)
    private Double distance;

    @Column(name = "trip_date", nullable = false)
    private LocalDate tripDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "trip_status", nullable = false, length = 20)
    private TripStatus tripStatus = TripStatus.PENDING;

    public Trip() {
    }

    public Trip(Vehicle vehicle, Driver driver, String source, String destination,
                Double distance, LocalDate tripDate, TripStatus tripStatus) {
        this.vehicle = vehicle;
        this.driver = driver;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.tripDate = tripDate;
        this.tripStatus = tripStatus == null ? TripStatus.PENDING : tripStatus;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }
}
