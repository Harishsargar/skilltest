package com.actify.skilltest.Service.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.actify.skilltest.Service.TaskService;
import com.actify.skilltest.dto.TaskDTO;
import com.actify.skilltest.entity.Task;
import com.actify.skilltest.entity.User;
import com.actify.skilltest.repository.TaskRepo;
import com.actify.skilltest.repository.UserRepo;
import com.actify.skilltest.util.CustomRuntimeException;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private UserRepo userRepo;


    // this will assign the task to the users
    @Override
    public Task createTask(Task task, String managerId) {
        // if (task.getUser() == null || task.getManager() == null) {
        //     throw new IllegalArgumentException("User and Manager must not be ...null");
        // }
        
        User user = userRepo.findById(task.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found"));
        User manager = userRepo.findById(managerId).orElseThrow(()-> new CustomRuntimeException("manager not found: "+managerId));
       
        // Ensure the assigned user has only the 'USER' role
        if (!user.getRollList().contains("USER")) {
            throw new CustomRuntimeException("Tasks can only be assigned to USERS, not Admins or Managers. :");
        }
        
        task.setUser(user);
        task.setManager(manager);

        String taskId = UUID.randomUUID().toString();
        task.setTaskId(taskId);

        return taskRepo.save(task);
    }

    @Override
    public List<Task> getTaskByUserId(String userId) {
        return taskRepo.findByUser_Id(userId);
    }

    @Override
    public void deleteTask(String taskId) {
        taskRepo.deleteById(taskId);
    }

    @Override
    public TaskDTO convertToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(task.getTaskId());
        taskDTO.setTaskName(task.getTaskName());
        taskDTO.setTaskDescription(task.getTaskDescription());
        taskDTO.setUserId(task.getUser().getId());
        taskDTO.setUserName(task.getUser().getName());
        taskDTO.setManagerId(task.getManager().getId());
         taskDTO.setManagerName(task.getManager().getName());
        return taskDTO;
    }

}
