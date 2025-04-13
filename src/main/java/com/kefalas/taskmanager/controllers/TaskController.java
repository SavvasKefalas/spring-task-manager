package com.kefalas.taskmanager.controllers;

import com.kefalas.taskmanager.models.User;
import com.kefalas.taskmanager.repositories.UserRepository;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kefalas.taskmanager.models.Task;
import com.kefalas.taskmanager.services.TaskService;
import com.kefalas.taskmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    // GET /tasks - Επιστρέφει όλες τις εργασίες
    @GetMapping
    public List<Task> getAllTasks(Authentication authentication) {
        return taskService.getAllTasks(authentication.name());
    }

    // GET /tasks/{id} - Επιστρέφει μία εργασία με το ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id, Authentication authentication) {
        Task task = taskService.getTaskById(id, authentication.name());
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    // POST /tasks - Δημιουργεί μία νέα εργασία
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, Authentication authentication) {

        String username = authentication.name();
        User currentUser = userService.findByUsername(username);
        Task newTask = taskService.createTask(task, currentUser);

        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    // PUT /tasks/{id} - Ενημερώνει μία υπάρχουσα εργασία
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task task, Authentication authentication) {
        Task updatedTask = taskService.updateTask(id, task, authentication.name());
        if (updatedTask == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    // DELETE /tasks/{id} - Διαγράφει μία εργασία
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id, Authentication authentication) {
        boolean isDeleted = taskService.deleteTask(id, authentication.name());
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
