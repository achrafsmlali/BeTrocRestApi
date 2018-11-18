package com.betroc.controller;

import com.betroc.model.ExchangeAd;
import com.betroc.payload.ApiResponse;
import com.betroc.repository.ExchangeAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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


    @PutMapping
    public ResponseEntity<?> updateExchangeAd(ExchangeAd exchangeAd){

        //check if the Ad with the same id exist if not return a not found
        Optional exchangeAdOptional = exchangeAdRepository.findById(exchangeAd.getId());
        if (!exchangeAdOptional.isPresent())
            return ResponseEntity.notFound().build();

        //if there is an Ad with the same id then update
        ExchangeAd exchangeAdWithOldData = (ExchangeAd) exchangeAdOptional.get();
        exchangeAd.setCreationDate(exchangeAdWithOldData.getCreationDate());
        exchangeAd.setModificationDate(new Date());
        exchangeAdRepository.save(exchangeAd);

        return ResponseEntity.accepted().body(new ApiResponse(true,"sucess"));

    }


}
