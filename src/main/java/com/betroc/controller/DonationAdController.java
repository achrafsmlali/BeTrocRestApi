package com.betroc.controller;

import com.betroc.model.DonationAd;
import com.betroc.payload.ApiResponse;
import com.betroc.repository.DonationAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/donationAds")
public class DonationAdController {

    @Autowired
    DonationAdRepository donationAdRepository;

    @GetMapping
    @Secured("ROLE_USER")
    public List<DonationAd> getAllAdExchange(){

        return donationAdRepository.findAll();
    }
    @PostMapping
    ResponseEntity<?> registerExchangeAd(DonationAd donationAd){
        donationAdRepository.save(donationAd);
        return ResponseEntity.accepted().body(new ApiResponse(true,"sucess"));
    }
}
