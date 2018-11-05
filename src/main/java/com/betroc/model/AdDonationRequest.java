package com.betroc.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class AdDonationRequest extends Advertisement{

    public AdDonationRequest(@NotNull String email) {
        super(email);
    }

    public AdDonationRequest(){
        super();
    }

}
