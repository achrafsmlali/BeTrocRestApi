package com.betroc.controller;

import com.betroc.model.ExchangeAd;
import com.betroc.payload.ApiResponse;
import com.betroc.repository.ExchangeAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchangeAds")
public class ExchangesAdController {

    @Autowired
    ExchangeAdRepository exchangeAdRepository;


    @GetMapping
    @Secured("ROLE_USER")
    public List<ExchangeAd> getAllAdExchange(){

            return exchangeAdRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> registerExchangeAd(ExchangeAd exchangeAd){
         exchangeAdRepository.save(exchangeAd);
         return ResponseEntity.accepted().body(new ApiResponse(true,"sucess"));
    }
}
