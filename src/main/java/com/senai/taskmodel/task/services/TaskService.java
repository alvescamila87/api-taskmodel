package com.senai.taskmodel.task.services;

import com.senai.taskmodel.task.dtos.TaskDTO;
import com.senai.taskmodel.task.entities.TaskEntity;
import com.senai.taskmodel.task.repositories.TaskRepository;
import com.senai.taskmodel.user.entities.UserEntity;
import com.senai.taskmodel.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository repository;

    @Autowired
    UserRepository userRepository;

    public List<TaskDTO> findAllTasks() {
        List<TaskDTO> listAllTasks = new ArrayList<>();

        List<TaskEntity> listAllTasksEntity = repository.findAll();

        for(TaskEntity taskEntity : listAllTasksEntity) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(taskEntity.getId());
            taskDTO.setTitle(taskEntity.getTitle());
            taskDTO.setDescription(taskEntity.getDescription());
            taskDTO.setDateTask(taskEntity.getDateTask());
            taskDTO.setStatus(taskEntity.getStatus());
            taskDTO.setUserEmail(taskEntity.getUser().getEmail());

            listAllTasks.add(taskDTO);
        }

        return listAllTasks;
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        TaskEntity newTaskEntity = new TaskEntity();

        Optional<UserEntity> userEntityEmail = userRepository.findByEmail(taskDTO.getUserEmail());

        if(userEntityEmail.isEmpty()) {
            return null;
        }

        newTaskEntity.setTitle(taskDTO.getTitle());
        newTaskEntity.setDescription(taskDTO.getDescription());
        newTaskEntity.setDateTask(taskDTO.getDateTask());
        newTaskEntity.setStatus(taskDTO.getStatus());
        newTaskEntity.setUser(userEntityEmail.get());

        repository.save(newTaskEntity);

        return taskDTO;
    }
    
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {

        Optional<TaskEntity> updateTaskEntityById = repository.findById(id);

        if(updateTaskEntityById.isEmpty()) {
            return null;
        }

        Optional<UserEntity> userEntityEmail = userRepository.findByEmail(taskDTO.getUserEmail());

        if(userEntityEmail.isEmpty()) {
            return null;
        }

        TaskEntity updateTaskEntity = updateTaskEntityById.get();
        updateTaskEntity.setTitle(taskDTO.getTitle());
        updateTaskEntity.setDescription(taskDTO.getDescription());
        updateTaskEntity.setDateTask(taskDTO.getDateTask());
        updateTaskEntity.setStatus(taskDTO.getStatus());
        updateTaskEntity.setUser(userEntityEmail.get());

        repository.save(updateTaskEntity);

        return taskDTO;
    }

    public void deleteTask(Long id) {
        Optional<TaskEntity> taskEntityId = repository.findById(id);

        if(taskEntityId.isEmpty()) {
            return;
        }

        repository.deleteById(id);
    }
}
