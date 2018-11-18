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
}
