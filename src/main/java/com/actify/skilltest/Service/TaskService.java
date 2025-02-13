package com.actify.skilltest.Service;

import java.util.List;

import com.actify.skilltest.dto.TaskDTO;
import com.actify.skilltest.entity.Task;

public interface TaskService {

    // create a task
    Task createTask(Task task, String managerId);

    // get assign task by user id
    List<Task> getTaskByUserId(String userId);

    //deleteing the task
    void deleteTask(String taskId);


    // converting task to DTO to solve the infinite responce problem
    TaskDTO convertToDTO(Task task);

}
