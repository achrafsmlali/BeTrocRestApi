package com.betroc.controller;

import com.betroc.model.AdDonation;
import com.betroc.payload.ApiResponse;
import com.betroc.repository.AdDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/donation")
public class AdDonationController {

    @Autowired
    AdDonationRepository adDonationRepository;

    @PostMapping
    ResponseEntity<?> registerExchangeAd(AdDonation adDonation){
        adDonationRepository.save(adDonation);
        return ResponseEntity.accepted().body(new ApiResponse(true,"sucess"));
    }
}
