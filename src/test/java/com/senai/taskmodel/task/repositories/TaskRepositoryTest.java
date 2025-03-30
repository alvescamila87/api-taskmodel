package com.senai.taskmodel.task.repositories;

import com.senai.taskmodel.task.entities.TaskEntity;
import com.senai.taskmodel.task.enums.EnumStatus;
import com.senai.taskmodel.user.entities.UserEntity;
import com.senai.taskmodel.user.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    TaskRepository repository;

    @Autowired
    UserRepository userRepository;

    private final String TASK_DEFAULT_NAME = "João Batista";
    private final String TASK_DEFAULT_TITLE = "Title test";
    private final String TASK_DEFAULT_DESCRIPTION = "Descrption from test";
    private final EnumStatus TASK_DEFAULT_STATUS = EnumStatus.IN_PROGRESS;
    private final LocalDate TASK_DEFAULT_TASK_DATE = LocalDate.parse("2025/03/23", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    private final String TASK_DEFAULT_USER_EMAIL = "zebedeu@gmail.com";

    @Test
    void when_save_task() {

        UserEntity userEntity = UserEntity
                .builder()
                .name(TASK_DEFAULT_NAME)
                .email(TASK_DEFAULT_USER_EMAIL)
                .build();

        TaskEntity taskEntity = TaskEntity
                .builder()
                .title(TASK_DEFAULT_TITLE)
                .description(TASK_DEFAULT_DESCRIPTION)
                .status(TASK_DEFAULT_STATUS)
                .dateTask(TASK_DEFAULT_TASK_DATE)
                .user(userEntity)
                .build();

        TaskEntity newTask = repository.save(taskEntity);

        assertNotNull(newTask);
        assertEquals(TASK_DEFAULT_TITLE, newTask.getTitle());
        assertEquals(TASK_DEFAULT_DESCRIPTION, newTask.getDescription());
        assertEquals(TASK_DEFAULT_STATUS, newTask.getStatus());
        assertEquals(TASK_DEFAULT_TASK_DATE, newTask.getDateTask());
        assertEquals(userEntity, newTask.getUser());
    }

    @Test
    void when_check_existing_task_related_by_user_email_then_return_true() {
        UserEntity userEntity = UserEntity
                .builder()
                .name(TASK_DEFAULT_NAME)
                .email(TASK_DEFAULT_USER_EMAIL)
                .build();

        userRepository.save(userEntity);

        TaskEntity taskEntity = TaskEntity
                .builder()
                .title(TASK_DEFAULT_TITLE)
                .description(TASK_DEFAULT_DESCRIPTION)
                .status(TASK_DEFAULT_STATUS)
                .dateTask(TASK_DEFAULT_TASK_DATE)
                .user(userEntity)
                .build();

        TaskEntity newTask = repository.save(taskEntity);

        Boolean hasTasksRelated = repository.existsByUserEmail(TASK_DEFAULT_USER_EMAIL);

        assertTrue(hasTasksRelated);
        assertEquals(TASK_DEFAULT_TITLE, newTask.getTitle());
        assertEquals(TASK_DEFAULT_DESCRIPTION, newTask.getDescription());
        assertEquals(TASK_DEFAULT_STATUS, newTask.getStatus());
        assertEquals(TASK_DEFAULT_TASK_DATE, newTask.getDateTask());
        assertEquals(userEntity, newTask.getUser());
    }

    @Test
    void when_check_if_not_exist_task_related_by_user_email_then_return_false() {
        UserEntity userEntity = UserEntity
                .builder()
                .name(TASK_DEFAULT_NAME)
                .email(TASK_DEFAULT_USER_EMAIL)
                .build();

        userRepository.save(userEntity);

        Boolean hasTasksRelated = repository.existsByUserEmail(TASK_DEFAULT_USER_EMAIL);

        assertFalse(hasTasksRelated);
    }

    @Test
    void when_find_tasks_related_by_user_email_and_date_in_the_same_date_then_return_list() {
        UserEntity userEntity = UserEntity
                .builder()
                .name(TASK_DEFAULT_NAME)
                .email(TASK_DEFAULT_USER_EMAIL)
                .build();

        userRepository.save(userEntity);

        TaskEntity taskEntity = TaskEntity
                .builder()
                .title(TASK_DEFAULT_TITLE)
                .description(TASK_DEFAULT_DESCRIPTION)
                .status(TASK_DEFAULT_STATUS)
                .dateTask(TASK_DEFAULT_TASK_DATE)
                .user(userEntity)
                .build();

        TaskEntity newTask = repository.save(taskEntity);

        Optional<TaskEntity> listTask = repository.findByDateTaskAndUserEmail(TASK_DEFAULT_TASK_DATE, TASK_DEFAULT_USER_EMAIL);
        assertNotNull(listTask);
        assertTrue(listTask.isPresent());

        TaskEntity taskFromOptional = listTask.get();

        assertEquals(TASK_DEFAULT_TITLE, taskFromOptional.getTitle());
        assertEquals(TASK_DEFAULT_DESCRIPTION, taskFromOptional.getDescription());
        assertEquals(TASK_DEFAULT_STATUS, taskFromOptional.getStatus());
        assertEquals(TASK_DEFAULT_TASK_DATE, taskFromOptional.getDateTask());
        assertEquals(userEntity, taskFromOptional.getUser());
    }

    @Test
    void when_find_tasks_related_by_user_email_and_date_in_the_same_date_then_return_empty_list() {
        UserEntity userEntity = UserEntity
                .builder()
                .name(TASK_DEFAULT_NAME)
                .email(TASK_DEFAULT_USER_EMAIL)
                .build();

        userRepository.save(userEntity);

        Optional<TaskEntity> listTask = repository.findByDateTaskAndUserEmail(TASK_DEFAULT_TASK_DATE, TASK_DEFAULT_USER_EMAIL);
        assertNotNull(listTask);
        assertTrue(listTask.isEmpty());
    }

}
