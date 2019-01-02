package com.betroc.controller;

import com.betroc.model.Advertisement;
import com.betroc.payload.ApiResponse;
import com.betroc.repository.AdvertisementBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
public abstract class AdBaseController <T extends Advertisement,W extends AdvertisementBaseRepository>{

    @Autowired
    private W repository;

    @GetMapping
    //@Secured("ROLE_USER")
    public Page getAllAds(@PageableDefault(size = 10, sort = "id") Pageable pageable){

        return repository.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<?> registerAd(@RequestBody T ad){
        repository.save(ad);
         return ResponseEntity.accepted().body(new ApiResponse(true,"sucess"));
    }

    @PutMapping
    public ResponseEntity<?> updateAd(T ad){

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

    @DeleteMapping
    public ResponseEntity<?> deleteAd(T ad){

        if(!repository.existsById(ad.getId()))
            return ResponseEntity.accepted().body(new ApiResponse(false,"failed no such Ad"));
        //TODO delete only with id not the all ad

        repository.delete(ad);

        return ResponseEntity.accepted().body(new ApiResponse(true,"success"));

    }
}
