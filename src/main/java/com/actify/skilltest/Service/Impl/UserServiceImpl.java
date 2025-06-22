package com.actify.skilltest.Service.Impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.actify.skilltest.Service.UserService;
import com.actify.skilltest.dto.TaskDTO;
import com.actify.skilltest.dto.UserDTO;
import com.actify.skilltest.dto.UserWithRolesDTO;
import com.actify.skilltest.dto.UserWithTasksDTO;
import com.actify.skilltest.entity.User;
import com.actify.skilltest.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();  // creating id for users
        user.setId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // encoding password before storing to db
        return userRepo.save(user);
    }

    @Override
    public UserWithTasksDTO getUserProfileById(String userId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new RuntimeException("user not found with id: "+ userId));   // return null if user not found
        UserWithTasksDTO userWithTasksDTO = new UserWithTasksDTO();
        userWithTasksDTO.setId(user.getId());
        userWithTasksDTO.setName(user.getName());
        userWithTasksDTO.setEmail(user.getEmail());
        
         // Map assigned tasks to TaskDTOs
        List<TaskDTO> taskDTOs = user.getAssignTasks().stream()
        .map(task -> {
            User taskUser = task.getUser();
            User taskManager = task.getManager();

        return new TaskDTO(
                task.getTaskId(),
                task.getTaskName(),
                task.getTaskDescription(),
                (taskUser != null) ? taskUser.getId() : null,          // userId
                (taskUser != null) ? taskUser.getName() : null,        // userName
                (taskManager != null) ? taskManager.getId() : null,    // managerId
                (taskManager != null) ? taskManager.getName() : null     // managerName
        );
    })
    .collect(Collectors.toList());
    userWithTasksDTO.setAssignedTasks(taskDTOs);
        return userWithTasksDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream().map(user-> new UserDTO(user.getId(), user.getName(), user.getEmail())).collect(Collectors.toList());
    }


    // only return the list of user and their task that have USER role
    @Override
    public List<UserWithTasksDTO> getAllUsersWithAssignTask() {
        List<User> users = userRepo.findAll();
    
        return users.stream()
            .filter(user -> user.getRollList().stream()
                    .anyMatch(role -> role.equalsIgnoreCase("USER"))) // Filtering users with "USER" role
            .map(user -> {
                List<TaskDTO> taskDTOs = user.getAssignTasks().stream()
                    .map(task -> new TaskDTO(
                            task.getTaskId(),
                            task.getTaskName(),
                            task.getTaskDescription(),
                            task.getUser().getId(),      // Extracting User ID
                            task.getUser().getName(),    // Extracting User Name
                            task.getManager().getId(),   // Extracting Manager ID
                            task.getManager().getName()  // Extracting Manager Name
                    ))
                    .collect(Collectors.toList());
    
                return new UserWithTasksDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        taskDTOs
                );
            })
            .collect(Collectors.toList());
    }
    
 
    @Override
    public User updateUser(User user) {
       User existingUser = userRepo.findById(user.getId()).orElseThrow();
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        // // Update roles safely
        existingUser.getRollList().clear();
        existingUser.getRollList().addAll(user.getRollList());

        return userRepo.save(existingUser);
       
    }

    @Override
    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    @Override
    public void deleteById(String UserId) {
        userRepo.deleteById(UserId);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found with email: "+ email));
    }

    @Override
    public UserWithRolesDTO getUserAndRoleById(String userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new RuntimeException("user not found with id: "+userId));
        return new UserWithRolesDTO(user.getId(), user.getName(), user.getEmail(), user.getRollList());
    }

    @Override
    public List<UserWithRolesDTO> getAllUserAndRoleById() {
        List<User> users = userRepo.findAll(); // Fetch all users
        return users.stream()
                .map(user -> new UserWithRolesDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRollList() // Map roles directly
                ))
                .collect(Collectors.toList());
    }
    



}
