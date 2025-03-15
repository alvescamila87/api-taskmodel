package com.senai.taskmodel.task.services;

import com.senai.taskmodel.task.dtos.TaskDTO;
import com.senai.taskmodel.task.entities.TaskEntity;
import com.senai.taskmodel.task.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository repository;

    public List<TaskDTO> findAllTasks() {
        List<TaskDTO> listAllTasks = new ArrayList<>();

        List<TaskEntity> listAllTasksEntity = repository.findAll();

        for(TaskEntity taskEntity : listAllTasksEntity) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(taskEntity.getId());
            taskDTO.setTitle(taskEntity.getTitle());
            taskDTO.setDescription(taskEntity.getDescription());
            taskDTO.setDateTask(taskEntity.getDateTask());
            taskDTO.setStatusENUM(taskEntity.getStatus());
            taskDTO.setUserEmail(taskEntity.getUser().getEmail());

            listAllTasks.add(taskDTO);
        }

        return listAllTasks;
    }
}
