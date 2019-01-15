package com.betroc.controller;

import com.betroc.model.Advertisement;
import com.betroc.model.User;
import com.betroc.payload.ApiResponse;
import com.betroc.repository.AdvertisementBaseRepository;
import com.betroc.repository.UserRepository;
import com.betroc.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
public abstract class AdBaseController <T extends Advertisement,W extends AdvertisementBaseRepository>{

    @Autowired
    private W repository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    //@Secured("ROLE_USER")
    public Page getAllAds(@PageableDefault(size = 10, sort = "id") Pageable pageable){

        return repository.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<?> registerAd(T ad){

        //get user data from his Authentication to create a new annonce
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Optional userOp = userRepository.findById(userPrincipal.getId());

        //if the user exist witch is normal :) then add the ad
        if(userOp.isPresent()){
            User user = (User) userOp.get();
            ad.setUser(user);
            repository.save(ad);
            return ResponseEntity.accepted().body(new ApiResponse(true,"sucess"));
        }

        return ResponseEntity.accepted().body(new ApiResponse(true,"failed"));
    }

    @PutMapping
    public ResponseEntity<?> updateAd(T ad){

        //check if the Ad with the same id exist if not return a not found
        Optional exchangeAdOptional = repository.findById(ad.getId());
        if (!exchangeAdOptional.isPresent())
            return ResponseEntity.notFound().build();

        T adWithOldData = (T) exchangeAdOptional.get();

        //check if the user sending the request is the owner of the ad
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        if(userPrincipal.getId() != adWithOldData.getUser().getId())
             return new ResponseEntity(new ApiResponse(false, "you do not have the rights to change this Ad"),
                     HttpStatus.BAD_REQUEST);

        //set the user
        ad.setUser(adWithOldData.getUser());

        //if there is an Ad with the same id then update
        ad.setCreationDate(adWithOldData.getCreationDate());
        ad.setModificationDate(new Date());
        repository.save(ad);

        return ResponseEntity.accepted().body(new ApiResponse(true,"success"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable("id") Long id){

        if(!repository.existsById(id))
            return ResponseEntity.accepted().body(new ApiResponse(false,"failed no such Ad"));


        //get auth data
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        Optional adOp = repository.findById(id);

        //check is the Ad exist
        if(!adOp.isPresent())
            return ResponseEntity.notFound().build();


        T ad = (T) adOp.get();

        //check if the user sending the request is the owner of the ad
        if (ad.getUser().getId() != userPrincipal.getId())
            return new ResponseEntity(new ApiResponse(false, "you do not have the rights to change this Ad"),
                    HttpStatus.BAD_REQUEST);


        repository.deleteById(id);

        return ResponseEntity.accepted().body(new ApiResponse(true,"success"));

    }
}
