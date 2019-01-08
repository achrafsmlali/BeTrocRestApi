package com.betroc.controller;

import com.betroc.model.DonationAd;
import com.betroc.model.DonationRequestAd;
import com.betroc.model.ExchangeAd;
import com.betroc.model.User;
import com.betroc.payload.ApiResponse;
import com.betroc.payload.PasswordUpdateRequest;
import com.betroc.payload.ProfileResponse;
import com.betroc.repository.DonationAdRepository;
import com.betroc.repository.DonationRequestAdRepository;
import com.betroc.repository.ExchangeAdRepository;
import com.betroc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    DonationAdRepository donationAdRepository;
    @Autowired
    ExchangeAdRepository exchangeAdRepository;
    @Autowired
    DonationRequestAdRepository donationRequestAdRepository;

    @GetMapping("/{id}")
    ProfileResponse getUser(@PathVariable("id") long id){

        ProfileResponse profileResponse = new ProfileResponse();

        String username = userRepository.findById(id).get().getUsername();
        String email = userRepository.findById(id).get().getEmail();
        List<DonationAd> donationAdsList = this.donationAdRepository.findAllByUser(userRepository.findById(id));
        List<ExchangeAd> exchangeAList = this.exchangeAdRepository.findAllByUser(userRepository.findById(id));
        List<DonationRequestAd> donationRequestAdList = this.donationRequestAdRepository.findAllByUser
                                                                                            (userRepository.findById(id));
        System.out.println(donationRequestAdRepository.getAllIdsByUser(userRepository.findById(id)).size());
        int nb_annonce = donationAdsList.size() + exchangeAList.size() + donationRequestAdList.size();

        profileResponse.setUsername(username);
        profileResponse.setEmail(email);
        profileResponse.setDonationAds(donationAdsList);
        profileResponse.setDonationRequestAds(donationRequestAdList);
        profileResponse.setExchangeAds(exchangeAList);
        profileResponse.setNb_annonce(nb_annonce);
        return profileResponse;
    }


    @PostMapping("/password/update")
    public ResponseEntity updatePassWord(@RequestBody PasswordUpdateRequest passwordUpdateRequest){

        Optional<User> userOp = userRepository.findById(passwordUpdateRequest.getUserId());
        if(userOp.isPresent()) {
            User user = userOp.get();
            //check if the old password matches the one in the database
            boolean passwordMatches = passwordEncoder.matches(passwordUpdateRequest.getOldPassword(), user.getPassword());
            //update the password
            if (passwordMatches) {
                user.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
                userRepository.save(user);
                return ResponseEntity.accepted().body(new ApiResponse(true, "the password was updated"));
            }else{
                return new ResponseEntity(new ApiResponse(false, "the old password you gave is wrong"), HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity(new ApiResponse(false, "the user doesnot exist"), HttpStatus.BAD_REQUEST);
        }

    }
}
