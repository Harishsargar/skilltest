package com.actify.skilltest.repository;

import com.actify.skilltest.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, String> {
    List<Task> findByUser_Id(String userId);
}
