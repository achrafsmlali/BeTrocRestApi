package com.betroc.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Exchange")
public class ExchangeAd extends Advertisement{

    @Enumerated(EnumType.STRING)
    private State state;


    private float estimatedPrice;

    @Column(columnDefinition = "TEXT")
    private String address;

    private double latitude;

    private double longitude;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public float getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(float estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
