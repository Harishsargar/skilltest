package com.actify.skilltest.Service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.actify.skilltest.entity.User;
import com.actify.skilltest.repository.UserRepo;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    private static final Logger logger = LoggerFactory.getLogger(SecurityCustomUserDetailService.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        logger.info("Loaded user: {}", user.getEmail());
        logger.info("Roles: {}", user.getRollList());
        return user;
    }


}
