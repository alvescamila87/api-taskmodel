package com.senai.taskmodel.task.repositories;

import com.senai.taskmodel.task.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByDateTaskAndUserEmail(LocalDate dataTask, String email);

    Boolean existsByUserEmail(String email);
}
