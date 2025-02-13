package com.actify.skilltest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.actify.skilltest.Service.UserService;
import com.actify.skilltest.dto.UserWithTasksDTO;
import com.actify.skilltest.entity.User;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // view profile
    @GetMapping("/getUserProfile")
    public ResponseEntity<UserWithTasksDTO> getUserProfile(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();   // our username is email address
        User user = userService.findByEmail(email);
        System.out.println("userid is: "+ user.getId() );
        UserWithTasksDTO userProfile = userService.getUserProfileById(user.getId());
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }
}
