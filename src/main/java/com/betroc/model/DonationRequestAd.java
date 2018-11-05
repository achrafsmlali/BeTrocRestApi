package com.betroc.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("DonationRequest")
public class DonationRequestAd extends Advertisement{

    public DonationRequestAd(@NotNull String email) {
        super(email);
    }

    public DonationRequestAd(){
        super();
    }

}
