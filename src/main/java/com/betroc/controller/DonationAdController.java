package com.betroc.controller;

import com.betroc.model.DonationAd;
import com.betroc.repository.DonationAdRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/donationAds")
public class DonationAdController extends AdBaseController<DonationAd,DonationAdRepository>{

    @GetMapping("/closet/lat/{lat}/long/{lng}")
    public List<DonationAd> getEchangeAdsByDistance(@PathVariable("lat") Double lat, @PathVariable("lng") Double lng ){

        return this.repository.getAllByDistance(lng,lat);
    }

}
