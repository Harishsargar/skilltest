package com.actify.skilltest.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    private String taskId;
    private String taskName;
    private String taskDescription;

    
    @ManyToOne(fetch = FetchType.LAZY) // many task can be assign to one user(one user can have multiple task)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)  // many task can be assign by single manager (singe managaer can assign multiple tasks)
    private User manager;

}
