package com.actify.skilltest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.actify.skilltest.Service.TaskService;
import com.actify.skilltest.Service.UserService;
import com.actify.skilltest.Service.Impl.SecurityCustomUserDetailService;
import com.actify.skilltest.dto.TaskDTO;
import com.actify.skilltest.dto.UserDTO;
import com.actify.skilltest.dto.UserWithTasksDTO;
import com.actify.skilltest.entity.Task;
import com.actify.skilltest.entity.User;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityCustomUserDetailService.class);
    

    // to see all the users 
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        logger.info("get all user method called");
        List<UserDTO> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    // to see all the users with their asign tasks
    @GetMapping("/getAllUserAndTask")
    public ResponseEntity<List<UserWithTasksDTO>> getUserWithTask(){
        List<UserWithTasksDTO> allUsersWithAssignTask = userService.getAllUsersWithAssignTask();
        return new ResponseEntity<>(allUsersWithAssignTask, HttpStatus.OK); 
    }


    // assign task to user,
    @PostMapping("/assignTask")
    public ResponseEntity<?> assignTask(@RequestBody Task task, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated!");
        }
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        System.out.println("this is task"+ task);
        // System.out.println(task.getUser().toString());
        Task savedTask = taskService.createTask(task,user.getId());
        TaskDTO taskDTO = taskService.convertToDTO(savedTask);
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }


    // view asign task of user

    @GetMapping("/getTask/{userId}")
    public ResponseEntity<List<TaskDTO>> viewTask(@PathVariable String userId){
        List<Task> tasks = taskService.getTaskByUserId(userId);
        List<TaskDTO> taskDTOs = tasks.stream()
                                    .map(taskService::convertToDTO)
                                    .collect(Collectors.toList());
        return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
    }

}
