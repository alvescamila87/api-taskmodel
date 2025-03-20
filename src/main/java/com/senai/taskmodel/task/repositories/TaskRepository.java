package com.senai.taskmodel.task.repositories;

import com.senai.taskmodel.task.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    //List<TaskEntity> findByUserEmail (String email);
    Boolean existsByUserEmail(String email);
}
