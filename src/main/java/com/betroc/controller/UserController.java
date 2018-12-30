package com.betroc.controller;

import com.betroc.model.User;
import com.betroc.payload.ApiResponse;
import com.betroc.payload.PasswordUpdateRequest;
import com.betroc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    Optional<User> getUser(@PathVariable("id") long id){
        return userRepository.findById(id);
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
