package com.betroc.controller;

import com.betroc.event.OnRegistrationCompleteEvent;
import com.betroc.exception.AppException;
import com.betroc.model.Role;
import com.betroc.model.RoleName;
import com.betroc.model.User;
import com.betroc.model.VerificationToken;
import com.betroc.payload.*;
import com.betroc.repository.RoleRepository;
import com.betroc.repository.UserRepository;
import com.betroc.repository.VerificationTokenRepository;
import com.betroc.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    ApplicationEventPublisher eventPublisher;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        if(userRepository.existsByUsername(loginRequest.getUsernameOrEmail())) {
            Optional<User> user = userRepository.findByUsernameOrEmail(loginRequest.getUsernameOrEmail(),loginRequest.getUsernameOrEmail().toString());
            if(!user.get().isEnabled())
                return new ResponseEntity(new ApiResponse(false, "Nous avons un mail de confirmation à: \n" + user.get().getEmail() +"\n Veuillez confirmer votre compte"),
                        HttpStatus.LOCKED);
        }

        if(userRepository.existsByUsername(loginRequest.getUsernameOrEmail()) || userRepository.existsByEmail(loginRequest.getUsernameOrEmail())) {
            //TODO if username or email does not exist then return not resgistred
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);

            long idUser = this.userRepository.findByUsernameOrEmail(loginRequest.getUsernameOrEmail(),
                    loginRequest.getUsernameOrEmail()).get().getId();

            return
                    ResponseEntity.ok().body(new LoginResponse(idUser,jwt ));
        }else if(!userRepository.existsByUsername(loginRequest.getUsernameOrEmail()))
            return new ResponseEntity(new ApiResponse(false, "Username does not existe"),
                    HttpStatus.NOT_FOUND);//we can note use ResponseEntity.notFound().found() because we need
                                            // to send message and notFound does not support body()
        else
            return new ResponseEntity(new ApiResponse(false, "Email oes not existe"),
                    HttpStatus.NOT_FOUND);


    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {

        String urlToThisServlet = request.getRequestURL().substring(0,request.getRequestURL().length() - 7);

        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.CONFLICT);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.FORBIDDEN);
        }

        //TODO exisitng mail or username but wrong password return wrong password

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);//TODO if user not created exp and move it ti after email verification (next line) to catch the expetion if the mail was not sent because of a non valid mail
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(result,urlToThisServlet));//TODO if there is a probleme in mail server don't register user
        return ResponseEntity.accepted().body(new ApiResponse(true, "User registered successfully please confirme"));

    }

    @GetMapping("/confirmRegistrationOrEmailUpdate")
    public String confirmRegistrationOrEmailUpdate( @RequestParam("token") String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null)
            return "BadUser invalidToken";

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0)
            return "BadUser Token expired";

        if (!verificationToken.getNewEmail().equals("")){//then it's a request for confirming an email update

            user.setEmail(verificationToken.getNewEmail());
            userRepository.save(user);
            return "user email updated successfully";

        }else{//it's a request for confirming an email for a new account

            user.setEnabled(true);
            userRepository.save(user);
            return "user verified";

        }

    }

//    @PostMapping("/signup/facebook")
//    public ResponseEntity<?> registerUserFacebook(@Valid @RequestBody LoginFacebookRequest loginFacebookRequest, HttpServletRequest request) {
//
//        String urlToThisServlet = request.getRequestURL().substring(0,request.getRequestURL().length() - 7);
//
//
//        if(userRepository.existsByEmail(loginFacebookRequest.getEmailFacebook())) {
//            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
//                    HttpStatus.FORBIDDEN);
//        }
//
//        //TODO exisitng mail or username but wrong password return wrong password
//
//        User user = new User(loginFacebookRequest.getIdNameFacebook().toString().toLowerCase(),loginFacebookRequest.getUserNameFacebook(),
//                loginFacebookRequest.getEmailFacebook(),loginFacebookRequest.getIdNameFacebook()+loginFacebookRequest.getUserNameFacebook());
//
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
//                .orElseThrow(() -> new AppException("User Role not set."));
//
//        user.setRoles(Collections.singleton(userRole));
//        user.setEnabled(true);
//        User result = userRepository.save(user);//TODO if user not created exp and move it ti after email verification (next line) to catch the expetion if the mail was not sent because of a non valid mail
//        return ResponseEntity.ok().build();
//    }

}
