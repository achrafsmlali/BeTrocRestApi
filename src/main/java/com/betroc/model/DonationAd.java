package com.betroc.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Donation")
public class DonationAd extends Advertisement{

    @Enumerated(EnumType.STRING)
    private State state;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
