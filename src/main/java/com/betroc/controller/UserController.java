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
import com.betroc.repository.DonationAdRepository;
import com.betroc.repository.DonationRequestAdRepository;
import com.betroc.repository.ExchangeAdRepository;
import com.betroc.repository.UserRepository;
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        Optional<User> userOp = userRepository.findById(emailUpdateRequest.getUserId());

        //the new email already exist
        if (userRepository.existsByEmail(emailUpdateRequest.getNewEmail()))
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);

        if (userOp.isPresent()){
            User user = userOp.get();

            String newEmail = emailUpdateRequest.getNewEmail();

            //the url to auth controller
            String urlToAuthServlet = this.serverUrl+AuthController.class.getAnnotation(RequestMapping.class).value()[0];

            //send an email to user new email
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user,urlToAuthServlet,newEmail));//TODO if there is a probleme in mail server don't register user

//            //Logout the user
//            if (auth != null){
//                //SecurityContextLogoutHandler performs a logout by modifying the SecurityContextHolder.
//                System.out.println("shoud log out");
//                new SecurityContextLogoutHandler().logout(request,null,auth);
//            }
            return new ResponseEntity(new ApiResponse(true, "you need to comfirm your new email"), HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponse(false, "no such user"), HttpStatus.BAD_REQUEST);

    }
}
