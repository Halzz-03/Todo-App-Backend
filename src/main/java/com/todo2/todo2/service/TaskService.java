package com.todo2.todo2.service;

import com.todo2.todo2.model.Task;
import com.todo2.todo2.model.TaskStatus;
import com.todo2.todo2.model.User;
import com.todo2.todo2.repo.TaskRepo;
import com.todo2.todo2.repo.UserRepo;
import com.todo2.todo2.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private JwtUtil jwtUtil;

    public Task createTask(Task task, String token) {
        String username = extractUserName(token);
        User user = userRepo.findByName(username).orElseThrow();
        task.setUser(user);
        task.setStatus(TaskStatus.PENDING);
        return taskRepo.save(task);
    }

    // Extract username from the JWT token with extra logging for debugging.
    private String extractUserName(String token) {
        System.out.println("Extracting username from token header: " + token);
        if (!token.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header must start with 'Bearer '");
        }
        String actualToken = token.substring(7);
        System.out.println("Actual token: " + actualToken);
        if (!jwtUtil.isTokenValid(actualToken)) {
            throw new RuntimeException("Invalid token");
        }
        String username = jwtUtil.extractUsername(actualToken);
        System.out.println("Extracted username: " + username);
        return username;
    }

    public List<Task> getAllTasks(String status, String token) {
        String username = extractUserName(token);
        User user = userRepo.findByName(username).orElseThrow();

        if (status == null || status.equalsIgnoreCase("ALL")) {
            // Return all tasks if status is null or "ALL"
            return taskRepo.findByUserId(user.getId());
        } else {
            TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
            return taskRepo.findByUserIdAndStatus(user.getId(), taskStatus);
        }
    }



    // Updated updateTask method accepts the token to verify ownership.
    public Task updateTask(int id, Task task, String token) {
        String username = extractUserName(token);
        Task existingTask = taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        if (!existingTask.getUser().getName().equals(username)) {
            throw new RuntimeException("Not authorized to update this task");
        }

        if (task.getStatus() != null) {
            System.out.println("Updating status to: " + task.getStatus());  // Debug log
            existingTask.setStatus(task.getStatus());
        }

        return taskRepo.save(existingTask);
    }


    public void deleteTask(int id) {
        taskRepo.deleteById(id);
    }
}
