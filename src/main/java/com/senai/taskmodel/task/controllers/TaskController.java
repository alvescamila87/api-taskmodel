package com.senai.taskmodel.task.controllers;

import com.senai.taskmodel.task.dtos.TaskDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @GetMapping
    public ResponseEntity<TaskDTO> listAllTasks() {
        TaskDTO tasksDTO = new TaskDTO();

        return ResponseEntity.ok().body(tasksDTO);
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
