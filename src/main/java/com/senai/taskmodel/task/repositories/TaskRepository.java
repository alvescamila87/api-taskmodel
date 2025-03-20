package com.senai.taskmodel.task.repositories;

import com.senai.taskmodel.task.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    // criar método para buscar usuario por email e task
    //List<TaskEntity> findByUserEmail (String email);
    Boolean existsByUserEmail(String email);
}
