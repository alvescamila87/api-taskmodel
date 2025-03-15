package com.senai.taskmodel.task.controllers;

import com.senai.taskmodel.task.dtos.TaskDTO;
import com.senai.taskmodel.task.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService service;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> listAllTasks() {
        List<TaskDTO> listAllTasksDTO = service.findAllTasks();

        return ResponseEntity.ok().body(listAllTasksDTO);
    }

    @PostMapping ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskDTO taskDTO) {
        TaskDTO newTask = new TaskDTO();

        return ResponseEntity.ok().body(newTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody @Valid TaskDTO taskDTO){
        TaskDTO updateTask = new TaskDTO();


        return ResponseEntity.ok().body(updateTask);
    }

    @DeleteMapping("/id")
    public ResponseEntity<TaskDTO> deleteTask(@PathVariable Long id) {
        TaskDTO deleteTask = new TaskDTO();

        return ResponseEntity.ok().body(deleteTask);
    }
}
