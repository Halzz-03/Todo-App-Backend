package com.todo2.todo2.repo;

import com.todo2.todo2.model.Task;
import com.todo2.todo2.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Integer> {
    List<Task> findByUserIdAndStatus(int userId, TaskStatus status);
    List<Task> findByUserId(int userId);
}