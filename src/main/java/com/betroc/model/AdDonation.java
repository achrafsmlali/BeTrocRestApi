package com.betroc.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class AdDonation extends Advertisement{

    private boolean etat;

    public AdDonation(@NotNull String email, boolean etat) {
        super(email);
        this.etat = etat;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public AdDonation(){
        super();
    }

}
