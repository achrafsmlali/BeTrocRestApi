package com.betroc.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("Donation")
public class DonationAd extends Advertisement{

    private boolean etat;

    public DonationAd(@NotNull String email, boolean etat) {
        super(email);
        this.etat = etat;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public DonationAd(){
        super();
    }

}
