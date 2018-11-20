package com.betroc.controller;

import com.betroc.model.Advertisement;
import com.betroc.payload.ApiResponse;
import com.betroc.repository.AdvertisementBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public abstract class AdBaseController <T extends Advertisement,W extends AdvertisementBaseRepository>{

    @Autowired
    private W repository;

    @GetMapping
    @Secured("ROLE_USER")
    public List<W> getAllAds(){

        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> registerAd(T ad){
        repository.save(ad);
         return ResponseEntity.accepted().body(new ApiResponse(true,"sucess"));
    }

    @PutMapping
    public ResponseEntity<?> updateExchangeAd(T ad){

        //check if the Ad with the same id exist if not return a not found
        Optional exchangeAdOptional = repository.findById(ad.getId());
        if (!exchangeAdOptional.isPresent())
            return ResponseEntity.notFound().build();

        //if there is an Ad with the same id then update
        T exchangeAdWithOldData = (T) exchangeAdOptional.get();
        ad.setCreationDate(exchangeAdWithOldData.getCreationDate());
        ad.setModificationDate(new Date());
        repository.save(ad);

        return ResponseEntity.accepted().body(new ApiResponse(true,"success"));

    }
}
