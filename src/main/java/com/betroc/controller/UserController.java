package com.betroc.controller;

import com.betroc.model.User;
import com.betroc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{id}")
    Optional<User> getUser(@PathVariable("id") long id){
        return userRepository.findById(id);
    }



}
