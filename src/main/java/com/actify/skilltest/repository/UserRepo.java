package com.actify.skilltest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.actify.skilltest.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);

}
