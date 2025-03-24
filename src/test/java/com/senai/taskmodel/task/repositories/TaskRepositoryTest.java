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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    TaskRepository repository;

    @Autowired
    UserRepository userRepository;

    private final String USER_DEFAULT_NAME = "João Batista";
    private final String USER_DEFAULT_TITLE = "Title test";
    private final String USER_DEFAULT_DESCRIPTION = "Descrption from test";
    private final EnumStatus USER_DEFAULT_STATUS = EnumStatus.IN_PROGRESS;
    private final LocalDate USER_DEFAULT_TASK_DATE = LocalDate.parse("2025/03/23", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    private final String USER_DEFAULT_USER_EMAIL = "zebedeu@gmail.com";

    @Test
    void when_save_task() {

        UserEntity userEntity = UserEntity
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_USER_EMAIL)
                .build();

        TaskEntity taskEntity = TaskEntity
                .builder()
                .title(USER_DEFAULT_TITLE)
                .description(USER_DEFAULT_DESCRIPTION)
                .status(USER_DEFAULT_STATUS)
                .dateTask(USER_DEFAULT_TASK_DATE)
                .user(userEntity)
                .build();

        TaskEntity newTask = repository.save(taskEntity);

        assertNotNull(newTask);
        assertEquals(USER_DEFAULT_TITLE, newTask.getTitle());
        assertEquals(USER_DEFAULT_DESCRIPTION, newTask.getDescription());
        assertEquals(USER_DEFAULT_STATUS, newTask.getStatus());
        assertEquals(USER_DEFAULT_TASK_DATE, newTask.getDateTask());
        assertEquals(userEntity, newTask.getUser());
    }

    @Test
    void when_check_existing_task_related_by_user_email_then_return_true() {
        UserEntity userEntity = UserEntity
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_USER_EMAIL)
                .build();

        userRepository.save(userEntity);

        TaskEntity taskEntity = TaskEntity
                .builder()
                .title(USER_DEFAULT_TITLE)
                .description(USER_DEFAULT_DESCRIPTION)
                .status(USER_DEFAULT_STATUS)
                .dateTask(USER_DEFAULT_TASK_DATE)
                .user(userEntity)
                .build();

        TaskEntity newTask = repository.save(taskEntity);

        Boolean hasTasksRelated = repository.existsByUserEmail(USER_DEFAULT_USER_EMAIL);

        assertTrue(hasTasksRelated);
        assertEquals(USER_DEFAULT_TITLE, newTask.getTitle());
        assertEquals(USER_DEFAULT_DESCRIPTION, newTask.getDescription());
        assertEquals(USER_DEFAULT_STATUS, newTask.getStatus());
        assertEquals(USER_DEFAULT_TASK_DATE, newTask.getDateTask());
        assertEquals(userEntity, newTask.getUser());
    }

    @Test
    void when_check_if_not_exist_task_related_by_user_email_then_return_false() {
        UserEntity userEntity = UserEntity
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_USER_EMAIL)
                .build();

        userRepository.save(userEntity);

        Boolean hasTasksRelated = repository.existsByUserEmail(USER_DEFAULT_USER_EMAIL);

        assertFalse(hasTasksRelated);
    }

    @Test
    void when_find_tasks_related_by_user_email_and_date_in_the_same_date_then_return_list() {
        UserEntity userEntity = UserEntity
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_USER_EMAIL)
                .build();

        userRepository.save(userEntity);

        TaskEntity taskEntity = TaskEntity
                .builder()
                .title(USER_DEFAULT_TITLE)
                .description(USER_DEFAULT_DESCRIPTION)
                .status(USER_DEFAULT_STATUS)
                .dateTask(USER_DEFAULT_TASK_DATE)
                .user(userEntity)
                .build();

        TaskEntity newTask = repository.save(taskEntity);

        List<TaskEntity> listTask = repository.findByDateTaskAndUserEmail(USER_DEFAULT_TASK_DATE, USER_DEFAULT_USER_EMAIL);

        assertNotNull(listTask);
        assertEquals(1, listTask.size());
        assertEquals(USER_DEFAULT_TITLE, newTask.getTitle());
        assertEquals(USER_DEFAULT_DESCRIPTION, newTask.getDescription());
        assertEquals(USER_DEFAULT_STATUS, newTask.getStatus());
        assertEquals(USER_DEFAULT_TASK_DATE, newTask.getDateTask());
        assertEquals(userEntity, newTask.getUser());
    }

    @Test
    void when_find_tasks_related_by_user_email_and_date_in_the_same_date_then_return_empty_list() {
        UserEntity userEntity = UserEntity
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_USER_EMAIL)
                .build();

        userRepository.save(userEntity);

        List<TaskEntity> listTask = repository.findByDateTaskAndUserEmail(USER_DEFAULT_TASK_DATE, USER_DEFAULT_USER_EMAIL);

        assertNotNull(listTask);
        assertEquals(0, listTask.size());
    }

}
