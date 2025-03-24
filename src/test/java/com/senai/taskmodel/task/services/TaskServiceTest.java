package com.senai.taskmodel.task.services;

import com.senai.taskmodel.task.dtos.ResponseTaskDTO;
import com.senai.taskmodel.task.entities.TaskEntity;
import com.senai.taskmodel.task.enums.EnumStatus;
import com.senai.taskmodel.task.repositories.TaskRepository;
import com.senai.taskmodel.user.entities.UserEntity;
import com.senai.taskmodel.user.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    TaskRepository repository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    TaskService service;

    private final Long TASK_DEFAULT_ID = 1L;
    private final String TASK_DEFAULT_NAME = "João Batista";
    private final String TASK_DEFAULT_TITLE = "Title test";
    private final String TASK_DEFAULT_DESCRIPTION = "Descrption from test";
    private final EnumStatus TASK_DEFAULT_STATUS = EnumStatus.IN_PROGRESS;
    private final LocalDate TASK_DEFAULT_TASK_DATE = LocalDate.parse("2025/03/23", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    private final String TASK_DEFAULT_USER_EMAIL = "zebedeu@gmail.com";

    @Test
    void when_find_tasks_then_return_all_tasks() {
        List<TaskEntity> taskEntityList = new ArrayList<>();

        UserEntity newUser1 = UserEntity
                .builder()
                .name(TASK_DEFAULT_NAME)
                .email(TASK_DEFAULT_USER_EMAIL)
                .build();

        TaskEntity taskEntity1 = TaskEntity
                .builder()
                .id(TASK_DEFAULT_ID)
                .title(TASK_DEFAULT_TITLE)
                .description(TASK_DEFAULT_DESCRIPTION)
                .status(TASK_DEFAULT_STATUS)
                .dateTask(TASK_DEFAULT_TASK_DATE)
                .user(newUser1)
                .build();

        taskEntityList.add(taskEntity1);

        when(repository.findAll()).thenReturn(taskEntityList);

        List<ResponseTaskDTO> responseTaskDTOList = service.findAllTasks();

        assertNotNull(responseTaskDTOList);
        assertEquals(1, responseTaskDTOList.size());
        assertEquals(TASK_DEFAULT_ID, taskEntity1.getId());
        assertEquals(TASK_DEFAULT_TITLE, taskEntity1.getTitle());
        assertEquals(TASK_DEFAULT_DESCRIPTION, taskEntity1.getDescription());
        assertEquals(TASK_DEFAULT_STATUS, taskEntity1.getStatus());
        assertEquals(TASK_DEFAULT_TASK_DATE, taskEntity1.getDateTask());
        assertEquals(TASK_DEFAULT_USER_EMAIL, taskEntity1.getUser().getEmail());

        verify(repository, times(1)).findAll();

    }
}
