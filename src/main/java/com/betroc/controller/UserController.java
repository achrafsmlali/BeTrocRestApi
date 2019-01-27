package com.betroc.controller;

import com.betroc.event.OnRegistrationCompleteEvent;
import com.betroc.model.DonationAd;
import com.betroc.model.DonationRequestAd;
import com.betroc.model.ExchangeAd;
import com.betroc.model.User;
import com.betroc.payload.ApiResponse;
import com.betroc.payload.EmailUpdateRequest;
import com.betroc.payload.PasswordUpdateRequest;
import com.betroc.payload.ProfileResponse;
import com.betroc.repository.*;
import com.betroc.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
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

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Value("${SERVER.URL}")
    String serverUrl;

    @Autowired
    ImageRepository imageRepository;

    @GetMapping("/{id}")
    ProfileResponse getUser(@PathVariable("id") long id){

        ProfileResponse profileResponse = new ProfileResponse();

        String username = userRepository.findById(id).get().getUsername();
        String email = userRepository.findById(id).get().getEmail();
        List<DonationAd> donationAdsList = this.donationAdRepository.findAllByUser(userRepository.findById(id));
        List<ExchangeAd> exchangeAList = this.exchangeAdRepository.findAllByUser(userRepository.findById(id));
        List<DonationRequestAd> donationRequestAdList = this.donationRequestAdRepository.findAllByUser
                                                                                            (userRepository.findById(id));
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

        //get Authenticated user info
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        //check if the account for which the password will be updated is for the Authenticated one
        if (userPrincipal.getId() != passwordUpdateRequest.getUserId())
            return new ResponseEntity(new ApiResponse(false, "you are not authorized"),
                    HttpStatus.BAD_REQUEST);

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

    @PostMapping("/email/update")
    public ResponseEntity updateEmail(@RequestBody EmailUpdateRequest emailUpdateRequest,HttpServletRequest request){

        //get Authenticated user info
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        //check if the account for which the email will be updated is for the Authenticated one
        if (userPrincipal.getId() != emailUpdateRequest.getUserId())
            return new ResponseEntity(new ApiResponse(false, "you are not authorized"),
                    HttpStatus.BAD_REQUEST);

        Optional<User> userOp = userRepository.findById(emailUpdateRequest.getUserId());

        //the new email already exist
        if (userRepository.existsByEmail(emailUpdateRequest.getNewEmail()))
            return new ResponseEntity(new ApiResponse(false, "email is already taken!"),
                    HttpStatus.BAD_REQUEST);

        if (userOp.isPresent()){
            User user = userOp.get();

            String newEmail = emailUpdateRequest.getNewEmail();

            //the url to auth controller
            String urlToAuthServlet = this.serverUrl+AuthController.class.getAnnotation(RequestMapping.class).value()[0];

            //send an email to user new email
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user,urlToAuthServlet,newEmail));//TODO if there is a probleme in mail server don't register user

            return new ResponseEntity(new ApiResponse(true, "you need to comfirm your new email"), HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(false, "no such user"), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/username/update/{newUsername}")
    public ResponseEntity updateUsername(@PathVariable String newUsername){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        Optional<User> userOp = userRepository.findById(userPrincipal.getId());


        if (userRepository.existsByUsername(newUsername)){
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }else {

            if (userOp.isPresent()){
                User user = userOp.get();
                user.setUsername(newUsername);
                userRepository.save(user);

                return new ResponseEntity(new ApiResponse(true, "username updated successfully"), HttpStatus.OK);
            }else
                return new ResponseEntity(new ApiResponse(false, "no such user"), HttpStatus.BAD_REQUEST);

        }

    }

    @PostMapping("/profileimage/update/{imageId}")
    public ResponseEntity updateProfileImage(@PathVariable long imageId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        Optional<User> userOp = userRepository.findById(userPrincipal.getId());

        if(!imageRepository.existsById(imageId))
            return new ResponseEntity(new ApiResponse(false, "Image with id = "+imageId+" does not exist "),
                    HttpStatus.BAD_REQUEST);

        if (userOp.isPresent()){
            User user = userOp.get();

            user.setProfileImage(imageRepository.findById(imageId).get());
            userRepository.save(user);
            return new ResponseEntity(new ApiResponse(true, "profile Image updated successfully"), HttpStatus.OK);
        }else
            return new ResponseEntity(new ApiResponse(false, "no such user"), HttpStatus.BAD_REQUEST);


    }


}
