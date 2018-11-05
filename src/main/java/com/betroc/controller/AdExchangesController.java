package com.betroc.controller;

import com.betroc.model.AdDonation;
import com.betroc.model.AdExchange;
import com.betroc.payload.ApiResponse;
import com.betroc.repository.AdDonationRepository;
import com.betroc.repository.AdExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adExchange")
public class AdExchangesController {

    @Autowired
    AdExchangeRepository adExchangeRepository;


    @GetMapping("/all")
    @Secured("ROLE_USER")
    public List<AdExchange> getAllAdExchange(){

            return adExchangeRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> registerExchangeAd(AdExchange adExchange){
         adExchangeRepository.save(adExchange);
         return ResponseEntity.accepted().body(new ApiResponse(true,"sucess"));
    }
}
