package com.actify.skilltest.Service;

import java.util.List;

import com.actify.skilltest.dto.UserDTO;
import com.actify.skilltest.dto.UserWithRolesDTO;
import com.actify.skilltest.dto.UserWithTasksDTO;
import com.actify.skilltest.entity.User;

public interface UserService {

    User saveUser(User user);
    UserWithTasksDTO getUserProfileById(String userId);
    List<UserWithTasksDTO> getAllUsersWithAssignTask();
    List<UserDTO> getAllUsers();
    User updateUser(User user);
    void deleteUser(User user);
    void deleteById(String UserId);
    User findByEmail(String email);

    // for admin(to see the roles of the users)
    UserWithRolesDTO getUserAndRoleById(String userId);
    List<UserWithRolesDTO> getAllUserAndRoleById();
}
