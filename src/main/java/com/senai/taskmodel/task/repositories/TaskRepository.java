package com.senai.taskmodel.task.repositories;

import com.senai.taskmodel.task.entities.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Page<TaskEntity> findAll(Pageable pageable);

    Optional<TaskEntity> findByDateTaskAndUserEmail(LocalDate dataTask, String email);

    Boolean existsByUserEmail(String email);
}
