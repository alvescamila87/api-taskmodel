package com.senai.taskmodel.task.services;

import com.senai.taskmodel.task.dtos.ResponseTaskDTO;
import com.senai.taskmodel.task.dtos.TaskDTO;
import com.senai.taskmodel.task.entities.TaskEntity;
import com.senai.taskmodel.task.repositories.TaskRepository;
import com.senai.taskmodel.user.entities.UserEntity;
import com.senai.taskmodel.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
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

        if(listAllTasksEntity.isEmpty()){
            return listAllTasks;
        }

        for(TaskEntity taskEntity : listAllTasksEntity) {
            ResponseTaskDTO responseTaskDTO = ResponseTaskDTO
                    .builder()
                    .id(taskEntity.getId())
                    .title(taskEntity.getTitle())
                    .description(taskEntity.getDescription())
                    .dateTask(taskEntity.getDateTask())
                    .status(taskEntity.getStatus())
                    .userEmail(taskEntity.getUser().getEmail())
                    .success(true)
                    .build();

            listAllTasks.add(responseTaskDTO);
        }

        return listAllTasks;
    }

    public ResponseTaskDTO getTaskById(Long id) {
        Optional<TaskEntity> taskEntity = repository.findById(id);

        if(taskEntity.isEmpty()) {
           return ResponseTaskDTO
                    .builder()
                    .mensagem("Task not found")
                    .success(false)
                    .build();
        }
       return ResponseTaskDTO
                .builder()
                .id(taskEntity.get().getId())
                .title(taskEntity.get().getDescription())
                .dateTask(taskEntity.get().getDateTask())
                .status(taskEntity.get().getStatus())
                .userEmail(taskEntity.get().getUser().getEmail())
                .success(true)
                .build();
    }

    public ResponseTaskDTO createTask(TaskDTO taskDTO) {
        Optional<UserEntity> userEntityEmail = userRepository.findByEmail(taskDTO.getUserEmail());

        if(userEntityEmail.isEmpty()) {
            return ResponseTaskDTO
                    .builder()
                    .mensagem("User not found")
                    .success(false)
                    .build();
        }

//        if(hasTaskTheSameDate(taskDTO)){
//            return ResponseTaskDTO
//                    .builder()
//                    .mensagem("There is another task on the same date")
//                    .success(false)
//                    .build();
//        }

        if(hasTaskTheSameDate2(taskDTO)){
            return ResponseTaskDTO
                    .builder()
                    .mensagem("There is another task on the same date")
                    .success(false)
                    .build();
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
                .mensagem("Task has been updated successfully")
                .success(true)
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

    private Boolean hasTaskTheSameDate(TaskDTO taskDTO) {
        List<TaskEntity> listTasksEntity = repository.findByUserEmail(taskDTO.getUserEmail());

        for(TaskEntity taskEntity : listTasksEntity) {
            if(taskEntity.getDateTask().equals(taskDTO.getDateTask())) {
                return true;
            }
        }

        return false;
    }

    private Boolean hasTaskTheSameDate2(TaskDTO taskDTO) {
        List<TaskEntity> listTaskEntity = repository.findByDateTaskAndUserEmail(taskDTO.getDateTask(), taskDTO.getUserEmail());

        return !listTaskEntity.isEmpty();
    }
}
