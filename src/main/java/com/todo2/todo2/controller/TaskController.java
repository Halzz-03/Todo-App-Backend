package com.todo2.todo2.controller;

import com.todo2.todo2.model.Task;
import com.todo2.todo2.service.TaskService;
import com.todo2.todo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Task createTask(@RequestBody Task task, @RequestHeader("Authorization") String token) {
        return taskService.createTask(task, token);
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) String status, @RequestHeader("Authorization") String token) {
        return taskService.getAllTasks(status, token);
    }


    @PutMapping("/{id}")
    public Task updateTask(@PathVariable int id, @RequestBody Task task, @RequestHeader("Authorization") String token) {
        return taskService.updateTask(id, task, token);
    }


    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
    }
}