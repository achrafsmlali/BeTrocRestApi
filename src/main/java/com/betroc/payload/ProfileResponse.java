package com.betroc.payload;

import com.betroc.model.DonationAd;
import com.betroc.model.DonationRequestAd;
import com.betroc.model.ExchangeAd;
import com.betroc.model.Image;

import java.util.List;

public class ProfileResponse {

    private String username;

    private String email;

    private List<ExchangeAd> exchangeAds;

    private List<DonationAd> donationAds;

    private List<DonationRequestAd> donationRequestAds;

    private int nb_annonce;

    private Image profileImage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ExchangeAd> getExchangeAds() {
        return exchangeAds;
    }

    public void setExchangeAds(List<ExchangeAd> exchangeAds) {
        this.exchangeAds = exchangeAds;
    }

    public List<DonationAd> getDonationAds() {
        return donationAds;
    }

    public void setDonationAds(List<DonationAd> donationAds) {
        this.donationAds = donationAds;
    }

    public List<DonationRequestAd> getDonationRequestAds() {
        return donationRequestAds;
    }

    public void setDonationRequestAds(List<DonationRequestAd> donationRequestAds) {
        this.donationRequestAds = donationRequestAds;
    }

    public int getNb_annonce() {
        return nb_annonce;
    }

    public void setNb_annonce(int nb_annonce) {
        this.nb_annonce = nb_annonce;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

}
