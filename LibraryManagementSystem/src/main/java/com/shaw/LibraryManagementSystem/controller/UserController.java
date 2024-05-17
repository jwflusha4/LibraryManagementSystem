package com.shaw.LibraryManagementSystem.controller;

import com.shaw.LibraryManagementSystem.dtos.AuthenticationRequest;
import com.shaw.LibraryManagementSystem.dtos.AuthenticationResponse;
import com.shaw.LibraryManagementSystem.dtos.RegisterRequest;
import com.shaw.LibraryManagementSystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    //private final UserService userService;

    //@PostMapping("/add")
    //public User addUser(@RequestBody RegisterRequest request){
        //return userService.addUser(request);
    //}

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
