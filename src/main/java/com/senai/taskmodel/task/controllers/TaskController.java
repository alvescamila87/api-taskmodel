package com.senai.taskmodel.task.controllers;

import com.senai.taskmodel.task.dtos.ResponseTaskDTO;
import com.senai.taskmodel.task.dtos.TaskDTO;
import com.senai.taskmodel.task.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService service;

    @GetMapping("/pagination")
    public ResponseEntity<Page<ResponseTaskDTO>> getAllTasks(Pageable pageable) {
        Page<ResponseTaskDTO> responseTaskDTOPage = service.getAllTasks(pageable);

        if(responseTaskDTOPage.isEmpty()) {
            return ResponseEntity.status(404).body(responseTaskDTOPage);
        }
        return ResponseEntity.ok().body(responseTaskDTOPage);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTaskDTO>> listAllTasks() {
        List<ResponseTaskDTO> listAllTasksDTO = service.findAllTasks();

        if(listAllTasksDTO.isEmpty()){
            return ResponseEntity.status(404).body(listAllTasksDTO);
        }
        return ResponseEntity.ok().body(listAllTasksDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTaskDTO> findTaskById(@PathVariable Long id) {
        ResponseTaskDTO responseTaskDTO = service.getTaskById(id);

        if(!responseTaskDTO.getSuccess()) {
            return ResponseEntity.status(404).body(responseTaskDTO);
        }

        return ResponseEntity.ok().body(responseTaskDTO);
    }

    @PostMapping ResponseEntity<ResponseTaskDTO> createTask(@RequestBody @Valid TaskDTO taskDTO) {
        ResponseTaskDTO newTask = service.createTask(taskDTO);

        if(!newTask.getSuccess() && newTask.getMensagem().equals("User not found")) {
            return ResponseEntity.status(404).body(newTask);
        }

        if(!newTask.getSuccess() && newTask.getMensagem().equals("There is another task on the same date")) {
            return ResponseEntity.status(409).body(newTask);
        }

        return ResponseEntity.ok().body(newTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTaskDTO> updateTask(@PathVariable Long id, @RequestBody @Valid TaskDTO taskDTO){
        ResponseTaskDTO updateTask = service.updateTask(id, taskDTO);

        if(!updateTask.getSuccess() && updateTask.getMensagem().equals("Task not found")) {
            return ResponseEntity.status(404).body(updateTask);
        }

        if(!updateTask.getSuccess() && updateTask.getMensagem().equals("User not found")) {
            return ResponseEntity.status(404).body(updateTask);
        }

        if(!updateTask.getSuccess() && updateTask.getMensagem().equals("There is another task on the same date")) {
            return ResponseEntity.status(409).body(updateTask);
        }

        return ResponseEntity.ok().body(updateTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTaskDTO> deleteTask(@PathVariable Long id) {
        ResponseTaskDTO responseTaskDTO = service.deleteTask(id);

        if(!responseTaskDTO.getSuccess()){
            return ResponseEntity.status(404).body(responseTaskDTO);
        }

        return ResponseEntity.ok().body(responseTaskDTO);
    }
}
