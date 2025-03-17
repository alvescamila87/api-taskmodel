package com.senai.taskmodel.task.controllers;

import com.senai.taskmodel.task.dtos.ResponseTaskDTO;
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
    public ResponseEntity<List<ResponseTaskDTO>> listAllTasks() {
        List<ResponseTaskDTO> listAllTasksDTO = service.findAllTasks();
        return ResponseEntity.ok().body(listAllTasksDTO);
    }

    @PostMapping ResponseEntity<ResponseTaskDTO> createTask(@RequestBody @Valid TaskDTO taskDTO) {
        ResponseTaskDTO newTask = service.createTask(taskDTO);
        return ResponseEntity.ok().body(newTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTaskDTO> updateTask(@PathVariable Long id, @RequestBody @Valid TaskDTO taskDTO){
        ResponseTaskDTO updateTask = service.updateTask(id, taskDTO);
        return ResponseEntity.ok().body(updateTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
