package com.actify.skilltest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.actify.skilltest.Service.UserService;
import com.actify.skilltest.dto.UserWithRolesDTO;
import com.actify.skilltest.entity.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    // create user
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    // to see all the users 
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserWithRolesDTO>> getAllUsers(){
        List<UserWithRolesDTO> allUsers = userService.getAllUserAndRoleById();
        return ResponseEntity.ok(allUsers);
    }

    // view single user
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserWithRolesDTO> getUser(@PathVariable String userId){
        UserWithRolesDTO user = userService.getUserAndRoleById(userId);
        if(user!=null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete user by id
    @DeleteMapping("/deleteUserbyId/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String userId){
        userService.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // update user 
    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user){
       User updatedUser =  userService.updateUser(user);
       return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    // assign role to user (it is achived in update user)

}
