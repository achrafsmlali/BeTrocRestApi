package com.betroc.controller;

import com.betroc.model.DonationAd;
import com.betroc.repository.DonationAdRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/donationAds")
public class DonationAdController extends AdBaseController<DonationAd,DonationAdRepository>{

    @GetMapping("/closet/lat/{lat}/long/{lng}")
    public Page getEchangeAdsByDistance(@PathVariable("lat") Double lat, @PathVariable("lng") Double lng ,@PageableDefault(size = 10, sort = "id") Pageable pageable){

        return this.repository.getAllByDistance(pageable ,lng,lat);
    }

}
