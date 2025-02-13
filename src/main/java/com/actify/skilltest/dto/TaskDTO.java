package com.actify.skilltest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private String taskId;
    private String taskName;
    private String taskDescription;
    private String userId; // Or UserDTO if you want more user details
    private String userName;
    private String managerId; // Or UserDTO if you want more manager details
    private String managerName;
}
