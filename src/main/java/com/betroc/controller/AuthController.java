package com.betroc.controller;

import com.betroc.event.OnRegistrationCompleteEvent;
import com.betroc.exception.AppException;
import com.betroc.model.Role;
import com.betroc.model.RoleName;
import com.betroc.model.User;
import com.betroc.model.VerificationToken;
import com.betroc.payload.ApiResponse;
import com.betroc.payload.JwtAuthenticationResponse;
import com.betroc.payload.LoginRequest;
import com.betroc.payload.SignUpRequest;
import com.betroc.repository.RoleRepository;
import com.betroc.repository.UserRepository;
import com.betroc.repository.VerificationTokenRepository;
import com.betroc.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

        //TODO if username or email does not exist then return not resgistred
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {

        String urlToThisServlet = request.getRequestURL().substring(0,request.getRequestURL().length() - 7);

        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        //TODO exisitng mail or username but wrong password return wrong password

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);//TODO if user not created exp and move it ti after email verification (next line) to catch the expetion if the mail was not sent because of a non valid mail
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(result,urlToThisServlet));
        return ResponseEntity.accepted().body(new ApiResponse(true, "User registered successfully please confirme"));

    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration( @RequestParam("token") String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null)
            return "BadUser invalidToken";

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0)
            return "BadUser Token expired";

        user.setEnabled(true);
        userRepository.save(user);

        return "user verified";
    }
}
