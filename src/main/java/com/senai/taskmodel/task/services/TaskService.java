package com.senai.taskmodel.task.services;

import com.senai.taskmodel.task.dtos.ResponseTaskDTO;
import com.senai.taskmodel.task.dtos.TaskDTO;
import com.senai.taskmodel.task.entities.TaskEntity;
import com.senai.taskmodel.task.enums.EnumStatus;
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

    public List<ResponseTaskDTO> findAllTasks() {
        List<ResponseTaskDTO> listAllTasks = new ArrayList<>();

        List<TaskEntity> listAllTasksEntity = repository.findAll();

        for(TaskEntity taskEntity : listAllTasksEntity) {
            ResponseTaskDTO responseTaskDTO = ResponseTaskDTO
                    .builder()
                    .id(taskEntity.getId())
                    .title(taskEntity.getTitle())
                    .description(taskEntity.getDescription())
                    .dateTask(taskEntity.getDateTask())
                    .status(taskEntity.getStatus())
                    .userEmail(taskEntity.getUser().getEmail())
                    .build();

            listAllTasks.add(responseTaskDTO);
        }

        return listAllTasks;
    }

    public ResponseTaskDTO createTask(TaskDTO taskDTO) {
        Optional<UserEntity> userEntityEmail = userRepository.findByEmail(taskDTO.getUserEmail());

        if(userEntityEmail.isEmpty()) {
            return null;
        }

        TaskEntity newTaskEntity = TaskEntity
                .builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .dateTask(taskDTO.getDateTask())
                .status(taskDTO.getStatus())
                .user(userEntityEmail.get())
                .build();

        repository.save(newTaskEntity);

        return ResponseTaskDTO
                .builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .dateTask(taskDTO.getDateTask())
                .status(taskDTO.getStatus())
                .userEmail(userEntityEmail.get().getEmail())
                .build();
    }
    
    public ResponseTaskDTO updateTask(Long id, TaskDTO taskDTO) {

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

        return ResponseTaskDTO
                .builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .dateTask(taskDTO.getDateTask())
                .status(taskDTO.getStatus())
                .userEmail(userEntityEmail.get().getEmail())
                .build();
    }

    public void deleteTask(Long id) {
        Optional<TaskEntity> taskEntityId = repository.findById(id);

        if(taskEntityId.isEmpty()) {
            return;
        }

        repository.deleteById(id);
    }

//    public Boolean findAllTasksByUserEmail(TaskDTO taskDTO) {
//        List<TaskEntity> listAllTasksByUserEmail = repository.findByUserEmail(taskDTO.getUserEmail());
//
//        return !listAllTasksByUserEmail.isEmpty();
//    }
}
